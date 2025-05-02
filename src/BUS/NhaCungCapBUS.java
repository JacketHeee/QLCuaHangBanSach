package BUS;

import java.util.ArrayList;

import DAO.NhaCungCapDAO;
import DTO.NhaCungCapDTO;


public class NhaCungCapBUS {
	private static NhaCungCapBUS instance;
	private NhaCungCapDAO nhaCungCapDAO;
	private ArrayList<NhaCungCapDTO> listNhaCungCap;
	
	public NhaCungCapBUS() {
		nhaCungCapDAO = NhaCungCapDAO.getInstance();
		listNhaCungCap = nhaCungCapDAO.getAll();
	}
	
	public static NhaCungCapBUS getInstance() {
		if(instance == null) {
			instance = new NhaCungCapBUS();
		}
		return(instance);
	}
	
	public int insert(NhaCungCapDTO NhaCungCap){
		if(nhaCungCapDAO.insert(NhaCungCap) != 0){
			listNhaCungCap.add(NhaCungCap);
			return(1);
		}
		return(0);
	}

	public int delete(int id){
		if(nhaCungCapDAO.delete(id) != 0){
			int index = getIndexByID(id);
			listNhaCungCap.remove(index);
			return(1);
		}
		return(0);
	}
	public int update(NhaCungCapDTO NhaCungCapDTO){
		if(nhaCungCapDAO.update(NhaCungCapDTO) != 0){
			int index = getIndexByID(NhaCungCapDTO.getMaNCC());
			listNhaCungCap.get(index).setTenNCC(NhaCungCapDTO.getTenNCC());
			listNhaCungCap.get(index).setDiaChi(NhaCungCapDTO.getDiaChi());
			listNhaCungCap.get(index).setSoDT(NhaCungCapDTO.getSoDT());
			listNhaCungCap.get(index).setEmail(NhaCungCapDTO.getEmail());
			return(1);
		}
		return(0);
	}
	
	public ArrayList<NhaCungCapDTO> getAll(){
		return(this.listNhaCungCap);
	}

	public int getIndexByID(int id){
		for(int i = 0; i < listNhaCungCap.size(); i++){
			if(id == listNhaCungCap.get(i).getMaNCC()){
				return(i);
			}
		}
		return(-1);
	}

	public ArrayList<String> getAllTenNhaCungCap(){
		ArrayList<String> result = new ArrayList<>();
		for(NhaCungCapDTO NhaCungCapDTO : this.listNhaCungCap){
			result.add(NhaCungCapDTO.getTenNCC());
		}
		return(result);
	}
	public int getMaNhaCungCapByTen(String ten){
		for(NhaCungCapDTO NhaCungCapDTO : this.listNhaCungCap){
			if(NhaCungCapDTO.getTenNCC().equals(ten)){
				return(NhaCungCapDTO.getMaNCC());
			}
		}
		return(-1);
	}
	public String getTenByMaNhaCungCap(int ma){
		for(NhaCungCapDTO NhaCungCapDTO : this.listNhaCungCap){
			if(NhaCungCapDTO.getMaNCC() == ma){
				return(NhaCungCapDTO.getTenNCC());
			}
		}
		return(null);
	}

	public NhaCungCapDTO getNCCById(int ma) {
		return nhaCungCapDAO.getNhaCungCapById(ma);
	}
	
}
