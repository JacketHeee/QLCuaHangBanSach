package BUS;

import java.util.ArrayList;

import DAO.KM_SachDAO;
import DTO.KM_SachDTO;

public class KM_SachBUS {
	private static KM_SachBUS instance;
	private KM_SachDAO kM_SachDAO;
	private ArrayList<KM_SachDTO> listKM_Sach;
	
	private KM_SachBUS() {
		kM_SachDAO = KM_SachDAO.getInstance();
		listKM_Sach = kM_SachDAO.getAll();
	}
	
	public static KM_SachBUS getInstance() {
		if(instance == null) {
			instance = new KM_SachBUS();
		}
		return(instance);
	}

	public int delete(int maSach, int maKM){
		if(kM_SachDAO.delete(maSach, maKM) != 0){
			int index = getIndexByMaSachAndMaKM(maSach, maKM);
			listKM_Sach.remove(index);
			return(1);
		}
		return(0);
	}
	
	public ArrayList<KM_SachDTO> getAll(){
		return(this.listKM_Sach);
	}

	public ArrayList<Integer> getAllMaKMByMaSach(int maSach){
		return(kM_SachDAO.getAllMaKMByMaSach(maSach));
	}

	public int getIndexByMaSachAndMaKM(int maSach, int maKM){
		for(int i = 0; i < listKM_Sach.size(); i++){
			if(maSach == listKM_Sach.get(i).getmaSach() && maKM == listKM_Sach.get(i).getMaKM()){
				return(i);
			}
		}
		return(-1);
	}

	public int insert(KM_SachDTO km_Sach){
		if(kM_SachDAO.insert(km_Sach) != 0){
			this.listKM_Sach.add(km_Sach);
			return(1);
		}
		return(0);
	}
	
}
