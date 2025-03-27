package DAO;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.NhomQuyenDTO;
import utils.JDBCUtil;

public class NhomQuyenDAO implements DAOInterface<NhomQuyenDTO>{

	private static NhomQuyenDAO instance;
	private NhomQuyenDAO() {}
	
	public static NhomQuyenDAO getInstance() {
		if(instance == null) {
			instance = new NhomQuyenDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(NhomQuyenDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(NhomQuyenDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(NhomQuyenDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<NhomQuyenDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<NhomQuyenDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM NHOMQUYEN WHERE TRANGTHAI = 1";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("maRole");
				String tenRole = rs.getString("tenRole");
				NhomQuyenDTO nhomQuyen = new NhomQuyenDTO(id, tenRole);
				result.add(nhomQuyen);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
