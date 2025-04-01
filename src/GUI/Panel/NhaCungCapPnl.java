package GUI.Panel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.NhaCungCapBUS;
import DTO.NhaCungCapDTO;
import GUI.MainFrame;
import GUI.Components.FunctionBar;
import GUI.Components.FunctionItem;
import GUI.Dialog.NhaCungCapDialog;
import net.miginfocom.swing.MigLayout;

public class NhaCungCapPnl extends JPanel implements MouseListener{
	private FunctionBar functionBar;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã nhà cung cấp","Tên nhà cung cấp","Địa chỉ","Số điện thoại","Email"};
	private String[] functionList = {"Thêm","Xóa","Sửa","Chi tiết"};
	private String[] listAtribute = {"Tên nhà cung cấp","Địa chỉ","Số điện thoại","Email"}; // Mã vùng, mã NXB
	private NhaCungCapBUS nhaCungCapBUS;
	private MainFrame mainFrame;
	
	public NhaCungCapPnl() {
		functionBar = new FunctionBar(functionList);
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		nhaCungCapBUS = NhaCungCapBUS.getInstance();

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
		loadDataTable(nhaCungCapBUS.getAll());
		addFunctionListener();
	}
	
	public void loadDataTable(ArrayList<NhaCungCapDTO> listNhaCungCap) {
		for(NhaCungCapDTO i : listNhaCungCap) {
			model.addRow(new Object[] {i.getMaNCC(), i.getTenNCC(), i.getDiaChi(), i.getSoDT(), i.getEmail()});
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
			// NhaCungCapDialog NhaCungCapDialog = new NhaCungCapDialog(this, "Thêm nhà cung cấp", "thêm");
		}
		
	}

	public void insert(){
		new NhaCungCapDialog(this, "Thêm nhà cung cấp", "thêm", listAtribute);
	}

	public void delete(){
		if(table.getSelectedRow() == -1){
			JOptionPane.showMessageDialog(null, "Vui lòng chọn nhà cung cấp cần xóa");
			return;
		}
		int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nhà cung cấp này ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		if(confirm == JOptionPane.YES_OPTION){
			int row = table.getSelectedRow();
			int maNhaCungCap = (int)model.getValueAt(row, 0);
			if(nhaCungCapBUS.delete(maNhaCungCap) != 0){
				JOptionPane.showMessageDialog(null, "Xóa nhà cung cấp thành công !");
				model.removeRow(row);
			}
			else{
				JOptionPane.showMessageDialog(null, "Xóa nhà cung cấp thất bại !");
			}
		}
	}

	public void update(){
		if(table.getSelectedRow() == -1){
			JOptionPane.showMessageDialog(null, "Vui lòng chọn nhà cung cấp cần sửa");
			return;
		}
		new NhaCungCapDialog(this, "Chỉnh sửa nhà cung cấp", "sửa", listAtribute);
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

	public NhaCungCapBUS getNhaCungCapBUS() {
		return nhaCungCapBUS;
	}

	public void setNhaCungCapBUS(NhaCungCapBUS nhaCungCapBUS) {
		this.nhaCungCapBUS = nhaCungCapBUS;
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
}

