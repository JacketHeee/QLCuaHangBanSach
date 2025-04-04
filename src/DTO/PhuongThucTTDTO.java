package DTO;

public class PhuongThucTTDTO {
    private int maPT; // Đã thay đổi kiểu từ String thành int
    private String tenPTTT;

    // Constructor không tham số
    public PhuongThucTTDTO() {
    }

    // Constructor đầy đủ tham số
    public PhuongThucTTDTO(int maPT, String tenPTTT) {
        this.maPT = maPT;
        this.tenPTTT = tenPTTT;
    }

    // cho insert
    public PhuongThucTTDTO(String tenPTTT) {
        this.tenPTTT = tenPTTT;
    }

    // Getter và Setter
    public int getMaPT() {
        return maPT;
    }

    public void setMaPT(int maPT) {
        this.maPT = maPT;
    }

    public String getTenPTTT() {
        return tenPTTT;
    }

    public void setTenPTTT(String tenPTTT) {
        this.tenPTTT = tenPTTT;
    }
}

