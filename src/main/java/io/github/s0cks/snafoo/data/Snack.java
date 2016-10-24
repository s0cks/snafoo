package io.github.s0cks.snafoo.data;

import io.github.s0cks.snafoo.service.FoodService;
import io.github.s0cks.snafoo.service.VotingService;
import io.github.s0cks.snafoo.service.entity.Food;
import io.github.s0cks.snafoo.service.entity.Voter;

public class Snack{
  public final int id;
  public final int purchaseCount;
  public final String name;
  public final String purchaseLocations;
  public final String lastPurchaseDate;
  public final boolean optional;
  public int votes;

  public Snack(int id, String name, String purchaseLocations, String lastPurchaseDate, int purchaseCount, int votes, boolean optional) {
    this.id = id;
    this.name = name;
    this.purchaseLocations = purchaseLocations;
    this.lastPurchaseDate = lastPurchaseDate;
    this.purchaseCount = purchaseCount;
    this.votes = votes;
    this.optional = optional;
  }

  public boolean vote(FoodService foods, VotingService voting, Voter voter){
    Food f = foods.getFood(this.name);
    if(voter.vote(f)){
      voting.save(voter);
      this.votes = f.getNumOfVotes();
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 0x0;
    hash += Integer.hashCode(this.id);
    return hash;
  }

  @Override
  public boolean equals(Object obj){
    if(obj == this) return true;
    if(!(obj instanceof Snack)) return false;
    Snack s = ((Snack) obj);
    return s.name.equalsIgnoreCase(this.name) &&
           s.id == this.id;
  }

  @Override
  public String toString() {
    return "Snack{" +
              "name = " + this.name + "," +
              "optional = " + this.optional +
            "};";
  }
}