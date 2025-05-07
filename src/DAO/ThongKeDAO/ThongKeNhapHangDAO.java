

package DAO.ThongKeDAO;

import config.JDBCUtil;
import DTO.PhieuNhapDTO;
import DTO.ThongKe.ThongKeNhapHangDTO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThongKeNhapHangDAO {
    // Thống kê theo sách
    public List<ThongKeNhapHangDTO> getImportByBook(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT S.maSach, S.tenSach, COALESCE(SUM(CT.soLuongNhap), 0) AS soLuongNhap, COALESCE(SUM(CT.soLuongNhap * CT.giaNhap), 0) AS tongTien " +
                     "FROM CT_PHIEUNHAP CT " +
                     "JOIN PHIEUNHAP PN ON CT.maNhap = PN.maNhap " +
                     "JOIN SACH S ON CT.maSach = S.maSach " +
                     "WHERE PN.ngayNhap BETWEEN ? AND ? " +
                     "GROUP BY S.maSach, S.tenSach";
        List<ThongKeNhapHangDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql,startDate, endDate);
        try {
            while (rs.next()) {
                ThongKeNhapHangDTO stats = new ThongKeNhapHangDTO(
                    rs.getInt("maSach"),
                    rs.getString("tenSach"),
                    rs.getInt("soLuongNhap"),
                    rs.getBigDecimal("tongTien")
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
    public List<ThongKeNhapHangDTO> getImportByEmployee(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT NV.maNV, NV.hoTen, COALESCE(COUNT(PN.maNhap), 0) AS soPhieuNhap, COALESCE(SUM(PN.tongTien), 0) AS tongTien " +
                     "FROM PHIEUNHAP PN " +
                     "JOIN TAIKHOAN TK ON PN.maTK = TK.maTK " +
                     "JOIN NHANVIEN NV ON TK.maTK = NV.maTK " +
                     "WHERE PN.ngayNhap BETWEEN ? AND ? " +
                     "GROUP BY NV.maNV, NV.hoTen";
        List<ThongKeNhapHangDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, startDate, endDate);
        try {
            while (rs.next()) {
                ThongKeNhapHangDTO stats = new ThongKeNhapHangDTO(
                    rs.getInt("maNV"),
                    rs.getString("hoTen"),
                    rs.getInt("soPhieuNhap"),
                    rs.getBigDecimal("tongTien")
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

    // Thống kê theo nhà cung cấp
    public List<ThongKeNhapHangDTO> getImportBySupplier(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT NCC.maNCC, NCC.tenNCC, COALESCE(COUNT(PN.maNhap), 0) AS soPhieuNhap, COALESCE(SUM(PN.tongTien), 0) AS tongTien " +
                     "FROM PHIEUNHAP PN " +
                     "JOIN NHACUNGCAP NCC ON PN.maNCC = NCC.maNCC " +
                     "WHERE PN.ngayNhap BETWEEN ? AND ? " +
                     "GROUP BY NCC.maNCC, NCC.tenNCC";
        List<ThongKeNhapHangDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, startDate,endDate);
        try {
            while (rs.next()) {
                ThongKeNhapHangDTO stats = new ThongKeNhapHangDTO(
                    rs.getInt("maNCC"),
                    rs.getString("tenNCC"),
                    rs.getInt("soPhieuNhap"),
                    rs.getBigDecimal("tongTien")
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
    public List<PhieuNhapDTO> getImportsByBook(String maSach, LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT PN.* " +
                     "FROM PHIEUNHAP PN " +
                     "JOIN CT_PHIEUNHAP CT ON PN.maNhap = CT.maNhap " +
                     "WHERE CT.maSach = ? AND PN.ngayNhap BETWEEN ? AND ?";
        List<PhieuNhapDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maSach, startDate, endDate);
        try {
            while (rs.next()) {
                int maNhap = rs.getInt("maNhap");
                LocalDateTime ngayNhap = rs.getTimestamp("ngayNhap").toLocalDateTime();
                BigDecimal tongTien = rs.getBigDecimal("tongTien");
                int maNCC = rs.getInt("maNCC");
                int maTK = rs.getInt("maTK");
                PhieuNhapDTO phieuNhap = new PhieuNhapDTO(maNhap, ngayNhap, tongTien, maNCC, maTK);
                result.add(phieuNhap);
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
    public List<PhieuNhapDTO> getImportsByEmployee(String maNV, LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT PN.* " +
                     "FROM PHIEUNHAP PN " +
                     "JOIN TAIKHOAN TK ON PN.maTK = TK.maTK " +
                     "JOIN NHANVIEN NV ON TK.maTK = NV.maTK " +
                     "WHERE NV.maNV = ? AND PN.ngayNhap BETWEEN ? AND ?";
        List<PhieuNhapDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maNV, startDate, endDate);
        try {
            while (rs.next()) {
                int maNhap = rs.getInt("maNhap");
                LocalDateTime ngayNhap = rs.getTimestamp("ngayNhap").toLocalDateTime();
                BigDecimal tongTien = rs.getBigDecimal("tongTien");
                int maNCC = rs.getInt("maNCC");
                int maTK = rs.getInt("maTK");
                PhieuNhapDTO phieuNhap = new PhieuNhapDTO(maNhap, ngayNhap, tongTien, maNCC, maTK);
                result.add(phieuNhap);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        } finally {
            jdbcUtil.Close();
        }
    }

    // Chi tiết phiếu nhập theo nhà cung cấp
    public List<PhieuNhapDTO> getImportsBySupplier(String maNCC, LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT PN.* " +
                     "FROM PHIEUNHAP PN " +
                     "JOIN NHACUNGCAP NCC ON PN.maNCC = NCC.maNCC " +
                     "WHERE PN.maNCC = ? AND PN.ngayNhap BETWEEN ? AND ?";
        List<PhieuNhapDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maNCC, startDate, endDate);
        try {
            while (rs.next()) {
                int maNhap = rs.getInt("maNhap");
                LocalDateTime ngayNhap = rs.getTimestamp("ngayNhap").toLocalDateTime();
                BigDecimal tongTien = rs.getBigDecimal("tongTien");
                int maNCCValue = rs.getInt("maNCC");
                int maTK = rs.getInt("maTK");
                PhieuNhapDTO phieuNhap = new PhieuNhapDTO(maNhap, ngayNhap, tongTien, maNCCValue, maTK);
                result.add(phieuNhap);
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
    public double[] getTotalImport(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT COALESCE(COUNT(*), 0) AS soPhieuNhap, COALESCE(SUM(tongTien), 0) AS tongTien " +
                     "FROM PHIEUNHAP WHERE ngayNhap BETWEEN ? AND ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, startDate, endDate);
        try {
            if (rs.next()) {
                return new double[]{rs.getInt("soPhieuNhap"), rs.getBigDecimal("tongTien").doubleValue()};
            }
            return new double[]{0, 0};
        } catch (SQLException e) {
            e.printStackTrace();
            return new double[]{0, 0};
        } finally {
            jdbcUtil.Close();
        }
    }

    // Top 5 sách nhập nhiều nhất
    public List<ThongKeNhapHangDTO> getTop5Books(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT S.maSach, S.tenSach, COALESCE(SUM(CT.soLuongNhap), 0) AS soLuongNhap, COALESCE(SUM(CT.soLuongNhap * CT.giaNhap), 0) AS tongTien " +
                     "FROM CT_PHIEUNHAP CT " +
                     "JOIN PHIEUNHAP PN ON CT.maNhap = PN.maNhap " +
                     "JOIN SACH S ON CT.maSach = S.maSach " +
                     "WHERE PN.ngayNhap BETWEEN ? AND ? " +
                     "GROUP BY S.maSach, S.tenSach " +
                     "ORDER BY soLuongNhap DESC LIMIT 5";
        List<ThongKeNhapHangDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, startDate, endDate);
        try {
            while (rs.next()) {
                ThongKeNhapHangDTO stats = new ThongKeNhapHangDTO(
                    rs.getInt("maSach"),
                    rs.getString("tenSach"),
                    rs.getInt("soLuongNhap"),
                    rs.getBigDecimal("tongTien")
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

    // Top 5 nhà cung cấp được nhập nhiều nhất
    public List<ThongKeNhapHangDTO> getTop5Suppliers(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT NCC.maNCC, NCC.tenNCC, COALESCE(COUNT(PN.maNhap), 0) AS soPhieuNhap, COALESCE(SUM(PN.tongTien), 0) AS tongTien " +
                     "FROM PHIEUNHAP PN " +
                     "JOIN NHACUNGCAP NCC ON PN.maNCC = NCC.maNCC " +
                     "WHERE PN.ngayNhap BETWEEN ? AND ? " +
                     "GROUP BY NCC.maNCC, NCC.tenNCC " +
                     "ORDER BY tongTien DESC LIMIT 5";
        List<ThongKeNhapHangDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, startDate, endDate);
        try {
            while (rs.next()) {
                ThongKeNhapHangDTO stats = new ThongKeNhapHangDTO(
                    rs.getInt("maNCC"),
                    rs.getString("tenNCC"),
                    rs.getInt("soPhieuNhap"),
                    rs.getBigDecimal("tongTien")
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

        // Thống kê theo sách
    public List<ThongKeNhapHangDTO> getImportByBook() {
        String sql = "SELECT S.maSach, S.tenSach, COALESCE(SUM(CT.soLuongNhap), 0) AS soLuongNhap, COALESCE(SUM(CT.soLuongNhap * CT.giaNhap), 0) AS tongTien " +
                     "FROM CT_PHIEUNHAP CT " +
                     "JOIN PHIEUNHAP PN ON CT.maNhap = PN.maNhap " +
                     "JOIN SACH S ON CT.maSach = S.maSach " +
                     "GROUP BY S.maSach, S.tenSach";
        List<ThongKeNhapHangDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                ThongKeNhapHangDTO stats = new ThongKeNhapHangDTO(
                    rs.getInt("maSach"),
                    rs.getString("tenSach"),
                    rs.getInt("soLuongNhap"),
                    rs.getBigDecimal("tongTien")
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
    public List<ThongKeNhapHangDTO> getImportByEmployee() {
        String sql = "SELECT NV.maNV, NV.hoTen, COALESCE(COUNT(PN.maNhap), 0) AS soPhieuNhap, COALESCE(SUM(PN.tongTien), 0) AS tongTien " +
                     "FROM PHIEUNHAP PN " +
                     "JOIN TAIKHOAN TK ON PN.maTK = TK.maTK " +
                     "JOIN NHANVIEN NV ON TK.maTK = NV.maTK " +
                     "GROUP BY NV.maNV, NV.hoTen";
        List<ThongKeNhapHangDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                ThongKeNhapHangDTO stats = new ThongKeNhapHangDTO(
                    rs.getInt("maNV"),
                    rs.getString("hoTen"),
                    rs.getInt("soPhieuNhap"),
                    rs.getBigDecimal("tongTien")
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

    // Thống kê theo nhà cung cấp
    public List<ThongKeNhapHangDTO> getImportBySupplier() {
        String sql = "SELECT NCC.maNCC, NCC.tenNCC, COALESCE(COUNT(PN.maNhap), 0) AS soPhieuNhap, COALESCE(SUM(PN.tongTien), 0) AS tongTien " +
                     "FROM PHIEUNHAP PN " +
                     "JOIN NHACUNGCAP NCC ON PN.maNCC = NCC.maNCC " +
                     "GROUP BY NCC.maNCC, NCC.tenNCC";
        List<ThongKeNhapHangDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                ThongKeNhapHangDTO stats = new ThongKeNhapHangDTO(
                    rs.getInt("maNCC"),
                    rs.getString("tenNCC"),
                    rs.getInt("soPhieuNhap"),
                    rs.getBigDecimal("tongTien")
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
    public List<PhieuNhapDTO> getImportsByBook(String maSach) {
        String sql = "SELECT PN.* " +
                     "FROM PHIEUNHAP PN " +
                     "JOIN CT_PHIEUNHAP CT ON PN.maNhap = CT.maNhap " +
                     "WHERE CT.maSach = ?";
        List<PhieuNhapDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maSach);
        try {
            while (rs.next()) {
                int maNhap = rs.getInt("maNhap");
                LocalDateTime ngayNhap = rs.getTimestamp("ngayNhap").toLocalDateTime();
                BigDecimal tongTien = rs.getBigDecimal("tongTien");
                int maNCC = rs.getInt("maNCC");
                int maTK = rs.getInt("maTK");
                PhieuNhapDTO phieuNhap = new PhieuNhapDTO(maNhap, ngayNhap, tongTien, maNCC, maTK);
                result.add(phieuNhap);
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
    public List<PhieuNhapDTO> getImportsByEmployee(String maNV) {
        String sql = "SELECT PN.* " +
                     "FROM PHIEUNHAP PN " +
                     "JOIN TAIKHOAN TK ON PN.maTK = TK.maTK " +
                     "JOIN NHANVIEN NV ON TK.maTK = NV.maTK " +
                     "WHERE NV.maNV = ?";
        List<PhieuNhapDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maNV);
        try {
            while (rs.next()) {
                int maNhap = rs.getInt("maNhap");
                LocalDateTime ngayNhap = rs.getTimestamp("ngayNhap").toLocalDateTime();
                BigDecimal tongTien = rs.getBigDecimal("tongTien");
                int maNCC = rs.getInt("maNCC");
                int maTK = rs.getInt("maTK");
                PhieuNhapDTO phieuNhap = new PhieuNhapDTO(maNhap, ngayNhap, tongTien, maNCC, maTK);
                result.add(phieuNhap);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        } finally {
            jdbcUtil.Close();
        }
    }

    // Chi tiết phiếu nhập theo nhà cung cấp
    public List<PhieuNhapDTO> getImportsBySupplier(String maNCC) {
        String sql = "SELECT PN.* " +
                     "FROM PHIEUNHAP PN " +
                     "JOIN NHACUNGCAP NCC ON PN.maNCC = NCC.maNCC " +
                     "WHERE PN.maNCC = ?";
        List<PhieuNhapDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maNCC);
        try {
            while (rs.next()) {
                int maNhap = rs.getInt("maNhap");
                LocalDateTime ngayNhap = rs.getTimestamp("ngayNhap").toLocalDateTime();
                BigDecimal tongTien = rs.getBigDecimal("tongTien");
                int maNCCValue = rs.getInt("maNCC");
                int maTK = rs.getInt("maTK");
                PhieuNhapDTO phieuNhap = new PhieuNhapDTO(maNhap, ngayNhap, tongTien, maNCCValue, maTK);
                result.add(phieuNhap);
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
    public double[] getTotalImport() {
        String sql = "SELECT COALESCE(COUNT(*), 0) AS soPhieuNhap, COALESCE(SUM(tongTien), 0) AS tongTien " +
                     "FROM PHIEUNHAP";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            if (rs.next()) {
                return new double[]{rs.getInt("soPhieuNhap"), rs.getBigDecimal("tongTien").doubleValue()};
            }
            return new double[]{0, 0};
        } catch (SQLException e) {
            e.printStackTrace();
            return new double[]{0, 0};
        } finally {
            jdbcUtil.Close();
        }
    }

    // Top 5 sách nhập nhiều nhất
    public List<ThongKeNhapHangDTO> getTop5Books() {
        String sql = "SELECT S.maSach, S.tenSach, COALESCE(SUM(CT.soLuongNhap), 0) AS soLuongNhap, COALESCE(SUM(CT.soLuongNhap * CT.giaNhap), 0) AS tongTien " +
                     "FROM CT_PHIEUNHAP CT " +
                     "JOIN PHIEUNHAP PN ON CT.maNhap = PN.maNhap " +
                     "JOIN SACH S ON CT.maSach = S.maSach " +
                     "GROUP BY S.maSach, S.tenSach " +
                     "ORDER BY soLuongNhap DESC LIMIT 5";
        List<ThongKeNhapHangDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                ThongKeNhapHangDTO stats = new ThongKeNhapHangDTO(
                    rs.getInt("maSach"),
                    rs.getString("tenSach"),
                    rs.getInt("soLuongNhap"),
                    rs.getBigDecimal("tongTien")
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

    // Top 5 nhà cung cấp được nhập nhiều nhất
    public List<ThongKeNhapHangDTO> getTop5Suppliers() {
        String sql = "SELECT NCC.maNCC, NCC.tenNCC, COALESCE(COUNT(PN.maNhap), 0) AS soPhieuNhap, COALESCE(SUM(PN.tongTien), 0) AS tongTien " +
                     "FROM PHIEUNHAP PN " +
                     "JOIN NHACUNGCAP NCC ON PN.maNCC = NCC.maNCC " +
                     "GROUP BY NCC.maNCC, NCC.tenNCC " +
                     "ORDER BY tongTien DESC LIMIT 5";
        List<ThongKeNhapHangDTO> result = new ArrayList<>();
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                ThongKeNhapHangDTO stats = new ThongKeNhapHangDTO(
                    rs.getInt("maNCC"),
                    rs.getString("tenNCC"),
                    rs.getInt("soPhieuNhap"),
                    rs.getBigDecimal("tongTien")
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