package DAO;

import DTO.NhanVienDTO;
import config.JDBCUtil;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        String sql = "INSERT INTO NHANVIEN (hoTen, ngaySinh, gioiTinh, soDT) VALUES (?,?,?,?)";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("NhanVien");
        rowInserted = jdbcUtil.executeUpdate(
            sql,
            nv.getHoTen(),
            nv.getNgaySinh(),
            nv.getGioiTinh(),
            nv.getSoDT()
        );
        jdbcUtil.Close();
        nv.setMaNV(nextID);
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String query = "UPDATE NHANVIEN SET TRANGTHAI = 0 WHERE maNV = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query, id);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(NhanVienDTO nv) {
        int rowUpdated = 0;
        String query = "UPDATE NHANVIEN SET hoTen = ?, ngaySinh = ?, gioiTinh = ?, soDT = ? WHERE maNV = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(
            query,
            nv.getHoTen(),
            nv.getNgaySinh().toString(),
            nv.getGioiTinh(),
            nv.getSoDT(),
            nv.getMaNV()
        );
        jdbcUtil.Close();
        return rowUpdated;
    }

    public ArrayList<NhanVienDTO> getAll() {
        ArrayList<NhanVienDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM NHANVIEN WHERE TRANGTHAI = 1"; 
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public NhanVienDTO SelectNhanVienByMaTK(int maTK){
        NhanVienDTO result = null;
        String sql = "SELECT * FROM NHANVIEN WHERE maTK = ? and TRANGTHAI = 1";

        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maTK);
        try {
            while(rs.next()){
                int maNV = rs.getInt("maNV");
                String hoTen = rs.getString("hoTen");
                Date ngaySinh = rs.getDate("ngaySinh");
                String gioiTinh = rs.getString("gioiTinh");
                String soDT = rs.getString("soDT");

                NhanVienDTO nv = new NhanVienDTO(maNV, hoTen, ngaySinh, gioiTinh, soDT, maTK);
                result = nv;
            }
            jdbcUtil.Close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }

    public ArrayList<String> getAllTenNVNotHaveAccount(){
        ArrayList<String> result = new ArrayList<>();
        String sql = "SELECT hoTen FROM NHANVIEN WHERE maTK IS NULL and TRANGTHAI = 1";

        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while(rs.next()){
                String hoTen = rs.getString("hoTen");
                result.add(hoTen);
            }
            jdbcUtil.Close();
        } catch (Exception e) {

        }
        return result;
    }

    public ArrayList<String> getAllTenNVHaveAccount(){
        ArrayList<String> result = new ArrayList<>();
        String sql = "SELECT hoTen FROM NHANVIEN WHERE maTK IS not NULL and TRANGTHAI = 1";

        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while(rs.next()){
                String hoTen = rs.getString("hoTen");
                result.add(hoTen);
            }
            jdbcUtil.Close();
        } catch (Exception e) {

        }
        return result;
    }
    
    public int getMaNVByTenNV(String tenNV){
        int result = -1;
        String sql = "SELECT maNV FROM nhanVien WHERE hoTen = ? and TRANGTHAI = 1";
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql, tenNV);
            while(rs.next()){
                result = rs.getInt("maNV");
            }
            jdbcUtil.Close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return(result);
    }

    public int setMaTK(int maNV, int maTK){
        int result = -1;
        String sql = "UPDATE NHANVIEN SET maTK = ? WHERE maNV = ?";
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            result = jdbcUtil.executeUpdate(sql, maTK, maNV);
            jdbcUtil.Close();
        } catch (Exception e) {
        }
        return(result);
    }

    public String getTenNVByMaTK(int maTK){
        String result = new String();
        String sql = "SELECT hoTen FROM nhanVien WHERE maTK = ? and TRANGTHAI = 1";
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql, maTK);
            while(rs.next()){
                result = rs.getString("hoTen");
            }
            jdbcUtil.Close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return(result);
    }

    public int getMaNVByMaTK(int maTK){
        int result = -1;
        String sql = "SELECT maNV FROM nhanVien WHERE maTK = ? and TRANGTHAI = 1";
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql, maTK);
            while(rs.next()){
                result = rs.getInt("maNV");
            }
            jdbcUtil.Close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return(result);
    }

    public NhanVienDTO getInstanceByMa(int maNV){
        NhanVienDTO result = new NhanVienDTO();
        String sql = "SELECT * FROM NHANVIEN WHERE MANV = ? AND TRANGTHAI = 1"; 
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maNV);
        try {
            while (rs.next()) {
                String hoTen = rs.getString("hoTen");
                Date ngaySinh = rs.getDate("ngaySinh");
                String gioiTinh = rs.getString("gioiTinh");
                String soDT = rs.getString("soDT");
                int maTK = rs.getInt("maTK");
                
                NhanVienDTO nv = new NhanVienDTO(maNV, hoTen, ngaySinh, gioiTinh, soDT, maTK);
                result = nv;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public ArrayList<String> getAllTenNVJoined(){
        ArrayList<String> result = new ArrayList<>();
        String sql = "SELECT hoTen FROM NHANVIEN WHERE TRANGTHAI = 1"; 
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                String hoTen = rs.getString("hoTen");
                result.add(hoTen);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }
        public String gettenNVByMaTK(int maTK) {
        String hoTen = null;
        String sql = "SELECT hoTen FROM nhanvien WHERE maTK = ? AND trangThai = 1";
    
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maTK);
    
        try {
            if (rs.next()) {
                hoTen = rs.getString("hoTen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        jdbcUtil.Close();
        return hoTen;
    }
}