package DAO;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.KM_SachDTO;
import config.JDBCUtil;

public class KM_SachDAO implements DAOInterface<KM_SachDTO>{

	private static KM_SachDAO instance;
	private KM_SachDAO() {}
	
	public static KM_SachDAO getInstance() {
		if(instance == null) {
			instance = new KM_SachDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(KM_SachDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(KM_SachDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<KM_SachDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<KM_SachDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM KM_SACH";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int maKM = rs.getInt("maKM");
				int maSach = rs.getInt("maSach");
				
				KM_SachDTO KM_Sach = new KM_SachDTO(maKM, maSach);
				result.add(KM_Sach);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
