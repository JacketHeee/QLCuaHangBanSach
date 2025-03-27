package BUS;

import java.util.ArrayList;

import DAO.ChiTietQuyenDAO;
import DTO.ChiTietQuyenDTO;

public class ChiTietQuyenBUS {
	private static ChiTietQuyenBUS instance;
	private ChiTietQuyenDAO chiTietQuyenDAO;
	private ArrayList<ChiTietQuyenDTO> listChiTietQuyen;
	
	private ChiTietQuyenBUS() {
		chiTietQuyenDAO = ChiTietQuyenDAO.getInstance();
		listChiTietQuyen = chiTietQuyenDAO.getAll();
	}
	
	public static ChiTietQuyenBUS getInstance() {
		if(instance == null) {
			instance = new ChiTietQuyenBUS();
		}
		return(instance);
	}
	
	public ArrayList<ChiTietQuyenDTO> getAll(){
		return(this.listChiTietQuyen);
	}
	
}
