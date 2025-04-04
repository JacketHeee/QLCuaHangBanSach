package BUS;

import java.util.ArrayList;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;

public class TaiKhoanBUS {
	private static TaiKhoanBUS instance;
	private TaiKhoanDAO taiKhoanDAO;
	private ArrayList<TaiKhoanDTO> listTaiKhoan;
	
	private TaiKhoanBUS() {
		taiKhoanDAO = TaiKhoanDAO.getInstance();
		listTaiKhoan = taiKhoanDAO.getAll();
	}
	
	public static TaiKhoanBUS getInstance() {
		if(instance == null) {
			instance = new TaiKhoanBUS();
		}
		return(instance);
	}
	
	public int insert(TaiKhoanDTO TaiKhoan){
		if(taiKhoanDAO.insert(TaiKhoan) != 0){
			listTaiKhoan.add(TaiKhoan);
			return(1);
		}
		return(0);
	}

	public int delete(int id){
		if(taiKhoanDAO.delete(id) != 0){
			int index = getIndexByID(id);
			listTaiKhoan.remove(index);
			return(1);
		}
		return(0);
	}
	public int update(TaiKhoanDTO TaiKhoanDTO){
		if(taiKhoanDAO.update(TaiKhoanDTO) != 0){
			int index = getIndexByID(TaiKhoanDTO.getMaTK());
			listTaiKhoan.get(index).setUsername(TaiKhoanDTO.getUsername());
			listTaiKhoan.get(index).setPassword(TaiKhoanDTO.getPassword());
			listTaiKhoan.get(index).setMaRole(TaiKhoanDTO.getMaRole());
			return(1);
		}
		return(0);
	}
	
	public ArrayList<TaiKhoanDTO> getAll(){
		return(this.listTaiKhoan);
	}

	public int getIndexByID(int id){	//xây dựng lại
		for(int i = 0; i < listTaiKhoan.size(); i++){
			if(id == listTaiKhoan.get(i).getMaTK()){
				return(i);
			}
		}
		return(-1);
	}

	public TaiKhoanDTO SelectTaiKhoanByUserName(String userName){
		TaiKhoanDTO taiKhoan = taiKhoanDAO.SelectTaiKhoanByUserName(userName);
		return(taiKhoan);
	}

	public ArrayList<String> getAllTenTaiKhoan(){	//xây dựng lại
		ArrayList<String> result = new ArrayList<>();
		for(TaiKhoanDTO TaiKhoanDTO : this.listTaiKhoan){
			result.add(TaiKhoanDTO.getUsername());
		}
		return(result);
	}
	public int getMaTaiKhoanByTen(String ten){	//xây dựng lại
		for(TaiKhoanDTO TaiKhoanDTO : this.listTaiKhoan){
			if(TaiKhoanDTO.getUsername().equals(ten)){
				return(TaiKhoanDTO.getMaTK());
			}
		}
		return(-1);
	}
	public String getTenByMaTaiKhoan(int ma){	//xây dựng lại
		for(TaiKhoanDTO TaiKhoanDTO : this.listTaiKhoan){
			if(TaiKhoanDTO.getMaTK() == ma){
				return(TaiKhoanDTO.getUsername());
			}
		}
		return(null);
	}

	public boolean isExist(String userName){
		if(taiKhoanDAO.SelectTaiKhoanByUserName(userName) == null){
			return(false);
		} 
		return(true);
	}
	
	public boolean isValidAcount(String userName, String passWord){
		TaiKhoanDTO taiKhoan = taiKhoanDAO.SelectTaiKhoanByUserName(userName);
		if(taiKhoan.getPassword().equals(passWord)){ //xử lý bằng hash sau
			return(true);
		}	
		return(false);
	}
}
