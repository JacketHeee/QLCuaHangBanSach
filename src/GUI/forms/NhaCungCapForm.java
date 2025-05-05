package GUI.forms;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.ChiTietQuyenBUS;
import BUS.NhaCungCapBUS;
import DTO.ChiTietQuyenDTO;
import DTO.NhaCungCapDTO;
import DTO.TaiKhoanDTO;

import java.util.ArrayList;
import java.util.List;

import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import GUI.component.TableActionListener;
import GUI.dialog.NhaCungCapDialog;
import excel.NhaCungCapExcelExport;
import excel.NhaCungCapExcelImport;
import GUI.component.search.SearchBarPanel;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import search.NhaCungCapSearch;
import utils.ExcelExporter;
import utils.ExcelImporter;
import utils.UIUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NhaCungCapForm extends JPanel implements TableActionListener, ActionListener {

    private SearchBarPanel<NhaCungCapDTO> searchBarPanel;
    private List<NhaCungCapDTO> filteredList = new ArrayList<>();

    private String title;
    private int id = 6;
    private String[] header = {"Mã nhà cung cấp","Tên nhà cung cấp","Địa chỉ","Số điện thoại","Email"};
    NhaCungCapBUS nhaCungCapBUS;
    private MainFrame mainFrame;
    private ArrayList<NhaCungCapDTO> listKH;
    private ArrayList<String[]> dataToShow;
    private TaiKhoanDTO taiKhoan;
    private ArrayList<String> listAction;
    private ChiTietQuyenBUS chiTietQuyenBUS;
    private String[][] attributes = {
        {"textbox","Tên nhà cung cấp"},
        {"inputDC", "Địa chỉ"},  
        {"textbox", "Số điện thoại"},
        {"textbox", "Email"},
    };
    private String[] filter = {"Tên nhà cung cấp","Mã nhà cung cấp","Địa chỉ","Số điện thoại","Email"};


    public NhaCungCapForm(String title, MainFrame mainFrame) {
        this.title = title;
        this.mainFrame = mainFrame;
        this.taiKhoan = mainFrame.getTaiKhoan();
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();

        nhaCungCapBUS = NhaCungCapBUS.getInstance();
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
        this.searchBarPanel = new SearchBarPanel<>(filter, new NhaCungCapSearch(listKH), this::updateTable, resetTable);
    
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
        listKH = nhaCungCapBUS.getAll();
        
       return DataToShow(listKH);
    }

    public ArrayList<String[]> DataToShow(ArrayList<NhaCungCapDTO> inputData){

        ArrayList<String[]> data = new ArrayList<>();
        for(NhaCungCapDTO i : inputData){
            data.add(new String[]{i.getMaNCC() + "", i.getTenNCC(), i.getDiaChi(), i.getSoDT(), i.getEmail()});
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
    // {"Mã nhà cung cấp","Tên nhà cung cấp","Địa chỉ","Số điện thoại","Email"};
    
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
        mainFrame.glassPane.setVisible(true);
        switch (e.getActionCommand()) {
            case "add":
                NhaCungCapDialog nhaCungCapDialog = new NhaCungCapDialog(this, "Nhà cung cấp", "Thêm Nhà Cung Cấp", "add", attributes);
                nhaCungCapDialog.setVisible(true);
                break;
           case "importExcel":
                List<NhaCungCapDTO> importedData = ExcelImporter.importFromExcel(new NhaCungCapExcelImport());

                if (importedData != null && !importedData.isEmpty()) {
                   int count = 0;
                   for (NhaCungCapDTO ncc : importedData) {
                       if (nhaCungCapBUS.insert(ncc) != 0) {
                           count++;
                       }
                   }
                   Notifications.getInstance().setJFrame(mainFrame);
                   Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,
                       "Import thành công!");
                   updateTable(nhaCungCapBUS.getAll());
              }
                break;
            case "exportExcel":
                String keyword = searchBarPanel.getSearchField().getText().trim();
                String filterCol = searchBarPanel.getComboBox().getSelectedItem().toString();

                List<NhaCungCapDTO> dataToExport = (filteredList != null && !filteredList.isEmpty())
                    ? filteredList
                    : nhaCungCapBUS.getAll();

                NhaCungCapExcelExport exporter = new NhaCungCapExcelExport(dataToExport, filterCol, keyword);
                ExcelExporter.exportToExcel(exporter, NhaCungCapDTO.class);
                break;

            default:
                break;
        }
        mainFrame.glassPane.setVisible(false);
    }

    @Override
    public void onActionPerformed(String actionId, int row) {
        mainFrame.glassPane.setVisible(true);
        switch (actionId) {
            case "edit":
                NhaCungCapDialog nhaCungCapDialog = new NhaCungCapDialog(this, "Nhà cung cấp", "Sửa Nhà Cung Cấp", "update", attributes, row);
                nhaCungCapDialog.setVisible(true);
                break;
            case "remove":
                // Logic xóa cho form này
                int choose = UIUtils.messageRemove("Bạn thực sự muốn xóa?");

                int ma = Integer.parseInt(table.getCellData(row, 0));
                if (choose == 0) {
                    if(nhaCungCapBUS.delete(ma) != 0){
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

    public Runnable resetTable = () -> {
        ArrayList<NhaCungCapDTO> list = nhaCungCapBUS.getAll();
        updateTable(list);
    };

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public NhaCungCapBUS getNhaCungCapBUS() {
        return nhaCungCapBUS;
    }

    public void setNhaCungCapBUS(NhaCungCapBUS nhaCungCapBUS) {
        this.nhaCungCapBUS = nhaCungCapBUS;
    }

    public CustomTable getTable() {
        return table;
    }

    public void setTable(CustomTable table) {
        this.table = table;
    }

    private void updateTable(ArrayList<NhaCungCapDTO> ketqua) {
        this.filteredList = ketqua;
        table.updateTable(DataToShow(ketqua));
    }

}