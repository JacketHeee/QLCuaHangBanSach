package GUI.component;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

class PanelEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel panel;
    private JButton button;

    public PanelEditor() {
        panel = new JPanel(new BorderLayout());
        button = new JButton("Click me");

        // Xử lý sự kiện khi nhấn nút trong ô
        button.addActionListener(e -> JOptionPane.showMessageDialog(null, "Button clicked!"));

        panel.add(button, BorderLayout.CENTER);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return panel; // Trả về JPanel có thể tương tác
    }

    @Override
    public Object getCellEditorValue() {
        return panel; // Trả về JPanel để không bị lỗi
    }
}

