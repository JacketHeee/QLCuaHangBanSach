package GUI.Dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.KhuyenMaiBUS;
import DTO.KhuyenMaiDTO;
import GUI.Components.InputForm;
import GUI.Components.InputFormItem;
import GUI.Panel.KhuyenMaiPnl;
import utils.Validate;
import net.miginfocom.swing.MigLayout;

public class KhuyenMaiDialog extends JDialog implements ActionListener {
    KhuyenMaiPnl khuyenMaiPnl;
    JLabel content;
    String type;
    InputForm inputForm;
    JTable table;
    DefaultTableModel model;
    JButton btnThem;
    JButton btnSua;
    JButton btnHuy;
    KhuyenMaiBUS khuyenMaiBUS;
    
    public KhuyenMaiDialog(KhuyenMaiPnl khuyenMaiPnl, String content, String type, String... listAttribute) {
        super(khuyenMaiPnl.getMainFrame(), content, true);
        this.khuyenMaiPnl = khuyenMaiPnl;
        this.content = new JLabel(content);
        this.type = type;
        this.inputForm = new InputForm(listAttribute);
        this.table = khuyenMaiPnl.getTable();
        this.model = khuyenMaiPnl.getModel();
        this.btnHuy = new JButton("Hủy");
        this.btnThem = new JButton("Thêm");
        this.btnSua = new JButton("Sửa");
        this.khuyenMaiBUS = khuyenMaiPnl.getKhuyenMaiBUS();

        this.initComponent();
    }

    public void initComponent() {
        this.setLayout(new MigLayout("wrap 1", "[grow]"));
        this.getContentPane().setBackground(Color.decode("#FFFFFF"));

        content.setFont(new Font("Segoe UI", Font.BOLD, 26));
        this.add(content, "center");

        inputForm.addItem(new InputFormItem("Ngày bắt đầu", "calendar"));
        inputForm.addItem(new InputFormItem("Ngày kết thúc", "calendar"));

        this.add(inputForm, "grow");

        if (this.type.equals("thêm")) {
            this.add(btnThem);
        } else if (this.type.equals("sửa")) {
            int row = table.getSelectedRow();
            String tenKM = (String) model.getValueAt(row, 1);
            String dieuKienGiam = (String) model.getValueAt(row, 2);
            String giaTriGiam = (String) model.getValueAt(row, 3).toString();
            LocalDateTime ngayBatDau = LocalDateTime.parse(model.getValueAt(row, 4).toString());
            LocalDateTime ngayKetThuc = LocalDateTime.parse(model.getValueAt(row, 5).toString());
            
            this.inputForm.getListItem().get(0).setContent(tenKM);
            this.inputForm.getListItem().get(1).setContent(dieuKienGiam);
            this.inputForm.getListItem().get(2).setContent(giaTriGiam);
            this.inputForm.getListItem().get(3).setDateTime(ngayBatDau);
            this.inputForm.getListItem().get(4).setDateTime(ngayKetThuc);
            
            this.add(btnSua);
        }
        this.add(btnHuy);

        this.setListener();

        this.setSize(400, 600);
        this.setLocationRelativeTo(khuyenMaiPnl.getMainFrame());
        this.setVisible(true);
    }

