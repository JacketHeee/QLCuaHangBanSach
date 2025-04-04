package DTO;

public class ViTriVungDTO {
	//From chatGPT
    private int maVung; // Đã thay đổi kiểu từ String thành int
    private String tenVung;

    // Constructor không tham số
    public ViTriVungDTO() {
    }

    // Constructor đầy đủ tham số
    public ViTriVungDTO(int maVung, String tenVung) {
        this.maVung = maVung;
        this.tenVung = tenVung;
    }

    public ViTriVungDTO(String tenVung) {
        this.tenVung = tenVung;
    }

    // Getter và Setter
    public int getMaVung() {
        return maVung;
    }

    public void setMaVung(int maVung) {
        this.maVung = maVung;
    }

    public String getTenVung() {
        return tenVung;
    }

    public void setTenVung(String tenVung) {
        this.tenVung = tenVung;
    }
}
