package GUI.component;

import javax.swing.*;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;

import java.awt.*;

public class LayeredLabelHome extends JLayeredPane{
    private String title; 
    private String desTitle;
    private String icon;

    public LayeredLabelHome(String icon,String title, String desTitle) {
        this.icon = icon;
        this.title = title; 
        this.desTitle = desTitle;
        init();
    }

    private void init() {
        setPreferredSize(new Dimension(500,200));
        setOpaque(false);
        // setBackground(Color.decode(baseTheme.backgroundColor));
        
        JLabel icon = new JLabel(new FlatSVGIcon(ButtonTaskBar.class.getResource("/resources/img/icon/" + this.icon)).derive(100,100));
        // icon.setOpaque(true);
        icon.setBackground(Color.decode(baseTheme.mainColor));
        icon.putClientProperty(FlatClientProperties.STYLE, "arc: 50; background:" + baseTheme.mainColor);
        icon.setBounds(0, 0, 150, 150);

        JPanel panel = new JPanel(new MigLayout("al center center"));
        panel.add(new JLabel("<html><b><font size='+3' color='"+baseTheme.mainColor+"'>"+title+"</font></b></html>"),"wrap, al center");
        panel.add(new JLabel("<html><font size='+1' color='"+baseTheme.mainColor+"'>"+desTitle+"</font></html>"),"");
        panel.setBounds(60, 30, 440, 175);
        panel.setOpaque(true);
        panel.putClientProperty(FlatClientProperties.STYLE,"arc: 50; background: #ffffff");

        add(icon,JLayeredPane.MODAL_LAYER);
        add(panel,JLayeredPane.DEFAULT_LAYER);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Ví dụ JLayeredPane");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        
        FlatIntelliJLaf.setup();
        JLayeredPane layeredPane = new LayeredLabelHome("","Đa dạng","Kho sách phong phú với <br/>nhiều thể loại khác nhau");

        frame.add(layeredPane);
        frame.setVisible(true);
    }

}
