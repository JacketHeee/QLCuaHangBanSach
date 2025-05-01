package GUI.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.NhaXBBUS;
import DTO.NhaXBDTO;
import DTO.NhaXBDTO;
import GUI.MainFrame;
import GUI.component.CustomButton;
import GUI.component.InputForm;
import GUI.forms.NXBForm;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import resources.base.baseTheme;
import utils.Validate;

public class NhaXBDialog extends JDialog implements ActionListener{
    private NXBForm nhaXBPanel;
    private MainFrame mainFrame;
    private NhaXBBUS nhaXBBUS; 
    private JLabel label;
    private String type;
    private String[][] attributes;
    private InputForm inputForm;
    private int rowSelected;
    private CustomButton btnDC;

    
    public NhaXBDialog(NXBForm nhaXBPanel, String title, String function, String type, String[][] attributes, int... row){
        super(nhaXBPanel.getMainFrame(), title, true);
        this.nhaXBPanel = nhaXBPanel;
        this.mainFrame = this.nhaXBPanel.getMainFrame();
        this.nhaXBBUS = this.nhaXBPanel.getNhaXBBUS();
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
        else if(type.equals("update")){// nhà xuất bản thì không có sửa
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
        String ten = nhaXBPanel.getTable().getCellData(rowSelected, 1);
        String diaChi = nhaXBPanel.getTable().getCellData(rowSelected, 2);
        String soDT = nhaXBPanel.getTable().getCellData(rowSelected, 3);
        String email = nhaXBPanel.getTable().getCellData(rowSelected, 4);

        inputForm.getListItem().get(0).setText(ten);
        inputForm.getListItem().get(1).setTextDC(diaChi);
        inputForm.getListItem().get(2).setText(soDT);
        inputForm.getListItem().get(3).setText(email);
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

        NhaXBDTO ncc = new NhaXBDTO(ten, diaChi, soDT, email);
        if(nhaXBBUS.insert(ncc) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Thêm thành công");
            String[] row = {ncc.getMaNXB() + "", ncc.getTenNXB(), ncc.getDiaChi(), ncc.getSoDT(), ncc.getEmail()};
            nhaXBPanel.getTable().addDataRow(row);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Thêm nhà xuất bản thất bại!");
            this.dispose();
        }
    }

    public void update(){
        int ma = Integer.parseInt(nhaXBPanel.getTable().getCellData(rowSelected, 0));
        String ten = inputForm.getListItem().get(0).getText();
        String diaChi = inputForm.getListItem().get(1).getTextDC();
        String soDT = inputForm.getListItem().get(2).getText();
        String email = inputForm.getListItem().get(3).getText();

        NhaXBDTO ncc = new NhaXBDTO(ma, ten, diaChi, soDT, email);
        if(nhaXBBUS.update(ncc) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Sửa thành công");
            String[] row = {ncc.getMaNXB() + "", ncc.getTenNXB(), ncc.getDiaChi(), ncc.getSoDT(), ncc.getEmail()};
            nhaXBPanel.getTable().setRowData(rowSelected, row);
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

        JTextField input = inputForm.getListItem().get(0).getTextField();
        if(Validate.isEmpty(input.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Tên vùng không được để trống!","Thiếu thông tin",JOptionPane.WARNING_MESSAGE);
            input.requestFocusInWindow();
            return(false);
        }

        if(Validate.isEmpty(ten.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Tên nhà xuất bản không được để trống!","",JOptionPane.WARNING_MESSAGE);
            ten.requestFocusInWindow();
            return(false);
        }
        if(!Validate.lengthGreaterThan(ten.getText(), 5)){
            JOptionPane.showMessageDialog(mainFrame, "Tên nhà xuất bản phải có độ dài trên 5 ký tự!","",JOptionPane.WARNING_MESSAGE);
            ten.requestFocusInWindow();
            return(false);
        }
        if(Validate.isEmpty(diaChi)){
            JOptionPane.showMessageDialog(mainFrame, "Địa chỉ không được để trống!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        if(Validate.isEmpty(soDT.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Số điện thoại nhà xuất bản không được để trống!","",JOptionPane.WARNING_MESSAGE);
            soDT.requestFocusInWindow();
            return(false);
        }
        if(!Validate.isPhoneNumber(soDT.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Số điện thoại nhà xuất bản phải nhập đúng định dạng!","",JOptionPane.WARNING_MESSAGE);
            soDT.requestFocusInWindow();
            return(false);
        }
        if(Validate.isEmpty(email.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Email nhà xuất bản không được để trống!","",JOptionPane.WARNING_MESSAGE);
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
