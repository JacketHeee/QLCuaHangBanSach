package GUI.component;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class BookStoreManager extends JFrame {
    private boolean isMaximized = true; // Trạng thái fullscreen
    private CustomTitleBar titleBar; // Sử dụng CustomTitleBar

    public BookStoreManager() {
        // Thiết lập FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Thiết lập JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // Loại bỏ thanh tiêu đề mặc định
        setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(true);
        setLayout(new MigLayout("fill", "[250][grow]", "[][grow]"));

        // Sử dụng CustomTitleBar
        titleBar = new CustomTitleBar(this, Color.WHITE);
        add(titleBar, "dock north");

        titleBar.addTab("⚙");
        titleBar.addTab("setting");
        titleBar.addTab("setting");

        // Sidebar
        JPanel sidebar = new JPanel(new MigLayout("fillx, wrap", "[grow]", "[]10[]10[]10[]"));
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        sidebar.add(new JButton("Quản lý sách"), "growx");
        sidebar.add(new JButton("Quản lý khách hàng"), "growx");
        sidebar.add(new JButton("Thống kê"), "growx");
        add(sidebar, "growy");

        // Nội dung chính
        JTable bookTable = new JTable(new Object[][]{{"1", "Java", "John", "200000", "10"}},
                new String[]{"ID", "Tên", "Tác giả", "Giá", "Số lượng"});
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, "grow");

        // Taskbar
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setPreferredSize(new Dimension(0, 30));
        statusBar.add(new JLabel("Số sách: 2 | Trạng thái: Đã kết nối"));
        add(statusBar, "dock south");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookStoreManager app = new BookStoreManager();
            app.setVisible(true);
        });
    }
}