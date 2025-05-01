package GUI.component.search;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import interfaces.Searchable;
import net.miginfocom.swing.MigLayout;

public class SearchBarPanel<T> extends JPanel {
    private JTextField txtSearch;
    private JComboBox<String> comboBox;
    private SearchPanel<T> searchPanel;
    private JButton butRefresh;
    private String[] listCBX;// Lưu cả tên và id

    public SearchBarPanel(
        String[] listCBX,
        Searchable<T> searchable,
        Consumer<ArrayList<T>> onResultReady,
        Runnable onRefreshClicked // Có thể truyền null nếu không cần xử lý
    ) {
        setLayout(new MigLayout("insets 0"));
        this.listCBX = listCBX;

        // txtSearch = new JTextField(20);  // Thay đổi kích thước tùy ý
        // add(txtSearch, "growx");

        // Combo box
        comboBox = new JComboBox<>(listCBX);
        comboBox.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusWidth:0; innerFocusWidth: 0;");
        add(comboBox, "h 32");

        // Search panel
        searchPanel = new SearchPanel<>(searchable, onResultReady, comboBox);
        add(searchPanel, "gapleft 5");

        // Refresh button
        butRefresh = new JButton(new FlatSVGIcon(SearchBarPanel.class.getResource("/resources/img/icon/refresh.svg")).derive(26, 26));
        butRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        butRefresh.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusWidth:0; innerFocusWidth: 0;");
        butRefresh.addActionListener(e -> {
            if (onRefreshClicked != null) {
                searchPanel.setEmptyInput();
                onRefreshClicked.run();
            }
        });
        add(butRefresh, "gapleft 5");
    }

    public String getSelectedItem() {
        return (String) comboBox.getSelectedItem();
    }

    public void setComboBoxEnabled(boolean enabled) {
        comboBox.setEnabled(enabled);
    }

    public String getSearchText() {
        return txtSearch.getText();
    }

    public JComboBox<String> getComboBox() {
        return comboBox;
    }    

    public JTextField getSearchField() {
        return searchPanel.getSearchField();
    }
    
    public String getFilter() {
        return getComboBox().getSelectedItem().toString();
    }

    public String getKeyword() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getKeyword'");
    }
    
    // public void setSearchFieldText(String text) {
    //     searchPanel.setSearchText(text);
    // }

    // public String getSearchText() {
    //     return searchPanel.getSearchText();
    // }

    // public void triggerSearch() {
    //     searchPanel.triggerSearch();
    // }
}
