package BUS;

import java.util.ArrayList;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;

public class TaiKhoanBUS {
	private static TaiKhoanBUS instance;
	private TaiKhoanDAO taiKhoanDAO;
	private ArrayList<TaiKhoanDTO> listTaiKhoan;
	
	private TaiKhoanBUS() {
		taiKhoanDAO = TaiKhoanDAO.getInstance();
		listTaiKhoan = taiKhoanDAO.getAll();
	}
	
	public static TaiKhoanBUS getInstance() {
		if(instance == null) {
			instance = new TaiKhoanBUS();
		}
		return(instance);
	}
	
	public ArrayList<TaiKhoanDTO> getAll(){
		return(this.listTaiKhoan);
	}
	
}
