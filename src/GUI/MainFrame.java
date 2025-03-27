package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GUI.component.CustomTitleBar;
import net.miginfocom.swing.MigLayout;

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

		JPanel panel = new JPanel(new MigLayout("wrap 1")); 
		panel.add(new CustomTitleBar(this, ))

		add(panel);
	}

}