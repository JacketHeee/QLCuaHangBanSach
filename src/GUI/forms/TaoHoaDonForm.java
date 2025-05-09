package GUI.forms;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import utils.ExcelReader;
import utils.FormatterUtil;
import utils.UIUtils;
import utils.Validate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import GUI.component.CustomTextFieldSL;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.CT_HoaDonBUS;
import BUS.ChiTietQuyenBUS;
import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.KhuyenMaiBUS;
import BUS.NhanVienBUS;
import BUS.PhuongThucTTBUS;
import BUS.SachBUS;
import DTO.CT_HoaDonDTO;
import DTO.ChiTietQuyenDTO;
import DTO.HoaDonDTO;
import DTO.KhuyenMaiDTO;
import DTO.NhanVienDTO;
import DTO.SachDTO;
import DTO.TaiKhoanDTO;
import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomButton;
import GUI.component.CustomTable;
import GUI.component.InvoiceTable;
import GUI.component.TableActionListener;
import GUI.component.search.TextFieldListSach;
import GUI.dialog.AddHoaDonDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

public class TaoHoaDonForm extends JPanel implements ActionListener, TableActionListener{
    private int id = 9;
    private String[] listKH; 
    private String[] listKM;
    private ArrayList<KhuyenMaiDTO> arrayListKM;
    private String[] listPTTT;
    private CustomButton buttonSave;
    private CustomButton buttonCancel;
    private CustomTable table;
    private MainFrame mainFrame;
    private TaiKhoanDTO taiKhoan;
    private NhanVienDTO nhanVienDTO;
    private ArrayList<String> listAction;
    private ChiTietQuyenBUS chiTietQuyenBUS;
    private KhachHangBUS khachHangBUS;
    private KhuyenMaiBUS khuyenMaiBUS;
    private PhuongThucTTBUS phuongThucTTBUS;
    private NhanVienBUS nhanVienBUS;
    private HoaDonBUS hoaDonBUS;
    private CT_HoaDonBUS ct_HoaDonBUS;
    private BigDecimal tongTienHoaDon = BigDecimal.ZERO;
    private JLabel lblTongTienHoaDon;
    private JLabel lblTongTienHang;
    private JLabel lblTongThanhToan;
    private JLabel lblTienKM;
    private JLabel lblTienThoi;
    private JTextField textFieldTienKhachDua;
    private BigDecimal tongThanhToan;
    private JComboBox<String> comboboxKM;
    private JComboBox<String> comboboxPTTT;
    private JComboBox<String> comboboxKH;
    private String[] headers = new String[] {"Mã sách","Tên sách","Số lượng","Giá bán(đ)","Thành tiền(đ)"};
    private String[] headerType = {"inputMa","label","inputNumber","label","label"};
    private String maHD;
    private GetDataCallBack getDataCallBack;
    private HoaDonDTO hoaDon;
    private JLabel lblNV;
    private JLabel lblMaHD;
    private JLabel lblNgay;
    private String type;
    private SachBUS sachBUS;
    private AddHoaDonDialog addHoaDonDialog;
    private Runnable runna;

    public TaoHoaDonForm(MainFrame mainFrame, Runnable runna) {
        this.runna = runna;
        this.mainFrame = mainFrame;
        this.taiKhoan = mainFrame.getTaiKhoan();
        this.nhanVienDTO = new NhanVienDTO();
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();               
        this.listAction = getListAction();
        this.tongTienHoaDon = new BigDecimal(0);
        this.lblTongTienHang = new JLabel();
        this.lblTienKM = new JLabel();
        this.textFieldTienKhachDua = new JTextField();
        this.lblTienThoi = new JLabel();
        this.tongThanhToan = new BigDecimal(0);
        this.khachHangBUS = KhachHangBUS.getInstance();
        this.khuyenMaiBUS = KhuyenMaiBUS.getInstance();
        this.phuongThucTTBUS = PhuongThucTTBUS.getInstance();
        this.nhanVienBUS = NhanVienBUS.getInstance();
        this.hoaDonBUS = HoaDonBUS.getInstance();
        this.ct_HoaDonBUS = CT_HoaDonBUS.getInstance();
        this.sachBUS = SachBUS.getInstance();
        this.type = "Thêm";
        this.getListKH();
        this.getListKM();
        this.getPTTT();
        this.getNV();
        this.getMaHD();
        init();
    }
    //Cho chi tiết
    public TaoHoaDonForm(MainFrame mainFrame, HoaDonDTO hoaDon, String type, Runnable runna) {
        this.runna = runna;
        this.mainFrame = mainFrame;
        this.taiKhoan = mainFrame.getTaiKhoan();
        this.nhanVienDTO = new NhanVienDTO();
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();               
        this.listAction = getListAction();
        this.tongTienHoaDon = new BigDecimal(0);
        this.lblTongTienHang = new JLabel();
        this.lblTienKM = new JLabel();
        this.textFieldTienKhachDua = new JTextField();
        this.lblTienThoi = new JLabel();
        this.tongThanhToan = new BigDecimal(0);
        this.khachHangBUS = KhachHangBUS.getInstance();
        this.khuyenMaiBUS = KhuyenMaiBUS.getInstance();
        this.phuongThucTTBUS = PhuongThucTTBUS.getInstance();
        this.nhanVienBUS = NhanVienBUS.getInstance();
        this.hoaDonBUS = HoaDonBUS.getInstance();
        this.ct_HoaDonBUS = CT_HoaDonBUS.getInstance();
        this.sachBUS = SachBUS.getInstance();
        this.hoaDon = hoaDon;
        this.type = type;
        this.getListKH();
        this.getListKM();
        this.getPTTT();
        this.getNV();
        this.getMaHD();
        init();
    }

