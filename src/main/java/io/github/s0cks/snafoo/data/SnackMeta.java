package io.github.s0cks.snafoo.data;

public final class SnackMeta {
  public final String name;
  public final String lastPurchaseDate;
  public final boolean voted;
  public final int votes;

  public SnackMeta(String name, String lastPurchaseDate, boolean voted, int votes) {
    this.name = name;
    this.lastPurchaseDate = lastPurchaseDate;
    this.voted = voted;
    this.votes = votes;
  }
}
