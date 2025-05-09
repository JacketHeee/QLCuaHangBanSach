package DTO;

import DAO.SachDAO;
import java.math.BigDecimal;

public class CT_HoaDonDTO {
    private int maSach;  // Đã thay đổi kiểu từ String thành int
    private int maHD;    // Đã thay đổi kiểu từ String thành int
    private int soLuong;
    private BigDecimal giaBan;

    // Constructor không tham số
    public CT_HoaDonDTO() {
    }

    // Constructor đầy đủ tham số
    

    // Getter và Setter

    public int getMaHD() {
        return maHD;
    }

    public CT_HoaDonDTO(int maSach, int maHD, int soLuong, BigDecimal giaBan) {
        this.maSach = maSach;
        this.maHD = maHD;
        this.soLuong = soLuong;
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

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    // Phương thức để lấy tên sách từ mã sách
    public String getTenSach() {
        SachDAO sachDAO = SachDAO.getInstance();
        return sachDAO.getTenByMa(this.maSach); 
    }
}

