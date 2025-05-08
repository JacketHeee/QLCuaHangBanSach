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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import org.apache.commons.math3.analysis.function.Sin;

import BUS.NhanVienBUS;
import BUS.NhomQuyenBUS;
import BUS.TaiKhoanBUS;
import DTO.TaiKhoanDTO;
import GUI.MainFrame;
import GUI.component.CustomButton;
import GUI.component.InputForm;
import GUI.forms.TaiKhoanForm;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
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
    private int rowSelected;

    
    public TaiKhoanDialog(TaiKhoanForm taiKhoanPanel, String title, String function, String type, String[][] attributes, int... row){
        super(taiKhoanPanel.getMainFrame(), title, true);
        this.taiKhoanPanel = taiKhoanPanel;
        this.mainFrame = this.taiKhoanPanel.getMainFrame();
        this.taiKhoanBUS = taiKhoanPanel.getTaiKhoanBUS();
        this.nhanVienBUS = taiKhoanPanel.getNhanVienBUS();
        this.nhomQuyenBUS = taiKhoanPanel.getNhomQuyenBUS();
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
        if(type.equals("add")){
            listItemNV = nhanVienBUS.getAllTenNVNotHaveAccount().toArray(new String[0]);
            inputForm.getListItem().get(2).setListCombobox(listItemNV);
        }
        else if(type.equals("update")){
            ArrayList<String> temp = nhanVienBUS.getAllTenNVNotHaveAccount();
            //set cbx cho nhân viên hiện tại
            int maTK = Integer.parseInt(taiKhoanPanel.getTable().getCellData(rowSelected, 0));
            String tenNV = nhanVienBUS.getTenNVByMaTK(maTK); 
            temp.add(tenNV);
            listItemNV = temp.toArray(new String[0]);
            inputForm.getListItem().get(2).setListCombobox(listItemNV);
        }

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
            this.add(new JLabel(), "push y");
            this.add(panel, "right, gap right 10");
        }
        else if(type.equals("update")){// tài khoản thì không có sửa
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
        int maTK = Integer.parseInt(taiKhoanPanel.getTable().getCellData(rowSelected, 0));
        // String userName = taiKhoanPanel.getTable().getCellData(rowSelected, 1);
        // String password = taiKhoanPanel.getTable().getCellData(rowSelected, 2);
        // String tenRole = taiKhoanPanel.getTable().getCellData(rowSelected, 3);

        TaiKhoanDTO tk = taiKhoanBUS.getTaiKhoanById(maTK);

        String tenNV = nhanVienBUS.getTenNVByMaTK(maTK);

        inputForm.getListItem().get(0).setText(tk.getUsername());
        inputForm.getListItem().get(1).setTextPass(tk.getPassword());
        inputForm.getListItem().get(2).setSelection(tenNV);
        inputForm.getListItem().get(3).setSelection(nhomQuyenBUS.getTenByMaNhomQuyen(tk.getMaRole()));
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
        String username = inputForm.getListItem().get(0).getText();
        String password = inputForm.getListItem().get(1).getTextPass();
        String tenNhanVien = inputForm.getListItem().get(2).getSelection();
        String tenNhomQuyen = inputForm.getListItem().get(3).getSelection();

        int maNV = nhanVienBUS.getMaNVByTen(tenNhanVien);
        int maRole = nhomQuyenBUS.getMaNhomQuyenByTen(tenNhomQuyen);

        TaiKhoanDTO taiKhoanDTO = new TaiKhoanDTO(username, password, maRole);

        if(taiKhoanBUS.insert(taiKhoanDTO) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Thêm thành công");
            if(nhanVienBUS.setMaTK(maNV, taiKhoanDTO.getMaTK()) != 0){
                String tenNQ = nhomQuyenBUS.getTenByMaNhomQuyen(taiKhoanDTO.getMaRole());
                NhanVienBUS nv = new NhanVienBUS();
                String[] row = {taiKhoanDTO.getMaTK()+"", taiKhoanDTO.getUsername(), nv.getTenNVByMaTK(taiKhoanDTO.getMaTK()), tenNQ};
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

    public void update(){
        int ma = Integer.parseInt(taiKhoanPanel.getTable().getCellData(rowSelected, 0));
        String username = inputForm.getListItem().get(0).getText();
        String password = inputForm.getListItem().get(1).getTextPass();
        String tenNhanVien = inputForm.getListItem().get(2).getSelection();
        String tenNhomQuyen = inputForm.getListItem().get(3).getSelection();

        int maRole = nhomQuyenBUS.getMaNhomQuyenByTen(tenNhomQuyen);
        int maNV = nhanVienBUS.getMaNVByTen(tenNhanVien);

        TaiKhoanDTO taiKhoanDTO = new TaiKhoanDTO(ma, username, password, maRole);

        if(taiKhoanBUS.update(taiKhoanDTO) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Sửa thành công");
            if(nhanVienBUS.setMaTK(maNV, taiKhoanDTO.getMaTK()) != 0){
                String tenNQ = nhomQuyenBUS.getTenByMaNhomQuyen(taiKhoanDTO.getMaRole());
                String[] row = {taiKhoanDTO.getMaTK()+"", taiKhoanDTO.getUsername(), taiKhoanDTO.getPassword(), tenNQ};
                taiKhoanPanel.getTable().setRowData(rowSelected, row);
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(mainFrame, "Lỗi gắn tài khoản cho nhân viên!");
                this.dispose();
            }
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Sửa thất bại!");
            this.dispose();
        }

    }

    public boolean validation(){
        // String tenTK = inputForm.getListItem().get(0).getText();
        JPasswordField password = inputForm.getListItem().get(1).getPass(); //Mật khẩu phải ở dạng JPasswordField

        JTextField input = inputForm.getListItem().get(0).getTextField();


        if(Validate.isEmpty(input.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Tên tài khoản không được để trống!","",JOptionPane.WARNING_MESSAGE);
            input.requestFocusInWindow();
            return(false);
        }
        if(!Validate.lengthGreaterThan(input.getText(), 5)){
            JOptionPane.showMessageDialog(mainFrame, "Tên tài khoản phải có độ dài trên 5 ký tự!","", JOptionPane.WARNING_MESSAGE);
            input.requestFocusInWindow();
            return(false);
        }
        if(Validate.isEmpty(new String(password.getPassword()))){
            JOptionPane.showMessageDialog(mainFrame, "Mật khẩu không được để trống!","",JOptionPane.WARNING_MESSAGE);
            password.requestFocusInWindow();
            return(false);
        }
        if(!Validate.lengthGreaterThan(new String(password.getPassword()), 5)){   // test sau
            JOptionPane.showMessageDialog(mainFrame, "Mật khẩu phải có độ dài trên 5 ký tự!","",JOptionPane.WARNING_MESSAGE);
            password.requestFocusInWindow();
            return(false);
        }
        return(true);
    }
}
