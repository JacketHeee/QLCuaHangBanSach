package search;

import java.util.ArrayList;

import DTO.NhanVienDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class NhanVienSearch implements Searchable<NhanVienDTO>{

    private ArrayList<NhanVienDTO> danhSach;

    public NhanVienSearch(ArrayList<NhanVienDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<NhanVienDTO> search(String keyword) {
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        return new ArrayList<>(danhSach.stream()
        .filter(x -> TextUtils.boDau(x.getHoTen()).toLowerCase().contains(keywordFormatted))
        .toList()
        );
    }

}
