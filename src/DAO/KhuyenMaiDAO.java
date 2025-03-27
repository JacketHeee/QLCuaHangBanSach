package DAO;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.KhuyenMaiDTO;
import utils.JDBCUtil;

public class KhuyenMaiDAO implements DAOInterface<KhuyenMaiDTO>{

	private static KhuyenMaiDAO instance;
	private KhuyenMaiDAO() {}
	
	public static KhuyenMaiDAO getInstance() {
		if(instance == null) {
			instance = new KhuyenMaiDAO();
		}
		return(instance);
	}
	
	@Override
	public int insert(KhuyenMaiDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(KhuyenMaiDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(KhuyenMaiDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<KhuyenMaiDTO> getAll() {
		Connection con = JDBCUtil.getConnection();
		ArrayList<KhuyenMaiDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM KHUYENMAI WHERE TRANGTHAI = 1";
		Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("maKM");
				String tenKM = rs.getString("tenKM");
				String dieuKienGiam = rs.getString("dieuKienGiam");
				BigDecimal giaTriGiam = rs.getBigDecimal("giaTriGiam");
				Date ngayBatDau = rs.getDate("ngayBatDau");
				Date ngayKetThuc = rs.getDate("ngayKetThuc");
				
				KhuyenMaiDTO khuyenMai = new KhuyenMaiDTO(id, tenKM, dieuKienGiam, giaTriGiam, ngayBatDau, ngayKetThuc);
				result.add(khuyenMai);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
