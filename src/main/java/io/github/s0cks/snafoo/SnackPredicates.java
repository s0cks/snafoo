package io.github.s0cks.snafoo;

import io.github.s0cks.snafoo.model.rest.Snack;

import java.util.function.Predicate;

/**
 * SnackPredicates
 *
 * Class containing useful {@link Predicate}s for filtering a {@link java.util.stream.Stream} of {@link Snack}s
 *
 * Since {@link Predicate} is an interface, and Java's enums allow inheritance from interfaces,
 * this holds instances of predicates spec'd by the enum fields.
 */
public enum SnackPredicates
implements Predicate<Snack>{
  // Snacks that are always purchased are filtered here
  ALWAYS_PURCHASED(){
    @Override
    public boolean test(Snack snack) {
      return !snack.optional;
    }
  },
  // Snacks that aren't always purchased and are only purchased with sufficient votes are filtered here
  OPTIONALLY_PURCHASED(){
    @Override
    public boolean test(Snack snack) {
      return snack.optional;
    }
  }
}