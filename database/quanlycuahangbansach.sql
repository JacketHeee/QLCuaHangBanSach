CREATE DATABASE quanlycuahangbansach;
USE quanlycuahangbansach;

CREATE TABLE SACH(
	maSach INT AUTO_INCREMENT PRIMARY KEY,
	tenSach VARCHAR(255) NOT NULL,
	soLuong INT DEFAULT 0 NOT NULL,
	giaBan DECIMAL(10, 2) NOT NULL,
	namXB INT NOT NULL,
	maVung INT NOT NULL,
	maNXB INT NOT NULL,
	trangThai TINYINT DEFAULT 1
);

CREATE TABLE PHANLOAI(
	maSach INT,
   maTheLoai INT,
   trangThai TINYINT DEFAULT 1,
   PRIMARY KEY (maSach, maTheLoai) 
);

CREATE TABLE THELOAI(
  maTheloai INT AUTO_INCREMENT PRIMARY KEY,
  tenTheloai VARCHAR(255) NOT NULL,
  trangThai TINYINT DEFAULT 1
);

CREATE TABLE VITRIVUNG(
   maVung INT AUTO_INCREMENT PRIMARY KEY,
	tenVung VARCHAR(255) NOT NULL,
	trangThai TINYINT DEFAULT 1
);

CREATE TABLE TACGIA(
  maTacGia INT AUTO_INCREMENT PRIMARY KEY,
  tenTacGia VARCHAR(255) NOT NULL,
  trangThai TINYINT DEFAULT 1
);

CREATE TABLE DANHMUC_TG(
  maTacGia INT,
  maSach INT,
  trangThai TINYINT DEFAULT 1,
  PRIMARY KEY (maTacGia, maSach, trangThai)
);

CREATE TABLE NHAXB (
  maNXB INT AUTO_INCREMENT PRIMARY KEY,
  tenNXB VARCHAR(255) NOT NULL,
  diaChi VARCHAR(255) NOT NULL,
  soDT VARCHAR(15) NOT NULL,
  email VARCHAR(100) NOT NULL,
  trangThai TINYINT DEFAULT 1
);

CREATE TABLE NHACUNGCAP (
  maNCC INT AUTO_INCREMENT PRIMARY KEY,
  tenNCC VARCHAR(255) NOT NULL,
  diaChi VARCHAR(255) NOT NULL,
  soDT VARCHAR(15) NOT NULL,
  email VARCHAR(100) NOT NULL,
  trangThai TINYINT DEFAULT 1
);

CREATE TABLE PHIEUNHAP (
  maNhap INT AUTO_INCREMENT PRIMARY KEY,
  ngayNhap DATETIME NOT NULL,
  tongTien decimal(10, 2) NOT NULL,
  maNCC INT NOT NULL,
  maTK INT NOT NULL,
  trangThai TINYINT DEFAULT 1
);

CREATE TABLE CT_PHIEUNHAP (
  maSach INT,
  maNhap INT,
  soLuongNhap INT NOT NULL,
  giaNhap decimal(10, 2) NOT NULL,
  PRIMARY KEY (maSach, maNhap)
);

CREATE TABLE HOADON (
  maHD INT AUTO_INCREMENT PRIMARY KEY,
  ngayBan DATETIME NOT NULL,
  tongTien decimal(10, 2) NOT NULL,
  maTK INT NOT NULL,
  maPT INT NOT NULL,
  maKM INT DEFAULT NULL,
  maKH INT DEFAULT NULL,
  trangThai TINYINT DEFAULT 1
);

CREATE TABLE CT_HOADON (
  maSach INT,
  maHD INT,
  soLuong INT NOT NULL,
  giaBan decimal(10, 2) NOT NULL,
  PRIMARY KEY (maSach, maHD)
);

CREATE TABLE PHUONGTHUC_TT ( 
  maPT INT AUTO_INCREMENT PRIMARY KEY,
  tenPTTT VARCHAR(255) NOT NULL,
  trangThai TINYINT DEFAULT 1
);

