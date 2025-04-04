package GUI.dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import GUI.component.CustomScrollPane;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

public class AddNhomQuyen extends JDialog {
    JFrame parent;
    String title;
    boolean modal;
    private String[][] arrCN;
    private String[][] actionRole;

    public AddNhomQuyen(JFrame parent, String title, boolean modal, String[][] arrCN,String[][] actionRole) {
        this.arrCN = arrCN;
        this.actionRole = actionRole;
        super(parent,title,modal);
        init(); 
    }

    private void init() {
        setSize(1024,768);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel(new MigLayout());
        panel.add(getInforPanel(),"pushx, growx,wrap");
        panel.add(getActionChooser(),"push,grow");

        add(panel);

        setVisible(true);
    }

    
    private JTextField fieldNameRole;
    
    private JPanel getInforPanel() {
        JPanel panel = new JPanel(new MigLayout("al center center"));
        panel.add(new JLabel("<html><b>Tên nhóm quyền: </b></html>"));
        fieldNameRole = new JTextField();
        panel.add(fieldNameRole,"pushx, grow");        
        return panel;
    }

    private JPanel getActionChooser() {
        JPanel panel = new JPanel(new MigLayout(""));
    
        panel.add(new JLabel("<html><b>DANH MỤC CHỨC NĂNG</b></html>"), "pushx, growx, cell 0 0");
        panel.add(new JLabel("<html><b>Xem</b></html>"), "pushx,al center, cell 1 0");
        panel.add(new JLabel("<html><b>Thêm</b></html>"), "pushx,al center, cell 2 0");
        panel.add(new JLabel("<html><b>Sửa</b></html>"), "pushx,al center, cell 3 0");
        panel.add(new JLabel("<html><b>Xóa</b></html>"), "pushx,al center, cell 4 0,wrap");

        for (int i=0; i<arrCN.length; i++) {
            panel.add(new JLabel(arrCN[i][0]),"pushx,gaptop 10");
            for (int j=0; j<3; j++) 
                panel.add(new JCheckBox(),"pushx, al center");
            panel.add(new JCheckBox(),"pushx, al center, wrap");
        }

        return panel;
    }
}
