package GUI.component.chart;

import raven.chart.bar.HorizontalBarChart;
import java.text.DecimalFormat;

public class HorizontalBarChartV2 extends HorizontalBarChart{
    
    public HorizontalBarChartV2() {
        super();
        setFormatNumber();
    }

    public void setFormatNumber() {
        valuesFormat = new DecimalFormat("#,##0");
    }

}