CREATE TABLE KHUYENMAI ( 
  maKM INT AUTO_INCREMENT PRIMARY KEY,
  tenKM VARCHAR(255) NOT NULL,
  dieuKienGiam VARCHAR(255) NOT NULL,
  giaTriGiam decimal(10, 2) NOT NULL,
  ngayBatDau DATETIME NOT NULL,
  ngayKetThuc DATETIME NOT NULL,
  trangThai TINYINT DEFAULT 1
);

CREATE TABLE KM_PHIENBANSACH (
  maKM INT,
  maSach INT,
  PRIMARY KEY (maKM, maSach)
);

CREATE TABLE NHANVIEN ( 
  maNV INT AUTO_INCREMENT PRIMARY KEY,
  hoTen VARCHAR(255) NOT NULL,
  ngaySinh DATE NOT NULL,
  gioiTinh VARCHAR(10) NOT NULL,
  soDT VARCHAR(15) NOT NULL,
  maTK INT DEFAULT NULL,
  trangThai TINYINT DEFAULT 1
);

CREATE TABLE TAIKHOAN ( 
  maTK INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  maRole INT NOT NULL,
  trangThai TINYINT DEFAULT 1
);

CREATE TABLE NHOMQUYEN ( 
  maRole INT AUTO_INCREMENT PRIMARY KEY,
  tenrole VARCHAR(255) NOT NULL,
  trangThai TINYINT DEFAULT 1
);

CREATE TABLE CHITIETQUYEN(
  maRole INT,
  maChucNang INT,
  hanhDong VARCHAR(255) NOT NULL,
  trangThai TINYINT DEFAULT 1,
  PRIMARY KEY (maRole, maChucNang, hanhDong, trangThai)
);

CREATE TABLE CHUCNANG ( 
  maChucNang INT AUTO_INCREMENT PRIMARY KEY,
  tenChucNang VARCHAR(255) NOT NULL,
  trangThai TINYINT DEFAULT 1
);

CREATE TABLE KHACHHANG ( 
  maKH INT AUTO_INCREMENT PRIMARY KEY,
  tenKH VARCHAR(255) NOT NULL,
  soDT VARCHAR(10) NOT NULL,
  gioiTinh VARCHAR(4) NOT NULL,
  trangThai TINYINT DEFAULT 1
);

-- Dữ liệu cho bảng VITRIVUNG
INSERT INTO VITRIVUNG (tenVung) VALUES
('Kệ A1'), ('Kệ B2'), ('Kệ C3'), ('Kệ D4'), ('Kệ E5');

-- Dữ liệu cho bảng THELOAI
INSERT INTO THELOAI (tenTheloai) VALUES
('Tiểu thuyết'), ('Khoa học'), ('Lịch sử'), ('Truyện tranh'), ('Kinh tế');

-- Dữ liệu cho bảng TACGIA
INSERT INTO TACGIA (tenTacGia) VALUES
('Nguyễn Nhật Ánh'), ('J.K. Rowling'), ('George Orwell'), ('Haruki Murakami'), ('Dale Carnegie');

-- Dữ liệu cho bảng NHAXB
INSERT INTO NHAXB (tenNXB, diaChi, soDT, email) VALUES
('NXB Trẻ', 'HCM, VN', '0901234567', 'nxbt@gmail.com'),
('NXB Kim Đồng', 'Hà Nội, VN', '0912345678', 'nxbkd@gmail.com'),
('NXB Văn Học', 'HCM, VN', '0923456789', 'nxbvh@gmail.com'),
('NXB Alpha Books', 'Hà Nội, VN', '0934567890', 'nxbab@gmail.com'),
('NXB Thế Giới', 'Đà Nẵng, VN', '0945678901', 'nxbtg@gmail.com');

