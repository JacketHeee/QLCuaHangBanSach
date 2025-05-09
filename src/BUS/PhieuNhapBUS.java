package BUS;

import DAO.PhieuNhapDAO;
import DTO.CT_PhieuNhapDTO;
import DTO.PhieuNhapDTO;
import java.math.BigDecimal;
import java.util.ArrayList;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;
import java.util.List;

public class PhieuNhapBUS {
	private static PhieuNhapBUS instance;
	private PhieuNhapDAO phieuNhapDAO;
	private ArrayList<PhieuNhapDTO> listPhieuNhap;
	
	private PhieuNhapBUS() {
		phieuNhapDAO = PhieuNhapDAO.getInstance();
		listPhieuNhap = phieuNhapDAO.getAll();
	}
	
	public static PhieuNhapBUS getInstance() {
		if(instance == null) {
			instance = new PhieuNhapBUS();
		}
		return(instance);
	}
	
	public int insert(PhieuNhapDTO PhieuNhap){
		if(phieuNhapDAO.insert(PhieuNhap) != 0){
			listPhieuNhap.addFirst(PhieuNhap);
			return(1);
		}
		return(0);
	}

	public int delete(int id){
		if(phieuNhapDAO.delete(id) != 0){
			int index = getIndexByID(id);
			listPhieuNhap.remove(index);
			return(1);
		}
		return(0);
	}
	public int update(PhieuNhapDTO PhieuNhapDTO){
		if(phieuNhapDAO.update(PhieuNhapDTO) != 0){
			int index = getIndexByID(PhieuNhapDTO.getMaNhap());
			listPhieuNhap.get(index).setNgayNhap(PhieuNhapDTO.getNgayNhap());
			listPhieuNhap.get(index).setTongTien(PhieuNhapDTO.getTongTien());
			listPhieuNhap.get(index).setMaNCC(PhieuNhapDTO.getMaNCC());
			listPhieuNhap.get(index).setMaTK(PhieuNhapDTO.getMaTK());
			return(1);
		}
		return(0);
	}
	
	public ArrayList<PhieuNhapDTO> getAll(){
		return(this.listPhieuNhap);
	}

	public int getIndexByID(int id){
		for(int i = 0; i < listPhieuNhap.size(); i++){
			if(id == listPhieuNhap.get(i).getMaNhap()){
				return(i);
			}
		}
		return(-1);
	}

	public int getNextID(){
		return(phieuNhapDAO.getNextID());
	}

	public PhieuNhapDTO getInstanceByID(int maNhap){
		return(phieuNhapDAO.getInstanceByID(maNhap));
	}

    public BigDecimal tinhTongGia(BigDecimal giaNhap, int soLuong){
        return(giaNhap.multiply(BigDecimal.valueOf(soLuong)));
    }
	
	// Hàm xuất PN ra PDF
    public void xuatPhieuNhapPDF(PhieuNhapDTO phieuNhap) {
        List<CT_PhieuNhapDTO> listCTPN = getChiTietPhieuNhap(phieuNhap.getMaNhap());
        phieuNhapDAO.xuatPhieuNhapPDF(phieuNhap, listCTPN);
    }
    private List<CT_PhieuNhapDTO> getChiTietPhieuNhap(int maNhap) {
        return CT_PhieuNhapBUS.getInstance().getListCTPNByMaPN(maNhap);
    }
}
