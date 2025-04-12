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
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
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
}
