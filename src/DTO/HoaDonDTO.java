package DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HoaDonDTO {
    private int maHD; // Đã thay đổi kiểu từ String thành int
    private LocalDateTime ngayBan;
    private BigDecimal tongTien;
    private int maTK;  // Đã thay đổi kiểu từ String thành int
    private int maPT;  // Đã thay đổi kiểu từ String thành int
    private int maKM;  // Đã thay đổi kiểu từ Integer thành int
    private int maKH;  // Đã thay đổi kiểu từ Integer thành int

    // Constructor không tham số
    public HoaDonDTO() {
    }

    // Constructor đầy đủ tham số
    public HoaDonDTO(int maHD, LocalDateTime ngayBan, BigDecimal tongTien, int maTK, int maPT, int maKM, int maKH) {
        this.maHD = maHD;
        this.ngayBan = ngayBan;
        this.tongTien = tongTien;
        this.maTK = maTK;
        this.maPT = maPT;
        this.maKM = maKM;
        this.maKH = maKH;
    }
// cho insert
    public HoaDonDTO(LocalDateTime ngayBan, BigDecimal tongTien, int maTK, int maPT, int maKM, int maKH) {
        this.ngayBan = ngayBan;
        this.tongTien = tongTien;
        this.maTK = maTK;
        this.maPT = maPT;
        this.maKM = maKM;
        this.maKH = maKH;
    }

    // Constructor không có mã khuyến mãi
    public HoaDonDTO(int maHD, LocalDateTime ngayBan, BigDecimal tongTien, int maTK, int maPT, int maKH) {
        this(maHD, ngayBan, tongTien, maTK, maPT, 0, maKH); // Mặc định maKM = 0 nếu không có
    }

    // Constructor không có mã khách hàng
    public HoaDonDTO(int maHD, LocalDateTime ngayBan, BigDecimal tongTien, int maTK, int maPT) {
        this(maHD, ngayBan, tongTien, maTK, maPT, 0, 0); // Mặc định cả maKM và maKH = 0 nếu không có
    }

    // Getter và Setter
    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public LocalDateTime getNgayBan() {
        return ngayBan;
    }

    public void setNgayBan(LocalDateTime ngayBan) {
        this.ngayBan = ngayBan;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public int getMaTK() {
        return maTK;
    }

    public void setMaTK(int maTK) {
        this.maTK = maTK;
    }

    public int getMaPT() {
        return maPT;
    }

    public void setMaPT(int maPT) {
        this.maPT = maPT;
    }

    public int getMaKM() {
        return maKM;
    }

    public void setMaKM(int maKM) {
        this.maKM = maKM;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }
}

