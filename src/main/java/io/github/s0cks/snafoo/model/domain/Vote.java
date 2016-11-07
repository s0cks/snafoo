package io.github.s0cks.snafoo.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

/**
 * Another standard JPA entity this one has nothing really special about it
 * No further comments
 */
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

  public Vote(){}

  public Vote(User user, String snack){
    this.id = UUID.randomUUID();
    this.snack = snack;
    this.user = user;
  }

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