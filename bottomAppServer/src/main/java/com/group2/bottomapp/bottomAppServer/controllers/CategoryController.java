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
import com.group2.bottomapp.bottomAppServer.model.Category;

@Controller
public class CategoryController {
	
	ApplicationContext context = 
			new ClassPathXmlApplicationContext("Spring-Module.xml");

	CategoryDAO categoryDAO = (CategoryDAO) context.getBean("categoryDAO");

	
	// list all categories
	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ModelAndView getAllCategories(){
		List<Category> categories = new ArrayList<Category>();
		
		categories = categoryDAO.getAllCategories();
		
		return new ModelAndView("categories", "categories", categories);
	}
	
	// view single category
	@RequestMapping(value="/edit_category/{id}", method = RequestMethod.GET)
	public ModelAndView getCategory(@PathVariable int id){
		Category category = null;
		category = categoryDAO.findByCategoryId(id);
		
		return new ModelAndView("edit_category", "category", category);
		
	}
	
	// edit single category
	@RequestMapping(value="/edit_category", method = RequestMethod.PUT)  
	@Transactional
	public String editCatgeory(@RequestParam(value = "id", required = true) int id, 
			@ModelAttribute("category") @Valid Category category, BindingResult result){
		
		if(result.hasErrors() != true){
			if(categoryDAO.update(category) != false){
				return "redirect:/categories";
			} else {
				return "edit_category";
			}
		} else {
			return "edit_category";
		}

	}
	
	
	// add new Category
	@RequestMapping(value="/add_category", method = RequestMethod.POST)
	@Transactional
	public String addCategory(@ModelAttribute("category") @Valid Category category, BindingResult result, ModelMap model){
		if(result.hasErrors() != true){
			if(categoryDAO.insert(category) != false){
				return "redirect:/categories";
			} else {
				return "add_category_form";
			}
		} else {
			return "add_category_form";
		}
	}
	
	// serve new Category form
	@RequestMapping(value="/add_category", method = RequestMethod.GET)
	public String displayCategoryForm(ModelMap model){
		model.addAttribute("category", new Category());
		return "add_category_form";
	}
	
	
	// remove Category
	@RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE)
	@Transactional
	public String removeCategory(@PathVariable int id){
		
		Category category = categoryDAO.findByCategoryId(id);
		
		if(category != null){
			
			// check if any ingredient uses the category
			if(categoryDAO.checkIfUsed(category) != true){
				
				if(categoryDAO.delete(category) != false){
					return "redirect:/categories";
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

