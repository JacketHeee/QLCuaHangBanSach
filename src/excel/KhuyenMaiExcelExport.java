package excel;

import java.util.List;

import DTO.KhuyenMaiDTO;
import interfaces.ExcelExportable;

public class KhuyenMaiExcelExport implements ExcelExportable<KhuyenMaiDTO>{

    private final List<KhuyenMaiDTO> data;
    private final String keyword;
    private final String filterColumn;
    private final String[] headers = {"Mã khuyến mãi","Tên khuyến mãi","Điều kiện giảm","Giá trị giảm", "Ngày bắt đầu", "Ngày kết thúc"};

    public KhuyenMaiExcelExport(List<KhuyenMaiDTO> data, String filterColumn, String keyword){
        this.data = data;
        this.filterColumn = filterColumn;
        this.keyword = keyword;
    }

    @Override
    public String getTitle() {
        if (filterColumn == null || filterColumn.equalsIgnoreCase("Tất cả") || keyword == null || keyword.isBlank())
            return "DANH SÁCH CÁC MÃ KHUYẾN MÃI";
        return String.format("DANH SÁCH CÁC MÃ KHUYỄN MÃI CÓ %s LÀ \"%s\"", filterColumn.toUpperCase(), keyword.toUpperCase());
    }

    @Override
    public List<String> getColumnHeaders() {
        return List.of(headers);
    }

    @Override
    public List<KhuyenMaiDTO> getData() {
        return data;
    }
    
}
