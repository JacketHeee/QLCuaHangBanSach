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
        int rowInserted = 0;
        String sql = "INSERT INTO CT_PHIEUNHAP (maSach, maNhap, soLuongNhap, giaNhap) VALUES (?, ?, ?, ?)";
            
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowInserted = jdbcUtil.executeUpdate(
            sql
            , ctpn.getmaSach()
            , ctpn.getMaNhap()
            , ctpn.getSoLuongNhap() + ""
            , ctpn.getGiaNhap() + ""
        );
        jdbcUtil.Close();
        return rowInserted;
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
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int maSach = rs.getInt("maSach");
                int maNhap = rs.getInt("maNhap");
                int soLuongNhap = rs.getInt("soLuongNhap");
                BigDecimal giaNhap = rs.getBigDecimal("giaNhap");
                
                CT_PhieuNhapDTO ctpn = new CT_PhieuNhapDTO(maSach, maNhap, soLuongNhap, giaNhap);
                result.add(ctpn);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public ArrayList<CT_PhieuNhapDTO> getListCTPNByMaPN(int maNhap){
        ArrayList<CT_PhieuNhapDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM CT_PHIEUNHAP WHERE maNhap = ?";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maNhap);
        try {
            while (rs.next()) {
                int maSach = rs.getInt("maSach");
                int soLuongNhap = rs.getInt("soLuongNhap");
                BigDecimal giaNhap = rs.getBigDecimal("giaNhap");
                
                CT_PhieuNhapDTO ctpn = new CT_PhieuNhapDTO(maSach, maNhap, soLuongNhap, giaNhap);
                result.add(ctpn);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

}
