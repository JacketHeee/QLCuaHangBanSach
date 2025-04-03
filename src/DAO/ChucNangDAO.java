package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ChucNangDTO;
import config.JDBCUtil;

public class ChucNangDAO implements DAOInterface<ChucNangDTO> {
    private static ChucNangDAO instance;
    private ChucNangDAO() {}
    
    public static ChucNangDAO getInstance() {
        if (instance == null) {
            instance = new ChucNangDAO();
        }
        return instance;
    }
    
    @Override
    public int insert(ChucNangDTO cn) {
        int rowInserted = 0;
        String sql = String.format(
            "INSERT INTO CHUCNANG (tenChucNang) VALUES ('%s')",
            cn.getTenChucNang()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("ChucNang");
        rowInserted = jdbcUtil.executeUpdate(sql);
        jdbcUtil.Close();
        cn.setMaChucNang(nextID);
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String query = String.format("UPDATE CHUCNANG SET TRANGTHAI = 0 WHERE maChucNang = '%d'", id);
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(ChucNangDTO cn) {
        int rowUpdated = 0;
        String query = String.format(
            "UPDATE CHUCNANG SET tenChucNang = '%s' WHERE maChucNang = '%d'",
            cn.getTenChucNang(),
            cn.getMaChucNang()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowUpdated;
    }

    public ArrayList<ChucNangDTO> getAll() {
        ArrayList<ChucNangDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM CHUCNANG WHERE TRANGTHAI = 1";
        
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
            while (rs.next()) {
                int maChucNang = rs.getInt("maChucNang");
                String tenChucNang = rs.getString("tenChucNang");
                
                ChucNangDTO cn = new ChucNangDTO(maChucNang, tenChucNang);
                result.add(cn);
            }
            jdbcUtil.Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
