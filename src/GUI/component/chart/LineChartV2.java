package GUI.component.chart;

import raven.chart.line.LineChart;
import java.text.DecimalFormat;

import javax.swing.JLabel;

import java.awt.Component;

import net.miginfocom.swing.MigLayout;

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