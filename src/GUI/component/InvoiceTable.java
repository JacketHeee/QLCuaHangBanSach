package GUI.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.SachBUS;
import DTO.SachDTO;
import GUI.component.search.TextFieldListSach;
import net.miginfocom.swing.MigLayout;
import utils.TextUtils;

public class InvoiceTable extends CustomTable{

    private SachBUS sachBUS;
    private DataForTable dataForTableFN;
    private TinhTongGia tinhTongGia;
    private TinhTongGiaChungTu tinhTongGiaChungTu;
    private String[] headerType;
    private Runnable updateKM; 

    public InvoiceTable(
            ArrayList<String[]> data, 
            String[][] actions, 
            DataForTable dataForTableFN, 
            TinhTongGia tinhTongGia,
            TinhTongGiaChungTu tinhTongGiaChungTu,
            String[] headers, 
            String[] headerType, 
            int maxText
            ) { // do có super làm hơi xấu 
        super(data, actions,maxText, headers);
        // Gọi constructor của lớp cha CustomTable
        // MigLayout layout = (MigLayout)getDataPanel().getLayout();
        // layout.setLayoutConstraints(layout.getLayoutConstraints()+",gap 2");
        this.headerType = headerType;
        this.dataForTableFN = dataForTableFN;
        this.tinhTongGia = tinhTongGia;
        this.tinhTongGiaChungTu = tinhTongGiaChungTu;
        sachBUS = SachBUS.getInstance();

        // Thêm MouseListener cho dataPanel
        dataPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentEditingField != null && e.getClickCount() == 1) {
                    System.out.println("Nhấp một lần trên dataPanel: thoát chỉnh sửa");
                    saveEditing();
                }
            }
        });
    }

    public Runnable getUpdateKM() {
        return updateKM;
    }

    public void setUpdateKM(Runnable updateKM) {
        this.updateKM = updateKM;
    }

    @Override
    public void setDataPanelPre() {
        dataPanel.setPreferredSize(null);
        // dataPanel.setPreferredSize(new Dimension(headers.length * 150, rowLabels.size() * 30));
    }

    //set ô
    @Override
    public void updateRowColors() {

    }


    public JPanel createDataInput(String text, int row, int columnIndex, String data[]) {
        String displayText = (text == null) ? "" : text;
        JPanel panel = new JPanel(new MigLayout("insets 10 5 10 5,al center center"));
        panel.setFocusable(false);
    
        if (this.headerType[columnIndex].equals("inputMa")) {
            TextFieldListSach textField = new TextFieldListSach(
                sachBUS.getAll(),
                new TextFieldListSach.CallBack() {
                    @Override
                    public void updateRowDatacb(SachDTO sach) {
                        int soLuong = getSoLuongSach(row);
                        String[] temp = dataForTableFN.getDataForTable(sach, soLuong);
                        for (int i = 0; i < temp.length; i++) {
                            if (temp[i] == null || temp[i].isEmpty()) {
                                temp[i] = i == 4 ? "0" : temp[i] == null ? "" : temp[i];
                            }
                        }
                        updateRowData(row, temp);
                        tinhTongGia.updateTongGia( row);
                        
                        //update list khuyến mãi tương ứng
                        
                    }
                },
                new TextFieldListSach.UpdateTongGiaCTWhenAddSach() {
                    @Override
                    public void TinhTongGiaCTWhenAddSach() {
                        tinhTongGiaChungTu.updateTongGiaChungTu();
                        // if (updateKM != null) {
                        //     updateKM.run();
                        // }
                    }
                }
            );
            textField.putClientProperty(FlatClientProperties.STYLE, "focusWidth: 0; innerFocusWidth:0");
            textField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mã sách");
            textField.setHorizontalAlignment(SwingConstants.CENTER);
            textField.setText(displayText);
            // Ngăn sự kiện nhấp chuột lan truyền
            textField.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    e.consume(); // Ngăn lan truyền lên dataPanel
                }
            });
            panel.add(textField, "push,growx");
        } else if (this.headerType[columnIndex].equals("inputNumber")) {
            CustomTextFieldSL textFieldSL = new CustomTextFieldSL();
            if (data != null) {
                textFieldSL.setText(data[columnIndex]);
            } else {
                textFieldSL.setText("1");
            }
            textFieldSL.putClientProperty(FlatClientProperties.STYLE, "focusWidth: 0; innerFocusWidth:0");
            textFieldSL.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Số lượng");
            textFieldSL.setHorizontalAlignment(SwingConstants.CENTER);
            // Ngăn sự kiện nhấp chuột lan truyền
            textFieldSL.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    e.consume(); // Ngăn lan truyền lên dataPanel
                }
            });
            panel.add(textFieldSL, "push,growx");
            setListenerTextFieldSL(textFieldSL, row);
        } else {
            JLabel label = createEditableLabel(displayText, row, columnIndex);
            label.setFocusable(false);
            panel.add(label, "push,grow");
        }
        panel.setOpaque(true);
        panel.setBackground(Color.white);
        // Ngăn sự kiện nhấp chuột lan truyền từ JPanel
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                e.consume(); // Ngăn lan truyền lên dataPanel
            }
        });
        return panel;
    }

    private JLabel createEditableLabel(String text, int row, int col) {

        text = TextUtils.orverFlowText(text, maxTextWidth, headers.length);

        JLabel label = new JLabel(text);
        // label.setPreferredSize(new Dimension(100, 40));
        // label.setMinimumSize(new Dimension(100, 40));
        // label.setMaximumSize(new Dimension(100, 40));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        // label.setBorder(new EmptyBorder(10, 5, 10, 5));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFocusable(false);
    
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    int currentRow = -1;
                    int colIndex = -1;
                    outerLoop:
                    for (Map.Entry<Integer, List<JComponent>> entry : rowLabels.entrySet()) {
                        List<JComponent> rowComponents = entry.getValue();
                        for (int i = 0; i < rowComponents.size(); i++) {
                            JComponent comp = rowComponents.get(i);
                            if (comp instanceof JPanel) {
                                for (Component innerComp : ((JPanel) comp).getComponents()) {
                                    if (innerComp == label) {
                                        currentRow = entry.getKey();
                                        colIndex = i;
                                        break outerLoop;
                                    }
                                }
                            }
                        }
                    }
                    if (currentRow == -1 || colIndex == -1) return;
    
                    if (colIndex < headers.length) {
                        startEditing(currentRow, colIndex, label);
                    }
                } else if (e.getClickCount() == 1) {
                    // Ngăn sự kiện nhấp một lần lan truyền lên dataPanel
                    e.consume();
                }
            }
        });
    
        return label;
    }

    @Override
    protected void startEditing(int row, int col, JLabel label) {
        if (currentEditingField != null) {
            saveEditing();
        }

        String originalText = (row - 1 < data.size() && col < data.get(row - 1).length)
                ? data.get(row - 1)[col] : label.getText();

        JTextField textField = new JTextField(originalText);
        // textField.setPreferredSize(new Dimension(100, 40));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setBackground(Color.WHITE);
        textField.setEditable(false);

        // Ngăn sự kiện nhấp chuột lan truyền
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                e.consume(); // Ngăn lan truyền lên dataPanel
            }
        });

        JComponent panel = rowLabels.get(row).get(col);
        if (!(panel instanceof JPanel)) return;

        ((JPanel) panel).removeAll();
        ((JPanel) panel).add(textField, "push,grow");

        currentEditingField = textField;
        currentEditingRow = row;
        currentEditingCol = col;

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

        dataPanel.revalidate();
        dataPanel.repaint();
        textField.requestFocusInWindow();
    }

    @Override
    protected void saveEditing() {
        if (currentEditingField == null) return;

        int row = currentEditingRow;
        int col = currentEditingCol;

        // Lấy văn bản từ JTextField
        String editedText = currentEditingField.getText();
        if (editedText.isEmpty()) {
            editedText = headerType[col].equals("inputNumber") ? "1" : "0";
        }

        // Cập nhật mảng data
        if (row - 1 < data.size() && col < data.get(row - 1).length) {
            data.get(row - 1)[col] = editedText;
        }

        // Tạo JLabel mới
        JLabel label = createEditableLabel(editedText, row, col);

        // Thay thế JTextField bằng JLabel trong JPanel
        JComponent panel = rowLabels.get(row).get(col);
        if (panel instanceof JPanel) {
            ((JPanel) panel).removeAll();
            ((JPanel) panel).add(label, "push,grow");
        }

        // Xóa trạng thái chỉnh sửa
        currentEditingField = null;
        currentEditingRow = -1;
        currentEditingCol = -1;

        // Cập nhật tổng tiền hàng
        tinhTongGia.updateTongGia(row);

        // Cập nhật giao diện
        dataPanel.revalidate();
        dataPanel.repaint();
    }

    @Override
    protected void cancelEditing(int row, int col, String originalText) {
        if (currentEditingField == null) return;

        // Sử dụng giá trị gốc, hoặc giá trị mặc định nếu rỗng
        String text = originalText.isEmpty() ? (headerType[col].equals("inputNumber") ? "1" : "0") : originalText;

        // Cập nhật mảng data
        if (row - 1 < data.size() && col < data.get(row - 1).length) {
            data.get(row - 1)[col] = text;
        }

        // Tạo JLabel mới
        JLabel label = createEditableLabel(text, row, col);

        // Thay thế JTextField bằng JLabel trong JPanel
        JComponent panel = rowLabels.get(row).get(col);
        if (panel instanceof JPanel) {
            ((JPanel) panel).removeAll();
            ((JPanel) panel).add(label, "push,grow");
        }

        // Xóa trạng thái chỉnh sửa
        currentEditingField = null;
        currentEditingRow = -1;
        currentEditingCol = -1;

        // Cập nhật tổng tiền hàng
        tinhTongGia.updateTongGia(row);

        // Cập nhật giao diện
        dataPanel.revalidate();
        dataPanel.repaint();
    }


    @Override
    public void addScollPane() {
        add(dataPanel,"pushx, grow");
    }

    // Phương thức hỗ trợ để đếm số component trong hàng hiện tại
    private int getComponentCountInRow(int row) {
        List<JComponent> components = rowLabels.get(row);
        return components != null ? components.size() : 0;
    }

    @Override
    public JPanel createActionPanel(int row) {
        JPanel label = new JPanel(new MigLayout("insets 0,al center center, gap 10"));
        // label.setPreferredSize(new Dimension(150, 30));
        label.setOpaque(true);
        label.setBackground(Color.white);

        CustumActionButtonTable but;
        for (String[] x : actions) {
            but = new CustumActionButtonTable(x[0], x[1], row);
            label.add(but);
            but.addActionListener(this);
        }
        return label;
    }

    @Override
    public void addDataRow(String[] data) {
        if (rowLabels.size()+1 > this.data.size()) 
            this.data.add(data);
        
        int row = rowLabels.size() + 1;
        String[] rowData = (data == null) ? new String[headers.length] : data;
        if (data == null) {
            for (int i = 0; i < rowData.length; i++) {
                rowData[i] = "";
            }
        }

        ArrayList<JComponent> labels = new ArrayList<>();
        for (int i = 0; i < headers.length; i++) {
            JPanel label = createDataInput(rowData[i], row, i, data);
            labels.add(label);
            dataPanel.add(label, "gapbottom 2,grow,cell " + i + " " + (row-1));

            label.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int currentRow = -1;
                    for (Map.Entry<Integer, List<JComponent>> entry : rowLabels.entrySet()) {
                        if (entry.getValue().contains(label)) {
                            currentRow = entry.getKey();
                            break;
                        }
                    }
                    if (currentRow != -1) {
                        setSelectedRow(currentRow);
                        if (onSelectRowListener != null) {
                            onSelectRowListener.OnSelectRow(currentRow);
                        }
                    }
                }
            });
        }

        if (actions != null) {
            JPanel panel = createActionPanel(row);
            labels.add(panel);
            dataPanel.add(panel, "gapbottom 2,grow,wrap");
            // dataPanel.add(panel, "grow,cell " + headers.length + " " + (row - 1));
        }

        rowLabels.put(row, labels);

        // if (row >= rowHeights.length) {
        //     int[] newRowHeights = new int[row + 1];
        //     System.arraycopy(rowHeights, 0, newRowHeights, 0, rowHeights.length);
        //     for (int i = rowHeights.length; i < newRowHeights.length; i++) {
        //         newRowHeights[i] = 30;
        //     }
        //     rowHeights = newRowHeights;
        // }

        // updateRowConstraints();

        dataPanel.setPreferredSize(null);
        dataPanel.revalidate();
        dataPanel.repaint();
        repaint();
        revalidate();

        // Tự động focus vào TextFieldListSach của cột đầu tiên
        focusOnAdd(row);
    }

     // Phương thức mới để focus vào TextFieldListSach của cột đầu tiên
     public void focusOnAdd(int row) {
        List<JComponent> rowComponents = rowLabels.get(row);
        if (rowComponents != null && !rowComponents.isEmpty()) {
            JComponent firstCell = rowComponents.get(0); // Cột đầu tiên
            if (firstCell instanceof JPanel) {
                JPanel panel = (JPanel) firstCell;
                if (panel.getComponentCount() > 0 && panel.getComponent(0) instanceof TextFieldListSach) {
                    TextFieldListSach textField = (TextFieldListSach) panel.getComponent(0);
                    textField.requestFocusInWindow();
                    textField.selectAll(); // Tùy chọn: chọn toàn bộ văn bản để dễ chỉnh sửa
                }
            }
        }
    }


    @Override
    public void setSelectedRow(int row) {
        if (!rowLabels.containsKey(row)) return;

        // Lưu ô đang chỉnh sửa trước khi thay đổi hàng được chọn
        if (currentEditingField != null) {
            saveEditing();
        }

        // Hủy màu nền của hàng được chọn trước đó
        if (selectedRow != -1 && rowLabels.containsKey(selectedRow)) {
            Color previousColor = (selectedRow % 2 == 0) ? evenRowColor : oddRowColor;
            for (JComponent lbl : rowLabels.get(selectedRow)) {
                lbl.setBackground(previousColor);
            }
        }

        // Cập nhật hàng được chọn mới
        selectedRow = row;

        // for (JComponent lbl : rowLabels.get(row)) {
        //     lbl.setBackground(selectedColor);
        // }

        dataPanel.revalidate();
        dataPanel.repaint();
    }
    
    @Override
    public String getCellData(int row, int column) {
        List<JComponent> rowComponents = rowLabels.get(row);
        if (rowComponents == null || column >= rowComponents.size()) {
            return "0"; // Giá trị mặc định nếu hàng hoặc cột không tồn tại
        }
        JComponent comp = rowComponents.get(column);
        if (comp instanceof JPanel) {
            JComponent innerComp = (JComponent) ((JPanel) comp).getComponent(0);
            if (innerComp instanceof JTextField) {
                String text = ((JTextField) innerComp).getText();
                return text.isEmpty() ? (headerType[column].equals("inputNumber") ? "1" : "0") : text;
            } else if (innerComp instanceof CustomTextFieldSL) {
                String text = ((CustomTextFieldSL) innerComp).getText();
                return text.isEmpty() ? "1" : text; // Giá trị mặc định cho số lượng
            } else if (innerComp instanceof JLabel) {
                String text = ((JLabel) innerComp).getText();
                return text.isEmpty() ? (headerType[column].equals("inputNumber") ? "1" : "0") : text;
            }
        }
        return headerType[column].equals("inputNumber") ? "1" : "0"; // Giá trị mặc định
    }


    @Override
    public void updateRowData(int row, String[] newData) {
        if (!rowLabels.containsKey(row) || newData == null || newData.length == 0) {
            return;
        }

        List<JComponent> rowComponents = rowLabels.get(row);
        for (int i = 0; i < Math.min(newData.length, headers.length); i++) {
            JComponent comp = rowComponents.get(i);
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                JComponent innerComp = (JComponent) panel.getComponent(0);
                String value = newData[i] != null ? newData[i] : "";
                value = TextUtils.orverFlowText(value, maxTextWidth, headers.length);
                
                if (innerComp instanceof JTextField) {
                    ((JTextField) innerComp).setText(value);
                } else if (innerComp instanceof CustomTextFieldSL) {
                    try {
                        ((CustomTextFieldSL) innerComp).setText(value);
                    } catch (IllegalArgumentException e) {
                        ((CustomTextFieldSL) innerComp).setText("0");
                    }
                } else if (innerComp instanceof JLabel) {
                    ((JLabel) innerComp).setText(value);
                }
            }
        }

        if (row <= data.size()) {
            data.set(row-1, newData);
        }

        dataPanel.revalidate();
        dataPanel.repaint();
    }

    public CustomTextFieldSL getTextFieldSL(int row){
        CustomTextFieldSL textField = null;
        for(int i = 0; i < headers.length; i++){
            if(headers[i].equals("Số lượng")){
                JComponent cpn = rowLabels.get(row).get(i);
                JPanel panel = (JPanel)cpn;
                textField = (CustomTextFieldSL)panel.getComponent(0);
                break;
            }
        }
        return(textField);
    }

    public int getSoLuongSach(int row){
        int result = -1;
        CustomTextFieldSL textField = getTextFieldSL(row);
        result = Integer.parseInt(textField.getText());
        return(result);
    }

    public void setListenerTextFieldSL(CustomTextFieldSL textFieldSL, int row){   //setListener khi thay đổi cột số lượng
        textFieldSL.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //khi số lượng thay đổi -> update cột thành tiền(đ) bằng hàm hóa đơn truyền vào
                tinhTongGia.updateTongGia(row);
                //update tổng giá cả chứng từ
                tinhTongGiaChungTu.updateTongGiaChungTu();
            }
        });
    }

    // public void updateTongTien(int row){
    //     System.out.println("luv");
    //     JLabel lblTongTien =  getLabelTongTien(row);
    //     JLabel lblGiaBan = getLabelGiaban(row);
    //     CustomTextFieldSL textFieldSL = getTextFieldSL(row);
    //     int soLuong = -1;
    //     BigDecimal giaBan = new BigDecimal(-1);

    //     try {
    //         soLuong = Integer.parseInt(textFieldSL.getText());
    //         System.out.println(soLuong);
    //     } catch (Exception e) {
    //         System.out.println("Lỗi parse SL");
    //     }
    //     try {
    //         giaBan = BigDecimal.valueOf(Double.parseDouble(lblGiaBan.getText()));
    //         System.out.println(giaBan);
    //     } catch (Exception e) {
    //         System.out.println("Lỗi parse GB");
    //     }
    //     lblTongTien.setText(giaBan.multiply(BigDecimal.valueOf(soLuong)) + "");
    // }

    public interface DataForTable {
        public String[] getDataForTable(SachDTO sach, int soLuong);
    }

    public interface TinhTongGia{
        public void updateTongGia(int row);
    }
    //callBack
    public interface TinhTongGiaChungTu{
        public void updateTongGiaChungTu();
    }

    @Override
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
        // updateRowConstraints();
        dataPanel.setPreferredSize(null);
        dataPanel.revalidate();
        dataPanel.repaint();
        revalidate();
        repaint();
    }

}
