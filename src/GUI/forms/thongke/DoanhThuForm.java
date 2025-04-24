package GUI.forms.thongke;

import javax.swing.*;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.ThongKeBUS.ThongKeDoanhThuBUS;
import DTO.HoaDonDTO;
import DTO.SachDTO;
import DTO.ThongKe.ThongKeDoanhThuDTO;
import GUI.component.CustomBoldJLabel;
import GUI.component.CustomTable;
import GUI.component.LabelInfor;
import GUI.component.LabelTongQuan;
import GUI.component.TableNoTouch;
import GUI.component.chart.HorizontalBarChartV2;
import net.miginfocom.swing.MigLayout;
import raven.chart.data.pie.DefaultPieDataset;
import raven.chart.pie.PieChart;
import resources.base.baseTheme;
import utils.FormatterUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Date;

public class DoanhThuForm extends JPanel implements ActionListener {
    JComboBox<String> comboBox;
    String[] choose = {"Sách","Khách hàng"};
    private ThongKeDoanhThuBUS doanhThuBUS;
    private Date startDate; 
    private Date endDate;
    private JPanel panelTable;
    private String[] headerSach = {"Mã sách","Tên sách","Số lượng bán","Tổng tiền"}; 
    private String[] headerKhachHang = {"Mã KH","Tên khách hàng","Số hóa đơn","Tổng tiền"};
    private CustomTable tableKhachHang;
    private CustomTable tableSach;

    private CustomTable table; 
    private PieChart pieChartt;
    private int[] dataPieChart;
    private CustomTable tableHoaDon;
    private List<ThongKeDoanhThuDTO> listSach;
    private List<ThongKeDoanhThuDTO> listKH;
    private String[] tongHD_TongDT;

    private List<ThongKeDoanhThuDTO> listTop5Sach;
    private List<ThongKeDoanhThuDTO> lisTop5Kh;

    public DoanhThuForm() {
        doanhThuBUS = new ThongKeDoanhThuBUS();
        loadData();
        init();
    }

    private void init() {
        setLayout(new MigLayout("insets 0, gap 0,wrap 1"));
        add(getHeader(),"pushx,growx, hmin 60");
        add(getMainContent(),"push,grow, gaptop 10");
    }

    private void loadData() {
        Calendar cal = Calendar.getInstance();

        // startDate: 01/05/2025
        cal.set(2025, Calendar.APRIL, 1);
        startDate = cal.getTime();

        // endDate: 01/01/2027 (vì 24 tháng sau tháng 4/2025 là tháng 4/2027)
        cal.set(2027, Calendar.APRIL, 1);
        endDate = cal.getTime();
            
        listSach = doanhThuBUS.getRevenueStats(startDate,endDate,"book");
        listKH = doanhThuBUS.getRevenueStats(startDate,endDate,"customer");
        tongHD_TongDT = doanhThuBUS.getTotalRevenue(startDate,endDate);

        listTop5Sach = doanhThuBUS.getTop5Books(startDate, endDate);
        lisTop5Kh = doanhThuBUS.getTop5Kh(startDate, endDate);
    }

    private JPanel getHeader() {
        JPanel panel = new JPanel(new MigLayout("insets 10 30 10 30, gap 5,al center center"));
        panel.setBackground(Color.decode(baseTheme.selectedButton));
        panel.add(new JLabel("Thống kê theo")); 
        comboBox = new JComboBox<>(choose);
        comboBox.addActionListener(this);
        panel.add(comboBox);
        panel.add(new JLabel(),"pushx"); 
        panel.add(new JLabel("Từ ngày"));
        panel.add(new JTextField(20));
        panel.add(new JLabel("Đến"));
        panel.add(new JTextField(20));
        panel.add(new JButton("Lọc"));
        return panel;
    }


    private JPanel getMainContent() {
        JPanel panel = new JPanel(new MigLayout("insets 0, gap 10"));
        panelTable = getPanelTable();
        panel.add(panelTable,"push,grow");
        panel.add(getPanelTongQuan(),"pushy,growy");
        return panel;
    }

    

