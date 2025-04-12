package GUI.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.KhachHangBUS;
import DTO.KhachHangDTO;
import GUI.MainFrame;
import GUI.component.CustomButton;
import GUI.component.InputForm;
import GUI.forms.KhachHangForm;
import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;
import utils.Validate;

public class KhachHangDialog extends JDialog implements ActionListener{
    private KhachHangForm khachHangPanel;
    private MainFrame mainFrame;
    private KhachHangBUS khachHangBUS; 
    private JLabel label;
    private String type;
    private String[][] attributes;
    private InputForm inputForm;
    
    public KhachHangDialog(KhachHangForm khachHangPanel, String title, String function, String type, String[][] attributes){
        super(khachHangPanel.getMainFrame(), title, true);
        this.khachHangPanel = khachHangPanel;
        this.mainFrame = this.khachHangPanel.getMainFrame();
        this.khachHangBUS = this.khachHangPanel.getKhachHangBUS();
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
            this.add(new JPanel(), "push y");
            this.add(panel, "right, gap right 10");
        }
        else if(type.equals("update")){// khách hàng thì không có sửa

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")){
            if(validation()){
                insert();
            }
        }
        else if(e.getActionCommand().equals("exit")){
            this.dispose();
        }
    }

    public void insert(){
        String ten = inputForm.getListItem().get(0).getText();
        String soDT = inputForm.getListItem().get(1).getText();
        String gioiTinh = inputForm.getListItem().get(2).getSelection();
        KhachHangDTO khach = new KhachHangDTO(ten, soDT, gioiTinh);
        if(khachHangBUS.insert(khach) != 0){
            JOptionPane.showMessageDialog(mainFrame, "Thêm khách hàng thành công!");
            String[] row = {khach.getMaKH()+"", ten,soDT,gioiTinh};
            khachHangPanel.getTable().addDataRow(row);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Thêm khách hàng thất bại!");
            this.dispose();
        }
    }

    public boolean validation(){
        String ten = inputForm.getListItem().get(0).getText();
        String soDT = inputForm.getListItem().get(1).getText();
        if(Validate.isEmpty(ten)){
            JOptionPane.showMessageDialog(mainFrame, "Tên khách hàng không được để trống!");
            return(false);
        }
        if(!Validate.lengthGreaterThan(ten, 5)){
            JOptionPane.showMessageDialog(mainFrame, "Tên khách hàng phải có độ dài trên 5 ký tự!");
            return(false);
        }
        if(Validate.isEmpty(soDT)){
            JOptionPane.showMessageDialog(mainFrame, "Số điện thoại khách hàng không được để trống!");
            return(false);
        }
        if(!Validate.isPhoneNumber(soDT)){
            JOptionPane.showMessageDialog(mainFrame, "Số điện thoại khách hàng phải nhập đúng định dạng!");
            return(false);
        }
        return(true);
    }
}
