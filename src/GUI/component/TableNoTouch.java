package GUI.component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class TableNoTouch extends CustomTable {

    public TableNoTouch(ArrayList<String[]> data, String... headers) {
        super(data, null, headers);
    }

    public TableNoTouch(ArrayList<String[]> data, OnSelectRowListener onSelectRowListener, String... headers) {
        super(data, null,onSelectRowListener, headers);
    }

    @Override
    protected void addColHanhDong() {
    }
    
    public void addDataRow(String[] data) {
        int row = rowLabels.size() + 1;
        ArrayList<JComponent> labels = new ArrayList<>();

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
                    // Tìm chỉ số hàng hiện tại của JLabel trong rowLabels
                    int currentRow = -1;
                    for (Map.Entry<Integer, List<JComponent>> entry : rowLabels.entrySet()) {
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
