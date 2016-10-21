package io.github.s0cks.snafoo.service;

import io.github.s0cks.snafoo.service.entity.Voter;
import org.springframework.data.repository.Repository;

interface VotingRepository
extends Repository<Voter, Long> {
  public Voter findById(long id);
}