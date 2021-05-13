package register;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbUtil.DbConnection;

public class RegisterModel {
	Connection connection;
	
	public RegisterModel() {
		try {
			this.connection = DbConnection.getConnection();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(this.connection == null) {
			System.exit(1);
		}
	}
	
	public boolean isConnected() {
		return this.connection != null;
	}
	
	//Check if user with this email exists in database
	public boolean checkEmail(String email) {
		PreparedStatement  pr = null;
		ResultSet rs = null;
		
		String sqlQuery = "SELECT * FROM User WHERE email = ? ";
		try {
			pr = this.connection.prepareStatement(sqlQuery);			
			pr.setString(1, email);
		
			rs = pr.executeQuery();
		
			if(rs.next()) 
				return false;
		
			else
				return true;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		finally {
			try {
				pr.close();
				rs.close();
			}
			catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	//Check if user with this username exists in database
		public boolean checkUsername(String username) {
			PreparedStatement  pr = null;
			ResultSet rs = null;
			
			String sqlQuery = "SELECT * FROM User WHERE username = ? ";
			try {
				pr = this.connection.prepareStatement(sqlQuery);			
				pr.setString(1, username);
			
				rs = pr.executeQuery();
			
				if(rs.next()) 
					return false;
			
				else
					return true;
			}
			catch(SQLException e) {
				e.printStackTrace();
				return false;
			}
			
			finally {
				try {
					pr.close();
					rs.close();
				}
				catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
	
	public boolean registerUser(String username, String password, String name, String email, String phone, String role) {
		if (checkEmail(email) && checkUsername(username)) {
			String sql_user = "INSERT INTO User(username, password, name, email, phone, role) VALUES(?,?,?,?,?,?)";
			try {
				PreparedStatement pr = this.connection.prepareStatement(sql_user);
				pr.setString(1, username);
				pr.setString(2,  password);
				pr.setString(3,  name);
				pr.setString(4, email);
				pr.setObject(5, phone, java.sql.Types.NUMERIC);
				pr.setString(6,  role);
				pr.executeUpdate();
				pr.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
				return false;

			}
			
			if(role.equals("Admin")) {
				
				int id = 0;
				String sql_id = "SELECT userID FROM User WHERE username = ?";
				
				try {
					PreparedStatement pr = this.connection.prepareStatement(sql_id);			
					pr.setString(1, username);
					ResultSet rs = pr.executeQuery();
				
					if(rs.next()) {
						System.out.println("id: "+ id);
						id = rs.getInt("userId");
						pr.close();
						rs.close();
					
						String sql_admin = "INSERT INTO Admin(adminId) VALUES(?)";
						try {
							PreparedStatement pr_admin = this.connection.prepareStatement(sql_admin);
							pr_admin.setInt(1, id);
							pr_admin.executeUpdate();
							pr_admin.close();
							return true;
						}
						catch(SQLException e) {
							e.printStackTrace();
							return false;
						}
					}		
				}
				catch(SQLException ex) {
					ex.printStackTrace();
					return false;
				}
				return false;
				
			}
			
			else if (role.equals("Teacher")) {
				
				int id = 0;
				String sql_id = "SELECT userID FROM User WHERE username = ?";
				
				try {
					PreparedStatement pr = this.connection.prepareStatement(sql_id);			
					pr.setString(1, username);
					ResultSet rs = pr.executeQuery();
				
					if(rs.next()) {
						id = rs.getInt("userId");
						pr.close();
						rs.close();
					
						String sql_admin = "INSERT INTO Teacher(teacherId) VALUES(?)";
						try {
							PreparedStatement pr_teacher = this.connection.prepareStatement(sql_admin);
							pr_teacher.setInt(1, id);
							pr_teacher.executeUpdate();
							pr_teacher.close();
							return true;
						}
						catch(SQLException e) {
							e.printStackTrace();
							return false;
						}
					}		
				}
				catch(SQLException ex) {
					ex.printStackTrace();
					return false;
				}
			}
		}
		return false;
		
	}
}
