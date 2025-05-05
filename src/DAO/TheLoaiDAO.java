package DAO;

import DTO.TheLoaiDTO;
import config.JDBCUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TheLoaiDAO implements DAOInterface<TheLoaiDTO> {
    private static TheLoaiDAO instance;
    
    private TheLoaiDAO() {}
    
    public static TheLoaiDAO getInstance() {
        if (instance == null) {
            instance = new TheLoaiDAO();
        }
        return instance;
    }
    
    @Override
    public int insert(TheLoaiDTO t) {
        String sql = "INSERT INTO THELOAI (tenTheloai) VALUES (?)";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("THELOAI");
        int rowInserted = jdbcUtil.executeUpdate(sql, t.getTenTheLoai());
        jdbcUtil.Close();
        t.setMaTheLoai(nextID);
        return rowInserted;
    }
    
    @Override
    public int delete(int id) {
        String query = "UPDATE THELOAI SET trangThai = 0 WHERE maTheloai = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int rowDeleted = jdbcUtil.executeUpdate(query, id);
        jdbcUtil.Close();
        return rowDeleted;
    }
    
    @Override
    public int update(TheLoaiDTO t) {
        String query = "UPDATE THELOAI SET tenTheloai = ? WHERE maTheloai = ?";    
    
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int rowUpdated = jdbcUtil.executeUpdate(query, t.getTenTheLoai(), t.getMaTheLoai());
        jdbcUtil.Close();
        return rowUpdated;
    }
    
    public ArrayList<TheLoaiDTO> getAll() {
        ArrayList<TheLoaiDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM THELOAI WHERE trangThai = 1";
        
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int id = rs.getInt("maTheloai");
                String tenTheLoai = rs.getString("tenTheloai");
                TheLoaiDTO theLoai = new TheLoaiDTO(id, tenTheLoai);
                result.add(theLoai);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public TheLoaiDTO getTheLoaiById(int maTheLoai) {
        TheLoaiDTO result = null;
        String sql = "SELECT * FROM THELOAI WHERE TRANGTHAI = 1 and maTheLoai = " + maTheLoai;
        
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int id = rs.getInt("maTheloai");
                String tenTheLoai = rs.getString("tenTheloai");
                TheLoaiDTO theLoai = new TheLoaiDTO(id, tenTheLoai);
                result = theLoai;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public ArrayList<String> getAllTenTheLoai(){
        ArrayList<String> result = new ArrayList<>();
        String sql = "SELECT tenTheLoai FROM THELOAI WHERE trangThai = 1";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                String tenTheLoai = rs.getString("tenTheloai");
                result.add(tenTheLoai);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public int getMaByTen(String ten){
        int result = -1;
        String sql = "SELECT maTheLoai FROM THELOAI WHERE tenTheLoai = ? AND trangThai = 1";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, ten);
        try {
            while (rs.next()) {
                result = rs.getInt("maTheLoai");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public String getTenByMa(int maTL){
        String result = new String();
        String sql = "SELECT tenTheLoai FROM THELOAI WHERE maTheLoai = ? AND trangThai = 1";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maTL);
        try {
            while (rs.next()) {
                result = rs.getString("tenTheLoai");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }
}
