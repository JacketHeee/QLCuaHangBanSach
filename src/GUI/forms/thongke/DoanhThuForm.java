package GUI.forms.thongke;

import javax.swing.*;

import com.formdev.flatlaf.FlatClientProperties;

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

import java.awt.*;
import java.util.Random;

public class DoanhThuForm extends JPanel {
    JComboBox<String> comboBox;
    String[] choose = {"Khách hàng","Sách"};

    public DoanhThuForm() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("insets 0, gap 0,wrap 1"));
        add(getHeader(),"pushx,growx, hmin 60");
        add(getMainContent(),"push,grow, gaptop 10");
    }

    private JPanel getHeader() {
        JPanel panel = new JPanel(new MigLayout("insets 10 30 10 30, gap 5,al center center"));
        panel.setBackground(Color.decode(baseTheme.selectedButton));
        panel.add(new JLabel("Thống kê theo")); 
        comboBox = new JComboBox<>(choose);
        panel.add(comboBox);
        panel.add(new JLabel(),"pushx"); 
        panel.add(new JLabel("Từ ngày"));
        panel.add(new JTextField(20));
        panel.add(new JLabel("Đến"));
        panel.add(new JTextField(20));
        return panel;
    }

    private JPanel getMainContent() {
        JPanel panel = new JPanel(new MigLayout("insets 0, gap 10"));
        panel.add(getPanelTable(),"push,grow");
        panel.add(getPanelTongQuan(),"pushy,growy");
        return panel;
    }

    private String[] headerSach = {"Mã sách","Tên sách","Số hóa đơn","Tổng tiền"}; 
    private String[] headerKhachHang = {"Mã KH","Tên khách hàng","Số hóa đơn","Tổng tiền"};

    private JPanel getPanelTable() {
        JPanel panel = new JPanel(new MigLayout("insets 0, gap 5,wrap 1"));
        panel.add(new JLabel("<html><font size='+1'><b>Tình hình kinh doanh</b></font></html>"));

        comboBox.setSelectedItem("Sách");
        String select = (String)comboBox.getSelectedItem();
        // String[] headerTable;
        TableNoTouch table;
        if (select.equals("Khách hàng")) {
            table = new TableNoTouch(null, headerKhachHang);
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
        }
        else {
            table = new TableNoTouch(null, headerSach);
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});
            table.addDataRow(new String[] {"1","Con bo biet bay","30","900.000.000"});

        }
        

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
        panelLabel.add(new LabelTongQuan("Tổng số hóa đơn","hoadon.svg","100000",baseTheme.soLuong),"pushx,growx,sg 1");
        panelLabel.add(new LabelTongQuan("Doanh thu (đ)","doanhthu.svg","100.000.000",baseTheme.tongTien),"pushx, growx,sg 1");

        panel.add(panelLabel,"pushx,growx");

        panel.add(pieChart(),"hmin 290,pushx,growx");
        return panel;
    }

    private PieChart pieChart() {
        PieChart pieChart1 = new PieChart();
        pieChart1.setChartType(PieChart.ChartType.DONUT_CHART);
        // pieChart1.setBackground(Color.white);
        pieChart1.setOpaque(false);
        JLabel header1 = new JLabel("<html><font><b>Top 5 sách nhập nhiều nhất</b></font></html>",JLabel.CENTER);
        header1.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:+1");
        pieChart1.setHeader(header1);
        pieChart1.getChartColor().addColor(Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"), Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"), Color.decode("#818cf8"), Color.decode("#c084fc"));
        pieChart1.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        pieChart1.setDataset(createPieData());
        // add(pieChart1, "split 3,height 2
        return pieChart1;
    }

    private DefaultPieDataset createPieData() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.addValue("Hãy cứ xông pha", (int)(Math.random() * 1000));
        dataset.addValue("Tạo hóa quên neft Hùng Mạnh", (int)(Math.random() * 1000));
        dataset.addValue("Ai chẳng có ước mơ", (int)(Math.random() * 1000));
        dataset.addValue("Từ chối giá như", (int)(Math.random() * 1000));
        dataset.addValue("Con sói đầu đàn", (int)(Math.random() * 1000));
        dataset.addValue("Các sách khác", 30);
        return dataset;
    }


    private JPanel getListHoaDon() {
        JPanel panel = new JPanel(new MigLayout("insets 0, gap 10,wrap 1"));
        // panel.setOpaque(true);
        // panel.setBackground(Color.white);
        panel.add(new CustomBoldJLabel("Hóa đơn tương ứng", 1),"pushx, al center"); 
        CustomTable table = new CustomTable(null, new String[][] {{"detail.svg","detail"}},"Mã hóa đơn","Tổng tiền");
        table.addDataRow(new String[] {"123","1239827"});
        table.addDataRow(new String[] {"123","1239827"});
        table.addDataRow(new String[] {"123","1239827"});
        table.addDataRow(new String[] {"123","1239827"});
        table.addDataRow(new String[] {"123","1239827"});
        table.addDataRow(new String[] {"123","1239827"});
        table.addDataRow(new String[] {"123","1239827"});
        table.addDataRow(new String[] {"123","1239827"});
        table.addDataRow(new String[] {"123","1239827"});
        table.addDataRow(new String[] {"123","1239827"});
        table.addDataRow(new String[] {"123","1239827"});
        table.addDataRow(new String[] {"123","1239827"});
        table.addDataRow(new String[] {"123","1239827"});
        panel.add(table,"push,grow");
        return panel;
    }
    
}