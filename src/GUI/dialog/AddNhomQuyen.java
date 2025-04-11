package GUI.dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import BUS.ChiTietQuyenBUS;
import BUS.ChucNangBUS;
import BUS.NhomQuyenBUS;
import DTO.ChiTietQuyenDTO;
import DTO.ChucNangDTO;
import DTO.NhomQuyenDTO;

import javax.swing.JCheckBox;

import GUI.MainFrame;
import GUI.component.CheckBoxCustom;
import GUI.component.CustomButton;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import GUI.forms.PhanQuyenForm;
import net.miginfocom.swing.MigLayout;
import utils.Validate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class AddNhomQuyen extends JDialog implements ActionListener, ItemListener{
    JFrame parent;
    String title;
    private PhanQuyenForm phanQuyenForm;
    private ArrayList<String[]> arrCN;
    private ChiTietQuyenBUS chiTietQuyenBUS;
    private NhomQuyenBUS nhomQuyenBUS;
    private ChucNangBUS chucNangBUS;
    private ArrayList<ArrayList<CheckBoxCustom>> listCheckBox;
    private CustomTable customTable;
    private ArrayList<String[]> listAllowSelect;
    private int numberOfCN = 16; 

    // NhomQuyenBUS nhomQuyenBUS
    public AddNhomQuyen(JFrame parent, PhanQuyenForm phanQuyenForm, String title, ArrayList<String[]> arrCN) {
        super(parent,title,true);
        this.phanQuyenForm = phanQuyenForm;
        this.arrCN = arrCN;
        this.customTable = phanQuyenForm.getTable();
        this.chiTietQuyenBUS = this.phanQuyenForm.getChiTietQuyenBUS();
        this.nhomQuyenBUS = this.phanQuyenForm.getNhomQuyenBUS();
        this.chucNangBUS = this.phanQuyenForm.getChucNangBUS();
        this.listCheckBox = new ArrayList<>();
        this.listAllowSelect = new ArrayList<>();
        loadListAllowSelect();
        init(); 
    }

    private void init() {
        setSize(1024,768);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel(new MigLayout());
        panel.add(getInforPanel(),"pushx, growx,wrap");
        panel.add(getActionChooser(),"push,grow");

        add(panel);

        setVisible(true);
    }

    
    private JTextField fieldNameRole;
    
    private JPanel getInforPanel() {
        JPanel panel = new JPanel(new MigLayout("al center center"));
        panel.add(new JLabel("<html><b>Tên nhóm quyền: </b></html>"));
        fieldNameRole = new JTextField();
        panel.add(fieldNameRole,"pushx, grow");        
        return panel;
    }

    private JPanel getActionChooser() {
        JPanel panel = new JPanel(new MigLayout("wrap 5"));
    
        panel.add(new JLabel("<html><b>DANH MỤC CHỨC NĂNG</b></html>"), "pushx, growx, cell 0 0");
        panel.add(new JLabel("<html><b>Xem</b></html>"), "pushx,al center, cell 1 0");
        panel.add(new JLabel("<html><b>Thêm</b></html>"), "pushx,al center, cell 2 0");
        panel.add(new JLabel("<html><b>Sửa</b></html>"), "pushx,al center, cell 3 0");
        panel.add(new JLabel("<html><b>Xóa</b></html>"), "pushx,al center, cell 4 0,wrap");

        initListCheckBox();
        for(int i = 0; i < listAllowSelect.size(); i++){
            panel.add(new JLabel(listAllowSelect.get(i)[0]),"pushx,gaptop 10");
            String chucNang = listAllowSelect.get(i)[1];
            String hanhDong = listAllowSelect.get(i)[2];
            int k = 0;
            int skipCell = 0;
            if(hanhDong.contains("Xem")){
                listCheckBox.get(i).add(new CheckBoxCustom(chucNang, "Xem"));
                panel.add(listCheckBox.get(i).get(k),"pushx, al center");
                k++;
            }
            else{
                panel.add(new JPanel());
                skipCell++;
            }
            if(hanhDong.contains("Thêm")){
                listCheckBox.get(i).add(new CheckBoxCustom(chucNang, "Thêm"));
                panel.add(listCheckBox.get(i).get(k),"pushx, al center");
                k++;
            }
            else{
                panel.add(new JPanel());
                skipCell++;
            }
            if(hanhDong.contains("Sửa")){
                listCheckBox.get(i).add(new CheckBoxCustom(chucNang, "Sửa"));
                panel.add(listCheckBox.get(i).get(k),"pushx, al center");
                k++;
            }
            else{
                panel.add(new JPanel());
                skipCell++;
            }
            if(hanhDong.contains("Xóa")){
                listCheckBox.get(i).add(new CheckBoxCustom(chucNang, "Xóa"));
                panel.add(listCheckBox.get(i).get(k),"pushx, al center");
                k++;
            }
            else{
                panel.add(new JPanel());
                skipCell++;
            }
            int soCN = hanhDong.split(" ").length;
            int lastIndex = soCN + skipCell;
            if(lastIndex < 3){
                panel.add(new JPanel(), "skip " + (2 - lastIndex));
            }
        }
        //Cài đặt không cho chọn khi chưa chọn nút xem
        setDefaultCheckBox();
        //Cài đặt listener nút xem
        setListenerCheckBox();

        CustomButton btnThem = new CustomButton("Thêm");    
        btnThem.setActionCommand("add");
        CustomButton btnHuy = new CustomButton("Hủy");    
        btnHuy.setActionCommand("exit");

        JPanel panelbtn = new JPanel();
        panelbtn.add(btnHuy);
        panelbtn.add(btnThem);

        panel.add(panelbtn, "push y, span 5, right");
        btnThem.addActionListener(this);
        btnHuy.addActionListener(this);

        return panel;
    }

    public void initListCheckBox(){
        int n = 16;
        for(int i = 0; i < n; i++){
            listCheckBox.add(new ArrayList<>());
        }
    }

    public void loadListAllowSelect(){
        listAllowSelect.add(new String[] {"Sách", "book", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[] {"Thể loại", "category", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Nhà xuất bản", "author", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Vùng kệ", "vungtl", "Xem Thêm Sửa Xóa"}); // Có làm giao diện vùng kệ k ?
        listAllowSelect.add(new String[]{"Nhà cung cấp", "ncc", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Tạo phiếu nhập", "createInput", "Xem Xóa"});
        listAllowSelect.add(new String[]{"Quản lý phiếu nhập", "qlInput", "Xem Thêm Sửa Xóa"}); //?
        listAllowSelect.add(new String[]{"Tạo hóa đơn", "createBill", "Xem Xóa"});
        listAllowSelect.add(new String[]{"Quản lý hóa đơn", "qlBill", "Xem Thêm Sửa Xóa"}); //?
        listAllowSelect.add(new String[]{"Khuyến mãi", "promotion", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Phương thức thanh toán", "pttt", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Nhân viên", "nv", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Tài khoản", "taikhoan", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Khách hàng", "khachhang", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Phân quyền", "phanquyen", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Báo cáo & thống kê", "report", "Xem"});
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("add")){
            if(Validation()){
                createNhomQuyen();
            }
        }
        else if(e.getActionCommand().equals("exit")){
            this.dispose();
        }
    }

    public void createNhomQuyen(){
        String tenNQ = fieldNameRole.getText();
        NhomQuyenDTO nhomQuyen = new NhomQuyenDTO(tenNQ);
        if(nhomQuyenBUS.insert(nhomQuyen) == 0){
            JOptionPane.showMessageDialog(parent, "Thêm nhóm quyền thất bại");
            return;
        }
    
        int maRole = nhomQuyenBUS.getMaNhomQuyenByTen(tenNQ);

        //Tạo list chi tiết quyền dựa trên listCheckBox
        for(int i = 0; i < listCheckBox.size(); i++){
            String tenCN = listAllowSelect.get(i)[1];
            int maCN = chucNangBUS.getMaChucNangByTen(tenCN);
            for(int j = 0; j < listCheckBox.get(i).size(); j++){
                if(listCheckBox.get(i).get(j).isSelected()){
                    ChiTietQuyenDTO chiTietQuyenDTO = new ChiTietQuyenDTO(maRole, maCN, listCheckBox.get(i).get(j).getHanhDong());
                    chiTietQuyenBUS.insert(chiTietQuyenDTO);
                }
            }
        }
        JOptionPane.showMessageDialog(parent, "Thêm nhóm quyền thành công");
        String[] s = {maRole + "",tenNQ};
        customTable.addDataRow(s);
        this.dispose();
    }

    public boolean Validation(){
        String tenNQ = fieldNameRole.getText();
        if(Validate.isEmpty(tenNQ)){
            JOptionPane.showMessageDialog(parent, "Tên nhóm quyền không được để trống!");
            return(false);
        }
        return(true);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        CheckBoxCustom checkBox = (CheckBoxCustom)e.getSource();

        if(checkBox.isSelected()){
            setEnabled(checkBox.getChucNang());
        }
        else {
            setUnEnable(checkBox.getChucNang());
        }
    }

    public void setDefaultCheckBox(){
        for(int i = 0; i < listCheckBox.size(); i++){
            for(int j = 1; j < listCheckBox.get(i).size(); j++){
                listCheckBox.get(i).get(j).setEnabled(false);
            }
        }
    }

    public void setListenerCheckBox(){
        for(int i = 0; i < listCheckBox.size(); i++){
            listCheckBox.get(i).get(0).addItemListener(this);
        }
    }

    public void setEnabled(String chucNang){
        for(int i = 0; i < listCheckBox.size(); i++){
            String tenCN = listCheckBox.get(i).get(0).getChucNang();
            if(chucNang.equals(tenCN)){
                for(int j = 1; j < listCheckBox.get(i).size(); j++){
                    listCheckBox.get(i).get(j).setEnabled(true);
                }
                break;
            }
        }
    }

    public void setUnEnable(String chucNang){
        for(int i = 0; i < listCheckBox.size(); i++){
            String tenCN = listCheckBox.get(i).get(0).getChucNang();
            if(chucNang.equals(tenCN)){
                for(int j = 1; j < listCheckBox.get(i).size(); j++){
                    listCheckBox.get(i).get(j).setEnabled(false);
                }
                break;
            }
        }
    }


}
