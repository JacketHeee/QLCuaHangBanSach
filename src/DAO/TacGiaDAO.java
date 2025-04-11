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
        String sql = "INSERT INTO TACGIA (tenTacGia) VALUES (?)";

        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("TACGIA");
        rowInserted = jdbcUtil.executeUpdate(
            sql,
            t.getTenTacGia()
        );
        jdbcUtil.Close();
        t.setMaTacGia(nextID);
        return rowInserted;
    }
    
    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String query = "UPDATE TACGIA SET TRANGTHAI = 0 WHERE maTacGia = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query, id);
        jdbcUtil.Close();
        return rowDeleted;
    }
    
    @Override
    public int update(TacGiaDTO t) {
        int rowUpdated = 0;
        String query ="UPDATE TACGIA SET tenTacGia = ? WHERE maTacGia = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(
            query,
            t.getTenTacGia(),
            t.getMaTacGia()
        );
        jdbcUtil.Close();
        return rowUpdated;
    }
    
    public ArrayList<TacGiaDTO> getAll() {
        ArrayList<TacGiaDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM TACGIA WHERE TRANGTHAI = 1";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int id = rs.getInt("maTacGia");
                String tenTacGia = rs.getString("tenTacGia");
                TacGiaDTO tacGia = new TacGiaDTO(id, tenTacGia);
                result.add(tacGia);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }
}
