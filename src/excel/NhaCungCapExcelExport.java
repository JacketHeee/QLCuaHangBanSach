package excel;

import DTO.NhaCungCapDTO;
import interfaces.ExcelExportable;

import java.util.List;

public class NhaCungCapExcelExport implements ExcelExportable<NhaCungCapDTO>{

    private final List<NhaCungCapDTO> data;
    private final String keyword;
    private final String filterColumn;
    private final String[] headers = {"Mã nhà cung cấp","Tên nhà cung cấp","Địa chỉ","Số điện thoại","Email"};

    public NhaCungCapExcelExport(List<NhaCungCapDTO> data, String filterColumn, String keyword){
        this.data = data;
        this.filterColumn = filterColumn;
        this.keyword = keyword;
    }

    @Override
    public String getTitle() {
        if (filterColumn == null || filterColumn.equalsIgnoreCase("Tất cả") || keyword == null || keyword.isBlank())
            return "DANH SÁCH CÁC NHÀ CUNG CẤP";
        return String.format("DANH SÁCH CÁC NHÀ CUNG CẤP CÓ %s LÀ \"%s\"", filterColumn.toUpperCase(), keyword.toUpperCase());
    }

    @Override
    public List<String> getColumnHeaders() {
        return List.of(headers);
    }

    @Override
    public List<NhaCungCapDTO> getData() {
        return data;
    }
    
}
