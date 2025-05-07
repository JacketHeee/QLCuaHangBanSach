package GUI.forms;

import java.awt.Color;
import java.util.function.Consumer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import com.formdev.flatlaf.FlatClientProperties;
import com.toedter.calendar.JDateChooser;

import BUS.NhanVienBUS;
import BUS.TaiKhoanBUS;
import DTO.NhanVienDTO;
import DTO.TaiKhoanDTO;
import GUI.component.CustomBoldJLabel;
import GUI.component.CustomButton;
import GUI.component.CustomTextFieldSL;
import GUI.component.InputForm;
import GUI.component.InputFormItem;
import GUI.component.PanelPicture;
import GUI.component.TransparentBackgroundPanel;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import resources.base.baseTheme;
import utils.DateCalculator;
import utils.UIUtils;
import utils.Validate;

public class ThongTinTaiKhoanForm extends TransparentBackgroundPanel{
    private TaiKhoanDTO taikhoan; 
    private NhanVienDTO nv;
    private NhanVienBUS nVienBUS;
    private TaiKhoanBUS tKhoanBUS;

    private Consumer<String> call;
    public ThongTinTaiKhoanForm(TaiKhoanDTO taiKhoan,Consumer<String> callback) {
        super();
        this.call = callback;
        this.taikhoan = taiKhoan;
        nVienBUS = NhanVienBUS.getInstance();
        tKhoanBUS = TaiKhoanBUS.getInstance();
        nv = nVienBUS.getInstanceByMa(nVienBUS.getMaNVByMaTK(taiKhoan.getMaTK()));
        init(); 
    }

    private void init() {
        setLayout(new MigLayout("al center center,gap 20,wrap 1, insets 10"));
        CustomBoldJLabel header = new CustomBoldJLabel("Thông tin tài khoản", 2);
        // header.setForeground(Color.decode(baseTheme.mainColor));
        add(header,"al center"); 
        add(mainContent(),"push,grow");
    }

    private JPanel mainContent() {
        JPanel panel = new JPanel(new MigLayout("al center center, gap 20")); 
        panel.setOpaque(false);

        panel.add(panelThongTinNhanvien(),"wmin 400");
        panel.add(panelThongTinAccount(),"wmin 400");
        return panel;
    }

    private int statusChangeTTCN = 0; // 0 không là đổi thông tin, 1 là lưu, lưu xong lại đổi thành 0

    private InputForm inputFormTT;
    private JPanel panelThongTinNhanvien() {
        JPanel panel = new JPanel(new MigLayout("insets 20 0 20 0"));
        // panel.setOpaque(true);
        // panel.setBackground(Color.decode(baseTheme.backgroundColor));
        panel.setBackground(Color.white);
        panel.putClientProperty(FlatClientProperties.STYLE, "arc: 10");

        panel.add(new CustomBoldJLabel("Thông tin nhân viên", 1),"al center, wrap");

        String[][] att = {
            {"textbox","Họ và tên"},
            {"inputDate","Ngày sinh"},
            {"combobox","Giới tính"},
            {"textbox","Số điện thoại"}
        };

        inputFormTT = new InputForm(att); 
        inputFormTT.getListItem().get(2).getCombobox().setModel(new DefaultComboBoxModel<>(new String[]{"Nam","Nữ"}));
        loadThongTin();

        // inputFormTT.setBackground(Color.decode(baseTheme.backgroundColor));
        setEditTable(false);

        panel.add(inputFormTT,"push,grow,wrap");

        JButton butDoiThongTin = new JButton("Sửa thông tin"); 
        panel.add(butDoiThongTin,"al center"); 

        butDoiThongTin.addActionListener(e -> {
            if (statusChangeTTCN == 0) {
                setEditTable(true);
                statusChangeTTCN = 1; 
                butDoiThongTin.setText("Lưu");
            }
            else {
                setEditTable(false);
                statusChangeTTCN = 0; 
                butDoiThongTin.setText("Sửa thông tin");
                if (validation()) {
                    update();
                }
            }
        });

        return panel;

    }

    private void loadThongTin() {
        inputFormTT.getListItem().get(0).setText(nv.getHoTen());
        inputFormTT.getListItem().get(1).setDate(nv.getNgaySinh());
        inputFormTT.getListItem().get(2).getCombobox().setSelectedItem(nv.getGioiTinh());
        inputFormTT.getListItem().get(3).setText(nv.getSoDT());
    }

    private void setEditTable(boolean status) {
        for (InputFormItem x : inputFormTT.getListItem()) {
            switch (x.type) {
                case "textbox":
                    x.getTextField().setEditable(status);
                    break;
                case "combobox":
                    x.getCombobox().setEnabled(status);
                    break;
                case "inputDate":
                    x.getInputDate().setEnabled(status);
                    break;
            }
        }
    }

    private InputForm input;

    private JPanel panelThongTinAccount() {
        JPanel panel = new JPanel(new MigLayout("wrap 1,insets 20 0 20 0")); 
        // panel.setOpaque(true);
        // panel.setBackground(Color.decode(baseTheme.backgroundColor));
        panel.setBackground(Color.white);
        panel.putClientProperty(FlatClientProperties.STYLE,"arc: 10");

        panel.add(new CustomBoldJLabel("Đổi mật khẩu", 1), "al center"); 

        String[][] att = {
            {"pass","Mật khẩu hiện tại"},
            {"pass","Mật khẩu mới"},
            {"pass","Xác nhận mật khẩu"}
        };

        input = new InputForm(att);
        // input.setBackground(Color.decode(baseTheme.backgroundColor));
        panel.add(input,"push,grow");
        

        JButton butDoiMatKhau =new JButton("Lưu");
        panel.add(butDoiMatKhau,"al center");
        
        butDoiMatKhau.addActionListener(e -> {
            // JOptionPane.showMessageDialog(null, "Lấy thông tin để đổi mật khẩu");
            if (validateDoiMatKhau()) {
                // UIUtils.showNotifi(null, "Đổi mặt khẩu thành công",1);
                updatePass();
            }
        });
        
        return panel; 
    }


