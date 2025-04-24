package GUI.forms;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import utils.FormatterUtil;
import utils.UIUtils;
import utils.Validate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import GUI.component.CustomTextFieldSL;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.border.Border;


import com.formdev.flatlaf.FlatClientProperties;

import BUS.CT_HoaDonBUS;
import BUS.ChiTietQuyenBUS;
import BUS.ChucNangBUS;
import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.KhuyenMaiBUS;
import BUS.NhanVienBUS;
import BUS.PhuongThucTTBUS;
import DTO.CT_HoaDonDTO;
import DTO.ChiTietQuyenDTO;
import DTO.HoaDonDTO;
import DTO.KhachHangDTO;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
    private BigDecimal tongTienHoaDon;
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
    private AddHoaDonDialog addHoaDonDialog;

    public TaoHoaDonForm(MainFrame mainFrame) {
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
        this.getListKH();
        this.getListKM();
        this.getPTTT();
        this.getNV();
        this.getMaHD();
        init();
    }

    public TaoHoaDonForm(MainFrame mainFrame,AddHoaDonDialog addHoaDonDialog) {
        this(mainFrame);
        this.addHoaDonDialog = addHoaDonDialog;
    }

    
    private void init() {
        setLayout(new MigLayout("insets 0,wrap 1, gap 10"));

        add(new JLabel("<html><b><font size='+2'>TẠO HÓA ĐƠN MỚI</font></b><html>"),"gaptop 20, al center,pushx");
        add(getThongtin(),"pushx, growx");
        add(getChiTietPhieuNhap(),"push, grow");
        add(panelKhuyenMai(),"pushx, growx");
        add(panelPTTT(),"pushx, growx");
        add(panelThongTinThanhToan(),"pushx, growx");
        add(getPanelAction(),"pushx, growx");
        addBtnListener();
        setStatusCombobox();
        setStatusTextField();
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
        
        panel.add(new JLabel("<html><font size='+1'><b>Nhân viên: </b>"+nhanVienDTO.getHoTen()+"</font></html>"),"pushx,growx");
        panel.add(new JLabel("<html><font size='+1'><b>Mã hóa đơn: </b> "+ maHD +"</font></html>"),"pushx,growx,wrap");
        panel.add(getPanelNhaCungCap(),"pushx,growx");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String day = sdf.format(new Date());

        panel.add(new JLabel("<html><font size='+1'><b>Ngày bán: </b>"+day+"</font></html>"),"pushx,growx");
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
                        list.add(sach.getGiaBan() + "");
                        list.add(tongGia+"");
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
                    updateTTTT();
                    updateStatusCombobox();
                    updateStatusTextFieldTienTra();
                    updateStatusbtnThem();
                    updateTienKhachDua();
                }
            }
            ,headers
            ,headerType
        );
        panel.add(table,"push,grow,wrap");
        table.setActionListener(this);
        panel.add(panelActionOnTable(),"pushx,growx,wrap");
        panel.add(getTongTien(),"pushx,growx");
        return panel;
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
        String template = "<html><font size='+1'><b>Tổng tiền: </b> %sđ</font><html>";
        lblTongTienHoaDon = new JLabel(String.format(template, tongTienHoaDon));
        panel.add(lblTongTienHoaDon);
        return panel;
    }

    private JPanel panelKhuyenMai() {
        JPanel panel = getPanel("Khuyến mãi");
        panel.setLayout(new MigLayout("al center center "));
        panel.add(new JLabel("<html><font size='+1'><b>Chọn khuyến mãi</b></font></html>"));
        panel.add(new JLabel(),"pushx");
        comboboxKM = new JComboBox<>(listKM);
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
        lblTongThanhToan = new JLabel(String.format(template, tongThanhToan + ""));

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
                addHoaDonDialog.setVisible(false);
                break;
            case "comboboxKM":
                updateTienKhuyenMai();
                updateTongTienHoaDon();
                updateTTTT();
                updateTienKhachDua();
                break;
            default:
                break;
        }

        // TODO Auto-generated method stub
        
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
                    updateTTTT();
                    updateStatusCombobox();
                    updateStatusTextFieldTienTra();
                    updateStatusbtnThem();
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
        Map<Integer, List<Component>> rowLabels = this.table.getRowLabels();
        JLabel labelTT = getLabelTongTien(row, rowLabels);
        JLabel labelGB = getLabelGiaban(row, rowLabels);
        CustomTextFieldSL textFieldSL = getTextFieldSL(row, rowLabels); 

        int soLuong = Integer.parseInt(textFieldSL.getText());
        BigDecimal giaBan = BigDecimal.valueOf(Double.parseDouble(labelGB.getText()));
        BigDecimal tongGia = hoaDonBUS.tinhTongGia(giaBan, soLuong);

        labelTT.setText(tongGia + "");
    }

    ///////////////////////////Các hàm get Component trong Map 
    public JLabel getLabelTongTien(int row, Map<Integer, List<Component>> rowLabels){
        JLabel label = null;
        Component cpn = rowLabels.get(row).get(4);
        JPanel panel = (JPanel)cpn;
        label = (JLabel)panel.getComponent(0);
        return(label);
    }

    public JLabel getLabelGiaban(int row, Map<Integer, List<Component>> rowLabels){
        JLabel label = null;
        Component cpn = rowLabels.get(row).get(3);
        JPanel panel = (JPanel)cpn;
        label = (JLabel)panel.getComponent(0);
        return(label);
    }

    public CustomTextFieldSL getTextFieldSL(int row, Map<Integer, List<Component>> rowLabels){
        CustomTextFieldSL textField = null;
        Component cpn = rowLabels.get(row).get(2);
        JPanel panel = (JPanel)cpn;
        textField = (CustomTextFieldSL)panel.getComponent(0);
        return(textField);
    }

    public TextFieldListSach getTextFieldMaSach(int row, Map<Integer, List<Component>> rowLabels){
        TextFieldListSach textField = null;
        Component cpn = rowLabels.get(row).get(0);
        JPanel panel = (JPanel)cpn;
        textField = (TextFieldListSach)panel.getComponent(0);
        return(textField);
    }
    ///////////////////////////Các hàm get Component trong Map 


    //Hàm này được gọi mỗi khi thêm sách mới; thay đổi cbx khuyến mãi; xóa
    public void updateTongTienHoaDon(){ //Mỗi lần thêm phải tính lại hết
        Map<Integer, List<Component>> rowLabels = table.getRowLabels();
        // System.out.println(rowLabels.get(0).get(1));
        int index = 0;
        BigDecimal result = new BigDecimal(0);
        for(Map.Entry<Integer, List<Component>> entry : rowLabels.entrySet()){

            JLabel lblTongTienSach = getLabelTongTien(index, rowLabels);
            BigDecimal tongTienSach = BigDecimal.valueOf(Double.parseDouble(lblTongTienSach.getText()));
            result = result.add(tongTienSach);
            index++;
        }

        //set Tổng tiền hóa đơn để tính toán
        this.tongTienHoaDon = result;
        //Set tổng tiền ở trên
        setTextLblTongTienHoaDon(this.tongTienHoaDon + "");
        //Set tổng tiền ở dưới
        lblTongTienHang.setText(this.tongTienHoaDon + "");


    

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
        lblTienKM.setText(getTienKM() + "");
    }
    //Mở khóa nút thêm
    public void updateStatusbtnThem(){        
        setStatusBtnThem(true);
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
        String template = "<html><font size='+1'><b>Tổng tiền: </b> %sđ</font><html>"; // đổi để lấy font rõ hơn
        lblTongTienHoaDon.setText(String.format(template, i));
    }

    public void setTextTongTienThanhToan(String tongTienHoaDon){
        //TongTienThanhToan = TongTienHoaDon - KM
        //TongTienThanhToan = TongTienHoaDon*%KM
        String template = "<html><font size='+1'><b>'%s'</b></font></html>";
        BigDecimal giaTienHoaDon = BigDecimal.valueOf(Double.parseDouble(tongTienHoaDon));
        //Tính toán khuyến mãi ở đây
        String tenKM = (String)comboboxKM.getSelectedItem();
        BigDecimal khuyenMai = getTienKM();
        this.tongThanhToan = hoaDonBUS.tinhKhuyenMai(giaTienHoaDon, khuyenMai);

        lblTongThanhToan.setText(String.format(template, tongThanhToan));
    }

    public BigDecimal getTienKM(){
        String tenKM = (String)comboboxKM.getSelectedItem();
        // if(tenKM.equals("Không có")){
        //     return(new BigDecimal(0));
        // }
        // int maKM = khuyenMaiBUS.getMaKhuyenMaiByTen(tenKM);
        // KhuyenMaiDTO khuyenMai = khuyenMaiBUS.getInstanceByMa(maKM);
        return(hoaDonBUS.getTienKhuyenMai(tenKM));
    }

    // public BigDecimal tinhKhuyenMai(BigDecimal giaBanDau, BigDecimal soTienKM){
    //     //Giảm theo phần trăm
    //     // 0 < soTienKM < 1
    //     if(soTienKM.compareTo(BigDecimal.valueOf(1)) < 0 && soTienKM.compareTo(BigDecimal.valueOf(0)) > 0){
    //         return(giaBanDau.multiply(soTienKM));
    //     }
    //     //Giảm theo giá bán
    //     return(giaBanDau.subtract(soTienKM));
    // }

    public void updateTienThoi(){
        if(getTienThoi().compareTo(new BigDecimal(0)) < 0){
            lblTienThoi.setText("Chưa đủ tiền");
        }
        else{
            lblTienThoi.setText(getTienThoi() + "");
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
        this.butAddData.setEnabled(i);
    }

    /////////////////////////////////////////////////////////////////// Các setter label, textField, các hàm tính toán

    /////////////////////////////////////////////////////////////////////////// Xử Lý Table
    
    /////////////////////////////////////////////////////////////////////////// Add hóa đơn

    public void TaoHoaDon(){
        //Thuộc tính: maHD, ngayBan, tongTien, maTK, maPT, maKM, maKH
        //Khóa ngoại NN: Sach
        if(listSPIsEmpty()){
            JOptionPane.showMessageDialog(mainFrame, "Chưa có sản phẩm nào được thêm!");
            return;
        }
        LocalDateTime ngayBan = LocalDateTime.now();
        System.out.print(ngayBan.toString());
        BigDecimal tongTien = this.tongThanhToan;
        int maTK = this.taiKhoan.getMaTK();
        int maPT = phuongThucTTBUS.getMaPhuongThucTTByTen((String)comboboxPTTT.getSelectedItem());

        int maKM;
        int maKH;
        if(!comboboxKM.getSelectedItem().equals("Không có")){
            maKM = khuyenMaiBUS.getMaKhuyenMaiByTen((String)comboboxKM.getSelectedItem());
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

        HoaDonDTO hoaDon = new HoaDonDTO(ngayBan, tongTien, maTK, maPT, maKM, maKH);
        if(hoaDonBUS.insert(hoaDon) != 0){
            JOptionPane.showMessageDialog(mainFrame, "Thêm hóa đơn thành công");
        }

        insertSach();   //thêm khóa ngoại nhiều nhiều

        getDataCallBack.setData(hoaDon);
    }

    public void insertSach(){
        //maSach, maHD, soLuong, giaBan
        Map<Integer, List<Component>> rowLabels = table.getRowLabels();
        int maHD = Integer.parseInt(this.maHD);
        for(Map.Entry<Integer, List<Component>> i : table.getRowLabels().entrySet()){
            int row = i.getKey();
            int maSach = Integer.parseInt(getTextFieldMaSach(row, rowLabels).getText());
            int soLuong = Integer.parseInt(getTextFieldSL(row, rowLabels).getText());
            BigDecimal giaBan = BigDecimal.valueOf(Double.parseDouble(getLabelGiaban(row, rowLabels).getText()));
            CT_HoaDonDTO ct_HoaDon = new CT_HoaDonDTO(maSach, maHD, soLuong, giaBan);
            ct_HoaDonBUS.insert(ct_HoaDon);
        }
    }

    public boolean listSPIsEmpty(){
        if(this.tongTienHoaDon.compareTo(new BigDecimal(0)) == 0){
            return(true);
        }
        return(false);
    }



    /////////////////////////////////////////////////////////////////////////// Add hóa đơn
    

    public void getListKH(){
        ArrayList<String> temp = khachHangBUS.getAllTenKhachHang();
        temp.add(0, "Anonymous");
        this.listKH = temp.toArray(new String[0]);
    }
    
    public void getListKM(){
        ArrayList<String> temp = khuyenMaiBUS.getAllTenKhuyenMai();
        temp.add(0, "Không có");
        this.listKM = temp.toArray(new String[0]);
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
