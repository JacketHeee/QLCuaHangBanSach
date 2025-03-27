package BUS;

import java.util.ArrayList;

import DAO.CT_PhieuNhapDAO;
import DTO.CT_PhieuNhapDTO;

public class CT_PhieuNhapBUS {
	private static CT_PhieuNhapBUS instance;
	private CT_PhieuNhapDAO cT_PhieuNhapDAO;
	private ArrayList<CT_PhieuNhapDTO> listCT_PhieuNhap;
	
	private CT_PhieuNhapBUS() {
		cT_PhieuNhapDAO = CT_PhieuNhapDAO.getInstance();
		listCT_PhieuNhap = cT_PhieuNhapDAO.getAll();
	}
	
	public static CT_PhieuNhapBUS getInstance() {
		if(instance == null) {
			instance = new CT_PhieuNhapBUS();
		}
		return(instance);
	}
	
	public ArrayList<CT_PhieuNhapDTO> getAll(){
		return(this.listCT_PhieuNhap);
	}
	
}
