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
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.DanhMuc_TGBUS;
import BUS.KM_SachBUS;
import BUS.KhuyenMaiBUS;
import BUS.NhaXBBUS;
import BUS.PhanLoaiBUS;
import BUS.SachBUS;
import BUS.TacGiaBUS;
import BUS.TheLoaiBUS;
import BUS.ViTriVungBUS;
import DTO.DanhMuc_TGDTO;
import DTO.KM_SachDTO;
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
import utils.FormatterUtil;
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
    private ArrayList<Integer> oldListKM;            

    //Lấy khóa ngoại
    private ViTriVungBUS viTriVungBUS;
    private NhaXBBUS nhaXBBUS;

    //Lấy khóa ngoại nhiều nhiều
    private TheLoaiBUS theLoaiBUS;
    private TacGiaBUS tacGiaBUS;
    private KhuyenMaiBUS khuyenMaiBUS;
    private PhanLoaiBUS phanLoaiBUS;
    private DanhMuc_TGBUS danhMuc_TGBUS;
    private KM_SachBUS kM_SachBUS;

    private int rowSelected;

    
    public SachDialog(SachForm sachPanel, String title, String function, String type, String[][] attributes, int... row){
        super(sachPanel.getMainFrame(), title, true);
        this.attributes = attributes;
        this.sachPanel = sachPanel;
        this.mainFrame = sachPanel.getMainFrame();
        
        this.inputForm = new InputForm(attributes);
        this.type = type;
        this.sachBUS = SachBUS.getInstance();
        this.phanLoaiBUS = PhanLoaiBUS.getInstance();
        this.danhMuc_TGBUS = DanhMuc_TGBUS.getInstance();
        this.oldListTL = new ArrayList<>();
        this.oldListTG = new ArrayList<>();
        this.oldListKM = new ArrayList<>();
        this.label = new JLabel("<html><strong><font size=+2>" + function + "</font></strong><html>");
        this.viTriVungBUS = ViTriVungBUS.getInstance();
        this.nhaXBBUS = NhaXBBUS.getInstance();
        this.theLoaiBUS = TheLoaiBUS.getInstance();
        this.tacGiaBUS = TacGiaBUS.getInstance();
        this.khuyenMaiBUS = KhuyenMaiBUS.getInstance();
        this.kM_SachBUS = KM_SachBUS.getInstance();
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
        this.setSize(new Dimension(600, originalSize + rowsize * (attributes.length-1))); // trừ 1 tại thêm cái ảnh á :<<
        this.add(panel, "grow");

        //Cài đặt lựa chọn cmbobox
        String[] listVung = viTriVungBUS.getAllTenVung().toArray(new String[0]);
        String[] listNXB = nhaXBBUS.getAllTenNXB().toArray(new String[0]);
        inputForm.getListItem().get(2).setListCombobox(listVung);
        inputForm.getListItem().get(3).setListCombobox(listNXB);

        //Cài đặt khóa ngoại nhiều nhiều
        setListenerBtnKNNN();

        this.add(inputForm, "grow");
        setLocationRelativeTo(mainFrame);

        setButton();
    }

    public void setListenerBtnKNNN(){
        inputForm.getListItem().get(4).getBtnKNNN().setActionCommand("theloai");
        inputForm.getListItem().get(5).getBtnKNNN().setActionCommand("tacgia");
        inputForm.getListItem().get(6).getBtnKNNN().setActionCommand("khuyenmai");
        inputForm.getListItem().get(4).getBtnKNNN().addActionListener(this);
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
            this.add(new JLabel(), "push y");
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
            this.add(new JLabel(), "push y");
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
        // String ten = sachPanel.getTable().getCellData(rowSelected, 1);
        // String namXB = sachPanel.getTable().getCellData(rowSelected, 4);
        
        SachDTO sach = sachBUS.getInstanceByID(ma);

        String tenVung = viTriVungBUS.getTenVungByMaSach(ma);
        String tenNXB = nhaXBBUS.getTenNXBByMaSach(ma);

        String listTL = new String();
        String listTG = new String();
        String listKM = new String();

        for(int i : phanLoaiBUS.getAllMaTheLoaiByMaSach(ma)){
            listTL += theLoaiBUS.getTenByMa(i) + ", ";
        }
        for(int i : danhMuc_TGBUS.getAllMaTacGiaByMaSach(ma)){
            listTG += tacGiaBUS.getTenByMa(i) + ", ";
        }
        for(int i : kM_SachBUS.getAllMaKMByMaSach(ma)){
            listKM += khuyenMaiBUS.getTenByMaKhuyenMai(i) + ", ";
        }
        String newListTL = removeLastCommaForFKNN(listTL);
        String newListTG = removeLastCommaForFKNN(listTG);
        String newListKM = removeLastCommaForFKNN(listKM);

        String path = sachBUS.getPathByMa(ma);

        inputForm.getListItem().get(0).setText(sach.getTenSach());
        inputForm.getListItem().get(1).setText(sach.getNamXB()+"");
        inputForm.getListItem().get(2).setSelection(tenVung);
        inputForm.getListItem().get(3).setSelection(tenNXB);
        inputForm.getListItem().get(4).setTextKNNN(newListTL);
        inputForm.getListItem().get(5).setTextKNNN(newListTG);
        inputForm.getListItem().get(6).setTextKNNN(newListKM);
        inputForm.getListItem().get(7).setAnh(path);

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
                    this.inputForm.getListItem().get(4).setTextKNNN(newData);
                } 
            );
            knnnDialog.setVisible(true);
        }
        else if (e.getActionCommand().equals("tacgia")){
            String[] listFK = tacGiaBUS.getAllTenTacGia().toArray(new String[0]);

            KNNNDialog knnnDialog = new KNNNDialog(mainFrame, "Tác giả","tác giả", listFK, data -> {
                    String newData = removeLastCommaForFKNN(data);

                    this.inputForm.getListItem().get(5).setTextKNNN(newData);
                } 
            );
            knnnDialog.setVisible(true);
        }
        else if (e.getActionCommand().equals("khuyenmai")){
            String[] listFK = khuyenMaiBUS.getAllTenKhuyenMai().toArray(new String[0]);

            KNNNDialog knnnDialog = new KNNNDialog(mainFrame, "Khuyến mãi","khuyến mãi", listFK, data -> {
                    String newData = removeLastCommaForFKNN(data);

                    this.inputForm.getListItem().get(6).setTextKNNN(newData);
                } 
            );
            knnnDialog.setVisible(true);
        }
    }

    public void insert(){
        String ten = inputForm.getListItem().get(0).getText();
        int namXB = Integer.parseInt(inputForm.getListItem().get(1).getText());
        String tenVung = inputForm.getListItem().get(2).getSelection();
        String tenNhaXB = inputForm.getListItem().get(3).getSelection();

        int maVung = viTriVungBUS.getMaViTriVungByTen(tenVung);
        int maNXB = nhaXBBUS.getMaNXBByTen(tenNhaXB);

        String path = inputForm.getListItem().get(7).getPath();

        SachDTO sach = new SachDTO(ten, namXB, maVung, maNXB, path);
        if(sachBUS.insert(sach) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Thêm thành công");
            String[] row = {sach.getMaSach()+"", sach.getTenSach(), sach.getSoLuong() + "", sach.getGiaBan() + "", sach.getNamXB() + ""};
            sachPanel.getTable().addDataRow(row);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Thêm sách thất bại!");
            this.dispose();
        }
        //Insert khóa ngoại
        String listTL = this.inputForm.getListItem().get(4).getTextKNNN();
        String listTG = this.inputForm.getListItem().get(5).getTextKNNN();
        String listKM = this.inputForm.getListItem().get(6).getTextKNNN();
        InsertListPhanLoai(sach.getMaSach(), listTL);
        InsertListDanhMucTG(sach.getMaSach(), listTG);
        InsertListKM_Sach(sach.getMaSach(), listKM);
    }

    public void update(){
        int ma = Integer.parseInt(sachPanel.getTable().getCellData(rowSelected, 0));
        String ten = inputForm.getListItem().get(0).getText();
        int namXB = Integer.parseInt(inputForm.getListItem().get(1).getText());
        String tenVung = inputForm.getListItem().get(2).getSelection();
        String tenNhaXB = inputForm.getListItem().get(3).getSelection();

        int maVung = viTriVungBUS.getMaViTriVungByTen(tenVung);
        int maNXB = nhaXBBUS.getMaNXBByTen(tenNhaXB);

        String path = inputForm.getListItem().get(7).getPath();
        
        SachDTO sach = new SachDTO(ma, ten, namXB, maVung, maNXB, path);
        BigDecimal giaban = sachBUS.getInstanceByID(ma).getGiaBan();
        if(sachBUS.update(sach) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Sửa thành công");
            String[] row = {sach.getMaSach()+"", sach.getTenSach(), sach.getSoLuong() + "", FormatterUtil.formatNumberVN(giaban) + "", sach.getNamXB() + ""};
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
        UpdateListKM_SACH();

    }

    public void setInputForDetail(){
        inputForm.getListItem().get(0).getTextField().setEditable(false);
        inputForm.getListItem().get(1).getTextField().setEditable(false);
        inputForm.getListItem().get(2).getCombobox().setEnabled(false);
        inputForm.getListItem().get(3).getCombobox().setEnabled(false);
        inputForm.getListItem().get(4).setEditKNNN(false);
        inputForm.getListItem().get(5).setEditKNNN(false);
        inputForm.getListItem().get(6).setEditKNNN(false);
        inputForm.getListItem().get(7).setEdit(false);
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

    public void InsertListKM_Sach(int maSach, String x){
        String[] list = x.split(", ");
        if(list.length != 1){   //Có thể không có khuyến mãi (vì split chuỗi rỗng ra length = 1 :<<)
            for(String i : list){
                int ma = khuyenMaiBUS.getMaKhuyenMaiByTen(i);
                KM_SachDTO pl = new KM_SachDTO(ma, maSach); 
                kM_SachBUS.insert(pl);
            }
        }
    }

    //update cho so sánh
    public void UpdateListPhanloai(){
        //oldListTL
        int maSach = Integer.parseInt(sachPanel.getTable().getCellData(rowSelected, 0));
        String[] temp = inputForm.getListItem().get(4).getTextKNNN().split(", ");
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
        String[] temp = inputForm.getListItem().get(5).getTextKNNN().split(", ");
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

    public void UpdateListKM_SACH(){
        //oldListKM
        int maSach = Integer.parseInt(sachPanel.getTable().getCellData(rowSelected, 0));
        String[] temp = inputForm.getListItem().get(6).getTextKNNN().split(", ");
        ArrayList<Integer> newListKM = new ArrayList<>();
        for(int i = 0; i < temp.length; i++){
            newListKM.add(khuyenMaiBUS.getMaKhuyenMaiByTen(temp[i]));
        }
        //Thực hiện delete nếu cũ có mới không có
        for(int i : oldListKM){
            if(!newListKM.contains(i)){
                kM_SachBUS.delete(maSach, i);
            }
        }
        //Thực hiện insert nếu cũ không có mới có
        for(int i : newListKM){
            if(!oldListTL.contains(i)){
                KM_SachDTO kmSach = new KM_SachDTO(i, maSach);
                kM_SachBUS.insert(kmSach);
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
        for(int i : kM_SachBUS.getAllMaKMByMaSach(ma)){
            oldListKM.add(i);
        }
    }


    public boolean validation(){
        JTextField ten = inputForm.getListItem().get(0).getTextField();
        JTextField namXB = inputForm.getListItem().get(1).getTextField();
        String theLoai = inputForm.getListItem().get(4).getTextKNNN();
        String tacGia = inputForm.getListItem().get(5).getTextKNNN();
        String path = inputForm.getListItem().get(7).getPath();

        // JTextField input = inputForm.getListItem().get(0).getTextField();
        // if(Validate.isEmpty(input.getText())){
        //     JOptionPane.showMessageDialog(mainFrame, "Tên vùng không được để trống!","Thiếu thông tin",JOptionPane.WARNING_MESSAGE);
        //     input.requestFocusInWindow();
        //     return(false);
        // }

        if(Validate.isEmpty(ten.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Tên sách không được để trống!","",JOptionPane.WARNING_MESSAGE);
            ten.requestFocusInWindow();
            return(false);
        }
        if(Validate.isEmpty(namXB.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Năm xuất bản không được để trống!","",JOptionPane.WARNING_MESSAGE);
            namXB.requestFocusInWindow();
            return(false);
        }
        if(!Validate.isYear(namXB.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Năm xuất bản phải nhập đúng định dạng!","",JOptionPane.WARNING_MESSAGE);
            namXB.requestFocusInWindow();
            return(false);
        }
        if(Validate.isEmpty(theLoai)){
            JOptionPane.showMessageDialog(mainFrame, "Thể loại không được để trống!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        if(Validate.isEmpty(tacGia)){
            JOptionPane.showMessageDialog(mainFrame, "Tác giả không được để trống!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        if(Validate.isEmpty(path)){
            JOptionPane.showMessageDialog(mainFrame, "Vui lòng thêm ảnh!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        
        return(true);
    }
}
