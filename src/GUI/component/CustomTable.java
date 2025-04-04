// package GUI.component;

// import net.miginfocom.swing.MigLayout;
// import javax.swing.*;

// import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;
// import com.mysql.cj.x.protobuf.MysqlxDatatypes.Object;

// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Collections;
// import javax.swing.border.EmptyBorder;
// import java.util.Map;
// import java.util.HashMap;

// public class CustomTable extends JPanel implements ActionListener {
//     // private final String[] headers;
//     // private Color headerBackground = Color.LIGHT_GRAY;
//     // private final Color evenRowColor = new Color(240, 240, 240);
//     // private final Color oddRowColor = Color.WHITE;
//     // private final Color hoverColor = new Color(220, 220, 255);
//     // private final java.util.Map<Integer, List<Component>> rowLabels = new java.util.HashMap<>();
//     // private int[] columnWidths; // Mảng lưu độ rộng của từng cột
//     // private int[] rowHeights;   // Mảng lưu chiều cao của từng hàng
//     // private MigLayout migLayout; // Lưu trữ MigLayout
//     // private ArrayList<String[]> data;
//     // // Biến lưu hàng đang được chọn
//     // private int selectedRow = -1;
//     // // Màu khi hàng được chọn
//     // // private final Color selectedColor = new Color(180, 255, 245);
//     // private final Color selectedColor = new Color(180, 200, 255);
//     // private String[][] actions;
    

//     // public CustomTable(ArrayList<String[]> data,String[][] actions,String...headers) {
//     //     this.data = data;
//     //     this.actions = actions;
//     //     this.headers = headers;
//     //     this.columnWidths = new int[headers.length];
//     //     this.rowHeights = new int[1];
//     //     // Đặt giá trị mặc định
//     //     for (int i = 0; i < headers.length; i++) {
//     //         columnWidths[i] = 150; // Độ rộng mặc định cho cột
//     //     }
//     //     rowHeights[0] = 30; // Chiều cao mặc định cho header

//     //     // Khởi tạo MigLayout
//     //     migLayout = new MigLayout(
//     //         "insets 0, fillx,gap 0" // Không padding, fill theo chiều ngang, bật debug
//     //         // getColumnConstraints(), // Constraint động cho cột
//     //         // getRowConstraints() // Constraint động cho hàng
//     //     );
//     //     setLayout(migLayout);

//     //     // Thêm header
//     //     for (int i = 0; i < headers.length; i++) {
//     //         add(createHeaderLabel(headers[i]), "grow,cell " + i + " 0");
//     //     }

//     //     add(createHeaderLabel("Hành động"), "grow,cell " + headers.length + " 0");


//     //     for (String[] x : data) 
//     //         addDataRow(x);
        
//     // }

//     private final String[] headers;
//     private Color headerBackground = Color.LIGHT_GRAY;
//     private final Color evenRowColor = new Color(240, 240, 240);
//     private final Color oddRowColor = Color.WHITE;
//     private final Color hoverColor = new Color(220, 220, 255);
//     private final java.util.Map<Integer, List<Component>> rowLabels = new java.util.HashMap<>();
//     private int[] columnWidths;
//     private int[] rowHeights;
//     private MigLayout migLayout;
//     private ArrayList<String[]> data;
//     private int selectedRow = -1;
//     private final Color selectedColor = new Color(180, 200, 255);
//     private String[][] actions;
//     private JPanel headerPanel; // Panel cho header
//     private JPanel dataPanel;  // Panel cho dữ liệu
//     private JScrollPane scrollPane; // JScrollPane cho dữ liệu

//     public CustomTable(ArrayList<String[]> data, String[][] actions, String... headers) {
//         this.data = data;
//         this.actions = actions;
//         this.headers = headers;
//         this.columnWidths = new int[headers.length];
//         this.rowHeights = new int[data.size() + 1]; // +1 cho header
//         for (int i = 0; i < headers.length; i++) {
//             columnWidths[i] = 150;
//         }
//         for (int i = 0; i < rowHeights.length; i++) {
//             rowHeights[i] = 30;
//         }

//         // Sử dụng BorderLayout cho CustomTable
//         setLayout(new BorderLayout());

//         // Tạo headerPanel
//         headerPanel = new JPanel(new MigLayout("insets 0, fillx, gap 0"));
//         for (int i = 0; i < headers.length; i++) {
//             headerPanel.add(createHeaderLabel(headers[i]), "grow,cell " + i + " 0");
//         }
//         headerPanel.add(createHeaderLabel("Hành động"), "grow,cell " + headers.length + " 0");

