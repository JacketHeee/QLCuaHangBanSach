package search;

import java.util.ArrayList;

import javax.swing.JComboBox;

import BUS.NhomQuyenBUS;
import DTO.TaiKhoanDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class TaiKhoanSearch implements Searchable<TaiKhoanDTO>{
    // private String[] header = {"Mã tài khoản","Username","Password","Quyền"};
    private ArrayList<TaiKhoanDTO> danhSach;

    public TaiKhoanSearch(ArrayList<TaiKhoanDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<TaiKhoanDTO> search(String keyword, JComboBox<String> comboBox) {
        String selectedItem = (String)comboBox.getSelectedItem(); 
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        // if(selectedItem.equals("Tất cả")){
        //     return new ArrayList<>(danhSach.stream()
        //     .filter(x -> 
        //         TextUtils.boDau(x.getMaTK() + "").toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getUsername()).toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getPassword()).toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(NhomQuyenBUS.getInstance().getTenByMaNhomQuyen(x.getMaRole())).toLowerCase().contains(keywordFormatted)
        //     )
        //     .toList()
        //     );
        // }
        // else 
        if(selectedItem.equals("Mã tài khoản")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaTK() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Username")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getUsername()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Password")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getPassword()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        //Quyền
        else {
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(NhomQuyenBUS.getInstance().getTenByMaNhomQuyen(x.getMaRole())).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }

    }

}

