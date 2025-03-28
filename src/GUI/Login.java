package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import GUI.component.CustomButton;
import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;

public class Login extends JFrame implements ActionListener{

    public Login() {
        init(); 
        addActionListener();
    }
    
    private void init() {
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        butLogin = new CustomButton("<html><b>ĐĂNG NHẬP</b></html>");
        txtPassword = new JPasswordField();
        txtUsername = new JTextField();
        forgotPass = new JLabel("<html><i><u><a href=''>Quên mật khẩu?</a></u><i></html>");

        cssComponent();
        
        JPanel panel = new JPanel(new MigLayout("al center center, wrap 1, gapy 8"));
        // panel.setBackground(Color.decode(baseTheme.backgroundColor));

        panel.add(new JLabel("<html><strong><font size=+2>Đăng nhập</font></strong><html>"),"al center"); 
        panel.add(new JLabel("<html><font size=-1>Vui lòng đăng nhập tài khoản của bạn!</font></html>"),"al center"); 
        panel.add(new JLabel("Tài khoản"),"gaptop 20");
        panel.add(txtUsername,"grow"); 
        panel.add(new JLabel("Mật khẩu"));
        panel.add(txtPassword,"grow");
        panel.add(forgotPass,"al right");
        panel.add(butLogin,"grow, gaptop 20");

        add(panel);
    }

    private void cssComponent() {
        
        txtUsername.putClientProperty(FlatClientProperties.STYLE, "showClearButton: true; focusWidth: 0; innerFocusWidth: 0");
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mã nhân viên hoặc số điện thoại");

        txtPassword.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true; focusWidth: 0; innerFocusWidth: 0");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mật khẩu");

        butLogin.putClientProperty(FlatClientProperties.STYLE, "focusWidth: 0; innerFocusWidth: 0; margin: 5,20,5,20; arc: 20;" + 
            String.format("background: %s; foreground: %s", baseTheme.mainColor,baseTheme.txtOnMainColor));

        butLogin.setActionCommand("butLogin");
    }

    private void addActionListener() {
        butLogin.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();

        if (str.equals("butLogin")) {
            checkLogin();
        }
    }

    public void checkLogin() {
        this.dispose();
        java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }

    private CustomButton butLogin; 
    private JTextField txtUsername; 
    private JPasswordField txtPassword; 
    private JLabel forgotPass;
}