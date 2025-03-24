package BUS;

import java.util.ArrayList;

import DAO.KhuyenMaiDAO;
import DTO.KhuyenMaiDTO;

public class KhuyenMaiBUS {
	private static KhuyenMaiBUS instance;
	private KhuyenMaiDAO khuyenMaiDAO;
	private ArrayList<KhuyenMaiDTO> listKhuyenMai;
	
	private KhuyenMaiBUS() {
		khuyenMaiDAO = KhuyenMaiDAO.getInstance();
		listKhuyenMai = khuyenMaiDAO.getAll();
	}
	
	public static KhuyenMaiBUS getInstance() {
		if(instance == null) {
			instance = new KhuyenMaiBUS();
		}
		return(instance);
	}
	
	public ArrayList<KhuyenMaiDTO> getAll(){
		return(this.listKhuyenMai);
	}
	
}
