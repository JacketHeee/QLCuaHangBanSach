package GUI.component.chart.barChart;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LegendItem extends JPanel{
     
    public LegendItem(ModelLegend data) {
        init();
        setOpaque(false);
        labelColor.setBackground(data.getColor());
        lbName.setText(data.getName());
    }

    private LabelColor labelColor;
    private JLabel lbName; 

    private void init() {
        lbName= new JLabel("Name");
        lbName.setForeground(new Color(100,100,100));
        labelColor = new LabelColor();
        labelColor.setPreferredSize(new Dimension(20, 20));
        // labelColor.setOpaque(true);
        labelColor.setBackground(Color.blue);
        add(labelColor);
        add(lbName);
    }
}
