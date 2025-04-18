package DTO;

import java.math.BigDecimal;

public class SachDTO {
    private int maSach;
    private String tenSach;
    private int soLuong;
    private BigDecimal giaBan;
    private int namXB;
    private int maVung;
    private int maNXB;

    // Constructor mặc định
    public SachDTO() {}

	//constructor cho insert
	public SachDTO( String tenSach, BigDecimal giaBan, int namXB,
			int maVung, int maNXB) {
		this.tenSach = tenSach;
		this.giaBan = giaBan;
		this.namXB = namXB;
		this.maVung = maVung;
		this.maNXB = maNXB;
	}

	public SachDTO(int maSach, String tenSach, int soLuong, BigDecimal giaBan, int namXB,
			int maVung, int maNXB) {
		this.maSach = maSach;
		this.tenSach = tenSach;
        this.soLuong = soLuong;
		this.giaBan = giaBan;
		this.namXB = namXB;
		this.maVung = maVung;
		this.maNXB = maNXB;
	}

    // Constructor khi insert (không có mã sách)
    public SachDTO(int maSach, String tenSach, BigDecimal giaBan, 
                   int namXB, int maVung, int maNXB) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaBan = giaBan;
        this.namXB = namXB;
        this.maVung = maVung;
        this.maNXB = maNXB;
    }

    public SachDTO(String tenSach, int soLuong, BigDecimal giaBan, 
    int namXB, int maVung, int maNXB) {
this.tenSach = tenSach;
this.soLuong = soLuong;
this.giaBan = giaBan;
this.namXB = namXB;
this.maVung = maVung;
this.maNXB = maNXB;
}

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public int getNamXB() {
        return namXB;
    }

    public void setNamXB(int namXB) {
        this.namXB = namXB;
    }

    public int getMaVung() {
        return maVung;
    }

    public void setMaVung(int maVung) {
        this.maVung = maVung;
    }

    public int getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(int maNXB) {
        this.maNXB = maNXB;
    }

    
}
