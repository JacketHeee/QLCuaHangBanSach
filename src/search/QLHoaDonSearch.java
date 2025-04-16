package search;

import java.util.ArrayList;

import javax.swing.JComboBox;

import DTO.HoaDonDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class QLHoaDonSearch implements Searchable<HoaDonDTO>{
    // private String[] header = {"Mã hóa đơn", "Ngày lập", "Tổng tiền", "Mã tài khoản", "Mã phương thức", "Mã khuyến mãi", "Mã khách hàng"};
    private ArrayList<HoaDonDTO> danhSach;

    public QLHoaDonSearch(ArrayList<HoaDonDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<HoaDonDTO> search(String keyword, JComboBox<String> comboBox) {
        String selectedItem = (String)comboBox.getSelectedItem(); 
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        if(selectedItem.equals("Tất cả")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaHD() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getNgayBan() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getTongTien() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getMaTK() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getMaPT() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getMaKM() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getMaKH() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Mã hóa đơn")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaHD() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Ngày lập")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getNgayBan() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Tổng tiền")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getTongTien() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Mã tài khoản")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaTK() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Mã phương thức")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaPT() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Mã khuyến mãi")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaKM() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        //Mã khách hàng
        else {
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaKH() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }

    }

}

