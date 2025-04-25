package DAO.ThongKeDAO;

import config.JDBCUtil;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import DTO.HoaDonDTO;
import DTO.ThongKe.ThongKeDoanhThuDTO;

public class ThongKeDoanhThuDAO {
    // Thống kê theo sách
    public List<ThongKeDoanhThuDTO> getRevenueByBook(Date startDate, Date endDate) {
        String sql = "SELECT S.maSach, S.tenSach, COALESCE(SUM(CT.soLuong), 0) AS soLuongBan, COALESCE(SUM(CT.soLuong * CT.giaBan), 0) AS doanhThu " +
                     "FROM CT_HOADON CT " +
                     "JOIN HOADON HD ON CT.maHD = HD.maHD " +
                     "JOIN SACH S ON CT.maSach = S.maSach " +
                     "WHERE HD.ngayBan BETWEEN ? AND ? " +
                     "GROUP BY S.maSach, S.tenSach";
        List<ThongKeDoanhThuDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
        try {
            while (rs.next()) {
                ThongKeDoanhThuDTO stats = new ThongKeDoanhThuDTO(
                    rs.getInt("maSach"),
                    rs.getString("tenSach"),
                    rs.getInt("soLuongBan"),
                    rs.getBigDecimal("doanhThu")
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
    public List<ThongKeDoanhThuDTO> getRevenueByCustomer(Date startDate, Date endDate) {
        String sql = "SELECT KH.maKH, COALESCE(KH.tenKH, 'Khách lẻ') AS tenKH, COALESCE(COUNT(HD.maHD), 0) AS soHoaDon, COALESCE(SUM(HD.tongTien), 0) AS doanhThu " +
                     "FROM HOADON HD " +
                     "LEFT JOIN KHACHHANG KH ON HD.maKH = KH.maKH " +
                     "WHERE HD.ngayBan BETWEEN ? AND ? " +
                     "GROUP BY KH.maKH, KH.tenKH";
        List<ThongKeDoanhThuDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
        try {
            while (rs.next()) {
                ThongKeDoanhThuDTO stats = new ThongKeDoanhThuDTO(
                    rs.getInt("maKH"),
                    rs.getString("tenKH"),
                    rs.getInt("soHoaDon"),
                    rs.getBigDecimal("doanhThu")
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
    public List<HoaDonDTO> getInvoicesByBook(String maSach, Date startDate, Date endDate) {
        String sql = "SELECT HD.* " +
                     "FROM HOADON HD " +
                     "JOIN CT_HOADON CT ON HD.maHD = CT.maHD " +
                     "WHERE CT.maSach = ? AND HD.ngayBan BETWEEN ? AND ?";
        List<HoaDonDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maSach, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
        try {
            while (rs.next()) {
                int id = rs.getInt("maHD");
                LocalDateTime ngayBan = rs.getTimestamp("ngayBan").toLocalDateTime();
                BigDecimal tongTien = rs.getBigDecimal("tongTien");
                int maTK = rs.getInt("maTK");
                int maPT = rs.getInt("maPT");
                int maKM = rs.getInt("maKM");
                int maKH = rs.getInt("maKH");
                HoaDonDTO hoaDon = new HoaDonDTO(id, ngayBan, tongTien, maTK, maPT, maKM, maKH);
                result.add(hoaDon);
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
    public List<HoaDonDTO> getInvoicesByCustomer(String maKH, Date startDate, Date endDate) {
        String sql = "SELECT HD.* " +
                     "FROM HOADON HD " +
                     "WHERE HD.maKH = ? AND HD.ngayBan BETWEEN ? AND ?";
        List<HoaDonDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maKH, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
        try {
            while (rs.next()) {
                int id = rs.getInt("maHD");
                LocalDateTime ngayBan = rs.getTimestamp("ngayBan").toLocalDateTime();
                BigDecimal tongTien = rs.getBigDecimal("tongTien");
                int maTK = rs.getInt("maTK");
                int maPT = rs.getInt("maPT");
                int maKM = rs.getInt("maKM");
                int maKHang = rs.getInt("maKH");
                HoaDonDTO hoaDon = new HoaDonDTO(id, ngayBan, tongTien, maTK, maPT, maKM, maKHang);
                result.add(hoaDon);
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
                return new double[]{rs.getInt("soHoaDon"), rs.getBigDecimal("doanhThu").doubleValue()};
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
    public List<ThongKeDoanhThuDTO> getTop5Books(Date startDate, Date endDate) {
        String sql = "SELECT S.maSach, S.tenSach, COALESCE(SUM(CT.soLuong), 0) AS soLuongBan " +
                     "FROM CT_HOADON CT " +
                     "JOIN HOADON HD ON CT.maHD = HD.maHD " +
                     "JOIN SACH S ON CT.maSach = S.maSach " +
                     "WHERE HD.ngayBan BETWEEN ? AND ? " +
                     "GROUP BY S.maSach, S.tenSach " +
                     "ORDER BY soLuongBan DESC LIMIT 5";
        List<ThongKeDoanhThuDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
        try {
            while (rs.next()) {
                ThongKeDoanhThuDTO stats = new ThongKeDoanhThuDTO(
                    rs.getInt("maSach"),
                    rs.getString("tenSach"),
                    rs.getInt("soLuongBan"),
                    new BigDecimal(0)
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

    // Top 5 khách hàng mua nhiều nhất
    public List<ThongKeDoanhThuDTO> getTop5Customers(Date startDate, Date endDate) {
        String sql = "SELECT KH.maKH, COALESCE(KH.tenKH, 'Khách lẻ') AS tenKH, " +
                     "COALESCE(COUNT(HD.maHD), 0) AS soHoaDon, COALESCE(SUM(HD.tongTien), 0) AS doanhThu " +
                     "FROM HOADON HD " +
                     "LEFT JOIN KHACHHANG KH ON HD.maKH = KH.maKH " +
                     "WHERE HD.ngayBan BETWEEN ? AND ? " +
                     "GROUP BY KH.maKH, KH.tenKH " +
                     "ORDER BY doanhThu DESC LIMIT 5";
        List<ThongKeDoanhThuDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
        try {
            while (rs.next()) {
                ThongKeDoanhThuDTO stats = new ThongKeDoanhThuDTO(
                    rs.getInt("maKH"),
                    rs.getString("tenKH"),
                    rs.getInt("soHoaDon"),
                    rs.getBigDecimal("doanhThu")
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
    //Lấy tất cả
    public List<ThongKeDoanhThuDTO> getRevenueByBook() {
        String sql = "SELECT S.maSach, S.tenSach, COALESCE(SUM(CT.soLuong), 0) AS soLuongBan, COALESCE(SUM(CT.soLuong * CT.giaBan), 0) AS doanhThu " +
                     "FROM CT_HOADON CT " +
                     "JOIN HOADON HD ON CT.maHD = HD.maHD " +
                     "JOIN SACH S ON CT.maSach = S.maSach " +
                     "GROUP BY S.maSach, S.tenSach";
        List<ThongKeDoanhThuDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                ThongKeDoanhThuDTO stats = new ThongKeDoanhThuDTO(
                    rs.getInt("maSach"),
                    rs.getString("tenSach"),
                    rs.getInt("soLuongBan"),
                    rs.getBigDecimal("doanhThu")
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

    public List<ThongKeDoanhThuDTO> getRevenueByCustomer() {
        String sql = "SELECT KH.maKH, COALESCE(KH.tenKH, 'Khách lẻ') AS tenKH, COALESCE(COUNT(HD.maHD), 0) AS soHoaDon, COALESCE(SUM(HD.tongTien), 0) AS doanhThu " +
                     "FROM HOADON HD " +
                     "LEFT JOIN KHACHHANG KH ON HD.maKH = KH.maKH " +
                     "GROUP BY KH.maKH, KH.tenKH";
        List<ThongKeDoanhThuDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                ThongKeDoanhThuDTO stats = new ThongKeDoanhThuDTO(
                    rs.getInt("maKH"),
                    rs.getString("tenKH"),
                    rs.getInt("soHoaDon"),
                    rs.getBigDecimal("doanhThu")
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

    public List<HoaDonDTO> getInvoicesByBook(String maSach) {
        String sql = "SELECT HD.* " +
                     "FROM HOADON HD " +
                     "JOIN CT_HOADON CT ON HD.maHD = CT.maHD " +
                     "WHERE CT.maSach = ?";
        List<HoaDonDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maSach);
        try {
            while (rs.next()) {
                int id = rs.getInt("maHD");
                LocalDateTime ngayBan = rs.getTimestamp("ngayBan").toLocalDateTime();
                BigDecimal tongTien = rs.getBigDecimal("tongTien");
                int maTK = rs.getInt("maTK");
                int maPT = rs.getInt("maPT");
                int maKM = rs.getInt("maKM");
                int maKH = rs.getInt("maKH");
                HoaDonDTO hoaDon = new HoaDonDTO(id, ngayBan, tongTien, maTK, maPT, maKM, maKH);
                result.add(hoaDon);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        } finally {
            jdbcUtil.Close();
        }
    }

    public List<HoaDonDTO> getInvoicesByCustomer(String maKH) {
        String sql = "SELECT HD.* " +
                     "FROM HOADON HD " +
                     "WHERE HD.maKH = ?";
        List<HoaDonDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maKH);
        try {
            while (rs.next()) {
                int id = rs.getInt("maHD");
                LocalDateTime ngayBan = rs.getTimestamp("ngayBan").toLocalDateTime();
                BigDecimal tongTien = rs.getBigDecimal("tongTien");
                int maTK = rs.getInt("maTK");
                int maPT = rs.getInt("maPT");
                int maKM = rs.getInt("maKM");
                int maKHang = rs.getInt("maKH");
                HoaDonDTO hoaDon = new HoaDonDTO(id, ngayBan, tongTien, maTK, maPT, maKM, maKHang);
                result.add(hoaDon);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        } finally {
            jdbcUtil.Close();
        }
    }

    public double[] getTotalRevenue() {
        String sql = "SELECT COALESCE(COUNT(*), 0) AS soHoaDon, COALESCE(SUM(tongTien), 0) AS doanhThu " +
                     "FROM HOADON";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            if (rs.next()) {
                return new double[]{rs.getInt("soHoaDon"), rs.getBigDecimal("doanhThu").doubleValue()};
            }
            return new double[]{0, 0};
        } catch (SQLException e) {
            e.printStackTrace();
            return new double[]{0, 0};
        } finally {
            jdbcUtil.Close();
        }
    }
    public List<ThongKeDoanhThuDTO> getTop5Books() {
        String sql = "SELECT S.maSach, S.tenSach, COALESCE(SUM(CT.soLuong), 0) AS soLuongBan " +
                     "FROM CT_HOADON CT " +
                     "JOIN HOADON HD ON CT.maHD = HD.maHD " +
                     "JOIN SACH S ON CT.maSach = S.maSach " +
                     "GROUP BY S.maSach, S.tenSach " +
                     "ORDER BY soLuongBan DESC LIMIT 5";
        List<ThongKeDoanhThuDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                ThongKeDoanhThuDTO stats = new ThongKeDoanhThuDTO(
                    rs.getInt("maSach"),
                    rs.getString("tenSach"),
                    rs.getInt("soLuongBan"),
                    new BigDecimal(0)
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

    public List<ThongKeDoanhThuDTO> getTop5Customers() {
        String sql = "SELECT KH.maKH, COALESCE(KH.tenKH, 'Khách lẻ') AS tenKH, " +
                     "COALESCE(COUNT(HD.maHD), 0) AS soHoaDon, COALESCE(SUM(HD.tongTien), 0) AS doanhThu " +
                     "FROM HOADON HD " +
                     "LEFT JOIN KHACHHANG KH ON HD.maKH = KH.maKH " +
                     "GROUP BY KH.maKH, KH.tenKH " +
                     "ORDER BY doanhThu DESC LIMIT 5";
        List<ThongKeDoanhThuDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                ThongKeDoanhThuDTO stats = new ThongKeDoanhThuDTO(
                    rs.getInt("maKH"),
                    rs.getString("tenKH"),
                    rs.getInt("soHoaDon"),
                    rs.getBigDecimal("doanhThu")
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