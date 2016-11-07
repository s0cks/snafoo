package io.github.s0cks.snafoo.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class Vote{
  @Id
  @GeneratedValue
  @Column
  private UUID id;

  @ManyToOne
  private User user;

  @Column(nullable = false)
  private String snack;

  public String getSnack() {
    return snack;
  }

  public Vote setSnack(String snack) {
    this.snack = snack;
    return this;
  }

  public User getUser() {
    return user;
  }

  public Vote setUser(User user) {
    this.user = user;
    return this;
  }
}