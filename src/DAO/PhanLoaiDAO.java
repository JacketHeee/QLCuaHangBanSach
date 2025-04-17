package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.PhanLoaiDTO;
import config.JDBCUtil;

public class PhanLoaiDAO implements DAOInterface<PhanLoaiDTO> {
    private static PhanLoaiDAO instance;
    private PhanLoaiDAO() {}
    
    public static PhanLoaiDAO getInstance() {
        if (instance == null) {
            instance = new PhanLoaiDAO();
        }
        return instance;
    }
    
    @Override
    public int insert(PhanLoaiDTO phanLoai) {
        int rowInserted = 0;
        String sql = "INSERT INTO PHANLOAI (maSach, maTheLoai) VALUES (?, ?)";
            
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowInserted = jdbcUtil.executeUpdate(sql, phanLoai.getMaSach(), phanLoai.getMaTheLoai());
        jdbcUtil.Close();
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    public int delete(int maSach, int maTL){
        int rowDeleted = 0;
        String query = "UPDATE PHANLOAI SET TRANGTHAI = 0 WHERE maSach = ? AND maTheLoai = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query, maSach, maTL);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(PhanLoaiDTO phanLoai) {
        return 0;
    }

    public ArrayList<PhanLoaiDTO> getAll() {
        ArrayList<PhanLoaiDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM PHANLOAI";
        
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int maSach = rs.getInt("maSach");
                int maTheLoai = rs.getInt("maTheLoai");
                
                PhanLoaiDTO phanLoai = new PhanLoaiDTO(maSach, maTheLoai);
                result.add(phanLoai);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public ArrayList<Integer> getAllMaTheLoaiByMaSach(int maSach){
        ArrayList<Integer> result = new ArrayList<>();
        String sql = "SELECT maTheLoai FROM PHANLOAI WHERE maSach = ?";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maSach);
        try {
            while (rs.next()) {
                int maTheloai = rs.getInt("maTheLoai");
                result.add(maTheloai);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }


    public static void main(String[] args) {
        System.out.println(new PhanLoaiDAO().delete(1, 5));
    }
}
