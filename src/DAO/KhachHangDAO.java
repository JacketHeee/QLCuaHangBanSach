package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.KhachHangDTO;
import config.JDBCUtil;

public class KhachHangDAO implements DAOInterface<KhachHangDTO>{

	private static KhachHangDAO instance;
	private KhachHangDAO() {}
	
	public static KhachHangDAO getInstance() {
		if(instance == null) {
			instance = new KhachHangDAO();
		}
		return(instance);
	}
	@Override
	public int insert(KhachHangDTO t) {
		int rowInserted = 0;
		String sql = String.format(
			"INSERT INTO KhachHang (tenKH, soDT, gioiTinh) VALUES ('%s','%s','%s')",
			t.getTenKH(),
			t.getSoDT(),
			t.getGioiTinh()
		 );
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		int nextID = jdbcUtil.getAutoIncrement("KhachHang");
		rowInserted = jdbcUtil.executeUpdate(sql);
		jdbcUtil.Close();
		t.setMaKH(nextID);
		return rowInserted;
	}

	@Override
	public int delete(int id) {
		int rowDeleted = 0;
		String query = String.format("UPDATE KhachHang SET TRANGTHAI = 0 WHERE maKH = '%s'", id + "");
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		rowDeleted = jdbcUtil.executeUpdate(query);
		jdbcUtil.Close();
		return(rowDeleted);
	}

	@Override
	public int update(KhachHangDTO t) {
		// String sql = "UPDATE KhachHang SET tenKhachHang = ?, giaBan = ?, namXB = ?, maVung = ?, maNXB = ? WHERE maKhachHang = ?";
		int rowUpdated = 0;
		String query = String.format(
			"UPDATE KhachHang SET tenKH = '%s', soDT = '%s', gioiTinh = '%s' WHERE maKH = '%s'",
			t.getTenKH(),
			t.getSoDT() + "",
			t.getGioiTinh() + "",
			t.getMaKH() + ""
		);
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		rowUpdated = jdbcUtil.executeUpdate(query);
		jdbcUtil.Close();
		return rowUpdated;
	}

	// @Override
	public ArrayList<KhachHangDTO> getAll() {
		ArrayList<KhachHangDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM KhachHang WHERE TRANGTHAI = 1";
		
		try {
			JDBCUtil jdbcUtil = new JDBCUtil();
			jdbcUtil.Open();
			ResultSet rs = jdbcUtil.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("maKH");
				String tenKH = rs.getString("tenKH");
				String soDT = rs.getString("soDT");
				String gioiTinh = rs.getString("gioiTinh");
				KhachHangDTO KhachHang = new KhachHangDTO(id, tenKH, soDT, gioiTinh);
				result.add(KhachHang);
			}
			jdbcUtil.Close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
