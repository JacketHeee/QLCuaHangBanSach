package GUI.component;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;

import com.formdev.flatlaf.FlatClientProperties;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.LineBorder;

public class InvoiceTable extends CustomTable {
    public InvoiceTable(ArrayList<String[]> data, String[][] actions, String... headers) {
        super(data, actions, headers);
        // Gọi constructor của lớp cha CustomTable
        // MigLayout layout = (MigLayout)getDataPanel().getLayout();
        // layout.setLayoutConstraints(layout.getLayoutConstraints()+",gap 2");
    }

    @Override
    public void setDataPanelPre() {
        dataPanel.setPreferredSize(null);
        // dataPanel.setPreferredSize(new Dimension(headers.length * 150, rowLabels.size() * 30));
    }

    public JPanel createDataInput(String text, int row, int columnIndex) { // Thêm tham số columnIndex
        // Chuẩn hóa text khi null
        String displayText = (text == null) ? "" : text;
        JPanel panel = new JPanel(new MigLayout("insets 0,al center center"));
        

        // Cột đầu tiên (index 0) là JTextField
        if (columnIndex == 0) {
            JTextField textField = new JTextField(displayText);
            textField.putClientProperty(FlatClientProperties.STYLE, "focusWidth: 0; innerFocusWidth:0");
            textField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mã sách");
            textField.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(textField,"push,growx");
        }

        else if (columnIndex == 2) {
            NumberFormatter formatter = new NumberFormatter(NumberFormat.getIntegerInstance());
            formatter.setAllowsInvalid(false);
            JFormattedTextField textField = new JFormattedTextField(formatter); //ngăn chặn nhập ký tự khác
            formatter.setCommitsOnValidEdit(true); //cập nhật ngay khi hợp lệ
            // textField.setText("1");
            textField.putClientProperty(FlatClientProperties.STYLE, "focusWidth: 0; innerFocusWidth:0");
            textField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Số lượng");
            textField.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(textField,"push,growx");
        }
        
        // Các cột khác vẫn là JLabel
        else {
            JLabel label = new JLabel(displayText);
            // label.setBorder(new javax.swing.border.EmptyBorder(10, 5, 10, 5));
            int height = 40;
            label.setPreferredSize(new Dimension(label.getWidth(),height));
            label.setMinimumSize(new Dimension(label.getWidth(),height));
            label.setMaximumSize(new Dimension(label.getWidth(),height));
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

        for (int i = 0; i <headers.length ; i++) {
            JPanel label = createDataInput(rowData[i], row,i);
            labels.add(label);
            dataPanel.add(label, "gapbottom 2,grow,cell " + i + " " + (row));
        }

        System.out.print(labels.size());

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
}
