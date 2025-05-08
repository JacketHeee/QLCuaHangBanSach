package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {


    public static ArrayList<String[]> openFile(int startRowData) {
        // Tạo JFileChooser để chọn file Excel
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xls", "xlsx"));
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                return readExcelFile(selectedFile,startRowData);
                // JOptionPane.showMessageDialog(null, "File loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    public static ArrayList<String[]> readExcelFile(File file,int startRowData) throws Exception {
        // Mở file Excel
        FileInputStream fis = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên

        // Lấy tiêu đề từ dòng đầu tiên
        Row headerRow = sheet.getRow(2);
        int colCount = headerRow.getPhysicalNumberOfCells();
        String[] headers = new String[colCount];
        for (int i = 0; i < colCount; i++) {
            Cell cell = headerRow.getCell(i);
            headers[i] = cell != null ? cell.toString() : "";
        }

        // Lấy dữ liệu từ các dòng còn lại
        ArrayList<String[]> data = new ArrayList<>();
        for (int i = startRowData; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                String[] rowData = new String[colCount];
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);
                    // rowData[j] = cell != null ? cell.toString() : "";
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case NUMERIC:
                                if (j == 0 || j == 2) { // Cột đầu tiên
                                    rowData[j] = String.valueOf((int) cell.getNumericCellValue()); // Ép về int và chuyển thành String
                                } else {
                                    rowData[j] = String.valueOf(cell.getNumericCellValue());
                                }
                                break;
                            case STRING:
                                rowData[j] = cell.getStringCellValue();
                                break;
                            case BOOLEAN:
                                rowData[j] = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case FORMULA:
                                rowData[j] = cell.getCellFormula();
                                break;
                            default:
                                rowData[j] = "";
                        }
                    } else {
                        rowData[j] = "";
                    }
                }
                data.add(rowData);
            }
        }

        // Hiển thị dữ liệu lên JTable
        // DefaultTableModel model = new DefaultTableModel(data.toArray(new String[0][]), headers);
        // table.setModel(model);

        // Đóng tài nguyên
        workbook.close();
        fis.close();
        return data;
    }

    public static void main(String[] args) {
        for (String[] x : openFile(3)) {
            for (String y : x) 
                System.out.print(y + " ");
            System.out.println();
        }
    }
}
