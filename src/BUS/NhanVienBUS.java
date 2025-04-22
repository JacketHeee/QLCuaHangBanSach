package BUS;

import java.util.ArrayList;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;

public class NhanVienBUS {
	private static NhanVienBUS instance;
	private NhanVienDAO nhanVienDAO;
	private ArrayList<NhanVienDTO> listNhanVien;
	
	public NhanVienBUS() {
		nhanVienDAO = NhanVienDAO.getInstance();
		listNhanVien = nhanVienDAO.getAll();
	}
	
	public static NhanVienBUS getInstance() {
		if(instance == null) {
			instance = new NhanVienBUS();
		}
		return(instance);
	}
	
	public int insert(NhanVienDTO NhanVien){
		if(nhanVienDAO.insert(NhanVien) != 0){
			listNhanVien.add(NhanVien);
			return(1);
		}
		return(0);
	}

	public int delete(int id){
		if(nhanVienDAO.delete(id) != 0){
			int index = getIndexByID(id);
			listNhanVien.remove(index);
			return(1);
		}
		return(0);
	}
	public int update(NhanVienDTO NhanVienDTO){
		if(nhanVienDAO.update(NhanVienDTO) != 0){
			int index = getIndexByID(NhanVienDTO.getMaNV());
			listNhanVien.get(index).setHoTen(NhanVienDTO.getHoTen());
			listNhanVien.get(index).setNgaySinh(NhanVienDTO.getNgaySinh());
			listNhanVien.get(index).setGioiTinh(NhanVienDTO.getGioiTinh());
			listNhanVien.get(index).setSoDT(NhanVienDTO.getSoDT());
			listNhanVien.get(index).setMaTK(NhanVienDTO.getMaTK());
			return(1);
		}
		return(0);
	}
	
	public ArrayList<NhanVienDTO> getAll(){
		return(this.listNhanVien);
	}

	public int getIndexByID(int id){
		for(int i = 0; i < listNhanVien.size(); i++){
			if(id == listNhanVien.get(i).getMaNV()){
				return(i);
			}
		}
		return(-1);
	}

	public NhanVienDTO SelectNhanVienByMaTK(int maTK){
		return(nhanVienDAO.SelectNhanVienByMaTK(maTK));
	}	

	public ArrayList<String> getAllTenNVNotHaveAccount(){
		return(nhanVienDAO.getAllTenNVNotHaveAccount());
	}

	public ArrayList<String> getAllTenNVHaveAccount(){
		return(nhanVienDAO.getAllTenNVHaveAccount());
	}


	public int getMaNVByTen(String tenNV){
		return(nhanVienDAO.getMaNVByTenNV(tenNV));
	}

	public int setMaTK(int maNV, int maTK){
		if(nhanVienDAO.setMaTK(maNV, maTK) != 0){
			int index = getIndexByID(maNV);
			listNhanVien.get(index).setMaTK(maTK);
			return(1);
		}
		return(0);
	}

	public String getTenNVByMaTK(int maTK){
		return(nhanVienDAO.getTenNVByMaTK(maTK));
	}

	public int getMaNVByMaTK(int maTK){
		return(nhanVienDAO.getMaNVByMaTK(maTK));
	}

	public NhanVienDTO getInstanceByMa(int maNV){
		return(nhanVienDAO.getInstanceByMa(maNV));
	}
	
}
