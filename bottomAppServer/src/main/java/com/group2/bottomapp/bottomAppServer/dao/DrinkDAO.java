package com.group2.bottomapp.bottomAppServer.dao;

import java.util.List;

import com.group2.bottomapp.bottomAppServer.model.Drink;
import com.group2.bottomapp.bottomAppServer.model.Ingredient;

public interface DrinkDAO {
	
	public boolean insert(Drink drink);
	public boolean insertDrinkIngredient(int drinkId, Ingredient ingredient);
	public boolean update(Drink drink);
	public boolean delete(Drink drink);
	public Drink findByDrinkId(int drinkId);
	public Drink findByDrinkName(String drinkName);
	public List<Drink> getAllDrinks(); 
	public List<Drink> findDrinksByIngredients(List<Ingredient> ingredientsList);
	public List<Drink> findDrinksByIngredient(Ingredient ingredient);
	
}
