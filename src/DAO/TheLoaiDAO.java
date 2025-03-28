package DAO;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.TheLoaiDTO;
import utils.JDBCUtil;

public class TheLoaiDAO implements DAOInterface<TheLoaiDTO>{

	private static TheLoaiDAO instance;
	private TheLoaiDAO() {}
	
	public static TheLoaiDAO getInstance() {
		if(instance == null) {
			instance = new TheLoaiDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(TheLoaiDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(TheLoaiDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(TheLoaiDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<TheLoaiDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<TheLoaiDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM THELOAI WHERE TRANGTHAI = 1";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("maTheLoai");
				String tenTheLoai = rs.getString("tenTheLoai");
				TheLoaiDTO theLoai = new TheLoaiDTO(id, tenTheLoai);
				result.add(theLoai);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
