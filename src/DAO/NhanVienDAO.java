package DAO;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.NhanVienDTO;
import config.JDBCUtil;

public class NhanVienDAO implements DAOInterface<NhanVienDTO>{

	private static NhanVienDAO instance;
	private NhanVienDAO() {}
	
	public static NhanVienDAO getInstance() {
		if(instance == null) {
			instance = new NhanVienDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(NhanVienDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(NhanVienDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<NhanVienDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<NhanVienDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM NHANVIEN WHERE TRANGTHAI = 1";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("maNV");
				String hoTen = rs.getString("hoTen");
				Date ngaySinh = rs.getDate("ngaySinh");
				String gioiTinh = rs.getString("gioiTinh");
				String soDT = rs.getString("soDT");
				int maTK = rs.getInt("maTK");
				
				NhanVienDTO nhanVien = new NhanVienDTO(id, hoTen, ngaySinh, gioiTinh, soDT, maTK);
				result.add(nhanVien);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
