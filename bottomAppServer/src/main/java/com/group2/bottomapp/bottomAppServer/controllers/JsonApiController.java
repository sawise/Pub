package com.group2.bottomapp.bottomAppServer.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody; 

import com.group2.bottomapp.bottomAppServer.dao.CategoryDAO;
import com.group2.bottomapp.bottomAppServer.dao.DrinkDAO;
import com.group2.bottomapp.bottomAppServer.dao.IngredientDAO;
import com.group2.bottomapp.bottomAppServer.dao.UserDAO;
import com.group2.bottomapp.bottomAppServer.model.Category;
import com.group2.bottomapp.bottomAppServer.model.Drink;
import com.group2.bottomapp.bottomAppServer.model.Ingredient;
import com.group2.bottomapp.bottomAppServer.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/json")
public class JsonApiController {

	ApplicationContext context = 
			new ClassPathXmlApplicationContext("Spring-Module.xml");

	IngredientDAO ingredientDAO = (IngredientDAO) context.getBean("ingredientDAO");
	DrinkDAO drinkDAO = (DrinkDAO) context.getBean("drinkDAO");
	CategoryDAO categoryDAO = (CategoryDAO) context.getBean("categoryDAO");
	UserDAO userDAO = (UserDAO) context.getBean("userDAO");
	
	
	// compares two lists of ingredients and return the number of matches found
	private boolean checkMatchingIngredients(List<Ingredient> userIngredients, List<Ingredient> drinkIngredients){
		
		int drinkIngredientsSize = drinkIngredients.size();
		int matches = 0;
		
		for(Ingredient userIngredient : userIngredients){
			for(Ingredient drinkIngredient : drinkIngredients){
				
				// every time the ingredients are the same
				if(userIngredient.getId() == drinkIngredient.getId()){
					// increase the nr of matches
					matches++;
				}
			}
		}
				
		// if then number of matches are the same as the number of ingredients in the drink, then we can make that drink
		if(matches == drinkIngredientsSize){
			return true;
		} else {
			return false;
		}
	}
	
	
	
	@RequestMapping("/categories/all")
	public @ResponseBody List<Category> generateJsonCategoriesResponse(){
		List<Category> categories = new ArrayList<Category>();
		
		categories = categoryDAO.getAllCategories();
		return categories;
	}

	
	@RequestMapping(value="/category/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
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
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/ingredients/all", produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Ingredient> generateJsonIngredientResponse(){
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		ingredients = ingredientDAO.getAllIngredients();
		
		return ingredients;
	}
	
	@RequestMapping(value = "/ingredients/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<Ingredient> generateJsonSingleIngredientResponse(@PathVariable int id){
		Ingredient ingredient = ingredientDAO.findByIngredientId(id);
		
		if(ingredient != null){
			return new ResponseEntity<Ingredient>(ingredient, HttpStatus.OK);
		} else {
			return new ResponseEntity<Ingredient>(ingredient, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value= "/drinks/all", produces = { MediaType.APPLICATION_JSON_VALUE })
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

        }
        
		return drinks;
	}
	
