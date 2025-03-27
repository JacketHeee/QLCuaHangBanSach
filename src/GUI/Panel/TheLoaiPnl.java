package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.TheLoaiBUS;
import DTO.TheLoaiDTO;
import net.miginfocom.swing.MigLayout;

public class TheLoaiPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã thể loại","Tên thể loại"};
	private TheLoaiBUS TheLoaiBUS;
	
	public TheLoaiPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		TheLoaiBUS = TheLoaiBUS.getInstance();
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
		loadDataTable(TheLoaiBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<TheLoaiDTO> listTheLoai) {
		for(TheLoaiDTO i : listTheLoai) {
			model.addRow(new Object[] {i.getMaTheLoai(), i.getTenTheLoai()});
		}
	}
}
