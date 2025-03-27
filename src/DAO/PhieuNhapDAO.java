package DAO;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.PhieuNhapDTO;
import utils.JDBCUtil;

public class PhieuNhapDAO implements DAOInterface<PhieuNhapDTO>{

	private static PhieuNhapDAO instance;
	private PhieuNhapDAO() {}
	
	public static PhieuNhapDAO getInstance() {
		if(instance == null) {
			instance = new PhieuNhapDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(PhieuNhapDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(PhieuNhapDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(PhieuNhapDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<PhieuNhapDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<PhieuNhapDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM PHIEUNHAP WHERE TRANGTHAI = 1";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("maNhap");
				Date ngayNhap = rs.getDate("ngayNhap");
				BigDecimal tongTien = rs.getBigDecimal("tongTien");
				int maNCC = rs.getInt("maNCC");
				int maTK = rs.getInt("maTK");
				
				PhieuNhapDTO phieuNhap = new PhieuNhapDTO(id, ngayNhap, tongTien, maNCC, maTK);
				result.add(phieuNhap);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
