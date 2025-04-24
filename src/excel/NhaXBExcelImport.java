package excel;

import DTO.NhaXBDTO;
import DTO.NhaXBDTO;
import DTO.NhaXBDTO;
import interfaces.ExcelImportable;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NhaXBExcelImport implements ExcelImportable<NhaXBDTO> {

    @Override
    public List<NhaXBDTO> readFromExcel(File file) throws Exception {
        List<NhaXBDTO> list = new ArrayList<>();
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
                NhaXBDTO dto = new NhaXBDTO();
                dto.setMaNXB(Integer.parseInt(rowData.get(0)));
                dto.setTenNXB(rowData.get(1));
                dto.setDiaChi(rowData.get(2));
                dto.setSoDT(rowData.get(3));
                dto.setEmail(rowData.get(4));        
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
