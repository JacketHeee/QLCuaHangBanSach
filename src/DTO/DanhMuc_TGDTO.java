package DTO;

public class DanhMuc_TGDTO {
    private int maTacGia; // Đã thay đổi kiểu từ String thành int
    private int maSach;   // Đã thay đổi kiểu từ String thành int

    // Constructor không tham số
    public DanhMuc_TGDTO() {
    }

    // Constructor đầy đủ tham số
    public DanhMuc_TGDTO(int maSach, int maTacGia) {
        this.maTacGia = maTacGia;
        this.maSach = maSach;
    }

    // Getter và Setter
    public int getMaTacGia() {
        return maTacGia;
    }

    public void setMaTacGia(int maTacGia) {
        this.maTacGia = maTacGia;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }
}

