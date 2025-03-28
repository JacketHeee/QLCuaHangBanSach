package BUS;

import java.util.ArrayList;

import DAO.NhaXBDAO;
import DTO.NhaXBDTO;
import DTO.ViTriVungDTO;

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
	
	public ArrayList<NhaXBDTO> getAll(){
		return(this.listNhaXB);
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

	public String getTenByMaNXB(int ma){
		for(NhaXBDTO nhaXBDTO : this.listNhaXB){
			if(nhaXBDTO.getMaNXB() == ma){
				return(nhaXBDTO.getTenNXB());
			}
		}
		return(null);
	}

}
