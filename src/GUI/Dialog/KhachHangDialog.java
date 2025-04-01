package GUI.Dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import GUI.Panel.KhachHangPnl;
import BUS.KhachHangBUS;
import DTO.KhachHangDTO;
import GUI.Components.InputForm;
import GUI.Components.InputFormItem;
import Utils.Validate;
import net.miginfocom.swing.MigLayout;

public class KhachHangDialog extends JDialog implements ActionListener {
    KhachHangPnl khachHangPnl;
    JLabel content;
    String type;
    InputForm inputForm;
    JTable table;
    DefaultTableModel model;
    JButton btnThem;
    JButton btnSua;
    JButton btnHuy;
    KhachHangBUS khachHangBUS;
    
    public KhachHangDialog(KhachHangPnl khachHangPnl, String content, String type, String... listAttribute) {
        super(khachHangPnl.getMainFrame(), content, true);
        this.khachHangPnl = khachHangPnl;
        this.content = new JLabel(content);
        this.type = type;
        this.inputForm = new InputForm(listAttribute);
        this.table = khachHangPnl.getTable();
        this.model = khachHangPnl.getModel();
        this.btnHuy = new JButton("Hủy");
        this.btnThem = new JButton("Thêm");
        this.btnSua = new JButton("Sửa");
        this.khachHangBUS = khachHangPnl.getKhachHangBUS();

        this.initComponent();
    }

    public void initComponent() {
        this.setLayout(new MigLayout("wrap 1", "[grow]"));
        this.getContentPane().setBackground(Color.decode("#FFFFFF"));

        content.setFont(new Font("Segoe UI", Font.BOLD, 26));
        this.add(content, "center");
        
        String[] array1 = {"Nam","Nữ"};
        inputForm.addItem(new InputFormItem("Giới tính","combobox",array1)); 

        this.add(inputForm, "grow");

        if (this.type.equals("thêm")) {
            this.add(btnThem);
        } else if (this.type.equals("sửa")) {
            int row = table.getSelectedRow();
            String tenKH = (String) model.getValueAt(row, 1);
            String soDT = (String) model.getValueAt(row, 2);
            String gioiTinh = (String) model.getValueAt(row, 3);
            
            this.inputForm.getListItem().get(0).setContent(tenKH);
            this.inputForm.getListItem().get(1).setContent(soDT);
            this.inputForm.getListItem().get(2).setSelection(gioiTinh);
            
            this.add(btnSua);
        }
        this.add(btnHuy);

        this.setListener();

        this.setSize(300, 700);
        this.setLocationRelativeTo(khachHangPnl.getMainFrame());
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
        String tenKH = inputForm.getListItem().get(0).getContent();
        String soDT = inputForm.getListItem().get(1).getContent();
        String gioiTinh = inputForm.getListItem().get(2).getSelection();
        
        if (!Validation(tenKH, soDT)) {
            return;
        }
        KhachHangDTO khachHang = new KhachHangDTO(tenKH, soDT, gioiTinh);
        if (khachHangBUS.insert(khachHang) != 0) {
            JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công!");
            model.addRow(new Object[]{khachHang.getMaKH(), khachHang.getTenKH(), khachHang.getSoDT(), khachHang.getGioiTinh()});
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Thêm khách hàng thất bại!");
        }
    }

    public void update() {
        String tenKH = inputForm.getListItem().get(0).getContent();
        String soDT = inputForm.getListItem().get(1).getContent();
        String gioiTinh = inputForm.getListItem().get(2).getSelection();
        
        if (!Validation(tenKH, soDT)) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.NO_OPTION) {
            return;
        }
        int row = table.getSelectedRow();
        int maKH = (int) model.getValueAt(row, 0);
        KhachHangDTO khachHang = new KhachHangDTO(maKH, tenKH, soDT, gioiTinh);
        if (khachHangBUS.update(khachHang) != 0) {
            JOptionPane.showMessageDialog(null, "Sửa khách hàng thành công!");
            model.setValueAt(tenKH, row, 1);
            model.setValueAt(soDT, row, 2);
            model.setValueAt(gioiTinh, row, 3);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Sửa khách hàng thất bại!");
        }
    }

    public boolean Validation(String ten, String soDT) {
        if (Validate.isEmpty(ten)) {
            JOptionPane.showMessageDialog(null, "Tên khách hàng không được để trống !");
            return false;
        }
        else if (Validate.isEmpty(soDT)) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không được để trống !");
            return false;
        }
        else if (!Validate.isPhoneNumber(soDT)) {
            JOptionPane.showMessageDialog(null, "Số điện thoại phải nhập đúng định dạng !");
            return false;
        }
        return true;
    }
}
