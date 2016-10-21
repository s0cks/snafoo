package io.github.s0cks.snafoo.service.entity;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Voter
implements Serializable{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private Integer count;

  @ElementCollection
  @Column(nullable = false)
  private Set<String> spent;

  public Voter(){
    this.count = 0;
    this.spent = new HashSet<>();
  }

  public int getCount(){
    return this.count;
  }

  public Set<String> getSpent(){
    return Collections.unmodifiableSet(this.spent);
  }
}