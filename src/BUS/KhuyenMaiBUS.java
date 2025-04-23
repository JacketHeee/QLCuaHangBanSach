package BUS;

import java.util.ArrayList;

import DAO.KhuyenMaiDAO;
import DTO.KhuyenMaiDTO;

public class KhuyenMaiBUS {
    private static KhuyenMaiBUS instance;
    private KhuyenMaiDAO khuyenMaiDAO;
    private ArrayList<KhuyenMaiDTO> listKhuyenMai;

    private KhuyenMaiBUS() {
        khuyenMaiDAO = KhuyenMaiDAO.getInstance();
        listKhuyenMai = khuyenMaiDAO.getAll();
    }

    public static KhuyenMaiBUS getInstance() {
        if (instance == null) {
            instance = new KhuyenMaiBUS();
        }
        return instance;
    }

    public int insert(KhuyenMaiDTO khuyenMai) {
        if (khuyenMaiDAO.insert(khuyenMai) != 0) {
            listKhuyenMai.add(khuyenMai);
            return 1;
        }
        return 0;
    }

    public int delete(int id) {
        if (khuyenMaiDAO.delete(id) != 0) {
            int index = getIndexByID(id);
            listKhuyenMai.remove(index);
            return 1;
        }
        return 0;
    }

    public int update(KhuyenMaiDTO khuyenMai) {
        if (khuyenMaiDAO.update(khuyenMai) != 0) {
            int index = getIndexByID(khuyenMai.getMaKM());
            listKhuyenMai.get(index).setTenKM(khuyenMai.getTenKM());
            listKhuyenMai.get(index).setDieuKienGiam(khuyenMai.getDieuKienGiam());
            listKhuyenMai.get(index).setGiaTriGiam(khuyenMai.getGiaTriGiam());
            listKhuyenMai.get(index).setNgayBatDau(khuyenMai.getNgayBatDau());
			listKhuyenMai.get(index).setNgayKetThuc(khuyenMai.getNgayKetThuc());
            return 1;
        }
        return 0;
    }

    public ArrayList<KhuyenMaiDTO> getAll() {
        return this.listKhuyenMai;
    }

    public int getIndexByID(int id) {
        for (int i = 0; i < listKhuyenMai.size(); i++) {
            if (id == listKhuyenMai.get(i).getMaKM()) {
                return i;
            }
        }
        return -1;
    }

	public ArrayList<String> getAllTenKhuyenMai(){
		ArrayList<String> result = new ArrayList<>();
		for(KhuyenMaiDTO KhuyenMaiDTO : this.listKhuyenMai){
			result.add(KhuyenMaiDTO.getTenKM());
		}
		return(result);
	}
	public int getMaKhuyenMaiByTen(String ten){
		return(khuyenMaiDAO.getMaKhuyenMaiByTen(ten));
	}
	public String getTenByMaKhuyenMai(int ma){
		return(khuyenMaiDAO.getTenByMaKhuyenMa(ma));
	}
    public KhuyenMaiDTO getInstanceByMa(int ma){
        return(khuyenMaiDAO.getInstanceByMa(ma));
    }

}
