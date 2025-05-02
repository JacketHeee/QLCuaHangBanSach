package BUS;

import java.util.ArrayList;


import DAO.SachDAO;
import DTO.SachDTO;

public class SachBUS {
	private static SachBUS instance;
	private SachDAO sachDAO;
	private ArrayList<SachDTO> listSach;
	
	private SachBUS() {
		sachDAO = SachDAO.getInstance();
		listSach = sachDAO.getAll();
	}
	
	public static SachBUS getInstance() {
		if(instance == null) {
			instance = new SachBUS();
		}
		return(instance);
	}

	public int insert(SachDTO sach){
		if(sachDAO.insert(sach) != 0){
			listSach.add(sach);
			return(1);
		}
		return(0);
	}

	public int delete(int id){
		if(sachDAO.delete(id) != 0){
			int index = getIndexByID(id);
			listSach.remove(index);
			return(1);
		}
		return(0);
	}
	public int update(SachDTO sachDTO){
		if(sachDAO.update(sachDTO) != 0){
			int index = getIndexByID(sachDTO.getMaSach());
			listSach.get(index).setTenSach(sachDTO.getTenSach());
			listSach.get(index).setNamXB(sachDTO.getNamXB());
			listSach.get(index).setMaVung(sachDTO.getMaVung());
			listSach.get(index).setMaNXB(sachDTO.getMaNXB());
			return(1);
		}
		return(0);
	}
	
	public ArrayList<SachDTO> getAll(){
		return(this.listSach);
	}

	public int getIndexByID(int id){
		for(int i = 0; i < listSach.size(); i++){
			if(id == listSach.get(i).getMaSach()){
				return(i);
			}
		}
		return(-1);
	}

	public String getTenByMa(int ma){
		return(sachDAO.getTenByMa(ma));
	}

	public SachDTO getInstanceByID(int maSach){
		return(sachDAO.getInstanceByID(maSach));
	}

	public ArrayList<SachDTO> getAllSachByMaVung(int ma){
		return(sachDAO.getAllSachByMaVung(ma));
	}
	
	public String getPathByMa(int ma){
		return(sachDAO.getPathByMa(ma));
	}
}
