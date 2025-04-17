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

	public int insert(ChiTietQuyenDTO chiTietQuyen){
		if(chiTietQuyenDAO.insert(chiTietQuyen) != 0){
			listChiTietQuyen.add(chiTietQuyen);
			return 1;
		}
		return 0;
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

	public ArrayList<ChiTietQuyenDTO> getListChiTietQuyenByMaRoleMaCN(int maRole, int maCN){
		return(chiTietQuyenDAO.getListChiTietQuyenByMaRoleMaCN(maRole, maCN));
	}

	public ArrayList<ChiTietQuyenDTO> getListChiTietQuyenByMaRole(int maRole){
		return(chiTietQuyenDAO.getListChiTietQuyenByMaRole(maRole));
	}

	public int deleteByMaRoleAndMaCNAndHanhDong(int maRole, int maCN, String hanhDong){
		return(chiTietQuyenDAO.deleteByMaRoleAndMaCNAndHanhDong(maRole, maCN, hanhDong));
	}

	public ArrayList<String> getAllHanhDongByMaRoleAndMaCN(int maRole, int maCN){
		return(chiTietQuyenDAO.getAllHanhDongByMaRoleAndMaCN(maRole, maCN));
	}

}
