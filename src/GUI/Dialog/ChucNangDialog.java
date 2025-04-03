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

import BUS.ChucNangBUS;
import DTO.ChucNangDTO;
import GUI.Components.InputForm;
import GUI.Panel.ChucNangPnl;
import utils.Validate;
import net.miginfocom.swing.MigLayout;

public class ChucNangDialog extends JDialog implements ActionListener{
    ChucNangPnl chucNangPnl;
    JLabel content; // hộp thoại thêm/ sửa/ xem
    String type; // "thêm"/ "sửa"/ "xem"
    InputForm inputForm;
    JTable table;
    DefaultTableModel model;
    JButton btnThem; // button trong phần dialog á
    JButton btnSua; //
    JButton btnHuy; //
    ChucNangBUS chucNangBUS;
    
    //listAttribute để tạo inputForm
    public ChucNangDialog(ChucNangPnl chucNangPnl, String content, String type, String... listAttribute){
        super(chucNangPnl.getMainFrame(), content, true);
        this.chucNangPnl = chucNangPnl;
        this.content = new JLabel(content);
        this.type = type;
        this.inputForm = new InputForm(listAttribute);
        this.table = chucNangPnl.getTable();
        this.model = chucNangPnl.getModel();
        this.btnHuy = new JButton("Hủy");
        this.btnThem = new JButton("Thêm");
        this.btnSua = new JButton("Sửa");
        this.chucNangBUS = chucNangPnl.getChucNangBUS();

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
            String tenChucNang = (String)model.getValueAt(row, 1);
            //set các thông tin cũ
            this.inputForm.getListItem().get(0).setContent(tenChucNang);

            this.add(btnSua);
        }
        // else if(this.type.equals("xem")){
            
        // }
        this.add(btnHuy);

        this.setListener();

        this.setSize(300, 700);
        this.setLocationRelativeTo(chucNangPnl.getMainFrame());
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
        String tenChucNang = inputForm.getListItem().get(0).getContent();
    
        if(!Validation(tenChucNang)){
            return;
        }
    
        ChucNangDTO chucNang = new ChucNangDTO(tenChucNang);
        if(chucNangBUS.insert(chucNang) != 0){
            JOptionPane.showMessageDialog(null,"Thêm chức năng thành công !");
            model.addRow(new Object[]{chucNang.getMaChucNang(), chucNang.getTenChucNang()});
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Thêm chức năng thất bại !");
        }
    }

    public void update(){
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa chức năng này ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.NO_OPTION){
            return;
        }
        //Lấy thông tin
        String tenChucNang = inputForm.getListItem().get(0).getContent();
    
        if(!Validation(tenChucNang)){
            return;
        }
        
        //Lấy mã sách để sửa
        int row = table.getSelectedRow();
        int maChucNang = (int)model.getValueAt(row, 0);

        ChucNangDTO chucNang = new ChucNangDTO(maChucNang, tenChucNang);

        if(chucNangBUS.update(chucNang) != 0){
            JOptionPane.showMessageDialog(null, "Sửa chức năng thành công !");
            model.setValueAt(tenChucNang, row, 1);
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Sửa chức năng thất bại !");
        }
    }

    public boolean Validation(String ten){
        if(Validate.isEmpty(ten)){
            JOptionPane.showMessageDialog(null, "Tên chức năng không được để trống !");
            return(false);
        }
        return(true);
    }

}