//         // Tạo dataPanel
//         dataPanel = new JPanel();
//         migLayout = new MigLayout("insets 0, fillx, gap 0");
//         dataPanel.setLayout(migLayout);

//         // Thêm dữ liệu vào dataPanel
//         for (String[] x : data) {
//             addDataRow(x);
//         }

//         // Tạo JScrollPane cho dataPanel
//         scrollPane = new JScrollPane(dataPanel);
//         scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//         scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//         scrollPane.setBorder(null); // Xóa viền nếu muốn

//         // Thêm headerPanel và scrollPane vào CustomTable
//         add(headerPanel, BorderLayout.NORTH);
//         add(scrollPane, BorderLayout.CENTER);
//     }

//     // Phương thức để set background cho header
//     public void setHeaderBackground(Color color) {
//         this.headerBackground = color;
//         for (Component comp : getComponents()) {
//             if (comp instanceof JLabel && getComponentZOrder(comp) < headers.length) {
//                 comp.setBackground(headerBackground);
//             }
//         }
//         repaint();
//     }

//     // Phương thức để set độ rộng cho cột
//     public void setColumnWidth(int columnIndex, int width) {
//         if (columnIndex < 0 || columnIndex >= headers.length) {
//             throw new IllegalArgumentException("Column index out of bounds");
//         }
//         columnWidths[columnIndex] = width;
//         updateColumnConstraints();
//         revalidate();
//         repaint();
//     }

//     // Phương thức để set chiều cao cho hàng
//     public void setRowHeight(int rowIndex, int height) {
//         if (rowIndex < 0) {
//             throw new IllegalArgumentException("Row index must be non-negative");
//         }
//         if (rowIndex >= rowHeights.length) {
//             int[] newRowHeights = new int[rowIndex + 1];
//             System.arraycopy(rowHeights, 0, newRowHeights, 0, rowHeights.length);
//             for (int i = rowHeights.length; i < newRowHeights.length; i++) {
//                 newRowHeights[i] = 30; // Chiều cao mặc định cho hàng mới
//             }
//             rowHeights = newRowHeights;
//         }
//         rowHeights[rowIndex] = height;
//         updateRowConstraints();
//         revalidate();
//         repaint();
//     }

//     // Cập nhật constraint cho cột
//     private void updateColumnConstraints() {
//         migLayout.setColumnConstraints(getColumnConstraints());
//     }

//     // Cập nhật constraint cho hàng
//     private void updateRowConstraints() {
//         migLayout.setRowConstraints(getRowConstraints());
//     }

//     // Tạo chuỗi constraint cho cột
//     private String getColumnConstraints() {
//         StringBuilder columnConstraints = new StringBuilder();
//         for (int width : columnWidths) {
//             columnConstraints.append("[").append(width).append("]");
//         }
//         return columnConstraints.toString();
//     }

//     // Tạo chuỗi constraint cho hàng
//     private String getRowConstraints() {
//         StringBuilder rowConstraints = new StringBuilder();
//         for (int height : rowHeights) {
//             rowConstraints.append("[").append(height).append("]");
//         }
//         return rowConstraints.toString();
//     }

//     // Tạo JLabel cho header
//     // private JLabel createHeaderLabel(String text) {
//     //     JLabel label = new JLabel(text);
//     //     label.setPreferredSize(new Dimension(label.getWidth(),25));
//     //     label.setOpaque(true);
//     //     label.setBackground(headerBackground);
//     //     label.setHorizontalAlignment(SwingConstants.CENTER);
//     //     label.setVerticalAlignment(SwingConstants.CENTER);
//     //     // label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//     //     label.setFont(new Font(label.getFont().getName(),Font.BOLD,15));
//     //     return label;
//     // }

//     // // Tạo JLabel cho dữ liệu
//     // private JLabel createDataLabel(String text, int row) {
//     //     JLabel label = new JLabel(text);
//     //     // label.setPreferredSize(new Dimension(label.getWidth(),30));
//     //     label.setBorder(new EmptyBorder(10,5,10,5));        
//     //     label.setOpaque(true);
//     //     label.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);
//     //     label.setHorizontalAlignment(SwingConstants.CENTER);
//     //     label.setVerticalAlignment(SwingConstants.CENTER);
//     //     // label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//     //     return label;
//     // }

//     // private JPanel createActionPanel(int row) {
//     //     JPanel label = new JPanel(new MigLayout("al center center, gap 10"));
//     //     label.setPreferredSize(new Dimension(label.getWidth(),30));
//     //     label.setOpaque(true);
//     //     label.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);

//     //     CustumActionButtonTable but;
//     //     for (String[] x : actions) {
//     //         but = new CustumActionButtonTable(x[0], x[1],row);
//     //         label.add(but);
//     //         but.addActionListener(this);
//     //     }
//     //     // label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//     //     return label;
//     // }

