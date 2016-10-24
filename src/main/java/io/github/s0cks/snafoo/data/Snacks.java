package io.github.s0cks.snafoo.data;

import io.github.s0cks.snafoo.SnafooSnackService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class Snacks {
  private static final Set<Snack> snacks = new HashSet<>();

  static {
    snacks.addAll(Arrays.asList(SnafooSnackService.INSTANCE.getSnacks()));
  }

  public static Set<Snack> getBaseSet() {
    return snacks.stream()
                 .filter((s) -> !s.optional)
                 .collect(Collectors.toSet());
  }

  public static Set<Snack> getSuggestions() {
    return snacks.stream()
                 .filter((s) -> s.optional)
                 .collect(Collectors.toSet());
  }

  public static Optional<Snack> get(String name) {
    return snacks.stream()
                 .filter((s) -> s.name.equalsIgnoreCase(name))
                 .findFirst();
  }

  public static void add(Snack s){
    snacks.add(s);
  }
}