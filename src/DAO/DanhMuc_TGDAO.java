package DAO;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.DanhMuc_TGDTO;
import config.JDBCUtil;

public class DanhMuc_TGDAO implements DAOInterface<DanhMuc_TGDTO>{

	private static DanhMuc_TGDAO instance;
	private DanhMuc_TGDAO() {}
	
	public static DanhMuc_TGDAO getInstance() {
		if(instance == null) {
			instance = new DanhMuc_TGDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(DanhMuc_TGDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(DanhMuc_TGDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(DanhMuc_TGDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<DanhMuc_TGDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<DanhMuc_TGDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM DANHMUC_TG";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int maTacGia = rs.getInt("maTacGia");
				int maSach = rs.getInt("maSach");
				
				DanhMuc_TGDTO DanhMuc_TG = new DanhMuc_TGDTO(maTacGia, maSach);
				result.add(DanhMuc_TG);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
