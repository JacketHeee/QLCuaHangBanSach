package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ChiTietQuyenDTO;
import config.JDBCUtil;

public class ChiTietQuyenDAO implements DAOInterface<ChiTietQuyenDTO> {
    private static ChiTietQuyenDAO instance;
    private ChiTietQuyenDAO() {}
    
    public static ChiTietQuyenDAO getInstance() {
        if (instance == null) {
            instance = new ChiTietQuyenDAO();
        }
        return instance;
    }
    
    @Override
    public int insert(ChiTietQuyenDTO ctq) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public int update(ChiTietQuyenDTO ctq) {
        return 0;
    }

    public ArrayList<ChiTietQuyenDTO> getAll() {
        ArrayList<ChiTietQuyenDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETQUYEN";
        
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
            while (rs.next()) {
                int maRole = rs.getInt("maRole");
                int maChucNang = rs.getInt("maChucNang");
                String hanhDong = rs.getString("hanhDong");
                
                ChiTietQuyenDTO ctq = new ChiTietQuyenDTO(maRole, maChucNang, hanhDong);
                result.add(ctq);
            }
            jdbcUtil.Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
