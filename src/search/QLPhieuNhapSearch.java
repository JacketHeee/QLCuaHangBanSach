package search;

import java.util.ArrayList;

import javax.swing.JComboBox;

import DTO.PhieuNhapDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class QLPhieuNhapSearch implements Searchable<PhieuNhapDTO>{
    // private String[] header = {"Mã nhập","Ngày nhập","Tổng tiền","Mã nhà cung cấp","Mã tài khoản"};
    private ArrayList<PhieuNhapDTO> danhSach;

    public QLPhieuNhapSearch(ArrayList<PhieuNhapDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<PhieuNhapDTO> search(String keyword, JComboBox<String> comboBox) {
        String selectedItem = (String)comboBox.getSelectedItem(); 
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        if(selectedItem.equals("Tất cả")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaNhap() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getNgayNhap() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getTongTien() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getMaNCC() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getMaTK() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Mã nhập")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaNhap() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Ngày nhập")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getNgayNhap() + "").toLowerCase().contains(keywordFormatted)
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
        else if(selectedItem.equals("Mã nhà cung cấp")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaNCC() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        //Mã tài khoản
        else {
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaTK() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
    }

}

