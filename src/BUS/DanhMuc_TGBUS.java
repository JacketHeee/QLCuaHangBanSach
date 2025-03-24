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
	
}
