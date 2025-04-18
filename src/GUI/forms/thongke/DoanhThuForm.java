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
        panelLabel.add(new LabelTongQuan("Tổng số hóa đơn","hoadon.svg","100.000",baseTheme.hoadon),"pushx,growx,sg 1");
        panelLabel.add(new LabelTongQuan("Doanh thu (đ)","doanhthu.svg","100.000.000",baseTheme.tongTien),"pushx, growx,sg 1");

        panel.add(panelLabel,"pushx,growx");

        HorizontalBarChartV2 top = new HorizontalBarChartV2();
        top.setBackground(Color.white);
        top.setHeader(new JLabel("<html><b>Top 5 sách bán chạy nhất</b><html>", JLabel.CENTER));
        top.setDataset(createDataHorizonetalBarchart());//add data
        top.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");

        panel.add(top,"hmin 250,pushx,growx");
        return panel;
    }

    private DefaultPieDataset createDataHorizonetalBarchart() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        Random random = new Random();
        dataset.addValue("July (ongoing)", random.nextInt(100));
        dataset.addValue("June", random.nextInt(100));
        dataset.addValue("May", random.nextInt(100));
        dataset.addValue("April", random.nextInt(100));
        dataset.addValue("March", random.nextInt(100));
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