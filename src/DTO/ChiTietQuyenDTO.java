package DTO;

public class ChiTietQuyenDTO {
    private int maRole; // Đã thay đổi kiểu từ String thành int
    private int maChucNang; // Đã thay đổi kiểu từ String thành int
    private String hanhDong;

    // Constructor không tham số
    public ChiTietQuyenDTO() {
    }

    // Constructor đầy đủ tham số
    public ChiTietQuyenDTO(int maRole, int maChucNang, String hanhDong) {
        this.maRole = maRole;
        this.maChucNang = maChucNang;
        this.hanhDong = hanhDong;
    }

    // Getter và Setter
    public int getMaRole() {
        return maRole;
    }

    public void setMaRole(int maRole) {
        this.maRole = maRole;
    }

    public int getMaChucNang() {
        return maChucNang;
    }

    public void setMaChucNang(int maChucNang) {
        this.maChucNang = maChucNang;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }
}

