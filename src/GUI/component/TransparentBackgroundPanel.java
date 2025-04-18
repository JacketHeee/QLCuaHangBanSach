    package GUI.component;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.image.BufferedImage;
    import java.io.File;
    import javax.imageio.ImageIO;

    public class TransparentBackgroundPanel extends JPanel {
        private BufferedImage backgroundImage;

        public TransparentBackgroundPanel() {
            try {
                backgroundImage = ImageIO.read(getClass().getResource("/resources/img/icon/nenHome.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            setOpaque(false); // Để cho nền trong suốt được vẽ đúng
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                // Đặt độ trong suốt: 0.0f = hoàn toàn trong suốt, 1.0f = hoàn toàn đậm
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g2d.dispose();
            }
        }
    }
