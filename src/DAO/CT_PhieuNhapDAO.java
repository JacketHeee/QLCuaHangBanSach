package DAO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.CT_PhieuNhapDTO;
import config.JDBCUtil;

public class CT_PhieuNhapDAO implements DAOInterface<CT_PhieuNhapDTO> {
    private static CT_PhieuNhapDAO instance;
    private CT_PhieuNhapDAO() {}
    
    public static CT_PhieuNhapDAO getInstance() {
        if (instance == null) {
            instance = new CT_PhieuNhapDAO();
        }
        return instance;
    }
    
    @Override
    public int insert(CT_PhieuNhapDTO ctpn) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public int update(CT_PhieuNhapDTO ctpn) {
        return 0;
    }

    public ArrayList<CT_PhieuNhapDTO> getAll() {
        ArrayList<CT_PhieuNhapDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM CT_PHIEUNHAP";
        
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
            while (rs.next()) {
                String maSach = rs.getString("maSach");
                int maNhap = rs.getInt("maNhap");
                int soLuongNhap = rs.getInt("soLuongNhap");
                BigDecimal giaNhap = rs.getBigDecimal("giaNhap");
                
                CT_PhieuNhapDTO ctpn = new CT_PhieuNhapDTO(maSach, maNhap, soLuongNhap, giaNhap);
                result.add(ctpn);
            }
            jdbcUtil.Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //     public static void main(String[] args) {
    //     CT_PhieuNhapDAO cT_PhieuNhapDAO = CT_PhieuNhapDAO.getInstance();
    //     ArrayList<CT_PhieuNhapDTO> list = cT_PhieuNhapDAO.getAll();
    //     for(CT_PhieuNhapDTO i : list){
    //         System.out.println(i.getMaNhap() + "\t" + i.getmaSach() + "\t" + i.getSoLuongNhap() + "\t" + i.getGiaNhap());
    //     }
    // }
}
