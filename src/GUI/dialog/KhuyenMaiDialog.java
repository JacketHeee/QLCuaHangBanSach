package GUI.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.KhuyenMaiBUS;
import DTO.KhuyenMaiDTO;
import GUI.MainFrame;
import GUI.component.CustomButton;
import GUI.component.InputForm;
import GUI.forms.KhuyenMaiForm;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import resources.base.baseTheme;
import utils.DateCalculator;
import utils.Validate;

public class KhuyenMaiDialog extends JDialog implements ActionListener{
    private KhuyenMaiForm khuyenMaiPanel;
    private MainFrame mainFrame;
    private JLabel label;
    private String type;
    private String[][] attributes;
    private InputForm inputForm;
    private KhuyenMaiBUS khuyenMaiBUS;
    private int rowSelected;

    
    public KhuyenMaiDialog(KhuyenMaiForm khuyenMaiPanel, String title, String function, String type, String[][] attributes, int... row){
        super(khuyenMaiPanel.getMainFrame(), title, true);
        this.khuyenMaiPanel = khuyenMaiPanel;
        this.mainFrame = this.khuyenMaiPanel.getMainFrame();
        this.type = type;
        this.attributes = attributes;
        inputForm = new InputForm(attributes);
        this.label = new JLabel("<html><strong><font size=+2>" + function + "</font></strong><html>");
        this.khuyenMaiBUS = khuyenMaiPanel.getKhuyenMaiBUS();
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
        int ma = Integer.parseInt(khuyenMaiPanel.getTable().getCellData(rowSelected, 0));
        KhuyenMaiDTO khuyenMai = khuyenMaiBUS.getInstanceByMa(ma);

        inputForm.getListItem().get(0).setText(khuyenMai.getTenKM());
        inputForm.getListItem().get(1).setText(khuyenMai.getDieuKienGiam());
        inputForm.getListItem().get(2).setText(khuyenMai.getGiaTriGiam() + "");
        inputForm.getListItem().get(3).setDateTime(khuyenMai.getNgayBatDau());
        inputForm.getListItem().get(4).setDateTime(khuyenMai.getNgayKetThuc());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")){
            if(validation()){
                insert();
            }
        }
        if (e.getActionCommand().equals("update")){
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
        String dKGiam = inputForm.getListItem().get(1).getText();
        String giaTriGiamS = inputForm.getListItem().get(2).getText();
        LocalDateTime ngayBatDau = inputForm.getListItem().get(3).getDateTime();
        LocalDateTime ngayKetThuc = inputForm.getListItem().get(4).getDateTime();

        //Chuyển kiểu dữ liệu
        BigDecimal giaTriGiam = BigDecimal.valueOf(Double.parseDouble(giaTriGiamS));

        KhuyenMaiDTO kM = new KhuyenMaiDTO(ten, dKGiam, giaTriGiam, ngayBatDau, ngayKetThuc);
        if(khuyenMaiBUS.insert(kM) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Thêm thành công");
            String[] row = {kM.getMaKM() + "", 
            kM.getTenKM(), 
            kM.getDieuKienGiam(), 
            kM.getGiaTriGiam() + "", 
            new DateCalculator().formatDateTimeToDateS(kM.getNgayBatDau()), 
            new DateCalculator().formatDateTimeToDateS(kM.getNgayKetThuc())};
            khuyenMaiPanel.getTable().addDataRow(row);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Thêm thất bại!");
            this.dispose();
        }
    }

    public void update(){
        int ma = Integer.parseInt(khuyenMaiPanel.getTable().getCellData(rowSelected, 0));
        String ten = inputForm.getListItem().get(0).getText();
        String dKGiam = inputForm.getListItem().get(1).getText();
        String giaTriGiamS = inputForm.getListItem().get(2).getText();
        LocalDateTime ngayBatDau = inputForm.getListItem().get(3).getDateTime();
        LocalDateTime ngayKetThuc = inputForm.getListItem().get(4).getDateTime();

        //Chuyển kiểu dữ liệu
        BigDecimal giaTriGiam = BigDecimal.valueOf(Double.parseDouble(giaTriGiamS));
        KhuyenMaiDTO kM = new KhuyenMaiDTO(ma, ten, dKGiam, giaTriGiam, ngayBatDau, ngayKetThuc);
        if(khuyenMaiBUS.update(kM) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Sửa thành công!");
            String[] row = {kM.getMaKM() + "",
            kM.getTenKM(), 
            kM.getDieuKienGiam(), 
            kM.getGiaTriGiam() + "", 
            new DateCalculator().formatDateTimeToDateS(kM.getNgayBatDau()), 
            new DateCalculator().formatDateTimeToDateS(kM.getNgayKetThuc())};
            khuyenMaiPanel.getTable().setRowData(rowSelected, row);
            this.dispose();
        }
        else{this.dispose();
            JOptionPane.showMessageDialog(mainFrame, "Sửa thất bại!");
            this.dispose();
        }
    }


    public boolean validation(){
        JTextField ten = inputForm.getListItem().get(0).getTextField();
        JTextField dieuKienGiam = inputForm.getListItem().get(1).getTextField();
        JTextField giaTriGiam = inputForm.getListItem().get(2).getTextField();
        String ngayBatDau = inputForm.getListItem().get(3).getDateTimeString();
        String ngayKetThuc = inputForm.getListItem().get(4).getDateTimeString();
        String thoiGianBatDau = inputForm.getListItem().get(3).getDateTimeTimeString(); //Validate thời gian
        String thoiGianKetThuc = inputForm.getListItem().get(4).getDateTimeTimeString();

        if(Validate.isEmpty(ten.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Tên khuyến mãi không được để trống!","",JOptionPane.WARNING_MESSAGE);
            ten.requestFocusInWindow();
            return(false);
        }
        if(Validate.isEmpty(dieuKienGiam.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Điều kiện giảm không được để trống!","",JOptionPane.WARNING_MESSAGE);
            dieuKienGiam.requestFocusInWindow();
            return(false);
        }
        if(Validate.isEmpty(giaTriGiam.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Giá trị giảm không được để trống!","",JOptionPane.WARNING_MESSAGE);
            giaTriGiam.requestFocusInWindow();
            return(false);
        }
        if(Validate.isEmpty(ngayBatDau)){
            JOptionPane.showMessageDialog(mainFrame, "Ngày bắt đầu không được để trống!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        if(!Validate.isDate(ngayBatDau)){
            JOptionPane.showMessageDialog(mainFrame, "Ngày bắt đầu phải nhập đúng định dạng!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        if(Validate.isEmpty(ngayKetThuc)){
            JOptionPane.showMessageDialog(mainFrame, "Ngày kết thúc không được để trống!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        if(!Validate.isDate(ngayKetThuc)){
            JOptionPane.showMessageDialog(mainFrame, "Ngày kết thúc phải nhập đúng định dạng!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        if(ngayBatDau.equals(ngayKetThuc)){
            if(!Validate.isStartTimeAndEndTime(thoiGianBatDau, thoiGianKetThuc)){
                JOptionPane.showMessageDialog(mainFrame, "Thời gian bắt đầu phải trước thời gian kết thúc","",JOptionPane.WARNING_MESSAGE);
                return(false);
            }
            return(true);
        }
        if(!Validate.isStartDateAndEndDate(ngayBatDau, ngayKetThuc)){
            JOptionPane.showMessageDialog(mainFrame, "Ngày bắt đầu phải trước ngày kết thúc!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        return(true);
    }
}
