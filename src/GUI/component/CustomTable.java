package GUI.component;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import resources.base.baseTheme;


public class CustomTable extends JTable {

    private String[][] data;
    private String[] cols;
    private DefaultTableCellRenderer center;

    // public CustomTable(String[][] data,String... cols) {
    //     this.data = data;
    //     this.cols = cols;
    //     DefaultTableModel model = new DefaultTableModel(data,cols);
        
    //     for (String[] x : data) 
    //     model.addRow(x);

    //     super(model);

    //     center = new DefaultTableCellRenderer();
    //     center.setHorizontalAlignment(JLabel.CENTER);
    //     init();
    // }

    public CustomTable(String[][] data, String... cols) {
        this.data = data;
        this.cols = cols;
        DefaultTableModel model = new DefaultTableModel(data, cols);
        setModel(model); // Gán model trực tiếp, không cần addRow lại
    
        center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        init();
    }

    private void init() {
        //custom header
        getTableHeader().setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD,14));
        getTableHeader().setBackground(Color.decode(baseTheme.selectedButton));
        getTableHeader().setForeground(Color.decode(baseTheme.textColor));
        getTableHeader().setPreferredSize(new Dimension(getTableHeader().getWidth(),30));

        //
        // putClientProperty(FlatClientProperties.STYLE, "focusCellHighlightBorder: 0;showHorizontalLines: false; showVerticalLines:false");

        setFocusable(false);
        setShowGrid(false);
        setBorder(null);
        setSelectionBackground(Color.decode(baseTheme.mainColor));
        setSelectionForeground(Color.decode(baseTheme.txtOnMainColor));

        setRowHeight(35);
        // setIntercellSpacing(new Dimension(0,4));

        // Set màu xen kẽ bằng renderer
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                        boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 14)); // Font cho ô

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(Color.decode(baseTheme.backgroundColor)); // Dòng chẵn
                    } else {
                        c.setBackground(Color.decode(baseTheme.selectedButton)); // Dòng lẻ
                    }
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(Color.decode(baseTheme.selectedButton));
                    c.setForeground(Color.decode(baseTheme.textColor));
                }

                // Căn giữa (kế thừa từ center)
                ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        }); 

        // for (int i=0; i<cols.length; i++)
        //     setAlignCenterCol(i);

    
        
    }

    public void setWidthCol(int index, int width) {
        this.getColumnModel().getColumn(index).setPreferredWidth(width);
    }

    public void setAlignCenterCol(int index) {
        getColumnModel().getColumn(index).setCellRenderer(center);
    }
  
}
