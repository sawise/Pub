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
import org.springframework.web.servlet.ModelAndView;

import com.group2.bottomapp.bottomAppServer.dao.CategoryDAO;
import com.group2.bottomapp.bottomAppServer.dao.IngredientDAO;
import com.group2.bottomapp.bottomAppServer.model.Category;
import com.group2.bottomapp.bottomAppServer.model.Ingredient;

@Controller
public class IngredientController {

	ApplicationContext context = 
			new ClassPathXmlApplicationContext("Spring-Module.xml");

	IngredientDAO ingredientDAO = (IngredientDAO) context.getBean("ingredientDAO");
	CategoryDAO categoryDAO = (CategoryDAO) context.getBean("categoryDAO");

	
	// list all ingredients
	@RequestMapping(value = "/ingredients", method = RequestMethod.GET)
	public ModelAndView getAllIngredients(){
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		ingredients = ingredientDAO.getAllIngredients();
		
		return new ModelAndView("ingredients", "ingredients", ingredients);
	}
	
	// view single ingredient
	@RequestMapping(value="/edit_ingredient/{id}", method = RequestMethod.GET)
	public ModelAndView getIngredient(@PathVariable int id){
		Ingredient ingredient = null;
		List<Category> categories = null;
		
		categories = categoryDAO.getAllCategories();
		ingredient = ingredientDAO.findByIngredientId(id);

		ModelAndView modelView = new ModelAndView("edit_ingredient", "ingredient", ingredient);
		modelView.addObject("categories", categories);
		
		return modelView;
	}
	
	// edit single ingredient
	@RequestMapping(value="/edit_ingredient", method = RequestMethod.PUT)  
	@Transactional
	public String editIngredient(@RequestParam(value = "id", required = true) int id, 
			@ModelAttribute("ingredient") @Valid Ingredient ingredient, BindingResult result){
		
		Category category = null;
		category = categoryDAO.findByCategoryId(ingredient.getCategory().getId());
		
		if(category != null){
			ingredient.setCategory(category);
		}
		
		if(result.hasErrors() != true){
			if(ingredientDAO.update(ingredient) != false){
				return "redirect:/ingredients";
			} else {
				return "edit_ingredient";
			}
		} else {
			return "edit_ingredient";
		}

	}
	
	
	// add new Ingredient
	@RequestMapping(value="/add_ingredient", method = RequestMethod.POST)
	@Transactional
	public String addIngredient(@ModelAttribute("ingredient") @Valid Ingredient ingredient, BindingResult result, ModelMap model){
		
		Category category = null;
		category = categoryDAO.findByCategoryId(ingredient.getCategory().getId());
		
		if(category != null){
			ingredient.setCategory(category);
		}
		
		if(result.hasErrors() != true){
			if(ingredientDAO.insert(ingredient) != false){
				return "redirect:/ingredients";
			} else {
				return "add_ingredinet_form";
			}
		} else {
			return "add_ingredient_form";
		}
	}       
		
	
	// serve new Ingredient form
	@RequestMapping(value="/add_ingredient", method = RequestMethod.GET)
	public String displayIngredientForm(ModelMap model){
		
		List<Category> categories = null;		
		categories = categoryDAO.getAllCategories();

		model.addAttribute("ingredient", new Ingredient());
		model.addAttribute("categories", categories);

		return "add_ingredient_form";
	}
	
	
	// remove Ingredient
	@RequestMapping(value = "/ingredient/{id}", method = RequestMethod.DELETE)
	@Transactional
	public String removeIngredient(@PathVariable int id){
		
		Ingredient ingredient = ingredientDAO.findByIngredientId(id);
		
		if(ingredient != null){
			
			// check if any drink uses the ingredient
			if(ingredientDAO.checkIfUsed(ingredient) != true){
				
				if(ingredientDAO.delete(ingredient) != false){
					return "redirect:/ingredients";
				} else {
					return "404";  
				}	
			} else {
				return "404";
			}
							
		} else {
			return "404";
		}
	}       
	 
}


