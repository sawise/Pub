package com.group2.bottomapp.bottomAppServer.dao;

import java.util.List;

import com.group2.bottomapp.bottomAppServer.model.Drink;
import com.group2.bottomapp.bottomAppServer.model.Ingredient;

public interface IngredientDAO {

	public boolean insert(Ingredient ingredient);
	public boolean update(Ingredient ingredient);
	public boolean delete(Ingredient ingredient);
	public Ingredient findByIngredientId(int ingredientId);
	public boolean checkIfUsed(Ingredient ingredient);
	public List<Ingredient> getIngredientsForDrink(Drink drink);
	public List<Ingredient> getAllIngredients();
	
}
