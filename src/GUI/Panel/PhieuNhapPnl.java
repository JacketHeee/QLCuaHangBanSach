package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.PhieuNhapBUS;
import DTO.PhieuNhapDTO;
import net.miginfocom.swing.MigLayout;

public class PhieuNhapPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã nhập","Ngày nhập","Tổng tiền","Mã nhà cung cấp","Mã tài khoản"};
	private PhieuNhapBUS phieuNhapBUS;
	
	public PhieuNhapPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		phieuNhapBUS = PhieuNhapBUS.getInstance();
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
		loadDataTable(phieuNhapBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<PhieuNhapDTO> listPhieuNhap) {
		for(PhieuNhapDTO i : listPhieuNhap) {
			model.addRow(new Object[] {i.getMaNhap(), i.getNgayNhap(), i.getTongTien(), i.getMaNCC(), i.getMaTK()});
		}
	}
}
