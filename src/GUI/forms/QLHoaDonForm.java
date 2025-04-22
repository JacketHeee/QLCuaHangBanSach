package GUI.forms;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import BUS.ChiTietQuyenBUS;
import BUS.ChucNangBUS;
import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.NhaCungCapBUS;
import BUS.NhanVienBUS;
import BUS.TaiKhoanBUS;
import DTO.ChiTietQuyenDTO;
import DTO.HoaDonDTO;
import DTO.KhachHangDTO;
import DTO.KhuyenMaiDTO;
import DTO.SachDTO;
import DTO.ViTriVungDTO;
import DTO.TaiKhoanDTO;

import java.util.ArrayList;
import java.util.List;

import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomBoldJLabel;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import GUI.component.DimGlassPane;
import GUI.component.TableActionListener;
import GUI.component.search.SearchBarPanel;
import GUI.dialog.AddHoaDonDialog;
import GUI.dialog.AddNhomQuyen;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import search.QLHoaDonSearch;
import search.SachSearch;
import utils.FormatterUtil;
import utils.UIUtils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class QLHoaDonForm extends JPanel implements TableActionListener, ActionListener {

    private String title;
    private int id = 10;
    private String[] header = {"Mã hóa đơn", "Ngày lập", "Khách hàng","Tổng tiền","Nhân viên"};
    HoaDonBUS hoaDonBUS;
    private MainFrame mainFrame;
    private ArrayList<HoaDonDTO> listKH;
    private ArrayList<String[]> dataToShow;
    private TaiKhoanDTO taiKhoan;
    private ArrayList<String> listAction;
    private ChiTietQuyenBUS chiTietQuyenBUS;
    public DimGlassPane glassPane = new DimGlassPane();
    private KhachHangBUS khachHangBUS;
    private NhanVienBUS nhanVienBUS;
    private String[][] attributes = {
        {"inputDate","Ngày bán"},   //tự get
        {"textbox", "Tổng tiền"},  //cài đặt sau
        {"textbox", "Mã tài khoản"},    //tự get
        {"combobox", "Phương thức thanh toán"},
        {"combobox", "Mã khuyến mãi"},  //tự đặt là không có khuyến mãi
        {"droplist", "Khách hàng"}  //droplist
    };
    private String[] filter =  {"Tất cả","Mã hóa đơn", "Ngày lập", "Mã tài khoản", "Mã phương thức", "Mã khuyến mãi", "Mã khách hàng"};

    public QLHoaDonForm(String title, MainFrame mainFrame) {
        this.title = title;
        this.mainFrame = mainFrame;
        this.taiKhoan = mainFrame.getTaiKhoan();
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();               
        hoaDonBUS = HoaDonBUS.getInstance();
        this.listAction = getListAction();
        this.khachHangBUS = KhachHangBUS.getInstance();
        this.nhanVienBUS = NhanVienBUS.getInstance();
        init();
    }
    
    private void init() {
        setLayout(new MigLayout("wrap 1, gap 10"));
        dataToShow = Data();

        add(getHeader(),"pushx, growx");
        add(getActions(),"pushx, growx");
        add(getMainContent(),"push,grow, gaptop 15");

    }

    ////////////////////////////////////////////////////////////////////
    public ArrayList<String> getListAction(){
        ArrayList<String> result = new ArrayList<>(); 
        int maNQ = taiKhoan.getMaRole();
        ArrayList<ChiTietQuyenDTO> listCTQ = this.chiTietQuyenBUS.getListChiTietQuyenByMaRoleMaCN(maNQ, id);
        for(ChiTietQuyenDTO i : listCTQ){
            result.add(i.getHanhDong());
        }
        return(result);
    }
    
    private JPanel getHeader() {
        JPanel panel = new JPanel(new MigLayout());
        panel.add(new JLabel(String.format("<html><b><font size='+2'>%s</b></html>", title)),"pushx");
        SearchBarPanel<HoaDonDTO> searchBarPanel = new SearchBarPanel<>(filter, new QLHoaDonSearch(listKH), this::updateTable, resetTable);
        panel.add(searchBarPanel);
        return panel;
    }

    
    ///////////////////////////////////////////////////////////////
    String[][] topActions = {
        {"Thêm","add.svg","add"},
        {"Export Excel","exportExcel.svg","exportExcel"}
    };
    String[][] bottomActions = {
        {"detail.svg","detail"}
    };
    private CustomTable table;

    private JPanel getActions() {
        JPanel panel = new JPanel(new MigLayout("gap 10"));
        panel.putClientProperty(FlatClientProperties.STYLE, "background: #ffffff; arc:5");

        ButtonAction but;
        for (String[] x : getActionTop()) {
            but = new ButtonAction(x[0],x[1],x[2]);
            panel.add(but);
            but.setActionCommand(but.getId());
            but.addActionListener(this);
        }

        return panel;
    }

    public ArrayList<String[]> getActionTop(){
        ArrayList<String[]> arrActions = new ArrayList<>();
        for(String i : listAction){
            if(i.equals("Thêm")){
                arrActions.add(topActions[0]);
            }
        }
        arrActions.add(topActions[1]);  
        return(arrActions);
    }
    
    public ArrayList<String[]> Data(){
        listKH = hoaDonBUS.getAll();
        
        return DataToShow(listKH);
    }

    public ArrayList<String[]> DataToShow(ArrayList<HoaDonDTO> inputData){

        ArrayList<String[]> data = new ArrayList<>();
        KhachHangBUS kh = new KhachHangBUS();
        TaiKhoanBUS tk = new TaiKhoanBUS();

        for(HoaDonDTO i : inputData){
            data.add(new String[]{i.getMaHD() + "", FormatterUtil.formatDateTime(i.getNgayBan()),kh.getTenByMaKhachHang(i.getMaKH()) , FormatterUtil.formatNumberVN(i.getTongTien()),tk.getTenByMaTaiKhoan(i.getMaTK())});
        }
        return(data);
    }
    // {"Mã hóa đơn", "Ngày lập", "Tổng tiền", "Mã tài khoản", "Mã phương thức", "Mã khuyến mãi", "Mã khách hàng"}
    /////////////////////////////////////////////////////////////////



    private JPanel getMainContent() {
        JPanel panel = new JPanel(new MigLayout("insets 0, gap 10"));
        table = new CustomTable(dataToShow,bottomActions, header);
        table.setActionListener(this);
        panel.add(InteractPanel(),"pushy, growy");
        panel.add(table,"push, grow");
        return panel;
    }

    private int widthInteractPanel = 250;

    private JPanel InteractPanel() {
        JPanel panel = new JPanel(new MigLayout("insets 20 10 20 10,wrap 1,gap 10"));
        panel.setPreferredSize(new Dimension(widthInteractPanel,100));
        panel.add(new CustomBoldJLabel("FILTER", 2));
        panel.add(new JLabel("Nhân viên"));
        NhanVienBUS nv = new NhanVienBUS();
        ArrayList<String> listNv = nv.getAllTenNVHaveAccount();
        listNv.addFirst("Tất cả");
        panel.add(new JComboBox<>(listNv.toArray()),"pushx,growx");

        panel.add(new JLabel("Khách hàng"));
        KhachHangBUS kh = new KhachHangBUS();
        ArrayList<String> listNcc = kh.getAllTenKhachHang();
        listNcc.addFirst("Tất cả");

        panel.add(new JComboBox<>(listNcc.toArray()),"pushx, growx");

        panel.add(new JLabel("Từ ngày:"));
        panel.add(new JTextField(),"pushx,grow");   

        panel.add(new JLabel("Đến ngày:"));
        panel.add(new JTextField(),"pushx,grow");
          
        panel.add(new JLabel("Từ số tiền (đ)"));
        panel.add(new JTextField(),"pushx,grow");   

        panel.add(new JLabel("Đến số tiền (đ)"));
        panel.add(new JTextField(),"pushx,grow");         
        
        panel.setBackground(Color.white);

        // JPanel action = new JPanel(new MigLayout());
        // action.setBackground(Color.white);
        // action.add(new JButton("Xuất Excel"));
        // action.add(new JButton("Làm mới"));

        // panel.add(action,"pushx,growx");

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "add":
                mainFrame.glassPane.setVisible(true);
                ButtonAction but = (ButtonAction) e.getSource();
                System.out.println(but.getId()+ but.getText());
                AddHoaDonDialog addHoaDonDialog = new AddHoaDonDialog(mainFrame);
                addHoaDonDialog.getTaoHoaDonForm().setCallBack(new TaoHoaDonForm.GetDataCallBack() {
                    @Override
                    public void setData(HoaDonDTO hoaDon) {
                        String tenKH = khachHangBUS.getTenByMaKhachHang(hoaDon.getMaKH());
                        String tenNV = nhanVienBUS.getTenNVByMaTK(taiKhoan.getMaTK());
                        table.addDataRow(new String[]{
                            hoaDon.getMaHD() + "", 
                            FormatterUtil.formatDateTime(hoaDon.getNgayBan()),
                            tenKH, 
                            FormatterUtil.formatNumberVN(hoaDon.getTongTien()),
                            tenNV
                        });
                    }  
                });
                addHoaDonDialog.setVisible(true);
                mainFrame.glassPane.setVisible(false);
                break;
            case "importExcel":
                
                break;
            case "exportExcel":
                
                break;
            default:
        }
    }
    public void onActionPerformed(String actionId, int row) {
        switch (actionId) {
            case "edit":
                JOptionPane.showMessageDialog(this, "Con bo biet bay");
                break;
            case "remove":
                // Logic xóa cho form này
                int choose = UIUtils.messageRemove("Bạn thực sự muốn xóa?");

                if (choose == 0) {
                    table.removeRow(row);
                    Notifications.getInstance().setJFrame(mainFrame);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Xóa thành công!");
                }
                break;
            default:
                System.out.println("Unknown action: " + actionId);
                break;
        }
    }

    public Runnable resetTable = () -> {
        ArrayList<HoaDonDTO> list = hoaDonBUS.getAll();
        updateTable(list);
    };

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    
    private void updateTable(ArrayList<HoaDonDTO> ketqua) {

        // System.out.println("con bo biet bay");
        table.updateTable(DataToShow(ketqua));
    }
}