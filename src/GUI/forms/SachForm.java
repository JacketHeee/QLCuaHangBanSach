package GUI.forms;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.SachBUS;
import BUS.ViTriVungBUS;
import DTO.SachDTO;

import java.util.ArrayList;

import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import GUI.component.TableActionListener;
import GUI.component.search.SearchBarPanel;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import search.SachSearch;
import utils.UIUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SachForm extends JPanel implements ActionListener,TableActionListener{
    		// "INSERT INTO SACH (tenSach, soLuong, giaNhap, giaBan, namXB, maVung, maNXB) VALUES ('%s','%s','%s','%s','%s','%s','%s')",

    private String title;
    private SachBUS sachBUS;

    private String[] header = {"Mã sách","Tên sách","Số lượng tồn","Giá bán","Vị trí vùng"}; // Tạm thời không cài đặt giá bán
    private MainFrame mainFrame;
    private ArrayList<SachDTO> listKH;
    private ArrayList<String[]> dataToShow;

    public SachForm(String title, MainFrame mainFrame) {
        this.title = title;
        this.mainFrame = mainFrame;
        sachBUS = SachBUS.getInstance();
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
        panel.add(new JLabel(String.format("<html><b><font size='+2'>%s</b></html>", title)),"pushx");
        SearchBarPanel<SachDTO> searchBarPanel = new SearchBarPanel<>(foods, new SachSearch(listKH), this::updateTable, null);
        panel.add(searchBarPanel);
        return panel;
    }

    String[] foods = {"Tất cả","Phở","Bún bò","Cơm tấm","Sườn bì chả"};

    ///////////////////////////////////////////////////////////////
    String[][] arrActions = {
        {"Thêm","add.svg","add"},
        {"Import Excel","importExcel.svg","importExcel"},
        {"Export Excel","exportExcel.svg","exportExcel"}
    };

    private JPanel getActions() {
        JPanel panel = new JPanel(new MigLayout("gap 10"));
        panel.putClientProperty(FlatClientProperties.STYLE, "background: #ffffff; arc:5");

        ButtonAction but;
        for (String[] x : arrActions) {
            but = new ButtonAction(x[0],x[1],x[2]);
            panel.add(but);
            but.setActionCommand(but.getId());
            but.addActionListener(this);
        }

        return panel;
    }
    
    public ArrayList<String[]> Data(){
        listKH = sachBUS.getAll(); 

        return DataToShow(listKH);
    }

    public ArrayList<String[]> DataToShow(ArrayList<SachDTO> inputData){

        ArrayList<String[]> data = new ArrayList<>();
        ViTriVungBUS vitri = ViTriVungBUS.getInstance();

        for(SachDTO i : inputData){
            data.add(new String[]{
                i.getMaSach() + "", 
                i.getTenSach(), 
                i.getSoLuong() + "", 
                i.getGiaBan() + "",
                vitri.getTenByMaViTriVung(i.getMaVung()), 
            });
        }
        return(data);
    }

    /////////////////////////////////////////////////////////////////

    String[][] actions = {
        {"edit.svg","edit"},
        {"detail.svg","detail"},
        {"remove.svg","remove"}
    };

    CustomTable table;
    private JPanel getMainContent() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        table = new CustomTable(dataToShow,actions, header);
        table.setActionListener(this);
        panel.add(new CustomScrollPane(table),"push, grow");
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
                break;
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



    private void updateTable(ArrayList<SachDTO> ketqua) {

        // System.out.println("con bo biet bay");
        table.updateTable(DataToShow(ketqua));
    }
    
}


