package GUI.test;

import javax.swing.*;

import com.formdev.flatlaf.FlatIntelliJLaf;

import GUI.component.CustomScrollPane;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestCustomTable extends JFrame {

    public TestCustomTable() {
        setTitle("Test Custom Table");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] headers = {"Tên", "Tuổi", "Địa chỉ", "Điểm"};
        ArrayList<String[]> dataList = new ArrayList<>(List.of(
            new String[]{"Nguyễn Văn A", "25", "Hà Nội", "8.5"},
            new String[]{"Trần Thị B", "30", "TP.HCM", "9.0"},
            new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"}
        ));
        ArrayList<String[]> data = new ArrayList<>(List.of(
        ));

        String[][] actions = {
            {"edit.svg","edit"},
            {"detail.svg","detail"},
            {"remove.svg","remove"}
        };
        
        CustomTableExample tablePanel = new CustomTableExample(dataList,actions,headers);
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});
        tablePanel.addDataRow(new String[]{"Lê Văn C", "22", "Đà Nẵng", "7.5"});

        // // tablePanel.setHeaderBackground(new Color(173, 216, 230));
        // tablePanel.setColumnWidth(0, 20);
        // tablePanel.setColumnWidth(1, 200);
        // tablePanel.setRowHeight(0, 40);
        // tablePanel.setRowHeight(2, 50);

        setSize(1000,600);
        add(new CustomScrollPane(tablePanel));
        // pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        SwingUtilities.invokeLater(() -> {
            new TestCustomTable().setVisible(true);
        });
    }
}