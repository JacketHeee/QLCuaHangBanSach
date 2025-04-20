package BUS;

import java.util.ArrayList;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;

public class PhieuNhapBUS {
	private static PhieuNhapBUS instance;
	private PhieuNhapDAO phieuNhapDAO;
	private ArrayList<PhieuNhapDTO> listPhieuNhap;
	
	private PhieuNhapBUS() {
		phieuNhapDAO = PhieuNhapDAO.getInstance();
		listPhieuNhap = phieuNhapDAO.getAll();
	}
	
	public static PhieuNhapBUS getInstance() {
		if(instance == null) {
			instance = new PhieuNhapBUS();
		}
		return(instance);
	}
	
	public int insert(PhieuNhapDTO PhieuNhap){
		if(phieuNhapDAO.insert(PhieuNhap) != 0){
			listPhieuNhap.add(PhieuNhap);
			return(1);
		}
		return(0);
	}

	public int delete(int id){
		if(phieuNhapDAO.delete(id) != 0){
			int index = getIndexByID(id);
			listPhieuNhap.remove(index);
			return(1);
		}
		return(0);
	}
	public int update(PhieuNhapDTO PhieuNhapDTO){
		if(phieuNhapDAO.update(PhieuNhapDTO) != 0){
			int index = getIndexByID(PhieuNhapDTO.getMaNhap());
			listPhieuNhap.get(index).setNgayNhap(PhieuNhapDTO.getNgayNhap());
			listPhieuNhap.get(index).setTongTien(PhieuNhapDTO.getTongTien());
			listPhieuNhap.get(index).setMaNCC(PhieuNhapDTO.getMaNCC());
			listPhieuNhap.get(index).setMaTK(PhieuNhapDTO.getMaTK());
			return(1);
		}
		return(0);
	}
	
	public ArrayList<PhieuNhapDTO> getAll(){
		return(this.listPhieuNhap);
	}

	public int getIndexByID(int id){
		for(int i = 0; i < listPhieuNhap.size(); i++){
			if(id == listPhieuNhap.get(i).getMaNhap()){
				return(i);
			}
		}
		return(-1);
	}

	public int getNextID(){
		return(phieuNhapDAO.getNextID());
	}
	
}
