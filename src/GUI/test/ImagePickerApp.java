package GUI.test;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePickerApp extends JFrame {
    private JLabel imageLabel;
    private File selectedImageFile;
    private final String SAVE_DIRECTORY = "../../resources/img/testSaveImg"; // Đường dẫn tương đối trong dự án

    public ImagePickerApp() {
        // Thiết lập JFrame
        setTitle("Image Picker and Saver");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo thư mục lưu ảnh nếu chưa tồn tại
        createSaveDirectory();

        // Tạo layout
        setLayout(new BorderLayout());

        // Tạo panel chứa các nút
        JPanel buttonPanel = new JPanel();
        JButton chooseButton = new JButton("Chọn ảnh");
        JButton saveButton = new JButton("Lưu ảnh");
        buttonPanel.add(chooseButton);
        buttonPanel.add(saveButton);

        // Tạo label hiển thị ảnh
        imageLabel = new JLabel("Chưa chọn ảnh", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(500, 300));

        // Thêm các thành phần vào frame
        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(imageLabel), BorderLayout.CENTER);

        // Xử lý sự kiện nút chọn ảnh
        chooseButton.addActionListener(e -> chooseImage());

        // Xử lý sự kiện nút lưu ảnh
        saveButton.addActionListener(e -> saveImage());
    }

    private void createSaveDirectory() {
        File directory = new File(SAVE_DIRECTORY);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                JOptionPane.showMessageDialog(this, 
                    "Không thể tạo thư mục: " + SAVE_DIRECTORY,
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image files", ImageIO.getReaderFileSuffixes()));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = fileChooser.getSelectedFile();
            try {
                BufferedImage img = ImageIO.read(selectedImageFile);
                Image scaledImg = img.getScaledInstance(500, 300, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImg));
                imageLabel.setText("");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tải ảnh: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveImage() {
        if (selectedImageFile == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ảnh trước khi lưu!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Đọc ảnh từ file đã chọn
            BufferedImage image = ImageIO.read(selectedImageFile);
            String originalName = selectedImageFile.getName();
            String extension = originalName.substring(originalName.lastIndexOf(".") + 1);
            String baseName = originalName.substring(0, originalName.lastIndexOf("."));

            // Tạo tên file duy nhất
            String newFileName = generateUniqueFileName(baseName, extension);
            File outputFile = new File(SAVE_DIRECTORY, newFileName);

            // Lưu ảnh
            boolean saved = ImageIO.write(image, extension, outputFile);
            if (saved && outputFile.exists()) {
                JOptionPane.showMessageDialog(this, 
                        "Ảnh đã được lưu tại: " + outputFile.getPath(),
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                throw new IOException("Không thể lưu ảnh vào file: " + outputFile.getPath());
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu ảnh: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateUniqueFileName(String baseName, String extension) {
        String newFileName = baseName + "." + extension;
        File file = new File(SAVE_DIRECTORY, newFileName);
        int counter = 1;

        // Kiểm tra trùng tên và tạo tên mới nếu cần
        while (file.exists()) {
            newFileName = baseName + "_" + counter + "." + extension;
            file = new File(SAVE_DIRECTORY, newFileName);
            counter++;
        }

        return newFileName;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ImagePickerApp().setVisible(true);
        });
    }
}