    public void update(){
        String ten = inputFormTT.getListItem().get(0).getText();
        java.sql.Date ngaySinh = inputFormTT.getListItem().get(1).getDate();
        String gioiTinh = inputFormTT.getListItem().get(2).getSelection();
        String soDT = inputFormTT.getListItem().get(3).getText();
        // NhanVienDTO nhanVien = new NhanVienDTO(ma, ten, ngaySinh, gioiTinh, soDT);
        nv.setHoTen(ten);
        nv.setNgaySinh(ngaySinh);
        nv.setGioiTinh(gioiTinh);
        nv.setSoDT(soDT);
        if(nVienBUS.update(nv) != 0){
            Notifications.getInstance().setJFrame(null);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Sửa thành công");
            call.accept(ten);
        }
        else{
            UIUtils.showNotifi(null, "Sửa thất bại!",3);
        }
    }

    public void updatePass() {
        String newPass = input.getListItem().get(1).getTextPass();
        taikhoan.setPassword(newPass);

        if (tKhoanBUS.update(taikhoan) != 0) {
            Notifications.getInstance().setJFrame(null);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Sửa thành công");
            resetAreaPass();
        }
    }

    private void resetAreaPass() {
        input.getListItem().get(0).getPass().setText("");
        input.getListItem().get(1).getPass().setText("");;
        input.getListItem().get(2).getPass().setText("");; 
    }

    public boolean validateDoiMatKhau() {
        JPasswordField currentPass = input.getListItem().get(0).getPass();
        JPasswordField newPass = input.getListItem().get(1).getPass();
        JPasswordField confirmPass= input.getListItem().get(2).getPass(); 

        String current = new String(currentPass.getPassword()); 
        String newP = new String(newPass.getPassword());
        String confirm = new String(confirmPass.getPassword());

        if (Validate.isEmpty(current)) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mật khẩu hiện tại!","",JOptionPane.WARNING_MESSAGE);
            currentPass.requestFocusInWindow();
            return false;
        }
        if (Validate.isEmpty(newP)) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mật khẩu mới!","",JOptionPane.WARNING_MESSAGE);
            newPass.requestFocusInWindow();
            return false;
        }
        if (Validate.isEmpty(confirm)) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập lại mật khẩu mới!","",JOptionPane.WARNING_MESSAGE);
            confirmPass.requestFocusInWindow();
            return false;
        }

        if (!current.equals(taikhoan.getPassword())) {
            JOptionPane.showMessageDialog(null, "Mật khẩu hiện tại không đúng!","",JOptionPane.WARNING_MESSAGE);
            currentPass.requestFocusInWindow();
            return false;
        }

        if (current.equals(newP)) {
            JOptionPane.showMessageDialog(null, "Mật khẩu mới phải khác mật khẩu cũ!","",JOptionPane.WARNING_MESSAGE);
            newPass.requestFocusInWindow();
            return false;
        }

        if (!confirm.equals(newP)) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập lại đúng mật khẩu mới!","",JOptionPane.WARNING_MESSAGE);
            confirmPass.requestFocusInWindow();
            return false;
        }

        if (newP.length() < 5) {
            JOptionPane.showMessageDialog(null, "Mật khẩu quá yếu! Vui lòng đổi mật khẩu trên 5 ký tự!","",JOptionPane.WARNING_MESSAGE);
            newPass.requestFocusInWindow();
            return false;
        }

        return true;
    }

    public boolean validation(){
        JTextField tenNV = inputFormTT.getListItem().get(0).getTextField();
        String ngaySinh = inputFormTT.getListItem().get(1).getDateString();
        JTextField soDT = inputFormTT.getListItem().get(3).getTextField();

        if(Validate.isEmpty(tenNV.getText())){
            JOptionPane.showMessageDialog(null, "Tên nhân viên không được để trống!","",JOptionPane.WARNING_MESSAGE);
            tenNV.requestFocusInWindow();
            return(false);
        }
        if(!Validate.lengthGreaterThan(tenNV.getText(), 5)){
            JOptionPane.showMessageDialog(null, "Tên nhân viên phải có độ dài trên 5 ký tự!","",JOptionPane.WARNING_MESSAGE);
            tenNV.requestFocusInWindow();
            return(false);
        }
        if(Validate.isEmpty(ngaySinh)){
            JOptionPane.showMessageDialog(null, "Ngày sinh không được để trống!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        if(!Validate.isDate(ngaySinh)){
            JOptionPane.showMessageDialog(null, "Ngày sinh phải nhập đúng định dạng!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        if(Validate.isEmpty(soDT.getText())){
            JOptionPane.showMessageDialog(null, "Số điện thoại nhân viên không được để trống!","",JOptionPane.WARNING_MESSAGE);
            soDT.requestFocusInWindow();
            return(false);
        }
        if(!Validate.isPhoneNumber(soDT.getText())){
            JOptionPane.showMessageDialog(null, "Số điện thoại nhân viên phải nhập đúng định dạng!","",JOptionPane.WARNING_MESSAGE);
            soDT.requestFocusInWindow();
            return(false);
        }
        return(true);
    }
}
