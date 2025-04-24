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
        int rowInserted = 0;
        String sql = "INSERT INTO CT_HOADON (maSach, maHD, soLuong, giaBan) VALUES (?, ?, ?, ?)";
            
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowInserted = jdbcUtil.executeUpdate(
            sql
            , cthd.getMaSach()
            , cthd.getMaHD()
            , cthd.getSoLuong()
            , cthd.getGiaBan()
        );
        jdbcUtil.Close();
        return rowInserted;
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
        
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int maSach = rs.getInt("maSach");
                int maHD = rs.getInt("maHD");
                int soLuong = rs.getInt("soLuong");
                BigDecimal giaBan = rs.getBigDecimal("giaBan");
                
                CT_HoaDonDTO cthd = new CT_HoaDonDTO(maSach, maHD, soLuong, giaBan);
                result.add(cthd);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public ArrayList<CT_HoaDonDTO> getListCTHDByMaHD(int maHD){
        ArrayList<CT_HoaDonDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM CT_HOADON WHERE maHD = ?";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maHD);
        try {
            while (rs.next()) {
                int maSach = rs.getInt("maSach");
                int soLuong = rs.getInt("soLuong");
                BigDecimal giaBan = rs.getBigDecimal("giaBan");
                
                CT_HoaDonDTO cthd = new CT_HoaDonDTO(maSach, maHD, soLuong, giaBan);
                result.add(cthd);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }


}