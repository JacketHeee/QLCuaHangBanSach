package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.HoaDonBUS;
import DTO.HoaDonDTO;
import net.miginfocom.swing.MigLayout;

public class HoaDonPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã hóa đơn","Ngày bán","Tổng tiền","Mã tài khoản","Mã phương thức","Mã khuyến mãi","Mã khách hàng"};
	private HoaDonBUS hoaDonBUS;
	
	public HoaDonPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		hoaDonBUS = HoaDonBUS.getInstance();
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
		loadDataTable(hoaDonBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<HoaDonDTO> listHoaDon) {
		for(HoaDonDTO i : listHoaDon) {
			model.addRow(new Object[] {i.getMaHD(), i.getNgayBan(), i.getTongTien(), i.getMaTK(), i.getMaPT(), i.getMaKM(), i.getMaKH()});
		}
	}
}
