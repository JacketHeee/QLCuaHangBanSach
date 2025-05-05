package DAO;

import DTO.SachDTO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import config.JDBCUtil;

public class SachDAO implements DAOInterface<SachDTO>{

	private static SachDAO instance;
	private SachDAO() {}
	
	public static SachDAO getInstance() {
		if(instance == null) {
			instance = new SachDAO();
		}
		return(instance);
	}
	@Override
	public int insert(SachDTO t) {
		int rowInserted = 0;
		String sql = "INSERT INTO SACH (tenSach, giaBan, namXB, maVung, maNXB, anh) VALUES (?,?,?,?,?,?)";

		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		int nextID = jdbcUtil.getAutoIncrement("Sach");
		rowInserted = jdbcUtil.executeUpdate(
			sql,
			t.getTenSach(),
			t.getGiaBan(),
			t.getNamXB(), 
			t.getMaVung(),
			t.getMaNXB(),
			t.getAnh()
		);
		// t.setGiaBan(new BigDecimal(0));
		jdbcUtil.Close();
		t.setMaSach(nextID);
		return rowInserted;
	}

	@Override
	public int delete(int id) {
		int rowDeleted = 0;
		String query = String.format("UPDATE SACH SET TRANGTHAI = 0 WHERE MASACH = ?");
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		rowDeleted =  jdbcUtil.executeUpdate(query, id);
		jdbcUtil.Close();
		return(rowDeleted);
	}

	@Override
	public int update(SachDTO t) {
		int rowUpdated = 0;
		String query = "UPDATE SACH SET tenSach = ?, giaBan = ?, namXB = ?, maVung = ?, maNXB = ?, anh = ? WHERE maSach = ?";

		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		rowUpdated = jdbcUtil.executeUpdate(
			query,
			t.getTenSach(),
			t.getNamXB(),
			t.getMaVung(),
			t.getMaNXB(),
			t.getAnh(),
			t.getMaSach()
		);

		jdbcUtil.Close();
		return rowUpdated;
	}

	// @Override
	public ArrayList<SachDTO> getAll() {
		ArrayList<SachDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM SACH WHERE TRANGTHAI = 1";
		
		
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		ResultSet rs = jdbcUtil.executeQuery(sql);
		try {
			while(rs.next()) {
				int id = rs.getInt("maSach");
				String tenSach = rs.getString("tenSach");
				int soLuong = rs.getInt("soLuong");
				BigDecimal giaBan = rs.getBigDecimal("giaBan");
				int namXB = rs.getInt("namXB");
				int maVung = rs.getInt("maVung");
				int maNXB = rs.getInt("maNXB");
				String anh = rs.getString("anh");
				SachDTO sach = new SachDTO(id, tenSach, soLuong, giaBan, namXB, maVung, maNXB, anh);
				result.add(sach);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jdbcUtil.Close();
		return result;
	}

	public SachDTO getInstanceByID(int maSach){
		SachDTO result = null;
		String sql = "SELECT * FROM SACH WHERE maSach = ? AND TRANGTHAI = 1";
		
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		ResultSet rs = jdbcUtil.executeQuery(sql, maSach);
		try {
			while(rs.next()) {
				int id = rs.getInt("maSach");
				String tenSach = rs.getString("tenSach");
				int soLuong = rs.getInt("soLuong");
				BigDecimal giaBan = rs.getBigDecimal("giaBan");
				int namXB = rs.getInt("namXB");
				int maVung = rs.getInt("maVung");
				int maNXB = rs.getInt("maNXB");
				String anh = rs.getString("anh");
				result = new SachDTO(id, tenSach, soLuong, giaBan, namXB, maVung, maNXB, anh);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jdbcUtil.Close();
		return result;
	}

	public ArrayList<SachDTO> getAllSachByMaVung(int maVung){
		ArrayList<SachDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM SACH WHERE maVung = ? AND TRANGTHAI = 1";
		
		
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		ResultSet rs = jdbcUtil.executeQuery(sql, maVung);
		try {
			while(rs.next()) {
				int id = rs.getInt("maSach");
				String tenSach = rs.getString("tenSach");
				int soLuong = rs.getInt("soLuong");
				BigDecimal giaBan = rs.getBigDecimal("giaBan");
				int namXB = rs.getInt("namXB");
				int maNXB = rs.getInt("maNXB");
				String anh = rs.getString("anh");
				SachDTO sach = new SachDTO(id, tenSach, soLuong, giaBan, namXB, maVung, maNXB, anh);
				result.add(sach);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jdbcUtil.Close();
		return result;
	}

	public String getPathByMa(int ma){
		String result = new String();
		String sql = "SELECT anh FROM SACH WHERE maSach = ? AND TRANGTHAI = 1";
		
		
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		ResultSet rs = jdbcUtil.executeQuery(sql, ma);
		try {
			while(rs.next()) {
				String anh = rs.getString("anh");
				result = anh;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jdbcUtil.Close();
		return result;
	}

	public String getTenByMa(int ma){
		String result = new String();
		String sql = "SELECT tenSach FROM SACH WHERE maSach = ? AND TRANGTHAI = 1";
		
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		ResultSet rs = jdbcUtil.executeQuery(sql, ma);
		try {
			while(rs.next()) {
				String ten = rs.getString("tenSach");
				result = ten;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jdbcUtil.Close();
		return result;
	}


}
