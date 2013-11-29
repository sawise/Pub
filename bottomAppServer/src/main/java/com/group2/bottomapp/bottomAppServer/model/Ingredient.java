package com.group2.bottomapp.bottomAppServer.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;


public class Ingredient implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	@NotEmpty
	private String name;
	private Category category;
	private String measurement;
	
	public Ingredient(){
		
	}
	
	public Ingredient(int id, String name, Category category, String measurement){
		this.id = id;
		this.name = name;
		this.category = category;
		this.measurement = measurement;
	}
	
	public Ingredient(int id, String name, Category category){
		this.id = id;
		this.name = name;
		this.category = category;
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
	
	public Category getCategory(){
		return this.category;
	}
	
	public void setCategory(Category category){
		this.category = category;
	}
	
	public String getMeasurement() {
		return measurement;
	}
	
	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}
	
}