    private JPanel getPanelTable() {
        JPanel panel = new JPanel(new MigLayout("insets 0, gap 5,wrap 1"));
        panel.add(new JLabel("<html><font size='+1'><b>Tình hình kinh doanh</b></font></html>"));

        // comboBox.setSelectedItem("Sách");
        // String select = (String)comboBox.getSelectedItem();
        // String[] headerTable;

        // Tạo dữ liệu cho tableSach
        
        // Tạo dữ liệu cho tableKhachHang
        ArrayList<String[]> dataSach = new ArrayList<>();
        for (ThongKeDoanhThuDTO x : listSach) {
            dataSach.add(new String[]{x.getId()+"",x.getName(),x.getSoluong()+"",FormatterUtil.formatNumberVN(x.getDoanhthu())+""});
        }

        ArrayList<String[]> dataKhachHang = new ArrayList<>();

        for (ThongKeDoanhThuDTO x : listKH) {
            dataKhachHang.add(new String[]{x.getId()+"",x.getName(),x.getSoluong()+"",FormatterUtil.formatNumberVN(x.getDoanhthu())+""});
        }

        tableSach = new TableNoTouch(dataSach,new CustomTable.OnSelectRowListener() {
            @Override
            public void OnSelectRow(int row) {
                System.out.println("hang " + row);
                loadTable(row);
            }
        }, headerSach);
        tableSach.setMaxTextWidth(80);

        tableKhachHang = new TableNoTouch(dataKhachHang,new CustomTable.OnSelectRowListener() {
            @Override
            public void OnSelectRow(int row) {
                System.out.println("khach " +  row);
                loadTable(row);
            }
        }, headerKhachHang);
        tableKhachHang.setMaxTextWidth(80);

       
       // Chọn bảng dựa trên comboBox
        String select = comboBox.getSelectedItem() != null ? (String) comboBox.getSelectedItem() : "Sách";
        table = select.equals("Khách hàng") ? tableKhachHang : tableSach;
        

        panel.add(table,"push,grow");
        
        return panel;
    }

    private JPanel getPanelTongQuan() {
        JPanel panel = new JPanel(new MigLayout("insets 0, gap 10,wrap 1"));
        panel.setPreferredSize(new Dimension(300,500));
        panel.add(getTongQuan(),"pushx,growx");
        panel.add(getListHoaDon(),"push,grow");
        return panel;
    }

    
    private JPanel getTongQuan() {
        JPanel panel = new JPanel(new MigLayout("insets 0, gap 10,wrap 1"));
        
        JPanel panelLabel = new JPanel(new MigLayout("insets 0, gap 10, al center center"));

        panelLabel.add(new LabelTongQuan("Tổng số hóa đơn","hoadon.svg",tongHD_TongDT[0],baseTheme.soLuong),"pushx,growx,sg 1");
        panelLabel.add(new LabelTongQuan("Doanh thu (đ)","doanhthu.svg",tongHD_TongDT[1],baseTheme.tongTien),"pushx, growx,sg 1");

        panel.add(panelLabel,"pushx,growx");

        pieChartt = pieChart();
        panel.add(pieChartt,"hmin 290,pushx,growx");
        return panel;
    }