-- Dữ liệu cho bảng SACH
INSERT INTO SACH (tenSach, soLuong, giaBan, namXB, maVung, maNXB) VALUES
('Đắc Nhân Tâm', 2, 2000, 2019, 1, 5),
('Harry Potter',2, 2000, 2000, 2, 2),
('1984', 1949,2, 2000, 3, 3),
('Rừng Na Uy',2, 2000, 1987, 4, 4),
('Tôi thấy hoa vàng trên cỏ xanh',2, 2000, 2015, 5, 1);

-- Dữ liệu cho bảng PHANLOAI
INSERT INTO PHANLOAI (maSach, maTheLoai) VALUES
(1, 5), (2, 4), (3, 3), (4, 2), (5, 1);

-- Dữ liệu cho bảng DANHMUC_TG
INSERT INTO DANHMUC_TG (maTacGia, maSach) VALUES
(5, 1), (2, 2), (3, 3), (4, 4), (1, 5);

-- Dữ liệu cho bảng NHACUNGCAP
INSERT INTO NHACUNGCAP (tenNCC, diaChi, soDT, email) VALUES
('Công ty Sách A', 'HCM, VN', '0971234567', 'nccA@gmail.com'),
('Công ty Sách B', 'Hà Nội, VN', '0982345678', 'nccB@gmail.com'),
('Công ty Sách C', 'Đà Nẵng, VN', '0993456789', 'nccC@gmail.com'),
('Công ty Sách D', 'Cần Thơ, VN', '0964567890', 'nccD@gmail.com'),
('Công ty Sách E', 'Huế, VN', '0955678901', 'nccE@gmail.com');

-- Dữ liệu cho bảng TAIKHOAN
INSERT INTO TAIKHOAN (username, password, maRole) VALUES
('admin', '123456', 1),
('nguyenan', '123456', 2),
('ngocchau', '123456', 2),
('huyentrang', '123456', 3),
('quocphung', '123456', 3);

-- Dữ liệu cho bảng NHOMQUYEN
INSERT INTO NHOMQUYEN (tenrole) VALUES
('Admin'), ('Nhân viên'), ('Khách hàng');

-- Dữ liệu cho bảng NHANVIEN
INSERT INTO NHANVIEN (hoTen, ngaySinh, gioiTinh, soDT, maTK) VALUES
('Nguyễn Hùng Strong', '1990-01-01', 'Nam', '0911000001', 1),
('Nguyễn Ngọc Thiên Ân', '1990-01-01', 'Nam', '0911000001', 2),
('Danh Thị Ngọc Châu', '1995-02-02', 'Nữ', '0911000002', 3),
('Huyền Trang', '1990-01-01', 'Nam', '0911000001', 4),
('Tống Phùng', '1995-02-02', 'Nữ', '0911000002', 5);

-- Dữ liệu cho bảng KHACHHANG
INSERT INTO KHACHHANG (tenKH, soDT, gioiTinh) VALUES
('Phạm Văn C', '0988111222', 'Nam'),
('Lê Thị D', '0977111222', 'Nữ'),
('Hoàng Văn E', '0966111222', 'Nam');

-- Dữ liệu cho bảng PHIEUNHAP
INSERT INTO PHIEUNHAP (ngayNhap, tongTien, maNCC, maTK) VALUES
('2024-03-10', 500000, 1, 2),
('2024-03-15', 600000, 2, 3);

-- Dữ liệu cho bảng CT_PHIEUNHAP
INSERT INTO CT_PHIEUNHAP (maSach, maNhap, soLuongNhap, giaNhap) VALUES
('1', 1, 10, 100000),
('2', 1, 15, 200000),
('3', 2, 5, 150000);

-- Dữ liệu cho bảng HOADON
INSERT INTO HOADON (ngayBan, tongTien, maTK, maPT, maKM, maKH) VALUES
('2024-03-20', 250000, 2, 1, NULL, 1),
('2024-03-21', 300000, 3, 2, 1, 2);

-- Dữ liệu cho bảng CT_HOADON
INSERT INTO CT_HOADON (maSach, maHD, soLuong, giaBan) VALUES
('1', 1, 1, 120000),
('2', 2, 1, 250000);

