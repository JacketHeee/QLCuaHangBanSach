package GUI.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
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
import DTO.KM_SachDTO;
import DTO.KhuyenMaiDTO;
import DTO.PhanLoaiDTO;
import DTO.SachDTO;
import GUI.MainFrame;
import GUI.component.CustomButton;
import GUI.component.InputForm;
import GUI.component.InputFormItem;
import GUI.forms.KhuyenMaiForm;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import resources.base.baseTheme;
import utils.DateCalculator;
import utils.FormatterUtil;
import utils.Validate;

public class KhuyenMaiDialog extends JDialog implements ActionListener{
    private KhuyenMaiForm khuyenMaiPanel;
    private MainFrame mainFrame;
    private JLabel label;
    private String type;
    private String[][] attributes;
    private InputForm inputForm;
    private KhuyenMaiBUS khuyenMaiBUS;
    private int rowSelected;
    private TheLoaiBUS theLoaiBUS;
    private TacGiaBUS tacGiaBUS;
    private SachBUS sachBUS;
    private NhaXBBUS nhaXBBUS;

    // ------------------
    String[] typeKM = {"Khuyến mãi theo hóa đơn","Khuyến mãi theo sách"};
    String[] typeGiamGia = {"Giảm theo %","Giảm theo giá trị cố định"}; 
    String[] giamTheoPTram = {"5%","10%","15%","20%","25%","30%","40%","50%","70%","90%"}; 
    String[] giamTheoTienCoDinh = {"5.000đ","10.000đ","15.000đ","20.000đ","25.000đ","30.000đ","50.000đ","70.000đ","150.000đ"};
    String[] dieuKienTheoSach = {"Thể loại","Sách","Tác giả","Nhà xuất bản"}; 
    String[] soTientoiThieu = {"0đ","50.000đ","99.000đ","150.000đ","200.000đ","300.000đ","500.000đ","1.000.000đ"};

    String[] listSach;
    String[] listTheLoai;
    String[] listTacGia;
    String[] listNXB;

    private ArrayList<Integer> oldListSach;


    
    public KhuyenMaiDialog(KhuyenMaiForm khuyenMaiPanel, String title, String function, String type, String[][] attributes, int... row){
        super(khuyenMaiPanel.getMainFrame(), title, true);
        this.khuyenMaiPanel = khuyenMaiPanel;
        this.mainFrame = this.khuyenMaiPanel.getMainFrame();
        this.type = type;
        this.attributes = attributes;
        inputForm = new InputForm(attributes);
        this.label = new JLabel("<html><strong><font size=+2>" + function + "</font></strong><html>");
        this.khuyenMaiBUS = khuyenMaiPanel.getKhuyenMaiBUS();
        if(row.length == 1){
            this.rowSelected = row[0];
        }

        theLoaiBUS = TheLoaiBUS.getInstance();
        tacGiaBUS = TacGiaBUS.getInstance();
        sachBUS = SachBUS.getInstance();
        nhaXBBUS = NhaXBBUS.getInstance();

        listSach = sachBUS.getAllTenSach();
        listTheLoai = theLoaiBUS.getAllTenTheLoai().toArray(new String[0]);
        listTacGia = tacGiaBUS.getAllTenTacGia().toArray(new String[0]);
        listNXB = nhaXBBUS.getAllTenNXB().toArray(new String[0]);

        this.oldListSach = new ArrayList<>();
        this.init();

        setLocationRelativeTo(null);
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
        this.setSize(new Dimension(500, originalSize + rowsize * attributes.length));
        this.add(panel, "grow");
        

        //Cài đặt lựa chọn cmbobox

        
        inputForm.getListItem().get(0).setListCombobox(typeKM);
        inputForm.hideInputItems(6,7);

        JComboBox combo = inputForm.getListItem().get(0).getCombobox();
        combo.addActionListener(e -> {
            String selected = (String)combo.getSelectedItem();
            if (selected.equals("Khuyến mãi theo hóa đơn")) {
                inputForm.hideInputItems(6,7);
            }
            else {
                inputForm.hideInputItems(8);
            }
        });

        // {"combobox","Loại khuyến mãi"},
        // {"textbox","Tên khuyến mãi"},
        // {"inputDateTime", "Ngày bắt đầu"},//làm dateTime
        // {"inputDateTime", "Ngày kết thúc"},//làm dateTime
        // {"combobox","Loại giảm giá"},
        // {"combobox", "Giá trị giảm"},
        // {"combobox","Theo"},
        // {"combobox","Những sách"},
        // {"combobox","Giá trị đơn hàng tối thiểu"}

        inputForm.getListItem().get(4).setListCombobox(typeGiamGia);
        
        JComboBox comboTypeGiamGia = inputForm.getListItem().get(4).getCombobox();
        

        inputForm.getListItem().get(5).setListCombobox(giamTheoPTram);
        comboTypeGiamGia.addActionListener(e -> {
            String selected = (String)comboTypeGiamGia.getSelectedItem();
            if (selected.equals("Giảm theo %")) {
                inputForm.getListItem().get(5).getCombobox().setModel(new DefaultComboBoxModel<>(giamTheoPTram));
            }
            else {
                inputForm.getListItem().get(5).getCombobox().setModel(new DefaultComboBoxModel<>(giamTheoTienCoDinh));
            }
        });

        
        inputForm.getListItem().get(6).setListCombobox(dieuKienTheoSach);

       
        inputForm.getListItem().get(7).setListCombobox(listTheLoai);
        JComboBox comboDieuKienTheoSach = inputForm.getListItem().get(6).getCombobox();
        comboDieuKienTheoSach.addActionListener(e -> {
            String selected = (String) comboDieuKienTheoSach.getSelectedItem();
            inputForm.getListItem().get(7).getLabel().setText("Những sách theo " + selected);
            switch (selected) {
                case "Thể loại":
                    inputForm.getListItem().get(7).getCombobox().setModel(new DefaultComboBoxModel<>(listTheLoai));                
                    break;
                case "Sách":
                    inputForm.getListItem().get(7).getCombobox().setModel(new DefaultComboBoxModel<>(listSach));
                    break;
                case "Tác giả":
                    inputForm.getListItem().get(7).getCombobox().setModel(new DefaultComboBoxModel<>(listTacGia));
                    break;
                case "Nhà xuất bản":
                    inputForm.getListItem().get(7).getCombobox().setModel(new DefaultComboBoxModel<>(listNXB));
                    break;
                default:
                    break;
            }
        });




        
        inputForm.getListItem().get(8).setListCombobox(soTientoiThieu);

        this.add(inputForm, "grow");

        setButton();
    }

