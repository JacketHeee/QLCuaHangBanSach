package GUI.forms;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import utils.ExcelReader;
import utils.UIUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import GUI.component.CustomTextFieldSL;
import javax.swing.BorderFactory;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.CT_PhieuNhapBUS;
import BUS.ChiTietQuyenBUS;
import BUS.ChucNangBUS;
import BUS.HoaDonBUS;
import BUS.NhaCungCapBUS;
import BUS.NhanVienBUS;
import BUS.PhieuNhapBUS;
import BUS.SachBUS;
import DTO.CT_PhieuNhapDTO;
import DTO.ChiTietQuyenDTO;
import DTO.PhieuNhapDTO;
import DTO.SachDTO;
import DTO.TaiKhoanDTO;
import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomButton;
import GUI.component.CustomTable;
import GUI.component.InvoiceTable;
import GUI.component.TableActionListener;
import GUI.component.search.TextFieldListSach;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

public class TaoPhieuNhapForm extends JPanel implements ActionListener, TableActionListener{
    private int id = 7;
    private String[] listNcc; 
    private CustomButton buttonSave;
    private CustomButton buttonCancel;
    private CustomTable table;
    private MainFrame mainFrame;
    private TaiKhoanDTO taiKhoan;
    private ArrayList<String> listAction;
    private ChiTietQuyenBUS chiTietQuyenBUS;
    private JPanel chiTietPhieuNhap;
    private String[] headers = {"Mã sách","Tên sách","Số lượng","Giá nhập","Thành tiền"};
    private String[] headerType = {"inputMa","label","inputNumber","inputNumber","label"};
    private CustomTextFieldSL textFieldLoiNhuan;
    private NhanVienBUS nhanVienBUS;
    private NhaCungCapBUS nhaCungCapBUS;
    private PhieuNhapBUS phieuNhapBUS;
    private CT_PhieuNhapBUS ct_PhieuNhapBUS;
    private JComboBox<String> comboboxNCC;   
    private String maNhap;
    private SachBUS sachBUS;

    private String tenNhanVien;
    private BigDecimal tongTienPhieuNhap = BigDecimal.ZERO;
    private JLabel lblTongTienPN;
    private String maPhieuNhap;

    private String type;

    private GetDataCallBack getDataCallBack;

    private PhieuNhapDTO phieuNhap;
    private JLabel lblNV;
    private JLabel lblPN;
    private JLabel lblNgayNhap;
    private Runnable runnable;

    public TaoPhieuNhapForm(MainFrame mainFrame,Runnable runnable) {
        this.runnable = runnable;
        this.mainFrame = mainFrame;
        this.taiKhoan = mainFrame.getTaiKhoan();
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();               
        this.listAction = getListAction();
        this.textFieldLoiNhuan = new CustomTextFieldSL();
        this.lblTongTienPN = new JLabel();
        this.nhanVienBUS = NhanVienBUS.getInstance();
        this.nhaCungCapBUS = NhaCungCapBUS.getInstance();
        this.phieuNhapBUS = PhieuNhapBUS.getInstance();
        this.ct_PhieuNhapBUS = CT_PhieuNhapBUS.getInstance();
        this.sachBUS = SachBUS.getInstance();
        this.type = "thêm";
        setTenNhanVien();
        setListNCC();
        setMaPhieuNhap();
        getMaNhap();
        init();
    }

    public TaoPhieuNhapForm(MainFrame mainFrame, PhieuNhapDTO phieuNhap, String type, Runnable runnable) {
        this.runnable = runnable;
        this.mainFrame = mainFrame;
        this.taiKhoan = mainFrame.getTaiKhoan();
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();               
        this.listAction = getListAction();
        this.textFieldLoiNhuan = new CustomTextFieldSL();
        this.lblTongTienPN = new JLabel();
        this.nhanVienBUS = NhanVienBUS.getInstance();
        this.nhaCungCapBUS = NhaCungCapBUS.getInstance();
        this.phieuNhapBUS = PhieuNhapBUS.getInstance();
        this.ct_PhieuNhapBUS = CT_PhieuNhapBUS.getInstance();
        this.sachBUS = SachBUS.getInstance();
        this.type = type;
        this.phieuNhap = phieuNhap;
        setTenNhanVien();
        setListNCC();
        setMaPhieuNhap();
        getMaNhap();
        init();
    }

