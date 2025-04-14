package search;

import java.util.ArrayList;

import DTO.TheLoaiDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class TheLoaiSearch implements Searchable<TheLoaiDTO>{

    private ArrayList<TheLoaiDTO> danhSach;

    public TheLoaiSearch(ArrayList<TheLoaiDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<TheLoaiDTO> search(String keyword) {
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        return new ArrayList<>(danhSach.stream()
            .filter(x -> TextUtils.boDau(x.getTenTheLoai()).toLowerCase().contains(keywordFormatted))
            .toList()
        );
    }

}

