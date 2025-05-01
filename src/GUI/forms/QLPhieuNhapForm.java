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
import GUI.component.CustomButton;
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
import utils.Validate;

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

    private InputForm inputFormSearch;

    String[][] listIFI = {
        {"combobox","Nhân viên"},
        {"combobox","Nhà cung cấp"},
        {"inputDate","Từ ngày"},
        {"inputDate","Đến ngày"},
        {"inputSL","Từ số tiền(đ)"},
        {"inputSL","Đến số tiền(đ)"}
    };

    private CustomButton btnXacNhan;
    private QLPhieuNhapSearch qlPhieuNhapSearch;

    public QLPhieuNhapForm(String title, MainFrame mainFrame) {
        this.title = title;
        this.mainFrame = mainFrame;
        this.taiKhoan = mainFrame.getTaiKhoan();
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance(); 
        this.nhanVienBUS = NhanVienBUS.getInstance();
        this.nhaCungCapBUS = NhaCungCapBUS.getInstance();              

        phieuNhapBUS = PhieuNhapBUS.getInstance();
        this.inputFormSearch = new InputForm(listIFI);
        this.listAction = getListAction();
        this.btnXacNhan = new CustomButton("Xác nhận");
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

        qlPhieuNhapSearch = new QLPhieuNhapSearch(listKH);

        SearchBarPanel<PhieuNhapDTO> searchBarPanel = new SearchBarPanel<>(filter, qlPhieuNhapSearch, this::updateTable, resetTable);
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


        ArrayList<String> listNVtemp = nhanVienBUS.getAllTenNVJoined(); //Lấy cả  những nhân viên đã nghỉ làm
        listNVtemp.addFirst("Tất cả");
        String[] listNV = listNVtemp.toArray(new String[0]);
        ArrayList<String> listNCCtemp = nhaCungCapBUS.getAllTenNhaCungCap();
        listNCCtemp.addFirst("Tất cả");
        String[] listNCC = listNCCtemp.toArray(new String[0]);
        inputFormSearch.getListItem().get(0).setListCombobox(listNV);
        inputFormSearch.getListItem().get(1).setListCombobox(listNCC);
        

        // panel.add(new JComboBox<>(listNcc.toArray()),"pushx, growx");

        // panel.add(new JLabel("Từ ngày:"));
        // panel.add(new JTextField(),"pushx,grow");   

        // panel.add(new JLabel("Đến ngày:"));
        // panel.add(new JTextField(),"pushx,grow");    

        // panel.add(new JLabel("Từ số tiền (đ)"));
        // panel.add(new JTextField(),"pushx,grow");   

        // panel.add(new JLabel("Đến số tiền (đ)"));
        // panel.add(new JTextField(),"pushx,grow");        
        
        panel.add(inputFormSearch, "pushx, grow");
        panel.add(btnXacNhan, "pushx, align right, gapright 20");
        panel.setBackground(Color.white);

        // JPanel action = new JPanel(new MigLayout());
        // action.setBackground(Color.white);
        // action.add(new JButton("Xuất Excel"));
        // action.add(new JButton("Làm mới"));

        // panel.add(action,"pushx,growx");

        btnXacNhan.setActionCommand("xac nhan");
        setListenerBtnXacNhan();

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainFrame.glassPane.setVisible(true);
        switch (e.getActionCommand()) {
            case "add":
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
                break;
            case "importExcel":
                
                break;
            case "exportExcel":
                
                break;
            default:
        }
        mainFrame.glassPane.setVisible(false);
    }

    @Override
    public void onActionPerformed(String actionId, int row) {
        mainFrame.glassPane.setVisible(true);
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
                int maNhap = Integer.parseInt(this.table.getCellData(row, 0));
                PhieuNhapDTO phieuNhap = phieuNhapBUS.getInstanceByID(maNhap);
                AddPhieuNhapDialog addPhieuNhapDialog = new AddPhieuNhapDialog(mainFrame, phieuNhap);
                addPhieuNhapDialog.setVisible(true);
                mainFrame.glassPane.setVisible(false);
                break;
            default:
                System.out.println("Unknown action: " + actionId);
                break;
        }
        mainFrame.glassPane.setVisible(false);
    }

    public void setListenerBtnXacNhan(){
        this.btnXacNhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Loc();
            }
        });
    }

        public void Loc(){
        String selectNV = this.inputFormSearch.getListItem().get(0).getSelection();
        String selectNCC = this.inputFormSearch.getListItem().get(1).getSelection();
        String ngayBatDauS = this.inputFormSearch.getListItem().get(2).getDateString();
        String ngayKetThucS = this.inputFormSearch.getListItem().get(3).getDateString();
        String tienFst = this.inputFormSearch.getListItem().get(4).getTextSL();
        String tienScnd = this.inputFormSearch.getListItem().get(5).getTextSL();

        if(Validation(ngayBatDauS, ngayKetThucS, tienFst, tienScnd)){
            ArrayList<PhieuNhapDTO> currentList = qlPhieuNhapSearch.getDanhSachHienTai();//list theo thanh tìm kiếm
            ArrayList<PhieuNhapDTO> updateList;    //làm theo kiểu lọc bộ lọc đầu -> delete bộ lọc phía sau
            //Xét mã nhân viên
                if(selectNV.equals("Tất cả")){
                    updateList = new ArrayList<>(currentList);  //Copy sang, không xài tham chiếu
                }
                else{
                    updateList = new ArrayList<>();
                    for(PhieuNhapDTO i : currentList){
                        String tenNV = nhanVienBUS.getTenNVByMaTK(i.getMaTK());
                        if(tenNV.equals(selectNV)){
                            updateList.add(i);
                        }
                    }   
                }
            //Xét mã nhà cung cấp
                if(!selectNCC.equals("Tất cả")){
                    for(int i = updateList.size() - 1 ; i >= 0; i--){   //quá hay
                        String tenNCC = nhaCungCapBUS.getTenByMaNhaCungCap(updateList.get(i).getMaNCC());
                        if(!tenNCC.equals(selectNCC)){
                            updateList.remove(i);
                        }
                    }
                }//Nếu là tất cả thì giữ nguyên đứa cũ
            //Xét ngày bắt đầu ngày kết thúc
                if(!Validate.isEmpty(ngayBatDauS) && !Validate.isEmpty(ngayKetThucS)){
                    for(int i = updateList.size() - 1; i >= 0; i--){
                        if(!Validate.isBetweenStartDateAndEndDate(ngayBatDauS, ngayKetThucS, updateList.get(i).getNgayNhap())){
                            updateList.remove(i);
                        }
                    }
                }//Nếu là tất cả thì giữ nguyên đứa cũ
            //Xét giá bắt đầu, giá kết thúc
                if(!tienFst.equals("0") && !tienScnd.equals("0")){
                    for(int i = updateList.size() - 1; i >= 0; i--){
                        if(!Validate.isBetweenTuGiaDenGia(tienFst, tienScnd, updateList.get(i).getTongTien())){
                            updateList.remove(i);
                        }
                    }
                }

                updateTable(updateList);
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

        table.updateTable(DataToShow(ketqua));
    }

    public boolean Validation(String ngayBatDauS, String ngayKetThucS, String tienFst, String tienScnd){
        if(!Validate.isEmpty(ngayBatDauS) && !Validate.isDate(ngayBatDauS)){
            JOptionPane.showMessageDialog(mainFrame, "Ngày bắt đầu phải đúng định dạng");
            return(false);
        }
        else if(!Validate.isEmpty(ngayKetThucS) && !Validate.isDate(ngayKetThucS)){
            JOptionPane.showMessageDialog(mainFrame, "Ngày kết thúc phải đúng định dạng");
            return(false);
        }
        else if(Validate.isEmpty(ngayBatDauS) && !Validate.isEmpty(ngayKetThucS)){
            JOptionPane.showMessageDialog(mainFrame, "Vui lòng nhập ngày bắt đầu");
            return(false);
        }
        else if(!Validate.isEmpty(ngayBatDauS) && Validate.isEmpty(ngayKetThucS)){
            JOptionPane.showMessageDialog(mainFrame, "Vui lòng nhập ngày kết thúc");
            return(false);
        }
        else if(!Validate.isEmpty(ngayBatDauS) && !Validate.isEmpty(ngayKetThucS) && !Validate.isStartDateAndEndDate(ngayBatDauS, ngayKetThucS)){
            JOptionPane.showMessageDialog(mainFrame, "Ngày bắt đầu phải trước ngày kết thúc");
            return(false);
        }
        else if(tienFst.equals("0") && !tienScnd.equals("0")){
            JOptionPane.showMessageDialog(mainFrame, "Vui lòng nhập tiền vào ô từ số tiền(đ)");
            return(false);
        }
        else if(!tienFst.equals("0") && tienScnd.equals("0")){
            JOptionPane.showMessageDialog(mainFrame, "Vui lòng nhập tiền vào ô đến số tiền(đ)");
            return(false);
        }
        else if(!tienScnd.equals("0") && !tienFst.equals("0") && !Validate.isGiaBatDauGiaKetThuc(tienFst, tienScnd)){
            JOptionPane.showMessageDialog(mainFrame, "Tiền (Từ số tiền) phải nhỏ hơn tiền (Tổng số tiền)");
            return(false);
        }
        return(true);
    }
}