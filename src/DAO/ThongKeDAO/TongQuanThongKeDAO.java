package DAO.ThongKeDAO;

import config.JDBCUtil;
import DTO.SachDTO;
import DTO.TyLeDTO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TongQuanThongKeDAO {
    
    // Số đơn hôm nay
    public int getOrdersToday() {
        String sql = "SELECT COUNT(*) AS soDon FROM HOADON WHERE DATE(ngayBan) = CURDATE()";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            if (rs.next()) {
                return rs.getInt("soDon");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            jdbcUtil.Close();
        }
    }

    // Doanh thu hôm nay
    public double getRevenueToday() {
        String sql = "SELECT COALESCE(SUM(tongTien), 0) AS doanhThu FROM HOADON WHERE DATE(ngayBan) = CURDATE()";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            if (rs.next()) {
                return rs.getDouble("doanhThu");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            jdbcUtil.Close();
        }
    }

    // Tiền nhập hôm nay
    public double getImportCostToday() {
        String sql = "SELECT COALESCE(SUM(tongTien), 0) AS tienNhap FROM PHIEUNHAP WHERE DATE(ngayNhap) = CURDATE()";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            if (rs.next()) {
                return rs.getDouble("tienNhap");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            jdbcUtil.Close();
        }
    }

    // Số sách tồn thấp
    public int getLowStockBooks(int nguong) {
        String sql = String.format("SELECT COUNT(*) AS soSach FROM SACH WHERE soLuong < %d",  nguong);

        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            if (rs.next()) {
                return rs.getInt("soSach");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            jdbcUtil.Close();
        }
    }

    // Doanh thu 7 ngày
    // public ArrayList<BigDecimal> getRevenue7Days() {
    //     String sql = "SELECT DATE(ngayBan) AS ngay, COALESCE(SUM(tongTien), 0) AS doanhThu " +
    //                  "FROM HOADON " +
    //                  "WHERE ngayBan >= CURDATE() - INTERVAL 6 DAY " +
    //                  "GROUP BY DATE(ngayBan) ORDER BY ngayBan";

    //     ArrayList<BigDecimal> result = new ArrayList<>();
    //     JDBCUtil jdbcUtil = new JDBCUtil();
    //     jdbcUtil.Open();
    //     ResultSet rs = jdbcUtil.executeQuery(sql);
    //     try {
    //         while (rs.next()) {
    //             result.add(rs.getBigDecimal("doanhThu"));
    //         }
    //         return result;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return result;
    //     } finally {
    //         jdbcUtil.Close();
    //     }
    // }

    public ArrayList<BigDecimal> getRevenue7Days() {
        String sql = "WITH RECURSIVE date_sequence AS (" +
                     "    SELECT CURDATE() AS ngay" +
                     "    UNION ALL" +
                     "    SELECT DATE_SUB(ngay, INTERVAL 1 DAY)" +
                     "    FROM date_sequence" +
                     "    WHERE ngay > CURDATE() - INTERVAL 6 DAY" +
                     ")" +
                     "SELECT COALESCE(SUM(HD.tongTien), 0) AS doanhThu " +
                     "FROM date_sequence " +
                     "LEFT JOIN HOADON HD ON DATE(HD.ngayBan) = date_sequence.ngay " +
                     "GROUP BY date_sequence.ngay " +
                     "ORDER BY date_sequence.ngay";
        ArrayList<BigDecimal> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                result.add(rs.getBigDecimal("doanhThu"));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            // Trả về danh sách 7 ngày với doanh thu 0 nếu có lỗi
            result.clear();
            for (int i = 0; i < 7; i++) {
                result.add(BigDecimal.ZERO);
            }
            return result;
        } finally {
            jdbcUtil.Close();
        }
    }

    public static void main(String[] args) {
        TongQuanThongKeDAO tongquan = new TongQuanThongKeDAO();

        for (BigDecimal x : tongquan.getRevenue7Days()) 
        System.out.println(x);
    }

    // Top 7 sách bán chạy
    public ArrayList<SachDTO> getTopBooks7Days() {
        String sql = "SELECT S.maSach, S.tenSach, COALESCE(SUM(CT.soLuong), 0) AS soLuongBan " +
                     "FROM CT_HOADON CT " +
                     "JOIN HOADON HD ON CT.maHD = HD.maHD " +
                     "JOIN SACH S ON CT.maSach = S.maSach " +
                     "WHERE HD.ngayBan >= CURDATE() - INTERVAL 6 DAY " +
                     "GROUP BY S.maSach, S.tenSach " +
                     "ORDER BY soLuongBan DESC LIMIT 7";
                     ArrayList<SachDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                SachDTO book = new SachDTO();
                book.setMaSach(rs.getInt("maSach"));
                book.setTenSach(rs.getString("tenSach"));
                book.setSoLuong(rs.getInt("soLuongBan")); // Lưu số lượng bán tạm thời
                result.add(book);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        } finally {
            jdbcUtil.Close();
        }
    }

    // Phần trăm hóa đơn khuyến mãi
    public TyLeDTO getPromotionUsage() {
        String sql = "SELECT " +
                     "COALESCE(COUNT(CASE WHEN maKM IS NOT NULL THEN 1 END), 0) AS coKhuyenMai, " +
                     "COALESCE(COUNT(CASE WHEN maKM IS NULL THEN 1 END), 0) AS khongKhuyenMai " +
                     "FROM HOADON " +
                     "WHERE ngayBan >= CURDATE() - INTERVAL 6 DAY";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            if (rs.next()) {
                return new TyLeDTO(rs.getInt("coKhuyenMai"), rs.getInt("khongKhuyenMai"));
            }
            return new TyLeDTO(0, 0); // Trả về giá trị mặc định nếu không có dữ liệu
        } catch (SQLException e) {
            e.printStackTrace();
            return new TyLeDTO(0, 0); // Trả về giá trị mặc định khi có lỗi
        } finally {
            jdbcUtil.Close();
        }
    }
}
