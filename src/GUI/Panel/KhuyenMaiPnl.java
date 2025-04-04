// package GUI.Panel;

// import java.awt.event.MouseEvent;
// import java.awt.event.MouseListener;
// import java.util.ArrayList;

// import javax.swing.JOptionPane;
// import javax.swing.JPanel;
// import javax.swing.JScrollPane;
// import javax.swing.JTable;
// import javax.swing.table.DefaultTableModel;

// import BUS.KhuyenMaiBUS;
// import DTO.KhuyenMaiDTO;
// import GUI.MainFrame;
// import GUI.Components.FunctionBar;
// import GUI.Components.FunctionItem;
// import GUI.Dialog.KhuyenMaiDialog;
// import net.miginfocom.swing.MigLayout;

// public class KhuyenMaiPnl extends JPanel implements MouseListener{
// 	private FunctionBar functionBar;
// 	private JTable table;
// 	private DefaultTableModel model;
// 	private JScrollPane scrollPane;
// 	private String[] header = {"Mã khuyến mãi","Tên khuyến mãi","Điều kiện giảm","Giá trị giảm", "Ngày bắt đầu", "Ngày kết thúc"};
// 	private String[] functionList = {"Thêm","Xóa","Sửa","Chi tiết"};
// 	private String[] listAtribute = {"Tên khuyến mãi","Điều kiện giảm","Giá trị giảm"};
// 	private KhuyenMaiBUS khuyenMaiBUS;
// 	private MainFrame mainFrame;
	
// 	public KhuyenMaiPnl() {
// 		functionBar = new FunctionBar(functionList);
// 		table = new JTable();
// 		model = new DefaultTableModel(header, 0);
// 		scrollPane = new JScrollPane();
// 		khuyenMaiBUS = KhuyenMaiBUS.getInstance();
// 		this.initComponent();
// 	}
	
// 	public void initComponent() {
// 		this.setLayout(new MigLayout("wrap 1","[grow]", "[grow]"));
		
// 		table.setModel(model);
// 		table.setFocusable(false);
// 		table.getTableHeader().setReorderingAllowed(false);
// 		table.setDefaultEditor(Object.class, null);
// 		table.getTableHeader();
		
// 		scrollPane.setViewportView(table);
		
// 		this.add(functionBar);
// 		this.add(scrollPane, "grow");
// 		loadDataTable(khuyenMaiBUS.getAll());
// 		addFunctionListener();
// 	}
	
// 	public void loadDataTable(ArrayList<KhuyenMaiDTO> listKhuyenMai) {
// 		for(KhuyenMaiDTO i : listKhuyenMai) {
// 			model.addRow(new Object[] {i.getMaKM(), i.getTenKM(), i.getDieuKienGiam(), i.getGiaTriGiam(), i.getNgayBatDau(), i.getNgayKetThuc()});
// 		}
// 	}

// 	public void addFunctionListener(){
// 		for(FunctionItem i : functionBar.getListItem()){
// 			i.addMouseListener(this);
// 		}
// 	}

// 	@Override
// 	public void mouseClicked(MouseEvent e) {
// 		// TODO Auto-generated method stub
// 		throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
// 	}

// 	@Override
// 	public void mousePressed(MouseEvent e) {
// 			//nút thêm
// 			if(e.getSource() == functionBar.getListItem().get(0)){
// 				insert();
// 			}
// 			//nút xóa
// 			else if(e.getSource() == functionBar.getListItem().get(1)){
// 				delete();
// 			}
// 			//nút sửa
// 			else if(e.getSource() == functionBar.getListItem().get(2)){
// 				update();
// 			}
// 			//nút chi tiết
// 			else if(e.getSource() == functionBar.getListItem().get(3)){

// 			}
// 	}

// 	public void insert(){
// 		new KhuyenMaiDialog(this, "Thêm khuyến mãi", "thêm", listAtribute);
// 	}

// 	public void delete(){
// 		if(table.getSelectedRow() == -1){
// 			JOptionPane.showMessageDialog(null, "Vui lòng chọn khuyến mãi cần xóa");
// 			return;
// 		}
// 		int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa khuyến mãi này ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
// 		if(confirm == JOptionPane.YES_OPTION){
// 			int row = table.getSelectedRow();
// 			int maKhuyenMai = (int)model.getValueAt(row, 0);
// 			if(khuyenMaiBUS.delete(maKhuyenMai) != 0){
// 				JOptionPane.showMessageDialog(null, "Xóa khuyến mãi thành công !");
// 				model.removeRow(row);
// 			}
// 			else{
// 				JOptionPane.showMessageDialog(null, "Xóa khuyến mãi thất bại !");
// 			}
// 		}
// 	}

// 	public void update(){
// 		if(table.getSelectedRow() == -1){
// 			JOptionPane.showMessageDialog(null, "Vui lòng chọn khuyến mãi cần sửa");
// 			return;
// 		}
// 		new KhuyenMaiDialog(this, "Chỉnh sửa khuyến mãi", "sửa", listAtribute);
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

// 	public KhuyenMaiBUS getKhuyenMaiBUS() {
// 		return khuyenMaiBUS;
// 	}

// 	public void setKhuyenMaiBUS(KhuyenMaiBUS khuyenMaiBUS) {
// 		this.khuyenMaiBUS = khuyenMaiBUS;
// 	}

// 	public MainFrame getMainFrame() {
// 		return mainFrame;
// 	}

// 	public void setMainFrame(MainFrame mainFrame) {
// 		this.mainFrame = mainFrame;
// 	}


// }
