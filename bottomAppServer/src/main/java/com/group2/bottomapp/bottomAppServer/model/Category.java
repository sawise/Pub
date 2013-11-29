package com.group2.bottomapp.bottomAppServer.model;

import org.hibernate.validator.constraints.NotEmpty;

public class Category {

	private int id;
	@NotEmpty
	private String name;
	
	public Category(){
		
	}
	
	public Category (int id, String name){
		this.id = id;
		this.name = name;
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
	
}
