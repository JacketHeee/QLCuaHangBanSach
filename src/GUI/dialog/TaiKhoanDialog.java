package GUI.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.NhanVienBUS;
import BUS.NhomQuyenBUS;
import BUS.TaiKhoanBUS;
import DTO.NhanVienDTO;
import DTO.TaiKhoanDTO;
import GUI.MainFrame;
import GUI.component.CustomButton;
import GUI.component.InputForm;
import GUI.forms.TaiKhoanForm;
import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;
import utils.Validate;

public class TaiKhoanDialog extends JDialog implements ActionListener{
    private TaiKhoanForm taiKhoanPanel;
    private MainFrame mainFrame;
    private TaiKhoanBUS taiKhoanBUS; 
    private NhanVienBUS nhanVienBUS;
    private NhomQuyenBUS nhomQuyenBUS;
    private JLabel label;
    private String type;
    private String[][] attributes;
    private InputForm inputForm;
    private String[] listItemNV;
    
    public TaiKhoanDialog(TaiKhoanForm taiKhoanPanel, String title, String function, String type, String[][] attributes){
        super(taiKhoanPanel.getMainFrame(), title, true);
        this.taiKhoanPanel = taiKhoanPanel;
        this.mainFrame = this.taiKhoanPanel.getMainFrame();
        this.taiKhoanBUS = taiKhoanPanel.getTaiKhoanBUS();
        this.nhanVienBUS = NhanVienBUS.getInstance();
        this.nhomQuyenBUS = NhomQuyenBUS.getInstance();
        this.type = type;
        this.attributes = attributes;
        inputForm = new InputForm(attributes);
        this.label = new JLabel("<html><strong><font size=+2>" + function + "</font></strong><html>");
        this.init();
    }

    public void init(){
        this.setLayout(new MigLayout("wrap 1, insets 30 0 20 0", "[grow]"));
        this.getContentPane().setBackground(Color.decode("#FFFFFF"));

        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("insets 20 0 20 0, align center"));
        panel.add(label);  
        label.setForeground(Color.decode("#FFFFFF"));
        panel.putClientProperty(FlatClientProperties.STYLE, String.format("background: %s", baseTheme.mainColor)); 

        int originalSize = 250;
        int rowsize = 70;
        this.setSize(new Dimension(300, originalSize + rowsize * attributes.length));
        this.add(panel, "grow");

        //Cài đặt lựa chọn cmbobox
        listItemNV = nhanVienBUS.getAllTenNVNotHaveAccount().toArray(new String[0]);
        inputForm.getListItem().get(2).setListCombobox(listItemNV);

        String[] listItemRole = nhomQuyenBUS.getAllTenNhomQuyen().toArray(new String[0]);
        inputForm.getListItem().get(3).setListCombobox(listItemRole);

        this.add(inputForm, "grow");
        setLocationRelativeTo(mainFrame);

        setButton();
    }

    public void setButton(){
        if(type.equals("add")){
            CustomButton btnThem = new CustomButton("Thêm");
            btnThem.setActionCommand("add");
            btnThem.addActionListener(this);
            CustomButton btnHuy = new CustomButton("Hủy");
            btnHuy.setActionCommand("exit");
            btnHuy.addActionListener(this);
            JPanel panel = new JPanel();
            panel.setLayout(new MigLayout("wrap 2"));
            panel.setBackground(Color.decode("#FFFFFF"));
            panel.add(btnHuy);
            panel.add(btnThem);
            this.add(new JPanel(), "push y");
            this.add(panel, "right, gap right 10");
        }
        else if(type.equals("update")){// tài khoản thì không có sửa

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")){
            if(listItemNV.length == 0){
                JOptionPane.showMessageDialog(mainFrame ,"Mọi nhân viên đã được cấp tài khoản, vui lòng thêm nhân viên!");//?
            }
            else if(validation()){
                insert();
            }
        }
        else if(e.getActionCommand().equals("exit")){
            this.dispose();
        }
    }

    public void insert(){
        String username = inputForm.getListItem().get(0).getText();
        String password = inputForm.getListItem().get(1).getText();
        String tenNhanVien = inputForm.getListItem().get(2).getSelection();
        String tenNhomQuyen = inputForm.getListItem().get(3).getSelection();

        int maNV = nhanVienBUS.getMaNVByTen(tenNhanVien);
        int maRole = nhomQuyenBUS.getMaNhomQuyenByTen(tenNhomQuyen);

        TaiKhoanDTO taiKhoanDTO = new TaiKhoanDTO(username, password, maRole);

        if(taiKhoanBUS.insert(taiKhoanDTO) != 0){
            JOptionPane.showMessageDialog(mainFrame, "Thêm tài khoản thành công!");
            if(nhanVienBUS.setMaTK(maNV, taiKhoanDTO.getMaTK()) != 0){
                String[] row = {taiKhoanDTO.getMaTK()+"", taiKhoanDTO.getUsername(), taiKhoanDTO.getPassword()};
                taiKhoanPanel.getTable().addDataRow(row);
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(mainFrame, "Lỗi gắn tài khoản cho nhân viên!");
                this.dispose();
            }
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Thêm tài khoản thất bại!");
            this.dispose();
        }
        
    }

    public boolean validation(){
        String tenTK = inputForm.getListItem().get(0).getText();
        String password = inputForm.getListItem().get(1).getText();

        if(Validate.isEmpty(tenTK)){
            JOptionPane.showMessageDialog(mainFrame, "Tên tài khoản không được để trống!");
            return(false);
        }
        if(!Validate.lengthGreaterThan(tenTK, 5)){
            JOptionPane.showMessageDialog(mainFrame, "Tên tài khoản phải có độ dài trên 5 ký tự!");
            return(false);
        }
        if(Validate.isEmpty(password)){
            JOptionPane.showMessageDialog(mainFrame, "Mật khẩu không được để trống!");
            return(false);
        }
        if(!Validate.lengthGreaterThan(password, 5)){   // test sau
            JOptionPane.showMessageDialog(mainFrame, "Mật khẩu phải có độ dài trên 5 ký tự!");
            return(false);
        }
        return(true);
    }
}
