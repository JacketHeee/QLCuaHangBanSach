package GUI.Panel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.NhaXBBUS;
import BUS.SachBUS;
import BUS.ViTriVungBUS;
import DTO.SachDTO;
import GUI.MainFrame;
import GUI.Components.FunctionBar;
import GUI.Components.FunctionItem;
import GUI.Dialog.SachDialog;
import net.miginfocom.swing.MigLayout;

public class SachPnl extends JPanel implements MouseListener{
	private FunctionBar functionBar;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã sách","Tên sách","Giá bán","Số lượng tồn","Năm XB", "Mã vùng", "Mã NXB"};
	private String[] functionList = {"Thêm","Xóa","Sửa","Chi tiết"};
	private String[] listAtribute = {"Tên sách","Giá bán","Năm XB"}; // Mã vùng, mã NXB
	private SachBUS sachBUS;
	private ViTriVungBUS viTriVungBUS;
	private NhaXBBUS nhaXBBUS;
	private MainFrame mainFrame;
	
	public SachPnl() {
		functionBar = new FunctionBar(functionList);
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		sachBUS = SachBUS.getInstance();
		viTriVungBUS = ViTriVungBUS.getInstance();
		nhaXBBUS = NhaXBBUS.getInstance();
		this.initComponent();
	}
	
	public void initComponent() {
		this.setLayout(new MigLayout("wrap 1, insets 0","[grow]", "[][grow]"));
		
		table.setModel(model);
		table.setFocusable(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader();
		
		scrollPane.setViewportView(table);
		
		this.add(functionBar);
		this.add(scrollPane, "grow");
		loadDataTable(sachBUS.getAll());
		addFunctionListener();
	}
	
	public void loadDataTable(ArrayList<SachDTO> listSach) {
		for(SachDTO i : listSach) {
			model.addRow(new Object[] {i.getMaSach(), i.getTenSach(), i.getGiaBan(), i.getSoLuongTon(), i.getNamXB(), i.getMaVung(), i.getMaNXB()});
		}
	}

	public void addFunctionListener(){
		for(FunctionItem i : functionBar.getListItem()){
			i.addMouseListener(this);
		}
	}
	//Mouse Listener cho các nút chức năng (thêm, xóa, sửa)
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		//nút thêm
		if(e.getSource() == functionBar.getListItem().get(0)){
			insert();
		}
		//nút xóa
		else if(e.getSource() == functionBar.getListItem().get(1)){
			delete();
		}
		//nút sửa
		else if(e.getSource() == functionBar.getListItem().get(2)){
			update();
		}
		//nút chi tiết
		else if(e.getSource() == functionBar.getListItem().get(3)){
			// SachDialog sachDialog = new SachDialog(this, "Thêm Sách", "thêm");
		}
		
	}

	public void insert(){
		new SachDialog(this, "Thêm Sách", "thêm", listAtribute);
	}

	public void delete(){
		if(table.getSelectedRow() == -1){
			JOptionPane.showMessageDialog(null, "Vui lòng chọn sách cần xóa");
			return;
		}
		int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa sách này ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		if(confirm == JOptionPane.YES_OPTION){
			int row = table.getSelectedRow();
			int maSach = (int)model.getValueAt(row, 0);
			if(sachBUS.delete(maSach) != 0){
				JOptionPane.showMessageDialog(null, "Xóa sách thành công !");
				model.removeRow(row);
			}
			else{
				JOptionPane.showMessageDialog(null, "Xóa sách thất bại !");
			}
		}
	}

	public void update(){
		if(table.getSelectedRow() == -1){
			JOptionPane.showMessageDialog(null, "Vui lòng chọn sách cần sửa");
			return;
		}
		new SachDialog(this, "Chỉnh sửa sách", "sửa", listAtribute);
	}


	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public SachBUS getSachBUS() {
		return sachBUS;
	}

	public void setSachBUS(SachBUS sachBUS) {
		this.sachBUS = sachBUS;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public void setModel(DefaultTableModel model) {
		this.model = model;
	}

	public ViTriVungBUS getViTriVungBUS() {
		return viTriVungBUS;
	}

	public void setViTriVungBUS(ViTriVungBUS viTriVungBUS) {
		this.viTriVungBUS = viTriVungBUS;
	}

	public NhaXBBUS getNhaXBBUS() {
		return nhaXBBUS;
	}

	public void setNhaXBBUS(NhaXBBUS nhaXBBUS) {
		this.nhaXBBUS = nhaXBBUS;
	}

	
	
}
