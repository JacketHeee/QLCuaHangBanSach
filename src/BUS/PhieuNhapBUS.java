package BUS;

import java.util.ArrayList;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;

public class PhieuNhapBUS {
	private static PhieuNhapBUS instance;
	private PhieuNhapDAO phieuNhapDAO;
	private ArrayList<PhieuNhapDTO> listPhieuNhap;
	
	private PhieuNhapBUS() {
		phieuNhapDAO = PhieuNhapDAO.getInstance();
		listPhieuNhap = phieuNhapDAO.getAll();
	}
	
	public static PhieuNhapBUS getInstance() {
		if(instance == null) {
			instance = new PhieuNhapBUS();
		}
		return(instance);
	}
	
	public ArrayList<PhieuNhapDTO> getAll(){
		return(this.listPhieuNhap);
	}
	
}
