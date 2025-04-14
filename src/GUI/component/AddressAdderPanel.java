package GUI.component;

import DTO.ModelTinhThanh.AdministrativeData;
import DTO.ModelTinhThanh.District;
import DTO.ModelTinhThanh.JsonParser;
import DTO.ModelTinhThanh.Province;
import DTO.ModelTinhThanh.Ward;
import net.miginfocom.swing.MigLayout;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class AddressAdderPanel extends JPanel {
    private JComboBox<Province> provinceComboBox;
    private JComboBox<District> districtComboBox;
    private JComboBox<Ward> wardComboBox;
    private JTextField streetTextField;
    private JTextArea resultArea;
    private JButton addButton;
    private JButton clearButton;

    private AdministrativeData adminData;
    private ArrayList<String> savedAddresses;
    private Consumer<String> onAddressAdded; // Callback khi thêm địa chỉ

    public AddressAdderPanel() {
        savedAddresses = new ArrayList<>();
        setPreferredSize(new Dimension(550, 450));
        setMinimumSize(new Dimension(550, 450));
        setMaximumSize(new Dimension(550, 450));
        
        loadJsonData();
        initializeUI();
        setupListeners();
    }

    public void setOnAddressAdded(Consumer<String> callback) {
        this.onAddressAdded = callback;
    }

    private void loadJsonData() {
        JsonParser parser = new JsonParser();
        adminData = parser.parseJson();
        if (adminData == null || adminData.getData() == null) {
            JOptionPane.showMessageDialog(this, "Không thể tải dữ liệu JSON!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeUI() {
        setLayout(new MigLayout("wrap 2, fill, insets 20", "[right][grow]", "[]10[]10[]10[]10[]10[][grow]"));
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(255, 87, 34)), 
                "Thêm Địa Chỉ Mới",
                0, 0, new Font(FlatRobotoFont.FAMILY, Font.BOLD, 16), new Color(255, 87, 34)));
        setBackground(new Color(250, 250, 250));

        JLabel provinceLabel = new JLabel("Tỉnh/Thành phố:");
        provinceLabel.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 15));
        provinceLabel.setForeground(new Color(50, 50, 50));
        add(provinceLabel, "right");

        provinceComboBox = new JComboBox<>();
        provinceComboBox.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 14));
        provinceComboBox.setBackground(Color.WHITE);
        provinceComboBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        if (adminData != null && adminData.getData() != null) {
            for (Province province : adminData.getData()) {
                provinceComboBox.addItem(province);
            }
        }
        add(provinceComboBox, "growx, sg input");

        JLabel districtLabel = new JLabel("Quận/Huyện:");
        districtLabel.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 15));
        districtLabel.setForeground(new Color(50, 50, 50));
        add(districtLabel, "right");

        districtComboBox = new JComboBox<>();
        districtComboBox.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 14));
        districtComboBox.setBackground(Color.WHITE);
        districtComboBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        districtComboBox.setEnabled(false);
        add(districtComboBox, "growx, sg input");

        JLabel wardLabel = new JLabel("Phường/Xã:");
        wardLabel.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 15));
        wardLabel.setForeground(new Color(50, 50, 50));
        add(wardLabel, "right");

        wardComboBox = new JComboBox<>();
        wardComboBox.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 14));
        wardComboBox.setBackground(Color.WHITE);
        wardComboBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        wardComboBox.setEnabled(false);
        add(wardComboBox, "growx, sg input");

        JLabel streetLabel = new JLabel("Số nhà, Đường:");
        streetLabel.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 15));
        streetLabel.setForeground(new Color(50, 50, 50));
        add(streetLabel, "right");

        streetTextField = new JTextField(12);
        streetTextField.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 14));
        streetTextField.setBackground(Color.WHITE);
        streetTextField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        streetTextField.setEnabled(false);
        streetTextField.setText("Ví dụ: 123 Đường Láng");
        streetTextField.setForeground(Color.GRAY);
        streetTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (streetTextField.getText().equals("Ví dụ: 123 Đường Láng")) {
                    streetTextField.setText("");
                    streetTextField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (streetTextField.getText().isEmpty()) {
                    streetTextField.setText("Ví dụ: 123 Đường Láng");
                    streetTextField.setForeground(Color.GRAY);
                }
            }
        });
        add(streetTextField, "growx, sg input");

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(255, 87, 34));
        add(separator, "span 2, growx, gapbottom 10");

        JLabel resultLabel = new JLabel("Địa chỉ:");
        resultLabel.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 15));
        resultLabel.setForeground(new Color(50, 50, 50));
        add(resultLabel, "span 2, growx");

        resultArea = new JTextArea(3, 20);
        resultArea.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBackground(new Color(245, 245, 245));
        resultArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        add(new JScrollPane(resultArea), "span 2, growx, hmin 80");

        JPanel buttonPanel = new JPanel(new MigLayout("fill", "[center][center]", ""));
        addButton = new JButton("Thêm");
        addButton.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 15));
        addButton.setBackground(new Color(255, 87, 34));
        addButton.setForeground(Color.WHITE);
        addButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addButton.setBackground(new Color(255, 107, 54));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addButton.setBackground(new Color(255, 87, 34));
            }
        });

        clearButton = new JButton("Xóa");
        clearButton.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 15));
        clearButton.setBackground(new Color(200, 200, 200));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                clearButton.setBackground(new Color(180, 180, 180));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                clearButton.setBackground(new Color(200, 200, 200));
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, "span 2, center, gaptop 10");
    }

    private void setupListeners() {
        provinceComboBox.addActionListener(e -> updateDistrictComboBox());
        districtComboBox.addActionListener(e -> updateWardComboBox());
        wardComboBox.addActionListener(e -> updateStreetFieldAndResultArea());
        streetTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateResultArea();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateResultArea();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateResultArea();
            }
        });

        addButton.addActionListener(e -> {
            String address = resultArea.getText();
            if (address.isEmpty() || provinceComboBox.getSelectedItem() == null ||
                districtComboBox.getSelectedItem() == null || wardComboBox.getSelectedItem() == null || streetTextField.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ tỉnh, huyện, xã và điền số nhà, đường đi!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            } else {
                savedAddresses.add(address);
                if (onAddressAdded != null) {
                    JOptionPane.showMessageDialog(this, "Đã thêm địa chỉ: " + address, "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    onAddressAdded.accept(address);
                }
                
            }
        });

        clearButton.addActionListener(e -> clearSelections());
    }

    private void updateDistrictComboBox() {
        districtComboBox.removeAllItems();
        Province selectedProvince = (Province) provinceComboBox.getSelectedItem();
        if (selectedProvince != null && selectedProvince.getLevel2s() != null) {
            for (District district : selectedProvince.getLevel2s()) {
                districtComboBox.addItem(district);
            }
            districtComboBox.setEnabled(true);
        } else {
            districtComboBox.setEnabled(false);
            wardComboBox.removeAllItems();
            wardComboBox.setEnabled(false);
            streetTextField.setEnabled(false);
            streetTextField.setText("Ví dụ: 123 Đường Láng");
            streetTextField.setForeground(Color.GRAY);
            resultArea.setText("");
        }
    }

    private void updateWardComboBox() {
        wardComboBox.removeAllItems();
        District selectedDistrict = (District) districtComboBox.getSelectedItem();
        if (selectedDistrict != null && selectedDistrict.getLevel3s() != null) {
            for (Ward ward : selectedDistrict.getLevel3s()) {
                wardComboBox.addItem(ward);
            }
            wardComboBox.setEnabled(true);
        } else {
            wardComboBox.setEnabled(false);
            streetTextField.setEnabled(false);
            streetTextField.setText("Ví dụ: 123 Đường Láng");
            streetTextField.setForeground(Color.GRAY);
            resultArea.setText("");
        }
    }

    private void updateStreetFieldAndResultArea() {
        Province province = (Province) provinceComboBox.getSelectedItem();
        District district = (District) districtComboBox.getSelectedItem();
        Ward ward = (Ward) wardComboBox.getSelectedItem();
        if (province != null && district != null && ward != null) {
            streetTextField.setEnabled(true);
            updateResultArea();
        } else {
            streetTextField.setEnabled(false);
            streetTextField.setText("Ví dụ: 123 Đường Láng");
            streetTextField.setForeground(Color.GRAY);
            resultArea.setText("");
        }
    }

    private void updateResultArea() {
        Province province = (Province) provinceComboBox.getSelectedItem();
        District district = (District) districtComboBox.getSelectedItem();
        Ward ward = (Ward) wardComboBox.getSelectedItem();
        String street = streetTextField.getText().trim();
        if (street.equals("Ví dụ: 123 Đường Láng")) {
            street = "";
        }

        if (province != null && district != null && ward != null) {
            String baseAddress = String.format("%s, %s, %s",
                    ward.getName(), district.getName(), province.getName());
            if (!street.isEmpty()) {
                resultArea.setText(String.format("%s, %s", street, baseAddress));
            } else {
                resultArea.setText(baseAddress);
            }
        } else {
            resultArea.setText("");
        }
    }

    private void clearSelections() {
        provinceComboBox.setSelectedIndex(-1);
        districtComboBox.removeAllItems();
        districtComboBox.setEnabled(false);
        wardComboBox.removeAllItems();
        wardComboBox.setEnabled(false);
        streetTextField.setEnabled(false);
        streetTextField.setText("Ví dụ: 123 Đường Láng");
        streetTextField.setForeground(Color.GRAY);
        resultArea.setText("");
    }

    public ArrayList<String> getSavedAddresses() {
        return savedAddresses;
    }
}