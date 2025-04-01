package DAO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import DTO.KhuyenMaiDTO;
import config.JDBCUtil;

public class KhuyenMaiDAO implements DAOInterface<KhuyenMaiDTO> {
    private static KhuyenMaiDAO instance;
    private KhuyenMaiDAO() {}
    
    public static KhuyenMaiDAO getInstance() {
        if (instance == null) {
            instance = new KhuyenMaiDAO();
        }
        return instance;
    }
    
    @Override
    public int insert(KhuyenMaiDTO km) {
        int rowInserted = 0;
        String sql = String.format(
            "INSERT INTO KHUYENMAI (tenKM, dieuKienGiam, giaTriGiam, ngayBatDau, ngayKetThuc) VALUES ('%s', '%s', '%s', '%s', '%s')",
            km.getTenKM(),
            km.getDieuKienGiam(),
            km.getGiaTriGiam(),
            km.getNgayBatDau(),
            km.getNgayKetThuc()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("KhuyenMai");
        rowInserted = jdbcUtil.executeUpdate(sql);
        jdbcUtil.Close();
        km.setMaKM(nextID);
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String query = String.format("UPDATE KHUYENMAI SET TRANGTHAI = 0 WHERE maKM = '%d'", id);
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(KhuyenMaiDTO km) {
        int rowUpdated = 0;
        String query = String.format(
            "UPDATE KHUYENMAI SET tenKM = '%s', dieuKienGiam = '%s', giaTriGiam = '%s', ngayBatDau = '%s', ngayKetThuc = '%s' WHERE maKM = '%d'",
            km.getTenKM(),
            km.getDieuKienGiam(),
            km.getGiaTriGiam(),
            km.getNgayBatDau(),
            km.getNgayKetThuc(),
            km.getMaKM()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowUpdated;
    }

    public ArrayList<KhuyenMaiDTO> getAll() {
        ArrayList<KhuyenMaiDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM KHUYENMAI WHERE TRANGTHAI = 1";
        
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
            while (rs.next()) {
                int maKM = rs.getInt("maKM");
                String tenKM = rs.getString("tenKM");
                String dieuKienGiam = rs.getString("dieuKienGiam");
                BigDecimal giaTriGiam = rs.getBigDecimal("giaTriGiam");
                LocalDateTime ngayBatDau = rs.getTimestamp("ngayBatDau").toLocalDateTime();
                LocalDateTime ngayKetThuc = rs.getTimestamp("ngayKetThuc").toLocalDateTime();
                
                KhuyenMaiDTO km = new KhuyenMaiDTO(maKM, tenKM, dieuKienGiam, giaTriGiam, ngayBatDau, ngayKetThuc);
                result.add(km);
            }
            jdbcUtil.Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}