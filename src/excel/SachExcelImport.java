package excel;

import DTO.SachDTO;
import DTO.SachDTO;
import DTO.SachDTO;
import DTO.SachDTO;
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

public class SachExcelImport implements ExcelImportable<SachDTO> {

    @Override
    public List<SachDTO> readFromExcel(File file) throws Exception {
        List<SachDTO> list = new ArrayList<>();
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
                SachDTO dto = new SachDTO();
                dto.setMaSach(Integer.parseInt(rowData.get(0)));
                dto.setTenSach(rowData.get(1));
                dto.setSoLuong(Integer.parseInt(rowData.get(2)));
                dto.setGiaBan(new BigDecimal(rowData.get(3).replace(",", "").trim()));
                dto.setNamXB(Integer.parseInt(rowData.get(4)));
                dto.setMaVung(Integer.parseInt(rowData.get(5)));
                dto.setMaNXB(Integer.parseInt(rowData.get(6)));

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
