// package GUI.Panel;

// import java.awt.event.MouseEvent;
// import java.awt.event.MouseListener;
// import java.util.ArrayList;

// import javax.swing.JOptionPane;
// import javax.swing.JPanel;
// import javax.swing.JScrollPane;
// import javax.swing.JTable;
// import javax.swing.table.DefaultTableModel;

// import BUS.KhachHangBUS;
// import DTO.KhachHangDTO;
// import GUI.MainFrame;
// import GUI.Components.FunctionBar;
// import GUI.Components.FunctionItem;
// import GUI.Dialog.KhachHangDialog;
// import net.miginfocom.swing.MigLayout;

// public class KhachHangPnl extends JPanel implements MouseListener{
// 	private FunctionBar functionBar;
// 	private JTable table;
// 	private DefaultTableModel model;
// 	private JScrollPane scrollPane;
// 	private String[] header = {"Mã khách hàng","Tên khách hàng","Số điện thoại","Giới tính"};
// 	private String[] functionList = {"Thêm","Xóa","Sửa","Chi tiết"};
// 	private String[] listAtribute = {"Tên khách hàng","Số điện thoại"};
// 	private KhachHangBUS khachHangBUS;

// 	private MainFrame mainFrame;
	
// 	public KhachHangPnl() {
// 		functionBar = new FunctionBar(functionList);
// 		table = new JTable();
// 		model = new DefaultTableModel(header, 0);
// 		scrollPane = new JScrollPane();
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
// 		loadDataTable(khachHangBUS.getAll());
// 		addFunctionListener();
// 	}
	
// 	public void loadDataTable(ArrayList<KhachHangDTO> listKhachHang) {
// 		for(KhachHangDTO i : listKhachHang) {
// 			model.addRow(new Object[] {i.getMaKH(), i.getTenKH(), i.getSoDT(), i.getGioiTinh()});
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
// 		//nút xóa
// 		else if(e.getSource() == functionBar.getListItem().get(1)){
// 			delete();
// 		}
// 		//nút sửa
// 		else if(e.getSource() == functionBar.getListItem().get(2)){
// 			update();
// 		}
// 		//nút chi tiết
// 		else if(e.getSource() == functionBar.getListItem().get(3)){
// 			// KhachHangDialog KhachHangDialog = new KhachHangDialog(this, "Thêm Sách", "thêm");
// 		}
		
// 	}

// 	public void insert(){
// 		new KhachHangDialog(this, "Thêm Khách Hàng", "thêm", listAtribute);
// 	}

// 	public void delete(){
// 		if(table.getSelectedRow() == -1){
// 			JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng cần xóa");
// 			return;
// 		}
// 		int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa khách hàng này ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
// 		if(confirm == JOptionPane.YES_OPTION){
// 			int row = table.getSelectedRow();
// 			int maKhachHang = (int)model.getValueAt(row, 0);
// 			if(khachHangBUS.delete(maKhachHang) != 0){
// 				JOptionPane.showMessageDialog(null, "Xóa khách hàng thành công !");
// 				model.removeRow(row);
// 			}
// 			else{
// 				JOptionPane.showMessageDialog(null, "Xóa khách hàng thất bại !");
// 			}
// 		}
// 	}

// 	public void update(){
// 		if(table.getSelectedRow() == -1){
// 			JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng cần sửa");
// 			return;
// 		}
// 		new KhachHangDialog(this, "Chỉnh sửa khách hàng", "sửa", listAtribute);
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

// 	public KhachHangBUS getKhachHangBUS() {
// 		return khachHangBUS;
// 	}

// 	public void setKhachHangBUS(KhachHangBUS khachHangBUS) {
// 		this.khachHangBUS = khachHangBUS;
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

// }
