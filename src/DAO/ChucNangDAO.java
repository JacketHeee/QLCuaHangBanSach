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
        String sql = "INSERT INTO CHUCNANG (tenChucNang) VALUES (?)";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("ChucNang");
        rowInserted = jdbcUtil.executeUpdate(sql, cn.getTenChucNang());
        jdbcUtil.Close();
        cn.setMaChucNang(nextID);
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String query = "UPDATE CHUCNANG SET TRANGTHAI = 0 WHERE maChucNang = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query, id);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(ChucNangDTO cn) {
        int rowUpdated = 0;
        String query = "UPDATE CHUCNANG SET tenChucNang = ? WHERE maChucNang = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(query,cn.getTenChucNang(),cn.getMaChucNang());
        jdbcUtil.Close();
        return rowUpdated;
    }

    public ArrayList<ChucNangDTO> getAll() {
        ArrayList<ChucNangDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM CHUCNANG WHERE TRANGTHAI = 1";
        
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int maChucNang = rs.getInt("maChucNang");
                String tenChucNang = rs.getString("tenChucNang");
                
                ChucNangDTO cn = new ChucNangDTO(maChucNang, tenChucNang);
                result.add(cn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public String getNameByMaCN(int maCN){
        String result = null;
        String sql = "SELECT tenChucNang FROM CHUCNANG WHERE maChucNang = ?";

        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maCN);
        try {
            while(rs.next()){
                String ten = rs.getString("tenChucNang");
                result = ten;
            }   
        } catch (Exception e) {
            // TODO: handle exception
        }
        jdbcUtil.Close();
        return(result);
    }

    public int getMaChucNangByTen(String tenCN){
        int result = -1;
        String sql = "SELECT maChucNang FROM CHUCNANG WHERE tenChucNang = ?";

        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, tenCN);
        try {
            while(rs.next()){
                int maCN = rs.getInt("maChucNang");
                result = maCN;
            }
            jdbcUtil.Close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return(result);
    }

}
