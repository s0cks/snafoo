package io.github.s0cks.snafoo.model.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Snack {
  public final int id;
  public final int purchaseCount;
  public final String name;
  public final String purchaseLocations;
  public final String lastPurchaseDate;
  public final boolean optional;

  private Snack(Snack s){
    this.id = s.id;
    this.purchaseCount = s.purchaseCount;
    this.name = s.name;
    this.purchaseLocations = s.purchaseLocations;
    this.lastPurchaseDate = s.lastPurchaseDate;
    this.optional = s.optional;
  }

  @JsonCreator
  private Snack(@JsonProperty("Id") int id,
                @JsonProperty("Name") String name,
                @JsonProperty("Optional") boolean optional,
                @JsonProperty("PurchaseLocations") String locations,
                @JsonProperty("PurchaseCount") int count,
                @JsonProperty("LastPurchaseDate") String date){
    this.id = id;
    this.name = name;
    this.purchaseCount = count;
    this.optional = optional;
    this.purchaseLocations = locations;
    this.lastPurchaseDate = date;
  }

  public static final class VotedSnack
  extends Snack{
    public final boolean voted;
    public final int votes;

    public VotedSnack(Snack owner, int votes, boolean voted){
      super(owner);
      this.voted = voted;
      this.votes = votes;
    }
  }
}