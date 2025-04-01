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
        String sql = String.format(
            "INSERT INTO PHUONGTHUC_TT (tenPTTT) VALUES ('%s')",
            t.getTenPTTT()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("PHUONGTHUC_TT");
        rowInserted = jdbcUtil.executeUpdate(sql);
        jdbcUtil.Close();
        t.setMaPT(nextID);
        return rowInserted;
    }
    
    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String query = String.format("UPDATE PHUONGTHUC_TT SET TRANGTHAI = 0 WHERE maPT = '%s'", id);
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowDeleted;
    }
    
    @Override
    public int update(PhuongThucTTDTO t) {
        int rowUpdated = 0;
        String query = String.format(
            "UPDATE PHUONGTHUC_TT SET tenPTTT = '%s' WHERE maPT = '%s'",
            t.getTenPTTT(),
            t.getMaPT()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowUpdated;
    }
    
    public ArrayList<PhuongThucTTDTO> getAll() {
        ArrayList<PhuongThucTTDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM PHUONGTHUC_TT WHERE TRANGTHAI = 1";
        
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("maPT");
                String tenPTTT = rs.getString("tenPTTT");
                PhuongThucTTDTO phuongThucTT = new PhuongThucTTDTO(id, tenPTTT);
                result.add(phuongThucTT);
            }
            jdbcUtil.Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}