    private PieChart pieChart() {
        PieChart pieChart1 = new PieChart();
        pieChart1.setChartType(PieChart.ChartType.DONUT_CHART);
        // pieChart1.setBackground(Color.white);
        pieChart1.setOpaque(false);

        JLabel header1;

        header1 = new JLabel("<html><font><b>Top 5 sách bán nhiều nhất</b></font></html>",JLabel.CENTER);

        header1.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:+1");
        pieChart1.setHeader(header1);
        pieChart1.getChartColor().addColor(Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"), Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"), Color.decode("#818cf8"), Color.decode("#c084fc"));
        pieChart1.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        pieChart1.setDataset(createPieData(listTop5Sach));
        // add(pieChart1, "split 3,height 2
        return pieChart1;
    }

    

    private DefaultPieDataset createPieData(List<ThongKeDoanhThuDTO> list) {

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        for (ThongKeDoanhThuDTO x : list) {
            dataset.addValue(x.getName(), x.getSoluong());
        }


        return dataset;
    }


    
    private JPanel getListHoaDon() {
        JPanel panel = new JPanel(new MigLayout("insets 0, gap 10,wrap 1"));
        // panel.setOpaque(true);
        // panel.setBackground(Color.white);
        panel.add(new CustomBoldJLabel("Hóa đơn tương ứng", 1),"pushx, al center"); 
        tableHoaDon = new CustomTable(null, new String[][] {{"detail.svg","detail"}},"Mã hóa đơn","Tổng tiền");
        tableHoaDon.setMaxTextWidth(25);
        
        panel.add(tableHoaDon,"push,grow");
        return panel;
    }


    private void updatePanelTable(CustomTable newTable) {
        // Xóa bảng cũ khỏi panelTable
        panelTable.removeAll();
        // Thêm tiêu đề
        panelTable.add(new JLabel("<html><font size='+1'><b>Tình hình kinh doanh</b></font></html>"));
        // Thêm bảng mới
        table = newTable;
        panelTable.add(table, "push,grow");
        // Cập nhật giao diện
        JLabel header1;
        if (((String)comboBox.getSelectedItem()).equals("Khách hàng")) {
            header1 = new JLabel("<html><font><b>Top 5 sách bán nhiều nhất</b></font></html>",JLabel.CENTER);
            header1.putClientProperty(FlatClientProperties.STYLE, ""
                    + "font:+1");
            pieChartt.setHeader(header1);
            pieChartt.setDataset(createPieData(lisTop5Kh));
        }
        else {
            header1 = new JLabel("<html><font><b>Top 5 khách hàng mua nhiều nhất</b></font></html>",JLabel.CENTER);
            header1.putClientProperty(FlatClientProperties.STYLE, ""
                    + "font:+1");
            pieChartt.setHeader(header1);
            pieChartt.setDataset(createPieData(listTop5Sach));
        }
        
        panelTable.revalidate();
        panelTable.repaint();
    }

    public void loadTable(int row){
        System.out.println("con cho biet o ne");
        int ma = getIdByRowIndex(row);
        ArrayList<String[]> dataUpdate = new ArrayList<>();
        if (((String)comboBox.getSelectedItem()).equals("Khách hàng")) {
            System.out.println("Khách hàng" + ma);
            for (HoaDonDTO x : doanhThuBUS.getInvoices(ma+"", startDate, endDate, "customer")) {
                dataUpdate.add(new String[] {x.getMaHD()+"",FormatterUtil.formatNumberVN(x.getTongTien())+""});
            };
        }
        else {
            System.out.println("Sách" + ma);
            for (HoaDonDTO x : doanhThuBUS.getInvoices(ma+"", startDate, endDate, "book")) {
                dataUpdate.add(new String[] {x.getMaHD()+"",FormatterUtil.formatNumberVN(x.getTongTien())+""});
            };
        }

        tableHoaDon.updateTable(dataUpdate);
    }

    public int getIdByRowIndex(int row){
        // JLabel label = (JLabel)this.table.getRowLabels().get(row).get(0);
        // int ma = Integer.parseInt(label.getText());
        // return(ma);
        try {
            List<Component> rowComponents = table.getRowLabels().get(row);
            if (rowComponents == null || rowComponents.isEmpty()) {
                System.out.println("Hàng " + row + " không tồn tại trong rowLabels");
                return -1; // Hoặc giá trị mặc định khác
            }
            Component component = rowComponents.get(0);
            if (!(component instanceof JLabel)) {
                System.out.println("Thành phần tại hàng " + row + ", cột 0 không phải JLabel");
                return -1;
            }
            JLabel label = (JLabel) component;
            String text = label.getText();
            if (text == null || text.trim().isEmpty()) {
                System.out.println("Giá trị tại hàng " + row + ", cột 0 rỗng");
                return -1;
            }
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            System.out.println("Giá trị tại hàng " + row + ", cột 0 không phải số nguyên: " + e.getMessage());
            return -1;
        }
    }
    


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        JComboBox combo = (JComboBox) e.getSource();

        switch ((String) combo.getSelectedItem()) {
            case "Khách hàng":
                updatePanelTable(tableKhachHang);
                break;
            case "Sách":
                updatePanelTable(tableSach);
                break;
            default:
                break;
        }
        
    }
    
}