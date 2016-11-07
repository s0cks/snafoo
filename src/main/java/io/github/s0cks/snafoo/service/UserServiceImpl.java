package io.github.s0cks.snafoo.service;

import io.github.s0cks.snafoo.model.domain.User;
import io.github.s0cks.snafoo.repo.UserRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component("userService")
public class UserServiceImpl
implements UserService{
  private final UserRepository users;

  public UserServiceImpl(UserRepository repo){
    this.users = repo;
  }

  @Override
  public User getUser(String uuid) {
    return this.users.findById(uuid);
  }

  @Override
  public User save(User user) {
    return this.users.save(user);
  }
}