package io.github.s0cks.snafoo.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Food
implements Serializable{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private Integer voteCount;

  @Column(nullable = false)
  private String name;

  public Food(){
    this.voteCount = 0;
  }

  public String getName(){
    return this.name;
  }

  public int getVotes(){
    return this.voteCount;
  }
}