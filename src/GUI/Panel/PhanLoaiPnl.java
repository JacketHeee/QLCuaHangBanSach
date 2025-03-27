package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.PhanLoaiBUS;
import DTO.PhanLoaiDTO;
import net.miginfocom.swing.MigLayout;

public class PhanLoaiPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã sách","Mã thể loại"};
	private PhanLoaiBUS phanLoaiBUS;
	
	public PhanLoaiPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		phanLoaiBUS = PhanLoaiBUS.getInstance();
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
		loadDataTable(phanLoaiBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<PhanLoaiDTO> listPhanLoai) {
		for(PhanLoaiDTO i : listPhanLoai) {
			model.addRow(new Object[] {i.getMaSach(), i.getMaTheLoai()});
		}
	}
}
