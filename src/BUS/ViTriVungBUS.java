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

	public ArrayList<String> getAllTenVung(){
		ArrayList<String> result = new ArrayList<>();
		for(ViTriVungDTO viTriVungDTO : this.listViTriVung){
			result.add(viTriVungDTO.getTenVung());
		}
		return(result);
	}
	public int getMaViTriVungByTen(String ten){
		for(ViTriVungDTO viTriVungDTO : this.listViTriVung){
			if(viTriVungDTO.getTenVung() == ten){
				return(viTriVungDTO.getMaVung());
			}
		}
		return(-1);
	}
	
}
