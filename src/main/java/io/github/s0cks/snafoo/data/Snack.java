package io.github.s0cks.snafoo.data;

public final class Snack{
  public final SnackData meta;
  public final int votes;
  public final boolean voted;

  public Snack(SnackData meta, int votes, boolean voted) {
    this.meta = meta;
    this.votes = votes;
    this.voted = voted;
  }
}