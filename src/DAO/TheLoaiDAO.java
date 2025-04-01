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
        String sql = String.format(
            "INSERT INTO THELOAI (tenTheloai) VALUES ('%s')", 
            t.getTenTheLoai()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("THELOAI");
        int rowInserted = jdbcUtil.executeUpdate(sql);
        jdbcUtil.Close();
        t.setMaTheLoai(nextID);
        return rowInserted;
    }
    
    @Override
    public int delete(int id) {
        String query = String.format("UPDATE THELOAI SET trangThai = 0 WHERE maTheloai = %d", id);
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int rowDeleted = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowDeleted;
    }
    
    @Override
    public int update(TheLoaiDTO t) {
        String query = String.format(
            "UPDATE THELOAI SET tenTheloai = '%s' WHERE maTheloai = %d",
            t.getTenTheLoai(), t.getMaTheLoai()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int rowUpdated = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowUpdated;
    }
    
    public ArrayList<TheLoaiDTO> getAll() {
        ArrayList<TheLoaiDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM THELOAI WHERE trangThai = 1";
        
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("maTheloai");
                String tenTheLoai = rs.getString("tenTheloai");
                TheLoaiDTO theLoai = new TheLoaiDTO(id, tenTheLoai);
                result.add(theLoai);
            }
            jdbcUtil.Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
