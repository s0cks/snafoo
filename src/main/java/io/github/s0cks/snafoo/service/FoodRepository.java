package io.github.s0cks.snafoo.service;

import io.github.s0cks.snafoo.service.entity.Food;
import org.springframework.data.repository.Repository;

interface FoodRepository
extends Repository<Food, Long> {
  public Food findByName(String name);
  public Food save(Food f);
}