package BUS;

import java.util.ArrayList;


import DAO.SachDAO;
import DTO.SachDTO;

public class SachBUS {
	private static SachBUS instance;
	private SachDAO sachDAO;
	private ArrayList<SachDTO> listSach;
	
	private SachBUS() {
		sachDAO = SachDAO.getInstance();
		listSach = sachDAO.getAll();
	}
	
	public static SachBUS getInstance() {
		if(instance == null) {
			instance = new SachBUS();
		}
		return(instance);
	}

	public int insert(SachDTO sach){
		if(sachDAO.insert(sach) != 0){
			listSach.add(sach);
			return(1);
		}
		return(0);
	}
	
	public ArrayList<SachDTO> getAll(){
		return(this.listSach);
	}
	
}
