// package GUI.Panel;

// import java.awt.event.MouseEvent;
// import java.awt.event.MouseListener;
// import java.util.ArrayList;

// import javax.swing.JPanel;
// import javax.swing.JScrollPane;
// import javax.swing.JTable;
// import javax.swing.table.DefaultTableModel;

// import BUS.PhuongThucTTBUS;
// import BUS.TaiKhoanBUS;
// import BUS.HoaDonBUS;
// import BUS.KhachHangBUS;
// import BUS.KhuyenMaiBUS;
// import DTO.HoaDonDTO;
// import GUI.MainFrame;
// import GUI.Components.FunctionBar;
// import GUI.Components.FunctionItem;
// import GUI.Dialog.HoaDonDialog;
// import net.miginfocom.swing.MigLayout;

// public class HoaDonPnl extends JPanel implements MouseListener{
// 	private FunctionBar functionBar;
// 	private JTable table;
// 	private DefaultTableModel model;
// 	private JScrollPane scrollPane;
//     private String[] header = {"Mã hóa đơn", "Ngày lập", "Tổng tiền", "Mã tài khoản", "Mã phương thức", "Mã khuyến mãi", "Mã khách hàng"};
// 	private String[] functionList = {"Thêm", "Chi tiết"};
// 	private String[] listAttribute = {"Ngày lập", "Tổng tiền"}; //Mã tài khoản, mã phương thức, mã khuyến mãi, mã khách hàng
// 	private HoaDonBUS hoaDonBUS;
// 	private TaiKhoanBUS taiKhoanBUS;
// 	private PhuongThucTTBUS phuongThucTTBUS;
// 	private KhuyenMaiBUS khuyenMaiBUS;
// 	private KhachHangBUS khachHangBUS;

// 	private MainFrame mainFrame;
	
// 	public HoaDonPnl() {
// 		functionBar = new FunctionBar(functionList);
// 		table = new JTable();
// 		model = new DefaultTableModel(header, 0);
// 		scrollPane = new JScrollPane();
// 		hoaDonBUS = HoaDonBUS.getInstance();
// 		taiKhoanBUS = TaiKhoanBUS.getInstance();
// 		phuongThucTTBUS = PhuongThucTTBUS.getInstance();
// 		khuyenMaiBUS = KhuyenMaiBUS.getInstance();
// 		khachHangBUS = KhachHangBUS.getInstance();
// 		this.initComponent();
// 	}
	
// 	public void initComponent() {
// 		this.setLayout(new MigLayout("wrap 1, insets 0","[grow]", "[][grow]"));
		
// 		table.setModel(model);
// 		table.setFocusable(false);
// 		table.getTableHeader().setReorderingAllowed(false);
// 		table.setDefaultEditor(Object.class, null);
// 		table.getTableHeader();
		
// 		scrollPane.setViewportView(table);
		
// 		this.add(functionBar);
// 		this.add(scrollPane, "grow");
// 		loadDataTable(hoaDonBUS.getAll());
// 		addFunctionListener();
// 	}
	
// 	public void loadDataTable(ArrayList<HoaDonDTO> listHoaDon) {
// 		for(HoaDonDTO i : listHoaDon) {
// 			model.addRow(new Object[] {i.getMaHD(), i.getNgayBan(), i.getTongTien(), i.getMaTK(), i.getMaPT(), i.getMaKM(), i.getMaKH()});
// 		}
// 	}

// 	public void addFunctionListener(){
// 		for(FunctionItem i : functionBar.getListItem()){
// 			i.addMouseListener(this);
// 		}
// 	}
// 	//Mouse Listener cho các nút chức năng (thêm, xóa, sửa)
// 	@Override
// 	public void mouseClicked(MouseEvent e) {

// 	}

// 	@Override
// 	public void mousePressed(MouseEvent e) {
// 		//nút thêm
// 		if(e.getSource() == functionBar.getListItem().get(0)){
// 			insert();
// 		}
// 		//nút chi tiết
// 		else if(e.getSource() == functionBar.getListItem().get(3)){
// 			// HoaDonDialog HoaDonDialog = new HoaDonDialog(this, "Thêm Sách", "thêm");
// 		}
		
// 	}

// 	public void insert(){
// 		new HoaDonDialog(this, "Tạo hóa đơn", "thêm", listAttribute);
// 	}


// 	@Override
// 	public void mouseReleased(MouseEvent e) {

// 	}

// 	@Override
// 	public void mouseEntered(MouseEvent e) {

// 	}

// 	@Override
// 	public void mouseExited(MouseEvent e) {

// 	}

// 	public MainFrame getMainFrame() {
// 		return mainFrame;
// 	}

// 	public void setMainFrame(MainFrame mainFrame) {
// 		this.mainFrame = mainFrame;
// 	}

// 	public JTable getTable() {
// 		return table;
// 	}

// 	public void setTable(JTable table) {
// 		this.table = table;
// 	}

// 	public DefaultTableModel getModel() {
// 		return model;
// 	}

// 	public void setModel(DefaultTableModel model) {
// 		this.model = model;
// 	}

// 	public FunctionBar getFunctionBar() {
// 		return functionBar;
// 	}

// 	public void setFunctionBar(FunctionBar functionBar) {
// 		this.functionBar = functionBar;
// 	}

// 	public HoaDonBUS getHoaDonBUS() {
// 		return hoaDonBUS;
// 	}

// 	public void setHoaDonBUS(HoaDonBUS hoaDonBUS) {
// 		this.hoaDonBUS = hoaDonBUS;
// 	}

// 	public TaiKhoanBUS getTaiKhoanBUS() {
// 		return taiKhoanBUS;
// 	}

// 	public void setTaiKhoanBUS(TaiKhoanBUS taiKhoanBUS) {
// 		this.taiKhoanBUS = taiKhoanBUS;
// 	}

// 	public PhuongThucTTBUS getPhuongThucTTBUS() {
// 		return phuongThucTTBUS;
// 	}

// 	public void setPhuongThucTTBUS(PhuongThucTTBUS phuongThucTTBUS) {
// 		this.phuongThucTTBUS = phuongThucTTBUS;
// 	}

// 	public KhuyenMaiBUS getKhuyenMaiBUS() {
// 		return khuyenMaiBUS;
// 	}

// 	public void setKhuyenMaiBUS(KhuyenMaiBUS khuyenMaiBUS) {
// 		this.khuyenMaiBUS = khuyenMaiBUS;
// 	}

// 	public KhachHangBUS getKhachHangBUS() {
// 		return khachHangBUS;
// 	}

// 	public void setKhachHangBUS(KhachHangBUS khachHangBUS) {
// 		this.khachHangBUS = khachHangBUS;
// 	}

	
	
// }

