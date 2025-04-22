package DAO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import DTO.HoaDonDTO;
import config.JDBCUtil;

public class HoaDonDAO implements DAOInterface<HoaDonDTO> {
    private static HoaDonDAO instance;
    private HoaDonDAO() {}
    
    public static HoaDonDAO getInstance() {
        if (instance == null) {
            instance = new HoaDonDAO();
        }
        return instance;
    }

    @Override
    public int insert(HoaDonDTO t) {
        int rowInserted = 0;
        String sql = "INSERT INTO HOADON (maHD,ngayBan, tongTien, maTK, maPT, maKM, maKH) VALUES (?,?,?,?,?,?,?)";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("HOADON");
        rowInserted = jdbcUtil.executeUpdate(
            sql,
            nextID,
            t.getNgayBan(),
            t.getTongTien(),
            t.getMaTK(),
            t.getMaPT(),
            t.getMaKM(),
            t.getMaKH()
        );
        jdbcUtil.Close();
        t.setMaHD(nextID);
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String query = "UPDATE HOADON SET trangThai = 0 WHERE maHD = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query, id);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(HoaDonDTO t) {
        int rowUpdated = 0;
        String query = "UPDATE HOADON SET ngayBan = ?, tongTien = ?, maTK = ?, maPT = ?, maKM = ?, maKH = ? WHERE maHD = ?";
            // t.getNgayBan(),
            // t.getTongTien(),
            // t.getMaTK(),
            // t.getMaPT(),
            // t.getMaKM(),
            // t.getMaKH(),
            // t.getMaHD()
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(
            query,
            t.getNgayBan(),
            t.getTongTien(),
            t.getMaTK(),
            t.getMaPT(),
            t.getMaKM(),
            t.getMaKH(),
            t.getMaHD()
        );
        jdbcUtil.Close();
        return rowUpdated;
    }

    public ArrayList<HoaDonDTO> getAll() {
        ArrayList<HoaDonDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM HOADON WHERE trangThai = 1";
        
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int id = rs.getInt("maHD");
                LocalDateTime ngayBan = rs.getTimestamp("ngayBan").toLocalDateTime();
                BigDecimal tongTien = rs.getBigDecimal("tongTien");
                int maTK = rs.getInt("maTK");
                int maPT = rs.getInt("maPT");
                int maKM = rs.getInt("maKM");
                int maKH = rs.getInt("maKH");
                HoaDonDTO hoaDon = new HoaDonDTO(id, ngayBan, tongTien, maTK, maPT, maKM, maKH);
                result.add(hoaDon);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public int getNextID(){
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("HOADON");
        jdbcUtil.Close();
        return(nextID);
    }
}
