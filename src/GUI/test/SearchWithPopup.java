package GUI.test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SearchWithPopup {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Gợi ý dịu dàng 😚");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); // dùng layout tự do để set vị trí dễ
        frame.setSize(400, 300);

        JTextField searchField = new JTextField();
        searchField.setBounds(50, 50, 200, 30);

        JPopupMenu suggestionPopup = new JPopupMenu();
        suggestionPopup.setFocusable(false); // QUAN TRỌNG: không cướp focus

        List<String> suggestions = List.of("Hà Nội", "Huế", "Hải Phòng", "Hòa Bình", "Hồ Chí Minh");

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = searchField.getText().trim().toLowerCase();
                suggestionPopup.removeAll();

                if (!text.isEmpty()) {
                    for (String s : suggestions) {
                        if (s.toLowerCase().startsWith(text)) {
                            JMenuItem item = new JMenuItem(s);
                            item.setFocusable(false); // cũng đừng để item lấy focus
                            item.addActionListener(ev -> {
                                searchField.setText(s);
                                suggestionPopup.setVisible(false);
                            });
                            suggestionPopup.add(item);
                        }
                    }
                }

                if (suggestionPopup.getComponentCount() > 0) {
                    suggestionPopup.show(searchField, 0, searchField.getHeight());
                } else {
                    suggestionPopup.setVisible(false);
                }
            }
        });

        // Nếu mất focus khỏi text field thì ẩn popup
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                suggestionPopup.setVisible(false);
            }
        });

        frame.add(searchField);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
