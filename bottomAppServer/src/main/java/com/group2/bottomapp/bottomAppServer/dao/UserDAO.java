package com.group2.bottomapp.bottomAppServer.dao;

import java.util.List;

import com.group2.bottomapp.bottomAppServer.model.User;


public interface UserDAO {
	public boolean checkLogin(User user);
	public boolean insert(User user);
	public boolean update(User user);
	public boolean delete(User user);
	public boolean insertIngredientForUser(int userId, int ingredientId);
	public boolean deleteIngredientForUser(int userId, int ingredientId);
	public User findUserById(int userId);
	public User findByEmail(String email);
	public List<User> getAllUsers(); 
}
