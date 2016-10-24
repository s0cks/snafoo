package io.github.s0cks.snafoo.service;

import io.github.s0cks.snafoo.service.entity.Voter;

public interface VotingService{
  public Voter getVoter(long id);
  public Voter save(Voter voter);
}