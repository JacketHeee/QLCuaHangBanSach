package BUS;

import java.util.ArrayList;

import DAO.NhaCungCapDAO;
import DTO.NhaCungCapDTO;

public class NhaCungCapBUS {
	private static NhaCungCapBUS instance;
	private NhaCungCapDAO nhaCungCapDAO;
	private ArrayList<NhaCungCapDTO> listNhaCungCap;
	
	private NhaCungCapBUS() {
		nhaCungCapDAO = NhaCungCapDAO.getInstance();
		listNhaCungCap = nhaCungCapDAO.getAll();
	}
	
	public static NhaCungCapBUS getInstance() {
		if(instance == null) {
			instance = new NhaCungCapBUS();
		}
		return(instance);
	}
	
	public ArrayList<NhaCungCapDTO> getAll(){
		return(this.listNhaCungCap);
	}
	
}
