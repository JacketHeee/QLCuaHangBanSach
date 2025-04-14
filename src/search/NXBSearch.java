package search;

import java.util.ArrayList;

import DTO.NhaXBDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class NXBSearch implements Searchable<NhaXBDTO>{

    private ArrayList<NhaXBDTO> danhSach;

    public NXBSearch(ArrayList<NhaXBDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<NhaXBDTO> search(String keyword) {
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        return new ArrayList<>(danhSach.stream()
        .filter(x -> TextUtils.boDau(x.getTenNXB()).toLowerCase().contains(keywordFormatted))
        .toList()
        );
    }

}
