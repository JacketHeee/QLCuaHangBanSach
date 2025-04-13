package GUI.component;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class CustomRoundedLabel extends JLabel {
    private int arcTopLeft;
    private int arcTopRight;
    private int arcBottomLeft;
    private int arcBottomRight;

    public CustomRoundedLabel(String text, int arcTopLeft, int arcTopRight, int arcBottomLeft, int arcBottomRight) {
        super(text);
        this.arcTopLeft = arcTopLeft;
        this.arcTopRight = arcTopRight;
        this.arcBottomLeft = arcBottomLeft;
        this.arcBottomRight = arcBottomRight;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Tạo hình dạng bo góc tùy chỉnh bằng Path2D
        Path2D.Float path = new Path2D.Float();
        float width = getWidth() - 1;
        float height = getHeight() - 1;

        // Bắt đầu từ điểm trên cùng bên trái, sau góc trên trái
        path.moveTo(arcTopLeft, 0);
        // Góc trên phải
        path.lineTo(width - arcTopRight, 0);
        path.quadTo(width, 0, width, arcTopRight);
        // Góc dưới phải
        path.lineTo(width, height - arcBottomRight);
        path.quadTo(width, height, width - arcBottomRight, height);
        // Góc dưới trái
        path.lineTo(arcBottomLeft, height);
        path.quadTo(0, height, 0, height - arcBottomLeft);
        // Góc trên trái
        path.lineTo(0, arcTopLeft);
        path.quadTo(0, 0, arcTopLeft, 0);
        path.closePath();

        // Vẽ nền
        g2.setColor(getBackground());
        g2.fill(path);

        // Vẽ viền (tùy chọn)
        g2.setColor(UIManager.getColor("Component.borderColor"));
        g2.draw(path);

        g2.dispose();

        // Vẽ văn bản và các thành phần khác của JLabel
        super.paintComponent(g);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}