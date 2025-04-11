package search;

import java.util.ArrayList;

import DTO.TaiKhoanDTO;
import interfaces.Searchable;
import utils.TextUtils;

public class TaiKhoanSearch implements Searchable<TaiKhoanDTO>{

    private ArrayList<TaiKhoanDTO> danhSach;

    public TaiKhoanSearch(ArrayList<TaiKhoanDTO> danhSach) {
        this.danhSach = danhSach;
    }

    @Override
    public ArrayList<TaiKhoanDTO> search(String keyword) {
        String keywordFormatted = TextUtils.boDau(keyword)
                                   .toLowerCase()
                                   .trim()
                                   .replaceAll("\\s+", " ");

        return new ArrayList<>(danhSach.stream()
            .filter(x -> TextUtils.boDau(x.getMaRole()+"").toLowerCase().contains(keywordFormatted))
            .toList()
        );
    }

}

