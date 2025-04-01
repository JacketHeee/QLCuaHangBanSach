package GUI.Dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JDialog;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import BUS.TaiKhoanBUS;
import BUS.PhieuNhapBUS;

import BUS.NhaCungCapBUS;
import DTO.PhieuNhapDTO;
import GUI.Components.InputForm;
import GUI.Components.InputFormItem;
import GUI.Panel.PhieuNhapPnl;
import Utils.Validate;
import net.miginfocom.swing.MigLayout;

public class PhieuNhapDialog extends JDialog implements ActionListener{
    PhieuNhapPnl phieuNhapPnl;
    JLabel content; // hộp thoại thêm/ sửa/ xem
    String type; // "thêm"/ "sửa"/ "xem"
    InputForm inputForm;
    JTable table;
    DefaultTableModel model;
    JButton btnThem; // button trong phần dialog á
    JButton btnSua; //
    JButton btnHuy; //
    // Khóa ngoại làm theo combobox
    private PhieuNhapBUS phieuNhapBUS;
    private NhaCungCapBUS nhaCungCapBUS;
	private TaiKhoanBUS taiKhoanBUS;
    
    
    //listAttribute để tạo inputForm
    public PhieuNhapDialog(PhieuNhapPnl phieuNhapPnl, String content, String type,  String... listAttribute){
        super(phieuNhapPnl.getMainFrame(), content, true);
        this.phieuNhapPnl = phieuNhapPnl;
        this.content = new JLabel(content);
        this.type = type;
        this.inputForm = new InputForm(listAttribute);
        this.table = phieuNhapPnl.getTable();
        this.model = phieuNhapPnl.getModel();
        this.btnHuy = new JButton("Hủy");
        this.btnThem = new JButton("Thêm");
        this.btnSua = new JButton("Sửa");
        this.phieuNhapBUS = phieuNhapPnl.getPhieuNhapBUS();
        this.nhaCungCapBUS = phieuNhapPnl.getNhaCungCapBUS();
        this.taiKhoanBUS = phieuNhapPnl.getTaiKhoanBUS();

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
        String[] array1 = nhaCungCapBUS.getAllTenNhaCungCap().toArray(new String[0]);
        inputForm.addItem(new InputFormItem("Nhà cung cấp", "combobox", array1));
        String[] array2 = taiKhoanBUS.getAllTenTaiKhoan().toArray(new String[0]);
        inputForm.addItem(new InputFormItem("Tài khoản", "combobox", array2));


        if(this.type.equals("thêm")){
            this.add(btnThem);
        }
        // else if(this.type.equals("xem")){
            
        // }
        this.add(btnHuy);

        this.setListener();

        this.setSize(300, 700);
        this.setLocationRelativeTo(phieuNhapPnl.getMainFrame());
        this.setVisible(true);
    }

    public void setListener(){
        this.btnThem.addActionListener(this);
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
    }

    public void insert(){
        //Lấy thông tin
        String ngayNhapS = inputForm.getListItem().get(0).getContent();
        String tongTienS = inputForm.getListItem().get(1).getContent();
        //Lấy lựa chọn combobox
        String tenNCC = (String)inputForm.getListItem().get(2).getSelection();
        String tenTK = (String)inputForm.getListItem().get(3).getSelection();
    
        if(!Validation(ngayNhapS, tongTienS)){
            return;
        }
    
        //Chuyển đổi sau validate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ngayBan = LocalDateTime.parse(ngayNhapS, formatter);
        BigDecimal tongTien = BigDecimal.valueOf(Double.parseDouble(tongTienS));

        int maNCC = nhaCungCapBUS.getMaNhaCungCapByTen(tenNCC);
        int maTK = taiKhoanBUS.getMaTaiKhoanByTen(tenTK);


        PhieuNhapDTO PhieuNhap = new PhieuNhapDTO(ngayBan, tongTien, maNCC, maTK);
        if(phieuNhapBUS.insert(PhieuNhap) != 0){
            JOptionPane.showMessageDialog(null,"Tạo phiếu nhập thành công !");
            model.addRow(new Object[]{PhieuNhap.getMaNhap(), PhieuNhap.getNgayNhap(), PhieuNhap.getTongTien(), PhieuNhap.getMaNCC(), PhieuNhap.getMaTK()});
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Thêm phiếu nhập thất bại !");
        }
    }

    public boolean Validation(String ngayBan, String tongTien){ 
        // cài đặt sau bằng inputformdate
        if(Validate.isEmpty(ngayBan)){
            JOptionPane.showMessageDialog(null, "Ngày nhập không để trống !");
            return(false);
        }
        else if(Validate.isEmpty(tongTien)){
            JOptionPane.showMessageDialog(null, "Giá tiền không được để trống !");
            return(false);
        }
        else if(!Validate.isPositiveNumber(tongTien)){
            JOptionPane.showMessageDialog(null, "Giá tiền phải là số dương !");
            return(false);
        }
        return(true);
    }

}
