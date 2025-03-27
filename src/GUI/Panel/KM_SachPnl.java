package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.KM_SachBUS;
import DTO.KM_SachDTO;
import net.miginfocom.swing.MigLayout;

public class KM_SachPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã khuyến mãi","Mã sách"};
	private KM_SachBUS kM_SachBUS;
	
	public KM_SachPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		kM_SachBUS = KM_SachBUS.getInstance();
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
		loadDataTable(kM_SachBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<KM_SachDTO> listKM_Sach) {
		for(KM_SachDTO i : listKM_Sach) {
			model.addRow(new Object[] {i.getMaKM(), i.getMaSach()});
		}
	}
}
