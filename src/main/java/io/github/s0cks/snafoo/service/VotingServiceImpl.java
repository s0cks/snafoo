package io.github.s0cks.snafoo.service;

import io.github.s0cks.snafoo.service.entity.Voter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component("votingService")
public class VotingServiceImpl
implements VotingService{
  private final VotingRepository votingRepo;

  public VotingServiceImpl(VotingRepository repo){
    this.votingRepo = repo;
  }

  @Override
  public Voter getVoter(long id) {
    return this.votingRepo.findById(id);
  }
}