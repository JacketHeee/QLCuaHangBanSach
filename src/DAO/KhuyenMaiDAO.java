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
        String sql = "INSERT INTO KHUYENMAI (tenKM, dieuKienGiam, giaTriGiam, ngayBatDau, ngayKetThuc) VALUES (?,?,?,?,?)";

        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("KhuyenMai");
        rowInserted = jdbcUtil.executeUpdate(
            sql,
            km.getTenKM(),
            km.getDieuKienGiam(),
            km.getGiaTriGiam(),
            km.getNgayBatDau(),
            km.getNgayKetThuc()
        );
        jdbcUtil.Close();
        km.setMaKM(nextID);
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String query = "UPDATE KHUYENMAI SET TRANGTHAI = 0 WHERE maKM = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query, id);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(KhuyenMaiDTO km) {
        int rowUpdated = 0;
        String query = "UPDATE KHUYENMAI SET tenKM = ?, dieuKienGiam = ?, giaTriGiam = ?, ngayBatDau = ?, ngayKetThuc = ? WHERE maKM = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(
            query,
            km.getTenKM(),
            km.getDieuKienGiam(),
            km.getGiaTriGiam(),
            km.getNgayBatDau(),
            km.getNgayKetThuc(),
            km.getMaKM()
        );
        jdbcUtil.Close();
        return rowUpdated;
    }

    public ArrayList<KhuyenMaiDTO> getAll() {
        ArrayList<KhuyenMaiDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM KHUYENMAI WHERE TRANGTHAI = 1";
        
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
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
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public KhuyenMaiDTO getInstanceByMa(int ma){
        KhuyenMaiDTO result = new KhuyenMaiDTO();
        String sql = "SELECT * FROM KHUYENMAI WHERE maKM = ? AND TRANGTHAI = 1";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, ma);
        try {
            while (rs.next()) {
                String tenKM = rs.getString("tenKM");
                String dieuKienGiam = rs.getString("dieuKienGiam");
                BigDecimal giaTriGiam = rs.getBigDecimal("giaTriGiam");
                LocalDateTime ngayBatDau = rs.getTimestamp("ngayBatDau").toLocalDateTime();
                LocalDateTime ngayKetThuc = rs.getTimestamp("ngayKetThuc").toLocalDateTime();
                
                KhuyenMaiDTO km = new KhuyenMaiDTO(ma, tenKM, dieuKienGiam, giaTriGiam, ngayBatDau, ngayKetThuc);
                result = km;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }
    
    public int getMaKhuyenMaiByTen(String ten){
        int result = -1;
        String sql = "SELECT maKM FROM KHUYENMAI WHERE tenKM = ? AND TRANGTHAI = 1";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, ten);
        try {
            while (rs.next()) {
                int maKM = rs.getInt("maKM");
                result = maKM;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public String getTenByMaKhuyenMa(int maKM){
        String result = new String();
        String sql = "SELECT tenKM FROM KHUYENMAI WHERE maKM = ? AND TRANGTHAI = 1";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maKM);
        try {
            while (rs.next()) {
                String tenKM = rs.getString("tenKM");
                result = tenKM;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }


}