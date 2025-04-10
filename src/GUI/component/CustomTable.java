///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package GUI.component;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.border.EmptyBorder;

public class CustomTable extends JPanel implements ActionListener {
    protected final String[] headers;
    protected Color headerBackground = Color.LIGHT_GRAY;
    protected final Color evenRowColor = new Color(240, 240, 240);
    protected final Color oddRowColor = Color.WHITE;
    protected final Color hoverColor = new Color(220, 220, 255);
    protected final Map<Integer, List<Component>> rowLabels = new HashMap<>();
    protected int[] columnWidths;
    protected int[] rowHeights;
    protected MigLayout migLayout;
    protected ArrayList<String[]> data;
    protected int selectedRow = -1;
    protected final Color selectedColor = new Color(180, 200, 255);
    protected String[][] actions;
    protected JPanel headerPanel;
    protected JPanel dataPanel;
    protected CustomScrollPane scrollPane;
    

    public JPanel getDataPanel() {
        return dataPanel;
    }

    public CustomTable(ArrayList<String[]> data, String[][] actions, String... headers) {
        this.data = ((data == null)? new ArrayList<>(): data);
        this.actions = actions;
        this.headers = headers;
        this.columnWidths = new int[headers.length];
        this.rowHeights = new int[this.data.size() + 1]; // +1 cho header
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = 150;
        }
        for (int i = 0; i < rowHeights.length; i++) {
            rowHeights[i] = 30;
        }

        setLayout(new BorderLayout());

        // Tạo headerPanel
        headerPanel = new JPanel(new MigLayout("insets 0, fillx, gap 0"));
        for (int i = 0; i < headers.length; i++) {
            headerPanel.add(createHeaderLabel(headers[i]), "grow,cell " + i + " 0");
        }
        
        addColHanhDong();

        // Tạo dataPanel với constraints ngay từ đầu
        dataPanel = new JPanel();
        migLayout = new MigLayout("insets 0, fillx, gap 0", getColumnConstraints(), getRowConstraints());
        dataPanel.setLayout(migLayout);

        // Thêm dữ liệu ban đầu
        for (String[] x : this.data) {
            addDataRow(x);
        }

        // Đặt PreferredSize cho dataPanel
        dataPanel.setPreferredSize(new Dimension(headers.length * 150, rowLabels.size() * 30));

        // Tạo JScrollPane
        
        
        // Thêm vào CustomTable
        add(headerPanel, BorderLayout.NORTH);
        addScollPane();

