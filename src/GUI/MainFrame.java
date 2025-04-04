package GUI;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Component;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;

import GUI.component.CustomTitleBar;
import GUI.component.MenuTaskBar;
import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MainFrame extends JFrame  implements ActionListener{
	private int width = 1024; 
	private int height = 768; 
	private String title = "Quản lý cửa hàng máy tính"; 

	private MenuTaskBar menuTaskBar;
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

		menuTaskBar = new MenuTaskBar(this,arrCN);
		mainContent = mainContentPanel();

		mainPanel.add(menuTaskBar,"pushy, growy");
		mainPanel.add(mainContent,"push, grow");


		panel.add(mainPanel,"push, grow");

		add(panel);

		addActionListener(this);
	}

	

	private JPanel listChucNang;

	private String[][] arrCN = {
		{"Home","home.svg","home"},
		{"Sách","book.svg","book"},
		{"Thể loại","category_1.svg","category"},
		{"Tác giả","author.svg","author"},
		{"Nhà xuất bản","NXB.svg","nxb"},
		{"Vùng kệ","areaCategory.svg","vungtl"},
		{"Nhà cung cấp","supplier.svg","ncc"},
		{"Tạo phiếu nhập","createInput.svg","createInput"},
		{"Quản lý phiếu nhập","listInput.svg","qlInput"},
		{"Tạo hóa đơn","createBill.svg","createBill"},
		{"Quản lý hóa đơn","listBill.svg","qlBill"},
		{"Khuyến mãi","promotion.svg","promotion"},
		{"Phương thức thanh toán","paymentMethod.svg","pttt"},
		{"Nhân viên","staff.svg","nv"},
		{"Tài khoản","account.svg","taikhoan"},
		{"Khách hàng","buyer.svg","khachhang"},
		{"Phân quyền","layer.svg","phanquyen"},
		{"Báo cáo & thống kê","report.svg","report"}
	};


	private JButton butmaintext;

	//maincontent
	private JPanel mainContentPanel() {
		JPanel panel = new JPanel(new MigLayout("insets 0"));
		butmaintext = new JButton("con bo biet bay");
		panel.add(butmaintext);
		return panel;
	}


	private void addActionListener(ActionListener listener) {
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}	

	public static void main(String[] args) {
		FlatIntelliJLaf.setup();
		new MainFrame().setVisible(true);
	}

	public void setPanel(Component panel) {
		// butmaintext.setText(txt);
        mainContent.removeAll();
        mainContent.add(panel,"push, grow");
        mainContent.repaint();
        mainContent.validate();
    }
}