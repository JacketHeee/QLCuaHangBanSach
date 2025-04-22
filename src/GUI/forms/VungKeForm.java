package GUI.forms;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.formdev.flatlaf.FlatClientProperties;
import BUS.ChiTietQuyenBUS;
import BUS.SachBUS;
import BUS.ViTriVungBUS;
import DTO.ChiTietQuyenDTO;
import DTO.SachDTO;
import DTO.TaiKhoanDTO;
import DTO.ViTriVungDTO;

import java.util.ArrayList;
import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomBoldJLabel;
import GUI.component.CustomButton;
import GUI.component.CustomTable;
import GUI.component.TableActionListener;
import GUI.component.sodoComponent;
import GUI.dialog.PopupSachDetail;
import GUI.dialog.SachDialog;
import GUI.dialog.ViTriVungDialog;
import GUI.component.search.SearchBarPanel;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import search.VungKeSearch;
import utils.ImageUtils;
import utils.UIUtils;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VungKeForm extends JPanel implements TableActionListener, ActionListener {

    private String title;
    private int id = 5;
    private String[] header = {"Mã vùng", "Tên vùng"};
    private ViTriVungBUS viTriVungBUS;
    private MainFrame mainFrame;
    private ArrayList<ViTriVungDTO> listKH;
    private ArrayList<String[]> dataToShow;

    private TaiKhoanDTO taiKhoan;
    private ArrayList<String> listAction;
    private ChiTietQuyenBUS chiTietQuyenBUS;
    private CustomTable tableSanPham;
    private CustomTable table;
    private String[][] attributes = {
        {"textbox","Tên vùng"}
    };
    private String[] filter = {"Tất cả","Mã vùng", "Tên vùng"};

    private SachBUS sachBUS;

    public VungKeForm(String title, MainFrame mainFrame) {
        this.title = title;
        this.mainFrame = mainFrame;
        this.taiKhoan = mainFrame.getTaiKhoan();
        viTriVungBUS = ViTriVungBUS.getInstance();
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();   
        this.sachBUS = SachBUS.getInstance();            
        this.listAction = getListAction();

        init();
    }
    
    private void init() {
        setLayout(new MigLayout("wrap 1, gap 10"));
        dataToShow = Data();

        add(getHeader(),"pushx, growx");
        add(getActions(),"pushx, growx");
        add(getMainContent(),"push,grow");

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
        SearchBarPanel<ViTriVungDTO> searchBarPanel = new SearchBarPanel<>(filter, new VungKeSearch(listKH), this::updateTable, resetTable);
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
        listKH = viTriVungBUS.getAll();
        
        return DataToShow(listKH);
    }

    public ArrayList<String[]> DataToShow(ArrayList<ViTriVungDTO> inputData){

        ArrayList<String[]> data = new ArrayList<>();
        for(ViTriVungDTO i : inputData){
            data.add(new String[]{i.getMaVung() + "", i.getTenVung()});
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


    String[][] actionOnTableSanPham = {
        {"detail.svg","detail"}
    };

    private JPanel getMainContent() {
        JPanel panel = new JPanel(new MigLayout("insets 0","[][]","[][]"));

        table = new CustomTable(
            dataToShow
            , getActionBottom()
            , new CustomTable.OnSelectRowListener() {
                @Override
                public void OnSelectRow(int row) {
                    loadTableSach(row);
                }
            }
            , header);
        table.setActionListener(this);

        // table.addDataRow(new String[] {"1","A1"});
        // table.addDataRow(new String[] {"1","A1"});
        // table.addDataRow(new String[] {"1","A1"});
        // table.addDataRow(new String[] {"1","A1"});
        // table.addDataRow(new String[] {"1","A1"});
        // table.addDataRow(new String[] {"1","A1"});
        // table.addDataRow(new String[] {"1","A1"});
        // table.addDataRow(new String[] {"1","A1"});
        // table.addDataRow(new String[] {"1","A1"});
        // table.addDataRow(new String[] {"1","A1"});
        // table.addDataRow(new String[] {"1","A1"});
        // table.addDataRow(new String[] {"1","A1"});
        // table.addDataRow(new String[] {"1","A1"});
        // table.addDataRow(new String[] {"1","A1"});
        // CustomTable table = new CustomTable(data,actions, "Mã vùng kệ","Tên vùng kệ");
        panel.add(getSoDo(),"");

        
        panel.add(panelSanPhamTuongUng(),"span 1 2, pushy,growy,wrap");

        panel.add(table,"push, grow");
        return panel;
    }

    private JPanel panelSanPhamTuongUng() {
        JPanel panel = new JPanel(new MigLayout("insets 10,wrap 1"));
        panel.putClientProperty(FlatClientProperties.STYLE,"arc: 10");

        panel.setBackground(Color.white);
        panel.add(new CustomBoldJLabel("Các sản phẩm có trong vùng",1),"pushx, gapbottom 5");

        tableSanPham = new CustomTable(null, actionOnTableSanPham, "Mã sách","Tên sách");
        // tableSanPham.addDataRow(new String[]{"1","Con bo"});
        // tableSanPham.addDataRow(new String[]{"1","Con bo"});
        // tableSanPham.addDataRow(new String[]{"1","Con bo"});
        // tableSanPham.addDataRow(new String[]{"1","Con bo"});
        // tableSanPham.addDataRow(new String[]{"1","Con bo"});
        // tableSanPham.addDataRow(new String[]{"1","Con bo"});
        // tableSanPham.addDataRow(new String[]{"1","Con bo"});

        tableSanPham.setActionListener(this);
        
        panel.add(tableSanPham,"push,grow");

        return panel;
    }

    private JPanel getSoDo() {
        JPanel panel = new JPanel(new MigLayout("insets 0")); 
        // sodoComponent panel = new sodoComponent();
        // panel.setLayout(new MigLayout("insets 0"));

        // panel.add(new JLabel("<html><b><font size='+1'>Sơ đồ cửa hàng</font></b></html>"),"pushx,growx, wrap");

        // ImageIcon originalIcon = new ImageIcon(getClass().getResource("/resources/img/sodo1.png"));

        // // Resize ảnh nè:
        // Image resizedImage = originalIcon.getImage().getScaledInstance(700, 457, Image.SCALE_SMOOTH);

        // // Gắn lại vào ImageIcon mới
        // ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Rồi gắn vào JLabel
        panel.setBackground(Color.white);
        panel.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        JLabel label = new JLabel(ImageUtils.getHighQualityResizedIcon(getClass(), "/resources/img/sodo1.png", 900, 500));
        panel.add(label,"al center");
        // panel.add(new JLabel(new FlatSVGIcon(VungKeForm.class.getResource("/resources/img/sodo.svg")).derive(700, 457)));
        return panel; 
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "add":
                ViTriVungDialog viTriVungDialog = new ViTriVungDialog(this, "Vị trí vùng", "Thêm Vùng", "add", attributes);
                viTriVungDialog.setVisible(true);
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
                ViTriVungDialog viTriVungDialog = new ViTriVungDialog(this, "Vị trí vùng", "Sửa Vùng", "update", attributes, row);
                viTriVungDialog.setVisible(true);
                // System.out.println();
                // Notifications.getInstance().setJFrame(mainFrame);
                // Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,"Sửa thành công!");
            break;
            case "remove":
                // Logic xóa cho form này
                int choose = UIUtils.messageRemove("Bạn thực sự muốn xóa?");

                int ma = Integer.parseInt(table.getCellData(row, 0));
                if (choose == 0) {
                    if(viTriVungBUS.delete(ma) != 0){
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
                int maSach = Integer.parseInt(tableSanPham.getCellData(row, 0));
                PopupSachDetail popup = new PopupSachDetail(maSach);
                JLabel label = (JLabel)tableSanPham.getRowLabels().get(row).get(0);
                popup.show(label, 10, label.getHeight());
                break;
            default:
                System.out.println("Unknown action: " + actionId);
                break;
        }
    }

    public Runnable resetTable = () -> {
        ArrayList<ViTriVungDTO> list = viTriVungBUS.getAll();
        updateTable(list);
    };

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public ViTriVungBUS getViTriVungBUS() {
        return viTriVungBUS;
    }

    public void setViTriVungBUS(ViTriVungBUS viTriVungBUS) {
        this.viTriVungBUS = viTriVungBUS;
    }

    public CustomTable getTable() {
        return table;
    }

    public void setTable(CustomTable table) {
        this.table = table;
    }

    
    
    private void updateTable(ArrayList<ViTriVungDTO> ketqua) {

        // System.out.println("con bo biet bay");
        table.updateTable(DataToShow(ketqua));
    }

    public void loadTableSach(int row){
        int ma = getMaVungKeByRowIndex(row);
        ArrayList<SachDTO> listSach = sachBUS.getAllSachByMaVung(ma);
        ArrayList<String[]> data = new ArrayList<>();
        for(int i = 0; i < listSach.size(); i++){
            String[] info = {listSach.get(i).getMaSach() + "", listSach.get(i).getTenSach()}; //action
            data.add(info);
        }
        tableSanPham.updateTable(data);
    }

    public int getMaVungKeByRowIndex(int row){
        JLabel label = (JLabel)this.table.getRowLabels().get(row).get(0);
        int ma = Integer.parseInt(label.getText());
        return(ma);
    }
}