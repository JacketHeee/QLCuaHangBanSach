package GUI.forms;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.ChiTietQuyenBUS;
import BUS.KhachHangBUS;
import DTO.ChiTietQuyenDTO;
import DTO.KhachHangDTO;
import DTO.TaiKhoanDTO;

import java.util.ArrayList;
import java.util.List;

import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import GUI.component.TableActionListener;
import GUI.component.search.SearchBarPanel;
import GUI.dialog.KhachHangDialog;
import excel.KhachHangExcelExport;
import excel.KhachHangExcelImport;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import search.KhachHangSearch;
import utils.ExcelExporter;
import utils.ExcelImporter;
import utils.UIUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class KhachHangForm extends JPanel implements TableActionListener, ActionListener{

    private SearchBarPanel<KhachHangDTO> searchBarPanel;
    private List<KhachHangDTO> filteredList = new ArrayList<>();

    private String title;
    private int id = 13;
    private KhachHangBUS khachHangBUS;
    private String[] header = {"Mã khách hàng","Tên khách hàng","Số điện thoại","Giới tính"};
    private MainFrame mainFrame;
    private ArrayList<KhachHangDTO> listKH;
    private ArrayList<String[]> dataToShow;
    private CustomTable table;
    private ArrayList<String> listAction;
    private TaiKhoanDTO taiKhoan;
    private ChiTietQuyenBUS chiTietQuyenBUS;
    private String[] filter = {"Tất cả","Mã khách hàng","Tên khách hàng","Số điện thoại","Giới tính"};

    private String[][] attributes = {
        {"textbox","Tên khách hàng"},
        {"textbox","Số điện thoại"},
        {"combobox", "Giới tính"}
    };

    public KhachHangForm(String title, MainFrame mainFrame) {
        this.title = title;
        this.mainFrame = mainFrame;
        this.taiKhoan = mainFrame.getTaiKhoan();
        this.khachHangBUS = KhachHangBUS.getInstance();
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();
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
    private JPanel getHeader() {
        JPanel panel = new JPanel(new MigLayout());
        panel.add(new JLabel(String.format("<html><b><font size='+2'>%s</b></html>", title)), "pushx");
    
        // GÁN ĐÚNG cho biến thành viên
        this.searchBarPanel = new SearchBarPanel<>(filter, new KhachHangSearch(listKH), this::updateTable, resetTable);
    
        panel.add(searchBarPanel);
        return panel;
    }
 


    ///////////////////////////////////////////////////////////////

    public ArrayList<String> getListAction(){
        ArrayList<String> result = new ArrayList<>(); 
        int maNQ = taiKhoan.getMaRole();
        ArrayList<ChiTietQuyenDTO> listCTQ = this.chiTietQuyenBUS.getListChiTietQuyenByMaRoleMaCN(maNQ, id);
        for(ChiTietQuyenDTO i : listCTQ){
            result.add(i.getHanhDong());
        }
        return(result);
    }

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

    private JPanel getMainContent() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        table = new CustomTable(Data(),getActionBottom(), header);
        table.setActionListener(this);
        panel.add(new CustomScrollPane(table),"push, grow");
        return panel;
    }

    public ArrayList<String[]> Data(){
        listKH = khachHangBUS.getAll();
        
       return DataToShow(listKH);
    }

    public ArrayList<String[]> DataToShow(ArrayList<KhachHangDTO> inputData){

        ArrayList<String[]> data = new ArrayList<>();
        for(KhachHangDTO i : inputData){
            data.add(new String[]{i.getMaKH() + "",i.getTenKH(),i.getSoDT(),i.getGioiTinh()});
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

    @Override
    public void actionPerformed(ActionEvent e) {
        mainFrame.glassPane.setVisible(true);
        switch (e.getActionCommand()) {
            case "add":
                KhachHangDialog khachHangDialog = new KhachHangDialog(this, "Khách hàng", "Thêm Khách Hàng", "add", attributes);
                khachHangDialog.setVisible(true);
                break;
            case "importExcel":
                List<KhachHangDTO> importedData = ExcelImporter.importFromExcel(new KhachHangExcelImport());

                if (importedData != null && !importedData.isEmpty()) {
                   int count = 0;
                   for (KhachHangDTO kh : importedData) {
                       if (khachHangBUS.insert(kh) != 0) {
                           count++;
                       }
                   }
                   Notifications.getInstance().setJFrame(mainFrame);
                   Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,
                       "Import thành công!");
                   updateTable(khachHangBUS.getAll());
              }
                break;
           
            case "exportExcel":
                String keyword = searchBarPanel.getSearchField().getText().trim();
                String filterCol = searchBarPanel.getComboBox().getSelectedItem().toString();

                List<KhachHangDTO> dataToExport = (filteredList != null && !filteredList.isEmpty())
                    ? filteredList
                    : khachHangBUS.getAll();

                KhachHangExcelExport exporter = new KhachHangExcelExport(dataToExport, filterCol, keyword);
                ExcelExporter.exportToExcel(exporter, KhachHangDTO.class);
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
                KhachHangDialog khachHangDialog = new KhachHangDialog(this, "Khách hàng", "Sửa Khách Hàng", "update", attributes, row);
                khachHangDialog.setVisible(true);
                break;
            case "remove":
                // Logic xóa cho form này
                int choose = UIUtils.messageRemove("Bạn thực sự muốn xóa?");
                int ma = Integer.parseInt(table.getCellData(row, 0));
                if (choose == 0) {
                    if(khachHangBUS.delete(ma) != 0){
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


    private void updateTable(ArrayList<KhachHangDTO> ketqua) {
        this.filteredList = ketqua;
        // System.out.println("con bo biet bay");
        table.updateTable(DataToShow(ketqua));
        // for (KhachHangDTO x: ketqua) {
        //     for (String y : new String[]{x.getMaKH() + "",x.getTenKH(),x.getSoDT(),x.getGioiTinh()}){}
        //     //     System.out.print(y);
        //     // System.out.println();
        // }

    }

    public Runnable resetTable = () -> {
        ArrayList<KhachHangDTO> list = khachHangBUS.getAll();
        updateTable(list);
    };

    public KhachHangBUS getKhachHangBUS() {
        return khachHangBUS;
    }

    public void setKhachHangBUS(KhachHangBUS khachHangBUS) {
        this.khachHangBUS = khachHangBUS;
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
    
}