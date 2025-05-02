package search;

import java.util.ArrayList;

import javax.swing.JComboBox;

import DTO.PhieuNhapDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class QLPhieuNhapSearch implements Searchable<PhieuNhapDTO>{
    // private String[] header = {"Mã nhập","Ngày nhập","","Mã nhà cung cấp","Mã tài khoản"};
    private ArrayList<PhieuNhapDTO> danhSach;
    private ArrayList<PhieuNhapDTO> danhSachHienTai;

    public QLPhieuNhapSearch(ArrayList<PhieuNhapDTO> danhSach) {
        this.danhSach = danhSach;
        this.danhSachHienTai = danhSach;
    }

    @Override
    public ArrayList<PhieuNhapDTO> search(String keyword, JComboBox<String> comboBox) {
        String selectedItem = (String)comboBox.getSelectedItem(); 
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        if(selectedItem.equals("Tất cả")){
            ArrayList<PhieuNhapDTO> list = new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaNhap() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getNgayNhap() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getMaNCC() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getMaTK() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
            return(list);
        }
        else{   //Mã nhập
            ArrayList<PhieuNhapDTO> list = new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaNhap() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
            return(list);
        }

    }

    public void setDanhSachHienTai(ArrayList<PhieuNhapDTO> listPN){
        this.danhSachHienTai = listPN;
    }

    public ArrayList<PhieuNhapDTO> getDanhSachHienTai(){
        return(this.danhSachHienTai);
    }

}

