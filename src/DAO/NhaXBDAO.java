package DAO;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.NhaXBDTO;
import utils.JDBCUtil;

public class NhaXBDAO implements DAOInterface<NhaXBDTO>{

	private static NhaXBDAO instance;
	private NhaXBDAO() {}
	
	public static NhaXBDAO getInstance() {
		if(instance == null) {
			instance = new NhaXBDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(NhaXBDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(NhaXBDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(NhaXBDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<NhaXBDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<NhaXBDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM NHAXB WHERE TRANGTHAI = 1";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int maNXB = rs.getInt("MaNXB");
				String tenNXB = rs.getString("tenNXB");
				String diaChi = rs.getString("diaChi");
				String soDT = rs.getString("soDT");
				String email = rs.getString("email");
				NhaXBDTO nhaXB = new NhaXBDTO(maNXB, tenNXB, diaChi, soDT, email);
				result.add(nhaXB);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
