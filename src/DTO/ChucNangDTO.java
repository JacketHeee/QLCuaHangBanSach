package DTO;

public class ChucNangDTO {
    private int maChucNang; // Đã thay đổi kiểu từ String thành int
    private String tenChucNang;

    // Constructor không tham số
    public ChucNangDTO() {
    }

    // Constructor đầy đủ tham số
    public ChucNangDTO(int maChucNang, String tenChucNang) {
        this.maChucNang = maChucNang;
        this.tenChucNang = tenChucNang;
    }

    // Getter và Setter
    public int getMaChucNang() {
        return maChucNang;
    }

    public void setMaChucNang(int maChucNang) {
        this.maChucNang = maChucNang;
    }

    public String getTenChucNang() {
        return tenChucNang;
    }

    public void setTenChucNang(String tenChucNang) {
        this.tenChucNang = tenChucNang;
    }
}

