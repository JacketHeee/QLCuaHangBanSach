package GUI.forms;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import DTO.NhomQuyenDTO;
import DTO.SachDTO;

import java.util.ArrayList;
import java.util.List;

import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import GUI.component.TableActionListener;
import GUI.component.search.SearchBarPanel;
import GUI.dialog.AddNhomQuyen;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import search.PhanQuyenSearch;
import search.SachSearch;
import utils.UIUtils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhanQuyenForm extends JPanel implements ActionListener,TableActionListener {

    private MainFrame mainFrame;
    private String title;
    private ArrayList<String[]> arrCN;
    private CustomTable table; 
    private ArrayList<String[]> dataToShow;

    public PhanQuyenForm(MainFrame mainframe, String title, ArrayList<String[]> arrCN) {
        this.mainFrame = mainframe;
        this.title = title;
        this.arrCN = arrCN;
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
        SearchBarPanel<NhomQuyenDTO> searchBarPanel = new SearchBarPanel<>(foods, new PhanQuyenSearch(data), this::updateTable, null);
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
    
    ArrayList<String[]> data = new ArrayList<>(List.of(
            new String[]{"1","admin"},
            new String[]{"2","Nhân viên bán hàng"},
            new String[]{"3","Nhân viên nhập hàng"},
            new String[]{"4","Nhân viên kiểm kê"}
    ));
    /////////////////////////////////////////////////////////////////

    String[][] actions = {
        {"edit.svg","edit"},
        {"detail.svg","detail"},
        {"remove.svg","remove"}
    };

    private JPanel getMainContent() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        table = new CustomTable(data,actions, "Mã quyền","Tên nhóm quyền");
        table.setActionListener(this);
        panel.add(new CustomScrollPane(table),"push, grow");
        return panel;
    }

    String[][] actionRole = {
        {"view","add","edit","remove"},
        {"view","add","edit","remove"},
        {"view","add","edit","remove"},
        {"view","add","edit","remove"},
        {"view","add","edit","remove"},
        {"view","add","edit"},
        {"view","add","edit","remove"},
        {"view","add","edit","remove"},
        {"view","add","edit","remove"},
        {"view","add","edit","remove"},
        {"view","add","edit","remove"},
        {"view","add","edit","remove"},
        {"view","add","edit","remove"},
        {"view","add","edit"},
        {"view","add","edit","remove"},
        {"view","add","edit","remove"},
        {"view","add","edit","remove"},
        {"view","add","edit","remove"}
    };
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch (e.getActionCommand()) {
            case "add":
                mainFrame.glassPane.setVisible(true);
                ButtonAction but = (ButtonAction) e.getSource();
                System.out.println(but.getId()+ but.getText());
                for (String[] x : actionRole) 
                    System.out.println(x.length);
                new AddNhomQuyen(mainFrame,"Thêm nhóm quyền",true,arrCN,actionRole);
                mainFrame.glassPane.setVisible(false);
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


    private void updateTable(ArrayList<NhomQuyenDTO> ketqua) {

        // System.out.println("con bo biet bay");
        table.updateTable(DataToShow(ketqua));
    }
}