package DAO;

import DTO.PhienBanSachDTO;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import config.JDBCUtil;

public class PhienBanSachDAO implements DAOInterface<PhienBanSachDTO>{

	private static PhienBanSachDAO instance;
	private PhienBanSachDAO() {}
	
	public static PhienBanSachDAO getInstance() {
		if(instance == null) {
			instance = new PhienBanSachDAO();
		}
		return(instance);
	}
	@Override
	public int insert(PhienBanSachDTO t) {  // CẦN THÊM HÀM CHECK MÃ VẠCH ĐÃ TỒN TẠI
		int rowInserted = 0;
		String sql = String.format(
			"INSERT INTO PHIENBANSACH (maVach, giaNhap, giaBan, soLuongTon, maSach) VALUES ('%s','%s','%s','%s','%s')",
			t.getMaVach(),
			t.getGiaNhap() + "",
			t.getGiaBan() + "",
			t.getSoLuong(),
            t.getMaSach()
		 );
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		rowInserted = jdbcUtil.executeUpdate(sql);
		jdbcUtil.Close();
		return rowInserted;
	}

	@Override
	public int delete(int maVach) {
        return(0);
	}

    public int delete(String maVach) {
		int rowDeleted = 0;
		String query = String.format("UPDATE PHIENBANSACH SET TRANGTHAI = 0 WHERE maVach = '%s'", maVach);
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		rowDeleted =  jdbcUtil.executeUpdate(query);
		jdbcUtil.Close();
		return(rowDeleted);
	}

	@Override
	public int update(PhienBanSachDTO t) {
        // maVach, giaNhap, giaBan, soLuongTon, maSach
		int rowUpdated = 0;
		String query = String.format(
			"UPDATE PHIENBANSACH SET giaNhap = '%s', giaBan = '%s'  WHERE maVach = '%s'",
			t.getGiaNhap() + "",
			t.getGiaBan() + "",
			t.getMaVach() + ""
		);
		JDBCUtil jdbcUtil = new JDBCUtil();
		jdbcUtil.Open();
		rowUpdated = jdbcUtil.executeUpdate(query);
		jdbcUtil.Close();
		return rowUpdated;
	}

	// @Override
	public ArrayList<PhienBanSachDTO> getAll() {
		ArrayList<PhienBanSachDTO> result = new ArrayList<>();
		String sql = "SELECT * FROM PHIENBANSACH WHERE TRANGTHAI = 1";
		
		try {
			JDBCUtil jdbcUtil = new JDBCUtil();
			jdbcUtil.Open();
			ResultSet rs = jdbcUtil.executeQuery(sql);
			while(rs.next()) {
				String maVach = rs.getString("maVach");
				BigDecimal giaNhap = rs.getBigDecimal("giaNhap"); 
                BigDecimal giaBan = rs.getBigDecimal("giaBan");
                int soLuong = rs.getInt("soLuongTon");
                int maSach = rs.getInt("maSach");
				PhienBanSachDTO phienBanSach = new PhienBanSachDTO(maVach, giaNhap, giaBan, soLuong, maSach);
				result.add(phienBanSach);
			}
			jdbcUtil.Close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

    public int getSoLuongSachByMaSach(int i){
        int result = 0;
        String sql = String.format("SELECT SUM(soLuongTon) AS tong FROM PHIENBANSACH WHERE maSach = '%s' GROUP BY maSach", i + "");
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
            while(rs.next()){
                result = rs.getInt("tong");
            }
            jdbcUtil.Close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return(result);
    }


    public static void main(String[] args) {
        PhienBanSachDAO phienBanSachDAO = PhienBanSachDAO.getInstance();
        ArrayList<PhienBanSachDTO> list = phienBanSachDAO.getAll();
        for(PhienBanSachDTO i : list){
            System.out.println(i.getMaVach() + "\t" + i.getGiaNhap() + "\t" + i.getGiaBan() + "\t" + i.getSoLuong() + "\t" + i.getMaSach());
        }
    //     System.out.println("getAll done");

        // BigDecimal giaNhap = new BigDecimal(2000);
        // BigDecimal giaBan = new BigDecimal(3000);
        // PhienBanSachDTO phienBan = new PhienBanSachDTO("5", giaNhap, giaBan, 2, 1);
        // PhienBanSachDTO phienBan2 = new PhienBanSachDTO("6", giaNhap, giaBan, 2, 1);
        // phienBanSachDAO.insert(phienBan);
        // phienBanSachDAO.insert(phienBan2);
        // for(PhienBanSachDTO i : list){
        //     System.out.println(i.getMaVach() + "\t" + i.getGiaNhap() + "\t" + i.getGiaBan() + "\t" + i.getSoLuong() + "\t" + i.getMaSach());
        // }
        // System.out.println("insert done");

        // for(PhienBanSachDTO i : list){
        //     System.out.println(i.getMaVach() + "\t" + i.getGiaNhap() + "\t" + i.getGiaBan() + "\t" + i.getSoLuong() + "\t" + i.getMaSach());
        // }
        // if(phienBanSachDAO.delete("5") != 0){
        //     System.out.println("delete done");
        // }

        // BigDecimal giaNhap3 = new BigDecimal(123);
        // BigDecimal giaBan3 = new BigDecimal(321);
        // PhienBanSachDTO phienBan3 = new PhienBanSachDTO("6", giaNhap3, giaBan3, 2, 1);
        // phienBanSachDAO.update(phienBan3);
        // for(PhienBanSachDTO i : list){
        //     System.out.println(i.getMaVach() + "\t" + i.getGiaNhap() + "\t" + i.getGiaBan() + "\t" + i.getSoLuong() + "\t" + i.getMaSach());
        // }
        // System.out.println("update done");

        System.out.println(phienBanSachDAO.getSoLuongSachByMaSach(1));
    }
}
