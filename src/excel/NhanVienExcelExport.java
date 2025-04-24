package excel;

import DTO.NhanVienDTO;
import interfaces.ExcelExportable;

import java.util.List;

public class NhanVienExcelExport implements ExcelExportable<NhanVienDTO>{

    private final List<NhanVienDTO> data;
    private final String keyword;
    private final String filterColumn;
    private final String[] headers = {"Mã nhân viên","Họ tên","Ngày sinh","Giới tính","Số điện thoại","Mã tài khoản"};

    public NhanVienExcelExport(List<NhanVienDTO> data, String filterColumn, String keyword){
        this.data = data;
        this.filterColumn = filterColumn;
        this.keyword = keyword;
    }

    @Override
    public String getTitle() {
        if (filterColumn == null || filterColumn.equalsIgnoreCase("Tất cả") || keyword == null || keyword.isBlank())
            return "DANH SÁCH TẤT CẢ NHÂN VIÊN";
        return String.format("DANH SÁCH CÁC NHÂN VIÊN CÓ %s LÀ \"%s\"", filterColumn.toUpperCase(), keyword.toUpperCase());
    }

    @Override
    public List<String> getColumnHeaders() {
        return List.of(headers);
    }

    @Override
    public List<NhanVienDTO> getData() {
        return data;
    }
    
}
