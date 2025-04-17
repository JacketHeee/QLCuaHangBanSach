package BUS;

import java.util.ArrayList;

import DAO.PhanLoaiDAO;
import DTO.PhanLoaiDTO;

public class PhanLoaiBUS {
	private static PhanLoaiBUS instance;
	private PhanLoaiDAO phanLoaiDAO;
	private ArrayList<PhanLoaiDTO> listPhanLoai;
	
	private PhanLoaiBUS() {
		phanLoaiDAO = PhanLoaiDAO.getInstance();
		listPhanLoai = phanLoaiDAO.getAll();
	}
	
	public static PhanLoaiBUS getInstance() {
		if(instance == null) {
			instance = new PhanLoaiBUS();
		}
		return(instance);
	}

	public int insert(PhanLoaiDTO pl){
		if(phanLoaiDAO.insert(pl) != 0){
			this.listPhanLoai.add(pl);
			return(1);
		}
		return(0);
	}

	public int delete(int maSach, int maTL){
		if(phanLoaiDAO.delete(maSach, maTL) != 0){
			int index = getIndexByMaSachAndMaTL(maSach, maTL);
			this.listPhanLoai.remove(index);
			return(1);
		}
		return(0);
	}

	public ArrayList<PhanLoaiDTO> getAll(){
		return(this.listPhanLoai);
	}

	public ArrayList<Integer> getAllMaTheLoaiByMaSach(int maSach){
		return(phanLoaiDAO.getAllMaTheLoaiByMaSach(maSach));
	}
	
	public int getIndexByMaSachAndMaTL(int maSach, int maTL){
		for(int i = 0; i < listPhanLoai.size(); i++){
			if(maSach == listPhanLoai.get(i).getMaSach() && maTL == listPhanLoai.get(i).getMaTheLoai()){
				return(i);
			}
		}
		return(-1);
	}
}
