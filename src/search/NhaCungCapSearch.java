package search;

import java.util.ArrayList;

import DTO.NhaCungCapDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class NhaCungCapSearch implements Searchable<NhaCungCapDTO>{

    private ArrayList<NhaCungCapDTO> danhSach;

    public NhaCungCapSearch(ArrayList<NhaCungCapDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<NhaCungCapDTO> search(String keyword) {
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        return new ArrayList<>(danhSach.stream()
        .filter(x -> TextUtils.boDau(x.getTenNCC()).toLowerCase().contains(keywordFormatted))
        .toList()
        );
    }

}
