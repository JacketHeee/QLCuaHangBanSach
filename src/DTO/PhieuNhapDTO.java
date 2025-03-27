package DTO;

import java.math.BigDecimal;
import java.util.Date;

public class PhieuNhapDTO {
    private int maNhap; // Đã thay đổi kiểu từ String thành int
    private Date ngayNhap;
    private BigDecimal tongTien;
    private int maNCC; // Đã thay đổi kiểu từ String thành int
    private int maTK;  // Đã thay đổi kiểu từ String thành int

    // Constructor không tham số
    public PhieuNhapDTO() {
    }

    // Constructor đầy đủ tham số
    public PhieuNhapDTO(int maNhap, Date ngayNhap, BigDecimal tongTien, int maNCC, int maTK) {
        this.maNhap = maNhap;
        this.ngayNhap = ngayNhap;
        this.tongTien = tongTien;
        this.maNCC = maNCC;
        this.maTK = maTK;
    }

    // Getter và Setter
    public int getMaNhap() {
        return maNhap;
    }

    public void setMaNhap(int maNhap) {
        this.maNhap = maNhap;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public int getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }

    public int getMaTK() {
        return maTK;
    }

    public void setMaTK(int maTK) {
        this.maTK = maTK;
    }
}

