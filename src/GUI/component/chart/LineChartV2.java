package GUI.component.chart;

import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.JLabel;

import raven.chart.line.LineChart;

public class LineChartV2 extends LineChart {
    
    public LineChartV2() {
        super();
        setFormatNumber();
    }
    public void setFormatNumber() {
        valuesFormat = new DecimalFormat("₫ #,##0.##");
    }

    @Override
    protected Component createLegend(Object data, int index) {
        return new JLabel(data.toString(),JLabel.CENTER);
    }
}