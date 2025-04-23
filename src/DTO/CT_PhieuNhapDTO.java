package DTO;

import java.math.BigDecimal;

public class CT_PhieuNhapDTO {
    private int maSach;  // Đã thay đổi kiểu từ String thành int
    private int maNhap;  // Đã thay đổi kiểu từ String thành int
    private int soLuongNhap;
    private BigDecimal giaNhap;

    // Constructor không tham số
    public CT_PhieuNhapDTO() {
    }

    // Constructor đầy đủ tham số
    public CT_PhieuNhapDTO(int maSach, int maNhap, int soLuongNhap, BigDecimal giaNhap) {
        this.maSach = maSach;
        this.maNhap = maNhap;
        this.soLuongNhap = soLuongNhap;
        this.giaNhap = giaNhap;
    }

    // Getter và Setter

    public int getMaNhap() {
        return maNhap;
    }

    public void setMaNhap(int maNhap) {
        this.maNhap = maNhap;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public BigDecimal getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(BigDecimal giaNhap) {
        this.giaNhap = giaNhap;
    }

    public int getmaSach() {
        return maSach;
    }

    public void setmaSach(int maSach) {
        this.maSach = maSach;
    }
}

