package io.github.s0cks.snafoo.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class PurchasedSnack
extends Snack{
  @JsonCreator
  private PurchasedSnack(@JsonProperty("Id") int id,
                         @JsonProperty("Name") String name,
                         @JsonProperty("Optional") boolean optional,
                         @JsonProperty("PurchaseLocations") String locations,
                         @JsonProperty("PurchaseCount") int count,
                         @JsonProperty("LastPurchaseDate") String date){
    super(id, name, locations, date, count, 0, optional);
  }
}