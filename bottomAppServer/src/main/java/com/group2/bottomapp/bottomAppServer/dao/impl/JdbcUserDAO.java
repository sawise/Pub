package com.group2.bottomapp.bottomAppServer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import com.group2.bottomapp.bottomAppServer.dao.UserDAO;
import com.group2.bottomapp.bottomAppServer.model.User;

public class JdbcUserDAO implements UserDAO {
	
	private DataSource dataSource;
	 
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	public boolean checkLogin(User user) {
		
		String salt = user.getSalt();

		String sql = ("SELECT * FROM users WHERE email = ? AND password = SHA(SHA(? '" + salt + "'))");
		 
		Connection conn = null;
		boolean status = false;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				status = true;
			} else {
				status = false;
			}
			
			rs.close();
			ps.close();
			return status;

			
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
	}
	
	
	public boolean insert(User user) {
		// generate salt
		String salt = UUID.randomUUID().toString();
		
		// generate identifier
    	String identifier = UUID.randomUUID().toString();
    	identifier = identifier.replace("-", ""); 
		user.setIdentifier(identifier);
    	
		String sql = ("INSERT INTO users (username, email, password, password_salt, identifier) VALUES (?, ?, SHA(SHA(? '" + salt + "')), ?, ?)");
		
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, salt);
			ps.setString(5, user.getIdentifier());
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
	
	
	public boolean update(User user) {
		String sql = "UPDATE users SET username = ?, email = ?, password = ?, password_salt = ?, identifier = ? WHERE id = ?";
		Connection conn = null;
		
		try{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getSalt());
			ps.setString(5, user.getIdentifier());
			ps.setInt(6, user.getId());
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

	public boolean delete(User user) {
		String sql = "DELETE FROM users WHERE id = ?";
		
		Connection conn = null;
		
		try{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getId());
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
	
	
	
	// insert a ingredient for a user
	public boolean insertIngredientForUser(int userId, int ingredientId){
		String sql = "INSERT INTO user_ingredients " +
				"(user_id, ingredient_id) VALUES (?, ?)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, ingredientId);
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
	
	
	// delete a ingredient for a user
	public boolean deleteIngredientForUser(int userId, int ingredientId){
	String sql = "DELETE FROM user_ingredients WHERE user_id = ? AND ingredient_id = ?";
		
		Connection conn = null;
		
		try{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, ingredientId);
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
	
	
	public User findUserById(int userId) {

		String sql = "SELECT * FROM users WHERE id = ?";
		 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			User user = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = new User(
					rs.getInt("id"),
					rs.getString("username"),
					rs.getString("email"),
					rs.getString("password"),
					rs.getString("password_salt"),
					rs.getString("identifier")
				);
			}
			rs.close();
			ps.close();
			return user;
			
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

	
	public User findByEmail(String email) {

		String sql = "SELECT * FROM users WHERE email = ?";
		 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			User user = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user= new User(
					rs.getInt("id"),
					rs.getString("username"),
					rs.getString("email"),
					rs.getString("password"),
					rs.getString("password_salt"),
					rs.getString("identifier")
				);
			}
			rs.close();
			ps.close();
			return user;
			
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
	

	public List<User> getAllUsers() {
		String sql = "SELECT * FROM users";
		List<User> usersList = new ArrayList<User>();
		 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			User user = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = new User(
					rs.getInt("id"),
					rs.getString("username"),
					rs.getString("email"),
					rs.getString("password"),
					rs.getString("password_salt"),
					rs.getString("identifier")
				);
				usersList.add(user);
			}
			rs.close();
			ps.close();
			return usersList;
			
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
