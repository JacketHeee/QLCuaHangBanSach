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

import BUS.PhuongThucTTBUS;
import DTO.PhuongThucTTDTO;
import GUI.Components.InputForm;
import GUI.Panel.PhuongThucTTPnl;
import utils.Validate;
import net.miginfocom.swing.MigLayout;

public class PhuongThucTTDialog extends JDialog implements ActionListener {
    PhuongThucTTPnl phuongThucTTPnl;
    JLabel content;
    String type;
    InputForm inputForm;
    JTable table;
    DefaultTableModel model;
    JButton btnThem;
    JButton btnSua;
    JButton btnHuy;
    PhuongThucTTBUS phuongThucTTBUS;
    
    public PhuongThucTTDialog(PhuongThucTTPnl phuongThucTTPnl, String content, String type, String... listAttribute) {
        super(phuongThucTTPnl.getMainFrame(), content, true);
        this.phuongThucTTPnl = phuongThucTTPnl;
        this.content = new JLabel(content);
        this.type = type;
        this.inputForm = new InputForm(listAttribute);
        this.table = phuongThucTTPnl.getTable();
        this.model = phuongThucTTPnl.getModel();
        this.btnHuy = new JButton("Hủy");
        this.btnThem = new JButton("Thêm");
        this.btnSua = new JButton("Sửa");
        this.phuongThucTTBUS = phuongThucTTPnl.getPhuongThucTTBUS();

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
            String tenPTTT = (String) model.getValueAt(row, 1);
            this.inputForm.getListItem().get(0).setContent(tenPTTT);
            this.add(btnSua);
        }
        this.add(btnHuy);

        this.setListener();

        this.setSize(300, 700);
        this.setLocationRelativeTo(phuongThucTTPnl.getMainFrame());
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
        String tenPTTT = inputForm.getListItem().get(0).getContent();
        if (!Validation(tenPTTT)) {
            return;
        }
        PhuongThucTTDTO phuongThucTT = new PhuongThucTTDTO(tenPTTT);
        if (phuongThucTTBUS.insert(phuongThucTT) != 0) {
            JOptionPane.showMessageDialog(null, "Thêm phương thức thanh toán thành công!");
            model.addRow(new Object[]{phuongThucTT.getMaPT(), phuongThucTT.getTenPTTT()});
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Thêm phương thức thanh toán thất bại!");
        }
    }

    public void update() {
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa phương thức thanh toán này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.NO_OPTION) {
            return;
        }
        String tenPTTT = inputForm.getListItem().get(0).getContent();
        if (!Validation(tenPTTT)) {
            return;
        }
        int row = table.getSelectedRow();
        int maPT = (int) model.getValueAt(row, 0);
        PhuongThucTTDTO phuongThucTT = new PhuongThucTTDTO(maPT, tenPTTT);
        if (phuongThucTTBUS.update(phuongThucTT) != 0) {
            JOptionPane.showMessageDialog(null, "Sửa phương thức thanh toán thành công!");
            model.setValueAt(tenPTTT, row, 1);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Sửa phương thức thanh toán thất bại!");
        }
    }

    public boolean Validation(String ten) {
        if (Validate.isEmpty(ten)) {
            JOptionPane.showMessageDialog(null, "Tên phương thức thanh toán không được để trống!");
            return false;
        }
        return true;
    }
}
