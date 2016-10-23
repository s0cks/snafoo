package io.github.s0cks.snafoo.data;

public final class SuggestedSnack
extends Snack{
  public final int votes;
  public final boolean alreadyVoted;

  protected SuggestedSnack(String id, String name, String purchaseLocations, boolean optional, int votes, boolean alreadyVoted) {
    super(id, name, purchaseLocations, optional);
    this.votes = votes;
    this.alreadyVoted = alreadyVoted;
  }
}