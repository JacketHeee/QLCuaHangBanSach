package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ViTriVungDTO;
import config.JDBCUtil;
public class ViTriVungDAO implements DAOInterface<ViTriVungDTO> {
    private static ViTriVungDAO instance;
    
    private ViTriVungDAO() {}
    
    public static ViTriVungDAO getInstance() {
        if (instance == null) {
            instance = new ViTriVungDAO();
        }
        return instance;
    }

    @Override
    public int insert(ViTriVungDTO t) {
        int rowInserted = 0;
        String sql = String.format(
            "INSERT INTO VITRIVUNG (tenVung) VALUES ('%s')",
            t.getTenVung()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("VITRIVUNG");
        rowInserted = jdbcUtil.executeUpdate(sql);
        jdbcUtil.Close();
        t.setMaVung(nextID);
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String sql = String.format("UPDATE VITRIVUNG SET trangThai = 0 WHERE maVung = '%s'", id);
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(sql);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(ViTriVungDTO t) {
        int rowUpdated = 0;
        String sql = String.format(
            "UPDATE VITRIVUNG SET tenVung = '%s' WHERE maVung = '%s'",
            t.getTenVung(), t.getMaVung()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(sql);
        jdbcUtil.Close();
        return rowUpdated;
    }

    public ArrayList<ViTriVungDTO> getAll() {
        ArrayList<ViTriVungDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM VITRIVUNG WHERE trangThai = 1";
        
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("maVung");
                String tenVung = rs.getString("tenVung");
                ViTriVungDTO vung = new ViTriVungDTO(id, tenVung);
                result.add(vung);
            }
            jdbcUtil.Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
