package io.github.s0cks.snafoo.ctrl;

import io.github.s0cks.snafoo.SnackPredicates;
import io.github.s0cks.snafoo.SnafooSnackService;
import io.github.s0cks.snafoo.model.domain.Vote;
import io.github.s0cks.snafoo.model.rest.Snack;
import io.github.s0cks.snafoo.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class SnafooController {
  @Autowired
  private VoteService voteService;

  @RequestMapping(
  {"/", "/voting"}
  )
  public String getVotingTemplate(Model model) {
    Set<Snack> snacks = new HashSet<>();
    snacks.addAll(Arrays.asList(SnafooSnackService.INSTANCE.getSnacks()));

    model.addAttribute("snacks", snacks.stream()
                                       .filter(SnackPredicates.ALWAYS_PURCHASED)
                                       .collect(Collectors.toSet()));

    model.addAttribute("suggestions", snacks.stream()
                                            .filter(SnackPredicates.OPTIONALLY_PURCHASED)
                                            .map((s) -> {
                                              List<Vote> votesFor = voteService.getVotesBy(s);
                                              return new Snack.VotedSnack(s, votesFor.size(), false);
                                            })
                                            .collect(Collectors.toSet()));
    return "voting";
  }
}