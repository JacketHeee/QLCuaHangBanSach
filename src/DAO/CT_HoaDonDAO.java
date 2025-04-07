package DAO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.CT_HoaDonDTO;
import config.JDBCUtil;

public class CT_HoaDonDAO implements DAOInterface<CT_HoaDonDTO> {
    private static CT_HoaDonDAO instance;
    private CT_HoaDonDAO() {}
    
    public static CT_HoaDonDAO getInstance() {
        if (instance == null) {
            instance = new CT_HoaDonDAO();
        }
        return instance;
    }
    
    @Override
    public int insert(CT_HoaDonDTO cthd) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public int update(CT_HoaDonDTO cthd) {
        return 0;
    }

    public ArrayList<CT_HoaDonDTO> getAll() {
        ArrayList<CT_HoaDonDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM CT_HOADON";
        
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
            while (rs.next()) {
                String maVach = rs.getString("maVach");
                int maHD = rs.getInt("maHD");
                int soLuong = rs.getInt("soLuong");
                BigDecimal giaBan = rs.getBigDecimal("giaBan");
                
                CT_HoaDonDTO cthd = new CT_HoaDonDTO(maVach, maHD, soLuong, giaBan);
                result.add(cthd);
            }
            jdbcUtil.Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // public static void main(String[] args) {
    //     CT_HoaDonDAO cT_HoaDonDAO = CT_HoaDonDAO.getInstance();
    //     ArrayList<CT_HoaDonDTO> list = cT_HoaDonDAO.getAll();
    //     for(CT_HoaDonDTO i : list){
    //         System.out.println(i.getMaHD() + "\t" + i.getMaVach() + "\t" + i.getSoLuong() + "\t" + i.getGiaBan());
    //     }
    // }
}