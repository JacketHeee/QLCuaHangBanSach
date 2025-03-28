package DAO;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.NhaCungCapDTO;
import config.JDBCUtil;

public class NhaCungCapDAO implements DAOInterface<NhaCungCapDTO>{

	private static NhaCungCapDAO instance;
	private NhaCungCapDAO() {}
	
	public static NhaCungCapDAO getInstance() {
		if(instance == null) {
			instance = new NhaCungCapDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(NhaCungCapDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(NhaCungCapDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<NhaCungCapDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<NhaCungCapDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM NHACUNGCAP WHERE TRANGTHAI = 1";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("maNCC");
				String tenNCC = rs.getString("tenNCC");
				String diaChi = rs.getString("diaChi");
				String soDT = rs.getString("soDT");
				String email = rs.getString("email");
				
				NhaCungCapDTO nhaCungCap = new NhaCungCapDTO(id, tenNCC, diaChi, soDT, email);
				result.add(nhaCungCap);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