    public void setButton(){
        if(type.equals("add")){
            // inputForm.getListItem().get()
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
    }

    public void setOldData(){
        int ma = Integer.parseInt(khuyenMaiPanel.getTable().getCellData(rowSelected, 0));
        KhuyenMaiDTO khuyenMai = khuyenMaiBUS.getInstanceByMa(ma);

        inputForm.getListItem().get(1).setText(khuyenMai.getTenKM());
        // inputForm.getListItem().get(1).setText(khuyenMai.getDieuKienGiam());
        // inputForm.getListItem().get(2).setText(khuyenMai.getGiaTriGiam() + "");
        inputForm.getListItem().get(2).setDateTime(khuyenMai.getNgayBatDau());
        inputForm.getListItem().get(3).setDateTime(khuyenMai.getNgayKetThuc());

        // {"Giảm theo %","Giảm theo giá trị cố định"};
        String giatrigiam = null; 
        if (khuyenMai.getGiaTriGiam().compareTo(BigDecimal.ONE)<0) {
            inputForm.getListItem().get(4).getCombobox().setSelectedItem("Giảm theo %");
            
            inputForm.getListItem().get(5).getCombobox().setModel(new DefaultComboBoxModel<>(giamTheoPTram));

            giatrigiam = FormatterUtil.formatAsPercent(khuyenMai.getGiaTriGiam());
            inputForm.getListItem().get(5).getCombobox().setSelectedItem(giatrigiam);
            System.out.println(giatrigiam);
        }
        else {
            inputForm.getListItem().get(4).getCombobox().setSelectedItem("Giảm theo giá trị cố định");

            inputForm.getListItem().get(5).getCombobox().setModel(new DefaultComboBoxModel<>(giamTheoTienCoDinh));

            giatrigiam = FormatterUtil.formatNumberKM(khuyenMai.getGiaTriGiam());
            inputForm.getListItem().get(5).getCombobox().setSelectedItem(giatrigiam);
            System.out.println(giatrigiam);
        }

        String[] dieukienapdung = khuyenMai.getDieuKienGiam().replaceAll("(Đơn\\shàng\\stối\\sthiểu:\\s)|(Sách\\stheo\\s)|(:\\s)", "#").split("#");
        int i = 1;
        for (String x : dieukienapdung) 
            System.out.println((i++) + x);

        System.out.println(dieukienapdung.length);

        if (dieukienapdung.length != 2) {
            // String[] typeKM = {"Khuyến mãi theo hóa đơn","Khuyến mãi theo sách"};
            inputForm.getListItem().get(0).getCombobox().setSelectedItem("Khuyến mãi theo sách");
            inputForm.hideInputItems(8);
            inputForm.getListItem().get(6).getCombobox().setSelectedItem(dieukienapdung[1]);

            String[] listSach = sachBUS.getAllTenSach();
            String[] listTheLoai = theLoaiBUS.getAllTenTheLoai().toArray(new String[0]);
            String[] listTacGia = tacGiaBUS.getAllTenTacGia().toArray(new String[0]);
            String[] listNXB = nhaXBBUS.getAllTenNXB().toArray(new String[0]);

            switch (dieukienapdung[1]) {
                case "Thể loại":
                    inputForm.getListItem().get(7).getCombobox().setModel(new DefaultComboBoxModel<>(listTheLoai));                
                    break;
                case "Sách":
                    inputForm.getListItem().get(7).getCombobox().setModel(new DefaultComboBoxModel<>(listSach));
                    break;
                case "Tác giả":
                    inputForm.getListItem().get(7).getCombobox().setModel(new DefaultComboBoxModel<>(listTacGia));
                    break;
                case "Nhà xuất bản":
                    inputForm.getListItem().get(7).getCombobox().setModel(new DefaultComboBoxModel<>(listNXB));
                    break;
                default:
                    break;
            }
            
            inputForm.getListItem().get(7).getCombobox().setSelectedItem(dieukienapdung[2]);
        }
        else {
            inputForm.getListItem().get(0).getCombobox().setSelectedItem("Khuyến mãi theo hóa đơn");

            inputForm.hideInputItems(6,7);
            
            inputForm.getListItem().get(8).getCombobox().setSelectedItem(dieukienapdung[1]);         
        }

        getOldList();
    }

    private void getOldList() {
        String  loaiKM =(String) inputForm.getListItem().get(0).getCombobox().getSelectedItem();
        
        if (loaiKM.equals("Khuyến mãi theo sách")) {
            System.out.println("con bo biet bay");
            int ma;
            String luachon = inputForm.getListItem().get(6).getCombobox().getSelectedItem() + "";
            String giatri = inputForm.getListItem().get(7).getCombobox().getSelectedItem() + "";

            // KM_SachBUS km_SachBUS = KM_SachBUS.getInstance();
            switch (luachon) {
                case "Thể loại":
                    ma = theLoaiBUS.getMaByTen(giatri);
                    PhanLoaiBUS phanLoaiBUS = PhanLoaiBUS.getInstance();
                    for (SachDTO x : sachBUS.getAll()) 
                        if (FormatterUtil.checkTonTai(phanLoaiBUS.getAllMaTheLoaiByMaSach(x.getMaSach()), ma)) {
                            // km_SachBUS.insert(new KM_SachDTO(kM.getMaKM(),x.getMaSach()));
                            oldListSach.add(x.getMaSach()); 
                        }
                    break;
                case "Sách":
                    for (SachDTO x : sachBUS.getAll()) 
                        if (x.getTenSach().equals(giatri)) {
                            // km_SachBUS.insert(new KM_SachDTO(kM.getMaKM(),x.getMaSach()));
                            oldListSach.add(x.getMaSach()); 
                        }
                    break;
                case "Tác giả":
                    ma = tacGiaBUS.getMaByTen(giatri);
                    DanhMuc_TGBUS danhMuc_TGBUS = DanhMuc_TGBUS.getInstance();
                    for (SachDTO x : sachBUS.getAll()) 
                        if (FormatterUtil.checkTonTai(danhMuc_TGBUS.getAllMaTacGiaByMaSach(x.getMaSach()), ma)) {
                            // km_SachBUS.insert(new KM_SachDTO(kM.getMaKM(),x.getMaSach()));
                            oldListSach.add(x.getMaSach()); 
                        }
                    break;
                case "Nhà xuất bản":
                    ma = nhaXBBUS.getMaNXBByTen(giatri);
                    for (SachDTO x : sachBUS.getAll()) 
                        if (x.getMaNXB() == ma) {
                            // km_SachBUS.insert(new KM_SachDTO(kM.getMaKM(),x.getMaSach()));
                            oldListSach.add(x.getMaSach()); 
                        }
                    break;
                default:
                    break;
            }
            
        }        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")){
            if(validation()){
                insert();
            }
            // insert();
        }
        if (e.getActionCommand().equals("update")){
            if(validation()){
                update();
            }

            // update();
        }
        else if(e.getActionCommand().equals("exit")){
            this.dispose();
        }
    }


