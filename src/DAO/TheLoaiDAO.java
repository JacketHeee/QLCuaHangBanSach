package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.TheLoaiDTO;
import config.JDBCUtil;

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
		Connection con = JDBCUtil.getConnection();
		int rowInserted = 0;
		String sql = "INSERT INTO THELOAI (tenTheLoai) VALUES (?)";
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, t.getTenTheLoai());

			rowInserted = pst.executeUpdate();

			//set mã DTO tăng tự động
			ResultSet rs = pst.getGeneratedKeys();
			while(rs.next()){
				t.setMaTheLoai(rs.getInt(1));
			}

			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowInserted;
	}

	@Override
	public int delete(int id) {
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
