package config;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

// new com.mysql.cj.jdbc.Driver()
public class JDBCUtil {
	private Connection connection;

	public void Open(){
		try {
			Driver driver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(driver);
			String url = "jdbc:mysql://localhost:3306/quanlycuahangbansach";
			Properties info = new Properties();
			info.setProperty("characterEncoding", "utf-8");
			info.setProperty("user", "root");
			info.setProperty("password", "");
			this.connection = DriverManager.getConnection(url, info);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Close(){
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery (String sql){
		ResultSet rs = null;
		try {
			Statement statement = this.connection.createStatement();
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(rs);
	}

	public int executeUpdate(String sql){
		int n = -1;
		try {
			Statement statement = this.connection.createStatement();
			n = statement.executeUpdate(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(n);
	}

	public int getAutoIncrement(String tableName){
		int nextID = -1;
		String query = String.format("SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = '%s'", tableName);
		try {
			Statement statment = this.connection.createStatement();
			ResultSet result = statment.executeQuery(query);
			while(result.next()){
				nextID = result.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(nextID);
	}

}
