package BUS;

import java.util.ArrayList;

import DAO.KM_SachDAO;
import DTO.KM_SachDTO;

public class KM_SachBUS {
	private static KM_SachBUS instance;
	private KM_SachDAO kM_SachDAO;
	private ArrayList<KM_SachDTO> listKM_Sach;
	
	private KM_SachBUS() {
		kM_SachDAO = KM_SachDAO.getInstance();
		listKM_Sach = kM_SachDAO.getAll();
	}
	
	public static KM_SachBUS getInstance() {
		if(instance == null) {
			instance = new KM_SachBUS();
		}
		return(instance);
	}
	
	public ArrayList<KM_SachDTO> getAll(){
		return(this.listKM_Sach);
	}
	
}
