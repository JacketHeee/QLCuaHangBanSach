package excel;

import java.util.List;

import DTO.NhomQuyenDTO;
import interfaces.ExcelExportable;

public class NhomQuyenExcelExport implements ExcelExportable<NhomQuyenDTO>{

    private final List<NhomQuyenDTO> data;
    private final String keyword;
    private final String filterColumn;
    private final String[] headers = {"Mã quyền","Tên nhóm quyền"};

    public NhomQuyenExcelExport(List<NhomQuyenDTO> data, String filterColumn, String keyword){
        this.data = data;
        this.filterColumn = filterColumn;
        this.keyword = keyword;
    }

    @Override
    public String getTitle() {
        if (filterColumn == null || filterColumn.equalsIgnoreCase("Tất cả") || keyword == null || keyword.isBlank())
            return "DANH SÁCH TẤT CẢ NHÓM QUYỀN";
        return String.format("DANH SÁCH CÁC NHÓM QUYỀN CÓ %s LÀ \"%s\"", filterColumn.toUpperCase(), keyword.toUpperCase());
    }

    @Override
    public List<String> getColumnHeaders() {
        return List.of(headers);
    }

    @Override
    public List<NhomQuyenDTO> getData() {
        return data;
    }
    
}
