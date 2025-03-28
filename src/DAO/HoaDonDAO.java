package DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.HoaDonDTO;
import DTO.HoaDonDTO;
import config.JDBCUtil;

public class HoaDonDAO implements DAOInterface<HoaDonDTO>{

	private static HoaDonDAO instance;
	private HoaDonDAO() {}
	
	public static HoaDonDAO getInstance() {
		if(instance == null) {
			instance = new HoaDonDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(HoaDonDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(HoaDonDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<HoaDonDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<HoaDonDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM HOADON WHERE TRANGTHAI = 1";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("maHD");
				Date tenSach = rs.getDate("ngayBan");
				BigDecimal tongTien = rs.getBigDecimal("tongTien");
				int maTK = rs.getInt("maTK");
				int maPT = rs.getInt("maPT");
				int maKM = rs.getInt("maKM");
				int maKH = rs.getInt("maKH");
				HoaDonDTO khach = new HoaDonDTO(id, tenSach, tongTien, maTK, maPT, maKM, maKH);
				result.add(khach);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
