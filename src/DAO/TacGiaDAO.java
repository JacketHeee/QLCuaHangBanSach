package DAO;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.TacGiaDTO;
import config.JDBCUtil;

public class TacGiaDAO implements DAOInterface<TacGiaDTO>{

	private static TacGiaDAO instance;
	private TacGiaDAO() {}
	
	public static TacGiaDAO getInstance() {
		if(instance == null) {
			instance = new TacGiaDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(TacGiaDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(TacGiaDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(TacGiaDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<TacGiaDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<TacGiaDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM TACGIA WHERE TRANGTHAI = 1";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("maTacGia");
				String tenTacGia = rs.getString("tenTacGia");
				TacGiaDTO tacGia = new TacGiaDTO(id, tenTacGia);
				result.add(tacGia);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
