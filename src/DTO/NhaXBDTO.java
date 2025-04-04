package DTO;

public class NhaXBDTO {
    private int maNXB; // Đã thay đổi kiểu từ String thành int
    private String tenNXB;
    private String diaChi;
    private String soDT;
    private String email;

    // Constructor không tham số
    public NhaXBDTO() {
    }

    // Constructor đầy đủ tham số
    public NhaXBDTO(int maNXB, String tenNXB, String diaChi, String soDT, String email) {
        this.maNXB = maNXB;
        this.tenNXB = tenNXB;
        this.diaChi = diaChi;
        this.soDT = soDT;
        this.email = email;
    }

    public NhaXBDTO(String tenNXB, String diaChi, String soDT, String email) {
        this.tenNXB = tenNXB;
        this.diaChi = diaChi;
        this.soDT = soDT;
        this.email = email;
    }

    // Getter và Setter
    public int getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(int maNXB) {
        this.maNXB = maNXB;
    }

    public String getTenNXB() {
        return tenNXB;
    }

    public void setTenNXB(String tenNXB) {
        this.tenNXB = tenNXB;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

