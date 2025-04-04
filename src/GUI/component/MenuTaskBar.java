package GUI.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import GUI.Login;
import GUI.MainFrame;
import GUI.component.MenuTaskBar;
import GUI.forms.KhachHangForm;
import GUI.forms.KhuyenMaiForm;
import GUI.forms.NXBForm;
import GUI.forms.NhaCungCapForm;
import GUI.forms.NhanVienForm;
import GUI.forms.PhanQuyenForm;
import GUI.forms.PhuongThucThanhToanForm;
import GUI.forms.QLHoaDonForm;
import GUI.forms.QLPhieuNhapForm;
import GUI.forms.SachForm;
import GUI.forms.TacGiaForm;
import GUI.forms.TheLoaiForm;
import GUI.forms.VungKeForm;
import GUI.forms.TaiKhoanForm;
import GUI.forms.TaoHoaDonForm;
import GUI.forms.TaoPhieuNhapForm;
import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MenuTaskBar extends JPanel implements MouseListener {
    
	//menu task bar
	private int widthmenu = 250;
	private JPanel butLogout;
    private MainFrame mainFrame;
    private String[][] arrCN;
    private JPanel menuChucNang;
    private ButtonTaskBar butstart;

    
    public MenuTaskBar(MainFrame mainFrame, String[][] arrCN) {
        this.mainFrame = mainFrame;
        this.arrCN = arrCN;
        init();
	}

    private void init() {
        
		setLayout(new MigLayout("wrap 1"));
		setPreferredSize(new Dimension(widthmenu, 100));
		butInfor = createButtonInfor();
		butLogout = createButLogout();

		add(butInfor,"pushx, growx");

        menuChucNang = panelChucNang();
		CustomScrollPane jPane = new CustomScrollPane(menuChucNang);
        changeColorOnCliked(butstart);
		
		add(jPane,"push, grow");
		add(butLogout,"pushx, growx, gaptop 10");
    }

    private JPanel panelChucNang() {
		JPanel panel = new JPanel(new MigLayout("gap 4, wrap 1"));
		// panel.add(new JButton("con bi biet bay"));
        butstart = new ButtonTaskBar(arrCN[0][0], arrCN[0][1], arrCN[0][2]);
        butstart.addMouseListener(this);
        panel.add(butstart,"pushx,growx");
        ButtonTaskBar but;
		for (int i=1; i<arrCN.length; i++) {
            String[] x = arrCN[i];
            but = new ButtonTaskBar(x[0], x[1], x[2]);
			panel.add(but,"pushx,growx");
            but.addMouseListener(this);
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
		panel.add(new JLabel("<html><b>Nguyễn Hùng Mạnh</b></html>"),"wrap");
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

    
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == butInfor) {
			JOptionPane.showMessageDialog(mainFrame,"Tinh nang danh phat trien","con bo biet bay",JOptionPane.INFORMATION_MESSAGE);
		}
		else 
		if (e.getSource() == butLogout) {
			mainFrame.dispose();
			java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
		}
        else {
            ButtonTaskBar but = (ButtonTaskBar) e.getSource();
            changeColorOnCliked(but);
            // mainFrame.setPanel(but.getText());
			switch (but.getId()) {
				case "book":
					mainFrame.setPanel(new SachForm(but.getText()));
					break;
				case "category":
					mainFrame.setPanel(new TheLoaiForm(but.getText()));
					break;
				case "author":
					mainFrame.setPanel(new TacGiaForm(but.getText()));
					break;
				case "nxb":
					mainFrame.setPanel(new NXBForm(but.getText()));
					break;
				case "ncc":
					mainFrame.setPanel(new NhaCungCapForm(but.getText()));
					break;
				case "vungtl":
					mainFrame.setPanel(new VungKeForm(but.getText()));
					break;
				case "qlInput":
					mainFrame.setPanel(new QLPhieuNhapForm(but.getText()));
					break;
				case "qlBill":
					mainFrame.setPanel(new QLHoaDonForm(but.getText()));
					break;
				case "promotion":
					mainFrame.setPanel(new KhuyenMaiForm(but.getText()));
					break;
				case "nv":
					mainFrame.setPanel(new NhanVienForm(but.getText()));
					break;
				case "taikhoan":
					mainFrame.setPanel(new TaiKhoanForm(but.getText()));
					break;
				case "khachhang":
					mainFrame.setPanel(new KhachHangForm(but.getText()));
					break;
				case "pttt":
					mainFrame.setPanel(new PhuongThucThanhToanForm(but.getText()));
					break;
				case "phanquyen":
					mainFrame.setPanel(new PhanQuyenForm(mainFrame,but.getText(),arrCN));
					break;
				case "createInput":
					mainFrame.setPanel(new TaoPhieuNhapForm());
					break;
				case "createBill":
					mainFrame.setPanel(new CustomScrollPane(new TaoHoaDonForm()));
					break;

				default:
					break;
			}
        }
		
	}

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
    }


    private void changeColorOnCliked(ButtonTaskBar but) {
        for (Component x : menuChucNang.getComponents()) {
            ButtonTaskBar button = (ButtonTaskBar) x;
            button.setSelected(false);
        }

        ButtonTaskBar button = (ButtonTaskBar) but;
        button.setSelected(true);
    }
}
