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

import BUS.NhomQuyenBUS;
import DTO.NhomQuyenDTO;
import GUI.Components.InputForm;
import GUI.Panel.NhomQuyenPnl;
import Utils.Validate;
import net.miginfocom.swing.MigLayout;

public class NhomQuyenDialog extends JDialog implements ActionListener {
    NhomQuyenPnl nhomQuyenPnl;
    JLabel content;
    String type;
    InputForm inputForm;
    JTable table;
    DefaultTableModel model;
    JButton btnThem;
    JButton btnSua;
    JButton btnHuy;
    NhomQuyenBUS nhomQuyenBUS;
    
    public NhomQuyenDialog(NhomQuyenPnl nhomQuyenPnl, String content, String type, String... listAttribute) {
        super(nhomQuyenPnl.getMainFrame(), content, true);
        this.nhomQuyenPnl = nhomQuyenPnl;
        this.content = new JLabel(content);
        this.type = type;
        this.inputForm = new InputForm(listAttribute);
        this.table = nhomQuyenPnl.getTable();
        this.model = nhomQuyenPnl.getModel();
        this.btnHuy = new JButton("Hủy");
        this.btnThem = new JButton("Thêm");
        this.btnSua = new JButton("Sửa");
        this.nhomQuyenBUS = nhomQuyenPnl.getNhomQuyenBUS();

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
            String tenRole = (String) model.getValueAt(row, 1);
            this.inputForm.getListItem().get(0).setContent(tenRole);
            this.add(btnSua);
        }
        this.add(btnHuy);

        this.setListener();

        this.setSize(300, 700);
        this.setLocationRelativeTo(nhomQuyenPnl.getMainFrame());
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
        String tenRole = inputForm.getListItem().get(0).getContent();
        if (!Validation(tenRole)) {
            return;
        }
        NhomQuyenDTO nhomQuyen = new NhomQuyenDTO(tenRole);
        if (nhomQuyenBUS.insert(nhomQuyen) != 0) {
            JOptionPane.showMessageDialog(null, "Thêm nhóm quyền thành công!");
            model.addRow(new Object[]{nhomQuyen.getMaRole(), nhomQuyen.getTenRole()});
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Thêm nhóm quyền thất bại!");
        }
    }

    public void update() {
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa nhóm quyền này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.NO_OPTION) {
            return;
        }
        String tenRole = inputForm.getListItem().get(0).getContent();
        if (!Validation(tenRole)) {
            return;
        }
        int row = table.getSelectedRow();
        int maRole = (int) model.getValueAt(row, 0);
        NhomQuyenDTO nhomQuyen = new NhomQuyenDTO(maRole, tenRole);
        if (nhomQuyenBUS.update(nhomQuyen) != 0) {
            JOptionPane.showMessageDialog(null, "Sửa nhóm quyền thành công!");
            model.setValueAt(tenRole, row, 1);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Sửa nhóm quyền thất bại!");
        }
    }

    public boolean Validation(String ten) {
        if (Validate.isEmpty(ten)) {
            JOptionPane.showMessageDialog(null, "Tên nhóm quyền không được để trống!");
            return false;
        }
        return true;
    }
}
