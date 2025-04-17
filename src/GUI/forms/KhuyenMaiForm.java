package GUI.forms;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import BUS.ChiTietQuyenBUS;
import BUS.KhuyenMaiBUS;
import DTO.ChiTietQuyenDTO;
import DTO.KhachHangDTO;
import DTO.KhuyenMaiDTO;
import DTO.SachDTO;
import DTO.ViTriVungDTO;
import DTO.TaiKhoanDTO;

import java.util.ArrayList;
import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import GUI.component.TableActionListener;
import GUI.dialog.KhuyenMaiDialog;
import GUI.component.search.SearchBarPanel;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import search.KhuyenMaiSearch;
import search.SachSearch;
import utils.DateCalculator;
import utils.UIUtils;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class KhuyenMaiForm extends JPanel implements TableActionListener, ActionListener{

    private String title;
    private int id = 11;
    private String[] header = {"Mã khuyến mãi","Tên khuyến mãi","Điều kiện giảm","Giá trị giảm", "Ngày bắt đầu", "Ngày kết thúc"};
    KhuyenMaiBUS khuyenMaiBUS;
    private MainFrame mainFrame;
    private CustomTable table;
    private ArrayList<KhuyenMaiDTO> listKH;
    private ArrayList<String[]> dataToShow;
    private TaiKhoanDTO taiKhoan;
    private String[] filter = {"Tất cả","Mã khuyến mãi","Tên khuyến mãi","Điều kiện giảm","Giá trị giảm", "Ngày bắt đầu", "Ngày kết thúc"};

    private ArrayList<String> listAction;
    private ChiTietQuyenBUS chiTietQuyenBUS;

    private String[][] attributes = {
        {"textbox","Tên khuyến mãi"},
        {"textbox","Điều kiện giảm"},
        {"textbox", "Giá trị giảm"},
        {"inputDateTime", "Ngày bắt đầu"},//làm dateTime
        {"inputDateTime", "Ngày kết thúc"},//làm dateTime
    };


    public KhuyenMaiForm(String title, MainFrame mainFrame) {
        this.title = title;
        this.mainFrame = mainFrame;
        this.taiKhoan = mainFrame.getTaiKhoan();
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();
        this.khuyenMaiBUS = KhuyenMaiBUS.getInstance();   
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
        SearchBarPanel<KhuyenMaiDTO> searchBarPanel = new SearchBarPanel<>(filter, new KhuyenMaiSearch(listKH), this::updateTable, resetTable);
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
        listKH = khuyenMaiBUS.getAll();
        
        return DataToShow(listKH);
    }

    public ArrayList<String[]> DataToShow(ArrayList<KhuyenMaiDTO> inputData){

        ArrayList<String[]> data = new ArrayList<>();
        for(KhuyenMaiDTO i : inputData){
            data.add(new String[]{
                i.getMaKM() + "",
                i.getTenKM(),
                i.getDieuKienGiam(),
                i.getGiaTriGiam() + "",
                new DateCalculator().formatDateTimeToDateS(i.getNgayBatDau()),
                new DateCalculator().formatDateTimeToDateS(i.getNgayBatDau())
            });      
        }
        return(data);
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
                KhuyenMaiDialog khuyenMaiDialog = new KhuyenMaiDialog(this, "Khuyến mãi", "Thêm Khuyến Mãi", "add", attributes);
                khuyenMaiDialog.setVisible(true);
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
            KhuyenMaiDialog khuyenMaiDialog = new KhuyenMaiDialog(this, "Khuyến mãi", "Sửa Khuyến Mãi", "update", attributes, row);
            khuyenMaiDialog.setVisible(true);
            break;
            case "remove":
                // Logic xóa cho form này
                int choose = UIUtils.messageRemove("Bạn thực sự muốn xóa?");

                int ma = Integer.parseInt(table.getCellData(row, 0));
                if (choose == 0) {
                    if(khuyenMaiBUS.delete(ma) != 0){
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

    public Runnable resetTable = () -> {
        ArrayList<KhuyenMaiDTO> list = khuyenMaiBUS.getAll();
        updateTable(list);
    };

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public KhuyenMaiBUS getKhuyenMaiBUS() {
        return khuyenMaiBUS;
    }

    public void setKhuyenMaiBUS(KhuyenMaiBUS khuyenMaiBUS) {
        this.khuyenMaiBUS = khuyenMaiBUS;
    }

    public CustomTable getTable() {
        return table;
    }

    public void setTable(CustomTable table) {
        this.table = table;
    }

    
 
    private void updateTable(ArrayList<KhuyenMaiDTO> ketqua) {

        // System.out.println("con bo biet bay");
        table.updateTable(DataToShow(ketqua));
    }
}