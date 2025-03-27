package DTO;

import java.math.BigDecimal;

public class SachDTO {
	private int maSach;
	private String tenSach;
	private BigDecimal giaBan;
	private int soLuongTon;
	private int namXB;
	private int maVung;
	private int maNXB;
	
	public SachDTO() {}
	
	public SachDTO(int maSach, String tenSach, BigDecimal giaBan, int soLuongTon, int namXB, int maVung, int maNXB) {
		this.maSach = maSach;
		this.tenSach = tenSach;
		this.giaBan = giaBan;
		this.soLuongTon = soLuongTon;
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

	public BigDecimal getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(BigDecimal giaBan) {
		this.giaBan = giaBan;
	}

	public int getSoLuongTon() {
		return soLuongTon;
	}

	public void setSoLuongTon(int soLuongTon) {
		this.soLuongTon = soLuongTon;
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

	public int getMaNXB() {
		return maNXB;
	}

	public void setMaVung(int maVung) {
		this.maVung = maVung;
	}

	public void setMaNXB(int maNXB) {
		this.maNXB = maNXB;
	}
}
