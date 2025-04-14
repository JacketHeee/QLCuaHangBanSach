package search;

import java.util.ArrayList;

import DTO.HoaDonDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class QLHoaDonSearch implements Searchable<HoaDonDTO>{

    private ArrayList<HoaDonDTO> danhSach;

    public QLHoaDonSearch(ArrayList<HoaDonDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<HoaDonDTO> search(String keyword) {
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        return new ArrayList<>(danhSach.stream()
        .filter(x -> TextUtils.boDau(x.getMaHD()+"").toLowerCase().contains(keywordFormatted))
        .toList()
        );
    }

}

