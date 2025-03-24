package DTO;

public class NhaCungCapDTO {
    private int maNCC; // Đã thay đổi kiểu từ String thành int
    private String tenNCC;
    private String diaChi;
    private String soDT;
    private String email;

    // Constructor không tham số
    public NhaCungCapDTO() {
    }

    // Constructor đầy đủ tham số
    public NhaCungCapDTO(int maNCC, String tenNCC, String diaChi, String soDT, String email) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.diaChi = diaChi;
        this.soDT = soDT;
        this.email = email;
    }

    // Getter và Setter
    public int getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
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