    public void insert(){
        String ten = inputForm.getListItem().get(1).getText();
        LocalDateTime ngayBatDau = inputForm.getListItem().get(2).getDateTime();
        LocalDateTime ngayKetThuc = inputForm.getListItem().get(3).getDateTime();
        
        String giaTriGiamS = inputForm.getListItem().get(5).getCombobox().getSelectedItem() +"";
        String dKGiam = ""; 

        String  loaiKM =(String) inputForm.getListItem().get(0).getCombobox().getSelectedItem();

        String luachon = inputForm.getListItem().get(6).getCombobox().getSelectedItem() + "";
        String giatri = inputForm.getListItem().get(7).getCombobox().getSelectedItem() + "";

        if (loaiKM.equals("Khuyến mãi theo hóa đơn")) {
            dKGiam = "Đơn hàng tối thiểu: " + inputForm.getListItem().get(8).getCombobox().getSelectedItem();
        }
        else {
            dKGiam = "Sách theo " + luachon
            + ": " + giatri; 
        }

        BigDecimal giaTriGiam = null;
        // Chuyển kiểu dữ liệu
        if (giaTriGiamS.indexOf("%") != -1) {
            giaTriGiam = BigDecimal.valueOf(Integer.parseInt(giaTriGiamS.replaceAll("%", ""))*1.0/100);
        }
        else {
            giaTriGiam = BigDecimal.valueOf(Double.parseDouble(giaTriGiamS.replaceAll("[\\.đ]", "")));
        }

        KhuyenMaiDTO kM = new KhuyenMaiDTO(ten, dKGiam, giaTriGiam, ngayBatDau, ngayKetThuc);
        if(khuyenMaiBUS.insert(kM) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Thêm thành công");

            String[] row = {kM.getMaKM() + "",
                ten, 
                dKGiam, 
                giaTriGiamS + "", 
                new DateCalculator().formatDateTimeToDateS(ngayBatDau), 
                new DateCalculator().formatDateTimeToDateS(ngayKetThuc)};
            if (loaiKM.equals("Khuyến mãi theo sách")) {
                int ma;
                KM_SachBUS km_SachBUS = KM_SachBUS.getInstance();
                switch (luachon) {
                    case "Thể loại":
                        ma = theLoaiBUS.getMaByTen(giatri);
                        PhanLoaiBUS phanLoaiBUS = PhanLoaiBUS.getInstance();
                        for (SachDTO x : sachBUS.getAll()) 
                            if (FormatterUtil.checkTonTai(phanLoaiBUS.getAllMaTheLoaiByMaSach(x.getMaSach()), ma)) {
                                km_SachBUS.insert(new KM_SachDTO(kM.getMaKM(),x.getMaSach()));
                            }
                        break;
                    case "Sách":
                        for (SachDTO x : sachBUS.getAll()) 
                            if (x.getTenSach().equals(giatri)) {
                                km_SachBUS.insert(new KM_SachDTO(kM.getMaKM(),x.getMaSach()));
                            }
                        break;
                    case "Tác giả":
                        ma = tacGiaBUS.getMaByTen(giatri);
                        System.out.println(giatri + " + " + ma);
                        DanhMuc_TGBUS danhMuc_TGBUS = DanhMuc_TGBUS.getInstance();
                        for (SachDTO x : sachBUS.getAll()) 
                            if (FormatterUtil.checkTonTai(danhMuc_TGBUS.getAllMaTacGiaByMaSach(x.getMaSach()), ma)) {
                                km_SachBUS.insert(new KM_SachDTO(kM.getMaKM(),x.getMaSach()));
                            }
                        break;
                    case "Nhà xuất bản":
                        ma = nhaXBBUS.getMaNXBByTen(giatri);
                        System.out.println(giatri + " + " + ma);
                        for (SachDTO x : sachBUS.getAll()) 
                            if (x.getMaNXB() == ma) {
                                km_SachBUS.insert(new KM_SachDTO(kM.getMaKM(),x.getMaSach()));
                            }
                        break;
                    default:
                        break;
                }
                
            }
            khuyenMaiPanel.getTable().addDataRow(row);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Thêm thất bại!");
            this.dispose();
        }
    }

