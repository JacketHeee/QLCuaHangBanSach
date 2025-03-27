package config;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;

public class JDBCUtil {
	private static final String url = "jdbc:mysql://localhost:3306/quanlycuahangbansachv3";
	private static final String user = "root";
	private static final String password = "";
	
	public static Connection getConnection() {
		Connection c = null;
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			
			c = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return(c);
	}

	public static void closeConnection(Connection c) {
		if(c != null) {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
