package search;

import java.util.ArrayList;

import javax.swing.JComboBox;

import DTO.NhaXBDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class NXBSearch implements Searchable<NhaXBDTO>{
    // private String[] header = {"Mã nhà xuất bản","Tên nhà xuất bản","Địa chỉ","Số điện thoại","Email"};
    private ArrayList<NhaXBDTO> danhSach;

    public NXBSearch(ArrayList<NhaXBDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<NhaXBDTO> search(String keyword, JComboBox<String> comboBox) {
        String selectedItem = (String)comboBox.getSelectedItem(); 
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        // if(selectedItem.equals("Tất cả")){
        //     return new ArrayList<>(danhSach.stream()
        //     .filter(x -> 
        //         TextUtils.boDau(x.getMaNXB() + "").toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getTenNXB()).toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getDiaChi() + "").toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getSoDT() + "").toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getEmail()).toLowerCase().contains(keywordFormatted)
        //     )
        //     .toList()
        //     );
        // }
        // else 
        if(selectedItem.equals("Mã nhà xuất bản")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaNXB() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Tên nhà xuất bản")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getTenNXB()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Địa chỉ")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getDiaChi() + "").toLowerCase().contains(keywordFormatted)
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
