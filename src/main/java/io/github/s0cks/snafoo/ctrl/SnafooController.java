package io.github.s0cks.snafoo.ctrl;

import io.github.s0cks.snafoo.SnackPredicates;
import io.github.s0cks.snafoo.SnafooResponse;
import io.github.s0cks.snafoo.SnafooSnackService;
import io.github.s0cks.snafoo.model.domain.User;
import io.github.s0cks.snafoo.model.domain.Vote;
import io.github.s0cks.snafoo.model.rest.Snack;
import io.github.s0cks.snafoo.model.rest.Suggestion;
import io.github.s0cks.snafoo.service.UserService;
import io.github.s0cks.snafoo.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This is where the real magic happens
 */
@Controller // Declare it to be a controller through annotations and it automagically happens yay class loader black magic....
public class SnafooController {
  @Autowired // More class loader magic...
  private VoteService voteService; // Give me the VoteService
  @Autowired
  private UserService userService; // More dependency injection magic for the UserService

  @ResponseBody // Need this so Spring will use the returned value of this function
  @RequestMapping( // Need this for mapping this function to the request route
  value = "/suggest", // This is for the endpoint's location
  method = RequestMethod.POST, // Declaring that it should accept POST only...
  produces = "application/json", // Produces a JSON object response see SnafooResponse as to what that looks like
  consumes = "application/json" // Consumes a JSON object see Suggestion as to what this looks like
  )
  public SnafooResponse postSuggestion(/* This annotation will allow the param to be mapped to the request's body */ @RequestBody Suggestion suggestion) {
    Snack s = SnafooSnackService.INSTANCE.addSuggestion(suggestion);
    if (s != null) return new SnafooResponse(200, "Suggested!");
    return new SnafooResponse(500, "Cannot suggest");
  }

  @ResponseBody // Same old boiler-plate code...
  @RequestMapping(
  value = "/vote/{Name}",
  method = RequestMethod.GET, // This time it accepts GET requests only
  produces = "application/json"
  )
  public SnafooResponse getVote(@CookieValue("UserId") String userId, // This will give me the already established cookie that contains the current User
                                @PathVariable("Name") String name) { // THe anme of the food
    User user = this.userService.getUser(userId);
    if (user.votesLeft() == 0) return new SnafooResponse(500, "Vote limit reached!");
    this.voteService.save(new Vote(user, name));
    return new SnafooResponse(200, "Voted!");
  }

  @RequestMapping(
  {"/shopping"} // More stuff this time its implicitly a GET only request and returns the template's resource name
  )
  public String getShoppingTemplate(Model model) {
    Set<Snack> shoppingList = new TreeSet<>(Snack.SORT_BY_NAME); // This will ensure that the calculated list is sorted by name
    Set<Snack> all = SnafooSnackService.allSnacks();

    Queue<Snack> alwaysPurchased = new LinkedList<>(); // Simple queue implementation
    all.stream()
       .filter(SnackPredicates.ALWAYS_PURCHASED)
       .forEach(alwaysPurchased::add); // Populate the results with the always purchased sub-set

    Queue<Snack> votedOn = new LinkedList<>();
    all.stream()
       .filter(SnackPredicates.OPTIONALLY_PURCHASED)
       .map((s) -> {
         List<Vote> votes = voteService.getVotesBy(s);
         return new Snack.VotedSnack(s, votes.size(), false);
       })
       .sorted(Snack.SORT_BY_VOTES)
       .forEach(votedOn::add); // Filter out the optionally purchased snacks then map them to the VotedSnack sub-type to allow us to prioritize by the most voted Snack
    // Note: This could be skipped and just call Collections.sort but that is an unnecesary complication, but the end result would be the same

    // Do this will it has 10 or less items
    // Note the extra clause, this is needed for when the votedOn set and the alwaysPurchased set have less than 10 elements combined
    while (shoppingList.size() <= 10 && !votedOn.isEmpty()) {
      if (!alwaysPurchased.isEmpty()) { // Filter the always purchased items first
        shoppingList.add(alwaysPurchased.remove());
      } else {
        shoppingList.add(votedOn.remove()); // When its empty populate with the most voted on element and pop it from the queue
      }
    }

    model.addAttribute("snacks", shoppingList); // Bind the "snacks" attribute to the calculated set
    return "shopping"; // Return the template's resource path
  }

  @RequestMapping(
  {"/suggestions"} // Another basic resource path
  )
  public String getSuggestionsTemplate(Model model) {
    model.addAttribute("suggestions", SnafooSnackService.allSnacks()
                                                        .stream()
                                                        .filter(SnackPredicates.OPTIONALLY_PURCHASED)
                                                        .collect(Collectors.toSet())); // Filter out the suggestions
    return "suggestions";
  }

  @RequestMapping(
  {"/", "/voting"} // This should map to the home path and the /voting path just in case I guess
  )
  public String getVotingTemplate(@CookieValue(value = "UserId", // Give me the cookie for the User ID
                                               defaultValue = "") String userId, // Default to an empty string to calculate a new one
                                  HttpServletResponse resp, // This is needed for the cookie saving
                                  Model model) {
    final User[] user = new User[1];// Final cause it will be used in a lambda (which despite not being the same as an anonymous inner class semantically when compiled down to bytecode it still needs the final modifier for stack frame preservation
    Arrays.fill(user, null);
    if (userId.isEmpty()) { // Default to a new ID if its empty, this hsould be the default case when first visiting or after deleting the cookie
      UUID uuid = UUID.randomUUID(); // New UUID
      Cookie c = new Cookie("UserId", uuid.toString()); // New Cookie
      c.setMaxAge(365 * 24 * 60 * 60); // Set this to a year cause gradle takes a year to boot the application up
      resp.addCookie(c); // Here you add the cookie
      user[0] = new User(uuid); // Create a new User and save it here. Don't try to minimize this with user = this.userService.save(new User(uuid))); because it will return null for some reason
      this.userService.save(user[0]);
    } else {
      user[0] = this.userService.getUser(userId); // Since its a return visit and a valid cookie then it will just collect the User
      if(user[0] == null){
        UUID uuid = UUID.randomUUID();
        Cookie c = new Cookie("UserId", uuid.toString());
        c.setMaxAge(365 * 24 * 60 * 60);
        resp.addCookie(c);
        user[0] = new User(uuid);
        this.userService.save(user[0]);
      }
    }

    Set<Snack> snacks = SnafooSnackService.allSnacks();

    model.addAttribute("snacks", snacks.stream()
                                       .filter(SnackPredicates.ALWAYS_PURCHASED)
                                       .collect(Collectors.toSet())); // Filter out the always purchased snacks and assign it to the snacks attribute
    model.addAttribute("suggestions", snacks.stream()
                                            .filter(SnackPredicates.OPTIONALLY_PURCHASED)
                                            .map((s) -> {
                                              List<Vote> votesFor = voteService.getVotesBy(s);
                                              return new Snack.VotedSnack(s, votesFor.size(), user[0].hasVotedOn(s));
                                            })
                                            .sorted(Snack.SORT_BY_VOTES)
                                            .collect(Collectors.toSet())); // Filter out the optional snacks and map them to a voted snack sub-type to help with client side rendering. See the template for relevant info
    model.addAttribute("votesLeft", user[0].votesLeft()); // Give the client the remaining votes this User has
    return "voting";
  }
}