        // Đặt kích thước mặc định cho CustomTable
        setMinimumSize(new Dimension(400, 200));
    }

    protected void addColHanhDong() {
        headerPanel.add(createHeaderLabel("Hành động"), "grow,cell " + headers.length + " 0");
    }

    @Override
    public void setMinimumSize(Dimension minimumSize) {
        // TODO Auto-generated method stub
        super.setMinimumSize(minimumSize);
    }

    public void addScollPane() {
        scrollPane = new CustomScrollPane(dataPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(150, 30));
        label.setOpaque(true);
        label.setBackground(headerBackground);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 15));
        return label;
    }

    public JLabel createDataLabel(String text, int row) {
        JLabel label = new JLabel(text);
        label.setBorder(new EmptyBorder(10, 5, 10, 5));
        label.setOpaque(true);
        label.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        return label;
    }

    public JPanel createActionPanel(int row) {
        JPanel label = new JPanel(new MigLayout("al center center, gap 10"));
        label.setPreferredSize(new Dimension(150, 30));
        label.setOpaque(true);
        label.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);

        CustumActionButtonTable but;
        for (String[] x : actions) {
            but = new CustumActionButtonTable(x[0], x[1], row);
            label.add(but);
            but.addActionListener(this);
        }
        return label;
    }

    
    // public void removeRow(int row) {
    //     if (!rowLabels.containsKey(row)) return;
        
    //     for (Component comp : rowLabels.get(row)) {
    //         dataPanel.remove(comp);
    //     }
    //     rowLabels.remove(row);
        
    //     Map<Integer, List<Component>> updatedRowLabels = new HashMap<>();
    //     int newRowIndex = 1;
    //     for (int key : rowLabels.keySet()) {
    //         if (key != row) {
    //             updatedRowLabels.put(newRowIndex, rowLabels.get(key));
    //             newRowIndex++;
    //         }
    //     }

    //     rowLabels.clear();
    //     rowLabels.putAll(updatedRowLabels);
    //     updateRowColors();
    //     updateRowConstraints();
    //     dataPanel.setPreferredSize(new Dimension(headers.length * 150, rowLabels.size() * 30));
    //     dataPanel.revalidate();
    //     dataPanel.repaint();
    //     repaint();
    //     revalidate();
    // }

    public void removeRow(int row) {
        if (!rowLabels.containsKey(row)) return;
    
        // Xóa các thành phần của hàng khỏi dataPanel
        // for (Component comp : rowLabels.get(row)) {
        //     dataPanel.remove(comp);
        // }
        rowLabels.remove(row);
    
        // Cập nhật lại toàn bộ các hàng trong dataPanel
        dataPanel.removeAll(); // Xóa toàn bộ để thêm lại từ đầu
        Map<Integer, List<Component>> updatedRowLabels = new HashMap<>();
        int newRowIndex = 1;
    
        // Tạo lại rowLabels với các chỉ số mới
        for (int key : rowLabels.keySet()) 
        if (key != row)
            {
            updatedRowLabels.put(newRowIndex, rowLabels.get(key));
            newRowIndex++;
        }
    
        rowLabels.clear();
        rowLabels.putAll(updatedRowLabels);
    
        // Thêm lại tất cả các thành phần vào dataPanel với constraint mới
        for (int r : rowLabels.keySet()) {
            List<Component> components = rowLabels.get(r);
            for (int col = 0; col < components.size(); col++) {
                Component comp = components.get(col);
                // Nếu là JPanel chứa các nút hành động, cập nhật giá trị row cho các nút
                if (comp instanceof JPanel && col == headers.length) {
                    JPanel actionPanel = (JPanel) comp;
                    for (Component button : actionPanel.getComponents()) {
                        if (button instanceof CustumActionButtonTable) {
                            ((CustumActionButtonTable) button).setRow(r); // Cập nhật row cho nút
                        }
                    }
                }
                dataPanel.add(comp, "grow,cell " + col + " " + (r - 1));
            }
        }
        // for (int r : rowLabels.keySet()) {
        //     List<Component> components = rowLabels.get(r);
        //     for (int col = 0; col < components.size(); col++) {
        //         dataPanel.add(components.get(col), "grow,cell " + col + " " + (r - 1));
        //     }
        // }
    
        // Điều chỉnh selectedRow nếu cần
        // if (selectedRow == row) {
        //     selectedRow = -1; // Không có hàng nào được chọn nếu hàng bị xóa là hàng được chọn
        // } else if (selectedRow > row) {
        //     selectedRow--; // Giảm chỉ số của selectedRow nếu hàng bị xóa nằm trước nó
        // }
    
        // Cập nhật màu và kích thước
        updateRowColors();
        updateRowConstraints();
        dataPanel.setPreferredSize(new Dimension(headers.length * 150, rowLabels.size() * 30));
        dataPanel.revalidate();
        dataPanel.repaint();
        repaint();
        revalidate();
    }

    public void setSelectedRow(int row) {
        if (!rowLabels.containsKey(row)) return;
        if (selectedRow != -1 && rowLabels.containsKey(selectedRow)) {
            Color previousColor = (selectedRow % 2 == 0) ? evenRowColor : oddRowColor;
            for (Component lbl : rowLabels.get(selectedRow)) {
                lbl.setBackground(previousColor);
            }
        }

        selectedRow = row;
        for (Component lbl : rowLabels.get(selectedRow)) {
            lbl.setBackground(selectedColor);
        }

        dataPanel.repaint();
        dataPanel.revalidate();
    }
    
    public void updateRowColors() {
        int rowIndex = 1;
        for (List<Component> rowComponents : rowLabels.values()) {
            Color backgroundColor = (rowIndex % 2 == 0) ? evenRowColor : oddRowColor;
            for (Component comp : rowComponents) {
                comp.setBackground(backgroundColor);
            }
            rowIndex++;
        }
    }

    public void editRow(int row) {
        if (!rowLabels.containsKey(row)) return;
        
        List<Component> rowComponents = rowLabels.get(row);
        List<Component> newComponents = new ArrayList<>();
        
        for (int i = 0; i < headers.length; i++) {
            Component oldComp = rowComponents.get(i);
            if (oldComp instanceof JLabel) {
                JLabel label = (JLabel) oldComp;
                JTextField textField = new JTextField(label.getText());
                textField.setPreferredSize(new Dimension(150, 30));
                newComponents.add(textField);
                dataPanel.remove(label); // Xóa từ dataPanel
                dataPanel.add(textField, "grow,cell " + i + " " + (row - 1)); // Thêm vào dataPanel
            } else {
                newComponents.add(oldComp);
            }
        }
        
        rowLabels.put(row, newComponents);
        dataPanel.revalidate();
        dataPanel.repaint();
    }

    public void setColumnWidth(int columnIndex, int width) {
        if (columnIndex < 0 || columnIndex >= headers.length) {
            throw new IllegalArgumentException("Column index out of bounds");
        }
        columnWidths[columnIndex] = width;
        updateColumnConstraints();
        dataPanel.setPreferredSize(new Dimension(headers.length * 150, rowLabels.size() * 30));
        dataPanel.revalidate();
        dataPanel.repaint();
    }

    public void setRowHeight(int rowIndex, int height) {
        if (rowIndex < 0) {
            throw new IllegalArgumentException("Row index must be non-negative");
        }
        if (rowIndex >= rowHeights.length) {
            int[] newRowHeights = new int[rowIndex + 1];
            System.arraycopy(rowHeights, 0, newRowHeights, 0, rowHeights.length);
            for (int i = rowHeights.length; i < newRowHeights.length; i++) {
                newRowHeights[i] = 30;
            }
            rowHeights = newRowHeights;
        }
        rowHeights[rowIndex] = height;
        updateRowConstraints();
        dataPanel.setPreferredSize(new Dimension(headers.length * 150, rowLabels.size() * 30));
        dataPanel.revalidate();
        dataPanel.repaint();
    }

    public void updateColumnConstraints() {
        migLayout.setColumnConstraints(getColumnConstraints());
    }

    public void updateRowConstraints() {
        migLayout.setRowConstraints(getRowConstraints());
    }

    private String getColumnConstraints() {
        StringBuilder columnConstraints = new StringBuilder();
        for (int width : columnWidths) {
            columnConstraints.append("[").append(width).append("]");
        }
        return columnConstraints.toString();
    }

    public String getRowConstraints() {
        StringBuilder rowConstraints = new StringBuilder();
        for (int height : rowHeights) {
            rowConstraints.append("[").append(height).append("]");
        }
        return rowConstraints.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CustumActionButtonTable but = (CustumActionButtonTable) e.getSource();
        switch (but.getId()) {
            case "edit":
                editRow(but.getRow());
                break;
            case "remove":
                removeRow(but.getRow());
                break;
            default:
            break;
        }
    }
    public void addDataRow(String[] data) {
        int row = rowLabels.size() + 1;
        // System.out.println(row);
        ArrayList<Component> labels = new ArrayList<>();
        final Color defaultColor = (row % 2 == 0 ? evenRowColor : oddRowColor);

        // / Xử lý trường hợp data là null
        String[] rowData = (data == null) ? new String[headers.length] : data;
        if (data == null) {
            for (int i = 0; i < rowData.length; i++) {
                rowData[i] = ""; // Điền giá trị rỗng cho dòng trống
            }
        }

        for (int i = 0; i < Math.min(rowData.length, headers.length); i++) {
            JLabel label = createDataLabel(rowData[i], row);
            labels.add(label);
            dataPanel.add(label, "grow,cell " + i + " " + (row - 1));
            label.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    setSelectedRow(row);
                }
            });
        }

        if (actions != null) {
            JPanel panel = createActionPanel(row);
            labels.add(panel);
            dataPanel.add(panel, "grow,cell " + headers.length + " " + (row - 1));
        }

        rowLabels.put(row, labels);

        // Cập nhật rowHeights nếu cần
        if (row >= rowHeights.length) {
            int[] newRowHeights = new int[row + 1];
            System.arraycopy(rowHeights, 0, newRowHeights, 0, rowHeights.length);
            for (int i = rowHeights.length; i < newRowHeights.length; i++) {
                newRowHeights[i] = 30;
            }
            rowHeights = newRowHeights;
        }
        updateRowConstraints();
        dataPanel.setPreferredSize(new Dimension(headers.length * 150, rowLabels.size() * 30));
        dataPanel.revalidate();
        dataPanel.repaint();
    }
}