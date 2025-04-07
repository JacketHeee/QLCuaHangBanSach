package GUI.forms.thongke;

import javax.swing.*;

import GUI.component.TableNoTouch;
import GUI.component.chart.barChart.BarChart;
import GUI.component.chart.barChart.ModelChart;
import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;

import java.awt.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ThongKeTungNgay extends JPanel {
    
    public ThongKeTungNgay() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("insets 0,wrap 1, gap 10"));

        addHeader();
        addChart();
        addTable();
    }

    private void addHeader() {
        JPanel panel = new JPanel(new MigLayout("insets 10, al center center, gap 5"));
        panel.setBackground(Color.decode(baseTheme.selectedButton));
        panel.add(new JLabel("Chọn tháng "));
        panel.add(new JSpinner());
        panel.add(new JLabel("Chọn năm "));
        panel.add(new JSpinner());
        panel.add(new JButton("Thống kê"),"sg 1");
        panel.add(new JButton("Làm mới"),"sg 1");
        panel.add(new JButton("Xuất Excel"),"sg 1");
        
        add(panel,"pushx,grow");
    }

    private void addChart() {
        BarChart barChart = new BarChart();
        int namBatDau = 1; 
        int namKetThuc = 12; 
        //setHeader
        barChart.setHeader(String.format("Thống kê doanh thu từ tháng %s/2025 đến tháng %s/2025", namBatDau,namKetThuc));

        //them cot 
        barChart.addLegend("Doanh thu", new Color(245, 189, 135));
        barChart.addLegend("Vốn", new Color(135, 189, 245));
        barChart.addLegend("Lợi nhuận", new Color(189, 135, 245));

        //them du liệu 
        for (int i=namBatDau; i<=namKetThuc; i++) {
            barChart.addData(new ModelChart(i+"", new double[] {(Math.random() * 1000), (Math.random() * 1000), (Math.random() * 1000)}));
        }

        add(barChart,"pushx, growx");
    }


    ArrayList<String[]> datas = new ArrayList<>(Arrays.asList(
        new String[] {"2023-12-12", "7.000.000", "34.000.000", "30.000.000"},
        new String[] {"2023-12-13", "8.000.000", "30.000.000", "22.000.000"},
        new String[] {"2023-12-12", "7.000.000", "34.000.000", "30.000.000"},
        new String[] {"2023-12-13", "8.000.000", "30.000.000", "22.000.000"},
        new String[] {"2023-12-12", "7.000.000", "34.000.000", "30.000.000"},
        new String[] {"2023-12-13", "8.000.000", "30.000.000", "22.000.000"},
        new String[] {"2023-12-13", "8.000.000", "30.000.000", "22.000.000"},
        new String[] {"2023-12-12", "7.000.000", "34.000.000", "30.000.000"},
        new String[] {"2023-12-13", "8.000.000", "30.000.000", "22.000.000"},
        new String[] {"2023-12-13", "8.000.000", "30.000.000", "22.000.000"},
        new String[] {"2023-12-12", "7.000.000", "34.000.000", "30.000.000"},
        new String[] {"2023-12-13", "8.000.000", "30.000.000", "22.000.000"},
        new String[] {"2023-12-13", "8.000.000", "30.000.000", "22.000.000"},
        new String[] {"2023-12-12", "7.000.000", "34.000.000", "30.000.000"},
        new String[] {"2023-12-13", "8.000.000", "30.000.000", "22.000.000"},
        new String[] {"2023-12-13", "8.000.000", "30.000.000", "22.000.000"}
    ));

    
    private void addTable() {
        TableNoTouch table = new TableNoTouch(datas, "Tháng","Vốn","Doanh thu","Lợi nhuận");
        add(table,"push,grow");
    }
}