    // public TaoHoaDonForm(MainFrame mainFrame,AddHoaDonDialog addHoaDonDialog) {
    //     this(mainFrame);
    //     this.addHoaDonDialog = addHoaDonDialog;
    // }
    
    //PDF
    // public TaoHoaDonForm(MainFrame mainFrame, HoaDonDTO hoaDon, String type, AddHoaDonDialog addHoaDonDialog) {
    //     this(mainFrame, hoaDon, type);
    //     this.addHoaDonDialog = addHoaDonDialog;
    // }
    // public TaoHoaDonForm(MainFrame mainFrame,AddHoaDonDialog addHoaDonDialog) {
    //     this(mainFrame);
    //     this.addHoaDonDialog = addHoaDonDialog;
    // }
    
    private void init() {
        setLayout(new MigLayout("insets 0,wrap 1, gap 10"));

        if(this.type.equals("detail")){
            add(new JLabel("<html><b><font size='+2'>CHI TIẾT HÓA ĐƠN</font></b><html>"),"gaptop 20, al center,pushx");
        }
        else{
            add(new JLabel("<html><b><font size='+2'>TẠO HÓA ĐƠN MỚI</font></b><html>"),"gaptop 20, al center,pushx");
        }
        add(getThongtin(),"pushx, growx");
        add(getChiTietPhieuNhap(),"push, grow");
        add(panelKhuyenMai(),"pushx, growx");
        add(panelPTTT(),"pushx, growx");
        add(panelThongTinThanhToan(),"pushx, growx");
        if (type.equals("detail")) {
            add(getPanelActionDetail(), "pushx, growx");
            addBtnListenerDetail();
        } else {
            add(getPanelAction(), "pushx, growx");
            addBtnListener();
        }

        setStatusCombobox();
        setStatusTextField();
        //detail
        if(type.equals("detail")){
            setDetail();
        }
    }

    public ArrayList<String> getListAction(){
        ArrayList<String> result = new ArrayList<>(); 
        int maNQ = taiKhoan.getMaRole();
        ArrayList<ChiTietQuyenDTO> listCTQ = this.chiTietQuyenBUS.getListChiTietQuyenByMaRoleMaCN(maNQ, id);
        for(ChiTietQuyenDTO i : listCTQ){
            result.add(i.getHanhDong());
        }
        return(result);
    }

    private JPanel getThongtin() {
        JPanel panel = getPanel("Thông tin chung");
        panel.setLayout(new MigLayout("","[][]","[][]"));
        
        lblNV = new JLabel("<html><font size='+1'><b>Nhân viên: </b>"+nhanVienDTO.getHoTen()+"</font></html>");
        panel.add(lblNV,"pushx,growx");
        lblMaHD = new JLabel("<html><font size='+1'><b>Mã hóa đơn: </b> "+ maHD +"</font></html>");
        panel.add(lblMaHD,"pushx,growx,wrap");
        panel.add(getPanelNhaCungCap(),"pushx,growx");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String day = sdf.format(new Date());

        lblNgay = new JLabel("<html><font size='+1'><b>Ngày bán: </b>"+day+"</font></html>");
        panel.add(lblNgay,"pushx,growx");

        return panel; 
    }

    
    private JPanel getPanelNhaCungCap() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        panel.add(new JLabel("<html><font size='+1'><b>Khách hàng: </b></font></html>"));

        comboboxKH = new JComboBox<>(listKH);

