package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.CT_PhieuNhapBUS;
import DTO.CT_PhieuNhapDTO;
import net.miginfocom.swing.MigLayout;

public class CT_PhieuNhapPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã sách","Mã nhập","Số lượng nhập", "Giá nhập"};
	private CT_PhieuNhapBUS cT_PhieuNhapBUS;
	
	public CT_PhieuNhapPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		cT_PhieuNhapBUS = CT_PhieuNhapBUS.getInstance();
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
		loadDataTable(cT_PhieuNhapBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<CT_PhieuNhapDTO> listCT_PhieuNhap) {
		for(CT_PhieuNhapDTO i : listCT_PhieuNhap) {
			model.addRow(new Object[] {i.getMaSach(), i.getMaNhap(), i.getSoLuongNhap(), i.getGiaNhap()});
		}
	}
}
