package io.github.s0cks.snafoo.model.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Suggestion{
  public final String name;
  public final String location;

  @JsonCreator
  private Suggestion(@JsonProperty("Name") String name,
                     @JsonProperty("Location") String location){
    this.name = name;
    this.location = location;
  }

  @Override
  public String toString() {
    return "{" +
              "\"Name\": \"" + this.name + "\"," +
              "\"Location\": \"" + this.location + "\"" +
            "}";
  }
}