package BUS;

import java.util.ArrayList;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;

public class NhanVienBUS {
	private static NhanVienBUS instance;
	private NhanVienDAO nhanVienDAO;
	private ArrayList<NhanVienDTO> listNhanVien;
	
	private NhanVienBUS() {
		nhanVienDAO = NhanVienDAO.getInstance();
		listNhanVien = nhanVienDAO.getAll();
	}
	
	public static NhanVienBUS getInstance() {
		if(instance == null) {
			instance = new NhanVienBUS();
		}
		return(instance);
	}
	
	public ArrayList<NhanVienDTO> getAll(){
		return(this.listNhanVien);
	}
	
}
