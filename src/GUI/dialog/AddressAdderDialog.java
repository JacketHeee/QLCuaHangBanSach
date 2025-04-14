package GUI.dialog;

import javax.swing.*;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import GUI.component.AddressAdderPanel;
import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.awt.event.*;

public class AddressAdderDialog extends JDialog {
    private AddressAdderPanel addressPanel;
    private JButton closeButton;
    private Consumer<ArrayList<String>> onCloseCallback;

    public AddressAdderDialog(Frame parent) {
        super(parent, "", true); // Modal dialog
        initializeDialog();
    }

    private void initializeDialog() {
        setLayout(new MigLayout("fill, insets 0", "[grow]", "[grow][]"));
        setPreferredSize(new Dimension(570, 490));
        setMinimumSize(new Dimension(570, 490));
        setMaximumSize(new Dimension(570, 490));
        setResizable(false);
        setLocationRelativeTo(getParent());

        addressPanel = new AddressAdderPanel();
        add(addressPanel, "push,grow, wrap");

        JPanel buttonPanel = new JPanel(new MigLayout("fill", "[center]", ""));
        closeButton = new JButton("Đóng");
        closeButton.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 15));
        closeButton.setBackground(new Color(100, 100, 100));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setBackground(new Color(120, 120, 120));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setBackground(new Color(100, 100, 100));
            }
        });
        closeButton.addActionListener(e -> closeDialog());
        buttonPanel.add(closeButton);
        add(buttonPanel, "center");

        // Gọi callback khi thêm địa chỉ
        addressPanel.setOnAddressAdded(address -> {
            if (onCloseCallback != null) {
                onCloseCallback.accept(addressPanel.getSavedAddresses());
            }
        });

        pack();
    }

    private void closeDialog() {
        if (onCloseCallback != null) {
            onCloseCallback.accept(addressPanel.getSavedAddresses());
        }
        dispose();
    }

    public void setOnCloseCallback(Consumer<ArrayList<String>> callback) {
        this.onCloseCallback = callback;
    }

    public ArrayList<String> getSavedAddresses() {
        return addressPanel.getSavedAddresses();
    }

    public static void main(String[] args) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("resources/themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatIntelliJLaf.setup();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Dialog");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 600);
            frame.setLocationRelativeTo(null);

            JButton openDialogButton = new JButton("Mở Dialog Thêm Địa Chỉ");
            openDialogButton.addActionListener(e -> {
                AddressAdderDialog dialog = new AddressAdderDialog(frame);
                dialog.setVisible(true);
                dialog.setOnCloseCallback(addresses -> {
                    System.out.println("Địa chỉ đã lưu: " + addresses);
                });
            });

            frame.setLayout(new FlowLayout());
            frame.add(openDialogButton);
            frame.setVisible(true);
        });
    }
}