package io.github.s0cks.snafoo.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class PurchasedSnack
extends Snack{
  public final String lastPurchaseDate;
  public final int purchaseCount;

  @JsonCreator
  private PurchasedSnack(
      @JsonProperty("Id") String id,
      @JsonProperty("Name") String name,
      @JsonProperty("Optional") boolean optional,
      @JsonProperty("PurchaseLocations") String purchaseLocations,
      @JsonProperty("PurchaseCount") int purchaseCount,
      @JsonProperty("LastPurchaseDate") String lastPurchaseDate){
    super(id, name, purchaseLocations, optional);
    this.lastPurchaseDate = lastPurchaseDate;
    this.purchaseCount = purchaseCount;
  }
}