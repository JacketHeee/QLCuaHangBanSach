package GUI.test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatIntelliJLaf;

import GUI.component.LabelInfor;
import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;

public class TestCustomTable extends JFrame {

    public TestCustomTable() {
        setTitle("Test Custom Table");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        setSize(1000,600);

        JPanel panel = new JPanel(new MigLayout());
        
        for (String[] x : data) 
            panel.add(new LabelInfor(x[0], x[1], x[2], x[3]),"pushx,grow");
        add(panel);
        // pack();
        setLocationRelativeTo(null);
    }

    String[][] data = {
        {"Cuốn sách hiện có","bookLabel.svg","100",baseTheme.bookLabel},
        {"Nhà cung cấp","supplierLabel.svg","98",baseTheme.supplierLabel},
        {"Khách hàng","customerLabel.svg","100",baseTheme.customerLabel},
        {"Nhân viên","staffLabel.svg","100",baseTheme.staffLabel}
        };
        
    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        SwingUtilities.invokeLater(() -> {
            new TestCustomTable().setVisible(true);
        });
    }
}