package search;

import java.util.ArrayList;

import DTO.PhieuNhapDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class QLPhieuNhapSearch implements Searchable<PhieuNhapDTO>{

    private ArrayList<PhieuNhapDTO> danhSach;

    public QLPhieuNhapSearch(ArrayList<PhieuNhapDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<PhieuNhapDTO> search(String keyword) {
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        return new ArrayList<>(danhSach.stream()
            .filter(x -> TextUtils.boDau(x.getMaNhap()+"").toLowerCase().contains(keywordFormatted))
            .toList()
        );
    }

}

