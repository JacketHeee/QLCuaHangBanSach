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

import BUS.PhuongThucTTBUS;
import BUS.TaiKhoanBUS;
import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.KhuyenMaiBUS;
import DTO.HoaDonDTO;
import GUI.Components.InputForm;
import GUI.Components.InputFormItem;
import GUI.Panel.HoaDonPnl;
import Utils.Validate;
import net.miginfocom.swing.MigLayout;

public class HoaDonDialog extends JDialog implements ActionListener{
    HoaDonPnl hoaDonPnl;
    JLabel content; // hộp thoại thêm/ sửa/ xem
    String type; // "thêm"/ "sửa"/ "xem"
    InputForm inputForm;
    JTable table;
    DefaultTableModel model;
    JButton btnThem; // button trong phần dialog á
    JButton btnSua; //
    JButton btnHuy; //
    // Khóa ngoại làm theo combobox
    private HoaDonBUS hoaDonBUS;
	private TaiKhoanBUS taiKhoanBUS;
	private PhuongThucTTBUS phuongThucTTBUS;
	private KhuyenMaiBUS khuyenMaiBUS;
	private KhachHangBUS khachHangBUS;
    
    
    //listAttribute để tạo inputForm
    public HoaDonDialog(HoaDonPnl hoaDonPnl, String content, String type,  String... listAttribute){
        super(hoaDonPnl.getMainFrame(), content, true);
        this.hoaDonPnl = hoaDonPnl;
        this.content = new JLabel(content);
        this.type = type;
        this.inputForm = new InputForm(listAttribute);
        this.table = hoaDonPnl.getTable();
        this.model = hoaDonPnl.getModel();
        this.btnHuy = new JButton("Hủy");
        this.btnThem = new JButton("Thêm");
        this.btnSua = new JButton("Sửa");
        this.hoaDonBUS = hoaDonPnl.getHoaDonBUS();
        this.taiKhoanBUS = hoaDonPnl.getTaiKhoanBUS();
        this.phuongThucTTBUS = hoaDonPnl.getPhuongThucTTBUS();
        this.khuyenMaiBUS = hoaDonPnl.getKhuyenMaiBUS();
        this.khachHangBUS = hoaDonPnl.getKhachHangBUS();
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
        String[] array1 = taiKhoanBUS.getAllTenTaiKhoan().toArray(new String[0]);
        inputForm.addItem(new InputFormItem("Tài khoản", "combobox", array1));
        String[] array2 = phuongThucTTBUS.getAllTenPhuongThucTT().toArray(new String[0]);
        inputForm.addItem(new InputFormItem("Phương thức", "combobox", array2));
        String[] array3 = khuyenMaiBUS.getAllTenKhuyenMai().toArray(new String[0]);
        inputForm.addItem(new InputFormItem("Khuyến mãi", "combobox", array3));
        String[] array4 = khachHangBUS.getAllTenKhachHang().toArray(new String[0]);
        inputForm.addItem(new InputFormItem("Khách hàng", "combobox", array4));

        if(this.type.equals("thêm")){
            this.add(btnThem);
        }
        // else if(this.type.equals("xem")){
            
        // }
        this.add(btnHuy);

        this.setListener();

        this.setSize(300, 700);
        this.setLocationRelativeTo(hoaDonPnl.getMainFrame());
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
    }

    public void insert(){
        //Lấy thông tin
        String ngayBanS = inputForm.getListItem().get(0).getContent();
        String tongTienS = inputForm.getListItem().get(1).getContent();
        //Lấy lựa chọn combobox
        String tenTK = (String)inputForm.getListItem().get(2).getSelection();
        String tenPT = (String)inputForm.getListItem().get(3).getSelection();
        String tenKM = (String)inputForm.getListItem().get(4).getSelection();
        String tenKH = (String)inputForm.getListItem().get(5).getSelection();
    
        if(!Validation(ngayBanS, tongTienS)){
            return;
        }
    
        //Chuyển đổi sau validate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ngayBan = LocalDateTime.parse(ngayBanS, formatter);
        BigDecimal tongTien = BigDecimal.valueOf(Double.parseDouble(tongTienS));

        int maTK = taiKhoanBUS.getMaTaiKhoanByTen(tenTK);
        int maPT = phuongThucTTBUS.getMaPhuongThucTTByTen(tenPT);
        int maKM = khuyenMaiBUS.getMaKhuyenMaiByTen(tenKM);
        int maKH = khachHangBUS.getMaKhachHangByTen(tenKH);

        HoaDonDTO hoaDon = new HoaDonDTO(ngayBan, tongTien, maTK, maPT, maKM, maKH);
        if(hoaDonBUS.insert(hoaDon) != 0){
            JOptionPane.showMessageDialog(null,"Tạo hóa đơn thành công !");
            model.addRow(new Object[]{hoaDon.getMaHD(), hoaDon.getNgayBan(), hoaDon.getTongTien(), hoaDon.getMaTK(), hoaDon.getMaPT(), hoaDon.getMaKM(), hoaDon.getMaKH()});
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Thêm hóa đơn thất bại !");
        }
    }

    public boolean Validation(String ngayBan, String tongTien){ 
        // cài đặt sau bằng inputformdate
        if(Validate.isEmpty(ngayBan)){
            JOptionPane.showMessageDialog(null, "Ngày bán không để trống !");
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