    public void setListener() {
        this.btnThem.addActionListener(this);
        this.btnSua.addActionListener(this);
        this.btnHuy.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnThem) {
            insert();
        } else if (e.getSource() == btnHuy) {
            this.dispose();
        } else if (e.getSource() == btnSua) {
            update();
        }
    }

    public void insert() {
        String tenKM = inputForm.getListItem().get(0).getContent();
        String dieuKienGiam = inputForm.getListItem().get(1).getContent();
        String giaTriGiamS = inputForm.getListItem().get(2).getContent();
        String ngayBatDauS = inputForm.getListItem().get(3).getDateS();
        String ngayKetThucS = inputForm.getListItem().get(4).getDateS();

        if (!Validation(tenKM, dieuKienGiam, giaTriGiamS, ngayBatDauS, ngayKetThucS)) {
            return;
        }
        BigDecimal giaTriGiam = BigDecimal.valueOf(Double.parseDouble(giaTriGiamS));
        LocalDateTime ngayBatDau = inputForm.getListItem().get(3).getDateTime();
        LocalDateTime ngayKetThuc = inputForm.getListItem().get(4).getDateTime();

        KhuyenMaiDTO khuyenMai = new KhuyenMaiDTO(tenKM, dieuKienGiam, giaTriGiam, ngayBatDau, ngayKetThuc);
        if (khuyenMaiBUS.insert(khuyenMai) != 0) {
            JOptionPane.showMessageDialog(null, "Thêm khuyến mãi thành công!");
            model.addRow(new Object[]{khuyenMai.getMaKM(), khuyenMai.getTenKM(), khuyenMai.getDieuKienGiam(), khuyenMai.getGiaTriGiam(), khuyenMai.getNgayBatDau(), khuyenMai.getNgayKetThuc()});
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Thêm khuyến mãi thất bại!");
        }
    }

    public void update() {
        String tenKM = inputForm.getListItem().get(0).getContent();
        String dieuKienGiam = inputForm.getListItem().get(1).getContent();
        String giaTriGiamS = inputForm.getListItem().get(2).getContent();
        String ngayBatDauS = inputForm.getListItem().get(3).getContent();
        String ngayKetThucS = inputForm.getListItem().get(4).getContent();

        
        if (!Validation(tenKM, dieuKienGiam,giaTriGiamS, ngayBatDauS, ngayKetThucS )) {
            return;
        }

        BigDecimal giaTriGiam = BigDecimal.valueOf(Double.parseDouble(giaTriGiamS));
        LocalDateTime ngayBatDau = inputForm.getListItem().get(3).getDateTime();
        LocalDateTime ngayKetThuc = inputForm.getListItem().get(4).getDateTime();

        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa khuyến mãi này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.NO_OPTION) {
            return;
        }
        int row = table.getSelectedRow();
        int maKM = (int) model.getValueAt(row, 0);
        KhuyenMaiDTO khuyenMai = new KhuyenMaiDTO(maKM, tenKM, dieuKienGiam, giaTriGiam, ngayBatDau, ngayKetThuc);
        if (khuyenMaiBUS.update(khuyenMai) != 0) {
            JOptionPane.showMessageDialog(null, "Sửa khuyến mãi thành công!");
            model.setValueAt(tenKM, row, 1);
            model.setValueAt(dieuKienGiam, row, 2);
            model.setValueAt(giaTriGiam, row, 3);
            model.setValueAt(ngayBatDau, row, 4);
            model.setValueAt(ngayKetThuc, row, 5);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Sửa khuyến mãi thất bại!");
        }
    }

    public boolean Validation(String ten, String dieuKienGiam, String giaTriGiam, String ngayBatDau, String ngayKetThuc) { //Chưa validate phần ngày bắt đầu < ngày kết thúc
        if (Validate.isEmpty(ten)) {
            JOptionPane.showMessageDialog(null, "Tên khuyến mãi không được để trống !");
            return false;
        }
        else if(Validate.isEmpty(dieuKienGiam)){ // chỗ này chưa biết validate sao
            JOptionPane.showMessageDialog(null, "Điều kiện giảm không được để trống !");
            return false;
        }
        else if(Validate.isEmpty(giaTriGiam)){
            JOptionPane.showMessageDialog(null, "Giá trị giảm không được để trống !");
            return false;
        }
        else if(!Validate.isPositiveNumber(giaTriGiam)){
            JOptionPane.showMessageDialog(null, "Giá trị giảm phải là số dương !");
            return false;
        }
        else if(Validate.isEmpty(ngayBatDau)){
            JOptionPane.showMessageDialog(null, "Ngày bắt đầu không được để trống !");
            return false;
        }
        else if(!Validate.isDate(ngayBatDau)){
            JOptionPane.showMessageDialog(null, "Ngày bắt đầu phải nhập đúng định dạng !");
            return false;
        }
        else if(Validate.isEmpty(ngayKetThuc)){
            JOptionPane.showMessageDialog(null, "Ngày kết thúc không được để trống !");
            return false;
        }
        else if(!Validate.isDate(ngayKetThuc)){
            JOptionPane.showMessageDialog(null, "Ngày kết thúc phải đúng định dạng !");
            return false;
        }
        return true;
    }
}
