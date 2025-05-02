package excel;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import DTO.NhanVienDTO;
import interfaces.ExcelImportable;

public class NhanVienExcelImport implements ExcelImportable<NhanVienDTO> {

    @Override
    public List<NhanVienDTO> readFromExcel(File file) throws Exception {
        List<NhanVienDTO> list = new ArrayList<>();
        FileInputStream fis = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        DataFormatter formatter = new DataFormatter();
        boolean skipHeader = true;

        for (Row row : sheet) {
            if (skipHeader) {
                skipHeader = false;
                continue;
            }

            // Đọc tất cả cell thành List<String>
            List<String> rowData = new ArrayList<>();
            for (Cell cell : row) {
                String value = formatter.formatCellValue(cell).trim();
                rowData.add(value);
            }

            if (rowData.size() < 2) continue; // Bỏ qua dòng không đủ dữ liệu

            try {
                NhanVienDTO dto = new NhanVienDTO();
                dto.setMaNV(Integer.parseInt(rowData.get(0)));
                dto.setHoTen(rowData.get(1));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                dto.setNgaySinh(Date.valueOf(rowData.get(2)));
                
                dto.setGioiTinh(rowData.get(3));
                dto.setSoDT(rowData.get(4));
                dto.setMaTK(Integer.parseInt(rowData.get(5)));

                list.add(dto);
            } catch (NumberFormatException e) {
                System.err.println("Lỗi định dạng số ở dòng: " + row.getRowNum());
            }
        }

        workbook.close();
        fis.close();
        return list;
    }
}
