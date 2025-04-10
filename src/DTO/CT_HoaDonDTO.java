package DTO;

import java.math.BigDecimal;

public class CT_HoaDonDTO {
    private String maSach;  // Đã thay đổi kiểu từ String thành int
    private int maHD;    // Đã thay đổi kiểu từ String thành int
    private int soLuong;
    private BigDecimal giaNhap;
    private BigDecimal giaBan;

    // Constructor không tham số
    public CT_HoaDonDTO() {
    }

    // Constructor đầy đủ tham số
    

    // Getter và Setter

    public int getMaHD() {
        return maHD;
    }

    public CT_HoaDonDTO(String maSach, int maHD, int soLuong, BigDecimal giaNhap, BigDecimal giaBan) {
        this.maSach = maSach;
        this.maHD = maHD;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public String getmaSach() {
        return maSach;
    }

    public void setmaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public BigDecimal getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(BigDecimal giaNhap) {
        this.giaNhap = giaNhap;
    }

    
}

