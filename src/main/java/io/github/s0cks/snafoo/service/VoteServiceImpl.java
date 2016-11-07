package io.github.s0cks.snafoo.service;

import io.github.s0cks.snafoo.model.domain.User;
import io.github.s0cks.snafoo.model.domain.Vote;
import io.github.s0cks.snafoo.model.rest.Snack;
import io.github.s0cks.snafoo.repo.VoteRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Component("voteService")
public class VoteServiceImpl
implements VoteService{
  private final VoteRepository votes;

  public VoteServiceImpl(VoteRepository repo){
    this.votes = repo;
  }

  @Override
  public List<Vote> getVotesBy(User user) {
    return this.votes.findByUser(user);
  }

  @Override
  public List<Vote> getVotesBy(Snack snack) {
    return this.votes.findBySnack(snack.name);
  }

  @Override
  public Vote getVoteByUserAndSnack(User user, Snack snack) {
    return this.votes.findBySnackAndUser(user, snack.name);
  }

  @Override
  public Vote save(Vote vote) {
    return this.votes.save(vote);
  }
}