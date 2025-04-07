package DTO;

public class KM_SachDTO {
    private int maKM;  // Đã thay đổi kiểu từ String thành int
    private String maVach; // Đã thay đổi kiểu từ String thành int

    // Constructor không tham số
    public KM_SachDTO() {
    }

    // Constructor đầy đủ tham số
    public KM_SachDTO(int maKM, String maVach) {
        this.maKM = maKM;
        this.maVach = maVach;
    }

    // Getter và Setter
    public int getMaKM() {
        return maKM;
    }

    public void setMaKM(int maKM) {
        this.maKM = maKM;
    }

    public String getMaVach() {
        return maVach;
    }

    public void setMaVach(String maVach) {
        this.maVach = maVach;
    }
}

