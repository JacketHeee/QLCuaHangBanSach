package DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class KhuyenMaiDTO {
    private int maKM; // Đã thay đổi kiểu từ String thành int
    private String tenKM;
    private String dieuKienGiam;
    private BigDecimal giaTriGiam;
    private LocalDateTime ngayBatDau;
    private LocalDateTime ngayKetThuc;

    // Constructor không tham số
    public KhuyenMaiDTO() {
    }

    // Constructor đầy đủ tham số
    public KhuyenMaiDTO(int maKM, String tenKM, String dieuKienGiam, BigDecimal giaTriGiam, LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc) {
        this.maKM = maKM;
        this.tenKM = tenKM;
        this.dieuKienGiam = dieuKienGiam;
        this.giaTriGiam = giaTriGiam;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    public KhuyenMaiDTO(String tenKM, String dieuKienGiam, BigDecimal giaTriGiam, LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc) {
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

    public LocalDateTime getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDateTime ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDateTime getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDateTime ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
}

