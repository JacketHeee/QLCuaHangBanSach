package GUI.Dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.NhanVienBUS;
import BUS.TaiKhoanBUS;
import DTO.NhanVienDTO;
import GUI.Components.InputForm;
import GUI.Components.InputFormItem;
import GUI.Panel.NhanVienPnl;
import Utils.Validate;
import net.miginfocom.swing.MigLayout;

public class NhanVienDialog extends JDialog implements ActionListener{
    NhanVienPnl nhanVienPnl;
    JLabel content; // hộp thoại thêm/ sửa/ xem
    String type; // "thêm"/ "sửa"/ "xem"
    InputForm inputForm;
    JTable table;
    DefaultTableModel model;
    JButton btnThem; // button trong phần dialog á
    JButton btnSua; //
    JButton btnHuy; //
    // Khóa ngoại làm theo combobox
    NhanVienBUS nhanVienBUS;
    TaiKhoanBUS taiKhoanBUS;
    
    //listAttribute để tạo inputForm
    public NhanVienDialog(NhanVienPnl nhanVienPnl, String content, String type, String... listAttribute){
        super(nhanVienPnl.getMainFrame(), content, true);
        this.nhanVienPnl = nhanVienPnl;
        this.content = new JLabel(content);
        this.type = type;
        this.inputForm = new InputForm(listAttribute);
        this.table = nhanVienPnl.getTable();
        this.model = nhanVienPnl.getModel();
        this.btnHuy = new JButton("Hủy");
        this.btnThem = new JButton("Thêm");
        this.btnSua = new JButton("Sửa");
        this.nhanVienBUS = nhanVienPnl.getNhanVienBUS();
        this.taiKhoanBUS = nhanVienPnl.getTaiKhoanBUS();

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
        String[] array1 = {"Nam","Nữ"};
        inputForm.addItem(new InputFormItem("Giới tính", "combobox", array1));
        inputForm.addItem(new InputFormItem("Số điện thoại", "text"));
        String[] array2 = taiKhoanBUS.getAllTenTaiKhoan().toArray(new String[0]);
        inputForm.addItem(new InputFormItem("Tài khoản", "combobox", array2));

        if(this.type.equals("thêm")){
            this.add(btnThem);
        }
        else if(this.type.equals("sửa")){
            //Lấy thông tin chọn
            int row = table.getSelectedRow();
            String tenNV = (String)model.getValueAt(row, 1);
            Date ngaySinh = (Date)model.getValueAt(row, 2);
            String gioiTinh = (String)model.getValueAt(row, 3);
            String soDT = (String)model.getValueAt(row, 4);
            int maTK = (int)model.getValueAt(row, 5);
            //set các thông tin cũ
            this.inputForm.getListItem().get(0).setContent(tenNV);
            this.inputForm.getListItem().get(1).setContent(ngaySinh + "");
            this.inputForm.getListItem().get(2).setSelection(gioiTinh);
            this.inputForm.getListItem().get(3).setContent(soDT);
            this.inputForm.getListItem().get(4).setSelection(taiKhoanBUS.getTenByMaTaiKhoan(maTK));

            this.add(btnSua);
        }
        // else if(this.type.equals("xem")){
            
        // }
        this.add(btnHuy);

        this.setListener();

        this.setSize(300, 700);
        this.setLocationRelativeTo(nhanVienPnl.getMainFrame());
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
        String tenNV = inputForm.getListItem().get(0).getContent();
        String ngaySinhS = inputForm.getListItem().get(1).getContent();
        String soDT = inputForm.getListItem().get(3).getContent();

        //Lấy lựa chọn combobox
        String gioiTinh = inputForm.getListItem().get(2).getSelection();
        String tenTaiKhoan = inputForm.getListItem().get(4).getSelection();
    
        if(!Validation(tenNV, ngaySinhS, soDT)){
            return;
        }
    
        //Chuyển đổi sau validate
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date ngaySinhUtil = new java.util.Date();
        try {
            ngaySinhUtil = sdf.parse(ngaySinhS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date ngaySinh = new java.sql.Date(ngaySinhUtil.getTime());

        int maTK = taiKhoanBUS.getMaTaiKhoanByTen(tenTaiKhoan);

        NhanVienDTO nhanVien = new NhanVienDTO(tenNV, ngaySinh, gioiTinh, soDT, maTK);
        if(nhanVienBUS.insert(nhanVien) != 0){
            JOptionPane.showMessageDialog(null,"Thêm nhân viên thành công !");
            model.addRow(new Object[]{nhanVien.getMaNV(), nhanVien.getHoTen(), nhanVien.getNgaySinh(), nhanVien.getGioiTinh(), nhanVien.getSoDT(), nhanVien.getMaTK()});
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Thêm nhân viên thất bại !");
        }
    }

    public void update(){
        //Lấy thông tin
        String tenNV = inputForm.getListItem().get(0).getContent();
        String ngaySinhS = inputForm.getListItem().get(1).getContent();
        String soDT = inputForm.getListItem().get(3).getContent();

        //Lấy lựa chọn combobox
        String gioiTinh = inputForm.getListItem().get(2).getSelection();
        String tenTaiKhoan = inputForm.getListItem().get(4).getSelection();
    
        if(!Validation(tenNV, ngaySinhS, soDT)){
            return;
        }
    
        //Chuyển đổi sau validate
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date ngaySinhUtil = new java.util.Date();
        try {
            ngaySinhUtil = sdf.parse(ngaySinhS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date ngaySinh = new java.sql.Date(ngaySinhUtil.getTime());
        int maTK = taiKhoanBUS.getMaTaiKhoanByTen(tenTaiKhoan);
    
        //Lấy mã nhân viên để sửa
        int row = table.getSelectedRow();
        int maNV = (int)model.getValueAt(row, 0);

        NhanVienDTO nhanVien = new NhanVienDTO(maNV, tenNV, ngaySinh, gioiTinh, soDT, maTK);
        if(nhanVienBUS.update(nhanVien) != 0){
            JOptionPane.showMessageDialog(null, "Sửa nhân viên thành công !");
            model.setValueAt(tenNV, row, 1);
            model.setValueAt(ngaySinh, row, 2);
            model.setValueAt(gioiTinh, row, 3);
            model.setValueAt(soDT, row, 4);
            model.setValueAt(maTK, row, 5);
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Sửa nhân viên thất bại !");
        }
    }

    public boolean Validation(String ten, String ngaySinh, String soDT){
        if(Validate.isEmpty(ten)){
            JOptionPane.showMessageDialog(null, "Tên nhân viên không được để trống !");
            return(false);
        }
        else if(Validate.isEmpty(ngaySinh)){
            JOptionPane.showMessageDialog(null, "Ngày sinh không được để trống !");
            return(false);
        }
        else if(!Validate.isDate(ngaySinh)){
            JOptionPane.showMessageDialog(null, "Ngày sinh phải nhập đúng định dạng !");
            return(false);
        }
        else if(Validate.isEmpty(soDT)){
            JOptionPane.showMessageDialog(null, "Số điện thoại không được để trống !");
            return(false);
        }
        else if(!Validate.isPhoneNumber(soDT)){
            JOptionPane.showMessageDialog(null, "Số điện thoại phải nhập đúng định dạng !");
            return(false);
        }
        
        return(true);
    }

}
