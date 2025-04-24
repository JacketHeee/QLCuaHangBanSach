package DAO.ThongKeDAO;

package dao;

import config.JDBCUtil;
import dto.InvoiceDTO;
import dto.RevenueStatsDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RevenueDAO {
    // Thống kê theo sách
    public List<RevenueStatsDTO> getRevenueByBook(Date startDate, Date endDate) {
        String sql = "SELECT S.maSach, S.tenSach, COALESCE(SUM(CT.soLuong), 0) AS soLuongBan, COALESCE(SUM(CT.soLuong * CT.giaBan), 0) AS doanhThu " +
                     "FROM CT_HOADON CT " +
                     "JOIN HOADON HD ON CT.maHD = HD.maHD " +
                     "JOIN SACH S ON CT.maSach = S.maSach " +
                     "WHERE HD.ngayBan BETWEEN ? AND ? " +
                     "GROUP BY S.maSach, S.tenSach";
        List<RevenueStatsDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
        try {
            while (rs.next()) {
                RevenueStatsDTO stats = new RevenueStatsDTO(
                    rs.getString("maSach"),
                    rs.getString("tenSach"),
                    rs.getInt("soLuongBan"),
                    rs.getDouble("doanhThu")
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

    // Thống kê theo khách hàng
    public List<RevenueStatsDTO> getRevenueByCustomer(Date startDate, Date endDate) {
        String sql = "SELECT KH.maKH, COALESCE(KH.tenKH, 'Khách lẻ') AS tenKH, COALESCE(COUNT(HD.maHD), 0) AS soHoaDon, COALESCE(SUM(HD.tongTien), 0) AS doanhThu " +
                     "FROM HOADON HD " +
                     "LEFT JOIN KHACHHANG KH ON HD.maKH = KH.maKH " +
                     "WHERE HD.ngayBan BETWEEN ? AND ? " +
                     "GROUP BY KH.maKH, KH.tenKH";
        List<RevenueStatsDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
        try {
            while (rs.next()) {
                RevenueStatsDTO stats = new RevenueStatsDTO(
                    rs.getString("maKH"),
                    rs.getString("tenKH"),
                    rs.getInt("soHoaDon"),
                    rs.getDouble("doanhThu")
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

    // Chi tiết hóa đơn theo sách
    public List<InvoiceDTO> getInvoicesByBook(String maSach, Date startDate, Date endDate) {
        String sql = "SELECT HD.maHD, HD.ngayBan, HD.tongTien " +
                     "FROM HOADON HD " +
                     "JOIN CT_HOADON CT ON HD.maHD = CT.maHD " +
                     "WHERE CT.maSach = ? AND HD.ngayBan BETWEEN ? AND ?";
        List<InvoiceDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maSach, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
        try {
            while (rs.next()) {
                InvoiceDTO invoice = new InvoiceDTO();
                invoice.setMaHD(rs.getString("maHD"));
                invoice.setNgayBan(rs.getDate("ngayBan"));
                invoice.setTongTien(rs.getDouble("tongTien"));
                result.add(invoice);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        } finally {
            jdbcUtil.Close();
        }
    }

    // Chi tiết hóa đơn theo khách hàng
    public List<InvoiceDTO> getInvoicesByCustomer(String maKH, Date startDate, Date endDate) {
        String sql = "SELECT HD.maHD, HD.ngayBan, HD.tongTien " +
                     "FROM HOADON HD " +
                     "WHERE HD.maKH = ? AND HD.ngayBan BETWEEN ? AND ?";
        List<InvoiceDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maKH, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
        try {
            while (rs.next()) {
                InvoiceDTO invoice = new InvoiceDTO();
                invoice.setMaHD(rs.getString("maHD"));
                invoice.setNgayBan(rs.getDate("ngayBan"));
                invoice.setTongTien(rs.getDouble("tongTien"));
                result.add(invoice);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        } finally {
            jdbcUtil.Close();
        }
    }

    // Tổng hóa đơn và doanh thu
    public double[] getTotalRevenue(Date startDate, Date endDate) {
        String sql = "SELECT COALESCE(COUNT(*), 0) AS soHoaDon, COALESCE(SUM(tongTien), 0) AS doanhThu " +
                     "FROM HOADON WHERE ngayBan BETWEEN ? AND ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
        try {
            if (rs.next()) {
                return new double[]{rs.getInt("soHoaDon"), rs.getDouble("doanhThu")};
            }
            return new double[]{0, 0};
        } catch (SQLException e) {
            e.printStackTrace();
            return new double[]{0, 0};
        } finally {
            jdbcUtil.Close();
        }
    }

    // Top 5 sách bán chạy
    public List<RevenueStatsDTO> getTop5Books(Date startDate, Date endDate) {
        String sql = "SELECT S.maSach, S.tenSach, COALESCE(SUM(CT.soLuong), 0) AS soLuongBan " +
                     "FROM CT_HOADON CT " +
                     "JOIN HOADON HD ON CT.maHD = HD.maHD " +
                     "JOIN SACH S ON CT.maSach = S.maSach " +
                     "WHERE HD.ngayBan BETWEEN ? AND ? " +
                     "GROUP BY S.maSach, S.tenSach " +
                     "ORDER BY soLuongBan DESC LIMIT 5";
        List<RevenueStatsDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
        try {
            while (rs.next()) {
                RevenueStatsDTO stats = new RevenueStatsDTO(
                    rs.getString("maSach"),
                    rs.getString("tenSach"),
                    rs.getInt("soLuongBan"),
                    0
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
}