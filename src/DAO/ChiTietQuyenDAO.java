package DAO;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.ChiTietQuyenDTO;
import config.JDBCUtil;

public class ChiTietQuyenDAO implements DAOInterface<ChiTietQuyenDTO>{

	private static ChiTietQuyenDAO instance;
	private ChiTietQuyenDAO() {}
	
	public static ChiTietQuyenDAO getInstance() {
		if(instance == null) {
			instance = new ChiTietQuyenDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(ChiTietQuyenDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(ChiTietQuyenDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(ChiTietQuyenDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<ChiTietQuyenDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<ChiTietQuyenDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM CHITIETQUYEN";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int maRole = rs.getInt("maRole");
				int maChucNang = rs.getInt("maChucNang");
				String hanhDong = rs.getString("hanhDong");
				
				ChiTietQuyenDTO ChiTietQuyen = new ChiTietQuyenDTO(maRole, maChucNang, hanhDong);
				result.add(ChiTietQuyen);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
