package io.github.s0cks.snafoo.model.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.UUID;

@Entity
public class User{
  @Id
  @GeneratedValue
  @Column
  private UUID id;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Vote> votes;

  public Set<Vote> getVotes() {
    return votes;
  }

  public User setVotes(Set<Vote> votes) {
    this.votes = votes;
    return this;
  }

  public UUID getId() {
    return id;
  }

  public User setId(UUID id) {
    this.id = id;
    return this;
  }
}