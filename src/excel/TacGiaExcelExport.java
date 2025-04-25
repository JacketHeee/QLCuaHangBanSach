package excel;

import DTO.TacGiaDTO;
import interfaces.ExcelExportable;

import java.util.List;

public class TacGiaExcelExport implements ExcelExportable<TacGiaDTO> {

    private final List<TacGiaDTO> data;
    private final String keyword;
    private final String filterColumn;
    private final String[] headers = {
        "Mã tác giả",
        "Tên tác giả"
    };

    public TacGiaExcelExport(List<TacGiaDTO> data, String filterColumn, String keyword){
        this.data = data;
        this.filterColumn = filterColumn;
        this.keyword = keyword;
    }

    @Override
    public String getTitle() {
        if (filterColumn == null || filterColumn.equalsIgnoreCase("Tất cả") || keyword == null || keyword.isBlank())
            return "DANH SÁCH TÁC GIẢ";
        return String.format("DANH SÁCH TÁC GIẢ CÓ %s LÀ \"%s\"", filterColumn.toUpperCase(), keyword.toUpperCase());
    }

    @Override
    public List<String> getColumnHeaders() {
        return List.of(headers);
    }

    @Override
    public List<TacGiaDTO> getData() {
        return data;
    }
}
