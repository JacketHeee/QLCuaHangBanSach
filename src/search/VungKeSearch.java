package search;

import java.util.ArrayList;

import javax.swing.JComboBox;

import DTO.ViTriVungDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class VungKeSearch implements Searchable<ViTriVungDTO>{
    // private String[] header = {"Mã vùng", "Tên vùng"};
    private ArrayList<ViTriVungDTO> danhSach;

    public VungKeSearch(ArrayList<ViTriVungDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<ViTriVungDTO> search(String keyword, JComboBox<String> comboBox) {
        String selectedItem = (String)comboBox.getSelectedItem(); 
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        if(selectedItem.equals("Tất cả")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaVung() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getTenVung()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Mã vùng")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaVung() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        //Tên vùng
        else {
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getTenVung()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
    }

}