    private void init() {
        setLayout(new MigLayout("insets 0,wrap 1, gap 10"));

        if(this.type.equals("detail")){
            add(new JLabel("<html><b><font size='+2'>CHI TIẾT PHIẾU NHẬP</font></b><html>"),"gaptop 20, al center,pushx");
        }
        else{
            add(new JLabel("<html><b><font size='+2'>TẠO PHIẾU NHẬP MỚI</font></b><html>"),"gaptop 20, al center,pushx");
        }
        add(getThongtin(),"pushx, growx");
        chiTietPhieuNhap = getChiTietPhieuNhap();
        add(chiTietPhieuNhap,"pushx, growx");
        if(!type.equals("detail")){
            add(panelThongTinNhapHang(), "pushx, align right");
        }
        add(getTongTien(),"pushx, growx");
        if (type.equals("detail")) {
            add(getPanelActionDetail(), "pushx, growx");
            addBtnListenerDetail();
        } else {
            add(getPanelAction(), "pushx, growx");
            addBtnListener();
        }
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
        
        lblNV = new JLabel("<html><font size='+1'><b>Nhân viên: </b>"+tenNhanVien+"</font></html>");
        panel.add(lblNV, "pushx,growx");

        lblPN = new JLabel("<html><font size='+1'><b>Mã phiếu nhập: </b> "+ maPhieuNhap +"</font></html>");
        panel.add(lblPN, "pushx,growx,wrap");
        panel.add(getPanelNhaCungCap(),"pushx,growx");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String day = sdf.format(new Date());

        lblNgayNhap = new JLabel("<html><font size='+1'><b>Ngày nhập: </b>"+ day +"</font></html>");
        panel.add(lblNgayNhap, "pushx,growx");
        return panel; 
    }

    
    private JPanel getPanelNhaCungCap() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        panel.add(new JLabel("<html><font size='+1'><b>Nhà cung cấp: </b></font></html>"));
        comboboxNCC = new JComboBox<>(listNcc);
        comboboxNCC.setFont(new Font(comboboxNCC.getFont().getName(),Font.PLAIN,16));
        comboboxNCC.putClientProperty(FlatClientProperties.STYLE, "focusWidth: 0; innerFocusWidth: 0");
        panel.add(comboboxNCC);
        return panel;
    }

    String[][]actions = {
        {"remove.svg","remove"}
    };

    private JPanel getChiTietPhieuNhap() {
        JPanel panel = getPanel("Chi tiết phiếu nhập");
        panel.setLayout(new MigLayout());
        table = new InvoiceTable(
            null
            , actions
            ,new InvoiceTable.DataForTable(){
                @Override
                public String[] getDataForTable(SachDTO sach, int soLuong) {                 //Truyền vào một hàm cho InvoiceTable
                    String[] result;    
                    BigDecimal giaNhap = sach.getGiaBan().divide(getPhanTramLoiNhuan(), 2, RoundingMode.HALF_UP);     //Khi invoiceTable đã có dữ liệu sachDTO
                    BigDecimal tongGia = tinhTongGia(giaNhap, soLuong);                         //Cho sử dụng cả hóa đơn và phiếu nhập
                    //Lưu giá nhập cũ của sách = giá bán cũ / % lợi nhuận
                    ArrayList<String> list = new ArrayList<>();
                        list.add(sach.getMaSach() + "");
                        list.add(sach.getTenSach());
                        list.add(soLuong + "");
                        list.add(giaNhap + "");
                        list.add(tongGia + "");
                    ;
                    result = list.toArray(new String[0]);
                    return(result);
                    // return(new String[]{""});
                }   
            }
            , new InvoiceTable.TinhTongGia(){
                @Override
                public void updateTongGia(int row) {    //Truyền vào cho invoice table sử dụng để update tổng tiền ctsp khi có thay đổi
                    updateTongGiaNhap(row);
                }
            }
            , new InvoiceTable.TinhTongGiaChungTu(){    //Được callBack mỗi khi thêm mới sách/ thay đổi số lượng
                @Override
                public void updateTongGiaChungTu(){
                    updateTongTienPhieuNhap();
                    // updateTTTT();
                    // updateStatusCombobox();
                    // updateStatusTextFieldTienTra();
                    // updateStatusbtnThem(true);
                }
            }
            , headers
            ,headerType
            ,90
        );
        if (!type.equals("detail")) table.addDataRow(null);

        panel.add(table,"push,grow,wrap");
        table.setActionListener(this);

        if(!type.equals("detail")){
            panel.add(panelActionOnTable(),"pushx,growx");
        }
        return panel;
    }

    private JPanel panelThongTinNhapHang() {
        JPanel panel = new JPanel(new MigLayout("al right"));
        panel.add(new JLabel("Phần trăm lợi nhuận(%)"),"sg 1");
        textFieldLoiNhuan.setText("1");
        panel.add(textFieldLoiNhuan,"sg 2,grow");
        panel.add(new JLabel("%"), "wrap");
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
        lblTongTienPN.setText(String.format(template, "0"));
        panel.add(lblTongTienPN);

        return panel;
    }
    
    private JPanel getPanelAction() {
        JPanel panel = new JPanel(new MigLayout("gap 10,al right"));
        buttonSave = new CustomButton("Lưu");
        buttonSave.setBackground(Color.decode("#4882FF"));
        buttonSave.setBackground(Color.white);    
        buttonCancel = new CustomButton("Hủy");
        buttonCancel.addActionListener(e -> runnable.run());
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
    }
    public void addBtnListenerDetail() {
        buttonSave.setActionCommand("btnExportPNPDF");
        buttonSave.addActionListener(this);
        buttonCancel.setActionCommand("btnCancelPNDetail");
        buttonCancel.addActionListener(this);
    } 
    private void exportPNPDF() {
        PhieuNhapBUS phieuNhapBUS = PhieuNhapBUS.getInstance();
        phieuNhapBUS.xuatPhieuNhapPDF(phieuNhap);
    }
    private void closePNDetail() {
        runnable.run();
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        String ms = e.getActionCommand();

        switch (ms) {
            case "addRowData":
                table.addDataRow(null);
                chiTietPhieuNhap.repaint();
                chiTietPhieuNhap.revalidate();
                repaint();
                revalidate();
                // updateStatusTextFieldLoiNhuan();
                // updateStatusbtnThem(false); //Sau khi nhấn nút thêm thì tạm khóa nút thêm
                break;
            case "btnLuu":
                themPhieuNhap();
                break;
            case "btnExportPNPDF":
            exportPNPDF();
            break;
            case "btnCancelPNDetail":
            closePNDetail();
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
            SachDTO sach = sachBUS.getInstanceByID((int)Integer.parseInt(x[0])); 
            int soLuong = Integer.parseInt(x[2]);
            BigDecimal giaNhap = new BigDecimal(x[3]);
            BigDecimal tongGia = giaNhap.multiply(new BigDecimal(soLuong));
            ArrayList<String> list = new ArrayList<>();
                        list.add(sach.getMaSach() + "");
                        list.add(sach.getTenSach());
                        list.add(soLuong + "");
                        list.add(giaNhap + "");
                        list.add(tongGia + "");
                    ;
            String[] rowData = list.toArray(new String[0]);
            table.addDataRow(rowData);
            updateTongGiaNhap(index++);
            updateTongTienPhieuNhap();
        }
    }

    @Override
    public void onActionPerformed(String actionId, int row) {
        switch (actionId) {
            case "remove":
                // Logic xóa cho form này
                // JOptionPane.showMessageDialog(null, "xoa " + row);
                int choose = UIUtils.messageRemove("Bạn thực sự muốn xóa?");

                if (choose == 0) {
                    table.removeRow(row);
                    updateTongTienPhieuNhap();
                    Notifications.getInstance().setJFrame(mainFrame);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Xóa row " + row + " thành công!");
                }
                
                break;
            default:
                System.out.println("Unknown action: " + actionId);
                break;
        }
    }


    //update cột thành tiền(đ) của một chi tiết sản phẩm
    public void updateTongGiaNhap(int row){
        Map<Integer, List<JComponent>> rowLabels = this.table.getRowLabels();
        JLabel labelTT = getLabelTongTien(row, rowLabels);
        CustomTextFieldSL textFieldGN = getTextFieldGiaNhap(row, rowLabels);
        CustomTextFieldSL textFieldSL = getTextFieldSL(row, rowLabels); 

        int soLuong = Integer.parseInt(textFieldSL.getText());
        // System.out.println(textFieldGN.getText()); //
        BigDecimal giaNhap = BigDecimal.valueOf(Double.parseDouble(textFieldGN.getText()));
        BigDecimal tongGia = giaNhap.multiply(BigDecimal.valueOf(soLuong));

        labelTT.setText(tongGia + "");
    }

    // public void updateTongTienPhieuNhap(){ //Mỗi lần thêm phải tính lại hết
    //     Map<Integer, List<JComponent>> rowLabels = table.getRowLabels();
    //     int index = 1;
    //     BigDecimal result = new BigDecimal(0);
    //     for(Map.Entry<Integer, List<JComponent>> entry : rowLabels.entrySet()){
    //         JLabel lblTongTienSach = getLabelTongTien(index, rowLabels);
    //         BigDecimal tongTienSach = BigDecimal.valueOf(Double.parseDouble(lblTongTienSach.getText()));
    //         result = result.add(tongTienSach);
    //         index++;
    //     }
    //     //set Tổng tiền pn để tính toán
    //     this.tongTienPhieuNhap = result;
    //     //Set tổng tiền ở trên
    //     setTextLblTongTienPN(this.tongTienPhieuNhap + "");

    // }

    public void updateTongTienPhieuNhap() {
        Map<Integer, List<JComponent>> rowLabels = table.getRowLabels();
        int index = 1;
        BigDecimal result = BigDecimal.ZERO; // Sử dụng BigDecimal.ZERO để khởi tạo
        for (Map.Entry<Integer, List<JComponent>> entry : rowLabels.entrySet()) {
            JLabel lblTongTienSach = getLabelTongTien(index, rowLabels);
            String tongTienText = lblTongTienSach.getText();
            BigDecimal tongTienSach;
            try {
                tongTienSach = tongTienText.isEmpty() ? BigDecimal.ZERO : BigDecimal.valueOf(Double.parseDouble(tongTienText));
            } catch (NumberFormatException e) {
                tongTienSach = BigDecimal.ZERO; // Giá trị mặc định nếu không phân tích được
                System.err.println("Lỗi phân tích tổng tiền sách tại hàng " + index + ": " + tongTienText);
            }
            result = result.add(tongTienSach);
            index++;
        }
        // Set Tổng tiền phiếu nhập để tính toán
        this.tongTienPhieuNhap = result;
        // Set tổng tiền trên giao diện
        setTextLblTongTienPN(this.tongTienPhieuNhap.toString());
    }

    public void setTextLblTongTienPN(String i){
        String template = "<html><font size='+1'><b>Tổng tiền: </b> %sđ</font><html>";
        this.lblTongTienPN.setText(String.format(template, i));
    }

    public JLabel getLabelTongTien(int row, Map<Integer, List<JComponent>> rowLabels){
        JLabel label = null;
        JComponent cpn = rowLabels.get(row).get(4);
        JPanel panel = (JPanel)cpn;
        label = (JLabel)panel.getComponent(0);
        return(label);
    }

    public CustomTextFieldSL getTextFieldGiaNhap(int row, Map<Integer, List<JComponent>> rowLabels){
        CustomTextFieldSL textField = null;
        JComponent cpn = rowLabels.get(row).get(3);
        JPanel panel = (JPanel)cpn;
        textField = (CustomTextFieldSL)panel.getComponent(0);
        return(textField);
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

    public void updateStatusTextFieldLoiNhuan(){
        if(listSPIsEmpty()){
            setStatusTextFieldLoiNhuan(true);
        }
        else{
            setStatusTextFieldLoiNhuan(false);
        }
    }

    public void updateStatusbtnThem(boolean i){        
        setStatusBtnThem(i);
    }



    public BigDecimal tinhTongGia(BigDecimal giaNhap, int soLuong){
        BigDecimal result = (giaNhap).multiply(BigDecimal.valueOf(soLuong));
        return(result);
    }

    public BigDecimal getPhanTramLoiNhuan(){
        double phanTram = Double.parseDouble(textFieldLoiNhuan.getText());
        BigDecimal phanTramLoi = BigDecimal.valueOf(phanTram).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        BigDecimal phanTramTong = phanTramLoi.add(new BigDecimal(1));
        return(phanTramTong);
    }
    

    public void setStatusBtnThem(boolean i){
        this.butAddData.setEnabled(i);
    }
    public void setStatusTextFieldLoiNhuan(boolean i){
        this.textFieldLoiNhuan.setEditable(i);
    }

    //Nếu đã nhấn nút thêm thì khóa textField % lợi nhuận (khác với hóa đơn)
    public boolean listSPIsEmpty(){
        // JOptionPane.showMessageDialog(null, tongTienPhieuNhap+"");
        if(tongTienPhieuNhap.compareTo(BigDecimal.ZERO) == 0){
            return(true);
        }
        return(false);
    }

    ////////////////////////////////////////////////////////////////////////////thêm phiếu nhập
    public void themPhieuNhap(){
//Thuộc tính: maNhap, ngayNhap, tongTien, maNCC, maTK
        //Khóa ngoại NN: Sach
        if(listSPIsEmpty()){
            JOptionPane.showMessageDialog(mainFrame, "Chưa có sản phẩm nào được thêm!");
            return;
        }

        // JOptionPane.showMessageDialog(null, "đã có sản phẩm");

        LocalDateTime ngayNhap = LocalDateTime.now();
        BigDecimal tongTien = this.tongTienPhieuNhap;
        int maTK = this.taiKhoan.getMaTK();
        int maNCC = this.nhaCungCapBUS.getMaNhaCungCapByTen((String)comboboxNCC.getSelectedItem());

        PhieuNhapDTO phieuNhap = new PhieuNhapDTO(ngayNhap, tongTien, maNCC, maTK);
        if(phieuNhapBUS.insert(phieuNhap) != 0){
            insertSach();   //thêm khóa ngoại nhiều nhiều // đã check...
            setGiaBanSach();    //cài đặt giá bán cho sách theo lợi nhuận
            getDataCallBack.setData(phieuNhap);
            // JOptionPane.showMessageDialog(mainFrame, "Thêm phiếu nhập thành công");
            runnable.run();
        }
    }

    public void insertSach(){
                //maSach, maNhap, soLuong, giaNhap
        Map<Integer, List<JComponent>> rowLabels = table.getRowLabels();
        int maNhap = Integer.parseInt(this.maNhap);
        for(Map.Entry<Integer, List<JComponent>> i : table.getRowLabels().entrySet()){
            int row = i.getKey();
            // System.out.println(row);
            int maSach = Integer.parseInt(getTextFieldMaSach(row, rowLabels).getText());
            int soLuong = Integer.parseInt(getTextFieldSL(row, rowLabels).getText());
            BigDecimal giaNhap = BigDecimal.valueOf(Double.parseDouble(getTextFieldGiaNhap(row, rowLabels).getText()));
            CT_PhieuNhapDTO ct_Nhap = new CT_PhieuNhapDTO(maSach, maNhap, soLuong, giaNhap);
            ct_PhieuNhapBUS.insert(ct_Nhap);
        }
    }

    public void setGiaBanSach(){
        double loiNhuan = (Integer.parseInt(textFieldLoiNhuan.getText())+100)*1.0/100;

        Map<Integer, List<JComponent>> rowLabels = this.table.getRowLabels();
        for(int i = 1; i <= rowLabels.size(); i++){
            int maSach = Integer.parseInt(getTextFieldMaSach(i, rowLabels).getText());
            System.out.println(maSach);
            SachDTO sach = sachBUS.getInstanceByID(maSach);
            BigDecimal giaban = BigDecimal.valueOf(Double.parseDouble(getTextFieldGiaNhap(i, rowLabels).getText())*loiNhuan);

            // BigDecimal tienSach = getGiaBanSach(giaNhap);
            sach.setGiaBan(giaban);
            int soluong = Integer.parseInt(getTextFieldSL(i,rowLabels).getText());
            sach.addSoLuong(soluong);

            System.out.println(giaban + " + " + soluong);
            sachBUS.updateOnNhap(sach);
        }
    }

    public BigDecimal getGiaBanSach(BigDecimal giaNhap){    //Lấy giá bán
        BigDecimal phanTram = getPhanTramLoiNhuan();
        return(giaNhap.multiply(phanTram));
    }       



    ////////////////////////////////////////////////////////////////////////////thêm phiếu nhập
    
    public void setDetail(){//bỏ phần trăm lợi nhuận đi
        //Nhân viên, mã phiếu nhập, nhà cung cấp, ngày nhập
        String tenNV = nhanVienBUS.getTenNVByMaTK(phieuNhap.getMaTK());
        int maPN = phieuNhap.getMaNhap();
        String tenNCC = nhaCungCapBUS.getTenByMaNhaCungCap(phieuNhap.getMaNCC());
        LocalDateTime ngayNhap = phieuNhap.getNgayNhap();
        BigDecimal tongTien = phieuNhap.getTongTien();

        String ngayNhapS = getStringNgay(ngayNhap);

        lblNV.setText("<html><font size='+1'><b>Nhân viên: </b>"+tenNV+"</font></html>");
        lblPN.setText("<html><font size='+1'><b>Mã phiếu nhập: </b> "+ maPN +"</font></html>");
        lblNgayNhap.setText("<html><font size='+1'><b>Ngày nhập: </b>"+ ngayNhapS +"</font></html>");
        setSelectionNCC(tenNCC);

        String template = "<html><font size='+1'><b>Tổng tiền: </b> %sđ</font><html>";
        lblTongTienPN.setText(String.format(template, tongTien + ""));

        loadTableDetail();   
        setStatusDetail();
    }

    public void setSelectionNCC(String text){
        for(int i = 0; i < comboboxNCC.getItemCount(); i++){
            if(comboboxNCC.getItemAt(i).equals(text)){
                comboboxNCC.setSelectedIndex(i);
                return;
            }
        }
        System.out.println("Lỗi không thấy giá trị combobox");
    }
    
    public String getStringNgay(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String day = date.format(formatter);
        return(day);
    }

    public void loadTableDetail(){
        int maNhap = phieuNhap.getMaNhap();
        ArrayList<CT_PhieuNhapDTO> listCTPN = ct_PhieuNhapBUS.getListCTPNByMaPN(maNhap);
        for(int i = 0; i < listCTPN.size(); i++){
            int maSach = listCTPN.get(i).getmaSach();
            String tenSach = sachBUS.getTenByMa(maSach);
            int soLuong = listCTPN.get(i).getSoLuongNhap();
            BigDecimal giaNhap = listCTPN.get(i).getGiaNhap();
            String[] data = new String[] {maSach + "", tenSach, soLuong + "", giaNhap + "", phieuNhapBUS.tinhTongGia(giaNhap, soLuong) + ""};
            this.table.addDataRow(data); 
        }
        updateTongTienPhieuNhap();
    }

    public void setStatusDetail(){
        this.comboboxNCC.setEnabled(false);
        Map<Integer, List<JComponent>> rowLabels = this.table.getRowLabels();
        int row = 1;
        for(Map.Entry<Integer, List<JComponent>> entry : rowLabels.entrySet()){
            getTextFieldSL(row, rowLabels).setEditable(false);
            getTextFieldMaSach(row, rowLabels).setEditable(false);
            getTextFieldGiaNhap(row, rowLabels).setEditable(false);
            row++;
        }
    }


    public void setTenNhanVien(){
        this.tenNhanVien = nhanVienBUS.getTenNVByMaTK(this.taiKhoan.getMaTK());
    }

    public void setListNCC(){
        ArrayList<String> list = nhaCungCapBUS.getAllTenNhaCungCap();
        this.listNcc = list.toArray(new String[0]);
    }

    public void setMaPhieuNhap(){
        this.maPhieuNhap = phieuNhapBUS.getNextID() + "";
    }

    public void getMaNhap(){
        this.maNhap = phieuNhapBUS.getNextID() + "";
    }

    public void setCallBack(GetDataCallBack getDataCallBack){
        this.getDataCallBack = getDataCallBack;
    }

    public interface GetDataCallBack {
        public void setData(PhieuNhapDTO phieuNhap);
    }

}
