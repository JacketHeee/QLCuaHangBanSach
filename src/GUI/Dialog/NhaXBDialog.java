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

import BUS.NhaXBBUS;
import DTO.NhaXBDTO;
import GUI.Components.InputForm;
import GUI.Panel.NhaXBPnl;
import Utils.Validate;
import net.miginfocom.swing.MigLayout;

public class NhaXBDialog extends JDialog implements ActionListener{
    NhaXBPnl nhaXBPnl;
    JLabel content; // hộp thoại thêm/ sửa/ xem
    String type; // "thêm"/ "sửa"/ "xem"
    InputForm inputForm;
    JTable table;
    DefaultTableModel model;
    JButton btnThem; // button trong phần dialog á
    JButton btnSua; //
    JButton btnHuy; //
    NhaXBBUS nhaXBBUS;
    
    //listAttribute để tạo inputForm
    public NhaXBDialog(NhaXBPnl nhaXBPnl, String content, String type, String... listAttribute){
        super(nhaXBPnl.getMainFrame(), content, true);
        this.nhaXBPnl = nhaXBPnl;
        this.content = new JLabel(content);
        this.type = type;
        this.inputForm = new InputForm(listAttribute);
        this.table = nhaXBPnl.getTable();
        this.model = nhaXBPnl.getModel();
        this.btnHuy = new JButton("Hủy");
        this.btnThem = new JButton("Thêm");
        this.btnSua = new JButton("Sửa");
        this.nhaXBPnl = nhaXBPnl;
        this.nhaXBBUS = nhaXBPnl.getNhaXBBUS();
        this.initComponent();
    }

    public void initComponent(){
        this.setLayout(new MigLayout("wrap 1", "[grow]"));
        this.getContentPane().setBackground(Color.decode("#FFFFFF"));

        content.setFont(new Font("Segoe UI", Font.BOLD, 26));
        this.add(content, "center");

        //Thêm InputForm
        this.add(inputForm, "grow");

        if(this.type.equals("thêm")){
            this.add(btnThem);
        }
        else if(this.type.equals("sửa")){
            //Lấy thông tin chọn
            int row = table.getSelectedRow();
            String tenNhaXB = (String)model.getValueAt(row, 1);
            String diaChi = (String)model.getValueAt(row, 2);
            String soDT = (String)model.getValueAt(row, 3);
            String email = (String)model.getValueAt(row, 4);

            //set các thông tin cũ
            this.inputForm.getListItem().get(0).setContent(tenNhaXB);
            this.inputForm.getListItem().get(1).setContent(diaChi);
            this.inputForm.getListItem().get(2).setContent(soDT);
            this.inputForm.getListItem().get(3).setSelection(email);

            this.add(btnSua);
        }
        // else if(this.type.equals("xem")){
            
        // }
        this.add(btnHuy);

        this.setListener();

        this.setSize(300, 700);
        this.setLocationRelativeTo(nhaXBPnl.getMainFrame());
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
        String tenNhaXB = inputForm.getListItem().get(0).getContent();
        String diaChi = inputForm.getListItem().get(1).getContent();
        String soDT = inputForm.getListItem().get(2).getContent();
        String email = inputForm.getListItem().get(3).getContent();

    
        if(!Validation(tenNhaXB, diaChi, soDT, email)){
            return;
        }
    
        NhaXBDTO nhaXB = new NhaXBDTO(tenNhaXB, diaChi, soDT, email);
        if(nhaXBBUS.insert(nhaXB) != 0){
            JOptionPane.showMessageDialog(null,"Thêm nhà xuất bản thành công !");
            model.addRow(new Object[]{nhaXB.getMaNXB(), nhaXB.getTenNXB(), nhaXB.getDiaChi(), nhaXB.getSoDT(), nhaXB.getEmail()});
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Thêm nhà xuất bản thất bại !");
        }
    }

    public void update(){
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa nhà xuất bản này ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.NO_OPTION){
            return;
        }

        //Lấy thông tin
        String tenNhaXB = inputForm.getListItem().get(0).getContent();
        String diaChi = inputForm.getListItem().get(1).getContent();
        String soDT = inputForm.getListItem().get(2).getContent();
        String email = inputForm.getListItem().get(3).getContent();

        if(!Validation(tenNhaXB, diaChi, soDT, email)){
            return;
        }
        
        //Lấy mã nhà xuất bản để sửa
        int row = table.getSelectedRow();
        int maNhaXB = (int)model.getValueAt(row, 0);

        NhaXBDTO nhaXB = new NhaXBDTO(maNhaXB, tenNhaXB, diaChi, soDT, email);

        if(nhaXBBUS.update(nhaXB) != 0){
            JOptionPane.showMessageDialog(null, "Sửa nhà xuất bản thành công !");
            model.setValueAt(tenNhaXB, row, 1);
            model.setValueAt(diaChi, row, 2);
            model.setValueAt(soDT, row, 3);
            model.setValueAt(email, row, 4);
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Sửa nhà xuất bản thất bại !");
        }
    }

    public boolean Validation(String ten, String diaChi, String soDT, String email){
        if(Validate.isEmpty(ten)){
            JOptionPane.showMessageDialog(null, "Tên nhà xuất bản không được để trống !");
            return(false);
        }
        else if(Validate.isEmpty(diaChi)){
            JOptionPane.showMessageDialog(null, "Địa chỉ không được để trống !");
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
        else if(Validate.isEmpty(email)){
            JOptionPane.showMessageDialog(null, "Email không được để trống !");
            return(false);
        }
        else if(!Validate.isEmail(email)){
            JOptionPane.showMessageDialog(null, "Email phải nhập đúng định dạng !");
            return(false);
        }
        return(true);
    }

}
