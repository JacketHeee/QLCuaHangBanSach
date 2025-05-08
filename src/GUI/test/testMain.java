package GUI.test;

import javax.swing.JFrame;

import GUI.component.TransparentBackgroundPanel;

public class testMain extends JFrame {
    
    public testMain() {
        init();
    }

    private void init() {
        setSize(700,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         TransparentBackgroundPanel panel = new TransparentBackgroundPanel("hihi");
         add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        
        new testMain();
    }
}
