package io.github.s0cks.snafoo.service;

import io.github.s0cks.snafoo.model.domain.User;
import io.github.s0cks.snafoo.model.domain.Vote;
import io.github.s0cks.snafoo.model.rest.Snack;

import java.util.List;

public interface VoteService{
  public List<Vote> getVotesBy(User user);
  public List<Vote> getVotesBy(Snack snack);
  public Vote getVoteByUserAndSnack(User user, Snack snack);
  public Vote save(Vote vote);
}