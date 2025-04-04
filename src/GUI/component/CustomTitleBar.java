package GUI.component;

import javax.swing.*;

import com.formdev.flatlaf.FlatClientProperties;

import DTO.KM_SachDTO;
import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomTitleBar extends JPanel {
    private Point initialClick; // Để kéo cửa sổ
    private JFrame parentFrame; // Tham chiếu đến JFrame cha
    private JButton maximizeButton; // Nút phóng to/thu nhỏ
    private boolean isMaximized; // Trạng thái fullscreen
    private Timer animationTimer; // Timer cho animation
    private int steps = 8; // Số bước animation
    private int currentStep = 0;
    private Rectangle startBounds; // Kích thước/vị trí ban đầu
    private Rectangle endBounds;

    private int widthButton = 35; 
    private int heightButton = 30;

    private int heightTitle = 30;

    private Color background;

    private Dimension size = new Dimension(widthButton, heightButton);


    public CustomTitleBar(JFrame parentFrame, Color color) {
        this.parentFrame = parentFrame;
        this.parentFrame.addWindowStateListener(event -> {
            if (event.getNewState() == Frame.NORMAL) {
                centerFrame();
            }
        });
        this.isMaximized = false;
        background = color;

        // Thiết lập layout và thuộc tính
        setLayout(new MigLayout("fill, ins 0", "[200]push[][][]", "[]"));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension(screenSize.width, heightTitle));
        setBackground(background); // Màu nền xám đậm

        tab = new JPanel(new MigLayout("al center center, gap 0, insets 0"));
        // Nút Settings (bên trái)
        add(tab,"");

        // Tiêu đề (giữa)
        JLabel titleLabel = new JLabel("Quản lý cửa hàng sách");
        titleLabel.setForeground(Color.decode("#323232"));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, "growx, pushx");

        // Nút thu nhỏ
        JButton minimizeButton = new JButton("-");
        minimizeButton.setPreferredSize(size);
        minimizeButton.setFocusPainted(false);
        minimizeButton.setBackground(background);
        minimizeButton.setForeground(Color.decode("#323232"));
        minimizeButton.setBorder(null);
        // minimizeButton.addActionListener(e -> parentFrame.setState(Frame.ICONIFIED));
        minimizeButton.addActionListener(e -> {
            parentFrame.setState(Frame.ICONIFIED);
            parentFrame.addWindowStateListener(event -> {
                if (event.getNewState() == Frame.NORMAL) {
                    centerFrame(); // Gọi phương thức căn giữa
                }
            });
        });
        add(minimizeButton, "cell 2 0");

        // Nút phóng to/thu nhỏ
        maximizeButton = new JButton("o");
        maximizeButton.setPreferredSize(size);
        maximizeButton.setFocusPainted(false);
        maximizeButton.setBackground(background);
        maximizeButton.setForeground(Color.decode("#323232"));
        maximizeButton.setBorder(null);
        maximizeButton.addActionListener(e -> animateToggleMaximize());

        add(maximizeButton, "cell 3 0");

        // Nút đóng
        JButton closeButton = new JButton("x");
        closeButton.setPreferredSize(size);
        closeButton.setFocusPainted(false);
        closeButton.setBackground(background); 
        closeButton.setForeground(Color.WHITE); 
        closeButton.setBorder(null);
        closeButton.addActionListener(e -> System.exit(0));

        closeButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                closeButton.setBackground(Color.decode("#FF5555"));  // Khi hover vào
                closeButton.setForeground(Color.white);
            }
            public void mouseExited(MouseEvent e) {
                closeButton.setBackground(background);  // Khi rời chuột
                closeButton.setForeground(Color.decode(baseTheme.textColor));
            }
        });
        
        add(closeButton, "cell 4 0");

        // Kéo cửa sổ bằng thanh tiêu đề
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (!isMaximized) {
                    int x = parentFrame.getLocation().x + e.getX() - initialClick.x;
                    int y = parentFrame.getLocation().y + e.getY() - initialClick.y;
                    parentFrame.setLocation(x, y);
                }
            }
        });
    }

    private void centerFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = parentFrame.getWidth();
        int height = parentFrame.getHeight();
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;
        parentFrame.setLocation(x, y);
    }
    

    // Logic animation cho phóng to/thu nhỏ
    private void animateToggleMaximize() {
        if (animationTimer != null && animationTimer.isRunning()) {
            return; // Tránh chạy nhiều animation cùng lúc
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (isMaximized) {
            // Thu nhỏ: Từ fullscreen về kích thước ban đầu
            startBounds = new Rectangle(0, 0, screenSize.width, screenSize.height);
            endBounds = new Rectangle(parentFrame.getLocation().x, parentFrame.getLocation().y, 1024, 768);
            if (endBounds.x < 0 || endBounds.y < 0 || endBounds.x + endBounds.width > screenSize.width) {
                endBounds = new Rectangle((screenSize.width - 1024) / 2, (screenSize.height - 768) / 2, 1024, 768); // Căn giữa nếu vượt màn hình
            }
            maximizeButton.setText("O"); // Icon phóng to
        } else {
            // Phóng to: Từ kích thước hiện tại lên fullscreen
            startBounds = new Rectangle(parentFrame.getLocation().x, parentFrame.getLocation().y, parentFrame.getWidth(), parentFrame.getHeight());
            endBounds = new Rectangle(0, 0, screenSize.width, screenSize.height);
            maximizeButton.setText("o"); // Icon thu nhỏ
        }

        
        currentStep = 0;
        animationTimer = new Timer(10, e -> {
            currentStep++;
            if (currentStep <= steps) {
                double ratio = (double) currentStep / steps;
                int x = (int) (startBounds.x + (endBounds.x - startBounds.x) * ratio);
                int y = (int) (startBounds.y + (endBounds.y - startBounds.y) * ratio);
                int width = (int) (startBounds.width + (endBounds.width - startBounds.width) * ratio);
                int height = (int) (startBounds.height + (endBounds.height - startBounds.height) * ratio);
                parentFrame.setBounds(x, y, width, height);
            } else {
                animationTimer.stop();
                parentFrame.setBounds(endBounds.x, endBounds.y, endBounds.width, endBounds.height);
                isMaximized = !isMaximized;
            }
        });
        animationTimer.start();
    }

    // Getter cho maximizeButton nếu cần
    public JButton getMaximizeButton() {
        return maximizeButton;
    }

    // Setter cho isMaximized để đồng bộ trạng thái nếu cần
    public void setMaximized(boolean maximized) {
        this.isMaximized = maximized;
    }

    public JButton addTab(String text) {
        JButton settingsButton = new JButton(text); // Icon settings đơn giản
        settingsButton.setPreferredSize(new Dimension(settingsButton.getWidth(), 18));
        settingsButton.setFocusPainted(false);
        settingsButton.setBackground(background);
        settingsButton.setForeground(Color.decode("#323232"));  
        settingsButton.addActionListener(e -> JOptionPane.showMessageDialog(parentFrame, "Cài đặt đang được phát triển!"));
        
        settingsButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                settingsButton.setBackground(Color.lightGray);  // Khi hover vào
            }
            public void mouseExited(MouseEvent e) {
                settingsButton.setBackground(background);  // Khi rời chuột
            }
        });
        
        settingsButton.putClientProperty(FlatClientProperties.STYLE, "borderWidth: 0; margin:2,3,2,3 ");
        tab.add(settingsButton);
        return settingsButton;
    } 

    private JPanel tab;
}