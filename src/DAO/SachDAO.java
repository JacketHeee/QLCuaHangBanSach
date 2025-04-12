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
		String sql = "INSERT INTO SACH (tenSach, soLuong, giaBan, namXB, maVung, maNXB) VALUES (?,?,?,?,?,?)";

		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		int nextID = jdbcUtil.getAutoIncrement("Sach");
		rowInserted = jdbcUtil.executeUpdate(
			sql,
			t.getTenSach(),
			t.getSoLuong(),
			t.getGiaBan(),
			t.getNamXB(), 
			t.getMaVung(),
			t.getMaNXB()
		);
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
		String query = "UPDATE SACH SET tenSach = ?, soLuong = ?, giaBan = ?, namXB = ?, maVung = ?, maNXB = ? WHERE maSach = ?";

		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		rowUpdated = jdbcUtil.executeUpdate(
			query,
			t.getTenSach(),
			t.getSoLuong(),
			t.getGiaBan(),
			t.getNamXB(),
			t.getMaVung(),
			t.getMaNXB(),
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
				SachDTO sach = new SachDTO(id, tenSach, soLuong, giaBan, namXB, maVung, maNXB);
				result.add(sach);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jdbcUtil.Close();
		return result;
	}
}
