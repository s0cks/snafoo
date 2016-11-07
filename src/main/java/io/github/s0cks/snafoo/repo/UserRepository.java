package io.github.s0cks.snafoo.repo;

import io.github.s0cks.snafoo.model.domain.User;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface UserRepository
extends Repository<User, UUID>{
  public User save(User user);
  public User findById(String id);
}