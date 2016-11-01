package io.github.s0cks.snafoo.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Voter
implements Serializable{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private Long voterId;

  @Column(nullable = false)
  private Integer count;

  public Voter(){}

  public Voter(long id){
    this.voterId = id;
    this.count = 0;
  }

  public boolean vote(Food f){
    if(f.addVoter(this)){
      this.count += 1;
      return true;
    }
    return false;
  }

  public int getCount(){
    return this.count;
  }
}