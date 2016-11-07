package io.github.s0cks.snafoo.service;

import io.github.s0cks.snafoo.model.domain.User;

public interface UserService{
  public User getUser(String uuid);
  public User save(User user);
}