package io.github.s0cks.snafoo.model.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is another Java JSON object representation class.
 * This represents the JSON object that gets POSTed to the Snack API endpoint.
 * For sanity sake this object is first sent to the app from the client and then to the final API endpoint.
 */
public final class Suggestion{
  public final String name;
  public final String location;

  // Another JSON deserialisation constructor.
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