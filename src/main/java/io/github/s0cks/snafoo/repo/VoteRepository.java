package io.github.s0cks.snafoo.repo;

import io.github.s0cks.snafoo.model.domain.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface VoteRepository
extends Repository<Vote, UUID> {
  public Vote save(Vote v);

  @Query("SELECT T FROM Vote AS T WHERE T.snack = :snack")
  public List<Vote> findBySnack(@Param("snack") String snack);
}