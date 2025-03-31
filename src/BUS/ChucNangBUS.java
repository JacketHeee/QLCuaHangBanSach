package BUS;

import java.util.ArrayList;

import DAO.ChucNangDAO;
import DTO.ChucNangDTO;

public class ChucNangBUS {
	private static ChucNangBUS instance;
	private ChucNangDAO chucNangDAO;
	private ArrayList<ChucNangDTO> listChucNang;
	
	private ChucNangBUS() {
		this.chucNangDAO = chucNangDAO.getInstance();
		this.listChucNang = chucNangDAO.getAll();
	}
	
	public static ChucNangBUS getInstance() {
		if(instance == null) {
			instance = new ChucNangBUS();
		}
		return(instance);
	}
	
	public ArrayList<ChucNangDTO> getAll(){
		return(this.listChucNang);
	}
	
}
