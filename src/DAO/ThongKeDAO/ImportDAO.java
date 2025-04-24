package DAO.ThongKeDAO;

import config.JDBCUtil;
import dto.ImportDTO;
import dto.ImportStatsDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImportDAO {
    // Thống kê theo sách
    public List<ImportStatsDTO> getImportByBook(Date startDate, Date endDate) {
        String sql = "SELECT S.maSach, S.tenSach, COALESCE(SUM(CT.soLuongNhap), 0) AS soLuongNhap, COALESCE(SUM(CT.soLuongNhap * CT.giaNhap), 0) AS tongTien " +
                     "FROM CT_PHIEUNHAP CT " +
                     "JOIN PHIEUNHAP PN ON CT.maNhap = PN.maNhap " +
                     "JOIN SACH S ON CT.maSach = S.maSach " +
                     "WHERE PN.ngayNhap BETWEEN ? AND ? " +
                     "GROUP BY S.maSach, S.tenSach";
        List<ImportStatsDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
        try {
            while (rs.next()) {
                ImportStatsDTO stats = new ImportStatsDTO(
                    rs.getString("maSach"),
                    rs.getString("tenSach"),
                    rs.getInt("soLuongNhap"),
                    rs.getDouble("tongTien")
                );
                result.add(stats);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        } finally {
            jdbcUtil.Close();
        }
    }

    // Thống kê theo nhân viên
    public List<ImportStatsDTO> getImportByEmployee(Date startDate, Date endDate) {
        String sql = "SELECT NV.maNV, NV.hoTen, COALESCE(COUNT(PN.maNhap), 0) AS soPhieuNhap, COALESCE(SUM(PN.tongTien), 0) AS tongTien " +
                     "FROM PHIEUNHAP PN " +
                     "JOIN TAIKHOAN TK ON PN.maTK = TK.maTK " +
                     "JOIN NHANVIEN NV ON TK.maTK = NV.maTK " +
                     "WHERE PN.ngayNhap BETWEEN ? AND ? " +
                     "GROUP BY NV.maNV, NV.hoTen";
        List<ImportStatsDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
        try {
            while (rs.next()) {
                ImportStatsDTO stats = new ImportStatsDTO(
                    rs.getString("maNV"),
                    rs.getString("hoTen"),
                    rs.getInt("soPhieuNhap"),
                    rs.getDouble("tongTien")
                );
                result.add(stats);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        } finally {
            jdbcUtil.Close();
        }
    }

    // Chi tiết phiếu nhập theo sách
    public List<ImportDTO> getImportsByBook(String maSach, Date startDate, Date endDate) {
        String sql = "SELECT PN.maNhap, PN.ngayNhap, PN.tongTien " +
                     "FROM PHIEUNHAP PN " +
                     "JOIN CT_PHIEUNHAP CT ON PN.maNhap = CT.maNhap " +
                     "WHERE CT.maSach = ? AND PN.ngayNhap BETWEEN ? AND ?";
        List<ImportDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maSach, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
        try {
            while (rs.next()) {
                ImportDTO importDTO = new ImportDTO();
                importDTO.setMaNhap(rs.getString("maNhap"));
                importDTO.setNgayNhap(rs.getDate("ngayNhap"));
                importDTO.setTongTien(rs.getDouble("tongTien"));
                result.add(importDTO);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        } finally {
            jdbcUtil.Close();
        }
    }

    // Chi tiết phiếu nhập theo nhân viên
    public List<ImportDTO> getImportsByEmployee(String maNV, Date startDate, Date endDate) {
        String sql = "SELECT PN.maNhap, PN.ngayNhap, PN.tongTien " +
                     "FROM PHIEUNHAP PN " +
                     "JOIN TAIKHOAN TK ON PN.maTK = TK.maTK " +
                     "JOIN NHANVIEN NV ON TK.maTK = NV.maTK " +
                     "WHERE NV.maNV = ? AND PN.ngayNhap BETWEEN ? AND ?";
        List<ImportDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maNV, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
        try {
            while (rs.next()) {
                ImportDTO importDTO = new ImportDTO();
                importDTO.setMaNhap(rs.getString("maNhap"));
                importDTO.setNgayNhap(rs.getDate("ngayNhap"));
                importDTO.setTongTien(rs.getDouble("tongTien"));
                result.add(importDTO);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        } finally {
            jdbcUtil.Close();
        }
    }

    // Tổng phiếu nhập và tiền nhập
    public double[] getTotalImport(Date startDate, Date endDate) {
        String sql = "SELECT COALESCE(COUNT(*), 0) AS soPhieuNhap, COALESCE(SUM(tongTien), 0) AS tongTien " +
                     "FROM PHIEUNHAP WHERE ngayNhap BETWEEN ? AND ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
        try {
            if (rs.next()) {
                return new double[]{rs.getInt("soPhieuNhap"), rs.getDouble("tongTien")};
            }
            return new double[]{0, 0};
        } catch (SQLException e) {
            e.printStackTrace();
            return new double[]{0, 0};
        } finally {
            jdbcUtil.Close();
        }
    }
}