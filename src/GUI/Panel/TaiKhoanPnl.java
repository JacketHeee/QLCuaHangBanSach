package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.TaiKhoanBUS;
import DTO.TaiKhoanDTO;
import net.miginfocom.swing.MigLayout;

public class TaiKhoanPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã tài khoản","Username","Password","Mã Role"};
	private TaiKhoanBUS taiKhoanBUS;
	
	public TaiKhoanPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		taiKhoanBUS = TaiKhoanBUS.getInstance();
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
		loadDataTable(taiKhoanBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<TaiKhoanDTO> listTaiKhoan) {
		for(TaiKhoanDTO i : listTaiKhoan) {
			model.addRow(new Object[] {i.getMaTK(), i.getUsername(), i.getPassword(), i.getMaRole()});
		}
	}
}
