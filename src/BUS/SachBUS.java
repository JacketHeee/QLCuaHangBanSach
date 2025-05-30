package BUS;

import java.math.BigDecimal;
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
			if (sachDTO.getGiaBan().compareTo(BigDecimal.valueOf(-1)) != 0) {
				listSach.get(index).setGiaBan(sachDTO.getGiaBan());
			}
			return(1);
		}
		return(0);
	}

	public int updateOnNhap(SachDTO sachDTO) {
		if(sachDAO.updateOnNhap(sachDTO) != 0){
			int index = getIndexByID(sachDTO.getMaSach());
			listSach.get(index).setGiaBan(sachDTO.getGiaBan());
			listSach.get(index).setSoLuong(sachDTO.getSoLuong());
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

	public SachDTO getCurrentSach(int maSach){
		for (SachDTO x : listSach) 
			if (x.getMaSach() == maSach ) 
				return x;
		return null;
	}

	public ArrayList<SachDTO> getAllSachByMaVung(int ma){
		return(sachDAO.getAllSachByMaVung(ma));
	}
	
	public String getPathByMa(int ma){
		return(sachDAO.getPathByMa(ma));
	}

	public String[] getAllTenSach() {
		ArrayList<String> listTen = new ArrayList<>();
		for (SachDTO x : listSach) {
			listTen.add(x.getTenSach());
		}

		return listTen.toArray(new String[0]);
	}

	
}
