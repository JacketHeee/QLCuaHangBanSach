package DTO;

public class KM_SachDTO {
    private int maKM;  // Đã thay đổi kiểu từ String thành int
    private int maSach; // Đã thay đổi kiểu từ String thành int

    // Constructor không tham số
    public KM_SachDTO() {
    }

    // Constructor đầy đủ tham số
    public KM_SachDTO(int maKM, int maSach) {
        this.maKM = maKM;
        this.maSach = maSach;
    }

    // Getter và Setter
    public int getMaKM() {
        return maKM;
    }

    public void setMaKM(int maKM) {
        this.maKM = maKM;
    }

    public int getmaSach() {
        return maSach;
    }

    public void setmaSach(int maSach) {
        this.maSach = maSach;
    }
}

