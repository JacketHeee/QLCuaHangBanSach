package BUS;

import DAO.CT_HoaDonDAO;
import DAO.HoaDonDAO;
import DTO.CT_HoaDonDTO;
import DTO.HoaDonDTO;
import java.util.ArrayList;
import utils.PDFHoaDonExporter;

public class HoaDonBUS {
    private static HoaDonBUS instance;
    private HoaDonDAO hoaDonDAO;
    private ArrayList<HoaDonDTO> listHoaDon;

    private HoaDonBUS() {
        hoaDonDAO = HoaDonDAO.getInstance();
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
    
    private ArrayList<CT_HoaDonDTO> getChiTietHoaDon(int maHD) {
        return CT_HoaDonDAO.getInstance().getByMaHD(maHD);
    }
    
    // Phương thức xuất hóa đơn ra PDF
    public void exportHoaDon(int maHD, String folderPath, String tenNhanVien) {
        HoaDonDTO hoaDon = hoaDonDAO.getByID(maHD); 
        if (hoaDon != null) {
            ArrayList<CT_HoaDonDTO> chiTietList = getChiTietHoaDon(maHD);
            PDFHoaDonExporter.exportHoaDonToPDF(hoaDon, chiTietList, folderPath, tenNhanVien);
        }
    }

    private int getIndexByID(int id) {
        for (int i = 0; i < listHoaDon.size(); i++) {
            if (id == listHoaDon.get(i).getMaHD()) {
                return i;
            }
        }
        return -1;
    }
}