package GUI.forms.thongke;

import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import GUI.component.TableNoTouch;
import GUI.component.chart.HorizontalBarChartV2;
import GUI.component.chart.LineChartV2;
import GUI.component.chart.CurveChart.CurveChart;
import GUI.component.chart.CurveChart.ModelChart2;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import raven.chart.bar.HorizontalBarChart;
import raven.chart.data.category.DefaultCategoryDataset;
import raven.chart.data.pie.DefaultPieDataset;
import raven.chart.line.LineChart;
import raven.chart.pie.PieChart;

import java.util.Arrays;

import java.util.ArrayList;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color; 
import resources.base.baseTheme;
import GUI.component.CustomBoldJLabel;
import GUI.component.LabelInfor;

public class TongQuan extends JPanel{
    JPanel headPanel;
    private LineChartV2 lineChart;
    public TongQuan() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("insets 0,gap 20"));
        headPanel = new JPanel(new MigLayout("gap 20,insets 0 10 0 10"));
        getLabel();

        add(headPanel,"pushx,growx,wrap,gaptop 20");
        add(panelChart(),"pushx,growx,wrap");
        add(chartArea(),"pushx,growx");
    }

    String[][] data = {
        {"Doanh thu hôm nay","doanhthu.svg","100",baseTheme.red},
        {"Số hóa đơn hôm nay","hoadon.svg","98",baseTheme.yellow},
        {"Tổng nhập hàng hôm nay","supplierLabel.svg","100",baseTheme.green},
        {"Số sách tồn thấp (<5)","bookLabel.svg","100",baseTheme.bubble}
    };
    
    private void getLabel() {
        for (String[] x : data) 
            headPanel.add(new LabelInfor(x[0], x[1], x[2], x[3]),"pushx,growx");
    }

    private JPanel panelChart() {
        JPanel panel = new JPanel(new MigLayout("insets 10 0 10 0, wrap 1"));
        panel.setBackground(Color.white);
        panel.putClientProperty("arc", 10);

        panel.add(new CustomBoldJLabel("Thống kê doanh thu 7 ngày gần nhất",0),"al center");
        panel.add(curveChart(),"pushx,growx");
        return panel;
    }

    private CurveChart chart;
    private CurveChart curveChart() {
        chart = new CurveChart();
        // chart.addLegend("Doanh thu",Color.decode(baseTheme.mainColor), Color.decode(baseTheme.green));
        chart.addLegend("Doanh thu",new Color(54, 4, 143), new Color(104, 49, 200));
        // chart.setOpaque(false);
        loadDataChart();
        return chart;
    }

    int[] values = {100,500,100,20,900,150,400};

    private void loadDataChart() {
        LocalDate today = LocalDate.now();
        LocalDate startDay = today.minusDays(6);
        Random rand = new Random();
        int i = 0;

        for (LocalDate date = startDay; !date.isAfter(today); date = date.plusDays(1)) {
            chart.addData(new ModelChart2(date.toString(), new double[] {values[i]}));
            i++;
        }

        chart.start();
    }

    private JPanel panelHorizonetalBarchart() {
        HorizontalBarChartV2 barChart1 = new HorizontalBarChartV2();
        
        // JLabel header1 = new JLabel("Top 5 sách bán chạy trong 7 ngày gần nhất");
        JLabel header1 = new CustomBoldJLabel("Top 5 sách bán chạy trong 7 ngày gần nhất",0);
        
        header1.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:+1;"
                + "border:0,0,5,0");
        barChart1.setHeader(header1);
        barChart1.setBarColor(Color.decode(baseTheme.green));
        barChart1.setDataset(createData());
        
        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        panel1.add(barChart1);
        // panel1.setBackground(Color.white);
        return panel1;
    }

    private DefaultPieDataset createData() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        Random random = new Random();
        dataset.addValue("Từ chối giá như", random.nextInt(100));
        dataset.addValue("Hãy tôn trọng bản thân", random.nextInt(100));
        dataset.addValue("Tạo hóa quên neft Hùng Mạnh", random.nextInt(100));
        dataset.addValue("Hãy biến thành công thành thói quen", random.nextInt(100));
        dataset.addValue("Cột sống vàng", random.nextInt(100));
        return dataset;
    }


    private PieChart pieChart() {
        PieChart pieChart1 = new PieChart();
        pieChart1.setChartType(PieChart.ChartType.DEFAULT);
        // pieChart1.setBackground(Color.white);
        pieChart1.setOpaque(false);
        JLabel header1 = new JLabel("<html><font><b>% Hóa đơn sử dụng khuyến mãi hôm nay</b></font></html>",JLabel.CENTER);
        header1.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:+1");
        pieChart1.setHeader(header1);
        pieChart1.getChartColor().addColor(Color.decode(baseTheme.yellow), Color.decode(baseTheme.yellowGreen), Color.decode("#22d3ee"), Color.decode("#818cf8"), Color.decode("#c084fc"));
        pieChart1.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        pieChart1.setDataset(createPieData());
        // add(pieChart1, "split 3,height 2
        return pieChart1;
    }

    private DefaultPieDataset createPieData() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.addValue("Không sử dụng", (int)(Math.random() * 1000));
        dataset.addValue("Sử dụng khuyến mãi", (int)(Math.random() * 1000));
        return dataset;
    }

    private JPanel chartArea() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        panel.add(panelHorizonetalBarchart(),"pushx, grow, hmin 250, sg 1");
        panel.add(pieChart(),"hmin 250");
        return panel;
    }

}
