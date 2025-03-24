package DAO;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.TaiKhoanDTO;
import config.JDBCUtil;

public class TaiKhoanDAO implements DAOInterface<TaiKhoanDTO>{

	private static TaiKhoanDAO instance;
	private TaiKhoanDAO() {}
	
	public static TaiKhoanDAO getInstance() {
		if(instance == null) {
			instance = new TaiKhoanDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(TaiKhoanDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(TaiKhoanDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(TaiKhoanDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<TaiKhoanDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<TaiKhoanDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM TAIKHOAN WHERE TRANGTHAI = 1";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("maTK");
				String username = rs.getString("username");
				String password = rs.getString("password");
				int maRole = rs.getInt("maRole");
				
				TaiKhoanDTO taiKhoan = new TaiKhoanDTO(id, username, password, maRole);
				result.add(taiKhoan);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
