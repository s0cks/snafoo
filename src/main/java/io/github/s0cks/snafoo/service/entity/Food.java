package io.github.s0cks.snafoo.service.entity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Food
implements Serializable{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String name;

  @ElementCollection
  @CollectionTable(
    name = "Voter",
    joinColumns = @JoinColumn(name = "voter_id")
  )
  private Set<Voter> voters;

  public Food(){}

  public Food(long id){
    this.id = id;
  }

  public Food(String name){
    this.name = name;
    this.voters = new HashSet<>();
  }

  public boolean addVoter(Voter v){
    if(this.voters.contains(v)) return false;
    this.voters.add(v);
    return true;
  }

  public String getName(){
    return this.name;
  }

  public boolean isVotedOnBy(Voter v){
    return this.voters.contains(v);
  }

  public int getNumOfVotes(){
    return this.voters.size();
  }
}