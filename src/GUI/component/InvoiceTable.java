package GUI.component;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.text.NumberFormatter;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.SachBUS;
import DTO.SachDTO;
import GUI.component.search.TextFieldListSach;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

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

    public JPanel createDataInput(String text, int row, int columnIndex) { // Thêm tham số columnIndex
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
            JFormattedTextField textFieldSL = new JFormattedTextField();
            NumberFormatter formatter = new NumberFormatter(NumberFormat.getIntegerInstance());
            formatter.setAllowsInvalid(false);
            textFieldSL = new JFormattedTextField(formatter); //ngăn chặn nhập ký tự khác
            formatter.setCommitsOnValidEdit(true); //cập nhật ngay khi hợp lệ
            textFieldSL.setText("1"); // để tạm mốt format lại
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
        label.setPreferredSize(new Dimension(150, 30));
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
        int row = rowLabels.size();
    
        ArrayList<Component> labels = new ArrayList<>();

        // / Xử lý trường hợp data là null
        String[] rowData = (data == null) ? new String[headers.length] : data;
        if (data == null) {
            for (int i = 0; i < rowData.length; i++) {
                rowData[i] = ""; // Điền giá trị rỗng cho dòng trống
            }
        }

        //Chỗ này chèn vào Panel
        for (int i = 0; i <headers.length ; i++) {
            JPanel label = createDataInput(rowData[i], row,i);
            labels.add(label);
            dataPanel.add(label, "gapbottom 2,grow,cell " + i + " " + (row));
        }

        // System.out.print(labels.size());

        JPanel panel = createActionPanel(row);
        labels.add(panel);
        dataPanel.add(panel, "gapbottom 2,grow,wrap");

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
        repaint();
        revalidate();
    }

    @Override
    public void setSelectedRow(int row) {
        super.setSelectedRow(row);
    }
    @Override
    public String getCellData(int row, int column){// chưa xây dựng
        if(column == 0){

        }

        String result = ((JLabel)rowLabels.get(row).get(column)).getText();
        return(result);
    }
    // @Override
    // public void setCellData(int row, int column, String text){
    //     Component temp = getComponentFromPanel((JPanel)rowLabels.get(row).get(column));
    //     if(column == 0){
    //         TextFieldListSach textField = (TextFieldListSach)(temp);
    //         textField.setText(text);
    //     }
    //     else if(column == 2){
    //         JFormattedTextField textField = (JFormattedTextField)(temp);
    //         textField.setText(text);
    //     }
    //     else{
    //         JLabel label = (JLabel)(temp);
    //         label.setText(text);
    //     }
    // }
    // @Override
    // public void setRowData(int row, String... list){
    //     for(int i = 0; i < list.length; i++){
    //         setCellData(row, i, list[i]);
    //     }
    // }

    // public Component getComponentFromPanel(JPanel panel){
    //     Component[] cpn = panel.getComponents();
    //     for(Component i : cpn){
    //         //trong table chỉ có TextFieldListSach, JLabel, JFormattedTextField
    //         if(i instanceof JLabel || i instanceof TextFieldListSach || i instanceof JFormattedTextField){
    //             return(i);
    //         }
    //     }
    //     return(null);
    // }

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
                } else if (innerComp instanceof JFormattedTextField) {
                    try {
                        ((JFormattedTextField) innerComp).setText(value);
                    } catch (IllegalArgumentException e) {
                        ((JFormattedTextField) innerComp).setText("0"); // Giá trị mặc định nếu không hợp lệ
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

    public JFormattedTextField getTextFieldSL(int row){
        JFormattedTextField textField = null;
        for(int i = 0; i < headers.length; i++){
            if(headers[i].equals("Số lượng")){
                Component cpn = rowLabels.get(row).get(i);
                JPanel panel = (JPanel)cpn;
                textField = (JFormattedTextField)panel.getComponent(0);
                break;
            }
        }
        return(textField);
    }

    public int getSoLuongSach(int row){
        int result = -1;
        JFormattedTextField textField = getTextFieldSL(row);
        result = Integer.parseInt(textField.getText());
        return(result);
    }

    public void setListenerTextFieldSL(JFormattedTextField textFieldSL, int row){
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
    //     JFormattedTextField textFieldSL = getTextFieldSL(row);
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
