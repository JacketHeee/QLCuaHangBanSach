package utils;

import GUI.component.CustomScrollPane;
import raven.toast.Notifications;

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JFrame;

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

    public static JPanel getPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;   
    }

    public static void showNotifi(JFrame mainFrame, String title,int type) {

        switch (type) { 
            case 1: //success
                Notifications.getInstance().setJFrame(mainFrame);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,title);
                break;
            case 2: //warning
                Notifications.getInstance().setJFrame(mainFrame);
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER,title);
                break;
            case 3: //error
                Notifications.getInstance().setJFrame(mainFrame);
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,title);
                break;
            case 4: //infor
                Notifications.getInstance().setJFrame(mainFrame);
                Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER,title);
                break;
            
        
            default:
                break;
        }
    }
}
