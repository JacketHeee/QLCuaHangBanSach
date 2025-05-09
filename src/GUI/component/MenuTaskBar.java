package GUI.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import GUI.Login;
import GUI.MainFrame;
import GUI.forms.HomeForm;
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
import GUI.forms.TaiKhoanForm;
import GUI.forms.TheLoaiForm;
import GUI.forms.ThongTinTaiKhoanForm;
import GUI.forms.VungKeForm;
import GUI.forms.thongke.TongQuanForm;
import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;


public class MenuTaskBar extends JPanel implements MouseListener {
    
	//menu task bar
	private int widthmenu = 250;
	private JPanel butLogout;
    private MainFrame mainFrame;
    private ArrayList<String[]> arrCN;
    private JPanel menuChucNang;
    private ButtonTaskBar butstart;

    
    public MenuTaskBar(MainFrame mainFrame, ArrayList<String[]> arrCN) {
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
        butstart = new ButtonTaskBar(arrCN.get(0)[0], arrCN.get(0)[1], arrCN.get(0)[2]);
        butstart.addMouseListener(this);
        panel.add(butstart,"pushx,growx");
        ButtonTaskBar but;
		for (int i=1; i<arrCN.size(); i++) {
            String[] x = arrCN.get(i);
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
	private JLabel textAcc;
	private JPanel createButtonInfor() {
		JPanel but = new JPanel(new MigLayout("","[]10[]","[]")); 

		but.putClientProperty(FlatClientProperties.STYLE, "arc: 5");

		JPanel panel = new JPanel(new MigLayout("aligny center"));
		JLabel labelImage = new JLabel(new FlatSVGIcon(MainFrame.class.getResource("../resources/img/icon/myAccount.svg")).derive(60, 60));
		panel.setOpaque(false);

		textAcc = new JLabel("<html><b>"+ mainFrame.getNhanVien().getHoTen() +"</b></html>");
		panel.add(textAcc,"wrap");
		panel.add(new JLabel(mainFrame.getTaiKhoan().getUsername()));

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

	public void setTextAcc(String text) {
		textAcc.setText("<html><b>"+ text +"</b></html>");
	}
    
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == butInfor) {
			setDefaulColor();
			mainFrame.setPanel(new ThongTinTaiKhoanForm(mainFrame.getTaiKhoan(), name -> {
				textAcc.setText("<html><b>"+ name +"</b></html>");
			}));
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
				case "home": 
					mainFrame.setPanel(new HomeForm());
					break;
				case "book":
					mainFrame.setPanel(new SachForm(but.getText(), mainFrame));
					break;
				case "category":
					mainFrame.setPanel(new TheLoaiForm(but.getText(), mainFrame));
					break;
				case "author":
					mainFrame.setPanel(new TacGiaForm(but.getText(), mainFrame));
					break;
				case "nxb":
					mainFrame.setPanel(new NXBForm(but.getText(), mainFrame));
					break;
				case "ncc":
					mainFrame.setPanel(new NhaCungCapForm(but.getText(), mainFrame));
					break;
				case "vungtl":
					mainFrame.setPanel(new VungKeForm(but.getText(), mainFrame));
					break;
				case "qlInput":
					mainFrame.setPanel(new QLPhieuNhapForm(but.getText(), mainFrame));
					break;
				case "qlBill":
					mainFrame.setPanel(new QLHoaDonForm(but.getText(), mainFrame));
					break;
				case "promotion":
					mainFrame.setPanel(new KhuyenMaiForm(but.getText(), mainFrame));
					break;
				case "nv":
					mainFrame.setPanel(new NhanVienForm(but.getText(), mainFrame));
					break;
				case "taikhoan":
					mainFrame.setPanel(new TaiKhoanForm(but.getText(), mainFrame));
					break;
				case "khachhang":
					mainFrame.setPanel(new KhachHangForm(but.getText(),mainFrame));
					break;
				case "pttt":
					mainFrame.setPanel(new PhuongThucThanhToanForm(but.getText(), mainFrame));
					break;
				case "phanquyen":
					mainFrame.setPanel(new PhanQuyenForm(mainFrame,but.getText()));
					break;
				case "report":
					mainFrame.setPanel(new TongQuanForm(mainFrame));
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

    private void setDefaulColor() {
        for (Component x : menuChucNang.getComponents()) {
            ButtonTaskBar button = (ButtonTaskBar) x;
            button.setSelected(false);
        }
    }
}
