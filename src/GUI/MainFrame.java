package GUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GUI.component.CustomTitleBar;
import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;

public class MainFrame extends JFrame {
	private int width = 1024; 
	private int height = 768; 
	private String title = "Quản lý cửa hàng máy tính"; 

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

		JPanel panel = new JPanel(new MigLayout("wrap 1, insets 0, debug")); 
		CustomTitleBar titleBar = new CustomTitleBar(this,Color.decode(baseTheme.selectedButton)); 
		titleBar.addTab("setting");
		panel.add(titleBar,"dock north"); 

		JPanel mainPanel = new JPanel(new MigLayout("debug"));
		mainPanel.add(menuTaskBar(),"pushy, growy");
		mainPanel.add(mainContentPanel(),"push, grow");


		panel.add(mainPanel,"push, grow");

		add(panel);
	}

	private int widthmenu = 250;

	private JPanel menuTaskBar() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(widthmenu, 100));

		return panel;
	}

	private JPanel mainContentPanel() {
		JPanel panel = new JPanel();

		return panel;
	}

}