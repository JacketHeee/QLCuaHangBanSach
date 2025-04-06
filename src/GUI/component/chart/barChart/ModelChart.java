package GUI.component.chart.barChart;

public class ModelChart {
    private String label; 
    private double values[];

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    public ModelChart() {

    }

    public ModelChart(String label, double[] values) {
        this.label = label;
        this.values = values;
    }

    public double getMaxValues() {
        double max = 0 ;
        for (double x : values) 
            if (x>max) 
                max = x; 
        return max;
    }
    
}
