package utils;

import DTO.SachDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.List;

public class ExcelExporter {

    public static void exportSachListToExcel(List<SachDTO> sachList, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Danh sách sách");

            // Tạo header
            String[] headers = {"Mã sách", "Tên sách", "Số lượng", "Giá bán", "Giá nhập", "Năm XB", "Mã vùng", "Mã NXB"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);

                // Optional: in đậm header
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            // Ghi dữ liệu
            int rowIndex = 1;
            for (SachDTO sach : sachList) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(sach.getMaSach());
                row.createCell(1).setCellValue(sach.getTenSach());
                row.createCell(2).setCellValue(sach.getSoLuong());
                row.createCell(3).setCellValue(sach.getGiaBan().doubleValue());
                row.createCell(4).setCellValue(
                    sach.getGiaNhap() != null ? sach.getGiaNhap().doubleValue() : 0.0
                );
                row.createCell(5).setCellValue(sach.getNamXB());
                row.createCell(6).setCellValue(sach.getMaVung());
                row.createCell(7).setCellValue(sach.getMaNXB());
            }

            // Tự động co độ rộng cột
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }

            System.out.println("✅ Đã xuất Excel thành công: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Xuất Excel thất bại!");
        }
    }
}
