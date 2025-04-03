package GUI.forms;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class TaoPhieuNhapForm extends JPanel implements ActionListener{
    private String[] listNcc; 
    private CustomButton buttonSave;
    private CustomButton buttonCancel;
    private InvoiceTable table;

    public TaoPhieuNhapForm() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("insets 0,wrap 1, gap 10"));

        add(new JLabel("<html><b><font size='+2'>TẠO PHIẾU NHẬP MỚI</font></b><html>"),"gaptop 20, al center,pushx");
        add(getThongtin(),"pushx, growx");
        add(getChiTietPhieuNhap(),"pushx, growx");
        add(getTongTien(),"pushx, growx");
        add(getPanelAction(),"pushx, growx");
        
    }

    private JPanel getThongtin() {
        JPanel panel = getPanel("Thông tin chung");
        panel.setLayout(new MigLayout("","[][]","[][]"));
        
        panel.add(new JLabel("<html><font size='+1'><b>Nhân viên: </b>NV001</font></html>"),"pushx,growx");
        panel.add(new JLabel("<html><font size='+1'><b>Mã phiếu nhập: </b> 109820938</font></html>"),"pushx,growx,wrap");
        panel.add(getPanelNhaCungCap(),"pushx,growx");
        panel.add(new JLabel("<html><font size='+1'><b>Ngày nhập: </b> 20/12/2025</font></html>"),"pushx,growx");
        return panel; 
    }

    
    private JPanel getPanelNhaCungCap() {
        listNcc = new String[] {"Nguyen Hung Manh","Nguyen Ngoc Thien An","Tong Quoc Phung","Danh Thi Ngoc Chau","Truong Thi Huyen Trang"};
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        panel.add(new JLabel("<html><font size='+1'><b>Nhà cung cấp: </b></font></html>"));
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
        JPanel panel = getPanel("Chi tiết phiếu nhập");
        panel.setLayout(new MigLayout());
        table = new InvoiceTable(null, actions, "Mã sách","Tên sách","Số lượng","Giá nhập","Thành tiền");
        table.addDataRow(new String[] {"1","Dang cap Nguyen Hung Manh","1","100.000","100.000"});
        table.addDataRow(new String[] {"1","Dang cap Nguyen Hung Manh","1","100.000","100.000"});
        table.addDataRow(new String[] {"1","Dang cap Nguyen Hung Manh","1","100.000","100.000"});
        table.addDataRow(new String[] {"1","Dang cap Nguyen Hung Manh","1","100.000","100.000"});
        panel.add(table,"push,grow,wrap");
        panel.add(panelActionOnTable(),"pushx,growx");
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
