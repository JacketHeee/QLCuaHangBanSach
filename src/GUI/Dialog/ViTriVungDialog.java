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

import BUS.ViTriVungBUS;
import DTO.ViTriVungDTO;
import GUI.Components.InputForm;
import GUI.Panel.ViTriVungPnl;
import Utils.Validate;
import net.miginfocom.swing.MigLayout;

public class ViTriVungDialog extends JDialog implements ActionListener {
    ViTriVungPnl viTriVungPnl;
    JLabel content;
    String type;
    InputForm inputForm;
    JTable table;
    DefaultTableModel model;
    JButton btnThem;
    JButton btnSua;
    JButton btnHuy;
    ViTriVungBUS viTriVungBUS;
    
    public ViTriVungDialog(ViTriVungPnl viTriVungPnl, String content, String type, String... listAttribute) {
        super(viTriVungPnl.getMainFrame(), content, true);
        this.viTriVungPnl = viTriVungPnl;
        this.content = new JLabel(content);
        this.type = type;
        this.inputForm = new InputForm(listAttribute);
        this.table = viTriVungPnl.getTable();
        this.model = viTriVungPnl.getModel();
        this.btnHuy = new JButton("Hủy");
        this.btnThem = new JButton("Thêm");
        this.btnSua = new JButton("Sửa");
        this.viTriVungBUS = viTriVungPnl.getViTriVungBUS();

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
            String tenVung = (String) model.getValueAt(row, 1);
            this.inputForm.getListItem().get(0).setContent(tenVung);
            this.add(btnSua);
        }
        this.add(btnHuy);

        this.setListener();

        this.setSize(300, 700);
        this.setLocationRelativeTo(viTriVungPnl.getMainFrame());
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
        String tenVung = inputForm.getListItem().get(0).getContent();
        if (!Validation(tenVung)) {
            return;
        }
        ViTriVungDTO viTriVung = new ViTriVungDTO(tenVung);
        if (viTriVungBUS.insert(viTriVung) != 0) {
            JOptionPane.showMessageDialog(null, "Thêm vị trí vùng thành công!");
            model.addRow(new Object[]{viTriVung.getMaVung(), viTriVung.getTenVung()});
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Thêm vị trí vùng thất bại!");
        }
    }

    public void update() {
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa vị trí vùng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.NO_OPTION) {
            return;
        }
        String tenVung = inputForm.getListItem().get(0).getContent();
        if (!Validation(tenVung)) {
            return;
        }
        int row = table.getSelectedRow();
        int maVung = (int) model.getValueAt(row, 0);
        ViTriVungDTO viTriVung = new ViTriVungDTO(maVung, tenVung);
        if (viTriVungBUS.update(viTriVung) != 0) {
            JOptionPane.showMessageDialog(null, "Sửa vị trí vùng thành công!");
            model.setValueAt(tenVung, row, 1);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Sửa vị trí vùng thất bại!");
        }
    }

    public boolean Validation(String ten) {
        if (Validate.isEmpty(ten)) {
            JOptionPane.showMessageDialog(null, "Tên vị trí vùng không được để trống!");
            return false;
        }
        return true;
    }
}
