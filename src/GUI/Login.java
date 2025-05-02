package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.TaiKhoanBUS;
import DTO.TaiKhoanDTO;
import GUI.component.CustomButton;
import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;
import utils.Validate;

public class Login extends JFrame implements ActionListener{

    private CustomButton butLogin; 
    private JTextField txtUsername; 
    private JPasswordField txtPassword; 
    private JLabel forgotPass;
    private TaiKhoanDTO taiKhoanDTO;
    private TaiKhoanBUS taiKhoanBUS;

    public Login() {
        taiKhoanBUS = TaiKhoanBUS.getInstance();
        init(); 
        addActionListener();
    }
    
    private void init() {
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        butLogin = new CustomButton("<html><b>ĐĂNG NHẬP</b></html>");
        txtPassword = new JPasswordField();
        txtPassword.setText("123456");
        txtUsername = new JTextField();
        txtUsername.setText("admin");
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
        
        txtUsername.putClientProperty(FlatClientProperties.STYLE, "showClearButton: true; focusWidth: 0; innerFocusWidth: 0; arc: 10");
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mã nhân viên hoặc số điện thoại");

        txtPassword.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true; focusWidth: 0; innerFocusWidth: 0; arc: 10");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mật khẩu");

        butLogin.putClientProperty(FlatClientProperties.STYLE, "focusWidth: 0; innerFocusWidth: 0; margin: 5,20,5,20; arc: 10;" + 
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
            if(checkLogin()){
                AcceptLogin();
            }
        }
    }

    public boolean checkLogin() {
        String userNameCheck = txtUsername.getText();
        String passWordCheck = new String(txtPassword.getPassword());
        if(Validate.isEmpty(userNameCheck) || Validate.isEmpty(passWordCheck)){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return(false);
        }
        if(!taiKhoanBUS.isExist(userNameCheck) || !taiKhoanBUS.isValidAcount(userNameCheck, passWordCheck)){    //kiểm tra bằng hash sau
            JOptionPane.showMessageDialog(this, "Tên tài khoản hoặc mật khẩu không chính xác!");
            return(false);
        }
        this.taiKhoanDTO = taiKhoanBUS.SelectTaiKhoanByUserName(userNameCheck);
        return(true);
    }

    public void AcceptLogin(){
        this.dispose();
        java.awt.EventQueue.invokeLater(() -> new MainFrame(taiKhoanDTO).setVisible(true));
    }
}