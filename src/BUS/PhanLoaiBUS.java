package BUS;

import java.util.ArrayList;

import DAO.PhanLoaiDAO;
import DTO.PhanLoaiDTO;

public class PhanLoaiBUS {
	private static PhanLoaiBUS instance;
	private PhanLoaiDAO phanLoaiDAO;
	private ArrayList<PhanLoaiDTO> listPhanLoai;
	
	private PhanLoaiBUS() {
		phanLoaiDAO = PhanLoaiDAO.getInstance();
		listPhanLoai = phanLoaiDAO.getAll();
	}
	
	public static PhanLoaiBUS getInstance() {
		if(instance == null) {
			instance = new PhanLoaiBUS();
		}
		return(instance);
	}
	
	public ArrayList<PhanLoaiDTO> getAll(){
		return(this.listPhanLoai);
	}
	
}
