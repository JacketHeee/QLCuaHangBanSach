package GUI.forms;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import BUS.PhuongThucTTBUS;
import DAO.PhuongThucTTDAO;
import DTO.PhuongThucTTDTO;
import DTO.SachDTO;
import DTO.ViTriVungDTO;

import java.util.ArrayList;
import java.util.List;

import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import GUI.component.TableActionListener;
import GUI.component.search.SearchBarPanel;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import search.PTTTSearch;
import search.SachSearch;
import utils.UIUtils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JButton;

public class PhuongThucThanhToanForm extends JPanel implements TableActionListener {

    private String title;
    private String[] header = {"Mã phương thức", "Tên phương thức thanh toán"};
    private PhuongThucTTBUS phuongThucTTBUS;
    private CustomTable table ;
    private MainFrame mainFrame;
    private ArrayList<PhuongThucTTDTO> listKH;
    private ArrayList<String[]> dataToShow;

    public PhuongThucThanhToanForm(String title,MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.title = title;
        phuongThucTTBUS = PhuongThucTTBUS.getInstance();
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
        SearchBarPanel<PhuongThucTTDTO> searchBarPanel = new SearchBarPanel<>(foods, new PTTTSearch(listKH), this::updateTable, null);
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

        for (String[] x : arrActions) {
            panel.add(new ButtonAction(x[0],x[1],x[2]));
        }

        return panel;
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
    // {"Mã phương thức", "Tên phương thức thanh toán"};
    /////////////////////////////////////////////////////////////////

    String[][] actions = {
        {"edit.svg","edit"},
        {"remove.svg","remove"}
    };

    private JPanel getMainContent() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        table = new CustomTable(Data(),actions, header);
        table.setActionListener(this);
        panel.add(new CustomScrollPane(table),"push, grow");
        return panel;
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

    private void updateTable(ArrayList<PhuongThucTTDTO> ketqua) {

        // System.out.println("con bo biet bay");
        table.updateTable(DataToShow(ketqua));
    }
}