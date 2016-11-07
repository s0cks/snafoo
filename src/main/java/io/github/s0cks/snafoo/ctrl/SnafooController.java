package io.github.s0cks.snafoo.ctrl;

import io.github.s0cks.snafoo.SnackPredicates;
import io.github.s0cks.snafoo.SnafooResponse;
import io.github.s0cks.snafoo.SnafooSnackService;
import io.github.s0cks.snafoo.model.domain.User;
import io.github.s0cks.snafoo.model.domain.Vote;
import io.github.s0cks.snafoo.model.rest.Snack;
import io.github.s0cks.snafoo.service.UserService;
import io.github.s0cks.snafoo.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class SnafooController {
  @Autowired private VoteService voteService;
  @Autowired private UserService userService;

  @ResponseBody
  @RequestMapping(
    value = "/vote/{Name}",
    method = RequestMethod.GET,
    produces = "application/json"
  )
  public SnafooResponse getVote(@CookieValue("UserId") String userId,
                                @PathVariable("Name") String name){
    User user = this.userService.getUser(userId);
    if(user.votesLeft() == 0) return new SnafooResponse(500, "Vote limit reached!");
    this.voteService.save(new Vote(user, name));
    return new SnafooResponse(200, "Voted!");
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
    } else{
      user = this.userService.getUser(userId);
    }

    Set<Snack> snacks = new HashSet<>();
    snacks.addAll(Arrays.asList(SnafooSnackService.INSTANCE.getSnacks()));

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
    return "voting";
  }
}