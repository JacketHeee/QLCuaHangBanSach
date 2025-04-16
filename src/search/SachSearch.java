package search;

import java.util.ArrayList;

import javax.swing.JComboBox;

import DTO.SachDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class SachSearch implements Searchable<SachDTO>{
    // private String[] header = {"Mã sách","Tên sách","Số lượng tồn","Giá bán","Năm xuất bản"};
    private ArrayList<SachDTO> danhSach;

    public SachSearch(ArrayList<SachDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<SachDTO> search(String keyword, JComboBox<String> comboBox) {
        String selectedItem = (String)comboBox.getSelectedItem(); 
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        if(selectedItem.equals("Tất cả")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaSach() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getTenSach()).toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getSoLuong() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getGiaBan() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getNamXB() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Mã sách")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaSach() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Tên sách")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getTenSach()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Số lượng tồn")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getSoLuong() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Giá bán")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getGiaBan() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        //Năm xuất bản
        else {
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getNamXB() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
    }

}
