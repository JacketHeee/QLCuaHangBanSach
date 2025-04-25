package utils;

import interfaces.ExcelExportable;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.lang.reflect.Field;


public class ExcelExporter {

    public static <T> void exportToExcel(ExcelExportable<T> exporter, Class<T> clazz) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn vị trí lưu file Excel");

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files","xlsx");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION){
            System.out.println("Đã hủy lưu file!");
            return;
        }

        //Lấy file
        File fileToSave = fileChooser.getSelectedFile();

        //Thêm đuôi .xlsx nếu ch có
        if (!fileToSave.getName().toLowerCase().endsWith(".xlsx")){
            fileToSave = new File(fileToSave.getAbsolutePath()+".xlsx");
        }

        //KT trùng tên
        if (fileToSave.exists()){
            int overwrite = JOptionPane.showConfirmDialog(null, "File đã tồn tại. Ghi đè?", "Xác nhận", JOptionPane.YES_NO_OPTION);
         if (overwrite != JOptionPane.YES_OPTION) {
        return;
    }
}
        
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
    
        int rowIndex = 0;
        List<String> headers = exporter.getColumnHeaders();

        // Tiêu đề
        Row titleRow = sheet.createRow(rowIndex++);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(exporter.getTitle());

        // Style cho tiêu đề
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 14);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCell.setCellStyle(titleStyle);

        
        // Merge động dòng tiêu đề theo số lượng cột
        if (!headers.isEmpty()) {
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
          }

        // Style cho Header
        Row headerRow = sheet.createRow(rowIndex++);

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(headerStyle);
        }


        // Dữ liệu dòng
        for (T item : exporter.getData()) {
            Row dataRow = sheet.createRow(rowIndex++);
            List<String> values = extractValues(item);
            for (int i = 0; i < values.size(); i++) {
                dataRow.createCell(i).setCellValue(values.get(i));
            }
        }

        //Chỉnh độ rộng cột
        for (int i = 0; i < headers.size(); i++){
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fos = new FileOutputStream(fileToSave)){
            workbook.write(fos);
            workbook.close();
            JOptionPane.showMessageDialog(null, "Xuất Excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Xuất Excel thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Dùng reflection để bóc dữ liệu từ DTO
    private static <T> List<String> extractValues(T obj) {
        List<String> values = new java.util.ArrayList<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object val = field.get(obj);
                values.add(val != null ? val.toString() : "");
            } catch (IllegalAccessException e) {
                values.add("");
            }
        }
        return values;
    }
}
