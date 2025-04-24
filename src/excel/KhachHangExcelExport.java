package excel;

import DTO.KhachHangDTO;
import interfaces.ExcelExportable;

import java.util.List;

public class KhachHangExcelExport implements ExcelExportable<KhachHangDTO>{

    private final List<KhachHangDTO> data;
    private final String keyword;
    private final String filterColumn;
    private final String[] headers = {"Mã khách hàng","Tên khách hàng","Số điện thoại","Giới tính"};

    public KhachHangExcelExport(List<KhachHangDTO> data, String filterColumn, String keyword){
        this.data = data;
        this.filterColumn = filterColumn;
        this.keyword = keyword;
    }

    @Override
    public String getTitle() {
        if (filterColumn == null || filterColumn.equalsIgnoreCase("Tất cả") || keyword == null || keyword.isBlank())
            return "DANH SÁCH TẤT CẢ KHÁCH HÀNG";
        return String.format("DANH SÁCH CÁC KHÁCH HÀNG CÓ %s LÀ \"%s\"", filterColumn.toUpperCase(), keyword.toUpperCase());
    }

    @Override
    public List<String> getColumnHeaders() {
        return List.of(headers);
    }

    @Override
    public List<KhachHangDTO> getData() {
        return data;
    }
    
}
