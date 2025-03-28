package DAO;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.PhanLoaiDTO;
import config.JDBCUtil;

public class PhanLoaiDAO implements DAOInterface<PhanLoaiDTO>{

	private static PhanLoaiDAO instance;
	private PhanLoaiDAO() {}
	
	public static PhanLoaiDAO getInstance() {
		if(instance == null) {
			instance = new PhanLoaiDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(PhanLoaiDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(PhanLoaiDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<PhanLoaiDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<PhanLoaiDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM PHANLOAI";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int maSach = rs.getInt("maSach");
				int maTheLoai = rs.getInt("maTheLoai");
				
				PhanLoaiDTO PhanLoai = new PhanLoaiDTO(maSach, maTheLoai);
				result.add(PhanLoai);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
