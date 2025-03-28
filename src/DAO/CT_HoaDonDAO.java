package DAO;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.CT_HoaDonDTO;
import config.JDBCUtil;

public class CT_HoaDonDAO implements DAOInterface<CT_HoaDonDTO>{

	private static CT_HoaDonDAO instance;
	private CT_HoaDonDAO() {}
	
	public static CT_HoaDonDAO getInstance() {
		if(instance == null) {
			instance = new CT_HoaDonDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(CT_HoaDonDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(CT_HoaDonDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<CT_HoaDonDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<CT_HoaDonDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM CT_HOADON";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int maSach = rs.getInt("maSach");
				int maHD = rs.getInt("maHD");
				int soLuong = rs.getInt("soLuong");
				BigDecimal giaBan = rs.getBigDecimal("giaBan");
				
				CT_HoaDonDTO CT_HoaDon = new CT_HoaDonDTO(maSach, maHD, soLuong, giaBan);
				result.add(CT_HoaDon);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
