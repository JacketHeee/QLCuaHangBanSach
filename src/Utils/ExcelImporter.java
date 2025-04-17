package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import BUS.SachBUS;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import DTO.SachDTO;

public class ExcelImporter {
    public static ArrayList<SachDTO> excelToSachs(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            ArrayList<SachDTO> list = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // bỏ dòng tiêu đề

                SachDTO sach = new SachDTO();
                sach.setTenSach(row.getCell(0).getStringCellValue());
                sach.setGiaBan(BigDecimal.valueOf(row.getCell(1).getNumericCellValue()));
                sach.setNamXB((int) row.getCell(2).getNumericCellValue());
                sach.setMaVung((int) row.getCell(3).getNumericCellValue());
                sach.setMaNXB((int) row.getCell(4).getNumericCellValue());

                list.add(sach);
            }

            workbook.close();
            return list;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi đọc Excel: " + e.getMessage());
        }
    }

    public static ArrayList<SachDTO> importExcelSach(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ArrayList<SachDTO> list = excelToSachs(fis);
            fis.close();
            return list;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi đọc file: " + e.getMessage());
        }
    }
    
}