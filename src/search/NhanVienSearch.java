package search;

import java.util.ArrayList;

import javax.swing.JComboBox;

import DTO.NhanVienDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class NhanVienSearch implements Searchable<NhanVienDTO>{
    // private String[] header = {"Mã nhân viên","Họ tên","Ngày sinh","Giới tính","Số điện thoại","Mã tài khoản"};
    private ArrayList<NhanVienDTO> danhSach;

    public NhanVienSearch(ArrayList<NhanVienDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<NhanVienDTO> search(String keyword, JComboBox<String> comboBox) {
        String selectedItem = (String)comboBox.getSelectedItem(); 
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        if(selectedItem.equals("Tất cả")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaNV() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getHoTen()).toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getNgaySinh() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getGioiTinh() + "").toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getSoDT()).toLowerCase().contains(keywordFormatted)
                || TextUtils.boDau(x.getMaTK() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Mã nhân viên")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaNV() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Họ tên")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getHoTen()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Ngày sinh")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getNgaySinh() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Giới tính")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getGioiTinh() + "").toLowerCase().contains(keywordFormatted)
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
        //Tài khoản
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
