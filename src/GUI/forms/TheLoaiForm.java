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
import BUS.TheLoaiBUS;
import DTO.SachDTO;
import DTO.ChiTietQuyenDTO;
import DTO.KhuyenMaiDTO;
import DTO.TaiKhoanDTO;
import DTO.TheLoaiDTO;
import DTO.ViTriVungDTO;

import java.util.ArrayList;
import java.util.List;

import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import GUI.component.TableActionListener;
import GUI.dialog.TheLoaiDialog;
import GUI.component.search.SearchBarPanel;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import search.SachSearch;
import search.TheLoaiSearch;
import utils.UIUtils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class TheLoaiForm extends JPanel implements TableActionListener, ActionListener{

    private String title;
    private int id = 2;
    private String[] header = {"Mã thể loại", "Tên thể loại"};
    private TheLoaiBUS theLoaiBUS;
    private MainFrame mainFrame;
    private ArrayList<TheLoaiDTO> listKH;
    private ArrayList<String[]> dataToShow;

    private TaiKhoanDTO taiKhoan;
    private ArrayList<String> listAction;
    private ChiTietQuyenBUS chiTietQuyenBUS;
    private String[][] attributes = {
        {"textbox","Tên thể loại"}
    };

    private String[] filter = {"Tất cả", "Mã thể loại", "Tên thể loại"};

    public TheLoaiForm(String title, MainFrame mainFrame) {
        this.title = title;
        this.mainFrame = mainFrame;
        this.taiKhoan = mainFrame.getTaiKhoan();
        theLoaiBUS = TheLoaiBUS.getInstance();
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
        SearchBarPanel<TheLoaiDTO> searchBarPanel = new SearchBarPanel<>(filter, new TheLoaiSearch(listKH), this::updateTable, resetTable);
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
        {"detail.svg","detail"},    //Có không ???
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
        // System.out.println("con bo biet bay");
        listKH = theLoaiBUS.getAll();
        // System.out.println("con cho biet bo");
        
        return DataToShow(listKH);
    }

    public ArrayList<String[]> DataToShow(ArrayList<TheLoaiDTO> inputData){

        ArrayList<String[]> data = new ArrayList<>();
        for(TheLoaiDTO i : inputData){
            data.add(new String[]{i.getMaTheLoai() + "", i.getTenTheLoai()});
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
    // {"Mã thể loại", "Tên thể loại"}
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
                TheLoaiDialog theLoaiDialog = new TheLoaiDialog(this, "Thể loại", "Thêm Thể Loại", "add", attributes);
                theLoaiDialog.setVisible(true);
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
                TheLoaiDialog theLoaiDialog = new TheLoaiDialog(this, "Thể loại", "Sửa Thể Loại", "update", attributes, row);
                theLoaiDialog.setVisible(true);
                break;
            case "remove":
                // Logic xóa cho form này
                int choose = UIUtils.messageRemove("Bạn thực sự muốn xóa?");

                int ma = Integer.parseInt(table.getCellData(row, 0));
                if (choose == 0) {
                    if(theLoaiBUS.delete(ma) != 0){
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
        ArrayList<TheLoaiDTO> list = theLoaiBUS.getAll();
        updateTable(list);
    };

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public TheLoaiBUS getTheLoaiBUS() {
        return theLoaiBUS;
    }

    public void setTheLoaiBUS(TheLoaiBUS theLoaiBUS) {
        this.theLoaiBUS = theLoaiBUS;
    }

    public CustomTable getTable() {
        return table;
    }

    public void setTable(CustomTable table) {
        this.table = table;
    }

    
    
    private void updateTable(ArrayList<TheLoaiDTO> ketqua) {

        // System.out.println("con bo biet bay");
        table.updateTable(DataToShow(ketqua));
    }
}