package GUI.component.search;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import interfaces.Searchable;
import GUI.forms.SachForm;
import net.miginfocom.swing.MigLayout;

import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

import java.util.ArrayList;



public class SearchPanel<T> extends JPanel {
    private JTextField inputSearch;
    private JButton butSearch;
    private Searchable<T> searchable;
    private JComboBox<String> comboBox;
    //callback de dua ket qua ra ngoai
    private Consumer<ArrayList<T>> onResultReady;
    
    public SearchPanel(Searchable<T> searchable, Consumer<ArrayList<T>> onResultReady, JComboBox<String> comboBox) {
        this.searchable = searchable; 
        this.onResultReady = onResultReady;
        this.comboBox = comboBox;
        init();
    }

    private void init() {
        setLayout(new MigLayout("insets 3"));
        inputSearch = new JTextField(30);
        inputSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm");
        inputSearch.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusWidth:0; innerFocusWidth: 0");
        add(inputSearch);

        butSearch = new JButton(new FlatSVGIcon(SearchPanel.class.getResource("/resources/img/icon/search.svg")).derive(20,20));
        butSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));

        butSearch.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusWidth:0; innerFocusWidth: 0");
        
        this.putClientProperty(FlatClientProperties.STYLE, "background: #ffffff; arc:5");

        // butSearch.addActionListener(e -> {
        //     String keyword = inputSearch.getText();
        //     List<T> result = searchable.search(keyword);
        //     onResultReady.accept(result);
        // }); 

        // Gộp phần xử lý tìm kiếm vào một method để tái sử dụng
        Runnable doSearch = () -> {
            String keyword = inputSearch.getText();
            ArrayList<T> result = searchable.search(keyword, comboBox);
            onResultReady.accept(result);
        };

        // Sự kiện khi nhấn nút tìm
        butSearch.addActionListener(e -> doSearch.run());

        // // Sự kiện khi nhấn ENTER trong ô input
        // inputSearch.addActionListener(e -> doSearch.run());

        inputSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                doSearch.run();
            }
        });

        add(butSearch);
    }

    public void setPlaceHolder(String text) {
        inputSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, text);
        inputSearch.repaint();
        inputSearch.revalidate();
    }

    public void setVisibleButtonSearch(boolean visible) {
        butSearch.setVisible(visible);
        repaint();
        revalidate();
    }

    public JTextField getSearchField() {
        return inputSearch;
    }
    
}
