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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class SnafooController {
  @Autowired
  private VoteService voteService;
  @Autowired
  private UserService userService;

  @ResponseBody
  @RequestMapping(
  value = "/suggest",
  method = RequestMethod.POST,
  produces = "application/json",
  consumes = "application/json"
  )
  public SnafooResponse postSuggestion(@RequestBody Suggestion suggestion) {
    Snack s = SnafooSnackService.INSTANCE.addSuggestion(suggestion);
    if (s != null) return new SnafooResponse(200, "Suggested!");
    return new SnafooResponse(500, "Cannot suggest");
  }

  @ResponseBody
  @RequestMapping(
  value = "/vote/{Name}",
  method = RequestMethod.GET,
  produces = "application/json"
  )
  public SnafooResponse getVote(@CookieValue("UserId") String userId,
                                @PathVariable("Name") String name) {
    User user = this.userService.getUser(userId);
    if (user.votesLeft() == 0) return new SnafooResponse(500, "Vote limit reached!");
    this.voteService.save(new Vote(user, name));
    return new SnafooResponse(200, "Voted!");
  }

  @RequestMapping(
  {"/shopping"}
  )
  public String getShoppingTemplate(Model model) {
    Set<Snack> shoppingList = new TreeSet<>(Snack.SORT_BY_NAME);
    Set<Snack> all = SnafooSnackService.allSnacks();

    Queue<Snack> alwaysPurchased = new LinkedList<>();
    all.stream()
       .filter(SnackPredicates.ALWAYS_PURCHASED)
       .forEach(alwaysPurchased::add);

    Queue<Snack> votedOn = new LinkedList<>();
    all.stream()
       .filter(SnackPredicates.OPTIONALLY_PURCHASED)
       .map((s) -> {
         List<Vote> votes = voteService.getVotesBy(s);
         return new Snack.VotedSnack(s, votes.size(), false);
       })
       .sorted(Snack.SORT_BY_VOTES)
       .forEach(votedOn::add);

    while (shoppingList.size() <= 10 && !votedOn.isEmpty()) {
      if (!alwaysPurchased.isEmpty()) {
        shoppingList.add(alwaysPurchased.remove());
      } else {
        shoppingList.add(votedOn.remove());
      }
    }

    model.addAttribute("snacks", shoppingList);
    return "shopping";
  }

  @RequestMapping(
  {"/suggestions"}
  )
  public String getSuggestionsTemplate(Model model) {
    model.addAttribute("suggestions", SnafooSnackService.allSnacks()
                                                        .stream()
                                                        .filter(SnackPredicates.OPTIONALLY_PURCHASED)
                                                        .collect(Collectors.toSet()));
    return "suggestions";
  }

  @RequestMapping(
  {"/", "/voting"}
  )
  public String getVotingTemplate(@CookieValue(value = "UserId",
                                               defaultValue = "") String userId,
                                  HttpServletResponse resp,
                                  Model model) {
    final User user;
    if (userId.isEmpty()) {
      UUID uuid = UUID.randomUUID();
      Cookie c = new Cookie("UserId", uuid.toString());
      c.setMaxAge(365 * 24 * 60 * 60);
      resp.addCookie(c);
      user = new User(uuid);
      this.userService.save(user);
    } else {
      user = this.userService.getUser(userId);
    }

    Set<Snack> snacks = SnafooSnackService.allSnacks();

    model.addAttribute("snacks", snacks.stream()
                                       .filter(SnackPredicates.ALWAYS_PURCHASED)
                                       .collect(Collectors.toSet()));
    model.addAttribute("suggestions", snacks.stream()
                                            .filter(SnackPredicates.OPTIONALLY_PURCHASED)
                                            .map((s) -> {
                                              List<Vote> votesFor = voteService.getVotesBy(s);
                                              return new Snack.VotedSnack(s, votesFor.size(), user.hasVotedOn(s));
                                            })
                                            .collect(Collectors.toSet()));
    model.addAttribute("votesLeft", user.votesLeft());
    return "voting";
  }
}