//     // public void addDataRow(String[] data) {
//     //     int row = rowLabels.size()+1; // Xác định số hàng hiện tại để thêm vào hàng mới
        
//     //     java.util.ArrayList<Component> labels = new java.util.ArrayList<>();
//     //     final Color defaultColor = (row % 2 == 0 ? evenRowColor : oddRowColor);
        
//     //     for (int i = 0; i < Math.min(data.length, headers.length); i++) {
//     //         JLabel label = createDataLabel(data[i], row);
//     //         labels.add(label);
//     //         add(label, "grow,cell " + i + " " + row);
            
//     //         // Thêm MouseListener cho hiệu ứng hover
//     //         label.addMouseListener(new java.awt.event.MouseAdapter() {
//     //             // @Override
//     //             // public void mouseEntered(java.awt.event.MouseEvent evt) {
//     //             //     for (JLabel lbl : labels) {
//     //             //         lbl.setBackground(hoverColor);
//     //             //     }
//     //             // }
                
//     //             // @Override
//     //             // public void mouseExited(java.awt.event.MouseEvent evt) {
//     //             //     for (JLabel lbl : labels) {
//     //             //         lbl.setBackground(defaultColor);
//     //             //     }
//     //             // }

//     //             public void mouseClicked(java.awt.event.MouseEvent evt) {
//     //                 setSelectedRow(row);
//     //             }
//     //         });
//     //     }

//     //     JPanel panel = createActionPanel(row);
//     //     labels.add(panel);
//     //     add(panel,"grow,cell " + headers.length + " " + row);
        
//     //     rowLabels.put(row, labels);
//     //     revalidate(); // Cập nhật layout
//     //     repaint();
//     // }    


//     // public void removeRow(int row) {
//     //     if (!rowLabels.containsKey(row)) return;
        
//     //     // Xóa các thành phần giao diện của hàng
//     //     for (Component comp : rowLabels.get(row)) {
//     //         remove(comp);
//     //     }
//     //     rowLabels.remove(row);
        
//     //     // Cập nhật lại vị trí các hàng bên dưới (nếu có)
//     //     java.util.Map<Integer, List<Component>> updatedRowLabels = new java.util.HashMap<>();
//     //     int newRowIndex = 1;
//     //     for (int key : rowLabels.keySet()) {
//     //         if (key != row) {
//     //             updatedRowLabels.put(newRowIndex, rowLabels.get(key));
//     //             newRowIndex++;
//     //         }
//     //     }

//     //     rowLabels.clear();
//     //     rowLabels.putAll(updatedRowLabels);
//     //     updateRowColors();   
//     //     revalidate();
//     //     repaint();
//     // }

//     private JLabel createHeaderLabel(String text) {
//         JLabel label = new JLabel(text);
//         label.setPreferredSize(new Dimension(150, 30)); // Kích thước cố định cho header
//         label.setOpaque(true);
//         label.setBackground(headerBackground);
//         label.setHorizontalAlignment(SwingConstants.CENTER);
//         label.setVerticalAlignment(SwingConstants.CENTER);
//         label.setFont(new Font(label.getFont().getName(), Font.BOLD, 15));
//         return label;
//     }

//     private JLabel createDataLabel(String text, int row) {
//         JLabel label = new JLabel(text);
//         label.setBorder(new EmptyBorder(10, 5, 10, 5));
//         label.setOpaque(true);
//         label.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);
//         label.setHorizontalAlignment(SwingConstants.CENTER);
//         label.setVerticalAlignment(SwingConstants.CENTER);
//         return label;
//     }

//     private JPanel createActionPanel(int row) {
//         JPanel label = new JPanel(new MigLayout("al center center, gap 10"));
//         label.setPreferredSize(new Dimension(150, 30));
//         label.setOpaque(true);
//         label.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);

//         CustumActionButtonTable but;
//         for (String[] x : actions) {
//             but = new CustumActionButtonTable(x[0], x[1], row);
//             label.add(but);
//             but.addActionListener(this);
//         }
//         return label;
//     }

//     public void addDataRow(String[] data) {
//         int row = rowLabels.size() + 1;
//         ArrayList<Component> labels = new ArrayList<>();
//         final Color defaultColor = (row % 2 == 0 ? evenRowColor : oddRowColor);

//         for (int i = 0; i < Math.min(data.length, headers.length); i++) {
//             JLabel label = createDataLabel(data[i], row);
//             labels.add(label);
//             dataPanel.add(label, "grow,cell " + i + " " + (row - 1)); // row - 1 vì không có header trong dataPanel
//             label.addMouseListener(new java.awt.event.MouseAdapter() {
//                 public void mouseClicked(java.awt.event.MouseEvent evt) {
//                     setSelectedRow(row);
//                 }
//             });
//         }

