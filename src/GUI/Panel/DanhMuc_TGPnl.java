package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.DanhMuc_TGBUS;
import DTO.DanhMuc_TGDTO;
import net.miginfocom.swing.MigLayout;

public class DanhMuc_TGPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã tác giả","Mã sách"};
	private DanhMuc_TGBUS danhMuc_TGBUS;
	
	public DanhMuc_TGPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		danhMuc_TGBUS = DanhMuc_TGBUS.getInstance();
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
		loadDataTable(danhMuc_TGBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<DanhMuc_TGDTO> listDanhMuc_TG) {
		for(DanhMuc_TGDTO i : listDanhMuc_TG) {
			model.addRow(new Object[] {i.getMaTacGia(), i.getMaSach()});
		}
	}
}
