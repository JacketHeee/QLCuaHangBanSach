package GUI.component.chart.barChart;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

public class LabelColor extends JLabel {

    @Override
    protected void paintComponent(Graphics g) { //mục đích là vẽ  hình tròn với kích thước của JLabel
        Graphics2D g2 = (Graphics2D) g; 
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON); //bật khử răng cưa để vẽ mượt mà hơn
        
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height)-4; 
        int x = (width-size) / 2; 
        int y = (height-size) / 2; // tính toán vị trí hình tròn sao cho nằm giua jlabel
        g2.setColor(getBackground());
        g2.fillOval(x,y,size, size); //vẽ hình tròn với kích thước đã tính toán
        // g2.fillOval(2, 2, width, height - 6);

    }
    
    // public static void main(String[] args) {
    //     JFrame fr = new JFrame();
    //     JLabel lab = new LabelColor();
    //     // lab.setOpaque(true);
    //     lab.setBackground(Color.BLACK);
    //     fr.add(lab);

    //     fr.setSize(1200, 300);
    //     fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     fr.setLocationRelativeTo(null);

    //     java.awt.EventQueue.invokeLater(() -> fr.setVisible(true));
    // }
}
