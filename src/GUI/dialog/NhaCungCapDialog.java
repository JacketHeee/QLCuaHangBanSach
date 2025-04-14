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
        String ten = nhaCungCapPanel.getTable().getCellData(rowSelected, 1);
        String diaChi = nhaCungCapPanel.getTable().getCellData(rowSelected, 2);
        String soDT = nhaCungCapPanel.getTable().getCellData(rowSelected, 3);
        String email = nhaCungCapPanel.getTable().getCellData(rowSelected, 4);

        inputForm.getListItem().get(0).setText(ten);
        inputForm.getListItem().get(1).setText(diaChi);
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
        String diaChi = inputForm.getListItem().get(1).getText();
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
        String diaChi = inputForm.getListItem().get(1).getText();
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
        String ten = inputForm.getListItem().get(0).getText();
        String diaChi = inputForm.getListItem().get(1).getText();
        String soDT = inputForm.getListItem().get(2).getText();
        String email = inputForm.getListItem().get(3).getText();

        if(Validate.isEmpty(ten)){
            JOptionPane.showMessageDialog(mainFrame, "Tên nhà cung cấp không được để trống!");
            return(false);
        }
        if(!Validate.lengthGreaterThan(ten, 5)){
            JOptionPane.showMessageDialog(mainFrame, "Tên nhà cung cấp phải có độ dài trên 5 ký tự!");
            return(false);
        }
        if(Validate.isEmpty(diaChi)){
            JOptionPane.showMessageDialog(mainFrame, "Địa chỉ không được để trống!");
            return(false);
        }
        if(Validate.isEmpty(soDT)){
            JOptionPane.showMessageDialog(mainFrame, "Số điện thoại nhà cung cấp không được để trống!");
            return(false);
        }
        if(!Validate.isPhoneNumber(soDT)){
            JOptionPane.showMessageDialog(mainFrame, "Số điện thoại nhà cung cấp phải nhập đúng định dạng!");
            return(false);
        }
        if(Validate.isEmpty(email)){
            JOptionPane.showMessageDialog(mainFrame, "Email nhà cung cấp không được để trống!");
            return(false);
        }
        if(!Validate.isEmail(email)){
            JOptionPane.showMessageDialog(mainFrame, "Email nhà cung cấp phải nhập đúng định dạng!");
            return(false);
        }
        return(true);
    }
}
