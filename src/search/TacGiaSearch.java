package search;

import java.util.ArrayList;

import javax.swing.JComboBox;

import DTO.TacGiaDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class TacGiaSearch implements Searchable<TacGiaDTO>{
    // private String[] header = {"Mã tác giả", "Tên tác giả"};
    private ArrayList<TacGiaDTO> danhSach;

    public TacGiaSearch(ArrayList<TacGiaDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<TacGiaDTO> search(String keyword, JComboBox<String> comboBox) {
        String selectedItem = (String)comboBox.getSelectedItem(); 
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        if(selectedItem.equals("Tất cả")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaTacGia() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getTenTacGia()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Mã tác giả")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaTacGia() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        //Tên tác giả
        else {
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getTenTacGia()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
    }

}

