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
import BUS.KhachHangBUS;
import BUS.NhaCungCapBUS;
import BUS.NhanVienBUS;
import BUS.PhieuNhapBUS;
import DTO.HoaDonDTO;
import DTO.KhuyenMaiDTO;
import DTO.PhieuNhapDTO;
import DTO.SachDTO;
import DTO.ViTriVungDTO;
import DTO.ChiTietQuyenDTO;
import DTO.PhieuNhapDTO;
import DTO.TaiKhoanDTO;

import java.util.ArrayList;
import java.util.List;

import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomBoldJLabel;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import GUI.component.InputForm;
import GUI.component.TableActionListener;
import GUI.component.search.SearchBarPanel;
import GUI.dialog.AddHoaDonDialog;
import GUI.dialog.AddPhieuNhapDialog;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import search.QLPhieuNhapSearch;
import search.SachSearch;
import utils.FormatterUtil;
import utils.UIUtils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class QLPhieuNhapForm extends JPanel implements TableActionListener, ActionListener {

    private String title;
    private int id = 7;
    private String[] header = {"Mã nhập","Ngày nhập", "Nhà cung cấp", "Tổng tiền"};
    PhieuNhapBUS phieuNhapBUS;
    private MainFrame mainFrame;
    private ArrayList<PhieuNhapDTO> listKH;
    private ArrayList<String[]> dataToShow;
    private TaiKhoanDTO taiKhoan;
    private ArrayList<String> listAction;
    private ChiTietQuyenBUS chiTietQuyenBUS;
    private NhanVienBUS nhanVienBUS;
    private NhaCungCapBUS nhaCungCapBUS;
    private String[][] attributes = {
        {"inputDate","Ngày nhập"},   //tự get
        {"textbox", "Tổng tiền"},  
        {"combobox", "Mã nhà cung cáp"},    
        {"textbox", "Mã tài khoản"},    //tự get
    };
    private String[] filter = {"Tất cả","Mã nhập","Ngày nhập","Mã nhà cung cấp","Mã tài khoản"};

    private String[] listInputFormItem;
    private InputForm inputFormSearch;


    public QLPhieuNhapForm(String title, MainFrame mainFrame) {
        this.title = title;
        this.mainFrame = mainFrame;
        this.taiKhoan = mainFrame.getTaiKhoan();
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance(); 
        this.nhanVienBUS = NhanVienBUS.getInstance();
        this.nhaCungCapBUS = NhaCungCapBUS.getInstance();              

        phieuNhapBUS = PhieuNhapBUS.getInstance();
        this.listAction = getListAction();
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
        SearchBarPanel<PhieuNhapDTO> searchBarPanel = new SearchBarPanel<>(filter, new QLPhieuNhapSearch(listKH), this::updateTable, resetTable);
        panel.add(searchBarPanel);
        return panel;
    }

    ///////////////////////////////////////////////////////////////
    String[][] topActions = {
        {"Thêm","add.svg","add"},
        {"Import Excel","importExcel.svg","importExcel"},
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
        arrActions.add(topActions[2]);
        return(arrActions);
    }
    
    public ArrayList<String[]> Data(){
        listKH = phieuNhapBUS.getAll();
        
        return DataToShow(listKH);
    }

    public ArrayList<String[]> DataToShow(ArrayList<PhieuNhapDTO> inputData){

        ArrayList<String[]> data = new ArrayList<>();
        NhaCungCapBUS ncc = new NhaCungCapBUS();
        NhanVienBUS nv = new NhanVienBUS();
        for(PhieuNhapDTO i : inputData){
            data.add(new String[]{i.getMaNhap() + "", FormatterUtil.formatDateTime(i.getNgayNhap()) + "",ncc.getTenByMaNhaCungCap(i.getMaNCC()),FormatterUtil.formatNumberVN(i.getTongTien()),nv.getTenNVByMaTK(i.getMaTK())});
        }
        return(data);
    }
    // {"Mã nhập","Ngày nhập","Tổng tiền","Mã nhà cung cấp","Mã tài khoản"};
    /////////////////////////////////////////////////////////////////

    // private JPanel getMainContent() {
    //     JPanel panel = new JPanel(new MigLayout("insets 0"));
    //     table = new CustomTable(Data(),bottomActions, header);
    //     table.setActionListener(this);
    //     panel.add(new CustomScrollPane(table),"push, grow");
    //     return panel;
    // }
    private JPanel getMainContent() {
        JPanel panel = new JPanel(new MigLayout("insets 0, gap 10"));
        table = new CustomTable(dataToShow,bottomActions, header);
        table.setMaxTextWidth(85);
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

        panel.add(new JLabel("Nhà cung cấp"));
        NhaCungCapBUS kh = new NhaCungCapBUS();
        ArrayList<String> listNcc = kh.getAllTenNhaCungCap();
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
                AddPhieuNhapDialog addPhieuNhapDialog = new AddPhieuNhapDialog(mainFrame);
                addPhieuNhapDialog.getTaoPhieuNhapForm().setCallBack(new TaoPhieuNhapForm.GetDataCallBack() {
                    @Override
                    public void setData(PhieuNhapDTO phieuNhap) {
                        String tenNCC = nhaCungCapBUS.getTenByMaNhaCungCap(phieuNhap.getMaNCC());
                        String tenNV = nhanVienBUS.getTenNVByMaTK(phieuNhap.getMaTK());
                        table.addDataRow(new String[] {
                            phieuNhap.getMaNhap() + ""
                            , FormatterUtil.formatDateTime(phieuNhap.getNgayNhap())
                            , tenNCC
                            , FormatterUtil.formatNumberVN(phieuNhap.getTongTien())
                        });
                    }  
                });
                addPhieuNhapDialog.setVisible(true);
                mainFrame.glassPane.setVisible(false);
                break;
            case "importExcel":
                
                break;
            case "exportExcel":
                
                break;
            default:
        }
    }

    @Override
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
            case "detail":
                break;
            default:
                System.out.println("Unknown action: " + actionId);
                break;
        }
    }

    public Runnable resetTable = () -> {
        ArrayList<PhieuNhapDTO> list = phieuNhapBUS.getAll();
        updateTable(list);
    };

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    
    private void updateTable(ArrayList<PhieuNhapDTO> ketqua) {

        // System.out.println("con bo biet bay");
        table.updateTable(DataToShow(ketqua));
    }
}