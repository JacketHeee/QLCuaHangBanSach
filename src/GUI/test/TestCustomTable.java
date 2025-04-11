package GUI.test;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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

        // JOptionPane.showMessageDialog(null, "Xin chào bạn!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        // JOptionPane.showMessageDialog(null, "Xin chào bạn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        // JOptionPane.showMessageDialog(null, "Xin chào bạn!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        // JOptionPane.showMessageDialog(null, "Xin chào bạn!", "Thông báo", JOptionPane.QUESTION_MESSAGE);
        // JOptionPane.showMessageDialog(null, "Xin chào bạn!", "Thông báo", JOptionPane.PLAIN_MESSAGE);

        //  JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        //  JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát không?", "Xác nhận", JOptionPane.YES_OPTION);
        //  JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát không?", "Xác nhận", JOptionPane.NO_OPTION);
        //  JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát không?", "Xác nhận", JOptionPane.CANCEL_OPTION);
        //  JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát không?", "Xác nhận", JOptionPane.CLOSED_OPTION);

        Object[] options = {"Yes", "Cancel"};
        int result = JOptionPane.showOptionDialog(
            null,
            "Bạn thực sự muốn xóa?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.ERROR_MESSAGE,
            null,
            options,
            options[0]
        );

        if (result == 0) {
            System.out.println("Người dùng chọn YES");
        } else {
            System.out.println("Người dùng chọn CANCEL hoặc đóng hộp thoại");
        }


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