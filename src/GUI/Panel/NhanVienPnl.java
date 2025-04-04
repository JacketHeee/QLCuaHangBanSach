// package GUI.Panel;

// import java.awt.event.MouseEvent;
// import java.awt.event.MouseListener;
// import java.util.ArrayList;

// import javax.swing.JOptionPane;
// import javax.swing.JPanel;
// import javax.swing.JScrollPane;
// import javax.swing.JTable;
// import javax.swing.table.DefaultTableModel;

// import BUS.NhanVienBUS;
// import BUS.TaiKhoanBUS;
// import DTO.NhanVienDTO;
// import GUI.MainFrame;
// import GUI.Components.FunctionBar;
// import GUI.Components.FunctionItem;
// import GUI.Dialog.NhanVienDialog;
// import net.miginfocom.swing.MigLayout;

// public class NhanVienPnl extends JPanel implements MouseListener{
// 	private FunctionBar functionBar;
// 	private JTable table;
// 	private DefaultTableModel model;
// 	private JScrollPane scrollPane;
// 	private String[] header = {"Mã nhân viên","Họ tên","Ngày sinh","Giới tính","Số điện thoại","Mã tài khoản"};
// 	private String[] functionList = {"Thêm","Xóa","Sửa","Chi tiết"};
// 	private String[] listAtribute = {"Họ tên","Ngày sinh"};//giới tính, số điện thoại, Mã tài khoản
// 	private NhanVienBUS nhanVienBUS;
// 	private TaiKhoanBUS taiKhoanBUS;
// 	private MainFrame mainFrame;
	
// 	public NhanVienPnl() {
// 		functionBar = new FunctionBar(functionList);
// 		table = new JTable();
// 		model = new DefaultTableModel(header, 0);
// 		scrollPane = new JScrollPane();
// 		nhanVienBUS = NhanVienBUS.getInstance();
// 		taiKhoanBUS = TaiKhoanBUS.getInstance();
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
// 		loadDataTable(nhanVienBUS.getAll());
// 		addFunctionListener();
// 	}
	
// 	public void loadDataTable(ArrayList<NhanVienDTO> listNhanVien) {
// 		for(NhanVienDTO i : listNhanVien) {
// 			model.addRow(new Object[] {i.getMaNV(), i.getHoTen(), i.getNgaySinh(), i.getGioiTinh(), i.getSoDT(), i.getMaTK()});
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
// 			// NhanVienDialog NhanVienDialog = new NhanVienDialog(this, "Thêm nhân viên", "thêm");
// 		}
		
// 	}

// 	public void insert(){
// 		new NhanVienDialog(this, "Thêm nhân viên", "thêm", listAtribute);
// 	}

// 	public void delete(){
// 		if(table.getSelectedRow() == -1){
// 			JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần xóa");
// 			return;
// 		}
// 		int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nhân viên này ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
// 		if(confirm == JOptionPane.YES_OPTION){
// 			int row = table.getSelectedRow();
// 			int maNhanVien = (int)model.getValueAt(row, 0);
// 			if(nhanVienBUS.delete(maNhanVien) != 0){
// 				JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công !");
// 				model.removeRow(row);
// 			}
// 			else{
// 				JOptionPane.showMessageDialog(null, "Xóa nhân viên thất bại !");
// 			}
// 		}
// 	}

// 	public void update(){
// 		if(table.getSelectedRow() == -1){
// 			JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần sửa");
// 			return;
// 		}
// 		new NhanVienDialog(this, "Chỉnh sửa nhân viên", "sửa", listAtribute);
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

// 	public NhanVienBUS getNhanVienBUS() {
// 		return nhanVienBUS;
// 	}

// 	public void setNhanVienBUS(NhanVienBUS nhanVienBUS) {
// 		this.nhanVienBUS = nhanVienBUS;
// 	}

// 	public TaiKhoanBUS getTaiKhoanBUS() {
// 		return taiKhoanBUS;
// 	}

// 	public void setTaiKhoanBUS(TaiKhoanBUS taiKhoanBUS) {
// 		this.taiKhoanBUS = taiKhoanBUS;
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

