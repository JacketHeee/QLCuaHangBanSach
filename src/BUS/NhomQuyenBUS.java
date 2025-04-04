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
	
	public int insert(NhomQuyenDTO NhomQuyen){
		if(nhomQuyenDAO.insert(NhomQuyen) != 0){
			listNhomQuyen.add(NhomQuyen);
			return(1);
		}
		return(0);
	}

	public int delete(int id){
		if(nhomQuyenDAO.delete(id) != 0){
			int index = getIndexByID(id);
			listNhomQuyen.remove(index);
			return(1);
		}
		return(0);
	}
	public int update(NhomQuyenDTO NhomQuyenDTO){
		if(nhomQuyenDAO.update(NhomQuyenDTO) != 0){
			int index = getIndexByID(NhomQuyenDTO.getMaRole());
			listNhomQuyen.get(index).setTenRole(NhomQuyenDTO.getTenRole());
			return(1);
		}
		return(0);
	}
	
	public ArrayList<NhomQuyenDTO> getAll(){
		return(this.listNhomQuyen);
	}

	public int getIndexByID(int id){
		for(int i = 0; i < listNhomQuyen.size(); i++){
			if(id == listNhomQuyen.get(i).getMaRole()){
				return(i);
			}
		}
		return(-1);
	}

	public ArrayList<String> getAllTenNhomQuyen(){
		ArrayList<String> result = new ArrayList<>();
		for(NhomQuyenDTO NhomQuyenDTO : this.listNhomQuyen){
			result.add(NhomQuyenDTO.getTenRole());
		}
		return(result);
	}
	public int getMaNhomQuyenByTen(String ten){
		for(NhomQuyenDTO NhomQuyenDTO : this.listNhomQuyen){
			if(NhomQuyenDTO.getTenRole().equals(ten)){
				return(NhomQuyenDTO.getMaRole());
			}
		}
		return(-1);
	}
	public String getTenByMaNhomQuyen(int ma){
		for(NhomQuyenDTO NhomQuyenDTO : this.listNhomQuyen){
			if(NhomQuyenDTO.getMaRole() == ma){
				return(NhomQuyenDTO.getTenRole());
			}
		}
		return(null);
	}

	public NhomQuyenDTO SelectByID(int maNQ){
		return(nhomQuyenDAO.SelectByID(maNQ));
	}
	
}
