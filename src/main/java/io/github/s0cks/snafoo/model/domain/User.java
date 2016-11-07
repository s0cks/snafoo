package io.github.s0cks.snafoo.model.domain;

import io.github.s0cks.snafoo.model.rest.Snack;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class User {
  @Id
  @Column
  private String id;

  @OneToMany(mappedBy = "user",
             cascade = CascadeType.ALL,
             orphanRemoval = true)
  private Set<Vote> votes;

  public User(){}

  public User(UUID uuid){
    this.id = uuid.toString();
    this.votes = new HashSet<>();
  }

  public Set<Vote> getVotes() {
    return votes;
  }

  public User setVotes(Set<Vote> votes) {
    this.votes = votes;
    return this;
  }

  public boolean hasVotedOn(Snack s) {
    return this.votes.stream()
                     .map(Vote::getSnack)
                     .anyMatch((name) -> name.equalsIgnoreCase(s.name));
  }

  public int votesLeft(){
    return 3 - this.votes.size();
  }

  public String getId() {
    return id;
  }

  public User setId(String id) {
    this.id = id;
    return this;
  }
}