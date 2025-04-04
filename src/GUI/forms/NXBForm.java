package GUI.forms;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import BUS.NhaXBBUS;
import DTO.NhaXBDTO;

import java.util.ArrayList;
import java.util.List;

import GUI.component.ButtonAction;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTable;
import net.miginfocom.swing.MigLayout;
import utils.UIUtils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JButton;

public class NXBForm extends JPanel {

    private String title;
    private String[] header = {"Mã nhà xuất bản","Tên nhà xuất bản","Địa chỉ","Số điện thoại","Email"};
    NhaXBBUS nhaXBBUS;

    public NXBForm(String title) {
        this.title = title;
        nhaXBBUS = NhaXBBUS.getInstance();
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

        for (String[] x : arrActions) {
            panel.add(new ButtonAction(x[0],x[1],x[2]));
        }

        return panel;
    }
    
    public ArrayList<String[]> Data(){
        ArrayList<NhaXBDTO> listKH = nhaXBBUS.getAll();
        ArrayList<String[]> data = new ArrayList<>();
        for(NhaXBDTO i : listKH){
            data.add(new String[]{i.getMaNXB() + "", i.getTenNXB(), i.getDiaChi(), i.getSoDT(), i.getEmail()});
        }
        return(data);
    }
    /////////////////////////////////////////////////////////////////

    String[][] actions = {
        {"edit.svg","edit"},
        {"remove.svg","remove"}
    };

    private JPanel getMainContent() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        CustomTable table = new CustomTable(Data(),actions, header);
        panel.add(new CustomScrollPane(table),"push, grow");
        return panel;
    }
}