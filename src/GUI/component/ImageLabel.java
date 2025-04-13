package GUI.component;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageLabel extends JLabel {
    private Image originalImage;

    public ImageLabel(String imagePath) {
        // Load ảnh từ resources
        System.out.println(imagePath);
        URL imageUrl = getClass().getResource("/resources/img/icon/"+imagePath);
        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);
            originalImage = icon.getImage();
        } else {
            System.err.println("Không tìm thấy ảnh: " + imagePath);
        }

        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (originalImage != null) {
            int width = 400;
            int height = 500;

            // Scale ảnh theo kích thước hiện tại của label
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            g.drawImage(scaledImage, 0, 0, width, height, this);
        }
    }
}
