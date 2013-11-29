package com.group2.bottomapp.bottomAppServer.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.group2.bottomapp.bottomAppServer.dao.DrinkDAO;
import com.group2.bottomapp.bottomAppServer.dao.IngredientDAO;
import com.group2.bottomapp.bottomAppServer.model.Drink;
import com.group2.bottomapp.bottomAppServer.model.Ingredient;

@Controller
public class DrinkController {

	ApplicationContext context = 
			new ClassPathXmlApplicationContext("Spring-Module.xml");

	IngredientDAO ingredientDAO = (IngredientDAO) context.getBean("ingredientDAO");
    DrinkDAO drinkDAO = (DrinkDAO) context.getBean("drinkDAO");
	
	// list all drinks
	@RequestMapping(value = "/drinks", method = RequestMethod.GET)
	public ModelAndView getAllDrinks(){
		List<Drink> drinks = new ArrayList<Drink>();
		
		drinks = drinkDAO.getAllDrinks();
		
		return new ModelAndView("drinks", "drinks", drinks);
	}
    
	
	// view single drink
	@RequestMapping(value="/edit_drink/{id}", method = RequestMethod.GET)
	public ModelAndView getDrink(@PathVariable int id){
		Drink drink = null;

		drink = drinkDAO.findByDrinkId(id);

		ModelAndView modelView = new ModelAndView("edit_drink", "drink", drink);

		return modelView;
	}
	
	// edit single drink (name, description, ratingUp, ratingDown)
	@RequestMapping(value="/edit_drink", method = RequestMethod.PUT)  
	@Transactional
	public String editDrink(@RequestParam(value = "id", required = true) int id, 
			@ModelAttribute("drink") @Valid Drink drink, BindingResult result){

		if(result.hasErrors() != true){
			if(drinkDAO.update(drink) != false){
				return "redirect:/drinks";
			} else {
				return "edit_drink";
			}
		} else {
			return "edit_drink";
		}
	}
	
	// helper method to get all ingredients for the new drink
	private List<Ingredient> getIngredientsForNewDrink(Drink drink){
		List<Ingredient> drinkIngredients = new ArrayList<Ingredient>();
		Ingredient drinkIngredient = null;
		
		// loop all ingredients (even empty once)
		for(Ingredient ingredient : drink.getIngredients()){
			
			if(ingredient.getId() != 0){
				// get ingredients by id
				drinkIngredient = ingredientDAO.findByIngredientId(ingredient.getId());
				
				if(drinkIngredient != null){
					// add ingredient to ingredients list
					drinkIngredients.add(ingredient);
				}
			} 
		}
		return drinkIngredients;
	}
	
	
	// add new Drink, handle form JSON submit
	@RequestMapping(value="/add_drink", method = RequestMethod.POST)
	@ResponseBody
	public String addDrink(Drink drink){
		
		Boolean error = false;
		String drinkName = drink.getName();
		
		// check if drink with that name already exists
		if(drinkDAO.findByDrinkName(drinkName) != null){
			return "Drink with that name already exists!";
		} else {
			List<Ingredient> drinkIngredients = getIngredientsForNewDrink(drink);
			
			// set the ingredients for the drink
			drink.setIngredients(drinkIngredients);
		}
		
		
		// add drink to database
		if(drinkDAO.insert(drink) != false){
			// get id for the drink we just added
			Drink tempDrink = drinkDAO.findByDrinkName(drinkName);
			int drinkId = tempDrink.getId();
			
			// set the id to the drink we are adding
			drink.setId(drinkId);
			
			// add all ingredients with measurement for the drink
			for(Ingredient ingredient : drink.getIngredients()){
				if(drinkDAO.insertDrinkIngredient(drinkId, ingredient) == false){
					error = true;
				}
			}
			
		} else {
			return "Something went wrong! Drink was not added.";
		}
		
		// if everything was added correctly
		if(error != true){
			return drink.getName() +  " was added!";
		} else {
			return "There was an error adding ingredients for this drink!";
		}
	}       
		
	
	// serve new Drink form
	@RequestMapping(value="/add_drink", method = RequestMethod.GET)
	public String displayDrinkForm(ModelMap model){
		
		List<Ingredient> ingredients;
		ingredients = ingredientDAO.getAllIngredients();

		model.addAttribute("drink", new Drink());
		model.addAttribute("ingredients", ingredients);

		return "add_drink_form";
	}
	
	
	// remove Drink
	@RequestMapping(value = "/drink/{id}", method = RequestMethod.DELETE)
	@Transactional
	public String removeDrink(@PathVariable int id){
			
		Drink drink = drinkDAO.findByDrinkId(id);

		if(drink != null){	
			if(drinkDAO.delete(drink) != false){
				return "redirect:/drinks";
			} else {
				return "404";  
			}	
		} else {
			return "404";
		}
	}       
	
	
}
