package BUS;

import DAO.HoaDonDAO;
import DTO.CT_HoaDonDTO;
import DTO.HoaDonDTO;
import DTO.KhuyenMaiDTO;
import DTO.SachDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HoaDonBUS {
    private static HoaDonBUS instance;
    private HoaDonDAO hoaDonDAO;
    private ArrayList<HoaDonDTO> listHoaDon;
    private KhuyenMaiBUS khuyenMaiBUS;

    private HoaDonBUS() {
        hoaDonDAO = HoaDonDAO.getInstance();
        khuyenMaiBUS = KhuyenMaiBUS.getInstance();
        listHoaDon = hoaDonDAO.getAll();
    }

    public static HoaDonBUS getInstance() {
        if (instance == null) {
            instance = new HoaDonBUS();
        }
        return instance;
    }

    public int insert(HoaDonDTO hoaDon) {
        if (hoaDonDAO.insert(hoaDon) != 0) {
            listHoaDon.add(hoaDon);
            return 1;
        }
        return 0;
    }

    public int delete(int id) {
        if (hoaDonDAO.delete(id) != 0) {
            int index = getIndexByID(id);
            if (index != -1) {
                listHoaDon.remove(index);
            }
            return 1;
        }
        return 0;
    }

    public int update(HoaDonDTO hoaDon) {

        if (hoaDonDAO.update(hoaDon) != 0) {
            int index = getIndexByID(hoaDon.getMaHD());
            listHoaDon.get(index).setNgayBan(hoaDon.getNgayBan());
			listHoaDon.get(index).setTongTien(hoaDon.getTongTien());
			listHoaDon.get(index).setMaTK(hoaDon.getMaTK());
			listHoaDon.get(index).setMaPT(hoaDon.getMaPT());
			listHoaDon.get(index).setMaKM(hoaDon.getMaKM());
			listHoaDon.get(index).setMaKH(hoaDon.getMaKH());
			return 1;
        }
        return 0;
    }

    public ArrayList<HoaDonDTO> getAll() {
        return listHoaDon;
    }

    private int getIndexByID(int id) {
        for (int i = 0; i < listHoaDon.size(); i++) {
            if (id == listHoaDon.get(i).getMaHD()) {
                return i;
            }
        }
        return -1;
    }

    public int getNextID(){
        return(hoaDonDAO.getNextID());
    }
    
    public BigDecimal tinhTongGia(SachDTO sach, int soLuong){
        BigDecimal result = (sach.getGiaBan()).multiply(BigDecimal.valueOf(soLuong));
        return(result);
    }

    public BigDecimal tinhTongGia(BigDecimal giaBan, int soLuong){
        return(giaBan.multiply(BigDecimal.valueOf(soLuong)));
    }

    public BigDecimal getTienKhuyenMai(String tenKM){
        if(tenKM.equals("Không có")){
            return(new BigDecimal(0));
        }
        int maKM = khuyenMaiBUS.getMaKhuyenMaiByTen(tenKM);
        KhuyenMaiDTO khuyenMai = khuyenMaiBUS.getInstanceByMa(maKM);
        return(khuyenMai.getGiaTriGiam());
    } 
    public BigDecimal tinhKhuyenMai(BigDecimal giaBanDau, BigDecimal soTienKM){
        //Giảm theo phần trăm
        // 0 < soTienKM < 1
        if(soTienKM.compareTo(BigDecimal.valueOf(1)) < 0 && soTienKM.compareTo(BigDecimal.valueOf(0)) > 0){
            return(giaBanDau.multiply(soTienKM));
        }
        //Giảm theo giá bán
        return(giaBanDau.subtract(soTienKM));
    }
    
    public BigDecimal getTienThoi(BigDecimal tienKhachDua, BigDecimal tongTien){
        return(tienKhachDua.subtract(tongTien));
    } 

    public HoaDonDTO getInstanceByID(int id){
        return(hoaDonDAO.getInstanceByID(id));
    }

    // Hàm xuất hóa đơn ra PDF
    public void xuatHoaDonPDF(HoaDonDTO hoaDon) {
        List<CT_HoaDonDTO> listChiTiet = getChiTietHoaDon(hoaDon.getMaHD());
        hoaDonDAO.xuatHoaDonPDF(hoaDon, listChiTiet);
    }
    private List<CT_HoaDonDTO> getChiTietHoaDon(int maHD) {
        return CT_HoaDonBUS.getInstance().getListCTHDByMaHD(maHD);
    }
    
    public String layGiaTriKhuyenMaiTheoHoaDon(HoaDonDTO hoaDon) {
        int maKM = hoaDon.getMaKM();
    
        if (maKM <= 0) {
            return "0";
        }
    
        KhuyenMaiDTO km = khuyenMaiBUS.getInstanceByMa(maKM); 
    
        if (km != null && km.getGiaTriGiam() != null) {
            return km.getGiaTriGiam().toString();
        } else {
            return "0";
        }
    }
    
}