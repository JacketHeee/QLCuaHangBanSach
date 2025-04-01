package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import GUI.component.CustomTitleBar;
import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;

public class MainFrame extends JFrame {
	private int width = 1024; 
	private int height = 768; 
	private String title = "Quản lý cửa hàng máy tính"; 

	private JPanel menuTaskBar;
	private JPanel mainContent;

	public MainFrame() {
		setTitle(title);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		//tu dong mo full man hinh
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(true);
		
		//bỏ tiêu để thêm tiêu đề custom
		setUndecorated(true);

		JPanel panel = new JPanel(new MigLayout("wrap 1, insets 0")); 
		CustomTitleBar titleBar = new CustomTitleBar(this,Color.decode(baseTheme.selectedButton)); 
		titleBar.addTab("setting");
		panel.add(titleBar,"dock north"); 


		JPanel mainPanel = new JPanel(new MigLayout("insets 0, gap 0"));

		menuTaskBar = menuTaskBar();
		mainContent = mainContentPanel();

		mainPanel.add(menuTaskBar,"pushy, growy");
		mainPanel.add(mainContent,"push, grow");


		panel.add(mainPanel,"push, grow");

		add(panel);
	}

	//menu task bar
	private int widthmenu = 250;

	private JPanel menuTaskBar() {
		JPanel panel = new JPanel(new MigLayout("wrap 1, debug"));
		panel.setPreferredSize(new Dimension(widthmenu, 100));
		butInfor = createButtonInfor();
		panel.add(butInfor,"pushx,growx");
		
		return panel;
	}


	//button information
	private JPanel butInfor;
	private int heightButtonInfor = 80;
	private JPanel createButtonInfor() {
		JPanel but = new JPanel(new MigLayout("","[]10[]","[]")); 
		// but.setPreferredSize(new Dimension(but.getWidth(),heightButtonInfor));
		// JLabel labelImage = new JLabel(new ImageIcon(getClass().getResource("../resources/img/icon/image.png")));
		JLabel labelImage = new JLabel(new FlatSVGIcon(MainFrame.class.getResource("../resources/img/icon/myAccount.svg")).derive(60, 60));

		but.add(labelImage);
		but.add(new JButton("con bo biet bay"));
		return but;
	}

	//maincontent
	private JPanel mainContentPanel() {
		JPanel panel = new JPanel();

		return panel;
	}

	public static void main(String[] args) {
		FlatIntelliJLaf.setup();
		new MainFrame().setVisible(true);
	}

}