package GUI.forms.thongke;

import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import GUI.component.TableNoTouch;
import GUI.component.chart.LineChartV2;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import raven.chart.data.category.DefaultCategoryDataset;
import raven.chart.line.LineChart;
import java.util.Arrays;

import java.util.ArrayList;

import java.awt.Dimension; 
import java.awt.Color; 
import resources.base.baseTheme;
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
        createLineChart();
        getTableDoanhThu();
    }

    String[][] data = {
        {"Cuốn sách hiện có","bookLabel.svg","100",baseTheme.bookLabel},
        {"Nhà cung cấp","supplierLabel.svg","98",baseTheme.supplierLabel},
        {"Khách hàng","customerLabel.svg","100",baseTheme.customerLabel},
        {"Nhân viên","staffLabel.svg","100",baseTheme.staffLabel}
    };
    
    private void getLabel() {
        for (String[] x : data) 
            headPanel.add(new LabelInfor(x[0], x[1], x[2], x[3]),"pushx,growx");
    }

    private void createLineChart() {
        lineChart = new LineChartV2();
        lineChart.setBackground(Color.white);
        lineChart.setChartType(LineChart.ChartType.CURVE);
        lineChart.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        lineChart.setMinimumSize(new Dimension(200,300));
        add(lineChart,"pushx,grow,wrap");
        createLineChartData();
    }

    int[][] value = {
        {100000000,1000000,10002300},
        {100000000,1000000,100000123},
        {23846729,24234,12000},
        {200000000,1500000,10003210},
        {50340000,100000,1000123},
        {124300000,434000,123001230},
        {100000000,1000000,100023100}
    };

    private void createLineChartData() {
        DefaultCategoryDataset<String, String> categoryDataset = new DefaultCategoryDataset<>();
        Calendar cal = Calendar.getInstance(); //lấy ngày giờ hiện tại
        cal.add(Calendar.DATE, -7);  //thống kê doanh thu 7 ngày gần nhất
       
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //"dd/MM/yyyy"
        

        // addValue(value, rowKey, columnKey)
        for (int i = 0; i < value.length; i++) {
            String date = df.format(cal.getTime());
            categoryDataset.addValue(value[i][0], "Doanh thu", date);
            categoryDataset.addValue(value[i][1], "Chi phí", date);
            categoryDataset.addValue(value[i][2], "Lợi nhuận", date);

            cal.add(Calendar.DATE, 1);
        }

        lineChart.setCategoryDataset(categoryDataset);
        lineChart.getChartColor().addColor(Color.decode("#38bdf8"), Color.decode("#fb7185"), Color.decode("#34d399"));
        JLabel header = new JLabel("<html><b><font>Thống kê doanh thu 7 ngày gần nhất</font></b></html>");
        lineChart.setHeader(header);
    }

    ArrayList<String[]> datas = new ArrayList<>(Arrays.asList(
        new String[] {"2023-12-12", "7.000.000", "34.000.000", "30.000.000"},
        new String[] {"2023-12-13", "8.000.000", "30.000.000", "22.000.000"},
        new String[] {"2023-12-12", "7.000.000", "34.000.000", "30.000.000"},
        new String[] {"2023-12-13", "8.000.000", "30.000.000", "22.000.000"},
        new String[] {"2023-12-12", "7.000.000", "34.000.000", "30.000.000"},
        new String[] {"2023-12-13", "8.000.000", "30.000.000", "22.000.000"},
        new String[] {"2023-12-13", "8.000.000", "30.000.000", "22.000.000"}
    ));

    
    private void getTableDoanhThu() {
        TableNoTouch table = new TableNoTouch(datas, "Ngày","Vốn","Doanh thu","Lợi nhuận");
        add(table,"push,grow");
    }
}
