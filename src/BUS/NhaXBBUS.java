package BUS;

import java.util.ArrayList;

import DAO.NhaXBDAO;
import DTO.NhaXBDTO;


public class NhaXBBUS {
	private static NhaXBBUS instance;
	private NhaXBDAO nhaXBDAO;
	private ArrayList<NhaXBDTO> listNhaXB;
	
	private NhaXBBUS() {
		nhaXBDAO = NhaXBDAO.getInstance();
		listNhaXB = nhaXBDAO.getAll();
	}
	
	public static NhaXBBUS getInstance() {
		if(instance == null) {
			instance = new NhaXBBUS();
		}
		return(instance);
	}
	
	public int insert(NhaXBDTO nhaXB){
		if(nhaXBDAO.insert(nhaXB) != 0){
			listNhaXB.add(nhaXB);
			return(1);
		}
		return(0);
	}

	public int delete(int id){
		if(nhaXBDAO.delete(id) != 0){
			int index = getIndexByID(id);
			listNhaXB.remove(index);
			return(1);
		}
		return(0);
	}
	public int update(NhaXBDTO nhaXBDTO){
		if(nhaXBDAO.update(nhaXBDTO) != 0){
			int index = getIndexByID(nhaXBDTO.getMaNXB());
			listNhaXB.get(index).setTenNXB(nhaXBDTO.getTenNXB());
			listNhaXB.get(index).setDiaChi(nhaXBDTO.getDiaChi());
			listNhaXB.get(index).setSoDT(nhaXBDTO.getSoDT());
			listNhaXB.get(index).setEmail(nhaXBDTO.getEmail());
			return(1);
		}
		return(0);
	}
	
	public ArrayList<NhaXBDTO> getAll(){
		return(this.listNhaXB);
	}

	public int getIndexByID(int id){
		for(int i = 0; i < listNhaXB.size(); i++){
			if(id == listNhaXB.get(i).getMaNXB()){
				return(i);
			}
		}
		return(-1);
	}
	
	public ArrayList<String> getAllTenNXB(){
		ArrayList<String> result = new ArrayList<>();
		for(NhaXBDTO nhaXBDTO : this.listNhaXB){
			result.add(nhaXBDTO.getTenNXB());
		}
		return(result);
	}

	public int getMaNXBByTen(String ten){
		for(NhaXBDTO nhaXBDTO : this.listNhaXB){
			if(nhaXBDTO.getTenNXB() == ten){
				return(nhaXBDTO.getMaNXB());
			}
		}
		return(-1);
	}

	public String getTenByMaNXB(int ma){	//???
		for(NhaXBDTO nhaXBDTO : this.listNhaXB){
			if(nhaXBDTO.getMaNXB() == ma){
				return(nhaXBDTO.getTenNXB());
			}
		}
		return(null);
	}

	public String getTenNXBByMaSach(int ma){
		return(nhaXBDAO.getTenNXBByMaSach(ma));
	}

}
