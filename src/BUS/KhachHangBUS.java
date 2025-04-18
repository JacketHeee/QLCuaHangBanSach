package BUS;

import java.util.ArrayList;


import DAO.KhachHangDAO;
import DTO.KhachHangDTO;

public class KhachHangBUS {
	private static KhachHangBUS instance;
	private KhachHangDAO khachHangDAO;
	private ArrayList<KhachHangDTO> listKhachHang;
	
	public KhachHangBUS() {
		khachHangDAO = KhachHangDAO.getInstance();
		listKhachHang = khachHangDAO.getAll();
	}
	
	public static KhachHangBUS getInstance() {
		if(instance == null) {
			instance = new KhachHangBUS();
		}
		return(instance);
	}

	public int insert(KhachHangDTO KhachHang){
		if(khachHangDAO.insert(KhachHang) != 0){
			listKhachHang.add(KhachHang);
			return(1);
		}
		return(0);
	}

	public int delete(int id){
		if(khachHangDAO.delete(id) != 0){
			int index = getIndexByID(id);
			listKhachHang.remove(index);
			return(1);
		}
		return(0);
	}
	public int update(KhachHangDTO KhachHangDTO){
		if(khachHangDAO.update(KhachHangDTO) != 0){
			int index = getIndexByID(KhachHangDTO.getMaKH());
			listKhachHang.get(index).setTenKH(KhachHangDTO.getTenKH());
			listKhachHang.get(index).setSoDT(KhachHangDTO.getSoDT());
			listKhachHang.get(index).setGioiTinh(KhachHangDTO.getGioiTinh());
			return(1);
		}
		return(0);
	}
	
	public ArrayList<KhachHangDTO> getAll(){
		return(this.listKhachHang);
	}

	public int getIndexByID(int id){
		for(int i = 0; i < listKhachHang.size(); i++){
			if(id == listKhachHang.get(i).getMaKH()){
				return(i);
			}
		}
		return(-1);
	}

    public ArrayList<String> getAllTenKhachHang(){
		ArrayList<String> result = new ArrayList<>();
		for(KhachHangDTO KhachHangDTO : this.listKhachHang){
			result.add(KhachHangDTO.getTenKH());
		}
		return(result);
	}
	public int getMaKhachHangByTen(String ten){
		for(KhachHangDTO KhachHangDTO : this.listKhachHang){
			if(KhachHangDTO.getTenKH().equals(ten)){
				return(KhachHangDTO.getMaKH());
			}
		}
		return(-1);
	}
	public String getTenByMaKhachHang(int ma){
		for(KhachHangDTO KhachHangDTO : this.listKhachHang){
			if(KhachHangDTO.getMaKH() == ma){
				return(KhachHangDTO.getTenKH());
			}
		}
		return(null);
	}
	
}
