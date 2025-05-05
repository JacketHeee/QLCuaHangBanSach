package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.DanhMuc_TGDTO;
import config.JDBCUtil;

public class DanhMuc_TGDAO implements DAOInterface<DanhMuc_TGDTO> {
    private static DanhMuc_TGDAO instance;
    private DanhMuc_TGDAO() {}
    
    public static DanhMuc_TGDAO getInstance() {
        if (instance == null) {
            instance = new DanhMuc_TGDAO();
        }
        return instance;
    }
    
    @Override
    public int insert(DanhMuc_TGDTO dmtg) {
        int rowInserted = 0;
        String sql = "INSERT INTO DANHMUC_TG (maSach, maTacGia) VALUES (?, ?)";
            
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowInserted = jdbcUtil.executeUpdate(sql, dmtg.getMaSach(), dmtg.getMaTacGia());
        jdbcUtil.Close();
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    public int delete(int maSach, int maTG){
        int rowDeleted = 0;
        String query = "UPDATE DANHMUC_TG SET TRANGTHAI = 0 WHERE maSach = ? AND maTacGia = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query, maSach, maTG);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(DanhMuc_TGDTO dmtg) {
        return 0;
    }

    public ArrayList<DanhMuc_TGDTO> getAll() {
        ArrayList<DanhMuc_TGDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM DANHMUC_TG WHERE TRANGTHAI = 1";
        
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int maTacGia = rs.getInt("maTacGia");
                int maSach = rs.getInt("maSach");
                
                DanhMuc_TGDTO dmtg = new DanhMuc_TGDTO(maTacGia, maSach);
                result.add(dmtg);
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public ArrayList<Integer> getAllMaTacGiaByMaSach(int maSach){
        ArrayList<Integer> result = new ArrayList<>();
        String sql = "SELECT maTacGia FROM DANHMUC_TG WHERE maSach = ? and TRANGTHAI = 1";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maSach);
        try {
            while (rs.next()) {
                int maTacGia = rs.getInt("maTacGia");
                result.add(maTacGia);
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

}