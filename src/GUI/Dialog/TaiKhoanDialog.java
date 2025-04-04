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
import BUS.TaiKhoanBUS;
import DTO.TaiKhoanDTO;
import GUI.Components.InputForm;
import GUI.Components.InputFormItem;
import GUI.Panel.TaiKhoanPnl;
import utils.Validate;
import net.miginfocom.swing.MigLayout;

public class TaiKhoanDialog extends JDialog implements ActionListener{
    TaiKhoanPnl taiKhoanPnl;
    JLabel content; // hộp thoại thêm/ sửa/ xem
    String type; // "thêm"/ "sửa"/ "xem"
    InputForm inputForm;
    JTable table;
    DefaultTableModel model;
    JButton btnThem; // button trong phần dialog á
    JButton btnSua; //
    JButton btnHuy; //
    // Khóa ngoại làm theo combobox
    TaiKhoanBUS taiKhoanBUS;
    NhomQuyenBUS nhomQuyenBUS;
    
    //listAttribute để tạo inputForm
    public TaiKhoanDialog(TaiKhoanPnl taiKhoanPnl, String content, String type, String... listAttribute){
        super(taiKhoanPnl.getMainFrame(), content, true);
        this.taiKhoanPnl = taiKhoanPnl;
        this.content = new JLabel(content);
        this.type = type;
        this.inputForm = new InputForm(listAttribute);
        this.table = taiKhoanPnl.getTable();
        this.model = taiKhoanPnl.getModel();
        this.btnHuy = new JButton("Hủy");
        this.btnThem = new JButton("Thêm");
        this.btnSua = new JButton("Sửa");
        this.taiKhoanBUS = taiKhoanPnl.getTaiKhoanBUS();
        this.nhomQuyenBUS = taiKhoanPnl.getNhomQuyenBUS();
        this.initComponent();
    }

    public void initComponent(){
        this.setLayout(new MigLayout("wrap 1", "[grow]"));
        this.getContentPane().setBackground(Color.decode("#FFFFFF"));

        content.setFont(new Font("Segoe UI", Font.BOLD, 26));
        this.add(content, "center");

        //Thêm InputForm
        this.add(inputForm, "grow");
        //Thêm item combobox
        String[] array1 = nhomQuyenBUS.getAllTenNhomQuyen().toArray(new String[0]);
        inputForm.addItem(new InputFormItem("Nhóm quyền", "combobox", array1));

        if(this.type.equals("thêm")){
            this.add(btnThem);
        }
        else if(this.type.equals("sửa")){
            //Lấy thông tin chọn
            int row = table.getSelectedRow();
            String username = (String)model.getValueAt(row, 1);
            String password = (String)model.getValueAt(row, 2);
            int maNhomQuyen = (int)model.getValueAt(row, 3);
            //set các thông tin cũ
            this.inputForm.getListItem().get(0).setContent(username);
            this.inputForm.getListItem().get(1).setContent(password + "");
            this.inputForm.getListItem().get(2).setSelection(nhomQuyenBUS.getTenByMaNhomQuyen(maNhomQuyen));

            this.add(btnSua);
        }
        // else if(this.type.equals("xem")){
            
        // }
        this.add(btnHuy);

        this.setListener();

        this.setSize(300, 700);
        this.setLocationRelativeTo(taiKhoanPnl.getMainFrame());
        this.setVisible(true);
    }

    public void setListener(){
        this.btnThem.addActionListener(this);
        this.btnSua.addActionListener(this);
        this.btnHuy.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnThem){
            insert();
        }
        else if(e.getSource() == btnHuy){
            this.dispose();
        }
        else if(e.getSource() == btnSua){
            update();
        }
    }

    public void insert(){
        //Lấy thông tin
        String username = inputForm.getListItem().get(0).getContent();
        String password = inputForm.getListItem().get(1).getContent();
        //Lấy lựa chọn combobox
        String tenNhomQuyen = (String)inputForm.getListItem().get(2).getSelection();

    
        if(!Validation(username, password)){
            return;
        }
    
        //Chuyển đổi sau validate
        int maNhomQuyen = nhomQuyenBUS.getMaNhomQuyenByTen(tenNhomQuyen);
        TaiKhoanDTO taiKhoan = new TaiKhoanDTO(username, password, maNhomQuyen);
        if(taiKhoanBUS.insert(taiKhoan) != 0){
            JOptionPane.showMessageDialog(null,"Thêm tài khoản thành công !");
            model.addRow(new Object[]{taiKhoan.getMaTK(), taiKhoan.getUsername(), taiKhoan.getPassword(), taiKhoan.getMaRole()});
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Thêm tài khoản thất bại !");
        }
    }

    public void update(){
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa tài khoản này ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.NO_OPTION){
            return;
        }
        //Lấy thông tin
        String username = inputForm.getListItem().get(0).getContent();
        String password = inputForm.getListItem().get(1).getContent();
        //Lấy lựa chọn combobox
        String tenNhomQuyen = (String)inputForm.getListItem().get(2).getSelection();

    
        if(!Validation(username, password)){
            return;
        }
    
        //Chuyển đổi sau validate
        int maNhomQuyen = nhomQuyenBUS.getMaNhomQuyenByTen(tenNhomQuyen);       
        //Lấy mã tài khoản để sửa
        int row = table.getSelectedRow();
        int maTaiKhoan = (int)model.getValueAt(row, 0);

        TaiKhoanDTO taiKhoan = new TaiKhoanDTO(maTaiKhoan, username, password, maNhomQuyen);
        if(taiKhoanBUS.update(taiKhoan) != 0){
            JOptionPane.showMessageDialog(null, "Sửa tài khoản thành công !");
            model.setValueAt(username, row, 1);
            model.setValueAt(password, row, 2);
            model.setValueAt(maNhomQuyen, row, 3);
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Sửa tài khoản thất bại !");
        }
    }

    public boolean Validation(String username, String password){
        if(Validate.isEmpty(username)){
            JOptionPane.showMessageDialog(null, "username không được để trống !");
            return(false);
        }
        else if(Validate.isEmpty(password)){
            JOptionPane.showMessageDialog(null, "Password không được để trống !");
            return(false);
        }
        return(true);
    }

}
