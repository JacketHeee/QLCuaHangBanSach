package BUS;

import java.util.ArrayList;
import DAO.HoaDonDAO;
import DTO.HoaDonDTO;

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
}