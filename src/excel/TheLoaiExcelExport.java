package excel;

import DTO.TheLoaiDTO;
import interfaces.ExcelExportable;

import java.util.List;

public class TheLoaiExcelExport implements ExcelExportable<TheLoaiDTO> {

    private final List<TheLoaiDTO> data;
    private final String keyword;
    private final String filterColumn;
    private final String[] headers = {
        "Mã thể loại",
        "Tên thể loại"
    };

    public TheLoaiExcelExport(List<TheLoaiDTO> data, String filterColumn, String keyword){
        this.data = data;
        this.filterColumn = filterColumn;
        this.keyword = keyword;
    }

    @Override
    public String getTitle() {
        if (filterColumn == null || filterColumn.equalsIgnoreCase("Tất cả") || keyword == null || keyword.isBlank())
            return "DANH SÁCH THỂ LOẠI";
        return String.format("DANH SÁCH THỂ LOẠI CÓ %s LÀ \"%s\"", filterColumn.toUpperCase(), keyword.toUpperCase());
    }

    @Override
    public List<String> getColumnHeaders() {
        return List.of(headers);
    }

    @Override
    public List<TheLoaiDTO> getData() {
        return data;
    }
}
