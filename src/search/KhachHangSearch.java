package search;

import java.util.ArrayList;

import javax.swing.JComboBox;

import DTO.KhachHangDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class KhachHangSearch implements Searchable<KhachHangDTO>{
    // {"Mã khách hàng","Tên khách hàng","Số điện thoại","Giới tính"};
    private ArrayList<KhachHangDTO> danhSach;

    public KhachHangSearch(ArrayList<KhachHangDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<KhachHangDTO> search(String keyword, JComboBox<String> comboBox) {
        String selectedItem = (String)comboBox.getSelectedItem();        
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");
        // Xét bộ lọc ở đây
        // if(selectedItem.equals("Tất cả")){
        //     return new ArrayList<>(danhSach.stream()
        //     .filter(x -> 
        //         TextUtils.boDau(x.getMaKH() + "").toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getTenKH()).toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getSoDT()).toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getGioiTinh()).toLowerCase().contains(keywordFormatted)
        //     )
        //     .toList()
        //     );
        // }
        // else 
        if(selectedItem.equals("Mã khách hàng")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaKH() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Tên khách hàng")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getTenKH()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Số điện thoại")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getSoDT()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        //Giới tính
        else {
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getGioiTinh()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
    }

}