package GUI.test;

import javax.swing.*;
import java.awt.*;

public class LayeredPaneDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ví dụ JLayeredPane");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(500, 400));

        // Tạo 3 panel với màu khác nhau
        JPanel redPanel = new JPanel();
        redPanel.setBackground(Color.RED);
        redPanel.setBounds(0, 0, 200, 150);

        JPanel greenPanel = new JPanel();
        greenPanel.setBackground(Color.GREEN);
        greenPanel.setBounds(100, 100, 200, 150);

        JPanel bluePanel = new JPanel();
        bluePanel.setBackground(Color.BLUE);
        bluePanel.setBounds(150, 150, 200, 150);

        // Thêm panel vào layeredPane với các layer khác nhau
        layeredPane.add(redPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(greenPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(bluePanel, JLayeredPane.MODAL_LAYER);

        frame.add(layeredPane);
        frame.setVisible(true);
    }
}
