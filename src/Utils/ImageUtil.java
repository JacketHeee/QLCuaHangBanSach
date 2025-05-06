package utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {
    private File selectedFile;
    private final String saveDirectory = "src/resources/img/img_product";

    // Getter cho selectedFile
    public File getSelectedFile() {
        return selectedFile;
    }

    // Setter cho selectedFile
    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    // Getter cho saveDirectory
    public String getSaveDirectory() {
        return saveDirectory;
    }

    // Lấy tên file đã chọn (chỉ tên, không đường dẫn)
    public String getSelectedFileName() {
        if (selectedFile == null) {
            return null;
        }
        return selectedFile.getName();
    }

    // Lấy ảnh đã chọn để hiển thị (xem trước)
    public BufferedImage getPreviewImage(int width, int height) {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(null, "Chưa chọn file ảnh!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        try {
            BufferedImage image = ImageIO.read(selectedFile);
            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            // Chuyển Image thành BufferedImage
            BufferedImage scaledBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scaledBufferedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();
            return scaledBufferedImage;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi đọc ảnh: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // Chọn file ảnh (PNG, JPG)
    public boolean chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image files (PNG, JPG)", "png", "jpg", "jpeg"));

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            try {
                // Kiểm tra file có phải ảnh hợp lệ
                ImageIO.read(selectedFile);
                return true;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi đọc file: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                selectedFile = null;
                return false;
            }
        }
        return false;
    }

    // Lưu file ảnh vào saveDirectory, trả về tên file đã lưu
    public String saveFile() throws IOException {
        if (selectedFile == null) {
            throw new IOException("Chưa chọn file ảnh!");
        }

        // Tạo thư mục lưu nếu chưa tồn tại
        File directory = new File(saveDirectory);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new IOException("Không thể tạo thư mục: " + directory.getAbsolutePath());
            }
        }

        // Đọc ảnh
        BufferedImage image = ImageIO.read(selectedFile);
        String originalName = selectedFile.getName();
        String extension = originalName.substring(originalName.lastIndexOf(".") + 1).toLowerCase();
        String baseName = originalName.substring(0, originalName.lastIndexOf("."));

        // Kiểm tra định dạng
        if (!extension.equals("png") && !extension.equals("jpg") && !extension.equals("jpeg")) {
            throw new IOException("Chỉ hỗ trợ định dạng PNG và JPG!");
        }

        // Tạo tên file duy nhất
        String newFileName = generateUniqueFileName(baseName, extension);
        File outputFile = new File(saveDirectory, newFileName);

        // Lưu ảnh
        boolean saved = ImageIO.write(image, extension, outputFile);
        if (saved && outputFile.exists()) {
            return newFileName;
        } else {
            throw new IOException("Không thể lưu ảnh vào: " + outputFile.getPath());
        }
    }

    // Tạo tên file duy nhất
    private String generateUniqueFileName(String baseName, String extension) {
        String newFileName = baseName + "." + extension;
        File file = new File(saveDirectory, newFileName);
        int counter = 1;

        while (file.exists()) {
            newFileName = baseName + "_" + counter + "." + extension;
            file = new File(saveDirectory, newFileName);
            counter++;
        }

        return newFileName;
    }
}