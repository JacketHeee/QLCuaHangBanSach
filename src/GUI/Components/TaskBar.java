// package GUI.Components;

// import java.awt.Color;
// import java.awt.Cursor;
// import java.awt.event.MouseEvent;
// import java.awt.event.MouseListener;
// import java.util.ArrayList;

// import javax.swing.JPanel;

// import GUI.Panel.CT_HoaDonPnl;
// import GUI.Panel.CT_PhieuNhapPnl;
// import GUI.Panel.ChiTietQuyenPnl;
// import GUI.Panel.ChucNangPnl;
// import GUI.Panel.DanhMuc_TGPnl;
// import GUI.Panel.HoaDonPnl;
// import GUI.Panel.KM_SachPnl;
// import GUI.Panel.KhachHangPnl;
// import GUI.Panel.KhuyenMaiPnl;
// import GUI.Panel.NhaCungCapPnl;
// import GUI.Panel.NhaXBPnl;
// import GUI.Panel.NhanVienPnl;
// import GUI.Panel.NhomQuyenPnl;
// import GUI.Panel.PhanLoaiPnl;
// import GUI.Panel.PhieuNhapPnl;
// import GUI.Panel.PhuongThucTTPnl;
// import GUI.Panel.SachPnl;
// import GUI.Panel.TacGiaPnl;
// import GUI.Panel.TaiKhoanPnl;
// import GUI.Panel.TheLoaiPnl;
// import GUI.Panel.ViTriVungPnl;
// import GUI.MainFrame;
// import net.miginfocom.swing.MigLayout;

// public class TaskBar extends JPanel implements MouseListener{
// 	private ArrayList<ItemTaskBar> list;
// 	private MainFrame mainFrame;
	
// 	public TaskBar(MainFrame mainFrame, String... str) {
// 		this.mainFrame = mainFrame;
// 		list = new ArrayList<>();
// 		for(String i : str) {
// 			list.add(new ItemTaskBar(i));
// 		}
// 		this.initComponent();
// 	}
	
// 	public void initComponent() {
// 		this.setBackground(Color.decode("#EEEEEE"));
// 		this.setLayout(new MigLayout("wrap 1", "[grow]"));
// 		for(ItemTaskBar i : list) {
// 			this.add(i, "grow");
// 			i.addMouseListener(this);
// 		}
// 	}
	
// 	public ArrayList<ItemTaskBar> getList() {
// 		return(this.list);
// 	}

// 	@Override
// 	public void mouseClicked(MouseEvent e) {
// 		// TODO Auto-generated method stub
		
// 	}

// 	@Override
// 	public void mousePressed(MouseEvent e) {
// 		if(e.getSource() == list.get(0)) {
// 			mainFrame.setPanel(new SachPnl());
// 		}
// 		else if(e.getSource() == list.get(1)) {
// 			mainFrame.setPanel(new TheLoaiPnl());
// 		}
// 		else if(e.getSource() == list.get(2)) {
// 			mainFrame.setPanel(new TacGiaPnl());
// 		}
// 		else if(e.getSource() == list.get(3)) {
// 			mainFrame.setPanel(new NhaXBPnl());
// 		}
// 		else if(e.getSource() == list.get(4)) {
// 			mainFrame.setPanel(new ViTriVungPnl());
// 		}
// 		else if(e.getSource() == list.get(5)) {
// 			mainFrame.setPanel(new NhaCungCapPnl());
// 		}
// 		else if(e.getSource() == list.get(6)) {
// 			mainFrame.setPanel(new PhieuNhapPnl());
// 		}
// 		else if(e.getSource() == list.get(7)) {
// 			mainFrame.setPanel(new HoaDonPnl());
// 		}
// 		else if(e.getSource() == list.get(8)) {
// 			mainFrame.setPanel(new KhuyenMaiPnl());
// 		}
// 		else if(e.getSource() == list.get(9)) {
// 			mainFrame.setPanel(new NhanVienPnl());
// 		}
// 		else if(e.getSource() == list.get(10)) {
// 			mainFrame.setPanel(new TaiKhoanPnl());
// 		}
// 		else if(e.getSource() == list.get(11)) {
// 			mainFrame.setPanel(new KhachHangPnl());
// 		}
// 		else if(e.getSource() == list.get(12)) {
// 			mainFrame.setPanel(new NhomQuyenPnl());
// 		}
// 		else if(e.getSource() == list.get(13)) {
// 			mainFrame.setPanel(new PhuongThucTTPnl());
// 		}
// 		else if(e.getSource() == list.get(14)) {
// 			mainFrame.setPanel(new PhanLoaiPnl());
// 		}
// 		else if(e.getSource() == list.get(15)) {
// 			mainFrame.setPanel(new DanhMuc_TGPnl());
// 		}
// 		else if(e.getSource() == list.get(16)) {
// 			mainFrame.setPanel(new CT_PhieuNhapPnl());
// 		}
// 		else if(e.getSource() == list.get(17)) {
// 			mainFrame.setPanel(new CT_HoaDonPnl());
// 		}
// 		else if(e.getSource() == list.get(18)) {
// 			mainFrame.setPanel(new KM_SachPnl());
// 		}
// 		else if(e.getSource() == list.get(19)) {
// 			mainFrame.setPanel(new ChiTietQuyenPnl());
// 		}
// 		else if(e.getSource() == list.get(20)) {
// 			mainFrame.setPanel(new ChucNangPnl());
// 		}
// 	}

// 	@Override
// 	public void mouseReleased(MouseEvent e) {
// 		// TODO Auto-generated method stub
		
// 	}

// 	@Override
// 	public void mouseEntered(MouseEvent e) {
// 		JPanel panel = (JPanel)e.getSource();
// 		panel.setBackground(Color.decode("#444444"));
// 		panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
// 	}

// 	@Override
// 	public void mouseExited(MouseEvent e) {
// 		JPanel panel = (JPanel)e.getSource();
// 		panel.setBackground(Color.decode("#333333"));
		
// 	}
// }
