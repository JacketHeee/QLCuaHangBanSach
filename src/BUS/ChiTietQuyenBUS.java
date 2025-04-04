package BUS;

import java.util.ArrayList;

import DAO.ChiTietQuyenDAO;
import DAO.ChucNangDAO;
import DTO.ChiTietQuyenDTO;

public class ChiTietQuyenBUS {
	private static ChiTietQuyenBUS instance;
	private ChiTietQuyenDAO chiTietQuyenDAO;
	private ArrayList<ChiTietQuyenDTO> listChiTietQuyen;
	
	private ChiTietQuyenBUS() {
		chiTietQuyenDAO = ChiTietQuyenDAO.getInstance();
		listChiTietQuyen = chiTietQuyenDAO.getAll();
	}
	
	public static ChiTietQuyenBUS getInstance() {
		if(instance == null) {
			instance = new ChiTietQuyenBUS();
		}
		return(instance);
	}
	
	public ArrayList<ChiTietQuyenDTO> getAll(){
		return(this.listChiTietQuyen);
	}

	public ArrayList<ChiTietQuyenDTO> selectChiTietQuyenByMaNQ(int maNQ){
		return(chiTietQuyenDAO.selectChiTietQuyenByMaNQ(maNQ));
	}

	public ArrayList<String> getListTenCNByMaNQ(int maNQ){
		ArrayList<String> result = new ArrayList<>();

		ArrayList<Integer> listCTQ = chiTietQuyenDAO.getListMaCNByMaNQ(maNQ);
		ChucNangDAO chucNangDAO = ChucNangDAO.getInstance();
		for(int i : listCTQ){
			String tenCN = chucNangDAO.getNameByMaCN(i); 
			result.add(tenCN);
		}
		return(result);
	}
}
