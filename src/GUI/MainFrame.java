package GUI;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Font;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import BUS.ChiTietQuyenBUS;
import BUS.NhanVienBUS;
import BUS.NhomQuyenBUS;
import DTO.ChiTietQuyenDTO;
import DTO.NhanVienDTO;
import DTO.NhomQuyenDTO;
import DTO.TaiKhoanDTO;
import GUI.component.CustomTitleBar;
import GUI.component.DimGlassPane;
import GUI.component.MenuTaskBar;
import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
public class MainFrame extends JFrame  implements ActionListener{
	private int width = 1024; 
	private int height = 768; 
	private String title = "Quản lý cửa hàng máy tính"; 
	
	public DimGlassPane glassPane = new DimGlassPane();

	private MenuTaskBar menuTaskBar;
	private JPanel mainContent;

	private TaiKhoanDTO taiKhoan;
	private NhanVienDTO nhanVien;
	private NhanVienBUS nhanVienBUS;
	private NhomQuyenBUS nhomQuyenBUS;
	private NhomQuyenDTO nhomQuyen;
	private ChiTietQuyenBUS chiTietQuyenBUS;

	public MainFrame(TaiKhoanDTO taiKhoan) {

		this.taiKhoan = taiKhoan; 
		this.nhanVienBUS = NhanVienBUS.getInstance();
		this.nhanVien = nhanVienBUS.SelectNhanVienByMaTK(taiKhoan.getMaTK());
		this.nhomQuyenBUS = NhomQuyenBUS.getInstance();
		this.nhomQuyen = nhomQuyenBUS.SelectByID(taiKhoan.getMaRole());
		this.chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();

		setTitle(title);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setGlassPane(glassPane);
		
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

		menuTaskBar = new MenuTaskBar(this, getListCNOfThisUser());
		mainContent = mainContentPanel();

		mainPanel.add(menuTaskBar,"pushy, growy");
		mainPanel.add(mainContent,"push, grow");


		panel.add(mainPanel,"push, grow");

		add(panel);

		addActionListener(this);
	}

	

	private JPanel listChucNang;

	private String[][] arrCN = {
		// {"Home","home.svg","home"},
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

	//Set các chức năng để tạo 
	//Khi tạo nhóm quyền mới, danh mục chức năng cần sắp từ trên xuống để hàm hoạt động
	public ArrayList<String[]> getListCNOfThisUser(){	
		ArrayList<String[]> result = new ArrayList<>();
		result.add(new String[]{"Home","home.svg","home"});
		ArrayList<String> listTenCN = chiTietQuyenBUS.getListTenCNByMaNQ(nhomQuyen.getMaRole());
		int j = 0;
		for(int i = 0; i < listTenCN.size(); i++){
			while(j < arrCN.length){
				if(arrCN[j][2].equals(listTenCN.get(i))){
					result.add(arrCN[j]);
					j++;
					break;
				}	
				j++;
			}

		}
		return(result);
	}

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
		FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("resources/themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN,13));
		
		FlatIntelliJLaf.setup();
		TaiKhoanDTO taiKhoanDTO = new TaiKhoanDTO(1,"admin","123456",1);
		MainFrame mainFrame = new MainFrame(taiKhoanDTO);
		mainFrame.setVisible(true);

	}

	public void setPanel(Component panel) {
        mainContent.removeAll();
        mainContent.add(panel,"push, grow");
        mainContent.repaint();
        mainContent.validate();
    }


	public TaiKhoanDTO getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(TaiKhoanDTO taiKhoan) {
		this.taiKhoan = taiKhoan;
	}


	public NhanVienDTO getNhanVien() {
		return nhanVien;
	}


	public void setNhanVien(NhanVienDTO nhanVien) {
		this.nhanVien = nhanVien;
	}

	
}