package GUI.test;
import javax.swing.JFrame;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.JFileChooser;
public class hihi {
    public static void main(String[] args) {
        JFrame frame = new JFrame(); 

        frame.setSize(1000,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        // FlatIntelliJLaf.setup();
        frame.add(new JFileChooser());
        frame.setVisible(true);
    }
}
