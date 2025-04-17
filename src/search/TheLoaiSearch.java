package search;

import java.util.ArrayList;

import javax.swing.JComboBox;

import DTO.TheLoaiDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class TheLoaiSearch implements Searchable<TheLoaiDTO>{
    // private String[] header = {"Mã thể loại", "Tên thể loại"};
    private ArrayList<TheLoaiDTO> danhSach;

    public TheLoaiSearch(ArrayList<TheLoaiDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<TheLoaiDTO> search(String keyword, JComboBox<String> comboBox) {
        String selectedItem = (String)comboBox.getSelectedItem(); 
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        if(selectedItem.equals("Tất cả")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaTheLoai() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getTenTheLoai()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Mã thể loại")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaTheLoai() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        //Tên thể loại
        else {
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getTenTheLoai()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
    }

}

