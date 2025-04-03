package DTO;

public class TheLoaiDTO {
	private int maTheLoai;
	private String tenTheLoai;
	
	public TheLoaiDTO() {
	}

	public TheLoaiDTO(int maTheLoai, String tenTheLoai) {
		this.maTheLoai = maTheLoai;
		this.tenTheLoai = tenTheLoai;
	}

	public TheLoaiDTO(String tenTheLoai) {
		this.tenTheLoai = tenTheLoai;
	}

	public int getMaTheLoai() {
		return maTheLoai;
	}

	public void setMaTheLoai(int maTheLoai) {
		this.maTheLoai = maTheLoai;
	}

	public String getTenTheLoai() {
		return tenTheLoai;
	}

	public void setTenTheLoai(String tenTheLoai) {
		this.tenTheLoai = tenTheLoai;
	}
	
}
