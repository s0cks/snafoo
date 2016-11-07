package io.github.s0cks.snafoo.service;

import io.github.s0cks.snafoo.model.domain.Vote;
import io.github.s0cks.snafoo.model.rest.Snack;

import java.util.List;

/**
 * VoteService
 *
 * @see {@link VoteServiceImpl} For implementation
 */
public interface VoteService{
  /**
   * Gets a list of votes by the {@link Snack}
   *
   * @param snack The snack to get the votes from
   * @return The list of votes
   */
  public List<Vote> getVotesBy(Snack snack);

  /**
   * Saves the supplied {@link Vote} to the repository.
   *
   * @param vote The vote to save
   * @return The supplied vote
   */
  public Vote save(Vote vote);
}