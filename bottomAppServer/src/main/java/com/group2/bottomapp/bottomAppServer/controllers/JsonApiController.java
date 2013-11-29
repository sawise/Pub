package com.group2.bottomapp.bottomAppServer.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody; 

import com.group2.bottomapp.bottomAppServer.dao.CategoryDAO;
import com.group2.bottomapp.bottomAppServer.dao.DrinkDAO;
import com.group2.bottomapp.bottomAppServer.dao.IngredientDAO;
import com.group2.bottomapp.bottomAppServer.model.Category;
import com.group2.bottomapp.bottomAppServer.model.Drink;
import com.group2.bottomapp.bottomAppServer.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/json")
public class JsonApiController {

	ApplicationContext context = 
			new ClassPathXmlApplicationContext("Spring-Module.xml");

	IngredientDAO ingredientDAO = (IngredientDAO) context.getBean("ingredientDAO");
	DrinkDAO drinkDAO = (DrinkDAO) context.getBean("drinkDAO");
	CategoryDAO categoryDAO = (CategoryDAO) context.getBean("categoryDAO");
	
	@RequestMapping("/categories/all")
	public @ResponseBody List<Category> generateJsonCategoriesResponse(){
		List<Category> categories = new ArrayList<Category>();
		
		categories = categoryDAO.getAllCategories();
		return categories;
	}
	
	
	@RequestMapping(value="/category/{id}", method = RequestMethod.GET)
	public @ResponseBody List<Ingredient> generateJsonIngredientsForCategoryResponse(@PathVariable int id){
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		List<Ingredient> ingredientsForCategory = new ArrayList<Ingredient>();
		
		ingredients = ingredientDAO.getAllIngredients();
		
		for(Ingredient ingredient : ingredients){
			// only listing ingredients for this category
			if(ingredient.getCategory().getId() == id){
				ingredientsForCategory.add(ingredient);
			}
		}
		
		return ingredientsForCategory;
	}
	
	
	@RequestMapping("/ingredients/all")
	public @ResponseBody List<Ingredient> generateJsonIngredientResponse(){
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		ingredients = ingredientDAO.getAllIngredients();
		
		return ingredients;
	}
	
	@RequestMapping(value = "/ingredients/{id}", method = RequestMethod.GET)
	public @ResponseBody Ingredient generateJsonSingleIngredientResponse(@PathVariable int id){
		Ingredient ingredient = ingredientDAO.findByIngredientId(id);
		
		return ingredient;
	}
	
	@RequestMapping("/drinks/all")
    public @ResponseBody List<Drink> generateJsonDrinkResponse(){
		List<Drink> drinks = new ArrayList<Drink>();
        List<Ingredient> ingredientsList = new ArrayList<Ingredient>();
        
		drinks = drinkDAO.getAllDrinks();

        // this uses a custom db-method that is meant only for displaying a list of drinks
		// not the full drink object, but this is displaying full object (right now)
		
        for(Drink d : drinks){
        	
        	ingredientsList = ingredientDAO.getIngredientsForDrink(d);
        	
        	
        	if(ingredientsList != null){
        		d.setIngredients(ingredientsList);
        	}
        	
        	
        	System.out.println(" ");
        	System.out.println("Drink: "  + d.getName());
        	
        	for(Ingredient i : d.getIngredients()){
        		System.out.println(i.getName() + " " + i.getMeasurement());
        	}
        	
        }
        
		return drinks;
	}
	

	@RequestMapping(value = "/drinks/{id}", method = RequestMethod.GET)
	public @ResponseBody Drink generateJsonSingleDrinkResponse(@PathVariable int id){
		Drink drink = drinkDAO.findByDrinkId(id);
		
		//Is this better with the new db-method or is it better to do as we do with the getAllDrinks() ???
		/*
		List<Ingredient> ingredients = ingredientDAO.getIngredientsForDrink(drink); 
		drink.setIngredients(ingredients);
		*/
		return drink;
	}

}
