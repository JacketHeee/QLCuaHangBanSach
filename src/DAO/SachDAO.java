package DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.SachDTO;
import config.JDBCUtil;

public class SachDAO implements DAOInterface<SachDTO>{

	private static SachDAO instance;
	private SachDAO() {}
	
	public static SachDAO getInstance() {
		if(instance == null) {
			instance = new SachDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(SachDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(SachDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(SachDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<SachDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<SachDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM SACH WHERE TRANGTHAI = 1";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("maSach");
				String tenSach = rs.getString("tenSach");
				BigDecimal giaBan = rs.getBigDecimal("giaBan");
				int soLuongTon = rs.getInt("soLuongTon");
				int namXB = rs.getInt("namXB");
				SachDTO sach = new SachDTO(id, tenSach, giaBan, soLuongTon, namXB);
				result.add(sach);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
