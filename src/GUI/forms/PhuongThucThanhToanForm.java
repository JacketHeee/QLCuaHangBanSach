package GUI.forms;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.ChiTietQuyenBUS;
import BUS.PhuongThucTTBUS;
import DTO.ChiTietQuyenDTO;
import DTO.PhuongThucTTDTO;
import DTO.TaiKhoanDTO;

import java.util.ArrayList;
import java.util.List;

import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import GUI.component.TableActionListener;
import GUI.dialog.PhuongThucTTDialog;
import excel.PhuongThucThanhToanExcelExport;
import excel.PhuongThucThanhToanExcelImport;
import excel.TaiKhoanExcelImport;
import GUI.component.search.SearchBarPanel;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import search.PTTTSearch;
import utils.ExcelExporter;
import utils.ExcelImporter;
import utils.UIUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhuongThucThanhToanForm extends JPanel implements TableActionListener, ActionListener {

    private SearchBarPanel<PhuongThucTTDTO> searchBarPanel;
    private List<PhuongThucTTDTO> filteredList = new ArrayList<>();
    private String title;
    private int id = 10;
    private String[] header = {"Mã phương thức", "Tên phương thức thanh toán"};
    private PhuongThucTTBUS phuongThucTTBUS;
    private MainFrame mainFrame;
    private ArrayList<PhuongThucTTDTO> listKH;
    private ArrayList<String[]> dataToShow;
    private TaiKhoanDTO taiKhoan;
    private ArrayList<String> listAction;
    private ChiTietQuyenBUS chiTietQuyenBUS;
    private String[][] attributes = {
        {"textbox","Tên phương thức thanh toán"}
    };
    private String[] filter = {"Tất cả","Mã phương thức", "Tên phương thức thanh toán"};


    public PhuongThucThanhToanForm(String title, MainFrame mainFrame) {
        this.title = title;
        this.mainFrame = mainFrame;
        this.taiKhoan = mainFrame.getTaiKhoan();
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();        

        phuongThucTTBUS = PhuongThucTTBUS.getInstance();
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
        this.searchBarPanel = new SearchBarPanel<>(filter, new PTTTSearch(listKH), this::updateTable, resetTable);
    
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
        listKH = phuongThucTTBUS.getAll();
        
        return DataToShow(listKH);
    }

    public ArrayList<String[]> DataToShow(ArrayList<PhuongThucTTDTO> inputData){

        ArrayList<String[]> data = new ArrayList<>();
        for(PhuongThucTTDTO i : inputData){
            data.add(new String[]{i.getMaPT() + "", i.getTenPTTT()});
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
    // {"Mã phương thức", "Tên phương thức thanh toán"};
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
                PhuongThucTTDialog phuongThucTTDialog = new PhuongThucTTDialog(this, "Phương thức thanh toán", "Thêm Phương Thức TT", "add", attributes);
                phuongThucTTDialog.setVisible(true);
                break;
            case "importExcel":
                List<PhuongThucTTDTO> importedData = ExcelImporter.importFromExcel(new PhuongThucThanhToanExcelImport());

                if (importedData != null && !importedData.isEmpty()) {
                   int count = 0;
                   for (PhuongThucTTDTO tk : importedData) {
                       if (phuongThucTTBUS.insert(tk) != 0) {
                           count++;
                       }
                   }
                   Notifications.getInstance().setJFrame(mainFrame);
                   Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,
                       "Import thành công!");
                   updateTable(phuongThucTTBUS.getAll());
              }
                break;
            case "exportExcel":
                String keyword = searchBarPanel.getSearchField().getText().trim();
                String filterCol = searchBarPanel.getComboBox().getSelectedItem().toString();

                List<PhuongThucTTDTO> dataToExport = (filteredList != null && !filteredList.isEmpty())
                    ? filteredList
                    : phuongThucTTBUS.getAll();

                PhuongThucThanhToanExcelExport exporter = new PhuongThucThanhToanExcelExport(dataToExport, filterCol, keyword);
                ExcelExporter.exportToExcel(exporter, PhuongThucTTDTO.class);
                break;
            default:
        }
        mainFrame.glassPane.setVisible(false);
    }
    public void onActionPerformed(String actionId, int row) {
        mainFrame.glassPane.setVisible(true);
        switch (actionId) {
            case "edit":
                PhuongThucTTDialog phuongThucTTDialog = new PhuongThucTTDialog(this, "Phương thức thanh toán", "Sửa Phương Thức TT", "update", attributes, row);
                phuongThucTTDialog.setVisible(true);
                break;
            case "remove":
                // Logic xóa cho form này
                int choose = UIUtils.messageRemove("Bạn thực sự muốn xóa?");

                int ma = Integer.parseInt(table.getCellData(row, 0));
                if (choose == 0) {
                    if(phuongThucTTBUS.delete(ma) != 0){
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
        ArrayList<PhuongThucTTDTO> list = phuongThucTTBUS.getAll();
        updateTable(list);
    };

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public PhuongThucTTBUS getPhuongThucTTBUS() {
        return phuongThucTTBUS;
    }

    public void setPhuongThucTTBUS(PhuongThucTTBUS phuongThucTTBUS) {
        this.phuongThucTTBUS = phuongThucTTBUS;
    }

    public CustomTable getTable() {
        return table;
    }

    public void setTable(CustomTable table) {
        this.table = table;
    }

    

    
    private void updateTable(ArrayList<PhuongThucTTDTO> ketqua) {

        this.filteredList = ketqua;
        table.updateTable(DataToShow(ketqua));
    }
}