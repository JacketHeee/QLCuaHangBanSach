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
		String sql = "INSERT INTO KhachHang (tenKH, soDT, gioiTinh) VALUES (?,?,?)";

		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		int nextID = jdbcUtil.getAutoIncrement("KhachHang");
		rowInserted = jdbcUtil.executeUpdate(
			sql,
			t.getTenKH(),
			t.getSoDT(),
			t.getGioiTinh()
		);
		jdbcUtil.Close();
		t.setMaKH(nextID);
		return rowInserted;
	}

	@Override
	public int delete(int id) {
		int rowDeleted = 0;
		String query = String.format("UPDATE KhachHang SET TRANGTHAI = 0 WHERE maKH = ?");
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		rowDeleted = jdbcUtil.executeUpdate(query, id);
		jdbcUtil.Close();
		return(rowDeleted);
	}

	@Override
	public int update(KhachHangDTO t) {
		int rowUpdated = 0;
		String query = "UPDATE KhachHang SET tenKH = ?, soDT = ?, gioiTinh = ? WHERE maKH = ?";

	
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		rowUpdated = jdbcUtil.executeUpdate(
			query,
			t.getTenKH(),
			t.getSoDT() + "",
			t.getGioiTinh() + "",
			t.getMaKH() + ""
		);
		jdbcUtil.Close();
		return rowUpdated;
	}

	// @Override
	public ArrayList<KhachHangDTO> getAll() {
		ArrayList<KhachHangDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM KhachHang WHERE TRANGTHAI = 1";
		
		
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		ResultSet rs = jdbcUtil.executeQuery(sql);
		try {
			while(rs.next()) {
				int id = rs.getInt("maKH");
				String tenKH = rs.getString("tenKH");
				String soDT = rs.getString("soDT");
				String gioiTinh = rs.getString("gioiTinh");
				KhachHangDTO KhachHang = new KhachHangDTO(id, tenKH, soDT, gioiTinh);
				result.add(KhachHang);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jdbcUtil.Close();
		return result;
	}

	public KhachHangDTO getKhachHangById(int maKhachHang) {
		KhachHangDTO result = null;
		String sql = "SELECT * FROM KhachHang WHERE maKH = " + maKhachHang;
		
		
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		ResultSet rs = jdbcUtil.executeQuery(sql);
		try {
			while(rs.next()) {
				int id = rs.getInt("maKH");
				String tenKH = rs.getString("tenKH");
				String soDT = rs.getString("soDT");
				String gioiTinh = rs.getString("gioiTinh");
				KhachHangDTO KhachHang = new KhachHangDTO(id, tenKH, soDT, gioiTinh);
				result = KhachHang;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jdbcUtil.Close();
		return result;
	}

	public String getTenByMaKhachHang(int ma){
		String result = new String();
		String sql = "SELECT tenKH FROM KhachHang WHERE maKH = ?";
		
		
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		ResultSet rs = jdbcUtil.executeQuery(sql, ma);
		try {
			while(rs.next()) {
				String tenKH = rs.getString("tenKH");
				result = tenKH;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jdbcUtil.Close();
		return result;
	}
}