	@RequestMapping(value = "/drinks/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	ResponseEntity<Drink> generateJsonSingleDrinkResponse(@PathVariable int id){
		Drink drink = drinkDAO.findByDrinkId(id);
		
		//Is this better with the new db-method or is it better to do as we do with the getAllDrinks() ???
		/*
		List<Ingredient> ingredients = ingredientDAO.getIngredientsForDrink(drink); 
		drink.setIngredients(ingredients);
		*/
		if(drink != null){
			return new ResponseEntity<Drink>(drink, HttpStatus.OK);
		} else {
			return new ResponseEntity<Drink>(drink, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/drinks/{id}/ratingup", produces = "application/json; charset=utf-8", headers = { "Accept=application/json", "Authorization=apikey='1c9fk3u35ldcefgw'" })
	ResponseEntity<String> raiseRatingUp(@PathVariable int id){		

		Drink drink = drinkDAO.findByDrinkId(id);

		try {
			drink.setRatingUp(drink.getRatingUp() + 1);
			
			if(drinkDAO.update(drink) != false){
				return new ResponseEntity <String>("Drink rating updated!", HttpStatus.OK);
			} else {
				return new ResponseEntity <String>("Internal error, drink could not be rated!", HttpStatus.INTERNAL_SERVER_ERROR);	
			}

		} catch (NullPointerException ex){
			return new ResponseEntity <String>("Drink not found!", HttpStatus.BAD_REQUEST);
		}	
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/drinks/{id}/ratingdown", produces = "application/json; charset=utf-8", headers = { "Accept=application/json", "Authorization=apikey='1c9fk3u35ldcefgw'" })
	ResponseEntity<String> raiseRatingDown(@PathVariable int id){		

		Drink drink = drinkDAO.findByDrinkId(id);

		try {
			drink.setRatingDown(drink.getRatingDown() + 1);
			
			if(drinkDAO.update(drink) != false){
				return new ResponseEntity <String>("Drink rating updated!", HttpStatus.OK);
			} else {
				return new ResponseEntity <String>("Internal error, drink could not be rated!", HttpStatus.INTERNAL_SERVER_ERROR);	
			}

		} catch (NullPointerException ex){
			return new ResponseEntity <String>("Drink not found!", HttpStatus.BAD_REQUEST);
		}	
	}
	
	
	@RequestMapping(value = "/users/new", method = RequestMethod.POST, produces = "application/json; charset=utf-8", headers = { "Accept=application/json", "Authorization=apikey='1c9fk3u35ldcefgw'" })
	ResponseEntity <String> addNewUserJson(@RequestBody User user){
		// Should be done over SSL.

		// check if Email is in use
		if(userDAO.findByEmail(user.getEmail()) == null){
			if(userDAO.insert(user) != false){
				return new ResponseEntity <String>("User added successfully", HttpStatus.OK);	
			} else {
				return new ResponseEntity <String>("Internal error, The User could not be added!", HttpStatus.INTERNAL_SERVER_ERROR);	
			}
		} else {
			return new ResponseEntity <String>("Email is in use!", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	// login with password
	@RequestMapping(value = "/users/login", method= RequestMethod.POST, produces = "application/json; charset=utf-8", headers = { "Accept=application/json", "Authorization=apikey='1c9fk3u35ldcefgw'" })
	ResponseEntity <String> loginUser(@RequestBody User user){
		// Should be done over SSL.	
		
		// generate identifier
    	String identifier = UUID.randomUUID().toString();
    	identifier = identifier.replace("-", ""); 
		
		// get salt for that Email
		User tempUser = new User();
		tempUser = userDAO.findByEmail(user.getEmail());
		
		if(tempUser != null){
			// set salt
			user.setSalt(tempUser.getSalt());
						
			// if this is correct the tempUser is the same as the user sent in
			if(userDAO.checkLogin(user) != false){
				
				// set new identifier
				tempUser.setIdentifier(identifier);
				
				// update stored user with new identifier
				if(userDAO.update(tempUser)){
					return new ResponseEntity <String>("Logged in successfully!" + "|userId:" + tempUser.getId() + "|identifier:" + tempUser.getIdentifier() + "|username:" + tempUser.getUsername(), HttpStatus.OK);	
				} else {
					return new ResponseEntity <String>("Login failed!", HttpStatus.BAD_REQUEST);	
				}
				
			} else {
				return new ResponseEntity <String>("Login failed!", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity <String>("Login failed!", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	// login with identifier
	@RequestMapping(value = "/users/identifierlogin", method= RequestMethod.POST, produces = "application/json; charset=utf-8", headers = { "Accept=application/json", "Authorization=apikey='1c9fk3u35ldcefgw'" })
	ResponseEntity <String> loginUserWithIdentifier(@RequestBody User user){
		// Should be done over SSL.	
				
		User userLogin = userDAO.findByEmail(user.getEmail());
		
		try{
			// if the sent user identifier is the same as the stored identifier for that email
			if(userLogin != null && userLogin.getIdentifier().equals(user.getIdentifier())){
				// if a user with that identifier and email is found
				
				// generate identifier and update that user
		    	String identifier = UUID.randomUUID().toString();
		    	identifier = identifier.replace("-", ""); 
				userLogin.setIdentifier(identifier);
				
				if(userDAO.update(userLogin) != false){
					return new ResponseEntity <String>("Logged in successfully!" + "|userId:" + userLogin.getId() + "|identifier:" + userLogin.getIdentifier() + "|username:" + userLogin.getUsername(), HttpStatus.OK);	
				} else {
					return new ResponseEntity <String>("Login failed!", HttpStatus.BAD_REQUEST);
				}
				
			} else {
				return new ResponseEntity <String>("Login failed!", HttpStatus.BAD_REQUEST);
			}
		} catch (NullPointerException ex){
			return new ResponseEntity <String>("Login failed!", HttpStatus.BAD_REQUEST);
		}

	}
	
	
	
	@RequestMapping(value = "/users/logout", method= RequestMethod.POST, produces = "application/json; charset=utf-8", headers = { "Accept=application/json", "Authorization=apikey='1c9fk3u35ldcefgw'" })
	ResponseEntity <String> logoutUser(@RequestBody User user){
		// Should be done over SSL.	
		
		// find user
		User userLogout = null;
		userLogout = userDAO.findByEmail(user.getEmail());
						
		// if the sent user identifier is the same as the stored identifier for that email
		if(userLogout != null && userLogout.getIdentifier().equals(user.getIdentifier())){
			// if a user with that identifier and email is found

			// set identifier to null
			userLogout.setIdentifier(null);
			
			// update
			if(userDAO.update(userLogout) != false){
				return new ResponseEntity <String>(userLogout.getUsername() + " Logged out successfully!", HttpStatus.OK);
			} else {
				return new ResponseEntity <String>("Logout failed!", HttpStatus.BAD_REQUEST);	
			}
				
		} else {
			return new ResponseEntity <String>("Logout failed!", HttpStatus.BAD_REQUEST);
		}

	}

	
	
	// get all drinks that a user have ingredients to make
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Drink> getAvailableDrinksForUser(@PathVariable int id){
		
		// List of drinks we can make
		List<Drink> matchingDrinks = new ArrayList<Drink>();
		
		// get ingredients for user 
		List<Ingredient> userIngredients = ingredientDAO.getIngredientsForUser(id);
		// get all drinks
		List<Drink> drinks = drinkDAO.getAllDrinks();
		
		// add ingredients for each drinks
		List<Ingredient> drinkIngredients = new ArrayList<Ingredient>();
		
        for(Drink d : drinks){
        	
        	drinkIngredients = ingredientDAO.getIngredientsForDrink(d);
        	
        	if(drinkIngredients != null){
        		
        		// if we can make this drink
        		if(checkMatchingIngredients(userIngredients, drinkIngredients) != false){
        			// add ingredients to that drink and add the drink to matchingDrinks
        			d.setIngredients(drinkIngredients);
        			matchingDrinks.add(d);
        		}
        	}
        }
				
		return matchingDrinks;
	}
	
	// return all ingredients for a user
	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}/ingredients", produces = "application/json; charset=utf-8")
	public @ResponseBody List<Ingredient> getUsersIngredients(@PathVariable int id){
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		ingredients = ingredientDAO.getIngredientsForUser(id);
		
		return ingredients;
				
	}
	
	// add a ingredient for a user
	@RequestMapping(method = RequestMethod.POST, value = "/users/add/ingredient/{id}", produces = "application/json; charset=utf-8", headers = { "Accept=application/json", "Authorization=apikey='1c9fk3u35ldcefgw'" })
	ResponseEntity<String> addIngredientForUser(@PathVariable int id, @RequestBody User user){		

		Ingredient ingredient;

		try {
			ingredient = ingredientDAO.findByIngredientId(id);
			
			// find user
			User userLogin = userDAO.findUserById(user.getId());
				
			// check if the user already have that ingredient
			for (Ingredient ingred : ingredientDAO.getIngredientsForUser(userLogin.getId())){
				if(ingred.getId() == ingredient.getId()){
					return new ResponseEntity <String>("Ingredient not added! User already have that ingredient", HttpStatus.BAD_REQUEST);
				}
			}
					
			// check if it is a valid user
			if(userLogin != null && userLogin.getIdentifier().equals(user.getIdentifier())){
					
				// add the ingredient for the user
				if(userDAO.insertIngredientForUser(user.getId(), ingredient.getId()) != false){
					return new ResponseEntity <String>("Ingredient added!", HttpStatus.OK);
				} else {
					return new ResponseEntity <String>("Internal error, ingredient could not be added!", HttpStatus.INTERNAL_SERVER_ERROR);	
				}
			} else {
				return new ResponseEntity <String>("Internal error, ingredient could not be added!", HttpStatus.BAD_REQUEST);	
			}
			
		} catch (NullPointerException ex){
			return new ResponseEntity <String>("Ingredient not found!", HttpStatus.BAD_REQUEST);
		}	
	}
	
	
	
	// remove a ingredient for a user
	@RequestMapping(method = RequestMethod.POST, value = "/users/remove/ingredient/{id}", produces = "application/json; charset=utf-8", headers = { "Accept=application/json", "Authorization=apikey='1c9fk3u35ldcefgw'" })
	ResponseEntity<String> removeIngredientForUser(@PathVariable int id, @RequestBody User user){		

		Ingredient ingredient;

		try {
			ingredient = ingredientDAO.findByIngredientId(id);
			
			// find user
			User userLogin = userDAO.findUserById(user.getId());
			
			// check if it is a valid user
			if(userLogin != null && userLogin.getIdentifier().equals(user.getIdentifier())){
				// delete the ingredient for the user
				if(userDAO.deleteIngredientForUser(user.getId(), ingredient.getId()) != false){
					return new ResponseEntity <String>("Ingredient removed!", HttpStatus.OK);
				} else {
					return new ResponseEntity <String>("Internal error, ingredient could not be added!", HttpStatus.INTERNAL_SERVER_ERROR);	
				}
			} else {
				return new ResponseEntity <String>("Internal error, ingredient could not be added!", HttpStatus.BAD_REQUEST);	
			}
			
		} catch (NullPointerException ex){
			return new ResponseEntity <String>("Ingredient not found!", HttpStatus.BAD_REQUEST);
		}	
	}
	

	// return all favorite drinks for a user
	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}/favorite/drink", produces = "application/json; charset=utf-8")
	public @ResponseBody List<Drink> getUserFavoriteDrinks(@PathVariable int id){
		
		List<Drink> drinks = new ArrayList<Drink>();
		List<Ingredient> ingredientsList = new ArrayList<Ingredient>();
		
		drinks = drinkDAO.getUserFavoriteDrinks(id);
		
		
        for(Drink d : drinks){
        	
        	ingredientsList = ingredientDAO.getIngredientsForDrink(d);
        	
        	
        	if(ingredientsList != null){
        		d.setIngredients(ingredientsList);
        	}

        }
		
		
		return drinks;
	}
	
	
	// add a favorite drink for a user
	@RequestMapping(method = RequestMethod.POST, value = "/users/add/favorite/drink/{id}", produces = "application/json; charset=utf-8", headers = { "Accept=application/json", "Authorization=apikey='1c9fk3u35ldcefgw'" })
	ResponseEntity<String> addFavoriteDrinkForUser(@PathVariable int id, @RequestBody User user){		

		Drink drink;

		try {
			drink = drinkDAO.findByDrinkId(id);
			
			// find user
			User userLogin = userDAO.findUserById(user.getId());
			
			// check if the user already have that drink as a favorite
			for (Drink d : drinkDAO.getUserFavoriteDrinks(userLogin.getId())){
				if(d.getId() == drink.getId()){
					return new ResponseEntity <String>("Drink not added to favorites! User already have that drink as a favorite", HttpStatus.BAD_REQUEST);
				}
			}
			
			
			// check if it is a valid user
			if(userLogin != null && userLogin.getIdentifier().equals(user.getIdentifier())){
				// add the ingredient for the user
				if(userDAO.insertFavoriteDrinkForUser(user.getId(), drink.getId()) != false){
					return new ResponseEntity <String>("Favorite Drink added!", HttpStatus.OK);
				} else {
					return new ResponseEntity <String>("Internal error, favorite drink could not be added!", HttpStatus.INTERNAL_SERVER_ERROR);	
				}
			} else {
				return new ResponseEntity <String>("Internal error, favorite drink could not be added!", HttpStatus.BAD_REQUEST);	
			}
			
		} catch (NullPointerException ex){
			return new ResponseEntity <String>("Drink not found!", HttpStatus.BAD_REQUEST);
		}	
	}
	
	
	// remove a drink for a user
	@RequestMapping(method = RequestMethod.POST, value = "/users/remove/favorite/drink/{id}", produces = "application/json; charset=utf-8", headers = { "Accept=application/json", "Authorization=apikey='1c9fk3u35ldcefgw'" })
	ResponseEntity<String> removeFavoriteDrinkForUser(@PathVariable int id, @RequestBody User user){		

		Drink drink;

		try {
			drink = drinkDAO.findByDrinkId(id);
			
			// find user
			User userLogin = userDAO.findUserById(user.getId());
			
			// check if it is a valid user
			if(userLogin != null && userLogin.getIdentifier().equals(user.getIdentifier())){
				// delete the ingredient for the user
				if(userDAO.deleteFavoriteDrinkForUser(user.getId(), drink.getId()) != false){
					return new ResponseEntity <String>("Favorite Drink removed!", HttpStatus.OK);
				} else {
					return new ResponseEntity <String>("Internal error, favorite drink could not be added!", HttpStatus.INTERNAL_SERVER_ERROR);	
				}
			} else {
				return new ResponseEntity <String>("Internal error, favorite drink could not be added!", HttpStatus.BAD_REQUEST);	
			}
			
		} catch (NullPointerException ex){
			return new ResponseEntity <String>("Drink not found!", HttpStatus.BAD_REQUEST);
		}	
	}
	
	
	


}
