package utils;

import GUI.component.CustomScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

public class UIUtils {
    public static CustomScrollPane getTableScollPane(String[][] data, String... cols) {
        DefaultTableModel model = new DefaultTableModel(cols,0);

        for (String[] x : data) 
            model.addRow(x);
        
        JTable table = new JTable(model); 

        return new CustomScrollPane(table);
    }
}
