package excel;

import DTO.KhuyenMaiDTO;
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

public class KhuyenMaiExcelImport implements ExcelImportable<KhuyenMaiDTO> {

    @Override
    public List<KhuyenMaiDTO> readFromExcel(File file) throws Exception {
        List<KhuyenMaiDTO> list = new ArrayList<>();
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
                KhuyenMaiDTO dto = new KhuyenMaiDTO();
                dto.setMaKM(Integer.parseInt(rowData.get(0)));
                dto.setTenKM(rowData.get(1));
                dto.setDieuKienGiam(rowData.get(2));
                dto.setGiaTriGiam(new BigDecimal(rowData.get(3).replace(",", "").trim()));
                dto.setNgayBatDau(LocalDateTime.parse(rowData.get(4))); // không cần formatter
                dto.setNgayKetThuc(LocalDateTime.parse(rowData.get(5)));

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
