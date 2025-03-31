package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.ChucNangBUS;
import DTO.ChucNangDTO;
import net.miginfocom.swing.MigLayout;

public class ChucNangPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã chức năng", "Tên chức năng"};
	private ChucNangBUS chucNangBUS;
	
	public ChucNangPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		chucNangBUS = ChucNangBUS.getInstance();
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
		loadDataTable(chucNangBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<ChucNangDTO> listChucNang) {
		for(ChucNangDTO i : listChucNang) {
			model.addRow(new Object[] {i.getMaChucNang(), i.getTenChucNang()});
		}
	}
}
