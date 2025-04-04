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

import BUS.TacGiaBUS;
import DTO.TacGiaDTO;
import GUI.Components.InputForm;
import GUI.Panel.TacGiaPnl;
import utils.Validate;
import net.miginfocom.swing.MigLayout;

public class TacGiaDialog extends JDialog implements ActionListener {
    TacGiaPnl tacGiaPnl;
    JLabel content;
    String type;
    InputForm inputForm;
    JTable table;
    DefaultTableModel model;
    JButton btnThem;
    JButton btnSua;
    JButton btnHuy;
    TacGiaBUS tacGiaBUS;
    
    public TacGiaDialog(TacGiaPnl tacGiaPnl, String content, String type, String... listAttribute) {
        super(tacGiaPnl.getMainFrame(), content, true);
        this.tacGiaPnl = tacGiaPnl;
        this.content = new JLabel(content);
        this.type = type;
        this.inputForm = new InputForm(listAttribute);
        this.table = tacGiaPnl.getTable();
        this.model = tacGiaPnl.getModel();
        this.btnHuy = new JButton("Hủy");
        this.btnThem = new JButton("Thêm");
        this.btnSua = new JButton("Sửa");
        this.tacGiaBUS = tacGiaPnl.getTacGiaBUS();

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
            String tenTacGia = (String) model.getValueAt(row, 1);
            this.inputForm.getListItem().get(0).setContent(tenTacGia);
            this.add(btnSua);
        }
        this.add(btnHuy);

        this.setListener();

        this.setSize(300, 700);
        this.setLocationRelativeTo(tacGiaPnl.getMainFrame());
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
        String tenTacGia = inputForm.getListItem().get(0).getContent();
        if (!Validation(tenTacGia)) {
            return;
        }
        TacGiaDTO tacGia = new TacGiaDTO(tenTacGia);
        if (tacGiaBUS.insert(tacGia) != 0) {
            JOptionPane.showMessageDialog(null, "Thêm tác giả thành công!");
            model.addRow(new Object[]{tacGia.getMaTacGia(), tacGia.getTenTacGia()});
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Thêm tác giả thất bại!");
        }
    }

    public void update() {
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa tác giả này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.NO_OPTION) {
            return;
        }
        String tenTacGia = inputForm.getListItem().get(0).getContent();
        if (!Validation(tenTacGia)) {
            return;
        }
        int row = table.getSelectedRow();
        int maTacGia = (int) model.getValueAt(row, 0);
        TacGiaDTO tacGia = new TacGiaDTO(maTacGia, tenTacGia);
        if (tacGiaBUS.update(tacGia) != 0) {
            JOptionPane.showMessageDialog(null, "Sửa tác giả thành công!");
            model.setValueAt(tenTacGia, row, 1);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Sửa tác giả thất bại!");
        }
    }

    public boolean Validation(String ten) {
        if (Validate.isEmpty(ten)) {
            JOptionPane.showMessageDialog(null, "Tên tác giả không được để trống!");
            return false;
        }
        return true;
    }
}
