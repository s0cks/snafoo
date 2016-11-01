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
    Voter v = this.votingRepo.findByVoterId(id);
    if(v == null) {
      v = new Voter(id);
      this.votingRepo.save(v);
    }
    return v;
  }

  @Override
  public Voter save(Voter voter) {
    try{
      if(voter == null) throw new IllegalStateException("Voter == null");
      return this.votingRepo.save(voter);
    } catch (Exception e){
      throw new RuntimeException(e);
    }
  }
}