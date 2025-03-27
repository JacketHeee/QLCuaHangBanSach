package DTO;

import java.math.BigDecimal;
import java.util.Date;

public class KhuyenMaiDTO {
    private int maKM; // Đã thay đổi kiểu từ String thành int
    private String tenKM;
    private String dieuKienGiam;
    private BigDecimal giaTriGiam;
    private Date ngayBatDau;
    private Date ngayKetThuc;

    // Constructor không tham số
    public KhuyenMaiDTO() {
    }

    // Constructor đầy đủ tham số
    public KhuyenMaiDTO(int maKM, String tenKM, String dieuKienGiam, BigDecimal giaTriGiam, Date ngayBatDau, Date ngayKetThuc) {
        this.maKM = maKM;
        this.tenKM = tenKM;
        this.dieuKienGiam = dieuKienGiam;
        this.giaTriGiam = giaTriGiam;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    // Getter và Setter
    public int getMaKM() {
        return maKM;
    }

    public void setMaKM(int maKM) {
        this.maKM = maKM;
    }

    public String getTenKM() {
        return tenKM;
    }

    public void setTenKM(String tenKM) {
        this.tenKM = tenKM;
    }

    public String getDieuKienGiam() {
        return dieuKienGiam;
    }

    public void setDieuKienGiam(String dieuKienGiam) {
        this.dieuKienGiam = dieuKienGiam;
    }

    public BigDecimal getGiaTriGiam() {
        return giaTriGiam;
    }

    public void setGiaTriGiam(BigDecimal giaTriGiam) {
        this.giaTriGiam = giaTriGiam;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
}

