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

/**
 * Standard issue JPA entity
 */
@Entity
public class User {
  @Id
  @Column
  private String id; // Attempted to use UUID but for lack of motivation to splurge on complexity I used String for its primary key

  @OneToMany(mappedBy = "user",
             cascade = CascadeType.ALL,
             orphanRemoval = true)
  private Set<Vote> votes; // This user can haz many Votes

  // Default constructor to comply to JPA
  public User(){}

  // Another constructor for you
  public User(UUID uuid){
    this.id = uuid.toString();
    this.votes = new HashSet<>();
  }

  // Getters and Setters for the votes column
  public Set<Vote> getVotes() {
    return votes;
  }

  // Builder cause why not?
  public User setVotes(Set<Vote> votes) {
    this.votes = votes;
    return this;
  }

  /**
   * Kind of an overly complicated way of checking if a User has voted on a snack using Java8's Streaming library
   */
  public boolean hasVotedOn(Snack s) {
    return this.votes.stream()
                     .map(Vote::getSnack)
                     .anyMatch((name) -> name.equalsIgnoreCase(s.name));
  }

  /**
   * This just returns the remaining votes a User has
   */
  public int votesLeft(){
    return 3 - this.votes.size(); // One might make the 3 a constant for ease of updating but for simplicity its 3
  }

  // Getters and Setters for the id field
  public String getId() {
    return id;
  }
  
  // Builder again cause why not?
  // P.S When in the off chance someone doesn't like the builder pattern for whatever ungodly known reason changing it to a void is no hastle
  public User setId(String id) {
    this.id = id;
    return this;
  }
}