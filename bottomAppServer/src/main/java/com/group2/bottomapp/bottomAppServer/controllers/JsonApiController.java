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

@Controller
@RequestMapping(value = "/json")
public class JsonApiController {

	ApplicationContext context = 
			new ClassPathXmlApplicationContext("Spring-Module.xml");

	IngredientDAO ingredientDAO = (IngredientDAO) context.getBean("ingredientDAO");
	DrinkDAO drinkDAO = (DrinkDAO) context.getBean("drinkDAO");
	CategoryDAO categoryDAO = (CategoryDAO) context.getBean("categoryDAO");
	UserDAO userDAO = (UserDAO) context.getBean("userDAO");
	
	
	
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
        	
        	
        	System.out.println(" ");
        	System.out.println("Drink: "  + d.getName());
        	
        	for(Ingredient i : d.getIngredients()){
        		System.out.println(i.getName() + " " + i.getMeasurement());
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
			drink.setRatingUp(drink.getRatingDown() + 1);
			
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
	
	
	
	@RequestMapping(value = "/users/login", method= RequestMethod.POST, produces = "application/json; charset=utf-8", headers = { "Accept=application/json", "Authorization=apikey='1c9fk3u35ldcefgw'" })
	ResponseEntity <String> loginUser(@RequestBody User user){
	
//curl -i -H "Accept: application/jso='1c9fk3u35ldcefgw'" -H "Content-type: application/json" -X POST -d '{"email":"svenne87@gmail.com","password":"svempa"}' http://localhost:8080/bottomAppServer/json/users/login
		
		// get salt for that Email
		User tempUser = new User();
		tempUser = userDAO.findByEmail(user.getEmail());
		
		if(tempUser != null){
			// set salt
			user.setSalt(tempUser.getSalt());
						
			if(userDAO.checkLogin(user) != false){
				// if this is correct the tempUser is the same as the user sent in
				return new ResponseEntity <String>(tempUser.getUsername() + " Logged in successfully!", HttpStatus.OK);	
			} else {
				return new ResponseEntity <String>("Login failed!", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity <String>("Login failed!", HttpStatus.BAD_REQUEST);
		}
	}


}