    public void update(){
        int ma = Integer.parseInt(khuyenMaiPanel.getTable().getCellData(rowSelected, 0));

        String ten = inputForm.getListItem().get(1).getText();
        LocalDateTime ngayBatDau = inputForm.getListItem().get(2).getDateTime();
        LocalDateTime ngayKetThuc = inputForm.getListItem().get(3).getDateTime();
        
        String giaTriGiamS = inputForm.getListItem().get(5).getCombobox().getSelectedItem() +"";
        String dKGiam = ""; 

        String  loaiKM =(String) inputForm.getListItem().get(0).getCombobox().getSelectedItem();

        String luachon = inputForm.getListItem().get(6).getCombobox().getSelectedItem() + "";
        String giatri = inputForm.getListItem().get(7).getCombobox().getSelectedItem() + "";

        if (loaiKM.equals("Khuyến mãi theo hóa đơn")) {
            dKGiam = "Đơn hàng tối thiểu: " + inputForm.getListItem().get(8).getCombobox().getSelectedItem();
        }
        else {
            dKGiam = "Sách theo " + luachon
            + ": " + giatri; 
        }

        BigDecimal giaTriGiam = null;
        // Chuyển kiểu dữ liệu
        if (giaTriGiamS.indexOf("%") != -1) {
            giaTriGiam = BigDecimal.valueOf(Integer.parseInt(giaTriGiamS.replaceAll("%", ""))*1.0/100);
        }
        else {
            giaTriGiam = BigDecimal.valueOf(Double.parseDouble(giaTriGiamS.replaceAll("[\\.đ]", "")));
        }

        //Chuyển kiểu dữ liệu
        // BigDecimal giaTriGiam = BigDecimal.valueOf(Double.parseDouble(giaTriGiamS));
        KhuyenMaiDTO kM = new KhuyenMaiDTO(ma, ten, dKGiam, giaTriGiam, ngayBatDau, ngayKetThuc);

        if(khuyenMaiBUS.update(kM) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Sửa thành công!");
            // String[] row = {kM.getMaKM() + "",
            // kM.getTenKM(), 
            // kM.getDieuKienGiam(), 
            // kM.getGiaTriGiam() + "", 
            // new DateCalculator().formatDateTimeToDateS(kM.getNgayBatDau()), 
            // new DateCalculator().formatDateTimeToDateS(kM.getNgayKetThuc())};

            String[] row = {ma +"",
                ten, 
                dKGiam, 
                giaTriGiamS + "", 
                new DateCalculator().formatDateTimeToDateS(ngayBatDau), 
                new DateCalculator().formatDateTimeToDateS(ngayKetThuc)};

                RemoveOldKMSach();

                if (loaiKM.equals("Khuyến mãi theo sách")) {
                    int maChoo; 
                    KM_SachBUS km_SachBUS = KM_SachBUS.getInstance();
                    switch (luachon) {
                        case "Thể loại":
                            maChoo = theLoaiBUS.getMaByTen(giatri);
                            PhanLoaiBUS phanLoaiBUS = PhanLoaiBUS.getInstance();
                            for (SachDTO x : sachBUS.getAll()) 
                                if (FormatterUtil.checkTonTai(phanLoaiBUS.getAllMaTheLoaiByMaSach(x.getMaSach()), maChoo)) {
                                    km_SachBUS.insert(new KM_SachDTO(kM.getMaKM(),x.getMaSach()));
                                }
                            break;
                        case "Sách":
                            for (SachDTO x : sachBUS.getAll()) 
                                if (x.getTenSach().equals(giatri)) {
                                    km_SachBUS.insert(new KM_SachDTO(kM.getMaKM(),x.getMaSach()));
                                }
                            break;
                        case "Tác giả":
                            maChoo = tacGiaBUS.getMaByTen(giatri);
                            DanhMuc_TGBUS danhMuc_TGBUS = DanhMuc_TGBUS.getInstance();
                            for (SachDTO x : sachBUS.getAll()) 
                                if (FormatterUtil.checkTonTai(danhMuc_TGBUS.getAllMaTacGiaByMaSach(x.getMaSach()), maChoo)) {
                                    km_SachBUS.insert(new KM_SachDTO(kM.getMaKM(),x.getMaSach()));
                                }
                            break;
                        case "Nhà xuất bản":
                            maChoo = nhaXBBUS.getMaNXBByTen(giatri);
                            for (SachDTO x : sachBUS.getAll()) 
                                if (x.getMaNXB() == maChoo) {
                                    km_SachBUS.insert(new KM_SachDTO(kM.getMaKM(),x.getMaSach()));
                                }
                            break;
                        default:
                            break;
                    }
                    
                }
    
            khuyenMaiPanel.getTable().setRowData(rowSelected, row);

            this.dispose();
        }
        else{this.dispose();
            JOptionPane.showMessageDialog(mainFrame, "Sửa thất bại!");
            this.dispose();
        }
    }

