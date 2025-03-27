package DAO;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.PhuongThucTTDTO;
import utils.JDBCUtil;

public class PhuongThucTTDAO implements DAOInterface<PhuongThucTTDTO>{

	private static PhuongThucTTDAO instance;
	private PhuongThucTTDAO() {}
	
	public static PhuongThucTTDAO getInstance() {
		if(instance == null) {
			instance = new PhuongThucTTDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(PhuongThucTTDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(PhuongThucTTDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(PhuongThucTTDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<PhuongThucTTDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<PhuongThucTTDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM PHUONGTHUC_TT WHERE TRANGTHAI = 1";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("maPT");
				String tenPT = rs.getString("tenPTTT");
				PhuongThucTTDTO PhuongThucTT = new PhuongThucTTDTO(id, tenPT);
				result.add(PhuongThucTT);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
