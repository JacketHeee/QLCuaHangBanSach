package utils;

import GUI.component.CustomScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class UIUtils {
    public static CustomScrollPane getTableScollPane(String[][] data, String... cols) {
        DefaultTableModel model = new DefaultTableModel(cols,0);

        for (String[] x : data) 
            model.addRow(x);
        
        JTable table = new JTable(model); 

        return new CustomScrollPane(table);
    }

    public static int messageRemove(String massage) { //0 = yes, 
        Object[] options = {"Yes", "Cancel"};
        return JOptionPane.showOptionDialog(
            null,
            massage,
            "Xác nhận",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.ERROR_MESSAGE,
            null,
            options,
            options[0]
        );
    }
}
