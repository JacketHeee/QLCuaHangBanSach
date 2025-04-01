package BUS;

import java.util.ArrayList;

import DAO.PhuongThucTTDAO;
import DTO.PhuongThucTTDTO;

public class PhuongThucTTBUS {
	private static PhuongThucTTBUS instance;
	private PhuongThucTTDAO phuongThucTTDAO;
	private ArrayList<PhuongThucTTDTO> listPhuongThucTT;
	
	private PhuongThucTTBUS() {
		phuongThucTTDAO = PhuongThucTTDAO.getInstance();
		listPhuongThucTT = phuongThucTTDAO.getAll();
	}
	
	public static PhuongThucTTBUS getInstance() {
		if(instance == null) {
			instance = new PhuongThucTTBUS();
		}
		return(instance);
	}
	
	public int insert(PhuongThucTTDTO PhuongThucTT){
		if(phuongThucTTDAO.insert(PhuongThucTT) != 0){
			listPhuongThucTT.add(PhuongThucTT);
			return(1);
		}
		return(0);
	}

	public int delete(int id){
		if(phuongThucTTDAO.delete(id) != 0){
			int index = getIndexByID(id);
			listPhuongThucTT.remove(index);
			return(1);
		}
		return(0);
	}
	public int update(PhuongThucTTDTO PhuongThucTTDTO){
		if(phuongThucTTDAO.update(PhuongThucTTDTO) != 0){
			int index = getIndexByID(PhuongThucTTDTO.getMaPT());
			listPhuongThucTT.get(index).setTenPTTT(PhuongThucTTDTO.getTenPTTT());
			return(1);
		}
		return(0);
	}
	
	public ArrayList<PhuongThucTTDTO> getAll(){
		return(this.listPhuongThucTT);
	}

	public int getIndexByID(int id){
		for(int i = 0; i < listPhuongThucTT.size(); i++){
			if(id == listPhuongThucTT.get(i).getMaPT()){
				return(i);
			}
		}
		return(-1);
	}

	public ArrayList<String> getAllTenPhuongThucTT(){
		ArrayList<String> result = new ArrayList<>();
		for(PhuongThucTTDTO PhuongThucTTDTO : this.listPhuongThucTT){
			result.add(PhuongThucTTDTO.getTenPTTT());
		}
		return(result);
	}
	public int getMaPhuongThucTTByTen(String ten){
		for(PhuongThucTTDTO PhuongThucTTDTO : this.listPhuongThucTT){
			if(PhuongThucTTDTO.getTenPTTT().equals(ten)){
				return(PhuongThucTTDTO.getMaPT());
			}
		}
		return(-1);
	}
	public String getTenByMaPhuongThucTT(int ma){
		for(PhuongThucTTDTO PhuongThucTTDTO : this.listPhuongThucTT){
			if(PhuongThucTTDTO.getMaPT() == ma){
				return(PhuongThucTTDTO.getTenPTTT());
			}
		}
		return(null);
	}
}
