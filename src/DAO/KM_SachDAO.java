package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.KM_SachDTO;
import config.JDBCUtil;

public class KM_SachDAO implements DAOInterface<KM_SachDTO> {
    private static KM_SachDAO instance;
    private KM_SachDAO() {}
    
    public static KM_SachDAO getInstance() {
        if (instance == null) {
            instance = new KM_SachDAO();
        }
        return instance;
    }
    
    @Override
    public int insert(KM_SachDTO kmSach) {
        int rowInserted = 0;
        String sql = "INSERT INTO KM_SACH (maKM, maSach) VALUES (?, ?)";
            
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowInserted = jdbcUtil.executeUpdate(sql, kmSach.getMaKM(), kmSach.getmaSach());
        jdbcUtil.Close();
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        String query = "UPDATE KM_SACH SET TRANGTHAI = 0 WHERE maKM = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        jdbcUtil.executeUpdate(query, id);
        jdbcUtil.Close();
        return 1;
    }

    public int delete(int maSach, int maKM){
        int rowDeleted = 0;
        String query = "UPDATE KM_SACH SET TRANGTHAI = 0 WHERE maSach = ? AND maKM = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query, maSach, maKM);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(KM_SachDTO kmSach) {
        return 0;
    }

    public ArrayList<KM_SachDTO> getAll() {
        ArrayList<KM_SachDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM KM_SACH WHERE TRANGTHAI = 1";
        
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int maKM = rs.getInt("maKM");
                int maSach = rs.getInt("maSach");
                
                KM_SachDTO kmSach = new KM_SachDTO(maKM, maSach);
                result.add(kmSach);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public ArrayList<Integer> getAllMaKMByMaSach(int maSach){
        ArrayList<Integer> result = new ArrayList<>();
        String sql = "SELECT maKM FROM KM_SACH WHERE maSach = ? AND TRANGTHAI = 1 ";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maSach);
        try {
            while (rs.next()) {
                int maKM = rs.getInt("maKM");
                result.add(maKM);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

}
