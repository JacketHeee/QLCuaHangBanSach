package GUI;

import javax.swing.JFrame;

public class Login extends JFrame{

    public Login() {
        init(); 
    }
    
    private void init() {
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}