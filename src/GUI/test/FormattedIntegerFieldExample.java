package GUI.test;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class FormattedIntegerFieldExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Formatted JTextField");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        // Tạo NumberFormatter cho số nguyên
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false); // Không hiển thị dấu phân tách nghìn

        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0); // Số nhỏ nhất có thể nhập
        formatter.setMaximum(1000); // Số lớn nhất có thể nhập
        formatter.setAllowsInvalid(false); // Không cho phép nhập ký tự không hợp lệ
        formatter.setCommitsOnValidEdit(true); // Tự động cập nhật giá trị khi hợp lệ

        // Tạo JFormattedTextField
        JFormattedTextField intField = new JFormattedTextField(formatter);
        intField.setColumns(10); // Độ rộng của JTextField
        intField.setValue(0); // Giá trị mặc định
        intField.setHorizontalAlignment(SwingConstants.RIGHT);

        frame.add(new JLabel("Nhập số nguyên:"));
        frame.add(intField);

        frame.setVisible(true);
    }
}

