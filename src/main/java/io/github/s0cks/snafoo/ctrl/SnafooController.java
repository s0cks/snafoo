package io.github.s0cks.snafoo.ctrl;

import io.github.s0cks.snafoo.SnafooSnackService;
import io.github.s0cks.snafoo.service.FoodService;
import io.github.s0cks.snafoo.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public final class SnafooController{
  @Autowired private VotingService voting;
  @Autowired private FoodService food;

  @RequestMapping({ "/", "/snafoo/voting" })
  public String getVoting(@CookieValue(value = "VoteId", defaultValue = "0") Integer voteId,
                           Model model){
    model.addAttribute("snacks", SnafooSnackService.INSTANCE.getSnacks());


    return "voting";
  }

  @RequestMapping({ "/snafoo/list" })
  public String getShoppingList(Model model){
    return "list";
  }
}