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
		String sql = String.format(
			"INSERT INTO SACH (tenSach, giaBan, namXB, maVung, maNXB) VALUES ('%s','%s','%s','%s','%s')",
			t.getTenSach(),
			t.getGiaBan(),
			t.getNamXB(),
			t.getMaVung(),
			t.getMaNXB()
		 );
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		int nextID = jdbcUtil.getAutoIncrement("Sach");
		rowInserted = jdbcUtil.executeUpdate(sql);
		jdbcUtil.Close();
		t.setMaSach(nextID);
		return rowInserted;
	}

	@Override
	public int delete(int id) {
		int rowDeleted = 0;
		String query = String.format("UPDATE SACH SET TRANGTHAI = 0 WHERE MASACH = '%s'", id + "");
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		rowDeleted =  jdbcUtil.executeUpdate(query);
		jdbcUtil.Close();
		return(rowDeleted);
	}

	@Override
	public int update(SachDTO t) {
		// String sql = "UPDATE SACH SET tenSach = ?, giaBan = ?, namXB = ?, maVung = ?, maNXB = ? WHERE maSach = ?";
		int rowUpdated = 0;
		String query = String.format(
			"UPDATE SACH SET tenSach = '%s', giaBan = '%s', namXB = '%s', maVung = '%s', maNXB = '%s' WHERE maSach = '%s'",
			t.getTenSach(),
			t.getGiaBan() + "",
			t.getNamXB() + "",
			t.getMaVung() + "",
			t.getMaNXB() + "",
			t.getMaSach() + ""
		);
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		rowUpdated = jdbcUtil.executeUpdate(query);
		jdbcUtil.Close();
		return rowUpdated;
	}

	// @Override
	public ArrayList<SachDTO> getAll() {
		ArrayList<SachDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM SACH WHERE TRANGTHAI = 1";
		
		try {
			JDBCUtil jdbcUtil = new JDBCUtil();
			jdbcUtil.Open();
			ResultSet rs = jdbcUtil.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("maSach");
				String tenSach = rs.getString("tenSach");
				BigDecimal giaBan = rs.getBigDecimal("giaBan");
				int soLuongTon = rs.getInt("soLuongTon");
				int namXB = rs.getInt("namXB");
				int maVung = rs.getInt("maVung");
				int maNXB = rs.getInt("maNXB");
				SachDTO sach = new SachDTO(id, tenSach, giaBan, soLuongTon, namXB, maVung, maNXB);
				result.add(sach);
			}
			jdbcUtil.Close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
