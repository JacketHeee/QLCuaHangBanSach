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
	
	public ArrayList<TacGiaDTO> getAll(){
		return(this.listTacGia);
	}
	
}
