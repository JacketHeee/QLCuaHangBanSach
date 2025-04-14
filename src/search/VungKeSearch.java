package search;

import java.util.ArrayList;

import DTO.ViTriVungDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class VungKeSearch implements Searchable<ViTriVungDTO>{

    private ArrayList<ViTriVungDTO> danhSach;

    public VungKeSearch(ArrayList<ViTriVungDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<ViTriVungDTO> search(String keyword) {
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        return new ArrayList<>(danhSach.stream()
            .filter(x -> TextUtils.boDau(x.getTenVung()).toLowerCase().contains(keywordFormatted))
            .toList()
        );
    }

}

