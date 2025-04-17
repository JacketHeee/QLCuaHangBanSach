package search;

import java.util.ArrayList;

import javax.swing.JComboBox;

import DTO.NhaCungCapDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class NhaCungCapSearch implements Searchable<NhaCungCapDTO>{
    // private String[] header = {"Mã nhà cung cấp","Tên nhà cung cấp","Địa chỉ","Số điện thoại","Email"};
    private ArrayList<NhaCungCapDTO> danhSach;

    public NhaCungCapSearch(ArrayList<NhaCungCapDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<NhaCungCapDTO> search(String keyword, JComboBox<String> comboBox) {
        String selectedItem = (String)comboBox.getSelectedItem();
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        if(selectedItem.equals("Tất cả")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaNCC() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getTenNCC()).toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getDiaChi()).toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getSoDT() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getEmail()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Mã nhà cung cấp")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaNCC() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Tên nhà cung cấp")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getTenNCC()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Địa chỉ")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getDiaChi()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Số điện thoại")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getSoDT() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        //Email
        else {
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getEmail()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
    }

}
