package search;

import java.util.ArrayList;

import DTO.SachDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class SachSearch implements Searchable<SachDTO>{

    private ArrayList<SachDTO> danhSach;

    public SachSearch(ArrayList<SachDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<SachDTO> search(String keyword) {
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        return new ArrayList<>(danhSach.stream()
        .filter(x -> TextUtils.boDau(x.getTenSach()).toLowerCase().contains(keywordFormatted))
        .toList()
        );
    }

}
