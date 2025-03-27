package GUI.Dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.NhaXBBUS;
import BUS.SachBUS;
import BUS.ViTriVungBUS;
import DTO.SachDTO;
import GUI.Components.InputForm;
import GUI.Components.InputFormItem;
import GUI.Panel.SachPnl;
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
    public SachDialog(SachPnl sachPnl, String content, String type, ViTriVungBUS viTriVungBUS, NhaXBBUS nhaXBBUS,  String... listAttribute){
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
        this.viTriVungBUS = viTriVungBUS;
        this.nhaXBBUS = nhaXBBUS;
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
        inputForm.addItem(new InputFormItem("Mã Vùng", "combobox", array1));
        //Thêm item combobox
        String[] array2 = nhaXBBUS.getAllTenNXB().toArray(new String[0]);
        inputForm.addItem(new InputFormItem("Mã nhà xuất bản", "combobox", array2));

        if(this.type.equals("thêm")){
            this.add(btnThem);
        }
        else if(this.type.equals("sửa")){
            this.add(btnSua);
        }
        // else if(this.type.equals("xem")){
            
        // }
        this.add(btnHuy);

        this.setSize(300, 700);
        this.setLocationRelativeTo(sachPnl.getMainFrame());
        this.setVisible(true);
    }

    public void setListener(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnThem){
            //Lấy thông tin
            String tenSach = inputForm.getListItem().get(0).getContent();
            BigDecimal gia = BigDecimal.valueOf(Double.parseDouble(inputForm.getListItem().get(1).getContent()));
            int soLuongTon = Integer.parseInt(inputForm.getListItem().get(2).getContent());
            int namXB = Integer.parseInt(inputForm.getListItem().get(3).getContent());
            //Lấy lựa chọn combobox
            String tenVung = (String)inputForm.getListItem().get(4).getComboBox().getSelectedItem();
            String tenNXB = (String)inputForm.getListItem().get(5).getComboBox().getSelectedItem();
            int maVung = viTriVungBUS.getMaViTriVungByTen(tenVung);
            int maNXB = nhaXBBUS.getMaNXBByTen(tenNXB);

            SachDTO sach = new SachDTO(tenSach, gia, soLuongTon, namXB, maVung, maNXB);
            if(sachBUS.insert(sach) != 0){
                JOptionPane.showConfirmDialog(null,"Thêm sách thành công");
                model.addRow(new Object[]{sach.getTenSach(), sach.getGiaBan(), sach.getSoLuongTon(), sach.getNamXB(), sach.getMaVung(), sach.getMaNXB()});
            }
            else{
                JOptionPane.showMessageDialog(null, "Thêm sách thất bại");
            }
        }
        else if(e.getSource() == btnHuy){
            this.dispose();
        }
        else if(e.getSource() == btnSua){
            
        }
    }

}
