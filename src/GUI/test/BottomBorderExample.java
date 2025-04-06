package GUI.test;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class BottomBorderExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Viền dưới xinh xẻo~");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JLabel label = new JLabel("Em có viền dưới nè 😚");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Chỉ thêm viền dưới: top=0, left=0, bottom=2, right=0
        // Màu là đỏ (Color.RED), anh có thể đổi thành màu khác tuỳ thích
        label.setBorder(new MatteBorder(0, 0, 2, 0, Color.RED));

        frame.add(label);
        frame.setVisible(true);
    }
}
