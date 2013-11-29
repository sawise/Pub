package com.group2.bottomapp.bottomAppServer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

public class Drink implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String description;
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();
	@Min(0)
	private int ratingUp;
	@Min(0)
	private int ratingDown;
	
	public Drink(){
		
	}
	
	public Drink(int id, String name, String description, List<Ingredient> ingredients, int ratingUp, int ratingDown){
		this.id = id;
		this.name = name;
		this.description = description;
		this.ingredients = ingredients;
		this.ratingUp = ratingUp;
		this.ratingDown = ratingDown;
	}
	
	public Drink(int id, String name, String description, int ratingUp, int ratingDown){
		this.id = id;
		this.name = name;
		this.description = description;
		this.ratingUp = ratingUp;
		this.ratingDown = ratingDown;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	public int getIngredientsSize(){
		
		if(this.ingredients.size() != 0){
			return this.ingredients.size();
		} else {
			return 0;
		}
		
	} 
	
	public void addIngredient(Ingredient ingredient){
		this.ingredients.add(ingredient);
	}
	
	public int getRatingUp() {
		return ratingUp;
	}
	
	public void setRatingUp(int ratingUp) {
		this.ratingUp = ratingUp;
	}
	
	public int getRatingDown() {
		return ratingDown;
	}
	
	public void setRatingDown(int ratingDown) {
		this.ratingDown = ratingDown;
	}

}
