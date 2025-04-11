package search;

import java.util.ArrayList;

import DTO.KhuyenMaiDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class KhuyenMaiSearch implements Searchable<KhuyenMaiDTO>{

    private ArrayList<KhuyenMaiDTO> danhSach;

    public KhuyenMaiSearch(ArrayList<KhuyenMaiDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<KhuyenMaiDTO> search(String keyword) {
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        return new ArrayList<>(danhSach.stream()
        .filter(x -> TextUtils.boDau(x.getTenKM()).toLowerCase().contains(keywordFormatted))
        .toList()
        );
    }

}
