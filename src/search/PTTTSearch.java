package search;

import java.util.ArrayList;

import javax.swing.JComboBox;

import DTO.PhuongThucTTDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class PTTTSearch implements Searchable<PhuongThucTTDTO>{
    // private String[] header = {"Mã phương thức", "Tên phương thức thanh toán"};
    private ArrayList<PhuongThucTTDTO> danhSach;

    public PTTTSearch(ArrayList<PhuongThucTTDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<PhuongThucTTDTO> search(String keyword, JComboBox<String> comboBox) {
        String selectedItem = (String)comboBox.getSelectedItem(); 
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        if(selectedItem.equals("Tất cả")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaPT() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getTenPTTT()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        if(selectedItem.equals("Mã phương thức")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaPT() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        //Tên phương thức thanh toán
        else {
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getTenPTTT()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
    }

}

