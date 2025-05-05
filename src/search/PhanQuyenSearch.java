package search;

import java.util.ArrayList;

import javax.swing.JComboBox;

import DTO.NhomQuyenDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class PhanQuyenSearch implements Searchable<NhomQuyenDTO>{
    // private String[] header = {"Mã quyền","Tên nhóm quyền"};

    private ArrayList<NhomQuyenDTO> danhSach;

    public PhanQuyenSearch(ArrayList<NhomQuyenDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<NhomQuyenDTO> search(String keyword, JComboBox<String> comboBox) {
        String selectedItem = (String)comboBox.getSelectedItem(); 
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        // if(selectedItem.equals("Tất cả")){
        //     return new ArrayList<>(danhSach.stream()
        //     .filter(x -> 
        //         TextUtils.boDau(x.getMaRole() + "").toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getTenRole()).toLowerCase().contains(keywordFormatted)
        //     )
        //     .toList()
        //     );
        // }
        // else 
        if(selectedItem.equals("Mã quyền")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaRole() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        //Tên quyền
        else {
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getTenRole() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
    }

}
