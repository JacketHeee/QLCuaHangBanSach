package GUI.component.search;

import javax.swing.*;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import interfaces.Searchable;
import GUI.forms.SachForm;
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class SuggestSearchPanel<T> extends JPanel {
    private JTextField inputSearch;
    private JButton butSearch;
    //interface xây dựng sẵn
    private Searchable<T> searchable;
    private Consumer<ArrayList<T>> onResultReady;
    private int maxSuggestions = 10; // mặc định 5


    private JPopupMenu suggestionPopup;

    public SuggestSearchPanel(Searchable<T> searchable, Consumer<ArrayList<T>> onResultReady) {
        this.searchable = searchable;
        this.onResultReady = onResultReady;
        init();
    }

    private void init() {
        setLayout(new MigLayout("insets 3"));
        inputSearch = new JTextField(30);
        inputSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm");
        inputSearch.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusWidth:0; innerFocusWidth: 0");
        add(inputSearch);

        butSearch = new JButton(new FlatSVGIcon(SachForm.class.getResource("/resources/img/icon/search.svg")).derive(20, 20));
        butSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        butSearch.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; focusWidth:0; innerFocusWidth: 0");

        putClientProperty(FlatClientProperties.STYLE, "background: #ffffff; arc:5");
        add(butSearch);

        suggestionPopup = new JPopupMenu();

        Runnable doSearch = () -> {
            String keyword = inputSearch.getText();
            ArrayList<T> result = searchable.search(keyword);

            onResultReady.accept(result);
        };

        // Khi nhấn nút tìm
        butSearch.addActionListener(e -> doSearch.run());

        // Khi nhấn ENTER
        // Khi nhấn ENTER
        inputSearch.addActionListener(e -> {
            // Chỉ gọi doSearch khi có từ khóa hợp lệ
            if (!inputSearch.getText().isEmpty()) {
                doSearch.run();
            }
        });

        // Gợi ý khi gõ
        inputSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = inputSearch.getText();

                if (keyword.isEmpty()) {
                    suggestionPopup.setVisible(false);
                    return;
                }

                ArrayList<T> suggestions = searchable.search(keyword);
                
                if (suggestions.size() > maxSuggestions) {
                    suggestions = new ArrayList<>(suggestions.subList(0, maxSuggestions));
                }
            

                if (suggestions.isEmpty()) {
                    suggestionPopup.setVisible(false);
                    return;
                }

                showSuggestions(suggestions);

                // SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                //     @Override
                //     protected Void doInBackground() throws Exception {
                //         String keyword = inputSearch.getText();
        
                //         if (keyword.isEmpty()) {
                //             suggestionPopup.setVisible(false);
                //             return null;
                //         }
        
                //         ArrayList<T> suggestions = searchable.search(keyword);
                //         if (suggestions.size() > maxSuggestions) {
                //             suggestions = new ArrayList<>(suggestions.subList(0, maxSuggestions));
                //         }
        
                //         if (suggestions.isEmpty()) {
                //             suggestionPopup.setVisible(false);
                //             return null;
                //         }
        
                //         // Update UI after search is done
                //         SwingUtilities.invokeLater(() -> showSuggestions(suggestions));
        
                //         return null;
                //     }
                // };
                // worker.execute();
                
            }
        });
    }

    private void showSuggestions(ArrayList<T> suggestions) {
        suggestionPopup.removeAll();  // Xóa các mục cũ đi
    
        for (T item : suggestions) {
            JMenuItem menuItem = new JMenuItem(item.toString());
    
            // Thêm sự kiện mouse hover để thay đổi màu nền khi di chuột qua
            menuItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    menuItem.setBackground(Color.LIGHT_GRAY);  // Hoặc màu khác nếu cần
                }
    
                @Override
                public void mouseExited(MouseEvent e) {
                    menuItem.setBackground(null);  // Trả lại màu nền cũ
                }
            });
    
            // Thêm sự kiện action để xử lý khi chọn mục
            menuItem.addActionListener(e -> {
                inputSearch.setText(item.toString());  // Cập nhật ô tìm kiếm
                suggestionPopup.setVisible(false);  // Ẩn bảng gợi ý
            });
    
            suggestionPopup.add(menuItem);  // Thêm mục vào bảng gợi ý
        }
    
        suggestionPopup.show(inputSearch, 0, inputSearch.getHeight());  // Hiển thị lại bảng gợi ý
        inputSearch.requestFocusInWindow();  // Đảm bảo ô tìm kiếm luôn có focus
    }
    

    public void setPalaceHolder(String text) {
        inputSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, text);
        inputSearch.repaint();
        inputSearch.revalidate();
    }

    public void setVisibleButtonSearch(boolean visible) {
        butSearch.setVisible(visible);
        repaint();
        revalidate();
    }

    public void setMaxSuggestions(int maxSuggestions) {
        this.maxSuggestions = maxSuggestions;
    }

}
