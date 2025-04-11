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
    private JComboBox<String> comboBox;
    private SearchPanel<T> searchPanel;
    private JButton butRefresh;

    public SearchBarPanel(
        String[] comboItems,
        Searchable<T> searchable,
        Consumer<ArrayList<T>> onResultReady,
        Runnable onRefreshClicked // Có thể truyền null nếu không cần xử lý
    ) {
        setLayout(new MigLayout("insets 0"));

        // Combo box
        comboBox = new JComboBox<>(comboItems);
        comboBox.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusWidth:0; innerFocusWidth: 0;");
        add(comboBox, "h 32");

        // Search panel
        searchPanel = new SearchPanel<>(searchable, onResultReady);
        add(searchPanel, "gapleft 5");

        // Refresh button
        butRefresh = new JButton(new FlatSVGIcon(SearchBarPanel.class.getResource("/resources/img/icon/refresh.svg")).derive(26, 26));
        butRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        butRefresh.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusWidth:0; innerFocusWidth: 0;");
        butRefresh.addActionListener(e -> {
            if (onRefreshClicked != null) {
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
