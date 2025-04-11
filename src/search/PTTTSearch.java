package search;

import java.util.ArrayList;

import DTO.PhuongThucTTDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class PTTTSearch implements Searchable<PhuongThucTTDTO>{

    private ArrayList<PhuongThucTTDTO> danhSach;

    public PTTTSearch(ArrayList<PhuongThucTTDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<PhuongThucTTDTO> search(String keyword) {
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        return new ArrayList<>(danhSach.stream()
        .filter(x -> TextUtils.boDau(x.getTenPTTT()).toLowerCase().contains(keywordFormatted))
        .toList()
        );
    }

}

