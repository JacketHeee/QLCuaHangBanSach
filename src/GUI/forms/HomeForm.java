package GUI.forms;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.component.LayeredLabelHome;
import GUI.component.TransparentBackgroundPanel;
import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;

public class HomeForm extends JPanel{

    private String title = "Hệ thống quản lý cửa hàng sách";
    protected JLabel headerTitle; 
    protected String[][] content = {
        {"bookHome.svg","Đa dạng","Kho sách phong phú với<br/>nhiều thể loại khác nhau"},
        {"quanlyHome.svg","Quản lý","Dễ dàng theo dõi và quản lý<br/>các đầu sách"},
        {"tienloiHome.svg","Tiện lợi","Hỗ trợ tìm kiếm và phân loại<br/>sách nhanh chóng"}
    };
    
    public HomeForm() {
        init();
    }

    private void init() {
        headerTitle = headerPanel(); 
        setLayout(new MigLayout(""));
        add(headerTitle,"al center,gaptop 10,wrap");
        add(mainContent(),"push,grow");
    }

    private JLabel headerPanel() {
        JLabel label = new JLabel("<html><font size='+3'><b> "+title+"</b></font></html>");
        label.setForeground(Color.decode(baseTheme.mainColor));
        return label;
    }
     
    private JPanel mainContent() {
        JPanel panel = new TransparentBackgroundPanel();
        panel.setOpaque(false);
        panel.setLayout(new MigLayout());
        
        JPanel panel2 = new JPanel(new MigLayout("insets 30,wrap 1, gap 20"));
        panel2.setOpaque(false);
        panel2.add(new LayeredLabelHome(content[0][0], content[0][1], content[0][2]));
        // panel2.add(new JLabel(),"pushy");
        panel2.add(new LayeredLabelHome(content[1][0], content[1][1], content[1][2]),"gapleft 60");
        // panel2.add(new JLabel(),"pushy");
        panel2.add(new LayeredLabelHome(content[2][0], content[2][1], content[2][2]),"gapleft 120");

        panel.add(panel2,"push");
        panel.add(new JLabel(new ImageIcon(getClass().getResource("/resources/img/icon/bookStore.png"))),"wmin 500,pushy,growy, al center center");

        return panel; 
    }
}
