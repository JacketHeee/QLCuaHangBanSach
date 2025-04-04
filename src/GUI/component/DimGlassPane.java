package GUI.component;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class DimGlassPane extends JPanel {
    public DimGlassPane() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Vẽ một lớp mờ màu đen bán trong suốt
        g.setColor(new Color(0, 0, 0, 100)); // 100 là độ mờ, 0-255
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
