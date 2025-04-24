package BUS;

import java.util.ArrayList;

import DAO.CT_HoaDonDAO;
import DTO.CT_HoaDonDTO;
import DTO.KM_SachDTO;

public class CT_HoaDonBUS {
	private static CT_HoaDonBUS instance;
	private CT_HoaDonDAO cT_HoaDonDAO;
	private ArrayList<CT_HoaDonDTO> listCT_HoaDon;
	
	private CT_HoaDonBUS() {
		cT_HoaDonDAO = CT_HoaDonDAO.getInstance();
		listCT_HoaDon = cT_HoaDonDAO.getAll();
	}
	
	public static CT_HoaDonBUS getInstance() {
		if(instance == null) {
			instance = new CT_HoaDonBUS();
		}
		return(instance);
	}
	
	public ArrayList<CT_HoaDonDTO> getAll(){
		return(this.listCT_HoaDon);
	}
	
	public int insert(CT_HoaDonDTO ct_HoaDon){
		if(cT_HoaDonDAO.insert(ct_HoaDon) != 0){
			this.listCT_HoaDon.add(ct_HoaDon);
			return(1);
		}
		return(0);
		
	}
	public ArrayList<CT_HoaDonDTO> getListCTHDByMaHD(int maHD){
		return(cT_HoaDonDAO.getListCTHDByMaHD(maHD));
	}
}
