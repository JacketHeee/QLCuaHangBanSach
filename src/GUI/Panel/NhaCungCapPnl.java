package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.NhaCungCapBUS;
import DTO.NhaCungCapDTO;
import net.miginfocom.swing.MigLayout;

public class NhaCungCapPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã nhà cung cấp","Tên nhà cung cấp","Địa chỉ","Số điện thoại","Email"};
	private NhaCungCapBUS nhaCungCapBUS;
	
	public NhaCungCapPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		nhaCungCapBUS = NhaCungCapBUS.getInstance();
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
		loadDataTable(nhaCungCapBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<NhaCungCapDTO> listNhaCungCap) {
		for(NhaCungCapDTO i : listNhaCungCap) {
			model.addRow(new Object[] {i.getMaNCC(), i.getTenNCC(), i.getDiaChi(), i.getSoDT(), i.getEmail()});
		}
	}
}
