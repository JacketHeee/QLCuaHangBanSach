package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.CT_HoaDonBUS;
import DTO.CT_HoaDonDTO;
import net.miginfocom.swing.MigLayout;

public class CT_HoaDonPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã sách","Mã hóa đơn","Số lượng","Giá bán"};
	private CT_HoaDonBUS cT_HoaDonBUS;
	
	public CT_HoaDonPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		cT_HoaDonBUS = CT_HoaDonBUS.getInstance();
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
		loadDataTable(cT_HoaDonBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<CT_HoaDonDTO> listCT_HoaDon) {
		for(CT_HoaDonDTO i : listCT_HoaDon) {
			model.addRow(new Object[] {i.getMaSach(), i.getMaHD(), i.getSoLuong(), i.getGiaBan()});
		}
	}
}
