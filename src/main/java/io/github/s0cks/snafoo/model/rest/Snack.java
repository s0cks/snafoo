package io.github.s0cks.snafoo.model.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Comparator;

/**
 * Basic Java representation of the Snack JSON Object.
 */
public class Snack {
  public final int id;
  public final int purchaseCount;
  public final String name;
  public final String purchaseLocations;
  public final String lastPurchaseDate;
  public final boolean optional;

  // Doesn't necessarily need to be private for this Copy constructor (I'm already missing C++'s = overloading for copy constructors :<)
  // However for sake of sanity and promotion of safe coding I made it private and the use of it is contained in a static inner class
  // Also bring friend classes to Java plz
  private Snack(Snack s){
    this.id = s.id;
    this.purchaseCount = s.purchaseCount;
    this.name = s.name;
    this.purchaseLocations = s.purchaseLocations;
    this.lastPurchaseDate = s.lastPurchaseDate;
    this.optional = s.optional;
  }

  // This is also private for the same reason however the usage is reflective and access has been changed to public so no need to worry
  // This just details how to deserialise the object from the JSON object via constructor call since reflection is slow and evil
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

  // This one-off inner class is a hackish way of safely mutating the immutable snack (weird right?)
  // Its an extension of Snack but takes a Snack instance in its constructor to clone its values into a new instance.
  // This just adds a couple fields for the internal rest service of this app.
  public static final class VotedSnack
  extends Snack{
    public final boolean voted; // Wether or not that this user has voted on this object
    public final int votes; // The number of votes that this object has

    public VotedSnack(Snack owner, int votes, boolean voted){
      super(owner);
      this.voted = voted;
      this.votes = votes;
    }
  }

  // These are just some utility comparators that I didn't want to stuff into a whole new class for conservation purposes
  // Excuse the excessive use of Java8/Functional paradigms but I'm quite fond of them. I would have used Scala for this if I chose to not use Spring stuff
  // This comparator sorts Snack objects by name
  public static final Comparator<Snack> SORT_BY_NAME = (t1, t2) -> t1.name.compareToIgnoreCase(t2.name);
  // This comparator sorts VotedSnack objects by votes
  public static final Comparator<VotedSnack> SORT_BY_VOTES = (t1, t2) -> Integer.compare(t1.votes, t2.votes);
  // Their uses are in the controller class
}  