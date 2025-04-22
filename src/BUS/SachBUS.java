package BUS;

import java.util.ArrayList;


import DAO.SachDAO;
import DTO.SachDTO;

public class SachBUS {
	private static SachBUS instance;
	private SachDAO sachDAO;
	private ArrayList<SachDTO> listSach;
	
	private SachBUS() {
		sachDAO = SachDAO.getInstance();
		listSach = sachDAO.getAll();
	}
	
	public static SachBUS getInstance() {
		if(instance == null) {
			instance = new SachBUS();
		}
		return(instance);
	}

	public int insert(SachDTO sach){
		if(sachDAO.insert(sach) != 0){
			listSach.add(sach);
			return(1);
		}
		return(0);
	}

	public int delete(int id){
		if(sachDAO.delete(id) != 0){
			int index = getIndexByID(id);
			listSach.remove(index);
			return(1);
		}
		return(0);
	}
	public int update(SachDTO sachDTO){
		if(sachDAO.update(sachDTO) != 0){
			int index = getIndexByID(sachDTO.getMaSach());
			listSach.get(index).setTenSach(sachDTO.getTenSach());
			listSach.get(index).setGiaBan(sachDTO.getGiaBan());
			listSach.get(index).setNamXB(sachDTO.getNamXB());
			listSach.get(index).setMaVung(sachDTO.getMaVung());
			listSach.get(index).setMaNXB(sachDTO.getMaNXB());
			return(1);
		}
		return(0);
	}
	
	public ArrayList<SachDTO> getAll(){
		return(this.listSach);
	}

	public int getIndexByID(int id){
		for(int i = 0; i < listSach.size(); i++){
			if(id == listSach.get(i).getMaSach()){
				return(i);
			}
		}
		return(-1);
	}

	public SachDTO getInstanceByID(int maSach){
		return(sachDAO.getInstanceByID(maSach));
	}

	// public void importFromExcel(ArrayList<SachDTO> importedList){
	// 	for(SachDTO sachMoi : importedList){
	// 		int index = -1;
			
	// 		// Kiểm tra sách đã tồn tại theo tên sách
	// 		for(int i = 0; i < listSach.size(); i++){
	// 			if(listSach.get(i).getTenSach().equalsIgnoreCase(sachMoi.getTenSach())){
	// 				index = i;
	// 				break;
	// 			}
	// 		}
			
	// 		if(index != -1){
	// 			// Đã có => Cập nhật số lượng
	// 			SachDTO sachCu = listSach.get(index);
	// 			int newSoLuong = sachCu.getSoLuong() + sachMoi.getSoLuong();
	// 			sachCu.setSoLuong(newSoLuong);
	// 			sachDAO.update(sachCu);
	// 		} else {
	// 			// Chưa có => Thêm mới
	// 			this.insert(sachMoi);
	// 		}
	// 	}
	// }
	
}
