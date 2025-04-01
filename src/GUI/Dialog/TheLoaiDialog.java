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

import BUS.TheLoaiBUS;
import DTO.TheLoaiDTO;
import GUI.Components.InputForm;
import GUI.Panel.TheLoaiPnl;
import Utils.Validate;
import net.miginfocom.swing.MigLayout;

public class TheLoaiDialog extends JDialog implements ActionListener {
    TheLoaiPnl theLoaiPnl;
    JLabel content;
    String type;
    InputForm inputForm;
    JTable table;
    DefaultTableModel model;
    JButton btnThem;
    JButton btnSua;
    JButton btnHuy;
    TheLoaiBUS theLoaiBUS;
    
    public TheLoaiDialog(TheLoaiPnl theLoaiPnl, String content, String type, String... listAttribute) {
        super(theLoaiPnl.getMainFrame(), content, true);
        this.theLoaiPnl = theLoaiPnl;
        this.content = new JLabel(content);
        this.type = type;
        this.inputForm = new InputForm(listAttribute);
        this.table = theLoaiPnl.getTable();
        this.model = theLoaiPnl.getModel();
        this.btnHuy = new JButton("Hủy");
        this.btnThem = new JButton("Thêm");
        this.btnSua = new JButton("Sửa");
        this.theLoaiBUS = theLoaiPnl.getTheLoaiBUS();

        this.initComponent();
    }

    public void initComponent() {
        this.setLayout(new MigLayout("wrap 1", "[grow]"));
        this.getContentPane().setBackground(Color.decode("#FFFFFF"));

        content.setFont(new Font("Segoe UI", Font.BOLD, 26));
        this.add(content, "center");

        this.add(inputForm, "grow");

        if (this.type.equals("thêm")) {
            this.add(btnThem);
        } else if (this.type.equals("sửa")) {
            int row = table.getSelectedRow();
            String tenTheLoai = (String) model.getValueAt(row, 1);
            this.inputForm.getListItem().get(0).setContent(tenTheLoai);
            this.add(btnSua);
        }
        this.add(btnHuy);

        this.setListener();

        this.setSize(300, 700);
        this.setLocationRelativeTo(theLoaiPnl.getMainFrame());
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
        String tenTheLoai = inputForm.getListItem().get(0).getContent();
        if (!Validation(tenTheLoai)) {
            return;
        }
        TheLoaiDTO theLoai = new TheLoaiDTO(tenTheLoai);
        if (theLoaiBUS.insert(theLoai) != 0) {
            JOptionPane.showMessageDialog(null, "Thêm thể loại thành công!");
            model.addRow(new Object[]{theLoai.getMaTheLoai(), theLoai.getTenTheLoai()});
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Thêm thể loại thất bại!");
        }
    }

    public void update() {
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa thể loại này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.NO_OPTION) {
            return;
        }
        String tenTheLoai = inputForm.getListItem().get(0).getContent();
        if (!Validation(tenTheLoai)) {
            return;
        }
        int row = table.getSelectedRow();
        int maTheLoai = (int) model.getValueAt(row, 0);
        TheLoaiDTO theLoai = new TheLoaiDTO(maTheLoai, tenTheLoai);
        if (theLoaiBUS.update(theLoai) != 0) {
            JOptionPane.showMessageDialog(null, "Sửa thể loại thành công!");
            model.setValueAt(tenTheLoai, row, 1);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Sửa thể loại thất bại!");
        }
    }

    public boolean Validation(String ten) {
        if (Validate.isEmpty(ten)) {
            JOptionPane.showMessageDialog(null, "Tên thể loại không được để trống!");
            return false;
        }
        return true;
    }
}
