package GUI.component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TableNoTouch extends CustomTable {

    public TableNoTouch(ArrayList<String[]> data, String... headers) {
        super(data, null, headers);
    }

    @Override
    protected void addColHanhDong() {
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

    @Override
    public void setMinimumSize(Dimension minimumSize) {
        super.setMinimumSize(minimumSize);
    }
}
