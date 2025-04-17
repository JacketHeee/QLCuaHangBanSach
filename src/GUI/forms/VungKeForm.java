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
import BUS.ViTriVungBUS;
import DTO.SachDTO;
import DTO.ChiTietQuyenDTO;
import DTO.TaiKhoanDTO;
import DTO.ViTriVungDTO;

import java.util.ArrayList;
import java.util.List;

import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomBoldJLabel;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import GUI.component.TableActionListener;
import GUI.component.sodoComponent;
import GUI.component.search.SearchBarPanel;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import search.SachSearch;
import search.VungKeSearch;
import utils.ImageUtils;
import utils.UIUtils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

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

    public VungKeForm(String title, MainFrame mainFrame) {
        this.title = title;
        this.mainFrame = mainFrame;
        this.taiKhoan = mainFrame.getTaiKhoan();
        viTriVungBUS = ViTriVungBUS.getInstance();
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();               
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
        SearchBarPanel<ViTriVungDTO> searchBarPanel = new SearchBarPanel<>(foods, new VungKeSearch(listKH), this::updateTable, null);
        panel.add(searchBarPanel);
        return panel;
    }

    String[] foods = {"Tất cả","Phở","Bún bò","Cơm tấm","Sườn bì chả"};

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

        table = new CustomTable(dataToShow,getActionBottom(), header);
        table.setActionListener(this);

        table.addDataRow(new String[] {"1","A1"});
        table.addDataRow(new String[] {"1","A1"});
        table.addDataRow(new String[] {"1","A1"});
        table.addDataRow(new String[] {"1","A1"});
        table.addDataRow(new String[] {"1","A1"});
        table.addDataRow(new String[] {"1","A1"});
        table.addDataRow(new String[] {"1","A1"});
        table.addDataRow(new String[] {"1","A1"});
        table.addDataRow(new String[] {"1","A1"});
        table.addDataRow(new String[] {"1","A1"});
        table.addDataRow(new String[] {"1","A1"});
        table.addDataRow(new String[] {"1","A1"});
        table.addDataRow(new String[] {"1","A1"});
        table.addDataRow(new String[] {"1","A1"});
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
        tableSanPham.addDataRow(new String[]{"1","Con bo"});
        tableSanPham.addDataRow(new String[]{"1","Con bo"});
        tableSanPham.addDataRow(new String[]{"1","Con bo"});
        tableSanPham.addDataRow(new String[]{"1","Con bo"});
        tableSanPham.addDataRow(new String[]{"1","Con bo"});
        tableSanPham.addDataRow(new String[]{"1","Con bo"});
        tableSanPham.addDataRow(new String[]{"1","Con bo"});
        
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
                JOptionPane.showMessageDialog(mainFrame, "hi");
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
            default:
                System.out.println("Unknown action: " + actionId);
                break;
        }
    }

    private void updateTable(ArrayList<ViTriVungDTO> ketqua) {

        // System.out.println("con bo biet bay");
        table.updateTable(DataToShow(ketqua));
    }
}