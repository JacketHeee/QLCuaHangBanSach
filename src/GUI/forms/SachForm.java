package GUI.forms;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import GUI.component.ButtonAction;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import net.miginfocom.swing.MigLayout;
import utils.UIUtils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JButton;

public class SachForm extends JPanel {

    private String title;
    public SachForm(String title) {
        this.title = title;
        init();
    }
    
    private void init() {
        setLayout(new MigLayout("wrap 1, gap 10"));

        add(getHeader(),"pushx, growx");
        add(getActions(),"pushx, growx");
        add(getMainContent(),"push,grow");

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

        for (String[] x : arrActions) {
            panel.add(new ButtonAction(x[0],x[1],x[2]));
        }

        return panel;
    }
    
    private String[][] data = {
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"},
        {"1","hii","1","Vết nhơ Huyền Trang","2024","100","100.000","hii"}
    }; 
    /////////////////////////////////////////////////////////////////
    private JPanel getMainContent() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        panel.add(new CustomScrollPane(new CustomTable(data, "#","Hình ảnh","Mã sách","Tên sách","Năm XB","Hiện có","Giá bán(đ)","Hành động")),"push, grow");
        return panel;
    }
}