-- Dữ liệu cho bảng PHUONGTHUC_TT
INSERT INTO PHUONGTHUC_TT (tenPTTT) VALUES
('Tiền mặt'), ('Chuyển khoản'), ('Thẻ tín dụng');

-- Dữ liệu cho bảng KHUYENMAI
INSERT INTO KHUYENMAI (tenKM, dieuKienGiam, giaTriGiam, ngayBatDau, ngayKetThuc) VALUES
('Giảm 10%', 'Hóa đơn > 500k', 50000, '2024-03-01T00:00:00', '2024-03-31T00:00:00'),
('Giảm 5%', 'Hóa đơn > 300k', 25000, '2024-03-01T00:00:00', '2024-03-31T00:00:00');

-- Dữ liệu cho bảng KM_PHIENBANSACH
INSERT INTO KM_PHIENBANSACH (maKM, maSach) VALUES
(1, '1'), (1, '2'), (2, '3');

-- Dữ liệu cho bảng CHUCNANG
-- Fix cứng
INSERT INTO CHUCNANG (tenChucNang) VALUES 
('book'), -- 1
('category'), -- 2
('author'), -- 3
('nxb'), -- 4
('vungtl'), -- 5
('ncc'), -- 6
('createInput'), -- 7
('qlInput'), -- 8
('createBill'), -- 9
('qlBill'), -- 10
('promotion'), -- 11
('pttt'), -- 12
('nv'), -- 13
('taikhoan'), -- 14
('khachhang'), -- 15
('phanquyen'), -- 16
('report'); -- 17

-- Dữ liệu cho bảng CHITIETQUYEN
INSERT INTO CHITIETQUYEN (maRole, maChucNang, hanhDong) VALUES
(1, 1, 'Xem'),
(1, 1, 'Thêm'),
(1, 1, 'Sửa'),
(1, 1, 'Xóa'),

(1, 2, 'Xem'),
(1, 2, 'Thêm'),
(1, 2, 'Sửa'),
(1, 2, 'Xóa'),

(1, 3, 'Xem'),
(1, 3, 'Thêm'),
(1, 3, 'Sửa'),
(1, 3, 'Xóa'),

(1, 4, 'Xem'),
(1, 4, 'Thêm'),
(1, 4, 'Sửa'),
(1, 4, 'Xóa'),

(1, 5, 'Xem'),
(1, 5, 'Thêm'),
(1, 5, 'Sửa'),
(1, 5, 'Xóa'),

(1, 6, 'Xem'),
(1, 6, 'Thêm'),
(1, 6, 'Sửa'),
(1, 6, 'Xóa'),


(1, 7, 'Xem'),

(1, 8, 'Xem'),
(1, 8, 'Thêm'),

(1, 9, 'Xem'),

(1, 10, 'Xem'),
(1, 10, 'Thêm'),

(1, 11, 'Xem'),
(1, 11, 'Thêm'),
(1, 11, 'Sửa'),
(1, 11, 'Xóa'),

(1, 12, 'Xem'),
(1, 12, 'Thêm'),
(1, 12, 'Sửa'),
(1, 12, 'Xóa'),

(1, 13, 'Xem'),
(1, 13, 'Thêm'),
(1, 13, 'Sửa'),
(1, 13, 'Xóa'),

(1, 14, 'Xem'),
(1, 14, 'Thêm'),
(1, 14, 'Sửa'),
(1, 14, 'Xóa'),

(1, 15, 'Xem'),
(1, 15, 'Thêm'),
(1, 15, 'Sửa'),
(1, 15, 'Xóa'),

(1, 16, 'Xem'),
(1, 16, 'Thêm'),
(1, 16, 'Sửa'),
(1, 16, 'Xóa'),

(1, 17, 'Xem'),

(2, 7, 'Xem'),

(2, 8, 'Xem'),
(2, 8, 'Thêm'),

(2, 9, 'Xem'),

