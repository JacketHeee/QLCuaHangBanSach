package DAO;

import DTO.SachDTO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import config.JDBCUtil;

public class SachDAO implements DAOInterface<SachDTO>{

	// int maSach, String tenSach, int soLuong, BigDecimal giaBan, BigDecimal giaNhap, int namXB,
			// int maVung, int maNXB

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
			"INSERT INTO SACH (tenSach, soLuong, giaNhap, giaBan, namXB, maVung, maNXB) VALUES ('%s','%s','%s','%s','%s','%s','%s')",
			t.getTenSach(),
			t.getSoLuong() + "",
			t.getGiaNhap() + "",
			t.getGiaBan() + "",
			t.getNamXB() + "", 
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
		int rowUpdated = 0;
		String query = String.format(
			"UPDATE SACH SET tenSach = '%s', soLuong = '%s', giaNhap = '%s', giaBan = '%s', namXB = '%s', maVung = '%s', maNXB = '%s' WHERE maSach = '%s'",
			t.getTenSach(),
			t.getSoLuong() + "",
			t.getGiaNhap() + "",
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
				int soLuong = rs.getInt("soLuong");
				BigDecimal giaNhap = rs.getBigDecimal("giaNhap");
				BigDecimal giaBan = rs.getBigDecimal("giaBan");
				int namXB = rs.getInt("namXB");
				int maVung = rs.getInt("maVung");
				int maNXB = rs.getInt("maNXB");
				SachDTO sach = new SachDTO(id, tenSach, soLuong, giaNhap, giaBan, namXB, maVung, maNXB);
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
