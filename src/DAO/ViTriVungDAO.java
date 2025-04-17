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
        String sql = "INSERT INTO VITRIVUNG (tenVung) VALUES (?)";

        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("VITRIVUNG");
        rowInserted = jdbcUtil.executeUpdate(sql, t.getTenVung());
        jdbcUtil.Close();
        t.setMaVung(nextID);
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String sql = "UPDATE VITRIVUNG SET trangThai = 0 WHERE maVung = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(sql, id);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(ViTriVungDTO t) {
        int rowUpdated = 0;
        String sql = "UPDATE VITRIVUNG SET tenVung = ? WHERE maVung = ?";

        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(
            sql,
            t.getTenVung(), 
            t.getMaVung()
        );
        jdbcUtil.Close();
        return rowUpdated;
    }

    public ArrayList<ViTriVungDTO> getAll() {
        ArrayList<ViTriVungDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM VITRIVUNG WHERE trangThai = 1";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int id = rs.getInt("maVung");
                String tenVung = rs.getString("tenVung");
                ViTriVungDTO vung = new ViTriVungDTO(id, tenVung);
                result.add(vung);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public String getTenVungByMaSach(int maSach){
        String result = new String();
        String sql = "SELECT tenVung FROM VITRIVUNG VT JOIN SACH S ON VT.maVung = S.maVung WHERE maSach = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maSach);
        try {
            while (rs.next()) {
                String tenVung = rs.getString("tenVung");
                result = tenVung;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }
}
