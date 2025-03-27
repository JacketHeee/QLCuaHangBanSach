package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.NhomQuyenBUS;
import DTO.NhomQuyenDTO;
import net.miginfocom.swing.MigLayout;

public class NhomQuyenPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã nhóm quyền","Tên nhóm quyền"};
	private NhomQuyenBUS nhomQuyenBUS;
	
	public NhomQuyenPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		nhomQuyenBUS = NhomQuyenBUS.getInstance();
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
		loadDataTable(nhomQuyenBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<NhomQuyenDTO> listNhomQuyen) {
		for(NhomQuyenDTO i : listNhomQuyen) {
			model.addRow(new Object[] {i.getMaRole(), i.getTenRole()});
		}
	}
}
