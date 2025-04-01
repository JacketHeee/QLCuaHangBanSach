package GUI.Panel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.NhomQuyenBUS;
import BUS.TaiKhoanBUS;
import DTO.TaiKhoanDTO;
import GUI.MainFrame;
import GUI.Components.FunctionBar;
import GUI.Components.FunctionItem;
import GUI.Dialog.TaiKhoanDialog;
import net.miginfocom.swing.MigLayout;

public class TaiKhoanPnl extends JPanel implements MouseListener{
	private FunctionBar functionBar;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã tài khoản","Username","Password","Mã Role"};
	private String[] functionList = {"Thêm","Xóa","Sửa","Chi tiết"};
	private String[] listAtribute = {"Username","Password"}; // Mã Role
	private TaiKhoanBUS taiKhoanBUS;
	private NhomQuyenBUS nhomQuyenBUS;
	private MainFrame mainFrame;
	
	public TaiKhoanPnl() {
		functionBar = new FunctionBar(functionList);
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		taiKhoanBUS = TaiKhoanBUS.getInstance();
		nhomQuyenBUS = NhomQuyenBUS.getInstance();
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
		loadDataTable(taiKhoanBUS.getAll());
		addFunctionListener();
	}
	
	public void loadDataTable(ArrayList<TaiKhoanDTO> listTaiKhoan) {
		for(TaiKhoanDTO i : listTaiKhoan) {
			model.addRow(new Object[] {i.getMaTK(), i.getUsername(), i.getPassword(), i.getMaRole()});
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
			// TaiKhoanDialog TaiKhoanDialog = new TaiKhoanDialog(this, "Thêm tài khoản", "thêm");
		}
		
	}

	public void insert(){
		new TaiKhoanDialog(this, "Thêm tài khoản", "thêm", listAtribute);
	}

	public void delete(){
		if(table.getSelectedRow() == -1){
			JOptionPane.showMessageDialog(null, "Vui lòng chọn tài khoản cần xóa");
			return;
		}
		int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa tài khoản này ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		if(confirm == JOptionPane.YES_OPTION){
			int row = table.getSelectedRow();
			int maTaiKhoan = (int)model.getValueAt(row, 0);
			if(taiKhoanBUS.delete(maTaiKhoan) != 0){
				JOptionPane.showMessageDialog(null, "Xóa tài khoản thành công !");
				model.removeRow(row);
			}
			else{
				JOptionPane.showMessageDialog(null, "Xóa tài khoản thất bại !");
			}
		}
	}

	public void update(){
		if(table.getSelectedRow() == -1){
			JOptionPane.showMessageDialog(null, "Vui lòng chọn tài khoản cần sửa");
			return;
		}
		new TaiKhoanDialog(this, "Chỉnh sửa tài khoản", "sửa", listAtribute);
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

	public TaiKhoanBUS getTaiKhoanBUS() {
		return taiKhoanBUS;
	}

	public void setTaiKhoanBUS(TaiKhoanBUS taiKhoanBUS) {
		this.taiKhoanBUS = taiKhoanBUS;
	}

	public NhomQuyenBUS getNhomQuyenBUS() {
		return nhomQuyenBUS;
	}

	public void setNhomQuyenBUS(NhomQuyenBUS nhomQuyenBUS) {
		this.nhomQuyenBUS = nhomQuyenBUS;
	}

	
}
