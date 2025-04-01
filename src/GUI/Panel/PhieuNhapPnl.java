package GUI.Panel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.TaiKhoanBUS;
import BUS.PhieuNhapBUS;

import BUS.NhaCungCapBUS;

import DTO.PhieuNhapDTO;
import GUI.MainFrame;
import GUI.Components.FunctionBar;
import GUI.Components.FunctionItem;
import GUI.Dialog.PhieuNhapDialog;
import net.miginfocom.swing.MigLayout;

public class PhieuNhapPnl extends JPanel implements MouseListener{
	private FunctionBar functionBar;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã nhập","Ngày nhập","Tổng tiền","Mã nhà cung cấp","Mã tài khoản"};
	private String[] functionList = {"Thêm", "Chi tiết"};
	private String[] listAttribute = {"Ngày lập", "Tổng tiền"}; //Mã nhà cung cấp, mã tài khoản
	private PhieuNhapBUS phieuNhapBUS;
	private NhaCungCapBUS nhaCungCapBUS;
	private TaiKhoanBUS taiKhoanBUS;

	private MainFrame mainFrame;
	
	public PhieuNhapPnl() {
		functionBar = new FunctionBar(functionList);
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		phieuNhapBUS = PhieuNhapBUS.getInstance();
		nhaCungCapBUS = NhaCungCapBUS.getInstance();
		taiKhoanBUS = TaiKhoanBUS.getInstance();
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
		loadDataTable(phieuNhapBUS.getAll());
		addFunctionListener();
	}
	
	public void loadDataTable(ArrayList<PhieuNhapDTO> listPhieuNhap) {
		for(PhieuNhapDTO i : listPhieuNhap) {
			model.addRow(new Object[] {i.getMaNhap(), i.getNgayNhap(), i.getTongTien(), i.getMaNCC(), i.getMaTK()});
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
		//nút chi tiết
		else if(e.getSource() == functionBar.getListItem().get(3)){
			// PhieuNhapDialog PhieuNhapDialog = new PhieuNhapDialog(this, "Thêm Sách", "thêm");
		}
		
	}

	public void insert(){
		new PhieuNhapDialog(this, "Tạo phiếu nhập", "thêm", listAttribute);
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

	public FunctionBar getFunctionBar() {
		return functionBar;
	}

	public void setFunctionBar(FunctionBar functionBar) {
		this.functionBar = functionBar;
	}

	public PhieuNhapBUS getPhieuNhapBUS() {
		return phieuNhapBUS;
	}

	public void setPhieuNhapBUS(PhieuNhapBUS PhieuNhapBUS) {
		this.phieuNhapBUS = PhieuNhapBUS;
	}

	public TaiKhoanBUS getTaiKhoanBUS() {
		return taiKhoanBUS;
	}

	public void setTaiKhoanBUS(TaiKhoanBUS taiKhoanBUS) {
		this.taiKhoanBUS = taiKhoanBUS;
	}

	public NhaCungCapBUS getNhaCungCapBUS() {
		return nhaCungCapBUS;
	}

	public void setNhaCungCapBUS(NhaCungCapBUS nhaCungCapBUS) {
		this.nhaCungCapBUS = nhaCungCapBUS;
	}

	
}

