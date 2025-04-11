package search;

import java.util.ArrayList;

import DTO.NhomQuyenDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class PhanQuyenSearch implements Searchable<NhomQuyenDTO>{

    private ArrayList<NhomQuyenDTO> danhSach;

    public PhanQuyenSearch(ArrayList<NhomQuyenDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<NhomQuyenDTO> search(String keyword) {
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        return new ArrayList<>(danhSach.stream()
        .filter(x -> TextUtils.boDau(x.getTenRole()).toLowerCase().contains(keywordFormatted))
        .toList()
        );
    }

}
