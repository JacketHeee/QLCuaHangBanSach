package BUS;

import java.util.ArrayList;

import DAO.NhaXBDAO;
import DTO.NhaXBDTO;

public class NhaXBBUS {
	private static NhaXBBUS instance;
	private NhaXBDAO nhaXBDAO;
	private ArrayList<NhaXBDTO> listNhaXB;
	
	private NhaXBBUS() {
		nhaXBDAO = NhaXBDAO.getInstance();
		listNhaXB = nhaXBDAO.getAll();
	}
	
	public static NhaXBBUS getInstance() {
		if(instance == null) {
			instance = new NhaXBBUS();
		}
		return(instance);
	}
	
	public ArrayList<NhaXBDTO> getAll(){
		return(this.listNhaXB);
	}
	
}
