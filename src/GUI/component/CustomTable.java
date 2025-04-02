package GUI.component;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Object;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class CustomTable extends JPanel implements ActionListener {
    private final String[] headers;
    private Color headerBackground = Color.LIGHT_GRAY;
    private final Color evenRowColor = new Color(240, 240, 240);
    private final Color oddRowColor = Color.WHITE;
    private final Color hoverColor = new Color(220, 220, 255);
    private final java.util.Map<Integer, List<Component>> rowLabels = new java.util.HashMap<>();
    private int[] columnWidths; // Mảng lưu độ rộng của từng cột
    private int[] rowHeights;   // Mảng lưu chiều cao của từng hàng
    private MigLayout migLayout; // Lưu trữ MigLayout
    private ArrayList<String[]> data;
    // Biến lưu hàng đang được chọn
    private int selectedRow = -1;
    // Màu khi hàng được chọn
    // private final Color selectedColor = new Color(180, 255, 245);
    private final Color selectedColor = new Color(180, 200, 255);
    private String[][] actions;
    

    public CustomTable(ArrayList<String[]> data,String[][] actions,String...headers) {
        this.data = data;
        this.actions = actions;
        this.headers = headers;
        this.columnWidths = new int[headers.length];
        this.rowHeights = new int[1];
        // Đặt giá trị mặc định
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = 150; // Độ rộng mặc định cho cột
        }
        rowHeights[0] = 30; // Chiều cao mặc định cho header

        // Khởi tạo MigLayout
        migLayout = new MigLayout(
            "insets 0, fillx,gap 0" // Không padding, fill theo chiều ngang, bật debug
            // getColumnConstraints(), // Constraint động cho cột
            // getRowConstraints() // Constraint động cho hàng
        );
        setLayout(migLayout);

        // Thêm header
        for (int i = 0; i < headers.length; i++) {
            add(createHeaderLabel(headers[i]), "grow,cell " + i + " 0");
        }

        add(createHeaderLabel("Hành động"), "grow,cell " + headers.length + " 0");


        for (String[] x : data) 
            addDataRow(x);
        
    }

    // Phương thức để set background cho header
    public void setHeaderBackground(Color color) {
        this.headerBackground = color;
        for (Component comp : getComponents()) {
            if (comp instanceof JLabel && getComponentZOrder(comp) < headers.length) {
                comp.setBackground(headerBackground);
            }
        }
        repaint();
    }

    // Phương thức để set độ rộng cho cột
    public void setColumnWidth(int columnIndex, int width) {
        if (columnIndex < 0 || columnIndex >= headers.length) {
            throw new IllegalArgumentException("Column index out of bounds");
        }
        columnWidths[columnIndex] = width;
        updateColumnConstraints();
        revalidate();
        repaint();
    }

    // Phương thức để set chiều cao cho hàng
    public void setRowHeight(int rowIndex, int height) {
        if (rowIndex < 0) {
            throw new IllegalArgumentException("Row index must be non-negative");
        }
        if (rowIndex >= rowHeights.length) {
            int[] newRowHeights = new int[rowIndex + 1];
            System.arraycopy(rowHeights, 0, newRowHeights, 0, rowHeights.length);
            for (int i = rowHeights.length; i < newRowHeights.length; i++) {
                newRowHeights[i] = 30; // Chiều cao mặc định cho hàng mới
            }
            rowHeights = newRowHeights;
        }
        rowHeights[rowIndex] = height;
        updateRowConstraints();
        revalidate();
        repaint();
    }

    // Cập nhật constraint cho cột
    private void updateColumnConstraints() {
        migLayout.setColumnConstraints(getColumnConstraints());
    }

    // Cập nhật constraint cho hàng
    private void updateRowConstraints() {
        migLayout.setRowConstraints(getRowConstraints());
    }

    // Tạo chuỗi constraint cho cột
    private String getColumnConstraints() {
        StringBuilder columnConstraints = new StringBuilder();
        for (int width : columnWidths) {
            columnConstraints.append("[").append(width).append("]");
        }
        return columnConstraints.toString();
    }

    // Tạo chuỗi constraint cho hàng
    private String getRowConstraints() {
        StringBuilder rowConstraints = new StringBuilder();
        for (int height : rowHeights) {
            rowConstraints.append("[").append(height).append("]");
        }
        return rowConstraints.toString();
    }

    // Tạo JLabel cho header
    private JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(label.getWidth(),25));
        label.setOpaque(true);
        label.setBackground(headerBackground);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        // label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setFont(new Font(label.getFont().getName(),Font.BOLD,15));
        return label;
    }

    // Tạo JLabel cho dữ liệu
    private JLabel createDataLabel(String text, int row) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(label.getWidth(),30));
        label.setOpaque(true);
        label.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        // label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return label;
    }

    private JPanel createActionPanel(int row) {
        JPanel label = new JPanel(new MigLayout("al center center, gap 10"));
        label.setPreferredSize(new Dimension(label.getWidth(),30));
        label.setOpaque(true);
        label.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);

        CustumActionButtonTable but;
        for (String[] x : actions) {
            but = new CustumActionButtonTable(x[0], x[1],row);
            label.add(but);
            but.addActionListener(this);
        }
        // label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return label;
    }

    public void setSelectedRow(int row) {
        if (!rowLabels.containsKey(row)) return;
        if (selectedRow != -1 && rowLabels.containsKey(selectedRow)) {
            // Trả hàng cũ về màu mặc định
            Color previousColor = (selectedRow % 2 == 0) ? evenRowColor : oddRowColor;
            for (Component lbl : rowLabels.get(selectedRow)) {
                lbl.setBackground(previousColor);
            }
        }
    
        // Cập nhật hàng mới
        selectedRow = row;
        for (Component lbl : rowLabels.get(selectedRow)) {
            lbl.setBackground(selectedColor);
        }
    
        repaint();
        revalidate();
    }

    public void addDataRow(String[] data) {
        int row = rowLabels.size()+1; // Xác định số hàng hiện tại để thêm vào hàng mới
        
        java.util.ArrayList<Component> labels = new java.util.ArrayList<>();
        final Color defaultColor = (row % 2 == 0 ? evenRowColor : oddRowColor);
        
        for (int i = 0; i < Math.min(data.length, headers.length); i++) {
            JLabel label = createDataLabel(data[i], row);
            labels.add(label);
            add(label, "grow,cell " + i + " " + row);
            
            // Thêm MouseListener cho hiệu ứng hover
            label.addMouseListener(new java.awt.event.MouseAdapter() {
                // @Override
                // public void mouseEntered(java.awt.event.MouseEvent evt) {
                //     for (JLabel lbl : labels) {
                //         lbl.setBackground(hoverColor);
                //     }
                // }
                
                // @Override
                // public void mouseExited(java.awt.event.MouseEvent evt) {
                //     for (JLabel lbl : labels) {
                //         lbl.setBackground(defaultColor);
                //     }
                // }

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    setSelectedRow(row);
                }
            });
        }

        JPanel panel = createActionPanel(row);
        labels.add(panel);
        add(panel,"grow,cell " + headers.length + " " + row);
        
        rowLabels.put(row, labels);
        revalidate(); // Cập nhật layout
        repaint();
    }    


    public void removeRow(int row) {
        if (!rowLabels.containsKey(row)) return;
        
        // Xóa các thành phần giao diện của hàng
        for (Component comp : rowLabels.get(row)) {
            remove(comp);
        }
        rowLabels.remove(row);
        
        // Cập nhật lại vị trí các hàng bên dưới (nếu có)
        java.util.Map<Integer, List<Component>> updatedRowLabels = new java.util.HashMap<>();
        int newRowIndex = 1;
        for (int key : rowLabels.keySet()) {
            if (key != row) {
                updatedRowLabels.put(newRowIndex, rowLabels.get(key));
                newRowIndex++;
            }
        }

        rowLabels.clear();
        rowLabels.putAll(updatedRowLabels);
        updateRowColors();   
        revalidate();
        repaint();
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
                textField.setPreferredSize(new Dimension(textField.getWidth(), 30));
                newComponents.add(textField);
                remove(label);
                add(textField, "grow,cell " + i + " " + row);
            } else {
                newComponents.add(oldComp);
            }
        }
        
        rowLabels.put(row, newComponents);
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CustumActionButtonTable but = (CustumActionButtonTable) e.getSource();
        
        switch (but.getId()) {
            case "edit":
                editRow(but.getRow());
                break;
            case "detail":
                
                break;
            case "remove":
                removeRow(but.getRow());
                break;
        
            default:
                break;
        }
    }

    
}