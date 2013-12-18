package com.group2.bottomapp.bottomAppServer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.group2.bottomapp.bottomAppServer.dao.DrinkDAO;
import com.group2.bottomapp.bottomAppServer.model.Category;
import com.group2.bottomapp.bottomAppServer.model.Drink;
import com.group2.bottomapp.bottomAppServer.model.Ingredient;

public class JdbcDrinkDAO implements DrinkDAO {
	
	private DataSource dataSource;
	 
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
 

	public boolean insert(Drink drink) {
		
		String sql = "INSERT INTO drinks " +
				"(name, description, ratingUp, ratingDown) VALUES (?, ?, ?, ?)";
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, drink.getName());
			ps.setString(2, drink.getDescription());
			ps.setInt(3, drink.getRatingUp());
			ps.setInt(4, drink.getRatingDown());
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
	
	
	// insert ingredient connection to drink (with measurement)
	public boolean insertDrinkIngredient(int drinkId, Ingredient ingredient) {
		
		String sql = "INSERT INTO drink_ingredients " +
				"(drink_id, ingredient_id, measurement) VALUES (?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, drinkId);
			ps.setInt(2, ingredient.getId());
			ps.setString(3, ingredient.getMeasurement());
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
	
	
	public boolean update(Drink drink) {
		
		//TODO update connections to ingredients
		
		String sql = "UPDATE drinks SET name = ?, description = ?, ratingUp = ?, ratingDown = ? WHERE id = ?";
		Connection conn = null;
		
		try{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, drink.getName());
			ps.setString(2, drink.getDescription());
			ps.setInt(3, drink.getRatingUp());
			ps.setInt(4, drink.getRatingDown());
			ps.setInt(5, drink.getId());
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

	public boolean delete(Drink drink) {
		
		// connection to drink in drink_ingredients are deleted with foreign key delete constrains
		
		String sql = "DELETE FROM drinks WHERE id = ?"; 
		
		Connection conn = null;
		
		try{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, drink.getId());
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

	public Drink findByDrinkId(int drinkId) {
		
		String sql = "SELECT d.id, d.name, d.description, d.ratingUp, d.ratingDown, "
	                 + "di.measurement, " 
                     + "i.id, i.ingredient_name, "
	                 + "c.id, c.name "
                     + "FROM drinks d, ingredients i, drink_ingredients di, categories c " 
                     + "WHERE d.id = ? AND di.drink_id = d.id AND di.ingredient_id = i.id AND i.category = c.id ";
		 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, drinkId);
			
			Drink drink = null;
			Category category = null;
			Ingredient ingredient = null;
			List<Ingredient> ingredients = new ArrayList<Ingredient>();
			
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
				
				ingredients.add(ingredient);
				
				drink = new Drink(
					rs.getInt("id"),
					rs.getString("name"),
				    rs.getString("description"),
				    ingredients,
				    rs.getInt("ratingUp"),
				    rs.getInt("ratingDown")
				); 
			}
			rs.close();
			ps.close();
			return drink;
			
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
	
	
	public List<Drink> getAllDrinks(){
		String sql = "SELECT * FROM drinks";
		 
		Connection conn = null;
		List<Drink> drinkList = new ArrayList<Drink>();
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			Drink drink = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				drink = new Drink(
					rs.getInt("id"),
					rs.getString("name"),
				    rs.getString("description"),
				    rs.getInt("ratingUp"),
				    rs.getInt("ratingDown")
				);
				drinkList.add(drink);
				
			}
			rs.close();
			ps.close();
			return drinkList;
			
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
	

	public Drink findByDrinkName(String drinkName) {

		String sql = "SELECT * FROM drinks WHERE name = ?";
		 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, drinkName);
			Drink drink = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				drink = new Drink(
					rs.getInt("id"),
					rs.getString("name"),
				    rs.getString("description"),
				    rs.getInt("ratingUp"),
				    rs.getInt("ratingDown")
				); 
			}
			rs.close();
			ps.close();
			return drink;
			
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
	
	
	// get favorite drinks for a certain user
	public List<Drink> getUserFavoriteDrinks(int userId){
		String sql = "SELECT d.id, d.name, d.description, d.ratingUp, d.ratingDown FROM drinks d, user_favorites uf "
				+ "WHERE uf.user_id = ? AND d.id = uf.drink_id";
	
		Connection conn = null;
		List<Drink> drinkList = new ArrayList<Drink>();

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			Drink drink = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				drink = new Drink(
					rs.getInt("id"),
					rs.getString("name"),
				    rs.getString("description"),
				    rs.getInt("ratingUp"),
				    rs.getInt("ratingDown")
				);
				drinkList.add(drink);
				
			}
			rs.close();
			ps.close();
			return drinkList;
			
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
