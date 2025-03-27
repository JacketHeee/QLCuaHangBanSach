package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.KhuyenMaiBUS;
import DTO.KhuyenMaiDTO;
import net.miginfocom.swing.MigLayout;

public class KhuyenMaiPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã khuyến mãi","Tên khuyến mãi","Điều kiện giảm","Giá trị giảm","Ngày bắt đầu","Ngày kết thúc"};
	private KhuyenMaiBUS khuyenMaiBUS;
	
	public KhuyenMaiPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		khuyenMaiBUS = KhuyenMaiBUS.getInstance();
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
		loadDataTable(khuyenMaiBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<KhuyenMaiDTO> listKhuyenMai) {
		for(KhuyenMaiDTO i : listKhuyenMai) {
			model.addRow(new Object[] {i.getMaKM(), i.getTenKM(), i.getDieuKienGiam(), i.getGiaTriGiam(), i.getNgayBatDau(), i.getNgayKetThuc()});
		}
	}
}
