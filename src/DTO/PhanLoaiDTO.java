package DTO;

public class PhanLoaiDTO {
	private int maSach;
	private int maTheLoai;
		
	public PhanLoaiDTO() {
		super();
	}
	
	public PhanLoaiDTO(int maSach, int maTheLoai) {
		super();
		this.maSach = maSach;
		this.maTheLoai = maTheLoai;
	}

	public int getMaSach() {
		return maSach;
	}

	public void setMaSach(int maSach) {
		this.maSach = maSach;
	}

	public int getMaTheLoai() {
		return maTheLoai;
	}

	public void setMaTheLoai(int maTheLoai) {
		this.maTheLoai = maTheLoai;
	}
	
}
