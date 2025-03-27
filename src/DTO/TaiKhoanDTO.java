package DTO;

public class TaiKhoanDTO {
    private int maTK; // Đã thay đổi kiểu từ String thành int
    private String username;
    private String password;
    private int maRole; // Đã thay đổi kiểu từ String thành int

    // Constructor không tham số
    public TaiKhoanDTO() {
    }

    // Constructor đầy đủ tham số
    public TaiKhoanDTO(int maTK, String username, String password, int maRole) {
        this.maTK = maTK;
        this.username = username;
        this.password = password;
        this.maRole = maRole;
    }

    // Getter và Setter
    public int getMaTK() {
        return maTK;
    }

    public void setMaTK(int maTK) {
        this.maTK = maTK;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaRole() {
        return maRole;
    }

    public void setMaRole(int maRole) {
        this.maRole = maRole;
    }
}
