package BUS;

import java.util.ArrayList;

import DAO.ViTriVungDAO;
import DTO.ViTriVungDTO;

public class ViTriVungBUS {
	private static ViTriVungBUS instance;
	private ViTriVungDAO viTriVungDAO;
	private ArrayList<ViTriVungDTO> listViTriVung;
	
	private ViTriVungBUS() {
		viTriVungDAO = ViTriVungDAO.getInstance();
		listViTriVung = viTriVungDAO.getAll();
	}
	
	public static ViTriVungBUS getInstance() {
		if(instance == null) {
			instance = new ViTriVungBUS();
		}
		return(instance);
	}
	
	public ArrayList<ViTriVungDTO> getAll(){
		return(this.listViTriVung);
	}
	
}
