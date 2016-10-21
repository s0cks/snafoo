package io.github.s0cks.snafoo.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class SnackData{
  public final String id;
  public final String name;
  public final String lastPurchaseDate;
  public final String purchaseLocations;
  public final boolean optional;
  public final int purchaseCount;

  @JsonCreator
  private SnackData(
                   @JsonProperty("Id") String id,
                   @JsonProperty("Name") String name,
                   @JsonProperty("Optional") String lastPurchaseDate,
                   @JsonProperty("PurchaseLocations") String purchaseLocations,
                   @JsonProperty("PurchaseCount") boolean optional,
                   @JsonProperty("LastPurchaseDate") int purchaseCount) {
    this.id = id;
    this.name = name;
    this.lastPurchaseDate = lastPurchaseDate;
    this.purchaseLocations = purchaseLocations;
    this.optional = optional;
    this.purchaseCount = purchaseCount;
  }

  @Override
  public String toString() {
    return this.name;
  }
}