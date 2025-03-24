package GUI.Panel;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.SachBUS;
import DTO.SachDTO;
import net.miginfocom.swing.MigLayout;

public class SachPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã sách","Tên sách","Giá bán","Số lượng tồn","Năm XB"};
	private SachBUS sachBUS;
	
	public SachPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		sachBUS = SachBUS.getInstance();
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
		loadDataTable(sachBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<SachDTO> listSach) {
		for(SachDTO i : listSach) {
			model.addRow(new Object[] {i.getMaSach(), i.getTenSach(), i.getGiaBan(), i.getSoLuongTon(), i.getNamXB()});
		}
	}
}
