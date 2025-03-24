package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.PhuongThucTTBUS;
import DTO.PhuongThucTTDTO;
import net.miginfocom.swing.MigLayout;

public class PhuongThucTTPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã phương thức", "Tên phương thức"};
	private PhuongThucTTBUS phuongThucTTBUS;
	
	public PhuongThucTTPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		phuongThucTTBUS = PhuongThucTTBUS.getInstance();
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
		loadDataTable(phuongThucTTBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<PhuongThucTTDTO> listPhuongThucTT) {
		for(PhuongThucTTDTO i : listPhuongThucTT) {
			model.addRow(new Object[] {i.getMaPT(), i.getTenPTTT()});
		}
	}
}
