package utils;

import interfaces.ExcelImportable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.util.List;

public class ExcelImporter {
    public static <T> List<T> importFromExcel(ExcelImportable<T> importer) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("excel")); // Gợi ý thư mục
        fileChooser.setDialogTitle("Chọn tệp Excel để nhập");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        File fileToImport = fileChooser.getSelectedFile();

        try {
            return importer.readFromExcel(fileToImport);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đọc file Excel:\n" + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }
}
