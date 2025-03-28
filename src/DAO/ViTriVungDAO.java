package DAO;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.ViTriVungDTO;
import config.JDBCUtil;

public class ViTriVungDAO implements DAOInterface<ViTriVungDTO>{

	private static ViTriVungDAO instance;
	private ViTriVungDAO() {}
	
	public static ViTriVungDAO getInstance() {
		if(instance == null) {
			instance = new ViTriVungDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(ViTriVungDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(ViTriVungDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<ViTriVungDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<ViTriVungDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM VITRIVUNG WHERE TRANGTHAI = 1";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("maVung");
				String tenVung = rs.getString("tenVung");
				ViTriVungDTO viTriVung = new ViTriVungDTO(id, tenVung);
				result.add(viTriVung);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
