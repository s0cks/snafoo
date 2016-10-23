package io.github.s0cks.snafoo.data;

public abstract class Snack{
  public final String id;
  public final String name;
  public final String purchaseLocations;
  public final boolean optional;

  protected Snack(String id, String name, String purchaseLocations, boolean optional) {
    this.id = id;
    this.name = name;
    this.purchaseLocations = purchaseLocations;
    this.optional = optional;
  }
}