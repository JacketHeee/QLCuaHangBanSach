package DAO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import DTO.PhieuNhapDTO;
import config.JDBCUtil;

public class PhieuNhapDAO implements DAOInterface<PhieuNhapDTO> {
    private static PhieuNhapDAO instance;
    private PhieuNhapDAO() {}

    public static PhieuNhapDAO getInstance() {
        if (instance == null) {
            instance = new PhieuNhapDAO();
        }
        return instance;
    }

    @Override
    public int insert(PhieuNhapDTO t) {
        int rowInserted = 0;
        String sql = "INSERT INTO PHIEUNHAP (ngayNhap, tongTien, maNCC, maTK) VALUES (?,?,?,?)";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("PHIEUNHAP");
        rowInserted = jdbcUtil.executeUpdate(
            sql,
            t.getNgayNhap(),
            t.getTongTien(),
            t.getMaNCC(),
            t.getMaTK()
        );
        jdbcUtil.Close();
        t.setMaNhap(nextID);
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String sql = "UPDATE PHIEUNHAP SET trangThai = 0 WHERE maNhap = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(sql, id);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(PhieuNhapDTO t) {
        int rowUpdated = 0;
        String sql = "UPDATE PHIEUNHAP SET ngayNhap = '%s', tongTien = '%s', maNCC = '%s', maTK = '%s' WHERE maNhap = ?";

        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(
            sql,
            t.getNgayNhap(),
            t.getTongTien(),
            t.getMaNCC(),
            t.getMaTK(),
            t.getMaNhap()
        );
        jdbcUtil.Close();
        return rowUpdated;
    }

    public ArrayList<PhieuNhapDTO> getAll() {
        ArrayList<PhieuNhapDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM PHIEUNHAP WHERE trangThai = 1";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int maNhap = rs.getInt("maNhap");
                LocalDateTime ngayNhap = rs.getTimestamp("ngayNhap").toLocalDateTime();
                BigDecimal tongTien = rs.getBigDecimal("tongTien");
                int maNCC = rs.getInt("maNCC");
                int maTK = rs.getInt("maTK");
                PhieuNhapDTO phieuNhap = new PhieuNhapDTO(maNhap, ngayNhap, tongTien, maNCC, maTK);
                result.add(phieuNhap);
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
        int nextID = jdbcUtil.getAutoIncrement("PHIEUNHAP");
        jdbcUtil.Close();
        return(nextID);
    }


    public PhieuNhapDTO getInstanceByID(int maNhap){
        PhieuNhapDTO result = new PhieuNhapDTO();
        String sql = "SELECT * FROM PHIEUNHAP WHERE maNhap = ? AND trangThai = 1";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maNhap);
        try {
            while (rs.next()) {
                LocalDateTime ngayNhap = rs.getTimestamp("ngayNhap").toLocalDateTime();
                BigDecimal tongTien = rs.getBigDecimal("tongTien");
                int maNCC = rs.getInt("maNCC");
                int maTK = rs.getInt("maTK");
                PhieuNhapDTO phieuNhap = new PhieuNhapDTO(maNhap, ngayNhap, tongTien, maNCC, maTK);
                result = phieuNhap;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }
}