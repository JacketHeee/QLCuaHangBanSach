package GUI.forms;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.ChiTietQuyenBUS;
import BUS.SachBUS;
import DTO.ChiTietQuyenDTO;
import DTO.SachDTO;
import DTO.TaiKhoanDTO;

import java.util.ArrayList;

import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import GUI.component.TableActionListener;
import GUI.dialog.SachDialog;
import excel.SachExcelExport;
import excel.SachExcelImport;
import GUI.component.search.SearchBarPanel;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import search.SachSearch;
import utils.FormatterUtil;
import utils.ExcelExporter;
import utils.ExcelImporter;
import utils.UIUtils;

import java.util.List;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SachForm extends JPanel implements ActionListener,TableActionListener{
    
    private List<SachDTO> filteredList = new ArrayList<>();
    private String title;
    private int id = 1;
    private SachBUS sachBUS;

    private String[] header = {"Mã sách","Tên sách","Số lượng tồn","Giá bán","Năm xuất bản"};
    private MainFrame mainFrame;
    private ArrayList<SachDTO> listKH;
    private ArrayList<String[]> dataToShow;
    private TaiKhoanDTO taiKhoan;
    private ArrayList<String> listAction;
    private ChiTietQuyenBUS chiTietQuyenBUS;
    private String[][] attributes = {
        {"textbox","Tên sách"},
        {"textbox", "Năm xuất bản"},
        {"textbox", "Giá bán (đ)"},
        {"combobox", "Vùng"}, //FK
        {"combobox", "Nhà xuất bản"}, //FK
        //Khóa ngoại nhiều-nhiều
        {"inputKNNN", "Thể loại"},
        {"inputKNNN", "Tác giả"},
        {"textArea", "Khuyến mãi hiện có"},
        {"inputAnh", "Ảnh"}
    };

    private String[] filter = {"Tên sách","Mã sách","Số lượng tồn","Năm xuất bản"};
    // private javax.swing.JTextField txtSearch;
    // private javax.swing.JComboBox<String> cbFilter;
    private SearchBarPanel<SachDTO> searchBarPanel;


    public SachForm(String title, MainFrame mainFrame) {
        this.title = title;
        this.mainFrame = mainFrame;
        this.taiKhoan = mainFrame.getTaiKhoan();
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();               

        sachBUS = SachBUS.getInstance();
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
        searchBarPanel = new SearchBarPanel<>(filter, new SachSearch(listKH), this::updateTable, resetTable);
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
            Runnable callback = null;
            if (x[2].equals("importExcel")) {
                callback = () -> updateTable(sachBUS.getAll());
            }
            but = new ButtonAction(x[0], x[1], x[2], callback);
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
        listKH = sachBUS.getAll(); 

        return DataToShow(listKH);
    }

    public ArrayList<String[]> DataToShow(ArrayList<SachDTO> inputData){

        ArrayList<String[]> data = new ArrayList<>();

        for(SachDTO i : inputData){
            data.add(new String[]{
                i.getMaSach() + "", 
                i.getTenSach(), 
                i.getSoLuong() + "", 
                FormatterUtil.formatNumberVN(i.getGiaBan()),
                i.getNamXB() + "", 
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
            if(i.equals("Xóa")){
                arrActions.add(bottomActions[2]);
            }
        }
        arrActions.add(bottomActions[1]);
        String[][] array = arrActions.toArray(new String[0][]);
        return(array);
    }
    /////////////////////////////////////////////////////////////////

    CustomTable table;
    private JPanel getMainContent() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        table = new CustomTable(dataToShow,getActionBottom(), header);
        table.setActionListener(this);
        panel.add(table,"push, grow");
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("==> Button clicked: " + e.getActionCommand()); // Log sự kiện để check
        mainFrame.glassPane.setVisible(true);
        switch (e.getActionCommand()) {
            case "add":
                SachDialog sachDialog = new SachDialog(this, "Sách", "Thêm Sách", "add", attributes);
                sachDialog.setVisible(true);
                break;
                
           case "importExcel":
                List<SachDTO> importedData = ExcelImporter.importFromExcel(new SachExcelImport());

                if (importedData != null && !importedData.isEmpty()) {
                   int count = 0;
                   for (SachDTO sach : importedData) {
                       if (sachBUS.insert(sach) != 0) {
                           count++;
                       }
                   }
                   Notifications.getInstance().setJFrame(mainFrame);
                   Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,
                       "Import thành công!");
                   updateTable(sachBUS.getAll());
              }
                break;

            case "exportExcel":
                String keyword = searchBarPanel.getSearchField().getText().trim();
                String filterCol = searchBarPanel.getComboBox().getSelectedItem().toString();
                 
                List<SachDTO> dataToExport = (filteredList != null && !filteredList.isEmpty()) ? filteredList : sachBUS.getAll(); // Nếu filteredList rỗng, dùng toàn bộ danh sách từ DB
                SachExcelExport exporter = new SachExcelExport(dataToExport, filterCol, keyword);

                ExcelExporter.exportToExcel(exporter, SachDTO.class);
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
                SachDialog sachDialog = new SachDialog(this, "Sách", "Sửa Sách", "update", attributes, row);
                sachDialog.setVisible(true);
                break;
            case "remove":
                // Logic xóa cho form này
                int choose = UIUtils.messageRemove("Bạn thực sự muốn xóa?");

                int ma = Integer.parseInt(table.getCellData(row, 0));
                if (choose == 0) {
                    if(sachBUS.delete(ma) != 0){
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
                SachDialog sachDialogd = new SachDialog(this, "Sách", "Chi Tiết Sách", "detail", attributes, row);
                sachDialogd.setVisible(true);            
                break;
            default:
                System.out.println("Unknown action: " + actionId);
                break;
        }
        mainFrame.glassPane.setVisible(false);
    }

    public Runnable resetTable = () -> {
        ArrayList<SachDTO> list = sachBUS.getAll();
        updateTable(list);
    };

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public SachBUS getSachBUS() {
        return sachBUS;
    }

    public void setSachBUS(SachBUS sachBUS) {
        this.sachBUS = sachBUS;
    }

    public CustomTable getTable() {
        return table;
    }

    public void setTable(CustomTable table) {
        this.table = table;
    }

    private void updateTable(ArrayList<SachDTO> ketqua) {
        this.filteredList = ketqua;
        table.updateTable(DataToShow(ketqua));
    }    
    
}


