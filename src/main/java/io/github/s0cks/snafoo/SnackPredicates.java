package io.github.s0cks.snafoo;

import io.github.s0cks.snafoo.model.rest.Snack;

import java.util.function.Predicate;

public enum SnackPredicates
implements Predicate<Snack>{
  ALWAYS_PURCHASED(){
    @Override
    public boolean test(Snack snack) {
      return !snack.optional;
    }
  },
  OPTIONALLY_PURCHASED(){
    @Override
    public boolean test(Snack snack) {
      return snack.optional;
    }
  }
}