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
	
	public ArrayList<PhuongThucTTDTO> getAll(){
		return(this.listPhuongThucTT);
	}
	
}
