package GUI.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.SachBUS;
import DTO.SachDTO;
import GUI.component.search.TextFieldListSach;
import net.miginfocom.swing.MigLayout;

public class InvoiceTable extends CustomTable{

    private SachBUS sachBUS;
    private DataForTable dataForTableFN;
    private TinhTongGia tinhTongGia;
    private TinhTongGiaChungTu tinhTongGiaChungTu;
    private String[] headerType;

    public InvoiceTable(ArrayList<String[]> data, String[][] actions, DataForTable dataForTableFN, TinhTongGia tinhTongGia, TinhTongGiaChungTu tinhTongGiaChungTu, String[] headers, String[] headerType) { // do có super làm hơi xấu
        super(data, actions, headers);
        // Gọi constructor của lớp cha CustomTable
        // MigLayout layout = (MigLayout)getDataPanel().getLayout();
        // layout.setLayoutConstraints(layout.getLayoutConstraints()+",gap 2");
        this.headerType = headerType;
        this.dataForTableFN = dataForTableFN;
        this.tinhTongGia = tinhTongGia;
        this.tinhTongGiaChungTu = tinhTongGiaChungTu;
        sachBUS = SachBUS.getInstance();
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

    public JPanel createDataInput(String text, int row, int columnIndex, String data[]) { // Thêm tham số columnIndex
        // Chuẩn hóa text khi null
        String displayText = (text == null) ? "" : text;
        JPanel panel = new JPanel(new MigLayout("insets 0,al center center"));

        // Cột đầu tiên (index 0) là JTextField
        if (this.headerType[columnIndex].equals("inputMa")) {
            TextFieldListSach textField = new TextFieldListSach(
                sachBUS.getAll()
                , new TextFieldListSach.CallBack(){ //  CallBack sau khi textFieldListSach trả về sachDTO lụm được 
                    @Override                       
                    public void updateRowDatacb(SachDTO sach) {
                        int soLuong = getSoLuongSach(row);
                        String[] temp = dataForTableFN.getDataForTable(sach, soLuong);  //Tái sử dụng cho hóa đơn và phiếu nhập
                        updateRowData(row, temp);
                    }
                }
                , new TextFieldListSach.UpdateTongGiaCTWhenAddSach() { //update tổng tiền cả chứng từ khi thêm sách mới
                    @Override
                    public void TinhTongGiaCTWhenAddSach() {
                        tinhTongGiaChungTu.updateTongGiaChungTu();  //callback ra form để uodate tổng giá chứng từ
                    }
                }
            );
            textField.putClientProperty(FlatClientProperties.STYLE, "focusWidth: 0; innerFocusWidth:0");
            textField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mã sách");
            textField.setHorizontalAlignment(SwingConstants.CENTER);
            textField.setText(displayText);
            panel.add(textField,"push,growx");
        }

        else if (this.headerType[columnIndex].equals("inputNumber")) {
            CustomTextFieldSL textFieldSL = new CustomTextFieldSL();
            // DecimalFormat plainFormat = new DecimalFormat("#");//fix tạm, tại đang hiển thị dấu ,
            // NumberFormatter formatter = new NumberFormatter(plainFormat);
            // formatter.setAllowsInvalid(false);
            // textFieldSL = new CustomTextFieldSL(formatter); //ngăn chặn nhập ký tự khác
            // formatter.setCommitsOnValidEdit(true); //cập nhật ngay khi hợp lệ

            if(data!=null){
                textFieldSL.setText(data[columnIndex]);
            }
            else {
                textFieldSL.setText("1"); 
            }
            textFieldSL.putClientProperty(FlatClientProperties.STYLE, "focusWidth: 0; innerFocusWidth:0");
            textFieldSL.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Số lượng");
            textFieldSL.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(textFieldSL,"push,growx");
            setListenerTextFieldSL(textFieldSL, row);
        }
        
        // Các cột khác vẫn là JLabel
        else {
            JLabel label = new JLabel(displayText);
            // label.setBorder(new javax.swing.border.EmptyBorder(10, 5, 10, 5));
            int height = 40;
            label.setPreferredSize(new Dimension(100,height)); //Sửa lại kích thước của Mạnh
            label.setMinimumSize(new Dimension(100,height));
            label.setMaximumSize(new Dimension(100,height));
            label.setOpaque(true);
            label.setBackground(Color.WHITE);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);

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

            panel.add(label,"push,grow");
        }
        panel.setOpaque(true);
        panel.setBackground(Color.white);
        return panel;
    }

