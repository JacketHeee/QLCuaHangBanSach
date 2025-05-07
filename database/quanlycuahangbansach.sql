-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 07, 2025 lúc 04:36 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `quanlycuahangbansach`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietquyen`
--

CREATE TABLE `chitietquyen` (
  `maRole` int(11) NOT NULL,
  `maChucNang` int(11) NOT NULL,
  `hanhDong` varchar(255) NOT NULL,
  `trangThai` tinyint(4) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `chitietquyen`
--

INSERT INTO `chitietquyen` (`maRole`, `maChucNang`, `hanhDong`, `trangThai`) VALUES
(1, 1, 'Sửa', 1),
(1, 1, 'Thêm', 1),
(1, 1, 'Xem', 1),
(1, 1, 'Xóa', 1),
(1, 2, 'Sửa', 1),
(1, 2, 'Thêm', 1),
(1, 2, 'Xem', 1),
(1, 2, 'Xóa', 1),
(1, 3, 'Sửa', 1),
(1, 3, 'Thêm', 1),
(1, 3, 'Xem', 1),
(1, 3, 'Xóa', 1),
(1, 4, 'Sửa', 1),
(1, 4, 'Thêm', 1),
(1, 4, 'Xem', 1),
(1, 4, 'Xóa', 1),
(1, 5, 'Sửa', 1),
(1, 5, 'Thêm', 1),
(1, 5, 'Xem', 1),
(1, 5, 'Xóa', 1),
(1, 6, 'Sửa', 1),
(1, 6, 'Thêm', 1),
(1, 6, 'Xem', 1),
(1, 6, 'Xóa', 1),
(1, 7, 'Thêm', 1),
(1, 7, 'Xem', 1),
(1, 8, 'Thêm', 1),
(1, 8, 'Xem', 1),
(1, 9, 'Sửa', 1),
(1, 9, 'Thêm', 1),
(1, 9, 'Xem', 1),
(1, 9, 'Xóa', 1),
(1, 10, 'Sửa', 1),
(1, 10, 'Thêm', 1),
(1, 10, 'Xem', 1),
(1, 10, 'Xóa', 1),
(1, 11, 'Sửa', 1),
(1, 11, 'Thêm', 1),
(1, 11, 'Xem', 1),
(1, 11, 'Xóa', 1),
(1, 12, 'Sửa', 1),
(1, 12, 'Thêm', 1),
(1, 12, 'Xem', 1),
(1, 12, 'Xóa', 1),
(1, 13, 'Sửa', 1),
(1, 13, 'Thêm', 1),
(1, 13, 'Xem', 1),
(1, 13, 'Xóa', 1),
(1, 14, 'Sửa', 1),
(1, 14, 'Thêm', 1),
(1, 14, 'Xem', 1),
(1, 14, 'Xóa', 1),
(1, 15, 'Xem', 1),
(4, 1, 'Xem', 1),
(4, 2, 'Xem', 1),
(4, 3, 'Xem', 1),
(4, 4, 'Xem', 1),
(4, 5, 'Xem', 1),
(4, 6, 'Xem', 1),
(4, 8, 'Thêm', 1),
(4, 8, 'Xem', 1),
(4, 13, 'Sửa', 1),
(4, 13, 'Thêm', 1),
(4, 13, 'Xem', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chucnang`
--

