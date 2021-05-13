package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

	private static final String connectionStr = "jdbc:sqlite:schoolDBMS.db";
	
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection(connectionStr);
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
