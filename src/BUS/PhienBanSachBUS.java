package BUS;

import java.math.BigDecimal;
import java.util.ArrayList;


import DAO.PhienBanSachDAO;
import DTO.PhienBanSachDTO;

public class PhienBanSachBUS {
	private static PhienBanSachBUS instance;
	private PhienBanSachDAO phienBanSachDAO;
	private ArrayList<PhienBanSachDTO> listPhienBanSach;
	
	private PhienBanSachBUS() {
		phienBanSachDAO = PhienBanSachDAO.getInstance();
		listPhienBanSach = phienBanSachDAO.getAll();
	}
	
	public static PhienBanSachBUS getInstance() {
		if(instance == null) {
			instance = new PhienBanSachBUS();
		}
		return(instance);
	}

	public int insert(PhienBanSachDTO phienBanSach){
		if(phienBanSachDAO.insert(phienBanSach) != 0){
			listPhienBanSach.add(phienBanSach);
			return(1);
		}
		return(0);
	}

	public int delete(String maVach){
		if(phienBanSachDAO.delete(maVach) != 0){
			int index = getIndexByMaVach(maVach);
			listPhienBanSach.remove(index);
			return(1);
		}
		return(0);
	}
	public int update(PhienBanSachDTO phienBanSachDTO){
		if(phienBanSachDAO.update(phienBanSachDTO) != 0){
			int index = getIndexByMaVach(phienBanSachDTO.getMaVach());
			listPhienBanSach.get(index).setGiaNhap(null);
			listPhienBanSach.get(index).setGiaBan(null);
			listPhienBanSach.get(index).setMaSach(index);
			return(1);
		}
		return(0);
	}
	
	public ArrayList<PhienBanSachDTO> getAll(){
		return(this.listPhienBanSach);
	}

	public int getIndexByMaVach(String maVach){
		for(int i = 0; i < listPhienBanSach.size(); i++){
			if(maVach.equals(listPhienBanSach.get(i).getMaVach())){
				return(i);
			}
		}
		return(-1);
	}

	public int getSoLuongSachByMaSach(int maSach){
		return(phienBanSachDAO.getSoLuongSachByMaSach(maSach));
	}
}
