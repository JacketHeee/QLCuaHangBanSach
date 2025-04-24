package excel;

import DTO.PhuongThucTTDTO;
import interfaces.ExcelExportable;

import java.util.List;

public class PhuongThucThanhToanExcelExport implements ExcelExportable<PhuongThucTTDTO>{

    private final List<PhuongThucTTDTO> data;
    private final String keyword;
    private final String filterColumn;
    private final String[] headers = {"Mã phương thức", "Tên phương thức thanh toán"};

    public PhuongThucThanhToanExcelExport(List<PhuongThucTTDTO> data, String filterColumn, String keyword){
        this.data = data;
        this.filterColumn = filterColumn;
        this.keyword = keyword;
    }

    @Override
    public String getTitle() {
        if (filterColumn == null || filterColumn.equalsIgnoreCase("Tất cả") || keyword == null || keyword.isBlank())
            return "DANH SÁCH CÁC PHƯƠNG THỨC THANH TOÁN HIỆN CÓ";
        return String.format("DANH SÁCH CÁC PHƯƠNG THỨC THANH TOÁN CÓ %s LÀ \"%s\"", filterColumn.toUpperCase(), keyword.toUpperCase());
    }

    @Override
    public List<String> getColumnHeaders() {
        return List.of(headers);
    }

    @Override
    public List<PhuongThucTTDTO> getData() {
        return data;
    }
    
}
