package io.github.s0cks.snafoo.ctrl;

import io.github.s0cks.snafoo.Response;
import io.github.s0cks.snafoo.SnafooSnackService;
import io.github.s0cks.snafoo.data.Snack;
import io.github.s0cks.snafoo.data.Snacks;
import io.github.s0cks.snafoo.data.Suggestion;
import io.github.s0cks.snafoo.service.FoodService;
import io.github.s0cks.snafoo.service.VotingService;
import io.github.s0cks.snafoo.service.entity.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public final class SnafooController{
  @Autowired private VotingService voting;
  @Autowired private FoodService food;

  @RequestMapping({ "/snafoo/suggestions" })
  public String getSuggestions(Model model){
    model.addAttribute("suggestions", Snacks.getSuggestions());
    return "suggestions";
  }

  @RequestMapping({ "/", "/snafoo/voting" })
  public String getVoting(@CookieValue(value = "VoteId", defaultValue = "0") Integer voteId,
                           Model model){
    model.addAttribute("snacks", Snacks.getBaseSet());
    model.addAttribute("suggestions", Snacks.getSuggestions());
    return "voting";
  }

  @ResponseBody
  @RequestMapping(value = { "/snafoo/suggest" },
                  method = RequestMethod.POST,
                  produces = "application/json",
                  consumes = "application/json")
  public Response postVote(@RequestBody Suggestion suggestion,
                           Model model){
    System.out.println("Suggestion: " + suggestion);

    Snack s = SnafooSnackService.INSTANCE.postSuggestion(suggestion);
    if(s != null){
      Snacks.add(s);
      return new Response(200, "Success: Suggested!");
    }

    return new Response(500, "Error: Response == null!");
  }

  @ResponseBody
  @RequestMapping(value = { "/snafoo/vote/{Name}" },
                  method = RequestMethod.GET,
                  produces = "application/json")
  public Response getVote(@CookieValue(value = "VoteId", defaultValue = "0") Integer voteId,
                        @PathVariable("Name") String name,
                        Model model){
    Voter voter = this.voting.getVoter(voteId);
    if(voter.getCount() >= 3){
      return new Response(500, "Error: Vote limit reached!");
    }

    Optional<Snack> snack = Snacks.get(name);
    if(snack.isPresent()){
      Snack s = snack.get();
      if(s.vote(this.food, this.voting, voter)){
        return new Response(200, "Success: Voted!");
      } else{
        return new Response(500, "Error: Already Voted!");
      }
    }

    return new Response(500, "Error: Unknown name: " + name);
  }
}