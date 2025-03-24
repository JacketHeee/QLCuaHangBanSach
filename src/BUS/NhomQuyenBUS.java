package BUS;

import java.util.ArrayList;

import DAO.NhomQuyenDAO;
import DTO.NhomQuyenDTO;

public class NhomQuyenBUS {
	private static NhomQuyenBUS instance;
	private NhomQuyenDAO nhomQuyenDAO;
	private ArrayList<NhomQuyenDTO> listNhomQuyen;
	
	private NhomQuyenBUS() {
		nhomQuyenDAO = NhomQuyenDAO.getInstance();
		listNhomQuyen = nhomQuyenDAO.getAll();
	}
	
	public static NhomQuyenBUS getInstance() {
		if(instance == null) {
			instance = new NhomQuyenBUS();
		}
		return(instance);
	}
	
	public ArrayList<NhomQuyenDTO> getAll(){
		return(this.listNhomQuyen);
	}
	
}
