package GUI.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.DanhMuc_TGBUS;
import BUS.NhaXBBUS;
import BUS.PhanLoaiBUS;
import BUS.SachBUS;
import BUS.TacGiaBUS;
import BUS.TheLoaiBUS;
import BUS.ViTriVungBUS;
import DTO.DanhMuc_TGDTO;
import DTO.PhanLoaiDTO;
import DTO.SachDTO;
import DTO.TacGiaDTO;
import GUI.MainFrame;
import GUI.component.CustomButton;
import GUI.component.InputForm;
import GUI.forms.SachForm;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import resources.base.baseTheme;
import utils.Validate;

public class SachDialog extends JDialog implements ActionListener{
    private SachForm sachPanel;
    private MainFrame mainFrame;
    private SachBUS sachBUS; 
    private JLabel label;
    private String type;
    private String[][] attributes;
    private InputForm inputForm;
    //old data
    private ArrayList<Integer> oldListTL; 
    private ArrayList<Integer> oldListTG;                

    //Lấy khóa ngoại
    private ViTriVungBUS viTriVungBUS;
    private NhaXBBUS nhaXBBUS;

    //Lấy khóa ngoại nhiều nhiều
    private TheLoaiBUS theLoaiBUS;
    private TacGiaBUS tacGiaBUS;
    private PhanLoaiBUS phanLoaiBUS;
    private DanhMuc_TGBUS danhMuc_TGBUS;

    private int rowSelected;

    
    public SachDialog(SachForm sachPanel, String title, String function, String type, String[][] attributes, int... row){
        super(sachPanel.getMainFrame(), title, true);
        phanLoaiBUS = PhanLoaiBUS.getInstance();
        danhMuc_TGBUS = DanhMuc_TGBUS.getInstance();
        this.oldListTL = new ArrayList<>();
        this.oldListTG = new ArrayList<>();
        if(row.length == 1){
            this.rowSelected = row[0];
        }
        this.init();
    }

    public void init(){
        this.setLayout(new MigLayout("wrap 1, insets 30 0 20 0", "[grow]"));
        this.getContentPane().setBackground(Color.decode("#FFFFFF"));

        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("insets 20 0 20 0, align center"));
        panel.add(label);  
        label.setForeground(Color.decode("#FFFFFF"));
        panel.putClientProperty(FlatClientProperties.STYLE, String.format("background: %s", baseTheme.mainColor)); 

        int originalSize = 250;
        int rowsize = 70;
        this.setSize(new Dimension(300, originalSize + rowsize * attributes.length));
        this.add(panel, "grow");

        //Cài đặt lựa chọn cmbobox
        String[] listVung = viTriVungBUS.getAllTenVung().toArray(new String[0]);
        String[] listNXB = nhaXBBUS.getAllTenNXB().toArray(new String[0]);
        inputForm.getListItem().get(3).setListCombobox(listVung);
        inputForm.getListItem().get(4).setListCombobox(listNXB);

        //Cài đặt khóa ngoại nhiều nhiều
        setListenerBtnKNNN();

        this.add(inputForm, "grow");
        setLocationRelativeTo(mainFrame);

