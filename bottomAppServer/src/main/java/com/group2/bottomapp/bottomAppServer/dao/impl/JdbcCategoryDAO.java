package com.group2.bottomapp.bottomAppServer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.group2.bottomapp.bottomAppServer.dao.CategoryDAO;
import com.group2.bottomapp.bottomAppServer.model.Category;

public class JdbcCategoryDAO implements CategoryDAO {

	private DataSource dataSource;
	 
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public boolean insert(Category category) {
		String sql = "INSERT INTO categories " +
				"(name) VALUES (?)";
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, category.getName());
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

	public boolean update(Category category) {
		String sql = "UPDATE categories SET name = ? WHERE id = ?";
		Connection conn = null;
		
		try{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, category.getName());
			ps.setInt(2, category.getId());
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

	public boolean delete(Category category) {
		String sql = "DELETE FROM categories WHERE id = ?";
		
		Connection conn = null;
		
		try{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, category.getId());
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
	
	
	// check if any ingredient belongs to the category 
	public boolean checkIfUsed(Category category){
		String sql = "SELECT * FROM categories c, ingredients i WHERE i.category = ?";
		
		Connection conn = null;
		int count = 0;
		
		try{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, category.getId());
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

	public Category findByCategoryId(int categoryId) {

		String sql = "SELECT * FROM categories WHERE id = ?";
		 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, categoryId);
			Category category = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				category= new Category(
					rs.getInt("id"),
					rs.getString("name")
				);
			}
			rs.close();
			ps.close();
			return category;
			
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
	

	public List<Category> getAllCategories() {
		String sql = "SELECT * FROM categories ORDER BY name";
		List<Category> categoriesList = new ArrayList<Category>();
		 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			Category category = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				category = new Category(
					rs.getInt("id"),
					rs.getString("name")
				);
				categoriesList.add(category);
			}
			rs.close();
			ps.close();
			return categoriesList;
			
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
