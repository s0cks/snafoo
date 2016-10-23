package io.github.s0cks.snafoo.service;

import io.github.s0cks.snafoo.service.entity.Food;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component("foodService")
public class FoodServiceImpl
implements FoodService{
  private final FoodRepository foods;

  public FoodServiceImpl(FoodRepository repo){
    this.foods = repo;
  }

  @Override
  public Food getFood(String name) {
    Food f = this.foods.findByName(name);
    if(f == null){
      f = new Food(name);
      this.foods.save(f);
    }
    return f;
  }
}