(2, 10, 'Xem'),
(2, 10, 'Thêm'),

(3, 10, 'Xem'),
(3, 10, 'Thêm'),

(3, 15, 'Xem'),
(3, 15, 'Sửa');


ALTER TABLE SACH
ADD CONSTRAINT SACH_VITRIVUNG FOREIGN KEY (maVung) REFERENCES VITRIVUNG(maVung);
 
ALTER TABLE PHANLOAI
ADD CONSTRAINT SACH_PHANLOAI FOREIGN KEY (maSach) REFERENCES SACH(maSach), 
ADD CONSTRAINT THELOAI_PHANLOAI FOREIGN KEY (maTheLoai) REFERENCES THELOAI(maTheloai);

ALTER TABLE DANHMUC_TG
ADD CONSTRAINT DANHMUC_TG_SACH FOREIGN KEY (maSach) REFERENCES SACH(maSach),
ADD CONSTRAINT DANHMUC_TG_TACGIA FOREIGN KEY (maTacGia) REFERENCES TACGIA(maTacGia);

ALTER TABLE SACH
ADD CONSTRAINT SACH_NHAXB FOREIGN KEY (maNXB) REFERENCES NHAXB(maNXB);

ALTER TABLE CT_PHIEUNHAP
ADD CONSTRAINT SACH_CT_PHIEUNHAP FOREIGN KEY (maSach) REFERENCES SACH(maSach),
ADD CONSTRAINT PHIEUNHAP_CT_PHIEUNHAP FOREIGN KEY (maNhap) REFERENCES PHIEUNHAP(maNhap);

ALTER TABLE CT_HOADON
ADD CONSTRAINT PHIENBANSACH_CT_HOADON FOREIGN KEY (maSach) REFERENCES SACH(maSach),
ADD CONSTRAINT HOADON_CT_HOADON FOREIGN KEY (maHD) REFERENCES HOADON(maHD);

ALTER TABLE PHIEUNHAP
ADD CONSTRAINT PHIEUNHAP_NHACUNGCAP FOREIGN KEY (maNCC) REFERENCES NHACUNGCAP(maNCC),
ADD CONSTRAINT PHIEUNHAP_NHANVIEN FOREIGN KEY (maTK) REFERENCES TAIKHOAN(maTK);

ALTER TABLE HOADON
ADD CONSTRAINT HOADON_NHANVIEN FOREIGN KEY (maTK) REFERENCES TAIKHOAN(maTK),
ADD CONSTRAINT HOADON_PHUONGTHUC_TT FOREIGN KEY (maPT) REFERENCES PHUONGTHUC_TT(maPT);

ALTER TABLE TAIKHOAN
ADD CONSTRAINT TAIKHOAN_NHOMQUYEN FOREIGN KEY (maRole) REFERENCES NHOMQUYEN(maRole);

ALTER TABLE CHITIETQUYEN
ADD CONSTRAINT NHOMQUYEN_CHITIETQUYEN FOREIGN KEY (maRole) REFERENCES NHOMQUYEN(maRole),
ADD CONSTRAINT CHUCNANG_CHITIETQUYEN FOREIGN KEY (maChucNang) REFERENCES CHUCNANG(maChucNang);

ALTER TABLE HOADON
ADD CONSTRAINT HOADON_KHUYENMAI FOREIGN KEY (maKM) REFERENCES KHUYENMAI(maKM),
ADD CONSTRAINT KH_HD FOREIGN KEY (maKH) REFERENCES KHACHHANG(maKH);

ALTER TABLE NHANVIEN
ADD CONSTRAINT NHANVIEN FOREIGN KEY (maTK) REFERENCES TAIKHOAN(maTK);

ALTER TABLE KM_PHIENBANSACH
ADD CONSTRAINT km1 FOREIGN KEY (maKM) REFERENCES KHUYENMAI(maKM),
ADD CONSTRAINT km2 FOREIGN KEY (maSach) REFERENCES SACH(maSach);


