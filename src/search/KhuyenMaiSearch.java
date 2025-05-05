package search;

import java.util.ArrayList;

import javax.swing.JComboBox;

import DTO.KhuyenMaiDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class KhuyenMaiSearch implements Searchable<KhuyenMaiDTO>{
    // private String[] header = {"Mã khuyến mãi","Tên khuyến mãi","Điều kiện giảm","Giá trị giảm", "Ngày bắt đầu", "Ngày kết thúc"};
    private ArrayList<KhuyenMaiDTO> danhSach;

    public KhuyenMaiSearch(ArrayList<KhuyenMaiDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<KhuyenMaiDTO> search(String keyword, JComboBox<String> comboBox) {
        String selectedItem = (String)comboBox.getSelectedItem();        
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        // if(selectedItem.equals("Tất cả")){
        //     return new ArrayList<>(danhSach.stream()
        //     .filter(x -> 
        //         TextUtils.boDau(x.getMaKM() + "").toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getTenKM()).toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getDieuKienGiam()).toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getGiaTriGiam() + "").toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getNgayBatDau() + "").toLowerCase().contains(keywordFormatted)
        //         || TextUtils.boDau(x.getNgayKetThuc() + "").toLowerCase().contains(keywordFormatted)
        //     )
        //     .toList()
        //     );
        // }
        // else 
        if(selectedItem.equals("Mã khuyến mãi")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getMaKM() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Tên khuyến mãi")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getTenKM()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Điều kiện giảm")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getDieuKienGiam()).toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Giá trị giảm")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getGiaTriGiam() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        else if(selectedItem.equals("Ngày bắt đầu")){
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getNgayBatDau() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
        //Ngày kết thúc
        else {
            return new ArrayList<>(danhSach.stream()
            .filter(x -> 
                TextUtils.boDau(x.getNgayKetThuc() + "").toLowerCase().contains(keywordFormatted)
            )
            .toList()
            );
        }
    }

}
