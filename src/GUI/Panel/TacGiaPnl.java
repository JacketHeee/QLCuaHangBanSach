package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.TacGiaBUS;
import DTO.TacGiaDTO;
import net.miginfocom.swing.MigLayout;

public class TacGiaPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã tác giả", "Tên tác giả"};
	private TacGiaBUS tacGiaBUS;
	
	public TacGiaPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		tacGiaBUS = TacGiaBUS.getInstance();
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
		loadDataTable(tacGiaBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<TacGiaDTO> listTacGia) {
		for(TacGiaDTO i : listTacGia) {
			model.addRow(new Object[] {i.getMaTacGia(), i.getTenTacGia()});
		}
	}
}
