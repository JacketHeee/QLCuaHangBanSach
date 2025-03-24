package GUI.Panel;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
import net.miginfocom.swing.MigLayout;

public class NhanVienPnl extends JPanel{
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private String[] header = {"Mã nhân viên","Họ tên","Ngày sinh","Giới tính","Số điện thoại","Mã tài khoản"};
	private NhanVienBUS nhanVienBUS;
	
	public NhanVienPnl() {
		table = new JTable();
		model = new DefaultTableModel(header, 0);
		scrollPane = new JScrollPane();
		nhanVienBUS = NhanVienBUS.getInstance();
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
		loadDataTable(nhanVienBUS.getAll());
	}
	
	public void loadDataTable(ArrayList<NhanVienDTO> listNhanVien) {
		for(NhanVienDTO i : listNhanVien) {
			model.addRow(new Object[] {i.getMaNV(), i.getHoTen(), i.getNgaySinh(), i.getGioiTinh(), i.getSoDT(), i.getMaTK()});
		}
	}
}
