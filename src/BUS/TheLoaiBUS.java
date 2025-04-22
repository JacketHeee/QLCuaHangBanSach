package BUS;

import java.util.ArrayList;

import DAO.TheLoaiDAO;
import DTO.TheLoaiDTO;

public class TheLoaiBUS {
	private static TheLoaiBUS instance;
	private TheLoaiDAO theLoaiDAO;
	private ArrayList<TheLoaiDTO> listTheLoai;
	
	private TheLoaiBUS() {
		theLoaiDAO = TheLoaiDAO.getInstance();
		listTheLoai = theLoaiDAO.getAll();
	}
	
	public static TheLoaiBUS getInstance() {
		if(instance == null) {
			instance = new TheLoaiBUS();
		}
		return(instance);
	}

	public int insert(TheLoaiDTO TheLoai){
		if(theLoaiDAO.insert(TheLoai) != 0){
			listTheLoai.add(TheLoai);
			return(1);
		}
		return(0);
	}

	public int delete(int id){
		if(theLoaiDAO.delete(id) != 0){
			int index = getIndexByID(id);
			listTheLoai.remove(index);
			return(1);
		}
		return(0);
	}
	public int update(TheLoaiDTO TheLoaiDTO){
		if(theLoaiDAO.update(TheLoaiDTO) != 0){
			int index = getIndexByID(TheLoaiDTO.getMaTheLoai());
			listTheLoai.get(index).setTenTheLoai(TheLoaiDTO.getTenTheLoai());
			return(1);
		}
		return(0);
	}
	
	public ArrayList<TheLoaiDTO> getAll(){
		return(this.listTheLoai);
	}

	public int getIndexByID(int id){
		for(int i = 0; i < listTheLoai.size(); i++){
			if(id == listTheLoai.get(i).getMaTheLoai()){
				return(i);
			}
		}
		return(-1);
	}

	public ArrayList<String> getAllTenTheLoai(){
		return(theLoaiDAO.getAllTenTheLoai());
	}

	public int getMaByTen(String ten){
		return(theLoaiDAO.getMaByTen(ten));
	}

	public String getTenByMa(int ma){
		return(theLoaiDAO.getTenByMa(ma));
	}

}
