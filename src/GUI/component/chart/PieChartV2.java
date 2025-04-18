// package GUI.component.chart;

// import com.formdev.flatlaf.extras.FlatSVGIcon;
// import java.awt.BorderLayout;
// import java.awt.Component;
// import java.text.DecimalFormat;
// import java.text.NumberFormat;
// import javax.swing.JLabel;
// import javax.swing.JLayeredPane;
// import javax.swing.JPanel;
// import net.miginfocom.swing.MigLayout;
// import raven.chart.ChartColor;
// import raven.chart.component.ColorIcon;
// import raven.chart.component.PieLabelPopup;
// import raven.chart.data.pie.DefaultPieDataset;
// import raven.chart.pie.PieChart.ChartType;
// import raven.chart.simple.SimpleDataBarChart;

// public class PieChartV2 extends JPanel {
//    private NumberFormat format = new DecimalFormat("#,##0.##");
//    private ChartType chartType;
//    private int donutSize;
//    private int selectedBorderSize;
//    private DefaultPieDataset<String> dataset;
//    private int selectedIndex;
//    private ChartColor chartColor;
//    protected JLayeredPane layeredPane;
//    private PanelRender panelRender;
//    private JPanel panelHeader;
//    private JPanel panelFooter;
//    private JPanel panelLegend;
//    protected PieLabelPopup popupComponents;
//    private JLabel labelNoData;

//    public PieChartV2() {
//       this.chartType = ChartType.DEFAULT;
//       this.donutSize = -1;
//       this.selectedBorderSize = 7;
//       this.dataset = new SimpleDataBarChart();
//       this.selectedIndex = -1;
//       this.init();
//    }

//    private void init() {
//       this.chartColor = new ChartColor();
//       this.layeredPane = new JLayeredPane();
//       this.layeredPane.setLayout(new MigLayout("wrap 1,fill", "fill", "[grow 0][fill][grow 0]"));
//       this.setLayout(new BorderLayout());
//       this.add(this.layeredPane);
//       this.panelRender = new PanelRender(this);
//       this.panelHeader = new JPanel(new BorderLayout());
//       this.panelFooter = new JPanel(new BorderLayout());
//       this.panelRender.putClientProperty("FlatLaf.style", "background:null;border:10,10,10,10");
//       this.panelHeader.putClientProperty("FlatLaf.style", "background:null");
//       this.panelFooter.putClientProperty("FlatLaf.style", "background:null");
//       this.panelLegend = new JPanel(new MigLayout("fillx,wrap,al center center", "fill"));
//       this.labelNoData = new JLabel("Empty Data", new FlatSVGIcon("com/raven/chart/empty.svg"), 0);
//       this.labelNoData.setHorizontalTextPosition(0);
//       this.labelNoData.setVerticalTextPosition(3);
//       this.layeredPane.add(this.panelHeader);
//       this.layeredPane.add(this.panelRender, "width 150:250,height 150:250,split 2");
//       this.layeredPane.add(this.panelLegend);
//       this.layeredPane.add(this.panelFooter);
//       this.initPopupComponent();
//       this.updateDataset();
//    }

//    public void startAnimation() {
//       this.panelRender.animator.start();
//    }

//    public void setSelectedIndex(int selectedIndex) {
//       if (this.selectedIndex != selectedIndex) {
//          this.selectedIndex = selectedIndex;
//          if (selectedIndex >= 0 && selectedIndex < this.dataset.getItemCount()) {
//             String title = (String)this.dataset.getKey(selectedIndex);
//             String value = this.dataset.getValue(selectedIndex) + " (" + this.format.format((double)(this.getPercent(selectedIndex) * 100.0F)) + "%)";
//             this.popupComponents.setValue(title, value);
//             this.popupComponents.setVisible(true);
//          } else {
//             this.popupComponents.setVisible(false);
//          }

//          this.repaint();
//       }

//    }

//    public DefaultPieDataset<String> getDataset() {
//       return this.dataset;
//    }

//    public void setDataset(DefaultPieDataset<String> dataset) {
//       this.dataset = dataset;
//       this.updateDataset();
//    }

//    private void updateDataset() {
//       int count = this.dataset.getItemCount();
//       this.panelRender.clear();
//       this.panelLegend.removeAll();
//       if (count > 0) {
//          this.noData(false);
//          double maxValue = 0.0;

//          int i;
//          for(i = 0; i < count; ++i) {
//             maxValue += this.dataset.getValue(i).doubleValue();
//          }

//          for(i = 0; i < count; ++i) {
//             double value = this.dataset.getValue(i).doubleValue();
//             float percent = (float)(value / maxValue);
//             this.panelRender.addItem((String)this.dataset.getKey(i), value, percent);
//             this.panelLegend.add(this.createLegend(i));
//          }
//       } else {
//          this.noData(true);
//       }

//       this.panelLegend.revalidate();
//       this.repaint();
//    }

//    private float getPercent(int index) {
//       return ((PanelRender.Item)this.panelRender.items.get(index)).percent;
//    }

//    private Component createLegend(int index) {
//       JLabel label = new JLabel((String)this.dataset.getKey(index));
//       label.setIcon(new ColorIcon(this.chartColor.getColor(index)));
//       return label;
//    }

//    private void initPopupComponent() {
//       if (this.popupComponents != null) {
//          this.layeredPane.remove(this.popupComponents);
//       }

//       this.popupComponents = new PieLabelPopup();
//       this.popupComponents.setVisible(false);
//       this.layeredPane.setLayer(this.popupComponents, JLayeredPane.POPUP_LAYER);
//       this.layeredPane.add(this.popupComponents, "pos 0 0", 0);
//    }

//    private void updateImageRender() {
//       raven.chart.pie.PieChart.PanelRender.access$002(this.panelRender, false);
//       this.repaint();
//    }

//    private void noData(boolean noData) {
//       if (noData) {
//          this.layeredPane.remove(this.labelNoData);
//          this.layeredPane.add(this.labelNoData, 0);
//       } else {
//          this.layeredPane.remove(this.labelNoData);
//       }

//    }

//    public void setHeader(Component component) {
//       this.panelHeader.removeAll();
//       this.panelHeader.add(component);
//       this.panelHeader.revalidate();
//       this.panelHeader.repaint();
//    }

//    public void setFooter(Component component) {
//       this.panelFooter.removeAll();
//       this.panelFooter.add(component);
//       this.panelFooter.revalidate();
//       this.panelFooter.repaint();
//    }

//    public ChartColor getChartColor() {
//       return this.chartColor;
//    }

//    public void setChartColor(ChartColor chartColor) {
//       this.chartColor = chartColor;
//       this.updateImageRender();
//    }

//    public ChartType getChartType() {
//       return this.chartType;
//    }

//    public void setChartType(ChartType chartType) {
//       if (this.chartType != chartType) {
//          this.chartType = chartType;
//          this.updateImageRender();
//       }

//    }

//    public int getDonutSize() {
//       return this.donutSize;
//    }

//    public void setDonutSize(int donutSize) {
//       if (this.donutSize != donutSize) {
//          this.donutSize = donutSize;
//          this.updateImageRender();
//       }

//    }
// }

