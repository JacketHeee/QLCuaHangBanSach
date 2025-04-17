package BUS;

import java.util.ArrayList;

import DAO.DanhMuc_TGDAO;
import DTO.DanhMuc_TGDTO;

public class DanhMuc_TGBUS {
	private static DanhMuc_TGBUS instance;
	private DanhMuc_TGDAO danhMuc_TGDAO;
	private ArrayList<DanhMuc_TGDTO> listDanhMuc_TG;
	
	private DanhMuc_TGBUS() {
		danhMuc_TGDAO = DanhMuc_TGDAO.getInstance();
		listDanhMuc_TG = danhMuc_TGDAO.getAll();
	}
	
	public static DanhMuc_TGBUS getInstance() {
		if(instance == null) {
			instance = new DanhMuc_TGBUS();
		}
		return(instance);
	}
	
	public ArrayList<DanhMuc_TGDTO> getAll(){
		return(this.listDanhMuc_TG);
	}

	public int insert(DanhMuc_TGDTO danhMuc){
		if(danhMuc_TGDAO.insert(danhMuc) != 0){
			this.listDanhMuc_TG.add(danhMuc);
			return(1);
		}
		return(0);
	}

	public int delete(int maSach, int maTG){
		if(danhMuc_TGDAO.delete(maSach, maTG) != 0){
			int index = getIndexByMaSachAndMaTL(maSach, maTG);
			listDanhMuc_TG.remove(index);
			return(1);
		}
		return(0);
	}

	public ArrayList<Integer> getAllMaTacGiaByMaSach(int maSach){
		return(danhMuc_TGDAO.getAllMaTacGiaByMaSach(maSach));
	}

	public int getIndexByMaSachAndMaTL(int maSach, int maTG){
		for(int i = 0; i < listDanhMuc_TG.size(); i++){
			if(maSach == listDanhMuc_TG.get(i).getMaSach() && maTG == listDanhMuc_TG.get(i).getMaTacGia()){
				return(i);
			}
		}
		return(-1);
	}

	
}
