package DAO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.NhanVienDTO;
import config.JDBCUtil;

public class NhanVienDAO implements DAOInterface<NhanVienDTO> {
    private static NhanVienDAO instance;
    private NhanVienDAO() {}
    
    public static NhanVienDAO getInstance() {
        if (instance == null) {
            instance = new NhanVienDAO();
        }
        return instance;
    }
    
    @Override
    public int insert(NhanVienDTO nv) {
        int rowInserted = 0;
        String sql = String.format(
            "INSERT INTO NHANVIEN (hoTen, ngaySinh, gioiTinh, soDT, maTK) VALUES ('%s', '%s', '%s', '%s', '%d')",
            nv.getHoTen(),
            nv.getNgaySinh().toString(),
            nv.getGioiTinh(),
            nv.getSoDT(),
            nv.getMaTK()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowInserted = jdbcUtil.executeUpdate(sql);
        int nextID = jdbcUtil.getAutoIncrement("NhanVien");
        jdbcUtil.Close();
        nv.setMaNV(nextID);
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String query = String.format("UPDATE NHANVIEN SET TRANGTHAI = 0 WHERE maNV = '%d'", id);
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(NhanVienDTO nv) {
        int rowUpdated = 0;
        String query = String.format(
            "UPDATE NHANVIEN SET hoTen = '%s', ngaySinh = '%s', gioiTinh = '%s', soDT = '%s', maTK = '%d' WHERE maNV = '%d'",
            nv.getHoTen(),
            nv.getNgaySinh().toString(),
            nv.getGioiTinh(),
            nv.getSoDT(),
            nv.getMaTK(),
            nv.getMaNV()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowUpdated;
    }

    public ArrayList<NhanVienDTO> getAll() {
        ArrayList<NhanVienDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM NHANVIEN WHERE TRANGTHAI = 1";
        
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
            while (rs.next()) {
                int maNV = rs.getInt("maNV");
                String hoTen = rs.getString("hoTen");
                Date ngaySinh = rs.getDate("ngaySinh");
                String gioiTinh = rs.getString("gioiTinh");
                String soDT = rs.getString("soDT");
                int maTK = rs.getInt("maTK");
                
                NhanVienDTO nv = new NhanVienDTO(maNV, hoTen, ngaySinh, gioiTinh, soDT, maTK);
                result.add(nv);
            }
            jdbcUtil.Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}