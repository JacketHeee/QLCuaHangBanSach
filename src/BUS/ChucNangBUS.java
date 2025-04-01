package BUS;

import java.util.ArrayList;
import DAO.ChucNangDAO;
import DTO.ChucNangDTO;

public class ChucNangBUS {
    private static ChucNangBUS instance;
    private ChucNangDAO chucNangDAO;
    private ArrayList<ChucNangDTO> listChucNang;
    
    private ChucNangBUS() {
        chucNangDAO = ChucNangDAO.getInstance();
        listChucNang = chucNangDAO.getAll();
    }
    
    public static ChucNangBUS getInstance() {
        if (instance == null) {
            instance = new ChucNangBUS();
        }
        return instance;
    }

    public int insert(ChucNangDTO chucNang) {
        if (chucNangDAO.insert(chucNang) != 0) {
            listChucNang.add(chucNang);
            return 1;
        }
        return 0;
    }

    public int delete(int id) {
        if (chucNangDAO.delete(id) != 0) {
            int index = getIndexByID(id);
            listChucNang.remove(index);
            return 1;
        }
        return 0;
    }
    
    public int update(ChucNangDTO chucNangDTO) {
        if (chucNangDAO.update(chucNangDTO) != 0) {
            int index = getIndexByID(chucNangDTO.getMaChucNang());
            listChucNang.get(index).setTenChucNang(chucNangDTO.getTenChucNang());
            return 1;
        }
        return 0;
    }
    
    public ArrayList<ChucNangDTO> getAll() {
        return this.listChucNang;
    }

    public int getIndexByID(int id) {
        for (int i = 0; i < listChucNang.size(); i++) {
            if (id == listChucNang.get(i).getMaChucNang()) {
                return i;
            }
        }
        return -1;
    }
}
