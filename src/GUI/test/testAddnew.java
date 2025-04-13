package GUI.test;

import javax.swing.*;

import com.formdev.flatlaf.FlatIntelliJLaf;

import GUI.dialog.AddressAdderDialog;
import net.miginfocom.swing.MigLayout;

import java.awt.*;

public class testAddnew extends JFrame {
    
    public testAddnew() {
        init();
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new MigLayout());

        // panel.add(new AddNewProvincePanel());
        AddressAdderDialog dialog = new AddressAdderDialog(this);
        dialog.setOnCloseCallback(addresses -> {
            System.out.println("Địa chỉ đã lưu: " + addresses);
            // Xử lý danh sách địa chỉ, ví dụ: lưu vào database, hiển thị lên UI
        });
        dialog.setVisible(true);
        // panel.add(adderDialog);
        add(panel);
        pack();
    }

    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        EventQueue.invokeLater(() -> new testAddnew().setVisible(true));
    }

}
