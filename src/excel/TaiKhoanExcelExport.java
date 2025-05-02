package excel;

import java.util.List;

import DTO.TaiKhoanDTO;
import interfaces.ExcelExportable;

public class TaiKhoanExcelExport implements ExcelExportable<TaiKhoanDTO>{

    private final List<TaiKhoanDTO> data;
    private final String keyword;
    private final String filterColumn;
    private final String[] headers = {"Mã tài khoản","Username","Password","Quyền"};

    public TaiKhoanExcelExport(List<TaiKhoanDTO> data, String filterColumn, String keyword){
        this.data = data;
        this.filterColumn = filterColumn;
        this.keyword = keyword;
    }

    @Override
    public String getTitle() {
        if (filterColumn == null || filterColumn.equalsIgnoreCase("Tất cả") || keyword == null || keyword.isBlank())
            return "DANH SÁCH TẤT CẢ TÀI KHOẢN";
        return String.format("DANH SÁCH CÁC TÀI KHOẢN CÓ %s LÀ \"%s\"", filterColumn.toUpperCase(), keyword.toUpperCase());
    }

    @Override
    public List<String> getColumnHeaders() {
        return List.of(headers);
    }

    @Override
    public List<TaiKhoanDTO> getData() {
        return data;
    }
    
}
