package DAO;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.CT_PhieuNhapDTO;
import config.JDBCUtil;

public class CT_PhieuNhapDAO implements DAOInterface<CT_PhieuNhapDTO>{

	private static CT_PhieuNhapDAO instance;
	private CT_PhieuNhapDAO() {}
	
	public static CT_PhieuNhapDAO getInstance() {
		if(instance == null) {
			instance = new CT_PhieuNhapDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(CT_PhieuNhapDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(CT_PhieuNhapDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(CT_PhieuNhapDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<CT_PhieuNhapDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<CT_PhieuNhapDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM CT_PHIEUNHAP";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int maSach = rs.getInt("maSach");
				int maNhap = rs.getInt("maNhap");
				int soLuongNhap = rs.getInt("soLuongNhap");
				BigDecimal giaNhap = rs.getBigDecimal("giaNhap");
				
				CT_PhieuNhapDTO CT_PhieuNhap = new CT_PhieuNhapDTO(maSach, maNhap, soLuongNhap, giaNhap);
				result.add(CT_PhieuNhap);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
