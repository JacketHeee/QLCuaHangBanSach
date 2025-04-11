package DTO;

import java.sql.Date;

public class NhanVienDTO {
    private int maNV; // Đã thay đổi kiểu từ String thành int
    private String hoTen;
    private Date ngaySinh;
    private String gioiTinh;
    private String soDT;
    private int maTK; // Đã thay đổi kiểu từ String thành int

    // Constructor không tham số
    public NhanVienDTO() {
    }

    // Constructor đầy đủ tham số
    public NhanVienDTO(int maNV, String hoTen, Date ngaySinh, String gioiTinh, String soDT, int maTK) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.soDT = soDT;
        this.maTK = maTK;
    }

    public NhanVienDTO(String hoTen, Date ngaySinh, String gioiTinh, String soDT, int maTK) {
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.soDT = soDT;
        this.maTK = maTK;
    }

    public NhanVienDTO(String hoTen, Date ngaySinh, String gioiTinh, String soDT) {
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.soDT = soDT;
    }

    // Getter và Setter
    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public int getMaTK() {
        return maTK;
    }

    public void setMaTK(int maTK) {
        this.maTK = maTK;
    }
}

