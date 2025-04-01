package GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import GUI.component.ButtonTaskBar;
import GUI.component.CustomScrollPane;
import GUI.component.CustomTitleBar;
import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainFrame extends JFrame  implements ActionListener, MouseListener{
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

		addActionListener(this);
	}

	//menu task bar
	private int widthmenu = 250;
	private JPanel butLogout;

	private JPanel menuTaskBar() {
		JPanel panel = new JPanel(new MigLayout("wrap 1"));
		panel.setPreferredSize(new Dimension(widthmenu, 100));
		butInfor = createButtonInfor();
		butLogout = createButLogout();

		panel.add(butInfor,"pushx, growx");

		CustomScrollPane jPane = new CustomScrollPane(panelChucNang());
		
		panel.add(jPane,"push, grow");
		panel.add(butLogout,"pushx, growx, gaptop 10");
		return panel;
	}

	private JPanel listChucNang;

	private String[][] arrCN = {
		{"Home","home.svg","home"},
		{"Sách","book.svg","book"},
		{"Thể loại","category_1.svg","category"},
		{"Tác giả","author.svg","author"},
		{"Nhà xuất bản","NXB.svg","nxb"},
		{"Vùng kệ","areaCategory.svg","home"},
		{"Nhà cung cấp","supplier.svg","vungtl"},
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

	private JPanel panelChucNang() {
		JPanel panel = new JPanel(new MigLayout("gap 4, wrap 1"));
		// panel.add(new JButton("con bi biet bay"));
		for (String[] x : arrCN) {
			panel.add(new ButtonTaskBar(x[0], x[1], x[2]),"pushx,growx");
		}
		return panel;
	}

	private JPanel createButLogout() {
		JPanel panel = new JPanel(new MigLayout("al center center, insets 10, gap 10"));
		panel.setBackground(Color.white);
		panel.add(new JLabel(new FlatSVGIcon(MainFrame.class.getResource("../resources/img/icon/logout.svg")).derive(20,20)));
		panel.add(new JLabel("Đăng xuất"));

		panel.putClientProperty(FlatClientProperties.STYLE, "arc: 10");

		panel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				panel.setBackground(Color.decode(baseTheme.selectedButton));
				panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e) {
				panel.setBackground(Color.white);
				panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});

		panel.addMouseListener(this);
		return panel;
	}

	//button information
	private JPanel butInfor;
	private int heightButtonInfor = 80;
	private JPanel createButtonInfor() {
		JPanel but = new JPanel(new MigLayout("","[]10[]","[]")); 

		but.putClientProperty(FlatClientProperties.STYLE, "arc: 5");

		JPanel panel = new JPanel(new MigLayout("aligny center"));
		JLabel labelImage = new JLabel(new FlatSVGIcon(MainFrame.class.getResource("../resources/img/icon/myAccount.svg")).derive(60, 60));
		panel.setOpaque(false);
		panel.add(new JLabel("<html><b>Nguyen Hung Manh</b></html>"),"wrap");
		panel.add(new JLabel("admin"));

		but.add(labelImage);
		but.add(panel,"push, grow");

		but.setOpaque(true);
		
		but.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                but.setBackground(Color.decode(baseTheme.selectedButton));  // Khi hover vào
				but.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                but.setBackground(Color.decode(baseTheme.backgroundColor));  // Khi rời chuột
				but.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

		but.addMouseListener(this);
		return but;
	}


	private JButton butmaintext;
	//maincontent
	private JPanel mainContentPanel() {
		JPanel panel = new JPanel();
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

	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == butInfor) {
			JOptionPane.showMessageDialog(this,"Tinh nang danh phat trien","con bo biet bay",JOptionPane.INFORMATION_MESSAGE);
		}
		else 
		if (e.getSource() == butLogout) {
			this.dispose();
			java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		FlatIntelliJLaf.setup();
		new MainFrame().setVisible(true);
	}

	

}