package com.group2.bottomapp.bottomAppServer.dao;

import java.util.List;

import com.group2.bottomapp.bottomAppServer.model.Category;

public interface CategoryDAO {

	public boolean insert(Category category);
	public boolean update(Category category);
	public boolean delete(Category category);
	public boolean checkIfUsed(Category category);
	public Category findByCategoryId(int categoryId);
	public List<Category> getAllCategories(); 


}
