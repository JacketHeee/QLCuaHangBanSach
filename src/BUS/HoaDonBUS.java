package BUS;

import java.util.ArrayList;

import DAO.HoaDonDAO;
import DTO.HoaDonDTO;

public class HoaDonBUS {
	private static HoaDonBUS instance;
	private HoaDonDAO hoaDonDAO;
	private ArrayList<HoaDonDTO> listHoaDon;
	
	private HoaDonBUS() {
		hoaDonDAO = HoaDonDAO.getInstance();
		listHoaDon = hoaDonDAO.getAll();
	}
	
	public static HoaDonBUS getInstance() {
		if(instance == null) {
			instance = new HoaDonBUS();
		}
		return(instance);
	}
	
	public ArrayList<HoaDonDTO> getAll(){
		return(this.listHoaDon);
	}
	
}
