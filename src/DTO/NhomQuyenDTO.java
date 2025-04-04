package DTO;

public class NhomQuyenDTO {
    private int maRole; // Đã thay đổi kiểu từ String thành int
    private String tenRole;

    // Constructor không tham số
    public NhomQuyenDTO() {
    }

    // Constructor đầy đủ tham số
    public NhomQuyenDTO(int maRole, String tenRole) {
        this.maRole = maRole;
        this.tenRole = tenRole;
    }

    //cho insert
    public NhomQuyenDTO(String tenRole) {
        this.tenRole = tenRole;
    }


    // Getter và Setter
    public int getMaRole() {
        return maRole;
    }

    public void setMaRole(int maRole) {
        this.maRole = maRole;
    }

    public String getTenRole() {
        return tenRole;
    }

    public void setTenRole(String tenRole) {
        this.tenRole = tenRole;
    }
}

