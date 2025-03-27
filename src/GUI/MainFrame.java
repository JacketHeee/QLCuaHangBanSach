package GUI;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GUI.Components.ItemTaskBar;
import GUI.Components.TaskBar;
import GUI.Panel.SachPnl;
import net.miginfocom.swing.MigLayout;

public class MainFrame extends JFrame{
	private JPanel mainPnl;
	private TaskBar taskBar;
	String[] listTask = {"Sách","Thể loại","Tác giả","Nhà xuất bản","Vùng thể loại","Nhà cung cấp","Quản lý phiếu nhập","Quản lý hóa đơn","Khuyến mãi","Nhân viên","Tài khoản","Phân quyền","Phương thức thanh toán","Phân loại","Danh mục_Tác giả","Chi tiết phiếu nhập","Chi tiết hóa đơn","Khuyến mãi_Sách","Chi tiết quyền"};
	
	public MainFrame() {
		taskBar = new TaskBar(this, listTask);
		mainPnl = new JPanel();
		this.initComponent();
	}
	
	public void initComponent() {
        this.setTitle("Test");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new MigLayout("wrap 2", "[][grow]"));
        this.mainPnl.setLayout(new MigLayout("wrap 1", "[grow]"));

        this.add(taskBar);

        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);

        this.add(mainPnl, "grow");
        this.setPanel(new SachPnl());
	}
	
	public void setPanel(JPanel panel) {
		mainPnl.removeAll();
		mainPnl.add(panel, "grow");
		mainPnl.repaint();
		mainPnl.validate();
	}
}
