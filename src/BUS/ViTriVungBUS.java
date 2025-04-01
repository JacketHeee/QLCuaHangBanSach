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
	
	public int insert(ViTriVungDTO ViTriVung){
		if(viTriVungDAO.insert(ViTriVung) != 0){
			listViTriVung.add(ViTriVung);
			return(1);
		}
		return(0);
	}

	public int delete(int id){
		if(viTriVungDAO.delete(id) != 0){
			int index = getIndexByID(id);
			listViTriVung.remove(index);
			return(1);
		}
		return(0);
	}
	public int update(ViTriVungDTO ViTriVungDTO){
		if(viTriVungDAO.update(ViTriVungDTO) != 0){
			int index = getIndexByID(ViTriVungDTO.getMaVung());
			listViTriVung.get(index).setTenVung(ViTriVungDTO.getTenVung());
			return(1);
		}
		return(0);
	}
	
	public ArrayList<ViTriVungDTO> getAll(){
		return(this.listViTriVung);
	}

	public int getIndexByID(int id){
		for(int i = 0; i < listViTriVung.size(); i++){
			if(id == listViTriVung.get(i).getMaVung()){
				return(i);
			}
		}
		return(-1);
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
	public String getTenByMaViTriVung(int ma){
		for(ViTriVungDTO viTriVungDTO : this.listViTriVung){
			if(viTriVungDTO.getMaVung() == ma){
				return(viTriVungDTO.getTenVung());
			}
		}
		return(null);
	}
	
}
