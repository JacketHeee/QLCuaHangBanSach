package DTO;

public class KhachHangDTO {
    private int maKH; // Đã thay đổi kiểu từ String thành int
    private String tenKH;
    private String soDT;
    private String gioiTinh;

    // Constructor không tham số
    public KhachHangDTO() {
    }

    // Constructor đầy đủ tham số
    public KhachHangDTO(int maKH, String tenKH, String soDT, String gioiTinh) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.soDT = soDT;
        this.gioiTinh = gioiTinh;
    }

    public KhachHangDTO(String tenKH, String soDT, String gioiTinh) {
        this.tenKH = tenKH;
        this.soDT = soDT;
        this.gioiTinh = gioiTinh;
    }

    // Getter và Setter
    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
}

