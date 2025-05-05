package search;

import java.util.ArrayList;

import javax.swing.JComboBox;

import DTO.HoaDonDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class QLHoaDonSearch implements Searchable<HoaDonDTO>{
    // private String[] header = {"Mã hóa đơn", "Ngày lập", " ", "Mã tài khoản", "Mã phương thức", "Mã khuyến mãi", "Mã khách hàng"};
    private ArrayList<HoaDonDTO> danhSach;
    private ArrayList<HoaDonDTO> danhSachHienTai;

    public QLHoaDonSearch(ArrayList<HoaDonDTO> danhSach) {
        this.danhSach = danhSach;
        this.danhSachHienTai = danhSach;
    }

    @Override
    public ArrayList<HoaDonDTO> search(String keyword, JComboBox<String> comboBox) {
        String selectedItem = (String)comboBox.getSelectedItem(); 
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        // if(selectedItem.equals("Tất cả")){
        //     ArrayList<HoaDonDTO> list = new ArrayList<>(danhSach.stream()
        //     .filter(x -> 
        //         TextUtils.boDau(x.getMaHD() + "").toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getNgayBan() + "").toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getMaTK() + "").toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getMaPT() + "").toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getMaKM() + "").toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getMaKH() + "").toLowerCase().contains(keywordFormatted)
        //     )
        //     .toList()
        //     );
        //     setDanhSachHienTai(list);
        //     return(list);
        // }
        // else 
        if(selectedItem.equals("Mã hóa đơn")){ //Mã hóa đơn
            ArrayList<HoaDonDTO> list = new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaHD() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
            setDanhSachHienTai(list);
            return(list);
        }

        return null;

    }

    public void setDanhSachHienTai(ArrayList<HoaDonDTO> listHD){
        this.danhSachHienTai = listHD;
    }

    public ArrayList<HoaDonDTO> getDanhSachHienTai(){
        return(this.danhSachHienTai);
    }

}

