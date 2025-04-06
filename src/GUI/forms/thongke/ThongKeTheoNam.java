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

public class ThongKeTheoNam extends JPanel {
    
    public ThongKeTheoNam() {
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
        panel.add(new JLabel("Từ năm"));
        panel.add(new JTextField(10));
        panel.add(new JLabel("Đến năm"));
        panel.add(new JTextField(10));
        panel.add(new JButton("Thống kê"),"sg 1");
        panel.add(new JButton("Làm mới"),"sg 1");
        panel.add(new JButton("Xuất Excel"),"sg 1");
        
        add(panel,"pushx,grow");
    }

    private void addChart() {
        BarChart barChart = new BarChart();
        int namBatDau = 2015; 
        int namKetThuc = 2024; 
        //setHeader
        barChart.setHeader(String.format("Thống kê doanh thu từ năm %d đến năm %d", namBatDau,namKetThuc));

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
        TableNoTouch table = new TableNoTouch(datas, "Năm","Vốn","Doanh thu","Lợi nhuận");
        add(table,"push,grow");
    }
}