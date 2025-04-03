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
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public int update(DanhMuc_TGDTO dmtg) {
        return 0;
    }

    public ArrayList<DanhMuc_TGDTO> getAll() {
        ArrayList<DanhMuc_TGDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM DANHMUC_TG";
        
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
            while (rs.next()) {
                int maTacGia = rs.getInt("maTacGia");
                int maSach = rs.getInt("maSach");
                
                DanhMuc_TGDTO dmtg = new DanhMuc_TGDTO(maTacGia, maSach);
                result.add(dmtg);
            }
            jdbcUtil.Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}