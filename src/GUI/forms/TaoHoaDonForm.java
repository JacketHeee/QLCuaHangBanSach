package GUI.forms;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import com.formdev.flatlaf.FlatClientProperties;

import GUI.component.ButtonAction;
import GUI.component.CustomButton;
import GUI.component.InvoiceTable;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Font;
import java.awt.Color;

public class TaoHoaDonForm extends JPanel implements ActionListener{
    private String[] listNcc; 
    private CustomButton buttonSave;
    private CustomButton buttonCancel;
    private InvoiceTable table;

    public TaoHoaDonForm() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("insets 0,wrap 1, gap 10"));

        add(new JLabel("<html><b><font size='+2'>TẠO HÓA ĐƠN MỚI</font></b><html>"),"gaptop 20, al center,pushx");
        add(getThongtin(),"pushx, growx");
        add(getChiTietPhieuNhap(),"pushx, growx");
        add(panelKhuyenMai(),"pushx, growx");
        add(panelPTTT(),"pushx, growx");
        add(panelThongTinThanhToan(),"pushx, growx");
        add(getPanelAction(),"pushx, growx");
        
    }

    private JPanel getThongtin() {
        JPanel panel = getPanel("Thông tin chung");
        panel.setLayout(new MigLayout("","[][]","[][]"));
        
        panel.add(new JLabel("<html><font size='+1'><b>Nhân viên: </b>NV001</font></html>"),"pushx,growx");
        panel.add(new JLabel("<html><font size='+1'><b>Mã hóa đơn: </b> 109820938</font></html>"),"pushx,growx,wrap");
        panel.add(getPanelNhaCungCap(),"pushx,growx");
        panel.add(new JLabel("<html><font size='+1'><b>Ngày bán: </b> 20/12/2025</font></html>"),"pushx,growx");
        return panel; 
    }

    
    private JPanel getPanelNhaCungCap() {
        listNcc = new String[] {"Nguyen Hung Manh","Nguyen Ngoc Thien An","Tong Quoc Phung","Danh Thi Ngoc Chau","Truong Thi Huyen Trang"};
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        panel.add(new JLabel("<html><font size='+1'><b>Khách hàng: </b></font></html>"));
        JComboBox combo = new JComboBox(listNcc);
        combo.setFont(new Font(combo.getFont().getName(),Font.PLAIN,16));
        combo.putClientProperty(FlatClientProperties.STYLE, "focusWidth: 0; innerFocusWidth: 0");
        panel.add(combo);
        return panel;
    }

    String[][]actions = {
        {"remove.svg","remove"}
    };

    private JPanel getChiTietPhieuNhap() {
        JPanel panel = getPanel("Chi tiết hóa đơn");
        panel.setLayout(new MigLayout());
        table = new InvoiceTable(null, actions, "Mã sách","Tên sách","Số lượng","Giá bán(đ)","Thành tiền(đ)");
        table.addDataRow(new String[] {"1","Dang cap Nguyen Hung Manh","1","100.000","100.000"});
        table.addDataRow(new String[] {"1","Dang cap Nguyen Hung Manh","1","100.000","100.000"});
        table.addDataRow(new String[] {"1","Dang cap Nguyen Hung Manh","1","100.000","100.000"});
        table.addDataRow(new String[] {"1","Dang cap Nguyen Hung Manh","1","100.000","100.000"});
        panel.add(table,"push,grow,wrap");
        panel.add(panelActionOnTable(),"pushx,growx,wrap");
        panel.add(getTongTien(),"pushx,growx");
        return panel;
    }

    private ButtonAction butAddData;
    private ButtonAction butImportData;
    private JPanel panelActionOnTable() {
        JPanel panel = new JPanel(new MigLayout("al left"));
        butAddData = new ButtonAction("Thêm", "add.svg", "add");
        butAddData.setActionCommand("addRowData");
        butAddData.addActionListener(this);
        panel.add(butAddData);

        butImportData = new ButtonAction("Import Excel", "importExcel.svg", "exportExcel");
        butImportData.setActionCommand("importRowData");
        butImportData.addActionListener(this);
        panel.add(butImportData);
        return panel;
    }

    private JPanel getTongTien() {
        JPanel panel = new JPanel(new MigLayout("al right"));
        panel.add(new JLabel(String.format("<html><font size='+1'><b>Tổng tiền: </b> %sđ</font><html>", "100.000")));

        return panel;
    }

    private String[] km = {"Hóa đơn 50k cho..."}; 
    private JPanel panelKhuyenMai() {
        JPanel panel = getPanel("Khuyến mãi");
        panel.setLayout(new MigLayout("al center center "));
        panel.add(new JLabel("<html><font size='+1'><b>Chọn khuyến mãi</b></font></html>"));
        panel.add(new JLabel(),"pushx");
        JComboBox combo = new JComboBox<>(km);
        panel.add(combo);
        return panel;
    }

    private String[] pttt = {"Tiền mặt","Chuyển khoản","Thẻ Visa","Shop Layter"};
    private JPanel panelPTTT() {
        JPanel panel = getPanel("Phương thức thanh toán");
        panel.setLayout(new MigLayout("al  center center"));
        panel.add(new JLabel("<html><font size='+1'><b>Chọn phương thức thanh toán</b></font></html>"));
        panel.add(new JLabel(),"pushx");
        JComboBox combo = new JComboBox<>(pttt);
        panel.add(combo);
        return panel;
    }

    private JPanel panelThongTinThanhToan() {
        JPanel panel = new JPanel(new MigLayout("al right"));
        panel.add(new JLabel("Tổng tiền hàng(đ):"),"sg 1");
        panel.add(new JLabel("100.000.00đ"),"sg 2,al right,wrap");
        panel.add(new JLabel("Khuyến mãi(đ):"),"sg 1");
        panel.add(new JLabel("0đ"),"sg 2,al right,wrap");
        panel.add(new JLabel("<html><font size='+1'><b>Tổng thanh toán(đ): </b></font></html>"),"sg 1");
        panel.add(new JLabel("<html><font size='+1'><b>100.000.00đ</b></font></html>"),"sg 2,al right,wrap");
        panel.add(new JLabel("Tiền khách đưa(đ):"),"sg 1");
        panel.add(new JTextField("100.000.00đ"),"sg 2,grow,wrap");
        panel.add(new JLabel("Tiền trả lại khách(đ):"),"sg 1");
        panel.add(new JLabel("100.000.00đ"),"sg 2,al right");
        return panel;
    }

    

    
    private JPanel getPanelAction() {
        JPanel panel = new JPanel(new MigLayout("gap 10,al right"));
        buttonSave = new CustomButton("Lưu");
        buttonSave.setBackground(Color.decode("#4882FF"));
        buttonSave.setBackground(Color.white);    
        buttonCancel = new CustomButton("Hủy");
        // buttonCancel.cssCancelButton();
        panel.add(buttonSave,"sg 1");
        panel.add(buttonCancel,"sg 1");
        return panel;
    }

    private JPanel getPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;   
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String ms = e.getActionCommand();

        switch (ms) {
            case "addRowData":
                table.addDataRow(null);
                break;
        
            default:
                break;
        }

        // TODO Auto-generated method stub
        
    }

    
}
