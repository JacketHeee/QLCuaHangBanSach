package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.ViTriVungBUS;
import DTO.ViTriVungDTO;
import net.miginfocom.swing.MigLayout;

public class ViTriVungPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã vùng","Tên vùng"};
	private ViTriVungBUS viTriVungBUS;
	
	public ViTriVungPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		viTriVungBUS = ViTriVungBUS.getInstance();
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
		loadDataTable(viTriVungBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<ViTriVungDTO> listViTriVung) {
		for(ViTriVungDTO i : listViTriVung) {
			model.addRow(new Object[] {i.getMaVung(), i.getTenVung()});
		}
	}
}
