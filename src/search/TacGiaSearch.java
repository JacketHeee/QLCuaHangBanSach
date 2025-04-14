package search;

import java.util.ArrayList;

import DTO.TacGiaDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class TacGiaSearch implements Searchable<TacGiaDTO>{

    private ArrayList<TacGiaDTO> danhSach;

    public TacGiaSearch(ArrayList<TacGiaDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<TacGiaDTO> search(String keyword) {
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        return new ArrayList<>(danhSach.stream()
            .filter(x -> TextUtils.boDau(x.getTenTacGia()).toLowerCase().contains(keywordFormatted))
            .toList()
        );
    }

}

