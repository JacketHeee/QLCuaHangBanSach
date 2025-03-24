package BUS;

import java.util.ArrayList;

import DAO.TheLoaiDAO;
import DTO.TheLoaiDTO;

public class TheLoaiBUS {
	private static TheLoaiBUS instance;
	private TheLoaiDAO theLoaiDAO;
	private ArrayList<TheLoaiDTO> listTheLoai;
	
	private TheLoaiBUS() {
		theLoaiDAO = TheLoaiDAO.getInstance();
		listTheLoai = theLoaiDAO.getAll();
	}
	
	public static TheLoaiBUS getInstance() {
		if(instance == null) {
			instance = new TheLoaiBUS();
		}
		return(instance);
	}
	
	public ArrayList<TheLoaiDTO> getAll(){
		return(this.listTheLoai);
	}
	
}
