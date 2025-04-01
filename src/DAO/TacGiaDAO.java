package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.TacGiaDTO;
import config.JDBCUtil;

public class TacGiaDAO implements DAOInterface<TacGiaDTO> {
    private static TacGiaDAO instance;
    private TacGiaDAO() {}
    
    public static TacGiaDAO getInstance() {
        if (instance == null) {
            instance = new TacGiaDAO();
        }
        return instance;
    }
    
    @Override
    public int insert(TacGiaDTO t) {
        int rowInserted = 0;
        String sql = String.format(
            "INSERT INTO TACGIA (tenTacGia) VALUES ('%s')",
            t.getTenTacGia()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("TACGIA");
        rowInserted = jdbcUtil.executeUpdate(sql);
        jdbcUtil.Close();
        t.setMaTacGia(nextID);
        return rowInserted;
    }
    
    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String query = String.format("UPDATE TACGIA SET TRANGTHAI = 0 WHERE maTacGia = '%s'", id);
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowDeleted;
    }
    
    @Override
    public int update(TacGiaDTO t) {
        int rowUpdated = 0;
        String query = String.format(
            "UPDATE TACGIA SET tenTacGia = '%s' WHERE maTacGia = '%s'",
            t.getTenTacGia(),
            t.getMaTacGia()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowUpdated;
    }
    
    public ArrayList<TacGiaDTO> getAll() {
        ArrayList<TacGiaDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM TACGIA WHERE TRANGTHAI = 1";
        
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("maTacGia");
                String tenTacGia = rs.getString("tenTacGia");
                TacGiaDTO tacGia = new TacGiaDTO(id, tenTacGia);
                result.add(tacGia);
            }
            jdbcUtil.Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
