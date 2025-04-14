package search;

import java.util.ArrayList;

import DTO.KhachHangDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class KhachHangSearch implements Searchable<KhachHangDTO>{

    private ArrayList<KhachHangDTO> danhSach;

    public KhachHangSearch(ArrayList<KhachHangDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<KhachHangDTO> search(String keyword) {
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        return new ArrayList<>(danhSach.stream()
        .filter(x -> TextUtils.boDau(x.getTenKH()).toLowerCase().contains(keywordFormatted))
        .toList()
        );
    }

}