    //update cho so sánh
    public void RemoveOldKMSach(){
        //oldListTL
        int maKM = Integer.parseInt(khuyenMaiPanel.getTable().getCellData(rowSelected, 0));
        
        KM_SachBUS km_SachBUS = KM_SachBUS.getInstance();

        for(int i : oldListSach){
            km_SachBUS.delete(i, maKM);
        }

        //Thực hiện insert nếu cũ không có mới có
        
    }


    public boolean validation(){
        JTextField ten = inputForm.getListItem().get(1).getTextField();
        // JTextField dieuKienGiam = inputForm.getListItem().get(1).getTextField();
        // JTextField giaTriGiam = inputForm.getListItem().get(2).getTextField();
        String ngayBatDau = inputForm.getListItem().get(2).getDateTimeString();
        String ngayKetThuc = inputForm.getListItem().get(3).getDateTimeString();
        String thoiGianBatDau = inputForm.getListItem().get(2).getDateTimeTimeString(); //Validate thời gian
        String thoiGianKetThuc = inputForm.getListItem().get(3).getDateTimeTimeString();

        if(Validate.isEmpty(ten.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Tên khuyến mãi không được để trống!","",JOptionPane.WARNING_MESSAGE);
            ten.requestFocusInWindow();
            return(false);
        }
        // if(Validate.isEmpty(dieuKienGiam.getText())){
        //     JOptionPane.showMessageDialog(mainFrame, "Điều kiện giảm không được để trống!","",JOptionPane.WARNING_MESSAGE);
        //     dieuKienGiam.requestFocusInWindow();
        //     return(false);
        // }
        // if(Validate.isEmpty(giaTriGiam.getText())){
        //     JOptionPane.showMessageDialog(mainFrame, "Giá trị giảm không được để trống!","",JOptionPane.WARNING_MESSAGE);
        //     giaTriGiam.requestFocusInWindow();
        //     return(false);
        // }
        if(Validate.isEmpty(ngayBatDau)){
            JOptionPane.showMessageDialog(mainFrame, "Ngày bắt đầu không được để trống!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        if(!Validate.isDate(ngayBatDau)){
            JOptionPane.showMessageDialog(mainFrame, "Ngày bắt đầu phải nhập đúng định dạng!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        if(Validate.isEmpty(ngayKetThuc)){
            JOptionPane.showMessageDialog(mainFrame, "Ngày kết thúc không được để trống!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        if(!Validate.isDate(ngayKetThuc)){
            JOptionPane.showMessageDialog(mainFrame, "Ngày kết thúc phải nhập đúng định dạng!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        if(ngayBatDau.equals(ngayKetThuc)){
            if(!Validate.isStartTimeAndEndTime(thoiGianBatDau, thoiGianKetThuc)){
                JOptionPane.showMessageDialog(mainFrame, "Thời gian bắt đầu phải trước thời gian kết thúc","",JOptionPane.WARNING_MESSAGE);
                return(false);
            }
            return(true);
        }
        if(!Validate.isStartDateAndEndDate(ngayBatDau, ngayKetThuc)){
            JOptionPane.showMessageDialog(mainFrame, "Ngày bắt đầu phải trước ngày kết thúc!","",JOptionPane.WARNING_MESSAGE);
            return(false);
        }
        return(true);
    }
}
