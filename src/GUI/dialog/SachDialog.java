package GUI.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.NhaXBBUS;
import BUS.SachBUS;
import BUS.ViTriVungBUS;
import DTO.SachDTO;
import GUI.MainFrame;
import GUI.component.CustomButton;
import GUI.component.InputForm;
import GUI.forms.SachForm;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import resources.base.baseTheme;
import utils.Validate;

public class SachDialog extends JDialog implements ActionListener{
    private SachForm sachPanel;
    private MainFrame mainFrame;
    private SachBUS sachBUS; 
    private JLabel label;
    private String type;
    private String[][] attributes;
    private InputForm inputForm;
    //Lấy khóa ngoại
    private ViTriVungBUS viTriVungBUS;
    private NhaXBBUS nhaXBBUS;
    private int rowSelected;

    
    public SachDialog(SachForm sachPanel, String title, String function, String type, String[][] attributes, int... row){
        super(sachPanel.getMainFrame(), title, true);
        this.sachPanel = sachPanel;
        this.mainFrame = this.sachPanel.getMainFrame();
        this.sachBUS = this.sachPanel.getSachBUS();
        this.type = type;
        this.attributes = attributes;
        inputForm = new InputForm(attributes);
        this.label = new JLabel("<html><strong><font size=+2>" + function + "</font></strong><html>");
        viTriVungBUS = ViTriVungBUS.getInstance();
        nhaXBBUS = NhaXBBUS.getInstance();
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
        String[] listVung = viTriVungBUS.getAllTenVung().toArray(new String[0]);
        String[] listNXB = nhaXBBUS.getAllTenNXB().toArray(new String[0]);
        inputForm.getListItem().get(3).setListCombobox(listVung);
        inputForm.getListItem().get(4).setListCombobox(listNXB);

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
        int ma = Integer.parseInt(sachPanel.getTable().getCellData(rowSelected, 0));
        String ten = sachPanel.getTable().getCellData(rowSelected, 1);
        String giaBan = sachPanel.getTable().getCellData(rowSelected, 3); 
        String namXB = sachPanel.getTable().getCellData(rowSelected, 4);

        String tenVung = viTriVungBUS.getTenVungByMaSach(ma);
        String tenNXB = nhaXBBUS.getTenNXBByMaSach(ma);

        inputForm.getListItem().get(0).setText(ten);
        inputForm.getListItem().get(1).setText(giaBan);
        inputForm.getListItem().get(2).setText(namXB);
        inputForm.getListItem().get(3).setSelection(tenVung);
        inputForm.getListItem().get(4).setSelection(tenNXB);
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
        BigDecimal giaBan = BigDecimal.valueOf(Double.parseDouble(inputForm.getListItem().get(1).getText()));
        int namXB = Integer.parseInt(inputForm.getListItem().get(2).getText());
        String tenVung = inputForm.getListItem().get(3).getSelection();
        String tenNhaXB = inputForm.getListItem().get(4).getSelection();

        int maVung = viTriVungBUS.getMaViTriVungByTen(tenVung);
        int maNXB = nhaXBBUS.getMaNXBByTen(tenNhaXB);

        SachDTO sach = new SachDTO(ten, giaBan, namXB, maVung, maNXB);
        if(sachBUS.insert(sach) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Thêm thành công");
            String[] row = {sach.getMaSach()+"", sach.getTenSach(), sach.getSoLuong() + "", sach.getGiaBan() + "", tenVung + ""};
            sachPanel.getTable().addDataRow(row);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Thêm sách thất bại!");
            this.dispose();
        }
    }

    public void update(){
        int ma = Integer.parseInt(sachPanel.getTable().getCellData(rowSelected, 0));
        String ten = inputForm.getListItem().get(0).getText();
        BigDecimal giaBan = BigDecimal.valueOf(Double.parseDouble(inputForm.getListItem().get(1).getText()));
        int namXB = Integer.parseInt(inputForm.getListItem().get(2).getText());
        String tenVung = inputForm.getListItem().get(3).getSelection();
        String tenNhaXB = inputForm.getListItem().get(4).getSelection();

        int maVung = viTriVungBUS.getMaViTriVungByTen(tenVung);
        int maNXB = nhaXBBUS.getMaNXBByTen(tenNhaXB);

        SachDTO sach = new SachDTO(ma, ten, giaBan, namXB, maVung, maNXB);
        if(sachBUS.update(sach) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Sửa thành công");
            String[] row = {sach.getMaSach()+"", sach.getTenSach(), sach.getSoLuong() + "", sach.getGiaBan() + "", sach.getNamXB() + ""};
            sachPanel.getTable().setRowData(rowSelected, row);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Sửa thất bại!");
            this.dispose();
        }

    }

    public boolean validation(){
        String ten = inputForm.getListItem().get(0).getText();
        String giaBan = inputForm.getListItem().get(1).getText();
        String namXB = inputForm.getListItem().get(2).getText();
        if(Validate.isEmpty(ten)){
            JOptionPane.showMessageDialog(mainFrame, "Tên sách không được để trống!");
            return(false);
        }
        if(Validate.isEmpty(giaBan)){
            JOptionPane.showMessageDialog(mainFrame, "Giá bán không được để trống!");
            return(false);
        }
        if(Validate.isEmpty(namXB)){
            JOptionPane.showMessageDialog(mainFrame, "Năm xuất bản không được để trống!");
            return(false);
        }
        if(!Validate.isYear(namXB)){
            JOptionPane.showMessageDialog(mainFrame, "Năm xuất bản phải nhập đúng định dạng!");
            return(false);
        }
        return(true);
    }
}
