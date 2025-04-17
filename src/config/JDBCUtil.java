package config;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;

// new com.mysql.cj.jdbc.Driver()
public class JDBCUtil {
	private Connection connection;
	private PreparedStatement pst;

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

	public ResultSet executeQuery (String sql, Object... params){
		ResultSet rs = null;
		try {
			pst = this.connection.prepareStatement(sql);
			setParams(params);
			rs = pst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(rs);
	}

	public int executeUpdate(String sql, Object... params){
		int n = -1;
		try {
			pst = this.connection.prepareStatement(sql);
			setParams(params);
			n = pst.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(n);
	}

	public int getAutoIncrement(String tableName){
		int nextID = -1;
		String query = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ?";
		try {
			pst = this.connection.prepareStatement(query);
			pst.setString(1, tableName);
			ResultSet result = pst.executeQuery();
			while(result.next()){
				nextID = result.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(nextID);
	}

	public void setParams(Object... params){
		for(int i = 0; i < params.length; i++){
			int index = i + 1;
			if(params[i] instanceof String){
				try {
					pst.setString(index, (String)params[i]);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if(params[i] instanceof Integer){
				try {
					pst.setInt(index, (Integer)params[i]);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if(params[i] instanceof BigDecimal){
				try {
					pst.setBigDecimal(index, (BigDecimal)params[i]);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if(params[i] instanceof LocalDateTime){//java hỗ trợ
				try {
					pst.setObject(index, params[i]);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if(params[i] instanceof java.sql.Date){
				try {
					pst.setDate(index, (java.sql.Date)params[i]);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else{
				System.out.println("Kiểu dữ liệu chưa có");
			}
		}
	}

}
