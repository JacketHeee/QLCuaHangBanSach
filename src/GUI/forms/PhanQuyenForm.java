package GUI.forms;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.util.ArrayList;
import java.util.List;

import GUI.MainFrame;
import GUI.component.ButtonAction;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import GUI.dialog.AddNhomQuyen;
import net.miginfocom.swing.MigLayout;
import utils.UIUtils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhanQuyenForm extends JPanel implements ActionListener {

    private MainFrame mainFrame;
    private String title;
    private ArrayList<String[]> arrCN;

    public PhanQuyenForm(MainFrame mainframe, String title, ArrayList<String[]> arrCN) {
        this.mainFrame = mainframe;
        this.title = title;
        this.arrCN = arrCN;
        init();
    }
    
    private void init() {
        setLayout(new MigLayout("wrap 1, gap 10"));

        add(getHeader(),"pushx, growx");
        add(getActions(),"pushx, growx");
        add(getMainContent(),"push,grow, gaptop 15");

    }

    ////////////////////////////////////////////////////////////////////
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
        CustomTable table = new CustomTable(data,actions, "Mã quyền","Tên nhóm quyền");
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
}