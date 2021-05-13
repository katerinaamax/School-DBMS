package login;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dbUtil.DbConnection;

public class LoginModel {
	Connection connection;
	
	
	public LoginModel() {
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
	
	public boolean isLogin(String username, String password, String option) throws Exception {
		PreparedStatement  pr = null;
		ResultSet rs = null;
		
		String sqlQuery = "SELECT * FROM User WHERE username = ? and password = ? and role = ?;";
		
		try {
			System.out.println("isClosed = " + connection.isClosed());
			
			pr = this.connection.prepareStatement(sqlQuery);
			System.out.println(pr == null);
			pr.setString(1, username);
			pr.setString(2,  password);
			pr.setString(3,  option);
			
			rs = pr.executeQuery();
			
			if(rs.next()) {
				return true;
			}	
			
			else
				return false;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		finally {
			pr.close();
			rs.close();
		}
	}
	
	public String getLoginId(String username) {
		String logId = "SELECT User.userId FROM User WHERE User.username = ?";
		String id = "";
		try {
			PreparedStatement log_id = this.connection.prepareStatement(logId);
			log_id.setString(1,  username);
			ResultSet rs = log_id.executeQuery();
			
			if(rs.next())
				id = rs.getString(1);
				
			log_id.close();
			rs.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
}