    @Override
    public void addScollPane() {
        add(dataPanel,BorderLayout.CENTER);
    }

    // Phương thức hỗ trợ để đếm số component trong hàng hiện tại
    private int getComponentCountInRow(int row) {
        List<Component> components = rowLabels.get(row);
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



    // Ghi đè addDataRow để đảm bảo các thành phần được thêm đúng cách
    @Override
    public void addDataRow(String[] data) {
        // int row = rowLabels.size() + 1; //Vừa thêm + 1 để debug
        int row = rowLabels.size() + 1;// Chứng tỏ nếu đi từ 1 thì xảy ra lỗi
    
        // / Xử lý trường hợp data là null
        String[] rowData = (data == null) ? new String[headers.length] : data;
        
        if (data == null) {
            for (int i = 0; i < rowData.length; i++) {
                rowData[i] = ""; // Điền giá trị rỗng cho dòng trống
            }
        }
        
        ArrayList<Component> labels = new ArrayList<>();

        //Chỗ này chèn vào Panel
        for (int i = 0; i <headers.length ; i++) {
            JPanel label = createDataInput(rowData[i], row,i, data);
            labels.add(label);
            dataPanel.add(label, "gapbottom 2,grow,cell " + i + " " + (row));

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
            dataPanel.add(panel, "gapbottom 2,grow,wrap");
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
        repaint();
        revalidate();
    }

    // @Override
    // public void addDataRow(String[] data) {
    //     int row = rowLabels.size() + 1; // Đồng bộ với CustomTable (chỉ số từ 1)

    //     String[] rowData = (data == null) ? new String[headers.length] : data;
    //     if (data == null) {
    //         for (int i = 0; i < rowData.length; i++) {
    //             rowData[i] = "";
    //         }
    //     }

    //     ArrayList<Component> labels = new ArrayList<>();
    //     for (int i = 0; i < headers.length; i++) {
    //         JPanel panel = createDataInput(rowData[i], row, i, rowData);
    //         labels.add(panel);
    //         dataPanel.add(panel, "gapbottom 2,grow,cell " + i + " " + (row - 1));
    //     }

    //     if (actions != null) {
    //         JPanel panel = createActionPanel(row);
    //         labels.add(panel);
    //         dataPanel.add(panel, "gapbottom 2,grow,cell " + headers.length + " " + (row - 1));
    //     }

    //     rowLabels.put(row, labels);
    //     this.data.add(rowData); // Đồng bộ với data của CustomTable

    //     if (row >= rowHeights.length) {
    //         int[] newRowHeights = new int[row + 1];
    //         System.arraycopy(rowHeights, 0, newRowHeights, 0, rowHeights.length);
    //         for (int i = rowHeights.length; i < newRowHeights.length; i++) {
    //             newRowHeights[i] = 30;
    //         }
    //         rowHeights = newRowHeights;
    //     }
    //     updateRowConstraints();
    //     dataPanel.setPreferredSize(null);
    //     dataPanel.revalidate();
    //     dataPanel.repaint();
    //     revalidate();
    //     repaint();
    // }

    @Override
    public void setSelectedRow(int row) {
        super.setSelectedRow(row);
    }
    // @Override
    // public String getCellData(int row, int column){// chưa xây dựng
    //     if(column == 0){

    //     }

    //     String result = ((JLabel)rowLabels.get(row).get(column)).getText();
    //     return(result);
    // }

    public String getCellData(int row, int column) {
        Component comp = rowLabels.get(row).get(column);
        if (comp instanceof JPanel) {
            Component innerComp = ((JPanel) comp).getComponent(0);
            if (innerComp instanceof JTextField) {
                String text = ((JTextField) innerComp).getText();
                return text.isEmpty() ? "0" : text; // Tránh chuỗi rỗng
            } else if (innerComp instanceof CustomTextFieldSL) {
                String text = ((CustomTextFieldSL) innerComp).getText();
                return text.isEmpty() ? "1" : text; // Giá trị mặc định cho số lượng
            } else if (innerComp instanceof JLabel) {
                String text = ((JLabel) innerComp).getText();
                return text.isEmpty() ? "0" : text; // Tránh chuỗi rỗng
            }
        }
        return "0"; // Giá trị mặc định nếu không xác định được
    }

    @Override
    public void updateRowData(int row, String[] newData) {
        if (!rowLabels.containsKey(row)) {
            return; // Hàng không tồn tại
        }
        if (newData == null || newData.length == 0) {
            return; // Dữ liệu mới không hợp lệ
        }

        List<Component> rowComponents = rowLabels.get(row);
        for (int i = 0; i < Math.min(newData.length, headers.length); i++) {
            Component comp = rowComponents.get(i);
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                Component innerComp = panel.getComponent(0); // Lấy thành phần bên trong panel
                String value = newData[i] != null ? newData[i] : "";
                
                if (innerComp instanceof JTextField) {
                    ((JTextField) innerComp).setText(value);
                } else if (innerComp instanceof CustomTextFieldSL) {
                    try {
                        ((CustomTextFieldSL) innerComp).setText(value);
                    } catch (IllegalArgumentException e) {
                        ((CustomTextFieldSL) innerComp).setText("0"); // Giá trị mặc định nếu không hợp lệ
                    }
                } else if (innerComp instanceof JLabel) {
                    ((JLabel) innerComp).setText(value);
                }
            }
        }

        // Cập nhật dữ liệu trong mảng data
        if (row < data.size()) {
            data.set(row, newData);
        }

        // Cập nhật giao diện
        dataPanel.revalidate();
        dataPanel.repaint();
    }

    // @Override
    // public void updateRowData(int row, String[] newData) {
    //     if (!rowLabels.containsKey(row)) {
    //         return;
    //     }
    //     if (newData == null || newData.length == 0) {
    //         return;
    //     }

    //     List<Component> rowComponents = rowLabels.get(row);
    //     for (int i = 0; i < Math.min(newData.length, headers.length); i++) {
    //         Component comp = rowComponents.get(i);
    //         if (comp instanceof JPanel) {
    //             JPanel panel = (JPanel) comp;
    //             Component innerComp = panel.getComponent(0);
    //             String value = newData[i] != null ? newData[i] : "";
                
    //             if (innerComp instanceof JTextField) {
    //                 ((JTextField) innerComp).setText(value);
    //             } else if (innerComp instanceof CustomTextFieldSL) {
    //                 try {
    //                     ((CustomTextFieldSL) innerComp).setText(value.isEmpty() ? "1" : value);
    //                 } catch (IllegalArgumentException e) {
    //                     ((CustomTextFieldSL) innerComp).setText("1");
    //                 }
    //             } else if (innerComp instanceof JLabel) {
    //                 ((JLabel) innerComp).setText(value);
    //             }
    //         }
    //     }

    //     if (row - 1 < data.size()) {
    //         data.set(row - 1, newData);
    //     } else {
    //         // Đảm bảo data đủ kích thước
    //         while (data.size() < row - 1) {
    //             data.add(new String[headers.length]);
    //         }
    //         data.add(newData);
    //     }

    //     dataPanel.revalidate();
    //     dataPanel.repaint();
    // }

    public CustomTextFieldSL getTextFieldSL(int row){
        CustomTextFieldSL textField = null;
        for(int i = 0; i < headers.length; i++){
            if(headers[i].equals("Số lượng")){
                Component cpn = rowLabels.get(row).get(i);
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

}
