package DTO;

public class TacGiaDTO {
    private int maTacGia; // Đã thay đổi kiểu từ String thành int
    private String tenTacGia;

    // Constructor không tham số
    public TacGiaDTO() {
    }

    // Constructor đầy đủ tham số
    public TacGiaDTO(int maTacGia, String tenTacGia) {
        this.maTacGia = maTacGia;
        this.tenTacGia = tenTacGia;
    }

    // Getter và Setter
    public int getMaTacGia() {
        return maTacGia;
    }

    public void setMaTacGia(int maTacGia) {
        this.maTacGia = maTacGia;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }
}
