package GUI.component;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.color.*;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import GUI.forms.SachForm;
import net.miginfocom.swing.MigLayout;

public class PanelSearch extends JPanel {
    private JComboBox<String> filter;
    private JTextField textSearchForm;
    private JButton butSearch;
    private JButton butRefresh;

    public PanelSearch(String... listAttribute){
        filter = new JComboBox<>(listAttribute);
        textSearchForm = new JTextField();
        butSearch = new JButton();
        butRefresh = new JButton();
        this.initCompnonent();
    }

    public void initCompnonent(){
        this.setBackground(Color.decode("#FFFFFF"));
        this.setLayout(new MigLayout("wrap 2, debug"));

        filter.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusWidth:0; innerFocusWidth: 0;");

        textSearchForm.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm");
        textSearchForm.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusWidth:0; innerFocusWidth: 0");

        butSearch.setIcon(new FlatSVGIcon(SachForm.class.getResource("../../resources/img/icon/search.svg")).derive(20,20));
        butSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));

        butSearch.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusWidth:0; innerFocusWidth: 0");
        
        JPanel search = new JPanel();
        search.setLayout(new MigLayout("wrap 2, insets 0"));

        search.add(textSearchForm);
        search.putClientProperty(FlatClientProperties.STYLE, "background: #ffffff; arc:5");
        search.add(butSearch);

        butRefresh = new JButton(new FlatSVGIcon(SachForm.class.getResource("../../resources/img/icon/refresh.svg")).derive(26,26));
        butRefresh.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusWidth:0; innerFocusWidth: 0;");
        butRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));

        this.add(filter,"h 32");
        this.add(search,"");
        this.add(butRefresh,"");

        this.add(filter);
        this.add(textSearchForm);
    }


}
