package GUI.forms.thongke;

import javax.swing.*;
import javax.swing.border.MatteBorder;


import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import GUI.component.TableNoTouch;
import GUI.forms.SachForm;

import java.util.Arrays;
import java.awt.*;
import java.util.ArrayList;

import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;

public class TonKho extends JPanel {

    private JTextField inputSearch;
    private JButton butSearch;

    public TonKho() {
        init();
    }

    private void init() {
        putClientProperty("arc:", 10);
        setLayout(new MigLayout("insets 10,gap 10"));
        // add(new JButton("con bo biet bay"));
        addInteractPanel();
        addTableTonKho();

    }

    private int widthInteractPanel = 300;


    private void addInteractPanel() {
        JPanel panel = new JPanel(new MigLayout("insets 20 10 20 10,wrap 1,gap 20    "));
        panel.setPreferredSize(new Dimension(widthInteractPanel,100));
        panel.add(new JLabel("Tìm kiếm sản phẩm"));
        panel.add(getPanelSearch(),"pushx,growx");
        panel.add(new JLabel("Từ ngày:"));
        panel.add(new JTextField(),"pushx,grow");        
        panel.add(new JLabel("Đến ngày:"));
        panel.add(new JTextField(),"pushx,grow");        
        panel.setBackground(Color.white);

        JPanel action = new JPanel(new MigLayout());
        action.setBackground(Color.white);
        action.add(new JButton("Xuất Excel"));
        action.add(new JButton("Làm mới"));

        panel.add(action,"pushx,growx");
        add(panel,"pushy,grow");
    }


    private JPanel getPanelSearch() {
        JPanel search = new JPanel(new MigLayout("insets 3"));
        inputSearch = new JTextField(30);
        inputSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm");
        inputSearch.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusWidth:0; innerFocusWidth: 0");
        search.add(inputSearch);
        butSearch = new JButton(new FlatSVGIcon(SachForm.class.getResource("../../resources/img/icon/search.svg")).derive(20,20));
        butSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));

        butSearch.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusWidth:0; innerFocusWidth: 0");
        
        search.putClientProperty(FlatClientProperties.STYLE, "background: #ffffff;");
        search.setBorder(new MatteBorder(1,1,1,1,Color.decode(baseTheme.selectedButton)));
        search.add(butSearch);

        return search;
    }
    
    private void addTableTonKho() {
        TableNoTouch table = new TableNoTouch(datas, "Mã sản phẩm","Tên sản phẩm","Tồn đầu kỳ","Nhập trong kỳ","Xuất trong kỳ","Tồn cuối kỳ");
        add(table,"push,grow");
    }   

    ArrayList<String[]> datas = new ArrayList<>(Arrays.asList(
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"},
        new String[] {"1", "Con bo biet bay", " 100", "50","150","1"}
    ));
}
