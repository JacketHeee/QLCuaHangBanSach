package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.PhuongThucTTDTO;
import config.JDBCUtil;

public class PhuongThucTTDAO implements DAOInterface<PhuongThucTTDTO> {
    private static PhuongThucTTDAO instance;
    
    private PhuongThucTTDAO() {}
    
    public static PhuongThucTTDAO getInstance() {
        if (instance == null) {
            instance = new PhuongThucTTDAO();
        }
        return instance;
    }
    
    @Override
    public int insert(PhuongThucTTDTO t) {
        int rowInserted = 0;
        String sql = "INSERT INTO PHUONGTHUC_TT (tenPTTT) VALUES (?)";

        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("PHUONGTHUC_TT");
        rowInserted = jdbcUtil.executeUpdate(sql, t.getTenPTTT());
        jdbcUtil.Close();
        t.setMaPT(nextID);
        return rowInserted;
    }
    
    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String query = "UPDATE PHUONGTHUC_TT SET TRANGTHAI = 0 WHERE maPT = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query, id);
        jdbcUtil.Close();
        return rowDeleted;
    }
    
    @Override
    public int update(PhuongThucTTDTO t) {
        int rowUpdated = 0;
        String query = "UPDATE PHUONGTHUC_TT SET tenPTTT = ? WHERE maPT = ?";
 
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(
            query,
            t.getTenPTTT(),
            t.getMaPT()
        );
        jdbcUtil.Close();
        return rowUpdated;
    }
    
    public ArrayList<PhuongThucTTDTO> getAll() {
        ArrayList<PhuongThucTTDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM PHUONGTHUC_TT WHERE TRANGTHAI = 1";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int id = rs.getInt("maPT");
                String tenPTTT = rs.getString("tenPTTT");
                PhuongThucTTDTO phuongThucTT = new PhuongThucTTDTO(id, tenPTTT);
                result.add(phuongThucTT);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public PhuongThucTTDTO getPTTTById(int maPTTT) {
        PhuongThucTTDTO result = null;
        String sql = "SELECT * FROM PHUONGTHUC_TT WHERE TRANGTHAI = 1 and maPT = " + maPTTT;
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int id = rs.getInt("maPT");
                String tenPTTT = rs.getString("tenPTTT");
                PhuongThucTTDTO phuongThucTT = new PhuongThucTTDTO(id, tenPTTT);
                result = phuongThucTT;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }
}