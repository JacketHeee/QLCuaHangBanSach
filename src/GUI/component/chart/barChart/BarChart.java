package GUI.component.chart.barChart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.JLabel;

import GUI.component.chart.barChart.blankChart.BlankPlotChart;
import GUI.component.chart.barChart.blankChart.BlankPlotChatRender;
import GUI.component.chart.barChart.blankChart.SeriesSize;

import net.miginfocom.swing.MigLayout;
import java.util.ArrayList;
import java.util.List;

public class BarChart extends JPanel {
    private List<ModelLegend> legends = new ArrayList<>();
    private List<ModelChart> model = new ArrayList<>();
    
    private JLabel headerPanel;
    private BlankPlotChart bChart;
    private JPanel panelLegend;

    private final int seriesSize = 6; 
    private final int seriesSpace = 6; 
    
    public BarChart() {
        init();
        setMinimumSize(new Dimension(200,400));
        bChart.setBlankPlotChatRender(new BlankPlotChatRender() {

            @Override
            public String getLabelText(int index) {
                return model.get(index).getLabel();
            }

            @Override
            public void renderSeries(BlankPlotChart chart, Graphics2D g2, SeriesSize size, int index) {
                double totalSeriesWidth = (seriesSize * legends.size()) + (seriesSpace * (legends.size() - 1));
                double x = (size.getWidth() - totalSeriesWidth) / 2;
                for (int i = 0; i < legends.size(); i++) {
                    ModelLegend legend = legends.get(i);
                    g2.setColor(legend.getColor());
                    double seriesValues = chart.getSeriesValuesOf(model.get(index).getValues()[i], size.getHeight());
                    g2.fillRect((int) (size.getX() + x), (int) (size.getY() + size.getHeight() - seriesValues), seriesSize, (int) seriesValues);
                    x += seriesSpace + seriesSize;
                }
            }
            
        });
    }

    private void init() {
        setLayout(new MigLayout("wrap, insets 0, gap 0","","[10%][80%][10%]"));
        headerPanel = new JLabel("Thống kê");
        headerPanel.setFont(new Font(headerPanel.getFont().getName(),Font.PLAIN,16));
        setBackground(Color.white);
        putClientProperty(FlatClientProperties.STYLE, "arc: 5");

        add(headerPanel,"al center");
        
        bChart = new BlankPlotChart();
        bChart.setOpaque(true);
        bChart.setBackground(Color.white);
        add(bChart, "push, grow");

        panelLegend = getPanelLegend();
        add(panelLegend,"pushx, grow, aligny center");
    }

    public void addData(ModelChart data) {
        model.add(data);
        bChart.setLabelCount(model.size());//set so cum can hien thị
        double max = data.getMaxValues();
        if (max>bChart.getMaxValues()) {
            bChart.setMaxValues(max); //set max value cho bchartbchart
        }
    }

    private JPanel getPanelLegend() {
        JPanel panel = new JPanel(); 
        panel.setOpaque(false);

        return panel;
    }

    public void addLegend(String name, Color color) {
        ModelLegend data = new ModelLegend(name, color); 
        legends.add(data);
        panelLegend.add(new LegendItem(data));
        panelLegend.repaint(); //yeu cau ve lai giao dien ngay lap tuc
        panelLegend.revalidate(); // cap nhat lai bo cuc cua panelLegend, dat biet thich hop khi them sua xoa
    }

    public void setHeader(String title) {
        headerPanel.setText(title);
        repaint();
        revalidate();
    }
}
