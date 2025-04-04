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

import BUS.NhaCungCapBUS;
import DTO.NhaCungCapDTO;
import GUI.Components.InputForm;
import GUI.Panel.NhaCungCapPnl;
import utils.Validate;
import net.miginfocom.swing.MigLayout;

public class NhaCungCapDialog extends JDialog implements ActionListener {
    NhaCungCapPnl nhaCungCapPnl;
    JLabel content;
    String type;
    InputForm inputForm;
    JTable table;
    DefaultTableModel model;
    JButton btnThem;
    JButton btnSua;
    JButton btnHuy;
    NhaCungCapBUS nhaCungCapBUS;
    
    public NhaCungCapDialog(NhaCungCapPnl nhaCungCapPnl, String content, String type, String... listAttribute) {
        super(nhaCungCapPnl.getMainFrame(), content, true);
        this.nhaCungCapPnl = nhaCungCapPnl;
        this.content = new JLabel(content);
        this.type = type;
        this.inputForm = new InputForm(listAttribute);
        this.table = nhaCungCapPnl.getTable();
        this.model = nhaCungCapPnl.getModel();
        this.btnHuy = new JButton("Hủy");
        this.btnThem = new JButton("Thêm");
        this.btnSua = new JButton("Sửa");
        this.nhaCungCapBUS = nhaCungCapPnl.getNhaCungCapBUS();

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
            String tenNCC = (String) model.getValueAt(row, 1);
            String diaChi = (String) model.getValueAt(row, 2);
            String soDT = (String) model.getValueAt(row, 3);
            String email = (String) model.getValueAt(row, 4);
            
            this.inputForm.getListItem().get(0).setContent(tenNCC);
            this.inputForm.getListItem().get(1).setContent(diaChi);
            this.inputForm.getListItem().get(2).setContent(soDT);
            this.inputForm.getListItem().get(3).setContent(email);
            
            this.add(btnSua);
        }
        this.add(btnHuy);

        this.setListener();

        this.setSize(400, 600);
        this.setLocationRelativeTo(nhaCungCapPnl.getMainFrame());
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
        String tenNCC = inputForm.getListItem().get(0).getContent();
        String diaChi = inputForm.getListItem().get(1).getContent();
        String soDT = inputForm.getListItem().get(2).getContent();
        String email = inputForm.getListItem().get(3).getContent();
        
        if (!Validation(tenNCC, diaChi, soDT, email)) {
            return;
        }
        NhaCungCapDTO nhaCungCap = new NhaCungCapDTO(tenNCC, diaChi, soDT, email);
        if (nhaCungCapBUS.insert(nhaCungCap) != 0) {
            JOptionPane.showMessageDialog(null, "Thêm nhà cung cấp thành công!");
            model.addRow(new Object[]{nhaCungCap.getMaNCC(), nhaCungCap.getTenNCC(), nhaCungCap.getDiaChi(), nhaCungCap.getSoDT(), nhaCungCap.getEmail()});
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Thêm nhà cung cấp thất bại!");
        }
    }

    public void update(){
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa nhà cung cấp này ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.NO_OPTION){
            return;
        }
        //Lấy thông tin

        String tenNCC = inputForm.getListItem().get(0).getContent();
        String diaChi = inputForm.getListItem().get(1).getContent();
        String soDT = inputForm.getListItem().get(2).getContent();
        String email = inputForm.getListItem().get(3).getContent();
        if(!Validation(tenNCC, diaChi, soDT, email)){
            return;
        }

        //Lấy mã nhà cung cấp để sửa
        int row = table.getSelectedRow();
        int maNCC = (int)model.getValueAt(row, 0);

        NhaCungCapDTO nhaCungCap = new NhaCungCapDTO(maNCC, tenNCC, diaChi, soDT, email);

        if(nhaCungCapBUS.update(nhaCungCap) != 0){
            JOptionPane.showMessageDialog(null, "Sửa nhà cung cấp thành công !");
            model.setValueAt(tenNCC, row, 1);
            model.setValueAt(diaChi, row, 2);
            model.setValueAt(soDT, row, 3);
            model.setValueAt(email, row, 4);
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Sửa nhà cung cấp thất bại !");
        }
    }

    public boolean Validation(String ten, String diaChi, String soDT, String email) {
        if (Validate.isEmpty(ten)) {
            JOptionPane.showMessageDialog(null, "Tên nhà cung cấp không được để trống !");
            return false;
        } 
        else if (Validate.isEmpty(diaChi)) {
            JOptionPane.showMessageDialog(null, "Địa chỉ không được để trống !");
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
        else if (Validate.isEmpty(email) || !Validate.isEmail(email)) {
            JOptionPane.showMessageDialog(null, "Email không hợp lệ !");
            return false;
        }
        return true;
    }
}
