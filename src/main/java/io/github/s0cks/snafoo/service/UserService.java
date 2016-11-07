package io.github.s0cks.snafoo.service;

import io.github.s0cks.snafoo.model.domain.User;

/**
 * UserService
 *
 * A more Java-y way of encapsulation. Mostly just trying to follow Spring's design flow.
 *
 * Also allows me to use the autowiring efficiently by wrapping the {@link io.github.s0cks.snafoo.repo.UserRepository} class with this.
 *
 * @see {@link UserServiceImpl} For implementation
 */
public interface UserService{
  /**
   * Gets a user by a UUID lookup
   * There are no null checks so can be null.
   *
   * This just wraps {@link io.github.s0cks.snafoo.repo.UserRepository#findById(String)}
   *
   * @param uuid The UUID of the User
   * @return The User
   */
  public User getUser(String uuid);

  /**
   * Saves a user to the repository.
   *
   * This just wraps {@link io.github.s0cks.snafoo.repo.UserRepository#save(User)}
   *
   * @param user The User to save
   * @return The supplied User
   */
  public User save(User user);
}