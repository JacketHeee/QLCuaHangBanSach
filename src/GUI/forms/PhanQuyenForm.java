package GUI.forms;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.formdev.flatlaf.FlatClientProperties;
import DTO.NhomQuyenDTO;
import BUS.ChiTietQuyenBUS;
import BUS.ChucNangBUS;
import BUS.NhomQuyenBUS;
import DTO.ChiTietQuyenDTO;
import DTO.TaiKhoanDTO;

import java.util.ArrayList;
import java.util.List;
import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import GUI.component.TableActionListener;
import GUI.component.search.SearchBarPanel;
import GUI.dialog.AddNhomQuyen;
import excel.NhomQuyenExcelExport;
import excel.NhomQuyenExcelImport;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import search.PhanQuyenSearch;
import utils.ExcelExporter;
import utils.ExcelImporter;
import utils.UIUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhanQuyenForm extends JPanel implements ActionListener,TableActionListener {

    private SearchBarPanel<NhomQuyenDTO> searchBarPanel;
    private List<NhomQuyenDTO> filteredList = new ArrayList<>();

    private MainFrame mainFrame;
    private String title;
    private int id = 14;
    private CustomTable table; 
    private ArrayList<String[]> dataToShow;
    private ArrayList<ButtonAction> buttonActionsList;
    private TaiKhoanDTO taiKhoan;

    private ChiTietQuyenBUS chiTietQuyenBUS;
    private NhomQuyenBUS nhomQuyenBUS;
    private ChucNangBUS chucNangBUS;
    
    private ArrayList<String> listAction;
    private ArrayList<NhomQuyenDTO> listKH; 

    private String[] header = {"Mã quyền","Tên nhóm quyền"};
    private String[] filter = {"Tên nhóm quyền","Mã quyền"};


    public PhanQuyenForm(MainFrame mainframe, String title) {
        this.mainFrame = mainframe;
        this.taiKhoan = mainFrame.getTaiKhoan();
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();
        this.chucNangBUS = ChucNangBUS.getInstance();

        this.title = title;
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();
        this.nhomQuyenBUS = NhomQuyenBUS.getInstance();
        listAction = getListAction();
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
        this.searchBarPanel = new SearchBarPanel<>(filter, new PhanQuyenSearch(listKH), this::updateTable, resetTable);
    
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
        {"detail.svg","detail"},
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
    
    // ArrayList<String[]> data = new ArrayList<>(List.of(
    //         new String[]{"1","admin"},
    //         new String[]{"2","Nhân viên bán hàng"},
    //         new String[]{"3","Nhân viên nhập hàng"},
    //         new String[]{"4","Nhân viên kiểm kê"}
    // ));



    /////////////////////////////////////////////////////////////////


    

    private JPanel getMainContent() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        table = new CustomTable(dataToShow,getActionBottom(), header);
        table.setActionListener(this);
        panel.add(new CustomScrollPane(table),"push, grow");
        return panel;
    }

    public ArrayList<String[]> Data(){
        listKH = nhomQuyenBUS.getAll();
        
       return DataToShow(listKH);
    }

    public ArrayList<String[]> DataToShow(ArrayList<NhomQuyenDTO> inputData){

        ArrayList<String[]> data = new ArrayList<>();
        for(NhomQuyenDTO i : inputData){
            data.add(new String[]{i.getMaRole() + "", i.getTenRole()});
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
                arrActions.add(bottomActions[2]);
            }

        }
        arrActions.add(bottomActions[1]);
        String[][] array = arrActions.toArray(new String[0][]);
        return(array);
    }


      
    @Override
    public void actionPerformed(ActionEvent e) {
        mainFrame.glassPane.setVisible(true);
        switch (e.getActionCommand()) {
            case "add":
                ButtonAction but = (ButtonAction) e.getSource();
                new AddNhomQuyen(mainFrame,this, "Thêm nhóm quyền", "add");
                break;
            case "importExcel":
                List<NhomQuyenDTO> importedData = ExcelImporter.importFromExcel(new NhomQuyenExcelImport());

                if (importedData != null && !importedData.isEmpty()) {
                   int count = 0;
                   for (NhomQuyenDTO nq : importedData) {
                       if (nhomQuyenBUS.insert(nq) != 0) {
                           count++;
                       }
                   }
                   Notifications.getInstance().setJFrame(mainFrame);
                   Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,
                       "Đã import thành công " + count + " dòng!");
                   updateTable(nhomQuyenBUS.getAll());
              }
                break;
               
            case "exportExcel":
                String keyword = searchBarPanel.getSearchField().getText().trim();
                String filterCol = searchBarPanel.getComboBox().getSelectedItem().toString();

                List<NhomQuyenDTO> dataToExport = (filteredList != null && !filteredList.isEmpty())
                    ? filteredList
                    : nhomQuyenBUS.getAll();

                NhomQuyenExcelExport exporter = new NhomQuyenExcelExport(dataToExport, filterCol, keyword);
                ExcelExporter.exportToExcel(exporter, NhomQuyenDTO.class);
                break;
            default:
                break;
        }

        mainFrame.glassPane.setVisible(false);
    }

    public ChiTietQuyenBUS getChiTietQuyenBUS() {
        return chiTietQuyenBUS;
    }

    public void setChiTietQuyenBUS(ChiTietQuyenBUS chiTietQuyenBUS) {
        this.chiTietQuyenBUS = chiTietQuyenBUS;
    }

    public NhomQuyenBUS getNhomQuyenBUS() {
        return nhomQuyenBUS;
    }

    public void setNhomQuyenBUS(NhomQuyenBUS nhomQuyenBUS) {
        this.nhomQuyenBUS = nhomQuyenBUS;
    }

    public ChucNangBUS getChucNangBUS() {
        return chucNangBUS;
    }

    public void setChucNangBUS(ChucNangBUS chucNangBUS) {
        this.chucNangBUS = chucNangBUS;
    }

    public CustomTable getTable() {
        return table;
    }

    public void setTable(CustomTable table) {
        this.table = table;
    }

    
    @Override
    public void onActionPerformed(String actionId, int row) {
        mainFrame.glassPane.setVisible(true);
        switch (actionId) {
            case "edit":
                new AddNhomQuyen(mainFrame,this, "Sửa nhóm quyền", "update", row);
                break;
            case "remove":
                // Logic xóa cho form này
                int choose = UIUtils.messageRemove("Bạn thực sự muốn xóa?");

                int ma = Integer.parseInt(table.getCellData(row, 0));
                if (choose == 0) {
                    if(nhomQuyenBUS.delete(ma) != 0){
                        table.removeRow(row);
                        Notifications.getInstance().setJFrame(mainFrame);
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Xóa thành công!");
                    }
                    else{
                        JOptionPane.showMessageDialog(mainFrame, "Xóa thất bại!");
                    }
                }
                break;
            case "detail":
                mainFrame.glassPane.setVisible(true);
                new AddNhomQuyen(mainFrame,this, "Chi tiết nhóm quyền", "detail", row);
                mainFrame.glassPane.setVisible(false);
                break;
            default:
                System.out.println("Unknown action: " + actionId);
                break;
        }
        mainFrame.glassPane.setVisible(false);
    }

    public Runnable resetTable = () -> {
        ArrayList<NhomQuyenDTO> list = nhomQuyenBUS.getAll();
        updateTable(list);
    };

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    

    private void updateTable(ArrayList<NhomQuyenDTO> ketqua) {

        this.filteredList = ketqua;
        table.updateTable(DataToShow(ketqua));
    }
}