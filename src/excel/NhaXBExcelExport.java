package excel;

import DTO.NhaXBDTO;
import interfaces.ExcelExportable;

import java.util.List;

public class NhaXBExcelExport implements ExcelExportable<NhaXBDTO>{

    private final List<NhaXBDTO> data;
    private final String keyword;
    private final String filterColumn;
    private final String[] headers = { "Mã nhà xuất bản","Tên nhà xuất bản","Địa chỉ","Số điện thoại","Email"};

    public NhaXBExcelExport(List<NhaXBDTO> data, String filterColumn, String keyword){
        this.data = data;
        this.filterColumn = filterColumn;
        this.keyword = keyword;
    }

    @Override
    public String getTitle() {
        if (filterColumn == null || filterColumn.equalsIgnoreCase("Tất cả") || keyword == null || keyword.isBlank())
            return "DANH SÁCH CÁC NHÀ XUẤT BẢN";
        return String.format("DANH SÁCH CÁC NHÀ XUẤT BẢN CÓ %s LÀ \"%s\"", filterColumn.toUpperCase(), keyword.toUpperCase());
    }

    @Override
    public List<String> getColumnHeaders() {
        return List.of(headers);
    }

    @Override
    public List<NhaXBDTO> getData() {
        return data;
    }
    
}
