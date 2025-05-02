package BUS;

import java.util.ArrayList;

import DAO.TacGiaDAO;
import DTO.TacGiaDTO;

public class TacGiaBUS {
	private static TacGiaBUS instance;
	private TacGiaDAO tacGiaDAO;
	private ArrayList<TacGiaDTO> listTacGia;
	
	private TacGiaBUS() {
		tacGiaDAO = TacGiaDAO.getInstance();
		listTacGia = tacGiaDAO.getAll();
	}
	
	public static TacGiaBUS getInstance() {
		if(instance == null) {
			instance = new TacGiaBUS();
		}
		return(instance);
	}
	
	public int insert(TacGiaDTO TacGia){
		if(tacGiaDAO.insert(TacGia) != 0){
			listTacGia.add(TacGia);
			return(1);
		}
		return(0);
	}

	public int delete(int id){
		if(tacGiaDAO.delete(id) != 0){
			int index = getIndexByID(id);
			listTacGia.remove(index);
			return(1);
		}
		return(0);
	}
	public int update(TacGiaDTO TacGiaDTO){
		if(tacGiaDAO.update(TacGiaDTO) != 0){
			int index = getIndexByID(TacGiaDTO.getMaTacGia());
			listTacGia.get(index).setTenTacGia(TacGiaDTO.getTenTacGia());
			return(1);
		}
		return(0);
	}
	
	public ArrayList<TacGiaDTO> getAll(){
		return(this.listTacGia);
	}

	public int getIndexByID(int id){
		for(int i = 0; i < listTacGia.size(); i++){
			if(id == listTacGia.get(i).getMaTacGia()){
				return(i);
			}
		}
		return(-1);
	}

	public ArrayList<String> getAllTenTacGia(){
		return(tacGiaDAO.getAllTenTacGia());
	}
	
	public int getMaByTen(String ten){
		return(tacGiaDAO.getMaByTen(ten));
	}

	public String getTenByMa(int ma){
		return(tacGiaDAO.getTenByMa(ma));
	}

	public TacGiaDTO getTacGiaById(int ma) {
		return tacGiaDAO.getTacGiaById(ma);
	}

}