//         JPanel panel = createActionPanel(row);
//         labels.add(panel);
//         dataPanel.add(panel, "grow,cell " + headers.length + " " + (row - 1));

//         rowLabels.put(row, labels);
//         dataPanel.revalidate();
//         dataPanel.repaint();
//     }

//     public void removeRow(int row) {
//         if (!rowLabels.containsKey(row)) return;

//         for (Component comp : rowLabels.get(row)) {
//             dataPanel.remove(comp);
//         }
//         rowLabels.remove(row);

//         Map<Integer, List<Component>> updatedRowLabels = new HashMap<>();
//         int newRowIndex = 1;
//         for (int key : rowLabels.keySet()) {
//             if (key != row) {
//                 updatedRowLabels.put(newRowIndex, rowLabels.get(key));
//                 newRowIndex++;
//             }
//         }

//         rowLabels.clear();
//         rowLabels.putAll(updatedRowLabels);
//         updateRowColors();
//         dataPanel.revalidate();
//         dataPanel.repaint();
//     }

    
//     public void setSelectedRow(int row) {
//         if (!rowLabels.containsKey(row)) return;
//         if (selectedRow != -1 && rowLabels.containsKey(selectedRow)) {
//             // Trả hàng cũ về màu mặc định
//             Color previousColor = (selectedRow % 2 == 0) ? evenRowColor : oddRowColor;
//             for (Component lbl : rowLabels.get(selectedRow)) {
//                 lbl.setBackground(previousColor);
//             }
//         }
    
//         // Cập nhật hàng mới
//         selectedRow = row;
//         for (Component lbl : rowLabels.get(selectedRow)) {
//             lbl.setBackground(selectedColor);
//         }
    
//         repaint();
//         revalidate();
//     }
    
    
//     public void updateRowColors() {
//         int rowIndex = 1;
//         for (List<Component> rowComponents : rowLabels.values()) {
//             Color backgroundColor = (rowIndex % 2 == 0) ? evenRowColor : oddRowColor;
//             for (Component comp : rowComponents) {
//                 comp.setBackground(backgroundColor);
//             }
//             rowIndex++;
//         }
//     }
    
//     public void editRow(int row) {
//         if (!rowLabels.containsKey(row)) return;
        
//         List<Component> rowComponents = rowLabels.get(row);
//         List<Component> newComponents = new ArrayList<>();
        
//         for (int i = 0; i < headers.length; i++) {
//             Component oldComp = rowComponents.get(i);
//             if (oldComp instanceof JLabel) {
//                 JLabel label = (JLabel) oldComp;
//                 JTextField textField = new JTextField(label.getText());
//                 textField.setPreferredSize(new Dimension(textField.getWidth(), 30));
//                 newComponents.add(textField);
//                 remove(label);
//                 add(textField, "grow,cell " + i + " " + row);
//             } else {
//                 newComponents.add(oldComp);
//             }
//         }
        
//         rowLabels.put(row, newComponents);
//         revalidate();
//         repaint();
//     }

//     @Override
//     public void actionPerformed(ActionEvent e) {
//         CustumActionButtonTable but = (CustumActionButtonTable) e.getSource();
        
//         switch (but.getId()) {
//             case "edit":
//                 editRow(but.getRow());
//                 break;
//             case "detail":
                
//                 break;
//             case "remove":
//                 removeRow(but.getRow());
//                 break;
        
//             default:
//                 break;
//         }
//     }

    
// }

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
        headerPanel.add(createHeaderLabel("Hành động"), "grow,cell " + headers.length + " 0");

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
        scrollPane = new CustomScrollPane(dataPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);

        // Thêm vào CustomTable
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Đặt kích thước mặc định cho CustomTable
        setMinimumSize(new Dimension(headers.length * 150, 200));
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

    
    public void removeRow(int row) {
        if (!rowLabels.containsKey(row)) return;
        
        for (Component comp : rowLabels.get(row)) {
            dataPanel.remove(comp);
        }
        rowLabels.remove(row);
        
        Map<Integer, List<Component>> updatedRowLabels = new HashMap<>();
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
        updateRowConstraints();
        dataPanel.setPreferredSize(new Dimension(headers.length * 150, rowLabels.size() * 30));
        dataPanel.revalidate();
        dataPanel.repaint();
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

        JPanel panel = createActionPanel(row);
        labels.add(panel);
        dataPanel.add(panel, "grow,cell " + headers.length + " " + (row - 1));

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