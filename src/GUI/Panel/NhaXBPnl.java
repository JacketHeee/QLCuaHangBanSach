package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.NhaXBBUS;
import DTO.NhaXBDTO;
import net.miginfocom.swing.MigLayout;

public class NhaXBPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã nhà xuất bản","Tên nhà xuất bản","Địa chỉ","Số điện thoại","Email"};
	private NhaXBBUS nhaXBBUS;
	
	public NhaXBPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		nhaXBBUS = NhaXBBUS.getInstance();
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
		loadDataTable(nhaXBBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<NhaXBDTO> listNhaXB) {
		for(NhaXBDTO i : listNhaXB) {
			model.addRow(new Object[] {i.getMaNXB(), i.getTenNXB(), i.getDiaChi(), i.getSoDT(), i.getEmail()});
		}
	}
}
