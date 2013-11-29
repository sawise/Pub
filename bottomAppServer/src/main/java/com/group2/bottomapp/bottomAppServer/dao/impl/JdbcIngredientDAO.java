package com.group2.bottomapp.bottomAppServer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.group2.bottomapp.bottomAppServer.dao.IngredientDAO;
import com.group2.bottomapp.bottomAppServer.model.Category;
import com.group2.bottomapp.bottomAppServer.model.Drink;
import com.group2.bottomapp.bottomAppServer.model.Ingredient;

 
public class JdbcIngredientDAO implements IngredientDAO
{
	private DataSource dataSource;
 
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	// INSERT
	public boolean insert(Ingredient ingredient){
		
		String sql = "INSERT INTO ingredients " +
				"(ingredient_name, category) VALUES (?, ?)";
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, ingredient.getName());
			ps.setInt(2, ingredient.getCategory().getId());
			ps.executeUpdate();
			ps.close();
 
		} catch (SQLException e) {
			return false;
			
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					return false;
				}
			}
		}
		return true;
	}


    // UPDATE
	public boolean update(Ingredient ingredient) {
		String sql = "UPDATE ingredients SET ingredient_name = ?, category = ? WHERE id = ?";
		Connection conn = null;

		try{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, ingredient.getName());
			ps.setInt(2, ingredient.getCategory().getId());
			ps.setInt(3, ingredient.getId());
			ps.executeUpdate();
			ps.close();
			
		} catch(SQLException e){
			return false;
		
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					return false;
				}
			}
		}
		
		return true;
	}

	// DELETE
	public boolean delete(Ingredient ingredient) {
		String sql = "DELETE FROM ingredients WHERE id = ?";
		
		Connection conn = null;
		
		try{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, ingredient.getId());
			ps.executeUpdate();
			ps.close();
			
		} catch(SQLException e){
			return false;
			
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {
					return false;
				}
			}
		}
		return true;
	}

	// FIND (id)
	public Ingredient findByIngredientId(int ingredientId) {
	
		String sql = "SELECT i.id, i.ingredient_name, i.category, c.id, c.name FROM ingredients i, categories c WHERE i.id = ? AND i.category = c.id";

		 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, ingredientId);
			
			Ingredient ingredient = null;
			Category category = null;
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				category = new Category(
					rs.getInt("c.id"),
					rs.getString("c.name")
				);
				
				ingredient = new Ingredient(
					rs.getInt("i.id"),
					rs.getString("i.ingredient_name"),
					category
				);
			}
			rs.close();
			ps.close();
			return ingredient;
			
		} catch (SQLException e) {
			return null;
			
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {
					return null;
				}
			}
		}
	}
	
	
	// check if the ingredient belongs to a drink 
	public boolean checkIfUsed(Ingredient ingredient){
		String sql = "SELECT * FROM drink_ingredients di, ingredients i WHERE i.id = ? AND di.ingredient_id = i.id";
		
		Connection conn = null;
		int count = 0;
		
		try{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, ingredient.getId());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				count++;
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e){
			return false;
			
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {
					return false;
				}
			}
		}
		
		if(count > 0){
			return true;
		} else {
			return false;
		}
	}
	

	public List<Ingredient> getAllIngredients() {
		String sql = "SELECT i.id, i.ingredient_name, i.category, c.id, c.name FROM ingredients i, categories c WHERE i.category = c.id";
		
		List<Ingredient> ingredientsList = new ArrayList<Ingredient>();
		 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			Ingredient ingredient = null;
			Category category = null;
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				category = new Category(
					rs.getInt("c.id"),
					rs.getString("c.name")
				);
				
				ingredient = new Ingredient(
					rs.getInt("i.id"),
					rs.getString("ingredient_name"),
					category
				);
				ingredientsList.add(ingredient);
			}
			rs.close();
			ps.close();
			return ingredientsList;
			
		} catch (SQLException e) {
			return null;
			
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {
					return null;
				}
			}
		}
	}
	
	
	public List<Ingredient> getIngredientsForDrink(Drink drink) {
		String sql = "SELECT i.id, i.ingredient_name, i.category, di.measurement, c.id, c.name FROM ingredients i, drink_ingredients di, categories c "
				+ "WHERE di.drink_id = ? AND i.id = ingredient_id AND i.category = c.id";
		
		List<Ingredient> ingredientsList = new ArrayList<Ingredient>();
		
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, drink.getId());
			
			Ingredient ingredient = null;
			Category category = null;
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				
				category = new Category(
					rs.getInt("c.id"), 
					rs.getString("c.name")		
				);
				
				ingredient = new Ingredient(
				    rs.getInt("i.id"), 
					rs.getString("i.ingredient_name"),
					category,
				    rs.getString("di.measurement")
				);
				ingredientsList.add(ingredient);
			}
			rs.close();
			ps.close();
			return ingredientsList;
			
		} catch (SQLException e) {
			return null;
			
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {
					return null;
				}
			}
		}
	}
	
	
}