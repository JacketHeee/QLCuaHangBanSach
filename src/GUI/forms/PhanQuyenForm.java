package GUI.forms;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import BUS.ChiTietQuyenBUS;
import BUS.ChucNangBUS;
import BUS.NhomQuyenBUS;
import DTO.ChiTietQuyenDTO;
import DTO.NhomQuyenDTO;
import DTO.TaiKhoanDTO;

import java.util.ArrayList;
import java.util.List;

import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import GUI.component.TableActionListener;
import GUI.dialog.AddNhomQuyen;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import utils.UIUtils;

import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhanQuyenForm extends JPanel implements ActionListener,TableActionListener {

    private MainFrame mainFrame;
    private String title;
    private String id = "phanquyen";
    private ArrayList<String[]> arrCN;
    private ArrayList<ButtonAction> buttonActionsList;
    private TaiKhoanDTO taiKhoan;

    private ChiTietQuyenBUS chiTietQuyenBUS;
    private NhomQuyenBUS nhomQuyenBUS;
    private ChucNangBUS chucNangBUS;
    

    private CustomTable table;
    private ArrayList<String> listAction;


    public PhanQuyenForm(MainFrame mainframe, String title, ArrayList<String[]> arrCN) {
        this.mainFrame = mainframe;
        this.taiKhoan = mainFrame.getTaiKhoan();
        this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();
        this.chucNangBUS = ChucNangBUS.getInstance();

        this.title = title;
        this.arrCN = arrCN;
        chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();
        nhomQuyenBUS = NhomQuyenBUS.getInstance();
        chucNangBUS = ChucNangBUS.getInstance();
        listAction = getListAction();
        init();
    }
    
    private void init() {
        setLayout(new MigLayout("wrap 1, gap 10"));

        add(getHeader(),"pushx, growx");
        add(getActions(),"pushx, growx");
        add(getMainContent(),"push,grow, gaptop 15");

    }

    ////////////////////////////////////////////////////////////////////
    public ArrayList<String> getListAction(){
        ArrayList<String> result = new ArrayList<>(); 
        int maNQ = taiKhoan.getMaRole();
        int maCN = chucNangBUS.getMaChucNangByTen(id);
        ArrayList<ChiTietQuyenDTO> listCTQ = this.chiTietQuyenBUS.getListChiTietQuyenByMaRoleMaCN(maNQ, maCN);
        for(ChiTietQuyenDTO i : listCTQ){
            result.add(i.getHanhDong());
        }
        return(result);
    }
    
    private JPanel getHeader() {
        JPanel panel = new JPanel(new MigLayout());
        panel.add(new JLabel(String.format("<html><b><font size='+2'>%s</b></html>", title)),"pushx");
        panel.add(getPanelSearch());
        return panel;
    }

    String[] foods = {"Tất cả","Phở","Bún bò","Cơm tấm","Sườn bì chả"};
    private JTextField inputSearch;
    private JComboBox<String> droplist;
    private JButton butRefresh;
    private JButton butSearch;

    private JPanel getPanelSearch() {
        JPanel panel = new JPanel(new MigLayout());
        droplist = new JComboBox<>(foods);
        droplist.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusWidth:0; innerFocusWidth: 0;");
        
        JPanel search = new JPanel(new MigLayout("insets 3"));
        inputSearch = new JTextField(30);
        inputSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm");
        inputSearch.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusWidth:0; innerFocusWidth: 0");
        search.add(inputSearch);
        butSearch = new JButton(new FlatSVGIcon(SachForm.class.getResource("../../resources/img/icon/search.svg")).derive(20,20));
        butSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));

        butSearch.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusWidth:0; innerFocusWidth: 0");
        
        search.putClientProperty(FlatClientProperties.STYLE, "background: #ffffff; arc:5");
        search.add(butSearch);
        
        
        butRefresh = new JButton(new FlatSVGIcon(SachForm.class.getResource("../../resources/img/icon/refresh.svg")).derive(26,26));
        butRefresh.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusWidth:0; innerFocusWidth: 0;");
        butRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panel.add(droplist,"h 32");
        panel.add(search,"");
        panel.add(butRefresh,"");
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
        table = new CustomTable(Data(),getActionBottom(), "Mã quyền","Tên nhóm quyền");
        table.setActionListener(this);
        panel.add(new CustomScrollPane(table),"push, grow");
        return panel;
    }

    public ArrayList<String[]> Data(){
        ArrayList<NhomQuyenDTO> listNQ = nhomQuyenBUS.getAll();
        ArrayList<String[]> data = new ArrayList<>();
        for(NhomQuyenDTO i : listNQ){
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
        
        switch (e.getActionCommand()) {
            case "add":
                mainFrame.glassPane.setVisible(true);
                ButtonAction but = (ButtonAction) e.getSource();
                System.out.println(but.getId()+ but.getText());
                new AddNhomQuyen(mainFrame,this, "Thêm nhóm quyền",arrCN);
                mainFrame.glassPane.setVisible(false);
                break;
        
            default:
                break;
        }
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
}