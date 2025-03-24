package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.ChiTietQuyenBUS;
import DTO.ChiTietQuyenDTO;
import net.miginfocom.swing.MigLayout;

public class ChiTietQuyenPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã Role","Mã chức năng","Hành động"};
	private ChiTietQuyenBUS chiTietQuyenBUS;
	
	public ChiTietQuyenPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		chiTietQuyenBUS = ChiTietQuyenBUS.getInstance();
		this.initComponent();
	}
	
	public void initComponent() {
		this.setLayout(new MigLayout("wrap 1","[grow]", "[grow]"));
		
		table.setModel(model);
		table.setFocusable(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader();
		
		scrollPane.setViewportView(table);
		
		this.add(scrollPane, "grow");
		loadDataTable(chiTietQuyenBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<ChiTietQuyenDTO> listChiTietQuyen) {
		for(ChiTietQuyenDTO i : listChiTietQuyen) {
			model.addRow(new Object[] {i.getMaRole(), i.getMaChucNang(), i.getHanhDong()});
		}
	}
}
