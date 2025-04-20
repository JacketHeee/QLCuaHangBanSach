package GUI.component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class CustomTextFieldSL extends JTextField implements KeyListener{
    public CustomTextFieldSL(){
        super("0");
        init();
    }

    public void init(){
        this.addKeyListener(this);
        this.setCaretPosition(1);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if(!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE){
            e.consume();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(this.getText().length() == 1 && e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
            this.setText("00"); // vì sự kiện này chạy trước khi text được set lại nên cài đặt 2 số
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        String firstNum = this.getText().substring(0, 1);

        if(this.getText().length() == 2 && firstNum.equals("0")){
            String originalText = this.getText();
            this.setText(originalText.substring(1));
        }
    } 
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new CustomTextFieldSL());
        frame.setSize(100, 100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
