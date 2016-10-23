package io.github.s0cks.snafoo.ctrl;

import io.github.s0cks.snafoo.SnafooSnackService;
import io.github.s0cks.snafoo.data.Snack;
import io.github.s0cks.snafoo.data.SnackData;
import io.github.s0cks.snafoo.service.FoodService;
import io.github.s0cks.snafoo.service.VotingService;
import io.github.s0cks.snafoo.service.entity.Food;
import io.github.s0cks.snafoo.service.entity.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;
import java.util.List;

@Controller
public final class SnafooController{
  @Autowired private VotingService voting;
  @Autowired private FoodService food;

  @RequestMapping({ "/", "/snafoo/voting" })
  public String getVoting(@CookieValue(value = "VoteId", defaultValue = "0") Integer voteId,
                           Model model){
    Voter voter = this.voting.getVoter(voteId);
    List<Snack> snacks = new LinkedList<>();
    for(SnackData data : SnafooSnackService.INSTANCE.getSnacks()){
      Food f = this.food.getFood(data.name);
      snacks.add(new Snack(data, f.getNumOfVotes(), f.isVotedOnBy(voter)));
    }
    model.addAttribute("snacks", snacks);
    return "voting";
  }

  @RequestMapping({ "/snafoo/list" })
  public String getShoppingList(Model model){
    return "list";
  }
}