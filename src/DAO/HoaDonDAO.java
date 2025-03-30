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
        String sql = String.format(
            "INSERT INTO HOADON (ngayBan, tongTien, maTK, maPT, maKM, maKH) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
            t.getNgayBan(),
            t.getTongTien(),
            t.getMaTK(),
            t.getMaPT(),
            t.getMaKM(),
            t.getMaKH()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowInserted = jdbcUtil.executeUpdate(sql);
        int nextID = jdbcUtil.getAutoIncrement("HOADON");
        jdbcUtil.Close();
        t.setMaHD(nextID);
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String query = String.format("UPDATE HOADON SET trangThai = 0 WHERE maHD = '%s'", id);
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(HoaDonDTO t) {
        int rowUpdated = 0;
        String query = String.format(
            "UPDATE HOADON SET ngayBan = '%s', tongTien = '%s', maTK = '%s', maPT = '%s', maKM = '%s', maKH = '%s' WHERE maHD = '%s'",
            t.getNgayBan(),
            t.getTongTien(),
            t.getMaTK(),
            t.getMaPT(),
            t.getMaKM(),
            t.getMaKH(),
            t.getMaHD()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowUpdated;
    }

    public ArrayList<HoaDonDTO> getAll() {
        ArrayList<HoaDonDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM HOADON WHERE trangThai = 1";
        
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
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
            jdbcUtil.Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
