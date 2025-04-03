package GUI.Dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JDialog;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.NhaXBBUS;
import BUS.SachBUS;
import BUS.ViTriVungBUS;
import DTO.SachDTO;
import GUI.Components.InputForm;
import GUI.Components.InputFormItem;
import GUI.Panel.SachPnl;
import utils.Validate;
import net.miginfocom.swing.MigLayout;

public class SachDialog extends JDialog implements ActionListener{
    SachPnl sachPnl;
    JLabel content; // hộp thoại thêm/ sửa/ xem
    String type; // "thêm"/ "sửa"/ "xem"
    InputForm inputForm;
    JTable table;
    DefaultTableModel model;
    JButton btnThem; // button trong phần dialog á
    JButton btnSua; //
    JButton btnHuy; //
    // Khóa ngoại làm theo combobox
    SachBUS sachBUS;
    ViTriVungBUS viTriVungBUS;
    NhaXBBUS nhaXBBUS;
    
    //listAttribute để tạo inputForm
    public SachDialog(SachPnl sachPnl, String content, String type, String... listAttribute){
        super(sachPnl.getMainFrame(), content, true);
        this.sachPnl = sachPnl;
        this.content = new JLabel(content);
        this.type = type;
        this.inputForm = new InputForm(listAttribute);
        this.table = sachPnl.getTable();
        this.model = sachPnl.getModel();
        this.btnHuy = new JButton("Hủy");
        this.btnThem = new JButton("Thêm");
        this.btnSua = new JButton("Sửa");
        this.sachBUS = sachPnl.getSachBUS();
        this.viTriVungBUS = sachPnl.getViTriVungBUS();
        this.nhaXBBUS = sachPnl.getNhaXBBUS();
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
        String[] array1 = viTriVungBUS.getAllTenVung().toArray(new String[0]);
        inputForm.addItem(new InputFormItem("Vùng", "combobox", array1));
        //Thêm item combobox
        String[] array2 = nhaXBBUS.getAllTenNXB().toArray(new String[0]);
        inputForm.addItem(new InputFormItem("Nhà xuất bản", "combobox", array2));

        if(this.type.equals("thêm")){
            this.add(btnThem);
        }
        else if(this.type.equals("sửa")){
            //Lấy thông tin chọn
            int row = table.getSelectedRow();
            String tenSach = (String)model.getValueAt(row, 1);
            BigDecimal gia = new BigDecimal(model.getValueAt(row, 2) + "");
            int namXB = (int)model.getValueAt(row, 4);
            int maVung = (int)model.getValueAt(row, 5); 
            int maNXB = (int)model.getValueAt(row, 6);
            //set các thông tin cũ
            this.inputForm.getListItem().get(0).setContent(tenSach);
            this.inputForm.getListItem().get(1).setContent(gia + "");
            this.inputForm.getListItem().get(2).setContent(namXB + "");
            this.inputForm.getListItem().get(3).setSelection(viTriVungBUS.getTenByMaViTriVung(maVung));
            this.inputForm.getListItem().get(4).setSelection(nhaXBBUS.getTenByMaNXB(maNXB));

            this.add(btnSua);
        }
        // else if(this.type.equals("xem")){
            
        // }
        this.add(btnHuy);

        this.setListener();

        this.setSize(300, 700);
        this.setLocationRelativeTo(sachPnl.getMainFrame());
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
        String tenSach = inputForm.getListItem().get(0).getContent();
        String giaS = inputForm.getListItem().get(1).getContent();
        String namXBS = inputForm.getListItem().get(2).getContent();
        //Lấy lựa chọn combobox
        String tenVung = (String)inputForm.getListItem().get(3).getSelection();
        String tenNXB = (String)inputForm.getListItem().get(4).getSelection();
    
        if(!Validation(tenSach, giaS, namXBS)){
            return;
        }
    
        //Chuyển đổi sau validate
        BigDecimal gia = BigDecimal.valueOf(Double.parseDouble(giaS));
        int namXB = Integer.parseInt(namXBS);
        int maVung = viTriVungBUS.getMaViTriVungByTen(tenVung);
        int maNXB = nhaXBBUS.getMaNXBByTen(tenNXB);
        SachDTO sach = new SachDTO(tenSach, gia, namXB, maVung, maNXB);
        if(sachBUS.insert(sach) != 0){
            JOptionPane.showMessageDialog(null,"Thêm sách thành công !");
            model.addRow(new Object[]{sach.getMaSach(), sach.getTenSach(), sach.getGiaBan(), sach.getSoLuongTon(), sach.getNamXB(), sach.getMaVung(), sach.getMaNXB()});
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Thêm sách thất bại !");
        }
    }

    public void update(){
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa sách này ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.NO_OPTION){
            return;
        }
        //Lấy thông tin
        String tenSach = inputForm.getListItem().get(0).getContent();
        String giaS = inputForm.getListItem().get(1).getContent();
        String namXBS = inputForm.getListItem().get(2).getContent();
        //Lấy lựa chọn combobox
        String tenVung = (String)inputForm.getListItem().get(3).getSelection();
        String tenNXB = (String)inputForm.getListItem().get(4).getSelection();
    
        if(!Validation(tenSach, giaS, namXBS)){
            return;
        }
    
        //Chuyển đổi sau validate
        BigDecimal gia = BigDecimal.valueOf(Double.parseDouble(giaS));
        int namXB = Integer.parseInt(namXBS);
        int maVung = viTriVungBUS.getMaViTriVungByTen(tenVung);
        int maNXB = nhaXBBUS.getMaNXBByTen(tenNXB);
        
        //Lấy mã sách để sửa
        int row = table.getSelectedRow();
        int maSach = (int)model.getValueAt(row, 0);

        SachDTO sach = new SachDTO(maSach, tenSach, gia, namXB, maVung, maNXB);

        if(sachBUS.update(sach) != 0){
            JOptionPane.showMessageDialog(null, "Sửa sách thành công !");
            model.setValueAt(tenSach, row, 1);
            model.setValueAt(gia, row, 2);
            model.setValueAt(namXB, row, 3);
            model.setValueAt(maVung, row, 4);
            model.setValueAt(maNXB, row, 5);
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Sửa sách thất bại !");
        }
    }

    public boolean Validation(String ten, String gia, String namXB){
        if(Validate.isEmpty(ten)){
            JOptionPane.showMessageDialog(null, "Tên sách không được để trống !");
            return(false);
        }
        else if(Validate.isEmpty(gia)){
            JOptionPane.showMessageDialog(null, "Giá tiền không được để trống !");
            return(false);
        }
        else if(!Validate.isPositiveNumber(gia)){
            JOptionPane.showMessageDialog(null, "Giá tiền phải là số dương !");
            return(false);
        }
        else if(Validate.isEmpty(namXB)){   
            JOptionPane.showMessageDialog(null, "Năm xuất bản không được để trống !");
            return(false);
        }
        else if(!Validate.isYear(namXB)){   
            JOptionPane.showMessageDialog(null, "Năm xuất bản phải nhập đúng định dạng !");
            return(false);
        }
        return(true);
    }

}
