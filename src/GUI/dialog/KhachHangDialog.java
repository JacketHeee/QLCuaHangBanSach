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
import raven.toast.Notifications;
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
    private int rowSelected;
    
    public KhachHangDialog(KhachHangForm khachHangPanel, String title, String function, String type, String[][] attributes, int...row){
        super(khachHangPanel.getMainFrame(), title, true);
        this.khachHangPanel = khachHangPanel;
        this.mainFrame = this.khachHangPanel.getMainFrame();
        this.khachHangBUS = this.khachHangPanel.getKhachHangBUS();
        this.type = type;
        this.attributes = attributes;
        this.inputForm = new InputForm(attributes);
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
            this.add(new JPanel(), "push y");
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
            this.add(new JPanel(), "push y");
            this.add(panel, "right, gap right 10");

            //set dữ liệu cũ
            setOldData();

        }
    }

    public void setOldData(){
        int ma = khachHangPanel.getTable().getCellData(rowSelected, 0);
        
        // String ten = khachHangPanel.getTable().getCellData(rowSelected, 1);
        // String soDT = khachHangPanel.getTable().getCellData(rowSelected, 2);
        // String gioiTinh = khachHangPanel.getTable().getCellData(rowSelected, 3);

        inputForm.getListItem().get(0).setText(ten);
        inputForm.getListItem().get(1).setText(soDT);
        inputForm.getListItem().get(2).setSelection(gioiTinh);
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
        String soDT = inputForm.getListItem().get(1).getText();
        String gioiTinh = inputForm.getListItem().get(2).getSelection();
        KhachHangDTO khach = new KhachHangDTO(ten, soDT, gioiTinh);
        if(khachHangBUS.insert(khach) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Thêm thành công");
            String[] row = {khach.getMaKH()+"", ten,soDT,gioiTinh};
            khachHangPanel.getTable().addDataRow(row);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Thêm thất bại!");
            this.dispose();
        }
    }

    public void update(){
        String maS = khachHangPanel.getTable().getCellData(rowSelected, 0);
        String ten = inputForm.getListItem().get(0).getText();
        String soDT = inputForm.getListItem().get(1).getText();
        String gioiTinh = inputForm.getListItem().get(2).getSelection();

        int ma = Integer.parseInt(maS);

        KhachHangDTO khach = new KhachHangDTO(ma, ten, soDT, gioiTinh);

        if(khachHangBUS.update(khach) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Sửa thành công");
            khachHangPanel.getTable().setRowData(
                rowSelected, 
                khach.getMaKH() + "",
                khach.getTenKH(),
                khach.getSoDT(),
                khach.getGioiTinh()    
            );
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Sửa thất bại!");
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
