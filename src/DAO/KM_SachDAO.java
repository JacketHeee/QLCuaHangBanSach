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
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public int update(KM_SachDTO kmSach) {
        return 0;
    }

    public ArrayList<KM_SachDTO> getAll() {
        ArrayList<KM_SachDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM KM_PHIENBANSACH";
        
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int maKM = rs.getInt("maKM");
                String maSach = rs.getString("maSach");
                
                KM_SachDTO kmSach = new KM_SachDTO(maKM, maSach);
                result.add(kmSach);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }
}
