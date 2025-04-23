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
import BUS.NhanVienBUS;
import BUS.NhomQuyenBUS;
import BUS.TaiKhoanBUS;
import DTO.SachDTO;
import DTO.ChiTietQuyenDTO;
import DTO.KhuyenMaiDTO;
import DTO.TaiKhoanDTO;
import DTO.ViTriVungDTO;

import java.util.ArrayList;
import java.util.List;

import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import GUI.component.TableActionListener;
import GUI.component.search.SearchBarPanel;
import GUI.dialog.KhachHangDialog;
import GUI.dialog.TaiKhoanDialog;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import search.SachSearch;
import search.TaiKhoanSearch;
import utils.UIUtils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class TaiKhoanForm extends JPanel implements TableActionListener, ActionListener {

    private String title;
    private int id = 12;
    private String[] header = {"Mã tài khoản","Username","Password","Quyền"};
    private TaiKhoanBUS taiKhoanBUS;
    private MainFrame mainFrame;
    private ArrayList<TaiKhoanDTO> listKH;
    private ArrayList<String[]> dataToShow;
    private TaiKhoanDTO taiKhoan;
    private ArrayList<String> listAction;
    private ChiTietQuyenBUS chiTietQuyenBUS;
    private NhanVienBUS nhanVienBUS;
    private CustomTable table;
    private NhomQuyenBUS nhomQuyenBUS;

    private String[] filter = {"Mã tài khoản","Username","Password","Quyền"};


    private String[][] attributes = {
        {"textbox","Tên đăng nhập"},
        {"textbox","Mật khẩu"},
        {"combobox","Nhân Viên"},
        {"combobox", "Nhóm quyền"}
    };

    public TaiKhoanForm(String title, MainFrame mainFrame) {
        this.title = title;
        this.mainFrame = mainFrame;
        this.taiKhoan = mainFrame.getTaiKhoan();
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();               
        this.taiKhoanBUS = TaiKhoanBUS.getInstance();
        this.nhanVienBUS = NhanVienBUS.getInstance();
        this.nhomQuyenBUS = NhomQuyenBUS.getInstance();
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
        SearchBarPanel<TaiKhoanDTO> searchBarPanel = new SearchBarPanel<>(filter, new TaiKhoanSearch(listKH), this::updateTable, resetTable);
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
        {"edit.svg","edit"},
        {"remove.svg","remove"}
    };

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
    public String[][] getActionBottom(){
        ArrayList<String[]> arrActions = new ArrayList<>();
        for(String i : listAction){
            if(i.equals("Sửa")){
                arrActions.add(bottomActions[0]);
            }
            else if(i.equals("Xóa")){
                arrActions.add(bottomActions[1]);
            }
        }
        String[][] array = arrActions.toArray(new String[0][]);
        return(array);
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
        listKH = taiKhoanBUS.getAll();
        
        return DataToShow(listKH);
    }

    public ArrayList<String[]> DataToShow(ArrayList<TaiKhoanDTO> inputData){

        ArrayList<String[]> data = new ArrayList<>();
        for(TaiKhoanDTO i : inputData){
            String tenNhomQuyen = nhomQuyenBUS.getTenByMaNhomQuyen(i.getMaRole());
            data.add(new String[]{i.getMaTK() + "", i.getUsername(), i.getPassword(), tenNhomQuyen});
        }
        return(data);
    }

    /////////////////////////////////////////////////////////////////



    private JPanel getMainContent() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        table = new CustomTable(dataToShow,getActionBottom(), header);
        table.setActionListener(this);
        panel.add(new CustomScrollPane(table),"push, grow");
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "add":
                ArrayList<String> listNV = nhanVienBUS.getAllTenNVNotHaveAccount();
                if(listNV.size() == 0){
                    JOptionPane.showMessageDialog(mainFrame, "Mọi nhân viên đã được thêm tài khoản, vui lòng thêm nhân viên!");
                }
                else{
                    TaiKhoanDialog taiKhoanDialog = new TaiKhoanDialog(this, "Tài khoản", "Thêm tài khoản", "add", attributes);
                    taiKhoanDialog.setVisible(true);
                }
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
                TaiKhoanDialog taiKhoanDialog = new TaiKhoanDialog(this, "Tài khoản", "Sửa tài khoản", "update", attributes, row);
                taiKhoanDialog.setVisible(true);
                break;
            case "remove":
                // Logic xóa cho form này
                int choose = UIUtils.messageRemove("Bạn thực sự muốn xóa?");

                int ma = Integer.parseInt(table.getCellData(row, 0));
                if (choose == 0) {
                    if(taiKhoanBUS.delete(ma) != 0){
                        table.removeRow(row);
                        Notifications.getInstance().setJFrame(mainFrame);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Xóa thành công!");
                    }
                    else{
                        JOptionPane.showMessageDialog(mainFrame, "Xóa thất bại!");
                    }
                }
                break;
            default:
                System.out.println("Unknown action: " + actionId);
                break;
        }
    }


    private void updateTable(ArrayList<TaiKhoanDTO> ketqua) {

        // System.out.println("con bo biet bay");
        table.updateTable(DataToShow(ketqua));
    }

    public Runnable resetTable = () -> {
        ArrayList<TaiKhoanDTO> list = taiKhoanBUS.getAll();
        updateTable(list);
    };
    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public ChiTietQuyenBUS getChiTietQuyenBUS() {
        return chiTietQuyenBUS;
    }

    public void setChiTietQuyenBUS(ChiTietQuyenBUS chiTietQuyenBUS) {
        this.chiTietQuyenBUS = chiTietQuyenBUS;
    }

    public TaiKhoanBUS getTaiKhoanBUS() {
        return taiKhoanBUS;
    }

    public void setTaiKhoanBUS(TaiKhoanBUS taiKhoanBUS) {
        this.taiKhoanBUS = taiKhoanBUS;
    }

    public CustomTable getTable() {
        return table;
    }

    public void setTable(CustomTable table) {
        this.table = table;
    }

    public NhanVienBUS getNhanVienBUS() {
        return nhanVienBUS;
    }

    public void setNhanVienBUS(NhanVienBUS nhanVienBUS) {
        this.nhanVienBUS = nhanVienBUS;
    }

    public NhomQuyenBUS getNhomQuyenBUS() {
        return nhomQuyenBUS;
    }

    public void setNhomQuyenBUS(NhomQuyenBUS nhomQuyenBUS) {
        this.nhomQuyenBUS = nhomQuyenBUS;
    }

    
}