        setButton();
    }

    public void setListenerBtnKNNN(){
        inputForm.getListItem().get(5).getBtnKNNN().setActionCommand("theloai");
        inputForm.getListItem().get(6).getBtnKNNN().setActionCommand("tacgia");
        inputForm.getListItem().get(5).getBtnKNNN().addActionListener(this);
        inputForm.getListItem().get(6).getBtnKNNN().addActionListener(this);
    }

    public void setButton(){
        if(type.equals("add")){
            CustomButton btnThem = new CustomButton("Thêm");
            btnThem.setActionCommand("add");
            btnThem.addActionListener(this);
            CustomButton btnHuy = new CustomButton("Hủy");
            btnHuy.setActionCommand("exit");
            btnHuy.addActionListener(this);
            JPanel panel = new JPanel();
            panel.setLayout(new MigLayout("wrap 2"));
            panel.setBackground(Color.decode("#FFFFFF"));
            panel.add(btnHuy);
            panel.add(btnThem);
            this.add(new JPanel(), "push y");
            this.add(panel, "right, gap right 10");
        }
        else if(type.equals("update")){
            CustomButton btnSua = new CustomButton("Sửa");
            btnSua.setActionCommand("update");
            btnSua.addActionListener(this);
            CustomButton btnHuy = new CustomButton("Hủy");
            btnHuy.setActionCommand("exit");
            btnHuy.addActionListener(this);
            JPanel panel = new JPanel();
            panel.setLayout(new MigLayout("wrap 2"));
            panel.setBackground(Color.decode("#FFFFFF"));
            panel.add(btnHuy);
            panel.add(btnSua);
            this.add(new JPanel(), "push y");
            this.add(panel, "right, gap right 10");

            //set dữ liệu cũ
            setOldData();
        }
        else if(type.equals("detail")){
            CustomButton btnHuy = new CustomButton("Thoát");
            btnHuy.setActionCommand("exit");
            btnHuy.addActionListener(this);
            JPanel panel = new JPanel();
            panel.setLayout(new MigLayout("wrap 2"));
            panel.setBackground(Color.decode("#FFFFFF"));
            panel.add(btnHuy);
            this.add(new JPanel(), "push y");
            this.add(panel, "right, gap right 10");

            //set dữ liệu cũ
            setOldData();
            setInputForDetail();
        }
    }

    public void setOldData(){
        int ma = Integer.parseInt(sachPanel.getTable().getCellData(rowSelected, 0));
        String ten = sachPanel.getTable().getCellData(rowSelected, 1);
        String giaBan = sachPanel.getTable().getCellData(rowSelected, 3); 
        String namXB = sachPanel.getTable().getCellData(rowSelected, 4);

        String tenVung = viTriVungBUS.getTenVungByMaSach(ma);
        String tenNXB = nhaXBBUS.getTenNXBByMaSach(ma);

        String listTL = new String();
        String listTG = new String();
        for(int i : phanLoaiBUS.getAllMaTheLoaiByMaSach(ma)){
            listTL += theLoaiBUS.getTenByMa(i) + ", ";
        }
        for(int i : danhMuc_TGBUS.getAllMaTacGiaByMaSach(ma)){
            listTG += tacGiaBUS.getTenByMa(i) + ", ";
        }
        String newListTL = removeLastCommaForFKNN(listTL);
        String newListTG = removeLastCommaForFKNN(listTG);

        inputForm.getListItem().get(0).setText(ten);
        inputForm.getListItem().get(1).setText(giaBan);
        inputForm.getListItem().get(2).setText(namXB);
        inputForm.getListItem().get(3).setSelection(tenVung);
        inputForm.getListItem().get(4).setSelection(tenNXB);
        inputForm.getListItem().get(5).setTextKNNN(newListTL);
        inputForm.getListItem().get(6).setTextKNNN(newListTG);

        //Lấy gtri 2 list cho update
        getOldList();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")){
            if(validation()){
                insert();
            }
        }
        else if (e.getActionCommand().equals("update")){
            if(validation()){
                update();
            }
        }
        else if(e.getActionCommand().equals("exit")){
            this.dispose();
        }
        //KNNN
        else if (e.getActionCommand().equals("theloai")){
            String[] listFK = theLoaiBUS.getAllTenTheLoai().toArray(new String[0]);

            KNNNDialog knnnDialog = new KNNNDialog(mainFrame, "Thể loại","thể loại", listFK, data -> {
                    //bỏ dấu , cuối
                    String newData = removeLastCommaForFKNN(data);
                    this.inputForm.getListItem().get(5).setTextKNNN(newData);
                } 
            );
            knnnDialog.setVisible(true);
        }
        else if (e.getActionCommand().equals("tacgia")){
            String[] listFK = tacGiaBUS.getAllTenTacGia().toArray(new String[0]);

            KNNNDialog knnnDialog = new KNNNDialog(mainFrame, "Tác giả","tác giả", listFK, data -> {
                    String newData = removeLastCommaForFKNN(data);

                    this.inputForm.getListItem().get(6).setTextKNNN(newData);
                } 
            );
            knnnDialog.setVisible(true);
        }
    }

    public void insert(){
        String ten = inputForm.getListItem().get(0).getText();
        BigDecimal giaBan = BigDecimal.valueOf(Double.parseDouble(inputForm.getListItem().get(1).getText()));
        int namXB = Integer.parseInt(inputForm.getListItem().get(2).getText());
        String tenVung = inputForm.getListItem().get(3).getSelection();
        String tenNhaXB = inputForm.getListItem().get(4).getSelection();

        int maVung = viTriVungBUS.getMaViTriVungByTen(tenVung);
        int maNXB = nhaXBBUS.getMaNXBByTen(tenNhaXB);

        SachDTO sach = new SachDTO(ten, giaBan, namXB, maVung, maNXB);
        if(sachBUS.insert(sach) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Thêm thành công");
            String[] row = {sach.getMaSach()+"", sach.getTenSach(), sach.getSoLuong() + "", sach.getGiaBan() + "", tenVung + ""};
            sachPanel.getTable().addDataRow(row);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Thêm sách thất bại!");
            this.dispose();
        }
        //Insert khóa ngoại
        String listTL = this.inputForm.getListItem().get(5).getTextKNNN();
        String listTG = this.inputForm.getListItem().get(6).getTextKNNN();
        InsertListPhanLoai(sach.getMaSach(), listTL);
        InsertListDanhMucTG(sach.getMaSach(), listTG);
    }

    public void update(){
        int ma = Integer.parseInt(sachPanel.getTable().getCellData(rowSelected, 0));
        String ten = inputForm.getListItem().get(0).getText();
        BigDecimal giaBan = BigDecimal.valueOf(Double.parseDouble(inputForm.getListItem().get(1).getText()));
        int namXB = Integer.parseInt(inputForm.getListItem().get(2).getText());
        String tenVung = inputForm.getListItem().get(3).getSelection();
        String tenNhaXB = inputForm.getListItem().get(4).getSelection();

        int maVung = viTriVungBUS.getMaViTriVungByTen(tenVung);
        int maNXB = nhaXBBUS.getMaNXBByTen(tenNhaXB);

        SachDTO sach = new SachDTO(ma, ten, giaBan, namXB, maVung, maNXB);
        if(sachBUS.update(sach) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Sửa thành công");
            String[] row = {sach.getMaSach()+"", sach.getTenSach(), sach.getSoLuong() + "", sach.getGiaBan() + "", sach.getNamXB() + ""};
            sachPanel.getTable().setRowData(rowSelected, row);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Sửa thất bại!");
            this.dispose();
        }

        //cập nhật xuống DAO
        UpdateListPhanloai();
        UpdateListDanhMuc_TG();

    }

    public void setInputForDetail(){
        inputForm.getListItem().get(0).getTextField().setEditable(false);
        inputForm.getListItem().get(1).getTextField().setEditable(false);
        inputForm.getListItem().get(2).getTextField().setEditable(false);
        inputForm.getListItem().get(3).getCombobox().setEnabled(false);
        inputForm.getListItem().get(4).getCombobox().setEnabled(false);
    }

    public String removeLastCommaForFKNN(String text) {
        if (text != null) {
            text = text.trim();
            if (text.endsWith(",")) {
                return text.substring(0, text.length() - 1);
            }
        }
        return text;
    }

    public void InsertListPhanLoai(int maSach, String x){
        String[] list = x.split(", ");
        for(String i : list){
            int ma = theLoaiBUS.getMaByTen(i);
            PhanLoaiDTO pl = new PhanLoaiDTO(maSach, ma); 
            phanLoaiBUS.insert(pl);
        }
    }

    public void InsertListDanhMucTG(int maSach, String x){
        String[] list = x.split(", ");
        for(String i : list){
            int ma = tacGiaBUS.getMaByTen(i);
            DanhMuc_TGDTO pl = new DanhMuc_TGDTO(maSach, ma); 
            danhMuc_TGBUS.insert(pl);
        }
    }

    //update cho so sánh
    public void UpdateListPhanloai(){
        //oldListTL
        int maSach = Integer.parseInt(sachPanel.getTable().getCellData(rowSelected, 0));
        String[] temp = inputForm.getListItem().get(5).getTextKNNN().split(", ");
        ArrayList<Integer> newListTL = new ArrayList<>();
        for(int i = 0; i < temp.length; i++){
            newListTL.add(theLoaiBUS.getMaByTen(temp[i]));
        }
        //Thực hiện delete nếu cũ có mới không có
        for(int i : oldListTL){
            if(!newListTL.contains(i)){
                phanLoaiBUS.delete(maSach, i);
            }
        }
        //Thực hiện insert nếu cũ không có mới có
        for(int i : newListTL){
            if(!oldListTL.contains(i)){
                PhanLoaiDTO phanLoai = new PhanLoaiDTO(maSach, i);
                phanLoaiBUS.insert(phanLoai);
            }
        }
    }

    public void UpdateListDanhMuc_TG(){
        //oldListTL
        int maSach = Integer.parseInt(sachPanel.getTable().getCellData(rowSelected, 0));
        String[] temp = inputForm.getListItem().get(6).getTextKNNN().split(", ");
        ArrayList<Integer> newListTG = new ArrayList<>();
        for(int i = 0; i < temp.length; i++){
            newListTG.add(tacGiaBUS.getMaByTen(temp[i]));
        }
        //Thực hiện delete nếu cũ có mới không có
        for(int i : oldListTL){
            if(!newListTG.contains(i)){
                danhMuc_TGBUS.delete(maSach, i);
            }
        }
        //Thực hiện insert nếu cũ không có mới có
        for(int i : newListTG){
            if(!oldListTL.contains(i)){
                DanhMuc_TGDTO tgia = new DanhMuc_TGDTO(maSach, i);
                danhMuc_TGBUS.insert(tgia);
            }
        }
    }

    //Lấy ds cũ
    public void getOldList(){
        int ma = Integer.parseInt(sachPanel.getTable().getCellData(rowSelected, 0)); 
        for(int i : phanLoaiBUS.getAllMaTheLoaiByMaSach(ma)){
            oldListTL.add(i);
        }
        for(int i : danhMuc_TGBUS.getAllMaTacGiaByMaSach(ma)){
            oldListTG.add(i);
        }
    }


    public boolean validation(){
        String ten = inputForm.getListItem().get(0).getText();
        String giaBan = inputForm.getListItem().get(1).getText();
        String namXB = inputForm.getListItem().get(2).getText();
        if(Validate.isEmpty(ten)){
            JOptionPane.showMessageDialog(mainFrame, "Tên sách không được để trống!");
            return(false);
        }
        if(Validate.isEmpty(giaBan)){
            JOptionPane.showMessageDialog(mainFrame, "Giá bán không được để trống!");
            return(false);
        }
        if(Validate.isEmpty(namXB)){
            JOptionPane.showMessageDialog(mainFrame, "Năm xuất bản không được để trống!");
            return(false);
        }
        if(!Validate.isYear(namXB)){
            JOptionPane.showMessageDialog(mainFrame, "Năm xuất bản phải nhập đúng định dạng!");
            return(false);
        }
        return(true);
    }
}
