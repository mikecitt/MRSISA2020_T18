package com.tim18.bolnicar.repository;

import com.tim18.bolnicar.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
}
