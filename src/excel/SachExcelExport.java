package excel;

import java.util.List;

import DTO.SachDTO;
import interfaces.ExcelExportable;

public class SachExcelExport implements ExcelExportable<SachDTO>{

    private final List<SachDTO> data;
    private final String keyword;
    private final String filterColumn;
    private final String[] headers = {
        "Mã sách",
        "Tên sách",
        "Số lượng tồn",
        "Giá bán",
        "Năm xuất bản",
        "Mã vùng",
        "Mã NXB"
    };

    public SachExcelExport(List<SachDTO> data, String filterColumn, String keyword){
        this.data = data;
        this.filterColumn = filterColumn;
        this.keyword = keyword;
    }

    @Override
    public String getTitle() {
        if (filterColumn == null || filterColumn.equalsIgnoreCase("Tất cả") || keyword == null || keyword.isBlank())
            return "DANH SÁCH CÁC SÁCH HIỆN CÓ TẠI CỬA HÀNG";
        return String.format("DANH SÁCH CÁC CÁCH CÓ %s LÀ \"%s\"", filterColumn.toUpperCase(), keyword.toUpperCase());
    }

    @Override
    public List<String> getColumnHeaders() {
        return List.of(headers);
    }

    @Override
    public List<SachDTO> getData() {
        return data;
    }
    
}