CREATE TABLE `chucnang` (
  `maChucNang` int(11) NOT NULL,
  `tenChucNang` varchar(255) NOT NULL,
  `trangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `chucnang`
--

INSERT INTO `chucnang` (`maChucNang`, `tenChucNang`, `trangThai`) VALUES
(1, 'book', 1),
(2, 'category', 1),
(3, 'author', 1),
(4, 'nxb', 1),
(5, 'vungtl', 1),
(6, 'ncc', 1),
(7, 'qlInput', 1),
(8, 'qlBill', 1),
(9, 'promotion', 1),
(10, 'pttt', 1),
(11, 'nv', 1),
(12, 'taikhoan', 1),
(13, 'khachhang', 1),
(14, 'phanquyen', 1),
(15, 'report', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ct_hoadon`
--

CREATE TABLE `ct_hoadon` (
  `maSach` int(11) NOT NULL,
  `maHD` int(11) NOT NULL,
  `soLuong` int(11) NOT NULL,
  `giaBan` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `ct_hoadon`
--

INSERT INTO `ct_hoadon` (`maSach`, `maHD`, `soLuong`, `giaBan`) VALUES
(1, 1, 1, 65347.00),
(1, 2, 8, 65347.00),
(1, 3, 1, 65347.00),
(1, 5, 1, 65347.00),
(1, 6, 100, 65347.00),
(1, 8, 100, 65347.00),
(1, 9, 1, 65347.00),
(1, 12, 1, 65347.00),
(1, 15, 1, 65347.00),
(1, 16, 1, 65347.00),
(1, 18, 1, 65347.00),
(1, 19, 1, 65347.00),
(2, 1, 1, 85545.00),
(2, 2, 10, 85545.00),
(2, 4, 8, 85545.00),
(2, 5, 2, 85545.00),
(2, 8, 10, 85545.00),
(2, 16, 12, 85545.00),
(2, 18, 1, 85545.00),
(2, 19, 1, 85545.00),
(3, 2, 13, 105743.00),
(3, 5, 2, 105743.00),
(3, 14, 1, 105743.00),
(4, 2, 16, 237624.00),
(4, 4, 15, 237624.00),
(4, 7, 100, 237624.00),
(4, 8, 100, 237624.00),
(4, 13, 36, 237624.00),
(5, 2, 15, 236436.00),
(5, 4, 7, 236436.00),
(5, 15, 1, 236436.00),
(5, 16, 10, 236436.00),
(6, 2, 10, 236436.00),
(6, 16, 17, 236436.00),
(6, 19, 1, 236436.00),
(7, 4, 10, 142574.00),
(7, 10, 15, 142574.00),
(7, 13, 27, 142574.00),
(7, 15, 1, 142574.00),
(8, 14, 1, 178218.00),
(8, 18, 1, 178218.00),
(9, 14, 29, 212673.00),
(9, 18, 1, 212673.00),
(10, 12, 145, 234059.00),
(10, 15, 89, 234059.00),
(11, 13, 145, 199604.00),
(12, 17, 1, 212673.00),
(14, 10, 50, 200792.00),
(16, 17, 1, 199604.00),
(17, 9, 100, 186535.00),
(17, 12, 97, 186535.00),
(18, 12, 68, 225743.00),
(19, 17, 1, 237624.00),
(21, 14, 68, 146139.00),
(24, 18, 1, 199604.00),
(25, 14, 57, 343366.00),
(25, 15, 68, 343366.00),
(29, 11, 100, 92673.00),
(30, 11, 100, 212673.00);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ct_phieunhap`
--

CREATE TABLE `ct_phieunhap` (
  `maSach` int(11) NOT NULL,
  `maNhap` int(11) NOT NULL,
  `soLuongNhap` int(11) NOT NULL,
  `giaNhap` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `ct_phieunhap`
--

INSERT INTO `ct_phieunhap` (`maSach`, `maNhap`, `soLuongNhap`, `giaNhap`) VALUES
(1, 1, 20, 54455.45),
(1, 2, 10, 64699.54),
(1, 3, 10, 70464.84),
(1, 4, 14, 70464.84),
(1, 5, 1, 76743.88),
(2, 1, 30, 71287.13),
(2, 2, 24, 84697.58),
(3, 1, 15, 88118.81),
(4, 1, 43, 198019.80),
(5, 1, 27, 197029.70),
(6, 1, 20, 197029.70),
(6, 2, 10, 234094.69),
(7, 1, 15, 118811.88),
(7, 2, 16, 141162.63),
(7, 4, 17, 153741.48),
(7, 5, 2, 167441.22),
(8, 1, 23, 148514.85),
(9, 1, 12, 177227.72),
(9, 5, 3, 210567.58),
(10, 1, 24, 195049.50),
(10, 3, 17, 231741.98),
(11, 1, 27, 166336.63),
(12, 1, 19, 177227.72),
(12, 3, 71, 210567.58),
(13, 1, 24, 155445.54),
(14, 1, 19, 167326.73),
(15, 1, 21, 396039.60),
(15, 5, 4, 470542.10),
(16, 1, 17, 166336.63),
(16, 5, 65, 197627.68),
(17, 1, 18, 155445.54),
(17, 3, 81, 184687.77),
(18, 1, 18, 188118.81),
(18, 3, 81, 223507.50),
(18, 5, 18, 223507.50),
(19, 1, 19, 198019.80),
(20, 1, 14, 188118.81),
(21, 1, 19, 121782.18),
(21, 4, 61, 144691.70),
(22, 1, 17, 176237.62),
(23, 1, 15, 143564.36),
(24, 1, 10, 166336.63),
(25, 1, 10, 286138.61),
(26, 1, 21, 56435.64),
(26, 4, 10, 67052.25),
(27, 1, 18, 1994.06),
(27, 3, 31, 2369.18),
(27, 4, 18, 2369.18),
(28, 1, 16, 89108.91),
(28, 3, 61, 105871.97),
(29, 1, 25, 77227.72),
(30, 1, 20, 177227.72);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `danhmuc_tg`
--

CREATE TABLE `danhmuc_tg` (
  `maTacGia` int(11) NOT NULL,
  `maSach` int(11) NOT NULL,
  `trangThai` tinyint(4) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `danhmuc_tg`
--

INSERT INTO `danhmuc_tg` (`maTacGia`, `maSach`, `trangThai`) VALUES
(1, 2, 1),
(1, 6, 1),
(1, 7, 1),
(1, 12, 1),
(1, 19, 1),
(1, 23, 1),
(1, 24, 1),
(1, 29, 1),
(2, 12, 1),
(2, 18, 1),
(2, 22, 1),
(2, 23, 1),
(4, 15, 1),
(4, 26, 1),
(5, 3, 1),
(5, 9, 1),
(5, 24, 1),
(5, 25, 1),
(6, 6, 1),
(6, 29, 1),
(7, 1, 1),
(7, 7, 1),
(7, 13, 1),
(7, 16, 1),
(7, 22, 1),
(7, 30, 1),
(8, 4, 1),
(8, 14, 1),
(8, 21, 1),
(8, 26, 1),
(8, 30, 1),
(9, 9, 1),
(9, 27, 1),
(10, 20, 1),
(10, 28, 1),
(11, 17, 1),
(11, 20, 1),
(12, 8, 1),
(12, 10, 1),
(13, 5, 1),
(13, 27, 1),
(14, 11, 1),
(14, 15, 1),
(14, 16, 1),
(14, 25, 1),
(15, 8, 1),
(16, 10, 1),
(18, 12, 1),
(20, 11, 1),
(20, 17, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoadon`
--

CREATE TABLE `hoadon` (
  `maHD` int(11) NOT NULL,
  `ngayBan` datetime NOT NULL,
  `tongTien` decimal(10,2) NOT NULL,
  `maTK` int(11) NOT NULL,
  `maPT` int(11) NOT NULL,
  `maKM` int(11) DEFAULT NULL,
  `maKH` int(11) DEFAULT NULL,
  `trangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `hoadon`
--

INSERT INTO `hoadon` (`maHD`, `ngayBan`, `tongTien`, `maTK`, `maPT`, `maKM`, `maKH`, `trangThai`) VALUES
(1, '2025-05-07 05:09:22', 120713.60, 1, 1, 7, 1, 1),
(2, '2025-05-07 05:11:41', 6232884.50, 1, 1, 2, 1, 1),
(3, '2025-05-07 05:18:38', 65347.00, 1, 1, 1, 1, 1),
(4, '2025-05-07 15:50:47', 6230085.20, 1, 1, 4, 1, 1),
(5, '2025-05-01 15:53:54', 447923.00, 1, 1, 1, 1, 1),
(6, '2025-05-01 15:54:46', 6534700.00, 1, 1, 1, 1, 1),
(7, '2025-05-01 15:55:01', 23762400.00, 1, 1, 1, 1, 1),
(8, '2025-05-02 15:55:45', 31152550.00, 1, 1, 1, 1, 1),
(9, '2025-05-02 15:56:16', 18718847.00, 1, 1, 1, 1, 1),
(10, '2025-05-03 15:57:00', 6089105.00, 1, 1, 2, 1, 1),
(11, '2025-05-04 15:57:53', 30484600.00, 1, 1, 6, 1, 1),
(12, '2025-05-05 15:59:52', 53958656.80, 1, 1, 7, 9, 1),
(13, '2025-05-06 16:00:53', 41346542.00, 1, 1, 1, 1, 1),
(14, '2025-05-07 16:03:50', 35910792.00, 1, 1, 6, 1, 1),
(15, '2025-05-08 16:05:01', 38722182.40, 1, 1, 7, 1, 1),
(16, '2025-05-08 16:07:13', 7455659.00, 1, 2, 5, 5, 1),
(17, '2025-05-08 16:07:36', 649901.00, 1, 1, 1, 13, 1),
(18, '2025-05-08 16:08:32', 741387.00, 1, 1, 1, 15, 1),
(19, '2025-05-08 16:08:56', 387328.00, 1, 1, 1, 21, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khachhang`
--

CREATE TABLE `khachhang` (
  `maKH` int(11) NOT NULL,
  `tenKH` varchar(255) NOT NULL,
  `soDT` varchar(10) NOT NULL,
  `gioiTinh` varchar(4) NOT NULL,
  `trangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khachhang`
--

INSERT INTO `khachhang` (`maKH`, `tenKH`, `soDT`, `gioiTinh`, `trangThai`) VALUES
(1, 'Anonymous', 'x', 'x', 1),
(2, 'Nguyễn Văn An', '0987654321', 'Nam', 1),
(3, 'Trần Thị Bích Ngọc', '0901234567', 'Nữ', 1),
(4, 'Lê Hoàng Long', '0912345678', 'Nam', 1),
(5, 'Phạm Minh Thư', '0938765432', 'Nữ', 1),
(6, 'Đỗ Văn Hùng', '0329876543', 'Nam', 1),
(7, 'Nguyễn Thị Kim Oanh', '0356789123', 'Nữ', 1),
(8, 'Trần Quốc Bảo', '0365123789', 'Nam', 1),
(9, 'Lê Thị Hồng Nhung', '0371234987', 'Nữ', 1),
(10, 'Phan Văn Đức', '0384567891', 'Nam', 1),
(11, 'Võ Thị Thu Trang', '0391122334', 'Nữ', 1),
(12, 'Bùi Anh Tuấn', '0709874567', 'Nam', 1),
(13, 'Nguyễn Hà My', '0769876543', 'Nữ', 1),
(14, 'Trịnh Công Sơn', '0775566778', 'Nam', 1),
(15, 'Lưu Thị Thu Hà', '0788899001', 'Nữ', 1),
(16, 'Hồ Quang Hiếu', '0793344556', 'Nam', 1),
(17, 'Mai Phương Thảo', '0814455667', 'Nữ', 1),
(18, 'Nguyễn Đức Huy', '0825566778', 'Nam', 1),
(19, 'Đặng Thị Hồng Gấm', '0836677889', 'Nữ', 1),
(20, 'Trần Hữu Tài', '0847788990', 'Nam', 1),
(21, 'Vũ Thị Mai Lan', '0858899001', 'Nữ', 1),
(22, 'nguyen hung manh', '0892348923', 'Nam', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khuyenmai`
--

CREATE TABLE `khuyenmai` (
  `maKM` int(11) NOT NULL,
  `tenKM` varchar(255) NOT NULL,
  `dieuKienGiam` varchar(255) NOT NULL,
  `giaTriGiam` decimal(10,2) NOT NULL,
  `ngayBatDau` datetime NOT NULL,
  `ngayKetThuc` datetime NOT NULL,
  `trangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khuyenmai`
--

INSERT INTO `khuyenmai` (`maKM`, `tenKM`, `dieuKienGiam`, `giaTriGiam`, `ngayBatDau`, `ngayKetThuc`, `trangThai`) VALUES
(1, 'anonymous', 'Đơn hàng tối thiểu: 0đ', 5000.00, '2021-05-01 04:59:18', '2031-05-09 04:59:18', 0),
(2, 'Giảm 50% cho đơn từ: 1.000.000đ', 'Đơn hàng tối thiểu: 1.000.000đ', 0.50, '2025-05-01 05:00:36', '2025-06-05 05:00:36', 1),
(3, 'Giảm 10% cho đơn từ 0đ', 'Đơn hàng tối thiểu: 0đ', 0.10, '2025-05-01 05:01:36', '2025-07-03 05:01:36', 1),
(4, 'Giảm 15% cho đơn từ 50.000đ', 'Đơn hàng tối thiểu: 50.000đ', 0.15, '2025-05-01 05:02:10', '2025-07-10 05:02:10', 1),
(5, 'Giảm 20k sách: Dế Mèn Phiêu Lưu Ký', 'Sách theo Sách: Dế Mèn Phiêu Lưu Ký ', 20000.00, '2025-05-01 05:02:51', '2025-07-10 05:02:51', 1),
(6, 'Giảm 50k cho Thể loại: Thiếu Nhi', 'Sách theo Thể loại: Thiếu nhi', 50000.00, '2025-05-01 05:04:09', '2025-05-31 05:04:09', 1),
(7, 'Giảm 20% Sách của tác giả: Tô Hoài', 'Sách theo Tác giả: Tô Hoài', 0.20, '2025-05-01 05:05:00', '2025-07-10 05:05:00', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `km_sach`
--

CREATE TABLE `km_sach` (
  `maKM` int(11) NOT NULL,
  `maSach` int(11) NOT NULL,
  `trangThai` tinyint(4) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `km_sach`
--

INSERT INTO `km_sach` (`maKM`, `maSach`, `trangThai`) VALUES
(5, 1, 1),
(6, 1, 1),
(6, 7, 1),
(6, 10, 1),
(6, 11, 1),
(6, 16, 1),
(6, 19, 1),
(6, 21, 1),
(6, 24, 1),
(6, 27, 1),
(6, 29, 1),
(6, 30, 1),
(7, 1, 1),
(7, 7, 1),
(7, 13, 1),
(7, 16, 1),
(7, 22, 1),
(7, 30, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `maNCC` int(11) NOT NULL,
  `tenNCC` varchar(255) NOT NULL,
  `diaChi` varchar(255) NOT NULL,
  `soDT` varchar(15) NOT NULL,
  `email` varchar(100) NOT NULL,
  `trangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhacungcap`
--

INSERT INTO `nhacungcap` (`maNCC`, `tenNCC`, `diaChi`, `soDT`, `email`, `trangThai`) VALUES
(1, 'Công ty Sách Phương Nam', '940 Quang Trung, Gò Vấp, TP.HCM', '0238456789', 'phuongnam@sach.vn', 1),
(2, 'Công ty TNHH Alpha Books', '187 Nguyễn Lương Bằng, Hà Nội', '0243567431', 'contact@alphabooks.vn', 1),
(3, 'Công ty CP Fahasa', '60-62 Lê Lợi, Quận 1, TP.HCM', '0283822154', 'cskh@fahasa.com', 1),
(4, 'Công ty Nhã Nam', '107 B3 Tô Hiệu, Cầu Giấy, Hà Nội', '0243795617', 'info@nhanam.vn', 1),
(5, 'Công ty CP Văn hóa Huy Hoàng', '116 Nguyễn Thị Minh Khai, TP.HCM', '0283920521', 'huyhoang@sach.vn', 1),
(6, 'Công ty Minh Long Book', '38 Nguyễn Thái Học, Ba Đình, HN', '0243834478', 'minhlong@sach.vn', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhanvien`
--

CREATE TABLE `nhanvien` (
  `maNV` int(11) NOT NULL,
  `hoTen` varchar(255) NOT NULL,
  `ngaySinh` date NOT NULL,
  `gioiTinh` varchar(10) NOT NULL,
  `soDT` varchar(15) NOT NULL,
  `maTK` int(11) DEFAULT NULL,
  `trangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhanvien`
--

INSERT INTO `nhanvien` (`maNV`, `hoTen`, `ngaySinh`, `gioiTinh`, `soDT`, `maTK`, `trangThai`) VALUES
(1, 'Nguyễn Hùng Mạnh', '1990-01-01', 'Nam', '0911000001', 1, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhaxb`
--

CREATE TABLE `nhaxb` (
  `maNXB` int(11) NOT NULL,
  `tenNXB` varchar(255) NOT NULL,
  `diaChi` varchar(255) NOT NULL,
  `soDT` varchar(15) NOT NULL,
  `email` varchar(100) NOT NULL,
  `trangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhaxb`
--

INSERT INTO `nhaxb` (`maNXB`, `tenNXB`, `diaChi`, `soDT`, `email`, `trangThai`) VALUES
(1, 'NXB Giáo Dục Việt Nam', '81 Trần Hưng Đạo, Hà Nội', '0438226432', 'giaoduc@nxb.vn', 1),
(2, 'NXB Trẻ', '161B Lý Chính Thắng, TP.HCM', '0838480109', 'info@nxbtre.vn', 1),
(3, 'NXB Kim Đồng', '55 Quang Trung, Hà Nội', '0439445670', 'contact@kimdong.vn', 1),
(4, 'NXB Văn Học', '65 Nguyễn Du, Hà Nội', '0439438389', 'vanhoc@nxb.vn', 1),
(5, 'NXB Hội Nhà Văn', '9 Trần Quốc Toản, Hà Nội', '0437346719', 'hoinhavan@nxb.vn', 1),
(6, 'NXB Lao Động', '175 Giảng Võ, Hà Nội', '0435122458', 'laodong@nxb.vn', 1),
(7, 'NXB Phụ Nữ', '39 Hàng Chuối, Hà Nội', '0439718853', 'phunu@nxb.vn', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhomquyen`
--

CREATE TABLE `nhomquyen` (
  `maRole` int(11) NOT NULL,
  `tenrole` varchar(255) NOT NULL,
  `trangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhomquyen`
--

INSERT INTO `nhomquyen` (`maRole`, `tenrole`, `trangThai`) VALUES
(1, 'Admin', 1),
(2, 'Nhân viên', 0),
(3, 'Khách hàng', 0),
(4, 'Nhân viên bán hàng', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phanloai`
--

CREATE TABLE `phanloai` (
  `maSach` int(11) NOT NULL,
  `maTheLoai` int(11) NOT NULL,
  `trangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phanloai`
--

INSERT INTO `phanloai` (`maSach`, `maTheLoai`, `trangThai`) VALUES
(1, 1, 1),
(2, 4, 1),
(3, 6, 1),
(4, 10, 1),
(5, 12, 1),
(6, 5, 1),
(7, 1, 1),
(7, 2, 1),
(7, 6, 1),
(8, 14, 1),
(8, 19, 1),
(9, 2, 1),
(9, 4, 1),
(10, 1, 1),
(10, 8, 1),
(10, 15, 1),
(11, 1, 1),
(11, 6, 1),
(11, 20, 1),
(12, 4, 1),
(12, 8, 1),
(13, 5, 1),
(14, 9, 1),
(15, 5, 1),
(15, 12, 1),
(16, 1, 1),
(16, 2, 1),
(17, 8, 1),
(17, 16, 1),
(18, 8, 1),
(18, 16, 1),
(19, 1, 1),
(20, 3, 1),
(20, 4, 1),
(21, 1, 1),
(21, 2, 1),
(22, 2, 1),
(22, 6, 1),
(23, 3, 1),
(23, 5, 1),
(24, 1, 1),
(24, 2, 1),
(25, 4, 1),
(25, 15, 1),
(26, 7, 1),
(26, 13, 1),
(27, 1, 1),
(27, 2, 1),
(28, 5, 1),
(29, 1, 1),
(29, 2, 1),
(30, 1, 1),
(30, 5, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieunhap`
--

CREATE TABLE `phieunhap` (
  `maNhap` int(11) NOT NULL,
  `ngayNhap` datetime NOT NULL,
  `tongTien` decimal(10,2) NOT NULL,
  `maNCC` int(11) NOT NULL,
  `maTK` int(11) NOT NULL,
  `trangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phieunhap`
--

INSERT INTO `phieunhap` (`maNhap`, `ngayNhap`, `tongTien`, `maNCC`, `maTK`, `trangThai`) VALUES
(1, '2025-05-07 04:49:41', 92933911.65, 1, 1, 1),
(2, '2025-05-07 16:10:36', 7279286.30, 3, 1, 1),
(3, '2025-05-07 16:11:23', 59190011.86, 5, 1, 1),
(4, '2025-05-07 16:12:06', 13139474.36, 4, 1, 1),
(5, '2025-05-07 16:13:02', 19794431.66, 2, 1, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phuongthuc_tt`
--

CREATE TABLE `phuongthuc_tt` (
  `maPT` int(11) NOT NULL,
  `tenPTTT` varchar(255) NOT NULL,
  `trangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phuongthuc_tt`
--

INSERT INTO `phuongthuc_tt` (`maPT`, `tenPTTT`, `trangThai`) VALUES
(1, 'Tiền mặt', 1),
(2, 'Chuyển khoản', 1),
(3, 'Thẻ tín dụng', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sach`
--

CREATE TABLE `sach` (
  `maSach` int(11) NOT NULL,
  `tenSach` varchar(255) NOT NULL,
  `soLuong` int(11) NOT NULL DEFAULT 0,
  `giaBan` decimal(10,2) NOT NULL DEFAULT 0.00,
  `namXB` int(11) NOT NULL,
  `maVung` int(11) NOT NULL,
  `maNXB` int(11) NOT NULL,
  `anh` varchar(255) DEFAULT NULL,
  `trangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sach`
--

INSERT INTO `sach` (`maSach`, `tenSach`, `soLuong`, `giaBan`, `namXB`, `maVung`, `maNXB`, `anh`, `trangThai`) VALUES
(1, 'Dế Mèn Phiêu Lưu Ký ', 35, 84418.27, 2021, 1, 3, 'DeMenPhieuLuuKy.jpg', 1),
(2, 'Tôi Thấy Hoa Vàng Trên Cỏ Xanh', 24, 93167.34, 2020, 2, 4, 'ToiThayHoaVangTrenCoXanh.jpg', 1),
(3, 'Nhà Giả Kim ', 100, 105742.57, 2024, 5, 5, 'NhaGiaKim.jpg', 1),
(4, 'Tuổi Trẻ Đáng Giá Bao Nhiêu ', 45, 237623.76, 2019, 6, 6, 'TuoiTreDangGiaBaoNhieu.jpg', 1),
(5, 'Đắc Nhân Tâm ', 78, 236435.64, 2022, 8, 6, 'DacNhanTam.jpg', 1),
(6, '7 Thói Quen Hiệu Quả', 68, 257504.16, 2019, 6, 5, '7ThoiQuenHieuQua.jpg', 1),
(7, 'Muôn Kiếp Nhân Sinh ', 35, 184185.34, 2010, 7, 5, 'MuonKiepNhanSinh.jpg', 1),
(8, 'Hiểu Về Trái Tim ', 20, 178217.82, 2014, 3, 2, 'HieuVeTraiTim.jpg', 1),
(9, 'Hành Trình Về Phương Đông ', 79, 231624.34, 2010, 5, 6, 'HanhTrinhVePhuongDong.jpg', 1),
(10, 'Totto-chan: Cô bé bên cửa sổ ', 56, 234059.40, 2015, 4, 7, 'TottoChanBenCuaSo.jpg', 1),
(11, 'Chiến Binh Cầu Vồng ', 89, 199603.96, 2014, 7, 2, 'ChienBinhCauVong.jpg', 1),
(12, 'Cà Phê Cùng Tony ', 89, 212673.26, 2014, 7, 7, 'CaPheCungTony.jpg', 1),
(13, 'Bố Già ', 24, 186534.65, 2015, 4, 4, 'BoGia.jpg', 1),
(14, 'Không Gia Đình ', 37, 200792.08, 2012, 5, 3, 'KhongGiaDinh.jpg', 1),
(15, 'Sherlock Holmes Toàn Tập ', 25, 517596.31, 2005, 8, 2, 'SherlockHolmesToanTap.jpg', 1),
(16, 'Sapiens - Lược Sử Loài Người ', 80, 217390.45, 2005, 6, 5, 'SapiensLuocSuLoaiNguoi.jpg', 1),
(17, 'Bí Mật Tư Duy Triệu Phú ', 81, 186534.65, 2010, 3, 2, 'BiMatTuDuyTrieuPhu.jpg', 1),
(18, 'Hoàng Tử Bé ', 99, 245858.25, 2016, 2, 6, 'HoangTuBe.jpg', 1),
(19, '13 Nguyên Tắc Nghĩ Giàu Làm Giàu ', 89, 237623.76, 2014, 1, 1, '13NguyenTacNghiGiauLamGiau.jpg', 1),
(20, 'Cánh Đồng Bất Tận ', 90, 225742.57, 2012, 1, 1, 'CanhDongBatTan.jpg', 1),
(21, 'Dấn Thân ', 61, 159160.87, 2012, 1, 2, 'DanThan.jpg', 1),
(22, 'Mỗi Ngày Tiết Kiệm 1 Giờ ', 179, 211485.14, 2012, 7, 5, 'MoiNgayTietKiemMotGio.jpg', 1),
(23, 'Tâm Lý Học Thành Công ', 15, 172277.23, 2019, 4, 3, 'TamLyHocThanhCong.jpg', 1),
(24, 'Những Lá Thư Chưa Gửi ', 57, 199603.96, 2012, 6, 5, 'NhungLaThuKhongGui.jpg', 1),
(25, 'Phi Lý Trí', 46, 343366.33, 2011, 2, 6, 'PhiLyTri.jpg', 1),
(26, 'Không Diệt Không Sinh Đừng Sợ Hãi ', 31, 73757.48, 2012, 7, 6, 'KhongDietKhongSinhDungSoHai.jpg', 1),
(27, 'Lược Sử Thời Gian ', 67, 2606.10, 2015, 3, 6, 'LuocSuThoiGian.jpg', 1),
(28, 'Nghệ Thuật Tinh Tế Của Việc Đếch Quan Tâm ', 77, 106930.69, 2016, 2, 4, 'NgheThuatTinhTeCuaViecDechQuanTam.jpg', 1),
(29, 'Hài Hước Một Chút Thế Giới Sẽ Khác ', 0, 92673.26, 2045, 5, 1, 'HaiHuocMotChutTheGioiSeKhacDi.jpg', 1),
(30, 'Nhà Lãnh Đạo Không Chức Danh ', 0, 212673.26, 2015, 4, 6, 'NhaLanhDaoKhongChucDanh.jpg', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tacgia`
--

CREATE TABLE `tacgia` (
  `maTacGia` int(11) NOT NULL,
  `tenTacGia` varchar(255) NOT NULL,
  `trangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tacgia`
--

INSERT INTO `tacgia` (`maTacGia`, `tenTacGia`, `trangThai`) VALUES
(1, 'Nguyễn Nhật Ánh', 1),
(2, 'J.K. Rowling', 1),
(3, 'Dan Brown', 1),
(4, 'Haruki Murakami', 1),
(5, 'Paulo Coelho', 1),
(6, 'Nguyễn Huy Thiệp', 1),
(7, 'Tô Hoài', 1),
(8, 'Victor Hugo', 1),
(9, 'Ernest Hemingway', 1),
(10, 'Lev Tolstoy', 1),
(11, 'Kim Dung', 1),
(12, 'Hồ Biểu Chánh', 1),
(13, 'George Orwell', 1),
(14, 'Jane Austen', 1),
(15, 'Nam Cao', 1),
(16, 'Lỗ Tấn', 1),
(17, 'Nguyễn Du', 1),
(18, 'Nguyễn Minh Châu', 1),
(19, 'Antoine de Saint-Exupéry', 1),
(20, 'Franz Kafka', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

CREATE TABLE `taikhoan` (
  `maTK` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `maRole` int(11) NOT NULL,
  `trangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`maTK`, `username`, `password`, `maRole`, `trangThai`) VALUES
(1, 'admin', '123456', 1, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `theloai`
--

CREATE TABLE `theloai` (
  `maTheloai` int(11) NOT NULL,
  `tenTheloai` varchar(255) NOT NULL,
  `trangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `theloai`
--

INSERT INTO `theloai` (`maTheloai`, `tenTheloai`, `trangThai`) VALUES
(1, 'Thiếu nhi', 1),
(2, 'Văn học', 1),
(3, 'Triết học', 1),
(4, 'Kỹ năng sống', 1),
(5, 'Phát triển bản thân', 1),
(6, 'Tâm linh', 1),
(7, 'Thiền/Chánh niệm', 1),
(8, 'Tiểu thuyết', 1),
(9, 'Trinh thám', 1),
(10, 'Lịch sử', 1),
(11, 'Kinh doanh', 1),
(12, 'Khoa học viễn tưởng', 1),
(13, 'Tâm lý học', 1),
(14, 'Lãng mạn', 1),
(15, 'Hài hước', 1),
(16, 'Hồi ký', 1),
(17, 'Tự truyện', 1),
(18, 'Lịch sử - Chính trị', 1),
(19, 'Nghệ thuật', 1),
(20, 'Tài chính - Đầu tư', 1),
(21, 'hihi', 0),
(22, 'hihi', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `vitrivung`
--

CREATE TABLE `vitrivung` (
  `maVung` int(11) NOT NULL,
  `tenVung` varchar(255) NOT NULL,
  `trangThai` tinyint(4) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `vitrivung`
--

INSERT INTO `vitrivung` (`maVung`, `tenVung`, `trangThai`) VALUES
(1, 'Vùng A', 1),
(2, 'Vùng B', 1),
(3, 'Vùng C', 1),
(4, 'Vùng D', 1),
(5, 'Vùng E', 1),
(6, 'Vùng F', 1),
(7, 'Vùng G', 1),
(8, 'Vùng H', 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chitietquyen`
--
ALTER TABLE `chitietquyen`
  ADD PRIMARY KEY (`maRole`,`maChucNang`,`hanhDong`,`trangThai`),
  ADD KEY `CHUCNANG_CHITIETQUYEN` (`maChucNang`);

--
-- Chỉ mục cho bảng `chucnang`
--
ALTER TABLE `chucnang`
  ADD PRIMARY KEY (`maChucNang`);

--
-- Chỉ mục cho bảng `ct_hoadon`
--
ALTER TABLE `ct_hoadon`
  ADD PRIMARY KEY (`maSach`,`maHD`),
  ADD KEY `HOADON_CT_HOADON` (`maHD`);

--
-- Chỉ mục cho bảng `ct_phieunhap`
--
ALTER TABLE `ct_phieunhap`
  ADD PRIMARY KEY (`maSach`,`maNhap`),
  ADD KEY `PHIEUNHAP_CT_PHIEUNHAP` (`maNhap`);

--
-- Chỉ mục cho bảng `danhmuc_tg`
--
ALTER TABLE `danhmuc_tg`
  ADD PRIMARY KEY (`maTacGia`,`maSach`,`trangThai`),
  ADD KEY `DANHMUC_TG_SACH` (`maSach`);

--
-- Chỉ mục cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`maHD`),
  ADD KEY `HOADON_NHANVIEN` (`maTK`),
  ADD KEY `HOADON_PHUONGTHUC_TT` (`maPT`),
  ADD KEY `HOADON_KHUYENMAI` (`maKM`),
  ADD KEY `KH_HD` (`maKH`);

--
-- Chỉ mục cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`maKH`);

--
-- Chỉ mục cho bảng `khuyenmai`
--
ALTER TABLE `khuyenmai`
  ADD PRIMARY KEY (`maKM`);

--
-- Chỉ mục cho bảng `km_sach`
--
ALTER TABLE `km_sach`
  ADD PRIMARY KEY (`maKM`,`maSach`,`trangThai`),
  ADD KEY `km2` (`maSach`);

--
-- Chỉ mục cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`maNCC`);

--
-- Chỉ mục cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`maNV`),
  ADD KEY `NHANVIEN` (`maTK`);

--
-- Chỉ mục cho bảng `nhaxb`
--
ALTER TABLE `nhaxb`
  ADD PRIMARY KEY (`maNXB`);

--
-- Chỉ mục cho bảng `nhomquyen`
--
ALTER TABLE `nhomquyen`
  ADD PRIMARY KEY (`maRole`);

--
-- Chỉ mục cho bảng `phanloai`
--
ALTER TABLE `phanloai`
  ADD PRIMARY KEY (`maSach`,`maTheLoai`),
  ADD KEY `THELOAI_PHANLOAI` (`maTheLoai`);

--
-- Chỉ mục cho bảng `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD PRIMARY KEY (`maNhap`),
  ADD KEY `PHIEUNHAP_NHACUNGCAP` (`maNCC`),
  ADD KEY `PHIEUNHAP_NHANVIEN` (`maTK`);

--
-- Chỉ mục cho bảng `phuongthuc_tt`
--
ALTER TABLE `phuongthuc_tt`
  ADD PRIMARY KEY (`maPT`);

--
-- Chỉ mục cho bảng `sach`
--
ALTER TABLE `sach`
  ADD PRIMARY KEY (`maSach`),
  ADD KEY `SACH_VITRIVUNG` (`maVung`),
  ADD KEY `SACH_NHAXB` (`maNXB`);

--
-- Chỉ mục cho bảng `tacgia`
--
ALTER TABLE `tacgia`
  ADD PRIMARY KEY (`maTacGia`);

--
-- Chỉ mục cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`maTK`),
  ADD KEY `TAIKHOAN_NHOMQUYEN` (`maRole`);

--
-- Chỉ mục cho bảng `theloai`
--
ALTER TABLE `theloai`
  ADD PRIMARY KEY (`maTheloai`);

--
-- Chỉ mục cho bảng `vitrivung`
--
ALTER TABLE `vitrivung`
  ADD PRIMARY KEY (`maVung`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `chucnang`
--
ALTER TABLE `chucnang`
  MODIFY `maChucNang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  MODIFY `maHD` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `maKH` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT cho bảng `khuyenmai`
--
ALTER TABLE `khuyenmai`
  MODIFY `maKM` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  MODIFY `maNCC` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  MODIFY `maNV` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `nhaxb`
--
ALTER TABLE `nhaxb`
  MODIFY `maNXB` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `nhomquyen`
--
ALTER TABLE `nhomquyen`
  MODIFY `maRole` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `phieunhap`
--
ALTER TABLE `phieunhap`
  MODIFY `maNhap` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `phuongthuc_tt`
--
ALTER TABLE `phuongthuc_tt`
  MODIFY `maPT` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `sach`
--
ALTER TABLE `sach`
  MODIFY `maSach` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT cho bảng `tacgia`
--
ALTER TABLE `tacgia`
  MODIFY `maTacGia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  MODIFY `maTK` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `theloai`
--
ALTER TABLE `theloai`
  MODIFY `maTheloai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT cho bảng `vitrivung`
--
ALTER TABLE `vitrivung`
  MODIFY `maVung` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chitietquyen`
--
ALTER TABLE `chitietquyen`
  ADD CONSTRAINT `CHUCNANG_CHITIETQUYEN` FOREIGN KEY (`maChucNang`) REFERENCES `chucnang` (`maChucNang`),
  ADD CONSTRAINT `NHOMQUYEN_CHITIETQUYEN` FOREIGN KEY (`maRole`) REFERENCES `nhomquyen` (`maRole`);

--
-- Các ràng buộc cho bảng `ct_hoadon`
--
ALTER TABLE `ct_hoadon`
  ADD CONSTRAINT `HOADON_CT_HOADON` FOREIGN KEY (`maHD`) REFERENCES `hoadon` (`maHD`),
  ADD CONSTRAINT `PHIENBANSACH_CT_HOADON` FOREIGN KEY (`maSach`) REFERENCES `sach` (`maSach`);

--
-- Các ràng buộc cho bảng `ct_phieunhap`
--
ALTER TABLE `ct_phieunhap`
  ADD CONSTRAINT `PHIEUNHAP_CT_PHIEUNHAP` FOREIGN KEY (`maNhap`) REFERENCES `phieunhap` (`maNhap`),
  ADD CONSTRAINT `SACH_CT_PHIEUNHAP` FOREIGN KEY (`maSach`) REFERENCES `sach` (`maSach`);

--
-- Các ràng buộc cho bảng `danhmuc_tg`
--
ALTER TABLE `danhmuc_tg`
  ADD CONSTRAINT `DANHMUC_TG_SACH` FOREIGN KEY (`maSach`) REFERENCES `sach` (`maSach`),
  ADD CONSTRAINT `DANHMUC_TG_TACGIA` FOREIGN KEY (`maTacGia`) REFERENCES `tacgia` (`maTacGia`);

--
-- Các ràng buộc cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD CONSTRAINT `HOADON_KHUYENMAI` FOREIGN KEY (`maKM`) REFERENCES `khuyenmai` (`maKM`),
  ADD CONSTRAINT `HOADON_NHANVIEN` FOREIGN KEY (`maTK`) REFERENCES `taikhoan` (`maTK`),
  ADD CONSTRAINT `HOADON_PHUONGTHUC_TT` FOREIGN KEY (`maPT`) REFERENCES `phuongthuc_tt` (`maPT`),
  ADD CONSTRAINT `KH_HD` FOREIGN KEY (`maKH`) REFERENCES `khachhang` (`maKH`);

--
-- Các ràng buộc cho bảng `km_sach`
--
ALTER TABLE `km_sach`
  ADD CONSTRAINT `km1` FOREIGN KEY (`maKM`) REFERENCES `khuyenmai` (`maKM`),
  ADD CONSTRAINT `km2` FOREIGN KEY (`maSach`) REFERENCES `sach` (`maSach`);

--
-- Các ràng buộc cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD CONSTRAINT `NHANVIEN` FOREIGN KEY (`maTK`) REFERENCES `taikhoan` (`maTK`);

--
-- Các ràng buộc cho bảng `phanloai`
--
ALTER TABLE `phanloai`
  ADD CONSTRAINT `SACH_PHANLOAI` FOREIGN KEY (`maSach`) REFERENCES `sach` (`maSach`),
  ADD CONSTRAINT `THELOAI_PHANLOAI` FOREIGN KEY (`maTheLoai`) REFERENCES `theloai` (`maTheloai`);

--
-- Các ràng buộc cho bảng `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD CONSTRAINT `PHIEUNHAP_NHACUNGCAP` FOREIGN KEY (`maNCC`) REFERENCES `nhacungcap` (`maNCC`),
  ADD CONSTRAINT `PHIEUNHAP_NHANVIEN` FOREIGN KEY (`maTK`) REFERENCES `taikhoan` (`maTK`);

--
-- Các ràng buộc cho bảng `sach`
--
ALTER TABLE `sach`
  ADD CONSTRAINT `SACH_NHAXB` FOREIGN KEY (`maNXB`) REFERENCES `nhaxb` (`maNXB`),
  ADD CONSTRAINT `SACH_VITRIVUNG` FOREIGN KEY (`maVung`) REFERENCES `vitrivung` (`maVung`);

--
-- Các ràng buộc cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD CONSTRAINT `TAIKHOAN_NHOMQUYEN` FOREIGN KEY (`maRole`) REFERENCES `nhomquyen` (`maRole`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