        comboboxKH.setFont(new Font(comboboxKH.getFont().getName(),Font.PLAIN,16));
        comboboxKH.putClientProperty(FlatClientProperties.STYLE, "focusWidth: 0; innerFocusWidth: 0");
        panel.add(comboboxKH);
        return panel;
    }

    String[][]actions = {
        {"remove.svg","remove"}
    };

    private JPanel getChiTietPhieuNhap() {
        JPanel panel = getPanel("Chi tiết hóa đơn");
        panel.setLayout(new MigLayout());
        //Cài đặt bảng chi tiết phiếu nhập
        table = new InvoiceTable(
            null
            , actions
            ,new InvoiceTable.DataForTable(){
                @Override
                public String[] getDataForTable(SachDTO sach, int soLuong) {    //Truyền vào một hàm cho InvoiceTable
                    String[] result;                                            //Khi invoiceTable đã có dữ liệu sachDTO
                    BigDecimal tongGia = hoaDonBUS.tinhTongGia(sach, soLuong);            //Cho sử dụng cả hóa đơn và phiếu nhập
                    ArrayList<String> list = new ArrayList<>();
                        list.add(sach.getMaSach() + "");
                        list.add(sach.getTenSach());
                        list.add(soLuong + "");
                        list.add(FormatterUtil.formatNumber(sach.getGiaBan()) + "");
                        list.add(FormatterUtil.formatNumber(tongGia)+"");
                    ;
                    result = list.toArray(new String[0]);
                    return(result);
                }   
            }
            , new InvoiceTable.TinhTongGia(){
                @Override
                public void updateTongGia(int row) {    //Truyền vào cho invoice table sử dụng để update tổng tiền ctsp khi có thay đổi
                    updateTongGiaBan(row);
                }
            }
            , new InvoiceTable.TinhTongGiaChungTu(){    //Được callBack mỗi khi thêm mới sách/ thay đổi số lượng
                @Override
                public void updateTongGiaChungTu(){
                    updateTongTienHoaDon();
                    updateListKM();
                    updateTTTT();
                    updateStatusCombobox();
                    updateTienKhuyenMai();
                    updateTienKhachDua();
                    updateStatusTextFieldTienTra();
                    // updateStatusbtnThem();
                    // updateTienKhachDua();
                }
            }
            ,headers
            ,headerType,
            90
        );
        panel.add(table,"push,grow,wrap");
        table.setActionListener(this);
        if (!type.equals("detail")) {
           table.addDataRow(null);
        }

        if(!type.equals("detail")){
            panel.add(panelActionOnTable(),"pushx,growx,wrap");
        }
        panel.add(getTongTien(),"pushx,growx");
        return panel;
    }

    private void updateListKM() {
        ArrayList<KhuyenMaiDTO> list = new ArrayList<>();
        
        // Lấy danh sách khuyến mãi theo mã sách
        Map<Integer, List<JComponent>> rowLabels = table.getRowLabels();
        for(Map.Entry<Integer, List<JComponent>> i : table.getRowLabels().entrySet()){
            int row = i.getKey();
            int maSach = Integer.parseInt(getTextFieldMaSach(row, rowLabels).getText());

            ArrayList<KhuyenMaiDTO> hihi =  khuyenMaiBUS.getListKMByIdSachEnable(maSach);

            for (KhuyenMaiDTO x : hihi) {
                boolean isTonTai = false;
                for (KhuyenMaiDTO y : list) 
                    if (x.getMaKM() == y.getMaKM()) {
                        isTonTai = true; 
                        break;
                    }
                if (!isTonTai)
                    list.add(x);
            }
        }

        // System.out.println("---------------------------------------");
        // Lấy danh sách khuyến mãi theo hóa đơn
        for (KhuyenMaiDTO x : khuyenMaiBUS.getAllKhuyenMaiEnable()) {
            String[] dieukienapdung = x.getDieuKienGiam().replaceAll("(Đơn\\shàng\\stối\\sthiểu:\\s)|(Sách\\stheo\\s)|(:\\s)", "#").split("#");

            if (dieukienapdung.length == 2) { //Khuyến mãi theo hóa đơn
                BigDecimal giaTriToiThieu = FormatterUtil.getBigDicimalByString(dieukienapdung[1]); 
                // System.out.println(giaTriToiThieu + " / " + tongTienHoaDon);
                if (tongTienHoaDon.compareTo(giaTriToiThieu) >=0) {
                    list.add(x);
                }
            }
            else {

            }
        }
        // System.out.println("---------------------------------------");
        // // debug
        // for (KhuyenMaiDTO x : list) {
        //     System.out.println(x.getTenKM());
        // } 
        // System.out.println("---------------------------------------");
        arrayListKM = list;

        listKM = new String[list.size()+1];
        listKM[0] = "Không dùng";

        int index = 1;
        for (KhuyenMaiDTO x : list) {
            listKM[index++] = x.getTenKM();
        }
 
        comboboxKM.setModel(new DefaultComboBoxModel<>(listKM));

    }

    private ButtonAction butAddData;
    private ButtonAction butImportData;

    private JPanel panelActionOnTable() {
        JPanel panel = new JPanel(new MigLayout("al left"));
        butAddData = new ButtonAction("Thêm", "add.svg", "add");
        butAddData.setActionCommand("addRowData");
        butAddData.addActionListener(this);
        panel.add(butAddData);
        butImportData = new ButtonAction("Import Excel", "importExcel.svg", "exportExcel");
        butImportData.setActionCommand("importRowData");
        butImportData.addActionListener(this);
        panel.add(butImportData);
        return panel;
    }

    private JPanel getTongTien() {
        JPanel panel = new JPanel(new MigLayout("al right"));
        String template = "<html><font size='+1'><b>Tổng tiền: </b> %s</font><html>";
        lblTongTienHoaDon = new JLabel(String.format(template, FormatterUtil.formatNumber(tongTienHoaDon)));
        panel.add(lblTongTienHoaDon);
        return panel;
    }

    private JPanel panelKhuyenMai() {
        JPanel panel = getPanel("Khuyến mãi");
        panel.setLayout(new MigLayout("al center center "));
        panel.add(new JLabel("<html><font size='+1'><b>Chọn khuyến mãi</b></font></html>"));
        panel.add(new JLabel(),"pushx");
        comboboxKM = new JComboBox<>();
        panel.add(comboboxKM);
        return panel;
    }

    private JPanel panelPTTT() {
        JPanel panel = getPanel("Phương thức thanh toán");
        panel.setLayout(new MigLayout("al  center center"));
        panel.add(new JLabel("<html><font size='+1'><b>Chọn phương thức thanh toán</b></font></html>"));
        panel.add(new JLabel(),"pushx");
        comboboxPTTT = new JComboBox<>(listPTTT);
        panel.add(comboboxPTTT);
        return panel;
    }

    private JPanel panelThongTinThanhToan() {
        JPanel panel = new JPanel(new MigLayout("al right"));

        panel.add(new JLabel("Tổng tiền hàng(đ):"),"sg 1");
        lblTongTienHang.setText("0đ");
        panel.add(this.lblTongTienHang,"sg 2,al right,wrap");
        panel.add(new JLabel("Khuyến mãi(đ):"),"sg 1");

        lblTienKM.setText("0");;
        panel.add(lblTienKM,"sg 2,al right,wrap");
        panel.add(new JLabel("<html><font size='+1'><b>Tổng thanh toán(đ): </b></font></html>"),"sg 1");

        String template = "<html><font size='+1'><b>'%s'</b></font></html>";
        lblTongThanhToan = new JLabel(String.format(template, FormatterUtil.formatNumber(tongThanhToan) + ""));

        panel.add(lblTongThanhToan,"sg 2,al right,wrap");
        panel.add(new JLabel("Tiền khách đưa(đ):"),"sg 1");

        panel.add(textFieldTienKhachDua,"sg 2,grow,wrap");

        panel.add(new JLabel("Tiền trả lại khách(đ):"),"sg 1");

        lblTienThoi.setText("0");

        panel.add(lblTienThoi,"sg 2,al right");
        return panel;
    }

    
    private JPanel getPanelAction() {
        JPanel panel = new JPanel(new MigLayout("gap 10,al right"));
        buttonSave = new CustomButton("Lưu");
        buttonSave.setBackground(Color.decode("#4882FF"));
        buttonSave.setBackground(Color.white);    
        buttonCancel = new CustomButton("Hủy");
        // buttonCancel.cssCancelButton();
        panel.add(buttonSave,"sg 1");
        panel.add(buttonCancel,"sg 1");
        return panel;
    }

    //PDF
    private JPanel getPanelActionDetail() {
        JPanel panel = new JPanel(new MigLayout("gap 10, al right"));
        buttonSave = new CustomButton("Xuất PDF");
        buttonSave.setBackground(Color.decode("#4CAF50"));
        buttonCancel = new CustomButton("Hủy");
    
        panel.add(buttonSave, "sg 1");
        panel.add(buttonCancel, "sg 1");
        return panel;
    }
    
    private JPanel getPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;   
    }

    public void addBtnListener(){
        //btn Tạo hóa đơn
        buttonSave.setActionCommand("btnLuu");
        buttonSave.addActionListener(this);
        //cbx Khuyến Mãi
        comboboxKM.setActionCommand("comboboxKM");
        comboboxKM.addActionListener(this);
        //textField tiền khách đưa
        textFieldTienKhachDua.setActionCommand("textFieldKD");
        setTextFieldKDListener();
    }

    public void addBtnListenerDetail() {
        buttonSave.setActionCommand("btnExportPDF");
        buttonSave.addActionListener(this);
        buttonCancel.setActionCommand("btnCancelDetail");
        buttonCancel.addActionListener(this);
    }    
    /////////////////////////////////////////////////////////////////////////// Xử Lý Table

    @Override
    public void actionPerformed(ActionEvent e) {

        String ms = e.getActionCommand();

        switch (ms) {
            case "addRowData":
                table.addDataRow(null);
                setStatusBtnThem(false);
                break;
            case "btnLuu":
                TaoHoaDon();
                break;
            case "comboboxKM":
                updateTienKhuyenMai();
                updateTongTienHoaDon();
                updateTTTT();
                updateTienKhachDua();
                break;
            case "btnExportPDF":
                exportPDF();
                break;
            case "btnCancelDetail":
                closeDetail();
                break;
            case "importRowData":
                importExcelForTable();
            break;
            default:
                break;
        }

        // TODO Auto-generated method stub
        
    }

    private void importExcelForTable() {
        ArrayList<String[]> listData = ExcelReader.openFile(3);
        // table.removeRow(1);
        int index = table.getRowLabels().size()+1;
        for (String[] x : listData) {
            SachDTO sach = sachBUS.getInstanceByID(Integer.parseInt(x[0])); 
            int soLuong = Integer.parseInt(x[2]);
            BigDecimal giaBan = sach.getGiaBan();
            BigDecimal tongGia = giaBan.multiply(new BigDecimal(soLuong));
            ArrayList<String> list = new ArrayList<>();
                        list.add(sach.getMaSach() + "");
                        list.add(sach.getTenSach());
                        list.add(soLuong + "");
                        list.add(FormatterUtil.formatNumber(giaBan) + "");
                        list.add(FormatterUtil.formatNumber(tongGia) + "");
                    ;
            String[] rowData = list.toArray(new String[0]);

            table.addDataRow(rowData);
            updateTongGiaBan(index++);
            updateTongTienHoaDon();
            updateListKM();
            updateTTTT();
            updateStatusCombobox();
            updateTienKhuyenMai();
            updateTienKhachDua();
            updateStatusTextFieldTienTra();
        }
    }

    //gọi callback để đóng dialog hóa đơn 
    public void closeDialog(Runnable callback) {
        callback.run();
    }
    //set text field tiền khách đưa
    public void setTextFieldKDListener(){
        this.textFieldTienKhachDua.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
                updateTienThoi();
            }
        });
    }

    @Override
    public void onActionPerformed(String actionId, int row) {
        switch (actionId) {
            case "remove":
                // Logic xóa cho form này
                int choose = UIUtils.messageRemove("Bạn thực sự muốn xóa?");
                if (choose == 0) {
                    table.removeRow(row);
                    Notifications.getInstance().setJFrame(mainFrame);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Xóa thành công!");
                    updateTongTienHoaDon();
                    updateListKM();
                    updateTTTT();
                    updateTienKhuyenMai();
                    updateTienKhachDua();
                    updateStatusCombobox();
                    updateStatusTextFieldTienTra();
                    // updateStatusbtnThem();
                }

                break;
            default:
                System.out.println("Unknown action: " + actionId);
                break;
        }
    }

    //Cài đặt không cho chọn khi chưa có sản phẩm
    public void setStatusCombobox(){
        if(listSPIsEmpty()){
            comboboxPTTT.setEnabled(false);
            comboboxKM.setEnabled(false);
        }
        else {
            comboboxPTTT.setEnabled(true);
            comboboxKM.setEnabled(true);
        }
    }

    public void setStatusTextField(){
        if(listSPIsEmpty()){
            this.textFieldTienKhachDua.setEditable(false);
        }
        else {
            this.textFieldTienKhachDua.setEditable(true);
        }
    }

    // public BigDecimal tinhTongGia(SachDTO sach, int soLuong){
    //     BigDecimal result = (sach.getGiaBan()).multiply(BigDecimal.valueOf(soLuong));
    //     return(result);
    // }

    //update cột thành tiền(đ) của một chi tiết sản phẩm
    public void updateTongGiaBan(int row){
        Map<Integer, List<JComponent>> rowLabels = this.table.getRowLabels();
        JLabel labelTT = getLabelTongTien(row, rowLabels);
        JLabel labelGB = getLabelGiaban(row, rowLabels);
        CustomTextFieldSL textFieldSL = getTextFieldSL(row, rowLabels); 

        int soLuong = Integer.parseInt(textFieldSL.getText());
        BigDecimal giaBan = FormatterUtil.getBigDicimalByString(labelGB.getText());
        BigDecimal tongGia = hoaDonBUS.tinhTongGia(giaBan, soLuong);

        labelTT.setText(FormatterUtil.formatNumber(tongGia) + "");
    }

    ///////////////////////////Các hàm get Component trong Map 
    public JLabel getLabelTongTien(int row, Map<Integer, List<JComponent>> rowLabels){
        JLabel label = null;
        JComponent cpn = rowLabels.get(row).get(4);
        JPanel panel = (JPanel)cpn;
        label = (JLabel)panel.getComponent(0);
        return(label);
    }

    public JLabel getLabelGiaban(int row, Map<Integer, List<JComponent>> rowLabels){
        JLabel label = null;
        JComponent cpn = rowLabels.get(row).get(3);
        JPanel panel = (JPanel)cpn;
        label = (JLabel)panel.getComponent(0);
        return(label);
    }

    public CustomTextFieldSL getTextFieldSL(int row, Map<Integer, List<JComponent>> rowLabels){
        CustomTextFieldSL textField = null;
        JComponent cpn = rowLabels.get(row).get(2);
        JPanel panel = (JPanel)cpn;
        textField = (CustomTextFieldSL)panel.getComponent(0);
        return(textField);
    }

    public TextFieldListSach getTextFieldMaSach(int row, Map<Integer, List<JComponent>> rowLabels){
        TextFieldListSach textField = null;
        JComponent cpn = rowLabels.get(row).get(0);
        JPanel panel = (JPanel)cpn;
        textField = (TextFieldListSach)panel.getComponent(0);
        return(textField);
    }
    ///////////////////////////Các hàm get Component trong Map 


    //Hàm này được gọi mỗi khi thêm sách mới; thay đổi cbx khuyến mãi; xóa
    public void updateTongTienHoaDon(){ //Mỗi lần thêm phải tính lại hết
        Map<Integer, List<JComponent>> rowLabels = table.getRowLabels();
        int index = 1;
        BigDecimal result = new BigDecimal(0);
        for(Map.Entry<Integer, List<JComponent>> entry : rowLabels.entrySet()){

            JLabel lblTongTienSach = getLabelTongTien(index, rowLabels);
            BigDecimal tongTienSach = FormatterUtil.getBigDicimalByString(lblTongTienSach.getText());
            result = result.add(tongTienSach);
            index++;
        }

        //set Tổng tiền hóa đơn để tính toán
        this.tongTienHoaDon = result;
        //Set tổng tiền ở trên
        setTextLblTongTienHoaDon(FormatterUtil.formatNumber(result) + "");
        //Set tổng tiền ở dưới
        lblTongTienHang.setText(FormatterUtil.formatNumber(result) + "");

    }


    /////////////////////////////////////////////////////////////////// Các dữ liệu cần update khi thêm sách/ thay đổi số lượng/ thay đổi cbx KM, xóa
    //Update tiền thanh toán (sau khuyến mãi)
    public void updateTTTT(){
        setTextTongTienThanhToan(this.tongTienHoaDon + "");
    }
    //Update trạng thái combobox
    public void updateStatusCombobox(){
        setStatusCombobox();
    }
    //Update trạng thái text tiền khách trả
    public void updateStatusTextFieldTienTra(){
        setStatusTextField();
    }
    //Update ô tiền khuyến mãi
    public void updateTienKhuyenMai(){
        lblTienKM.setText(FormatterUtil.formatNumber(getTienKM()) + "");
    }
    //Mở khóa nút thêm
    public void updateStatusbtnThem(){        
        // setStatusBtnThem(true);
    }

    public void updateTienKhachDua(){
        this.textFieldTienKhachDua.setText(this.tongThanhToan+ "");
        textFieldTienKhachDua.repaint();
        textFieldTienKhachDua.revalidate();
    }

    /////////////////////////////////////////////////////////////////// Các dữ liệu cần update khi thêm sách/ thay đổi số lượng/ thay đổi cbx KM

    /////////////////////////////////////////////////////////////////// Các setter label, textField, các hàm tính toán
    //setText để giữ nguyên font chữ
    public void setTextLblTongTienHoaDon(String i){
        String template = "<html><font size='+1'><b>Tổng tiền: </b> %s</font><html>"; // đổi để lấy font rõ hơn
        lblTongTienHoaDon.setText(String.format(template, i));
    }

    public void setTextTongTienThanhToan(String tongTienHoaDon){
        // TongTienThanhToan = TongTienHoaDon - KM
        // TongTienThanhToan = TongTienHoaDon*%KM
        String template = "<html><font size='+1'><b>'%s'</b></font></html>";
        BigDecimal giaTienHoaDon = BigDecimal.valueOf(Double.parseDouble(tongTienHoaDon));
        //Tính toán khuyến mãi ở đây
        // String tenKM = (String)comboboxKM.getSelectedItem();
        int select = comboboxKM.getSelectedIndex();
        BigDecimal giatrigiam = (select !=0) ? arrayListKM.get(select-1).getGiaTriGiam() : BigDecimal.ZERO;

        BigDecimal khuyenMai = (giatrigiam.compareTo(BigDecimal.ONE) <0) ? giaTienHoaDon.multiply(giatrigiam): giatrigiam;

        this.tongThanhToan = giaTienHoaDon.subtract(khuyenMai);

        lblTongThanhToan.setText(String.format(template, FormatterUtil.formatNumber(tongThanhToan)));
    }

    public BigDecimal getTienKM(){
        int selectIndex = comboboxKM.getSelectedIndex();
        BigDecimal giaTriGiam = (selectIndex != 0) ? arrayListKM.get(selectIndex-1).getGiaTriGiam() : BigDecimal.ZERO;
        if (giaTriGiam.compareTo(BigDecimal.ONE) <0) 
            return giaTriGiam.multiply(tongTienHoaDon);
        return giaTriGiam;
    }


    public void updateTienThoi(){
        if(getTienThoi().compareTo(new BigDecimal(0)) < 0){
            lblTienThoi.setText("Chưa đủ tiền");
        }
        else{
            lblTienThoi.setText(FormatterUtil.formatNumber(getTienThoi()) + "");
        }
    }

    public BigDecimal getTienThoi(){
        //ô text chưa nhập
        if(Validate.isEmpty(textFieldTienKhachDua.getText())){
            return(new BigDecimal(-1));
        }
        BigDecimal tienKhachDua = BigDecimal.valueOf(Double.parseDouble(textFieldTienKhachDua.getText())); 
        BigDecimal tongTien = this.tongThanhToan;
        // return(tienKhachDua.subtract(tongTien));
        return(hoaDonBUS.getTienThoi(tienKhachDua, tongTien));
    }
    //Xử lý nút thêm không cho thêm mới khi chưa điền thông tin
    public void setStatusBtnThem(boolean i){
        // this.butAddData.setEnabled(i);
    }

    /////////////////////////////////////////////////////////////////// Các setter label, textField, các hàm tính toán

    /////////////////////////////////////////////////////////////////////////// Xử Lý Table
    
    /////////////////////////////////////////////////////////////////////////// Add hóa đơn

    public void TaoHoaDon(){
        //Thuộc tính: maHD, ngayBan, tongTien, maTK, maPT, maKM, maKH
        //Khóa ngoại NN: Sach
        if(listSPIsEmpty()){  // đã check
            JOptionPane.showMessageDialog(mainFrame, "Chưa có sản phẩm nào được thêm!");
            return;
        }

        // if () // Vượt quá số lượng

        LocalDateTime ngayBan = LocalDateTime.now();
        // ngayBan = ngayBan.plusDays(1);

        BigDecimal tongTien = this.tongThanhToan;
        int maTK = this.taiKhoan.getMaTK();
        int maPT = phuongThucTTBUS.getMaPhuongThucTTByTen((String)comboboxPTTT.getSelectedItem());

        int maKM;
        int maKH;
        if(comboboxKM.getSelectedIndex() != 0){
            maKM = (arrayListKM.get(comboboxKM.getSelectedIndex() - 1).getMaKM());
        }
        else{
            maKM = 1;//không có khuyến mãi
        }

        if(!comboboxKH.getSelectedItem().equals("Anonymous")){
            maKH = khachHangBUS.getMaKhachHangByTen((String)comboboxKH.getSelectedItem());
        }
        else{
            maKH = 1;
        }

        // System.out.println(ngayBan.toString() + " " + tongTien + " " + maTK + " "+ maPT + " "+ maKM + " "+ maKH + "");

        HoaDonDTO hoaDon = new HoaDonDTO(ngayBan, tongTien, maTK, maPT, maKM, maKH);
        if(hoaDonBUS.insert(hoaDon) != 0){
            // JOptionPane.showMessageDialog(mainFrame, "Thêm hóa đơn thành công");
            insertSach();   //thêm khóa ngoại nhiều nhiều
            // Cập nhật số lượng sách
            updateCountOfBook();
            getDataCallBack.setData(hoaDon);
            runna.run();
            UIUtils.showNotifi(mainFrame, "Thêm hóa đơn thành công!", 1);
        }
    }

    private void updateCountOfBook() {
        System.out.println("Cap nhat so luong sach");
    }
    private void exportPDF() {
        HoaDonBUS hoaDonBUS = HoaDonBUS.getInstance();
        hoaDonBUS.xuatHoaDonPDF(hoaDon);
    }
    
    private void closeDetail() {
    // if (addHoaDonDialog != null) {
    //     addHoaDonDialog.setVisible(false);
    //     addHoaDonDialog.dispose();
    //     }
        if (runna !=null) {
            runna.run();
        }
    } 
    public void insertSach(){
        //maSach, maHD, soLuong, giaBan
        Map<Integer, List<JComponent>> rowLabels = table.getRowLabels();
        int maHD = Integer.parseInt(this.maHD);
        for(Map.Entry<Integer, List<JComponent>> i : table.getRowLabels().entrySet()){
            int row = i.getKey();
            int maSach = Integer.parseInt(getTextFieldMaSach(row, rowLabels).getText());
            int soLuong = Integer.parseInt(getTextFieldSL(row, rowLabels).getText());
            BigDecimal giaBan =FormatterUtil.getBigDicimalByString(getLabelGiaban(row, rowLabels).getText());
            CT_HoaDonDTO ct_HoaDon = new CT_HoaDonDTO(maSach, maHD, soLuong, giaBan);
            ct_HoaDonBUS.insert(ct_HoaDon);

            //Cập nhật số lượng sách mỗi khi bán ra
            if (sachBUS == null) sachBUS = SachBUS.getInstance();
            SachDTO sachDTO = sachBUS.getInstanceByID(maSach);
            sachDTO.subSoLuong(soLuong);
            sachBUS.updateOnNhap(sachDTO);
        }
    }

    public boolean listSPIsEmpty(){
        if(this.tongTienHoaDon.compareTo(new BigDecimal(0)) == 0){
            return(true);
        }
        return(false);
    }



    /////////////////////////////////////////////////////////////////////////// Add hóa đơn
    

    public void setDetail(){
        //tên nhân viên, mã hóa đơn, khách hàng, ngày bán, tổng tiền, tổng tiền hàng, khuyến mãi, phương thức thanh toán,
        String tenNV = nhanVienBUS.getTenNVByMaTK(hoaDon.getMaTK());
        int maHD = hoaDon.getMaHD();
        String tenKH = khachHangBUS.getTenByMaKhachHang(hoaDon.getMaKH());
        LocalDateTime ngayBan = hoaDon.getNgayBan();
        BigDecimal tongTien = hoaDon.getTongTien();
        BigDecimal tongTienHang = tongTien;

        int maKM = hoaDon.getMaKM();
        KhuyenMaiDTO khuyenMai = khuyenMaiBUS.getInstanceByMa(maKM);



        String tenPTTT = phuongThucTTBUS.getTenByMaPhuongThucTT(hoaDon.getMaPT());

        // String ngayBanS = getStringNgay(ngayBan);


        this.lblNV.setText("<html><font size='+1'><b>Nhân viên: </b>"+tenNV+"</font></html>");
        this.lblMaHD.setText("<html><font size='+1'><b>Mã hóa đơn: </b> "+ maHD +"</font></html>");
        this.lblNgay.setText("<html><font size='+1'><b>Ngày bán: </b>"+FormatterUtil.formatDateTime(ngayBan)+"</font></html>");

        setSelectionKH(tenKH);
        comboboxKH.setEnabled(false);

        // setSelectionKM(tenKM);
        setSelectionPTTT(tenPTTT);

        arrayListKM = new ArrayList<>();

        String tenKM;
        BigDecimal giaKM;
        if (maKM != 1) {
            tenKM = khuyenMaiBUS.getTenByMaKhuyenMai(maKM);
            giaKM = khuyenMai.getGiaTriGiam();
            arrayListKM.add(khuyenMai);
            listKM = new String[arrayListKM.size() + 1]; 
            listKM[0] = "Không dùng"; 
            listKM[1] = tenKM;
        }

        else {
            tenKM = "Không dùng"; 
            listKM = new String[] {tenKM};
            giaKM = BigDecimal.ZERO;
        }

        // BigDecimal tongThanhToan = hoaDonBUS.tinhKhuyenMai(tongTien, giaKM);
        
        comboboxKM.setModel(new DefaultComboBoxModel(new String[]{tenKM}));
        comboboxKM.setEditable(false);

        loadTableDetail();
        
        updateTongTienHoaDon();

        giaKM = (giaKM.compareTo(BigDecimal.ONE) <0 ) ? giaKM.multiply(tongTienHoaDon) : giaKM;
        String template = "<html><font size='+1'><b>Tổng tiền: </b> %s</font><html>";

        this.lblTongTienHoaDon.setText(String.format(template, FormatterUtil.formatNumber(tongTienHoaDon)));
        this.lblTongTienHang.setText(FormatterUtil.formatNumber(tongTienHoaDon) + "");
        this.lblTienKM.setText(giaKM + "");

        String template2 = "<html><font size='+1'><b>'%s'</b></font></html>";
        tongThanhToan = tongTien;
        this.lblTongThanhToan.setText(String.format(template2, FormatterUtil.formatNumber(tongTien) + ""));

        updateTienKhachDua();

        setStatusTextFieldSL();

    }

    public void setStatusTextFieldSL(){
        Map<Integer, List<JComponent>> rowLabels = this.table.getRowLabels();
        int row = 1;
        for(Map.Entry<Integer, List<JComponent>> entry : rowLabels.entrySet()){
            getTextFieldSL(row, rowLabels).setEditable(false);
            getTextFieldMaSach(row, rowLabels).setEditable(false);
            row++;
        }
    }

    public void loadTableDetail(){
        int maHD = hoaDon.getMaHD();
        ArrayList<CT_HoaDonDTO> listCTHD = ct_HoaDonBUS.getListCTHDByMaHD(maHD);
        for(int i = 0; i < listCTHD.size(); i++){
            int maSach = listCTHD.get(i).getMaSach();
            String tenSach = sachBUS.getTenByMa(maSach);
            int soLuong = listCTHD.get(i).getSoLuong();
            BigDecimal giaBan = listCTHD.get(i).getGiaBan();
            String[] data = new String[] {maSach + "", tenSach, soLuong + "", FormatterUtil.formatNumber(giaBan) + "", FormatterUtil.formatNumber(hoaDonBUS.tinhTongGia(giaBan, soLuong)) + ""};
            this.table.addDataRow(data); 
        }
        updateTTTT();
    }

    ////setSelection cbx
    public void setSelectionKH(String text){
        for(int i = 0; i < comboboxKH.getItemCount(); i++){
            if(comboboxKH.getItemAt(i).equals(text)){
                comboboxKH.setSelectedIndex(i);
                return;
            }
        }
        System.out.println("Lỗi không thấy giá trị combobox");
    }

    public void setSelectionKM(String text){
        if(text.equals("Anonymous")){
            comboboxKM.setSelectedItem("Không có");
            return;
        }
        for(int i = 0; i < comboboxKM.getItemCount(); i++){
            if(comboboxKM.getItemAt(i).equals(text)){
                comboboxKM.setSelectedIndex(i);
                return;
            }
        }
        System.out.println("Lỗi không thấy giá trị combobox");
    }

    public void setSelectionPTTT(String text){
        for(int i = 0; i < comboboxPTTT.getItemCount(); i++){
            if(comboboxPTTT.getItemAt(i).equals(text)){
                comboboxPTTT.setSelectedIndex(i);
                return;
            }
        }
        System.out.println("Lỗi không thấy giá trị combobox");
    }
    ////setSelection cbx
    
    

    public String getStringNgay(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String day = date.format(formatter);
        return(day);
    }

    public void getListKH(){
        ArrayList<String> temp = khachHangBUS.getAllTenKhachHang();
        // temp.add(0, "Anonymous");
        this.listKH = temp.toArray(new String[0]);
    }
    
    public void getListKM(){
        // ArrayList<String> temp = khuyenMaiBUS.getAllTenKhuyenMai();
        // temp.add(0, "Không có");
        // this.listKM = temp.toArray(new String[0]);
    }

    public void getPTTT(){
        ArrayList<String> temp = phuongThucTTBUS.getAllTenPhuongThucTT();
        this.listPTTT = temp.toArray(new String[0]);
    }

    public void getNV(){
        int maTK = this.taiKhoan.getMaTK();
        int maNV = nhanVienBUS.getMaNVByMaTK(maTK); 
        NhanVienDTO nhanVien = nhanVienBUS.getInstanceByMa(maNV);
        this.nhanVienDTO = nhanVien;
    }

    public void getMaHD(){
        this.maHD = hoaDonBUS.getNextID() + "";
    }
    
    public void setCallBack(GetDataCallBack getDataCallBack){
        this.getDataCallBack = getDataCallBack;
    }

    public interface GetDataCallBack {
        public void setData(HoaDonDTO hoaDon);
    }
}
