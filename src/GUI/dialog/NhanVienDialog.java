package GUI.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
import GUI.MainFrame;
import GUI.component.CustomButton;
import GUI.component.InputForm;
import GUI.forms.NhanVienForm;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import resources.base.baseTheme;
import utils.DateCalculator;
import utils.Validate;

public class NhanVienDialog extends JDialog implements ActionListener{
    private NhanVienForm nhanVienPanel;
    private MainFrame mainFrame;
    private NhanVienBUS nhanVienBUS; 
    private JLabel label;
    private String type;
    private String[][] attributes;
    private InputForm inputForm;
    private int rowSelected;

    
    public NhanVienDialog(NhanVienForm nhanVienPanel, String title, String function, String type, String[][] attributes, int... row){
        super(nhanVienPanel.getMainFrame(), title, true);
        this.nhanVienPanel = nhanVienPanel;
        this.mainFrame = this.nhanVienPanel.getMainFrame();
        this.nhanVienBUS = this.nhanVienPanel.getNhanVienBUS();
        this.type = type;
        this.attributes = attributes;
        inputForm = new InputForm(attributes);
        this.label = new JLabel("<html><strong><font size=+2>" + function + "</font></strong><html>");
        if(row.length == 1){
            this.rowSelected = row[0];
        }
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
        inputForm.getListItem().get(2).setListCombobox("Nam", "Nữ");
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
            this.add(new JLabel(), "push y");
            this.add(panel, "right, gap right 10");
        }
        else if(type.equals("update")){
            CustomButton btnSua = new CustomButton("Sửa");
            btnSua.setActionCommand("update");
            btnSua.addActionListener(this);
            CustomButton btnHuy = new CustomButton("Hủy");
            btnHuy.setActionCommand("exit");
            btnHuy.addActionListener(this);
            JPanel panel = new JPanel();
            panel.setLayout(new MigLayout("wrap 2"));
            panel.setBackground(Color.decode("#FFFFFF"));
            panel.add(btnHuy);
            panel.add(btnSua);
            this.add(new JLabel(), "push y");
            this.add(panel, "right, gap right 10");

            //set dữ liệu cũ
            setOldData();
        }
    }

    public void setOldData(){
        String ma = nhanVienPanel.getTable().getCellData(rowSelected, 0);

        NhanVienDTO nv = nhanVienBUS.getInstanceByMa(Integer.parseInt(ma));



        inputForm.getListItem().get(0).setText(nv.getHoTen());
        inputForm.getListItem().get(1).setDateByString(nv.getNgaySinhAsString());
        inputForm.getListItem().get(2).setSelection(nv.getGioiTinh());
        inputForm.getListItem().get(3).setText(nv.getSoDT());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")){
            if(validation()){
                insert();
            }
        }
        else if (e.getActionCommand().equals("update")){
            if(validation()){
                update();
            }
        }
        else if(e.getActionCommand().equals("exit")){
            this.dispose();
        }
    }

    public void insert(){
        String ten = inputForm.getListItem().get(0).getText();
        java.sql.Date ngaySinh = inputForm.getListItem().get(1).getDate();
        String gioiTinh = inputForm.getListItem().get(2).getSelection();
        String soDT = inputForm.getListItem().get(3).getText();
        NhanVienDTO nhanVien = new NhanVienDTO(ten, ngaySinh, gioiTinh, soDT);
        if(nhanVienBUS.insert(nhanVien) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Thêm thành công");
            String[] row = {
                nhanVien.getMaNV()+"", 
                nhanVien.getHoTen(), 
                new DateCalculator().formatDateToDateS(nhanVien.getNgaySinh()), 
                nhanVien.getGioiTinh(), 
                nhanVien.getSoDT()
            };
            nhanVienPanel.getTable().addDataRow(row);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Thêm nhân viên thất bại!");
            this.dispose();
        }
    }

    public void update(){
        int ma = Integer.parseInt(nhanVienPanel.getTable().getCellData(rowSelected, 0));
        String ten = inputForm.getListItem().get(0).getText();
        java.sql.Date ngaySinh = inputForm.getListItem().get(1).getDate();
        String gioiTinh = inputForm.getListItem().get(2).getSelection();
        String soDT = inputForm.getListItem().get(3).getText();
        NhanVienDTO nhanVien = new NhanVienDTO(ma, ten, ngaySinh, gioiTinh, soDT);
        if(nhanVienBUS.update(nhanVien) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Sửa thành công");
            String[] row = {
                nhanVien.getMaNV()+"", 
                nhanVien.getHoTen(), 
                new DateCalculator().formatDateToDateS(nhanVien.getNgaySinh()), 
                nhanVien.getGioiTinh(), 
                nhanVien.getSoDT()
            };
            nhanVienPanel.getTable().setRowData(rowSelected, row);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Sửa thất bại!");
            this.dispose();
        }
    }

    public boolean validation(){
        JTextField tenNV = inputForm.getListItem().get(0).getTextField();
        String ngaySinh = inputForm.getListItem().get(1).getDateString();
        JTextField soDT = inputForm.getListItem().get(3).getTextField();

        if(Validate.isEmpty(tenNV.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Tên nhân viên không được để trống!","",JOptionPane.WARNING_MESSAGE);
            tenNV.requestFocusInWindow();
            return(false);
        }
        if(!Validate.lengthGreaterThan(tenNV.getText(), 5)){
            JOptionPane.showMessageDialog(mainFrame, "Tên nhân viên phải có độ dài trên 5 ký tự!","",JOptionPane.WARNING_MESSAGE);
            tenNV.requestFocusInWindow();
            return(false);
        }
        if(Validate.isEmpty(ngaySinh)){
            JOptionPane.showMessageDialog(mainFrame, "Ngày sinh không được để trống!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        if(!Validate.isDate(ngaySinh)){
            JOptionPane.showMessageDialog(mainFrame, "Ngày sinh phải nhập đúng định dạng!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        if(Validate.isEmpty(soDT.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Số điện thoại nhân viên không được để trống!","",JOptionPane.WARNING_MESSAGE);
            soDT.requestFocusInWindow();
            return(false);
        }
        if(!Validate.isPhoneNumber(soDT.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Số điện thoại nhân viên phải nhập đúng định dạng!","",JOptionPane.WARNING_MESSAGE);
            soDT.requestFocusInWindow();
            return(false);
        }
        return(true);
    }
}
