///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package GUI.component;

import net.miginfocom.swing.MigLayout;
import utils.TextUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    //Callback sự kiện chọn hàng
    protected OnSelectRowListener onSelectRowListener;
    //set
    protected TableActionListener actionListener;

    protected int maxTextWidth = 110;


    // Các thuộc tính hiện có...
    protected JTextField currentEditingField = null;
    protected int currentEditingRow = -1;
    protected int currentEditingCol = -1;
    

    public JPanel getDataPanel() {
        return dataPanel;
    }

    public CustomTable(ArrayList<String[]> data, String[][] actions, String... headers) {
        // setBackground(Color.white);
        this.data = ((data == null)? new ArrayList<>(): data);
        this.actions = actions;
        this.headers = headers;
        this.columnWidths = new int[headers.length];
        this.rowHeights = new int[this.data.size() + 1]; // +1 cho header
        this.init();
    }
    //Cho form cần sự kiện chọn vào hàng
    public CustomTable(ArrayList<String[]> data, String[][] actions, OnSelectRowListener onSelectRowListener, String... headers) {
        // setBackground(Color.white);
        this.data = ((data == null)? new ArrayList<>(): data);
        this.actions = actions;
        this.headers = headers;
        this.onSelectRowListener = onSelectRowListener;
        this.columnWidths = new int[headers.length];
        this.rowHeights = new int[this.data.size() + 1]; // +1 cho header
        this.init();
    }

    public void init(){
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
        migLayout = new MigLayout("insets 0 0 20 0, fillx, gap 0", getColumnConstraints(), getRowConstraints());
        dataPanel.setLayout(migLayout);
        // dataPanel.setOpaque(true);
        // dataPanel.setBackground(Color.white);

        // Thêm dữ liệu ban đầu
        for (String[] x : this.data) {
            addDataRow(x);
        }

        // Đặt PreferredSize cho dataPanel
        // dataPanel.setPreferredSize(new Dimension(headers.length * 150, rowLabels.size() * 30));
        setDataPanelPre();
        // Tạo JScrollPane
        
        
        // Thêm vào CustomTable
        add(headerPanel, BorderLayout.NORTH);   
        addScollPane();

        // Đặt kích thước mặc định cho CustomTable
        // setMinimumSize(new Dimension(400, 100));
        // setPreferredSize(null);
    }

    public void setDataPanelPre() {
        dataPanel.setPreferredSize(null);
        // dataPanel.setPreferredSize(new Dimension(headers.length * 150, rowLabels.size() * 30));
    }

    public void setActionListener(TableActionListener listener) {
        this.actionListener = listener;
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
        // int lim = (int) 110/(headers.length);
        // if (text!=null && text.length() > lim) {
        //     text = text.substring(0, lim-3) + "...";
        // }

        text = TextUtils.orverFlowText(text, maxTextWidth, headers.length);

        JLabel label = new JLabel(text);
        label.setBorder(new EmptyBorder(10, 5, 10, 5));
        label.setOpaque(true);
        label.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        // Thêm MouseListener cho nhấp đúp
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    // Tìm chỉ số hàng hiện tại
                    int currentRow = -1;
                    for (Map.Entry<Integer, List<Component>> entry : rowLabels.entrySet()) {
                        if (entry.getValue().contains(label)) {
                            currentRow = entry.getKey();
                            break;
                        }
                    }
                    if (currentRow == -1) return; // Hàng không tồn tại

                    // Tìm chỉ số cột
                    int colIndex = -1;
                    List<Component> rowComponents = rowLabels.get(currentRow);
                    if (rowComponents != null) {
                        for (int i = 0; i < rowComponents.size(); i++) {
                            if (rowComponents.get(i) == label) {
                                colIndex = i;
                                break;
                            }
                        }
                    }
                    if (colIndex < headers.length) {
                        startEditing(currentRow, colIndex, label);
                    }
                }
            }
        });
        return label;
    }

    protected void startEditing(int row, int col, JLabel label) {
        // Nếu đang chỉnh sửa ô khác, lưu ô đó trước
        if (currentEditingField != null) {
            saveEditing();
        }
    
        // Lấy dữ liệu gốc từ mảng data
        String originalText = (row - 1 < data.size() && col < data.get(row - 1).length) 
            ? data.get(row - 1)[col] : label.getText();
    
        // Tạo JTextField chỉ đọc
        JTextField textField = new JTextField(originalText);
        textField.setPreferredSize(new Dimension(150, 30));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);
        textField.setEditable(false); // Vô hiệu hóa chỉnh sửa
    
        // Thay thế JLabel bằng JTextField
        dataPanel.remove(label);
        dataPanel.add(textField, "grow,cell " + col + " " + (row - 1));
        rowLabels.get(row).set(col, textField);
    
        // Lưu trạng thái chỉnh sửa
        currentEditingField = textField;
        currentEditingRow = row;
        currentEditingCol = col;
    
        // Thêm FocusListener và KeyListener để trở lại JLabel
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                saveEditing();
            }
        });
    
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    saveEditing();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cancelEditing(row, col, originalText);
                }
            }
        });
    
        // Cập nhật giao diện và đặt tiêu điểm
        dataPanel.revalidate();
        dataPanel.repaint();
        textField.requestFocusInWindow();
    }
    
    private void saveEditing() {
        if (currentEditingField == null) return;
    
        int row = currentEditingRow;
        int col = currentEditingCol;
    
        // Lấy văn bản gốc từ mảng data vì JTextField không thể chỉnh sửa
        String originalText = (row - 1 < data.size() && col < data.get(row - 1).length) 
            ? data.get(row - 1)[col] : "";
    
        // Chuyển lại thành JLabel
        JLabel label = createDataLabel(originalText, row);
        dataPanel.remove(currentEditingField);
        dataPanel.add(label, "grow,cell " + col + " " + (row - 1));
        rowLabels.get(row).set(col, label);
    
        // Xóa trạng thái chỉnh sửa
        currentEditingField = null;
        currentEditingRow = -1;
        currentEditingCol = -1;
    
        // Cập nhật giao diện
        dataPanel.revalidate();
        dataPanel.repaint();
    }

    private void cancelEditing(int row, int col, String originalText) {
        if (currentEditingField == null) return;

        // Chuyển lại thành JLabel với văn bản gốc
        JLabel label = createDataLabel(originalText, row);
        dataPanel.remove(currentEditingField);
        dataPanel.add(label, "grow,cell " + col + " " + (row - 1));
        rowLabels.get(row).set(col, label);

        // Xóa trạng thái chỉnh sửa
        currentEditingField = null;
        currentEditingRow = -1;
        currentEditingCol = -1;

        // Cập nhật giao diện
        dataPanel.revalidate();
        dataPanel.repaint();
    }


    public JPanel createActionPanel(int row) {

        JPanel label = new JPanel(new MigLayout("al center center, gap 10"));
        // label.setPreferredSize(new Dimension(150, 30));
        label.setOpaque(true);
        label.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);

        for (String[] x : actions) {
            CustumActionButtonTable but = new CustumActionButtonTable(x[0], x[1], row);
            label.add(but);
            but.addActionListener(this); // Gắn ActionListener
        }
        return label;
    }

    public void removeRow(int row) {
        if (!rowLabels.containsKey(row)) return;

        // Lưu trạng thái chỉnh sửa nếu hàng bị xóa đang được chỉnh sửa
        if (currentEditingRow == row) {
            saveEditing();
        }

        // Xóa hàng khỏi data
        if (row - 1 < data.size()) {
            data.remove(row - 1);
        }

        // Cập nhật rowHeights
        if (row < rowHeights.length) {
            int[] newRowHeights = new int[rowHeights.length - 1];
            for (int i = 0, j = 0; i < rowHeights.length; i++) {
                if (i != row) {
                    newRowHeights[j++] = rowHeights[i];
                }
            }
            rowHeights = newRowHeights;
        }

        // Lưu dữ liệu và xóa rowLabels, dataPanel
        ArrayList<String[]> tempData = new ArrayList<>(data);
        dataPanel.removeAll();
        rowLabels.clear();

        // Tái tạo các hàng từ tempData
        for (String[] rowData : tempData) {
            addDataRow(rowData);
        }

        // Cập nhật selectedRow
        if (selectedRow == row) {
            selectedRow = -1;
        } else if (selectedRow > row) {
            selectedRow--;
        }

        // Cập nhật màu, constraints và giao diện
        updateRowColors();
        updateRowConstraints();
        dataPanel.setPreferredSize(null);
        dataPanel.revalidate();
        dataPanel.repaint();
        revalidate();
        repaint();
    }

    // Phương thức sửa đổi để lưu ô đang chỉnh sửa khi chọn hàng khác
    public void setSelectedRow(int row) {
        if (!rowLabels.containsKey(row)) return;

        // Lưu ô đang chỉnh sửa trước khi thay đổi hàng được chọn
        if (currentEditingField != null) {
            saveEditing();
        }

        // Hủy màu nền của hàng được chọn trước đó
        if (selectedRow != -1 && rowLabels.containsKey(selectedRow)) {
            Color previousColor = (selectedRow % 2 == 0) ? evenRowColor : oddRowColor;
            for (Component lbl : rowLabels.get(selectedRow)) {
                lbl.setBackground(previousColor);
            }
        }

        // Cập nhật hàng được chọn mới
        selectedRow = row;
        for (Component lbl : rowLabels.get(row)) {
            lbl.setBackground(selectedColor);
        }

        dataPanel.revalidate();
        dataPanel.repaint();
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
        // dataPanel.setPreferredSize(new Dimension(headers.length * 150, rowLabels.size() * 30));
        dataPanel.setPreferredSize(null);
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
        // dataPanel.setPreferredSize(new Dimension(headers.length * 150, rowLabels.size() * 30));
        dataPanel.setPreferredSize(null);
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

    // @Override
    // public void actionPerformed(ActionEvent e) {
    //     CustumActionButtonTable but = (CustumActionButtonTable) e.getSource();
    //     switch (but.getId()) {
    //         case "edit":
    //             editRow(but.getRow());
    //             break;
    //         case "remove":
    //             removeRow(but.getRow());
    //             break;
    //         default:
    //         break;
    //     }
    // }

    @Override
    public void actionPerformed(ActionEvent e) {
        CustumActionButtonTable but = (CustumActionButtonTable) e.getSource();
        int buttonRow = but.getRow();

        // Kiểm tra hàng hợp lệ
        if (!rowLabels.containsKey(buttonRow)) {
            return;
        }

        // Thông báo sự kiện lên listener nếu có
        if (actionListener != null) {
            actionListener.onActionPerformed(but.getId(), buttonRow);
        }
    }

    public void updateTable(ArrayList<String[]> newData) {
        // Lưu trữ trạng thái hàng được chọn
        int previouslySelectedRow = selectedRow;
        
        // Xóa tất cả dữ liệu hiện tại
        dataPanel.removeAll();
        rowLabels.clear();
        
        // Cập nhật dữ liệu mới
        this.data = (newData == null) ? new ArrayList<>() : newData;
        
        // Cập nhật rowHeights
        rowHeights = new int[this.data.size() + 1]; // +1 cho header
        for (int i = 0; i < rowHeights.length; i++) {
            rowHeights[i] = 30;
        }
        
        // Thêm lại các hàng dữ liệu
        for (String[] rowData : this.data) {
            addDataRow(rowData);
        }
        
        // Cập nhật constraints và kích thước
        updateRowConstraints();
        // dataPanel.setPreferredSize(new Dimension((headers.length + (actions != null ? 1 : 0)) * 150, rowLabels.size() * 30));
        // dataPanel.setPreferredSize(new Dimension((headers.length + (actions != null ? 1 : 0)) * 150, rowLabels.size() * 30));
        dataPanel.setPreferredSize(null);
        
        // Khôi phục trạng thái chọn hàng nếu có thể
        if (previouslySelectedRow != -1 && previouslySelectedRow <= rowLabels.size()) {
            setSelectedRow(previouslySelectedRow);
        } else {
            selectedRow = -1; // Reset nếu hàng được chọn trước đó không còn tồn tại
        }
        
        // Cập nhật giao diện
        dataPanel.revalidate();
        dataPanel.repaint();
        revalidate();
        repaint();
    }

    
    public void addDataRow(String[] data) {
        int row = rowLabels.size() + 1;
        
        // / Xử lý trường hợp data là null
        String[] rowData = (data == null) ? new String[headers.length] : data;
        
        if (data == null) {
            for (int i = 0; i < rowData.length; i++) {
                rowData[i] = ""; // Điền giá trị rỗng cho dòng trống
            }
        }

        ArrayList<Component> labels = new ArrayList<>();

        for (int i = 0; i < Math.min(rowData.length, headers.length); i++) {
            JLabel label = createDataLabel(rowData[i], row);
            labels.add(label);
            dataPanel.add(label, "grow,cell " + i + " " + (row - 1));
            label.addMouseListener(new java.awt.event.MouseAdapter() {
                // public void mouseClicked(java.awt.event.MouseEvent evt) {
                //     setSelectedRow(row);
                // }

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    // Tìm chỉ số hàng hiện tại của JLabel trong rowLabels
                    int currentRow = -1;
                    for (Map.Entry<Integer, List<Component>> entry : rowLabels.entrySet()) {
                        if (entry.getValue().contains(label)) {
                            currentRow = entry.getKey();
                            break;
                        }
                    }
                    if (currentRow != -1) {
                        setSelectedRow(currentRow); // Chọn hàng dựa trên chỉ số hiện tại
                        if(onSelectRowListener != null){
                            onSelectRowListener.OnSelectRow(currentRow); // đổi row->currentRow
                        }
                    }
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
        // dataPanel.setPreferredSize(new Dimension(headers.length * 150, rowLabels.size() * 30));
        dataPanel.setPreferredSize(null);
        dataPanel.revalidate();
        dataPanel.repaint();
    }

    public String getCellData(int row, int column){
        String result = ((JLabel)rowLabels.get(row).get(column)).getText();
        return(result);
    }

    public void setCellData(int row, int column, String text){
        JLabel label = (JLabel)rowLabels.get(row).get(column);
        label.setText(text);
    }

    // public void setRowData(int row, String... list){
    //     for(int i = 0; i < list.length; i++){
    //         JLabel label = (JLabel)rowLabels.get(row).get(i);
    //         label.setText(list[i]);
    //     }
    // }

    public void setRowData(int row, String... newData) {
        if (!rowLabels.containsKey(row)) {
            return; // Hàng không tồn tại
        }
        if (newData == null || newData.length == 0) {
            return; // Dữ liệu mới không hợp lệ
        }

        List<Component> rowComponents = rowLabels.get(row);
        for (int i = 0; i < Math.min(newData.length, headers.length); i++) {
            newData[i] = TextUtils.orverFlowText(newData[i], maxTextWidth, headers.length);
            Component comp = rowComponents.get(i);
            if (comp instanceof JLabel) {
                ((JLabel) comp).setText(newData[i] != null ? newData[i] : "");
            }
        }

        // Cập nhật dữ liệu trong mảng data
        if (row - 1 < data.size()) {
            data.set(row - 1, newData);
        }

        // Cập nhật giao diện
        dataPanel.revalidate();
        dataPanel.repaint();
    }


    public void updateRowData(int row, String... newData) {
        if (!rowLabels.containsKey(row)) {
            return; // Hàng không tồn tại
        }
        if (newData == null || newData.length == 0) {
            return; // Dữ liệu mới không hợp lệ
        }

        List<Component> rowComponents = rowLabels.get(row);
        for (int i = 0; i < Math.min(newData.length, headers.length); i++) {
            TextUtils.orverFlowText(newData[i], maxTextWidth, headers.length);
            Component comp = rowComponents.get(i);
            if (comp instanceof JLabel) {
                ((JLabel) comp).setText(newData[i] != null ? newData[i] : "");
            }
        }

        // Cập nhật dữ liệu trong mảng data
        if (row - 1 < data.size()) {
            data.set(row - 1, newData);
        }

        // Cập nhật giao diện
        dataPanel.revalidate();
        dataPanel.repaint();
    }


    public void replaceColumnComponent(int columnIndex, String componentType) {
        if (columnIndex < 0 || columnIndex >= headers.length) {
            throw new IllegalArgumentException("Column index out of bounds");
        }

        for (Map.Entry<Integer, List<Component>> entry : rowLabels.entrySet()) {
            int row = entry.getKey();
            List<Component> components = entry.getValue();
            Component oldComponent = components.get(columnIndex);

            // Xóa component cũ khỏi dataPanel
            dataPanel.remove(oldComponent);

            // Tạo component mới dựa trên componentType
            Component newComponent;
            if ("JTextField".equalsIgnoreCase(componentType)) {
                String text = (oldComponent instanceof JLabel) ? ((JLabel) oldComponent).getText() : "";
                JTextField textField = new JTextField(text);
                textField.setPreferredSize(new Dimension(150, 30));
                textField.setHorizontalAlignment(SwingConstants.CENTER);
                textField.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);
                newComponent = textField;
            } else if ("JPanel".equalsIgnoreCase(componentType)) {
                JPanel panel = new JPanel();
                panel.setPreferredSize(new Dimension(150, 30));
                panel.setOpaque(true);
                panel.setBackground(row % 2 == 0 ? evenRowColor : oddRowColor);
                newComponent = panel;
            } else {
                throw new IllegalArgumentException("Unsupported component type: " + componentType);
            }

            // Thêm component mới vào dataPanel
            dataPanel.add(newComponent, "grow,cell " + columnIndex + " " + (row - 1));

            // Cập nhật rowLabels
            components.set(columnIndex, newComponent);
        }

        // Cập nhật giao diện
        dataPanel.revalidate();
        dataPanel.repaint();
    }
    public Map<Integer, List<Component>> getRowLabels() {
        return rowLabels;
    }

    // Hàm call back để xử lý thêm khi người dùng nhấn chọn 1 hàng (hiện tại có vùng kệ sử dụng)
    public interface OnSelectRowListener{
        public void OnSelectRow(int row);
    }


    // Hàm mới để đặt maxTextWidth và làm mới bảng
    public void setMaxTextWidth(int newMaxTextWidth) {
        if (newMaxTextWidth <= 0) {
            throw new IllegalArgumentException("maxTextWidth must be positive");
        }
        this.maxTextWidth = newMaxTextWidth;

        // Cập nhật lại văn bản cho tất cả các ô trong bảng
        for (int row = 1; row <= rowLabels.size(); row++) {
            List<Component> components = rowLabels.get(row);
            for (int col = 0; col < Math.min(components.size(), headers.length); col++) {
                Component comp = components.get(col);
                if (comp instanceof JLabel && col < headers.length) {
                    String originalText = col < data.get(row - 1).length ? data.get(row - 1)[col] : "";
                    ((JLabel) comp).setText(TextUtils.orverFlowText(originalText, maxTextWidth, headers.length));
                }
            }
        }

        dataPanel.revalidate();
        dataPanel.repaint();
    }
}