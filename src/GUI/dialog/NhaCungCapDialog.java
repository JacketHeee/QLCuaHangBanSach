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

import BUS.NhaCungCapBUS;
import DTO.NhaCungCapDTO;
import GUI.MainFrame;
import GUI.component.CustomButton;
import GUI.component.InputForm;
import GUI.forms.NhaCungCapForm;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import resources.base.baseTheme;
import utils.Validate;

public class NhaCungCapDialog extends JDialog implements ActionListener{
    private NhaCungCapForm nhaCungCapPanel;
    private MainFrame mainFrame;
    private NhaCungCapBUS nhaCungCapBUS; 
    private JLabel label;
    private String type;
    private String[][] attributes;
    private InputForm inputForm;
    private int rowSelected;
    private CustomButton btnDC;

    
    public NhaCungCapDialog(NhaCungCapForm nhaCungCapPanel, String title, String function, String type, String[][] attributes, int... row){
        super(nhaCungCapPanel.getMainFrame(), title, true);
        this.nhaCungCapPanel = nhaCungCapPanel;
        this.mainFrame = this.nhaCungCapPanel.getMainFrame();
        this.nhaCungCapBUS = this.nhaCungCapPanel.getNhaCungCapBUS();
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

        this.add(inputForm, "grow");

        //set listener dc
        btnDC = inputForm.getListItem().get(1).getButtonDC();
        setListenerBtnDC();

        setLocationRelativeTo(mainFrame);

        setButton();
    }

    public void setListenerBtnDC(){
        btnDC.addActionListener(e -> {
            AddressAdderDialog dialog = new AddressAdderDialog(mainFrame);
            dialog.setOnCloseCallback(addresses -> {
                System.out.println("Địa chỉ đã lưu: " + addresses);
                String diaChi = String.join("",addresses);
                inputForm.getListItem().get(1).setTextDC(diaChi);
            });
            dialog.setVisible(true);
        });
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

        int ma = Integer.parseInt(nhaCungCapPanel.getTable().getCellData(rowSelected, 0));
        NhaCungCapDTO ncc = nhaCungCapBUS.getNCCById(ma);

        inputForm.getListItem().get(0).setText(ncc.getTenNCC());
        inputForm.getListItem().get(1).setTextDC(ncc.getDiaChi());
        inputForm.getListItem().get(2).setText(ncc.getSoDT());
        inputForm.getListItem().get(3).setText(ncc.getEmail());
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
        String diaChi = inputForm.getListItem().get(1).getTextDC();
        String soDT = inputForm.getListItem().get(2).getText();
        String email = inputForm.getListItem().get(3).getText();

        NhaCungCapDTO ncc = new NhaCungCapDTO(ten, diaChi, soDT, email);
        if(nhaCungCapBUS.insert(ncc) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Thêm thành công");
            String[] row = {ncc.getMaNCC() + "", ncc.getTenNCC(), ncc.getDiaChi(), ncc.getSoDT(), ncc.getEmail()};
            nhaCungCapPanel.getTable().addDataRow(row);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Thêm nhà cung cấp thất bại!");
            this.dispose();
        }
    }

    public void update(){
        int ma = Integer.parseInt(nhaCungCapPanel.getTable().getCellData(rowSelected, 0));
        String ten = inputForm.getListItem().get(0).getText();
        String diaChi = inputForm.getListItem().get(1).getTextDC();
        String soDT = inputForm.getListItem().get(2).getText();
        String email = inputForm.getListItem().get(3).getText();
        NhaCungCapDTO ncc = new NhaCungCapDTO(ma, ten, diaChi, soDT, email);
        if(nhaCungCapBUS.update(ncc) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Sửa thành công");
            String[] row = {ncc.getMaNCC() + "", ncc.getTenNCC(), ncc.getDiaChi(), ncc.getSoDT(), ncc.getEmail()};
            nhaCungCapPanel.getTable().setRowData(rowSelected, row);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Sửa thất bại!");
            this.dispose();
        }
    }

    public boolean validation(){
        JTextField ten = inputForm.getListItem().get(0).getTextField();
        String diaChi = inputForm.getListItem().get(1).getTextDC();
        JTextField soDT = inputForm.getListItem().get(2).getTextField();
        JTextField email = inputForm.getListItem().get(3).getTextField();

        if(Validate.isEmpty(ten.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Tên nhà cung cấp không được để trống!","",JOptionPane.WARNING_MESSAGE);
            ten.requestFocusInWindow();
            return(false);
        }
        if(!Validate.lengthGreaterThan(ten.getText(), 5)){
            JOptionPane.showMessageDialog(mainFrame, "Tên nhà cung cấp phải có độ dài trên 5 ký tự!","",JOptionPane.WARNING_MESSAGE);
            ten.requestFocusInWindow();
            return(false);
        }
        if(Validate.isEmpty(diaChi)){
            JOptionPane.showMessageDialog(mainFrame, "Địa chỉ không được để trống!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        if(Validate.isEmpty(soDT.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Số điện thoại nhà cung cấp không được để trống!","",JOptionPane.WARNING_MESSAGE);
            soDT.requestFocusInWindow();
            return(false);
        }
        if(!Validate.isPhoneNumber(soDT.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Số điện thoại nhà cung cấp phải nhập đúng định dạng!","",JOptionPane.WARNING_MESSAGE);
            soDT.requestFocusInWindow();
            return(false);
        }
        if(Validate.isEmpty(email.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Email nhà cung cấp không được để trống!","",JOptionPane.WARNING_MESSAGE);
            email.requestFocusInWindow();
            return(false);
        }
        if(!Validate.isEmail(email.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Email hợp lệ có dạng: ten@domain.com, chỉ chứa chữ, số, dấu chấm hoặc gạch dưới!","",JOptionPane.WARNING_MESSAGE);
            email.requestFocusInWindow();
            return(false);
        }
        return(true);
    }
}
