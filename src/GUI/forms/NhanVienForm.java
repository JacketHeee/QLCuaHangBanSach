package GUI.forms;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.ChiTietQuyenBUS;
import BUS.NhanVienBUS;
import DTO.ChiTietQuyenDTO;
import DTO.NhanVienDTO;
import DTO.NhanVienDTO;
import DTO.TaiKhoanDTO;

import java.util.ArrayList;
import java.util.List;

import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import GUI.component.TableActionListener;
import GUI.component.search.SearchBarPanel;
import GUI.dialog.NhanVienDialog;
import excel.NhanVienExcelExport;
import excel.NhanVienExcelImport;
import excel.TaiKhoanExcelImport;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import search.NhanVienSearch;
import search.NhanVienSearch;
import utils.DateCalculator;
import utils.ExcelExporter;
import utils.ExcelImporter;
import utils.UIUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NhanVienForm extends JPanel implements TableActionListener, ActionListener{

    private SearchBarPanel<NhanVienDTO> searchBarPanel;
    private List<NhanVienDTO> filteredList = new ArrayList<>();

    private String title;
    private int id = 11;
    private String[] header = {"Mã nhân viên","Họ tên","Ngày sinh","Giới tính","Số điện thoại","Mã tài khoản"};
    NhanVienBUS nhanVienBUS;
    private MainFrame mainFrame;
    private ArrayList<NhanVienDTO> listKH;
    private ArrayList<String[]> dataToShow;
    private TaiKhoanDTO taiKhoan;
    private ArrayList<String> listAction;
    private ChiTietQuyenBUS chiTietQuyenBUS;
    private String[][] attributes = {
        {"textbox","Tên nhân viên"},
        {"inputDate","Ngày sinh"},
        {"combobox", "Giới tính"},
        {"textbox", "Số điện thoại"}
    };
    private String[] filter = {"Tất cả", "Mã nhân viên","Họ tên","Ngày sinh","Giới tính","Số điện thoại","Mã tài khoản"};


    public NhanVienForm(String title, MainFrame mainFrame) {
        this.title = title;
        this.mainFrame = mainFrame;
        this.taiKhoan = mainFrame.getTaiKhoan();
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();

        nhanVienBUS = NhanVienBUS.getInstance();
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
        panel.add(new JLabel(String.format("<html><b><font size='+2'>%s</b></html>", title)), "pushx");
    
        // GÁN ĐÚNG cho biến thành viên
        this.searchBarPanel = new SearchBarPanel<>(filter, new NhanVienSearch(listKH), this::updateTable, resetTable);
    
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
        listKH = nhanVienBUS.getAll();
        
       return DataToShow(listKH);
    }

    public ArrayList<String[]> DataToShow(ArrayList<NhanVienDTO> inputData){

        ArrayList<String[]> data = new ArrayList<>();
        for(NhanVienDTO i : inputData){
            data.add(new String[]{i.getMaNV() + "", 
            i.getHoTen(), 
            new DateCalculator().formatDateToDateS(i.getNgaySinh()), 
            i.getGioiTinh(), 
            i.getSoDT(), 
            i.getMaTK() + ""});
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
        table = new CustomTable(Data(),getActionBottom(), header);
        table.setActionListener(this);
        panel.add(new CustomScrollPane(table),"push, grow");
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainFrame.glassPane.setVisible(true);
        switch (e.getActionCommand()) {
            case "add":
            NhanVienDialog nhanVienDialog = new NhanVienDialog(this, "Nhân viên", "Thêm Nhân Viên", "add", attributes);
            nhanVienDialog.setVisible(true);
                break;
            case "importExcel":
                List<NhanVienDTO> importedData = ExcelImporter.importFromExcel(new NhanVienExcelImport());

                if (importedData != null && !importedData.isEmpty()) {
                   int count = 0;
                   for (NhanVienDTO nv : importedData) {
                       if (nhanVienBUS.insert(nv) != 0) {
                           count++;
                       }
                   }
                   Notifications.getInstance().setJFrame(mainFrame);
                   Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,
                       "Import thành công!");
                   updateTable(nhanVienBUS.getAll());
              }
                break;
            case "exportExcel":
                String keyword = searchBarPanel.getSearchField().getText().trim();
                String filterCol = searchBarPanel.getComboBox().getSelectedItem().toString();

                List<NhanVienDTO> dataToExport = (filteredList != null && !filteredList.isEmpty())
                    ? filteredList
                    : nhanVienBUS.getAll();

                NhanVienExcelExport exporter = new NhanVienExcelExport(dataToExport, filterCol, keyword);
                ExcelExporter.exportToExcel(exporter, NhanVienDTO.class);
                break;
            default:
        }
        mainFrame.glassPane.setVisible(false);
    }
    public void onActionPerformed(String actionId, int row) {
        mainFrame.glassPane.setVisible(true);
        switch (actionId) {
            case "edit":
                NhanVienDialog nhanVienDialog = new NhanVienDialog(this, "Nhân viên", "Sửa Nhân Viên", "update", attributes, row);
                nhanVienDialog.setVisible(true);
                break;
            case "remove":
                // Logic xóa cho form này
                int choose = UIUtils.messageRemove("Bạn thực sự muốn xóa?");

                int ma = Integer.parseInt(table.getCellData(row, 0));
                if (choose == 0) {
                    if(nhanVienBUS.delete(ma) != 0){
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

        mainFrame.glassPane.setVisible(false);
    }

    private void updateTable(ArrayList<NhanVienDTO> ketqua) {

        this.filteredList = ketqua;
        table.updateTable(DataToShow(ketqua));
    }

    public Runnable resetTable = () -> {
        ArrayList<NhanVienDTO> list = nhanVienBUS.getAll();
        updateTable(list);
    };

    public NhanVienBUS getNhanVienBUS() {
        return nhanVienBUS;
    }

    public void setNhanVienBUS(NhanVienBUS nhanVienBUS) {
        this.nhanVienBUS = nhanVienBUS;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public CustomTable getTable() {
        return table;
    }

    public void setTable(CustomTable table) {
        this.table = table;
    }

    
}