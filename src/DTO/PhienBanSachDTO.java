package DTO;

import java.math.BigDecimal;

public class PhienBanSachDTO {
    private String maVach;
    private BigDecimal giaNhap;
    private BigDecimal giaBan;
    private int soLuong;
    private int maSach;

    public PhienBanSachDTO(){}

    public PhienBanSachDTO(String maVach, BigDecimal giaNhap, BigDecimal giaBan, int soLuong, int maSach) {
        this.maVach = maVach;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.maSach = maSach;
    }

    public String getMaVach() {
        return maVach;
    }

    public void setMaVach(String maVach) {
        this.maVach = maVach;
    }

    public BigDecimal getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(BigDecimal giaNhap) {
        this.giaNhap = giaNhap;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    
    

}
