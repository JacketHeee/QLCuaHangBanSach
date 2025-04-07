package GUI.forms.thongke;

import java.awt.*;
import javax.swing.*;

import GUI.component.CustomScrollPane;
import GUI.component.CustomSubTab;
import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import resources.base.baseTheme;

public class DoanhThuForm extends JPanel implements ActionListener{

    private JPanel mainPanel; 
    private JPanel hPanel;

    private JButton butThongKeTheoNam;
    private JButton butThongKeTheoThang;
    private JButton butThongKeTheoNgay;
    private JButton butThongKeTuNgayDenNgay;

    public DoanhThuForm() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("insets 0,gap 0"));

        hPanel = tabTime();
        add(hPanel,"pushx,growx,wrap"); 

        mainPanel = new JPanel(new MigLayout("insets 0"));
        mainPanel.add(new JButton("jhihi"));
        add(mainPanel,"push,grow");

        setPanel(new ThongKeTheoNam());
    }

    private JPanel tabTime() {
        JPanel panel = new JPanel(new MigLayout("insets 0,gap 0"));

        butThongKeTheoNam = new CustomSubTab("Thống kê theo năm");
        butThongKeTheoThang = new CustomSubTab("Thống kê từng tháng trong năm");
        butThongKeTheoNgay = new CustomSubTab("Thống kê từng ngày trong tháng");
        butThongKeTuNgayDenNgay = new CustomSubTab("Thống kê từ ngày đến ngày");

        butThongKeTheoNam.setActionCommand("tkNam");
        butThongKeTheoThang.setActionCommand("tkThang");
        butThongKeTheoNgay.setActionCommand("tkNgay");
        butThongKeTuNgayDenNgay.setActionCommand("tkNgayDenNgay");

        butThongKeTheoNam.setSelected(true);

        butThongKeTheoNam.addActionListener(this);
        butThongKeTheoThang.addActionListener(this);
        butThongKeTheoNgay.addActionListener(this);
        butThongKeTuNgayDenNgay.addActionListener(this);
        

        panel.add(butThongKeTheoNam,"growy");
        panel.add(butThongKeTheoThang,"growy");
        panel.add(butThongKeTheoNgay,"growy");
        panel.add(butThongKeTuNgayDenNgay,"growy");

        return panel;
    }

    public void setPanel(Component panel) {
        mainPanel.removeAll();
        mainPanel.add(panel,"push, grow");
        mainPanel.repaint();
        mainPanel.validate();
    }


    private void setTabDefault() {
        CustomSubTab but;

        for (Component x : hPanel.getComponents()) {
            but = (CustomSubTab) x;
            but.setDefault(baseTheme.backgroundColor,baseTheme.textColor);
        }
    }


     @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        setTabDefault();

        CustomSubTab but;
        switch (e.getActionCommand()) {
            case "tkNam":
                but = (CustomSubTab) e.getSource();
                but.setSelected(true);
                setPanel(new ThongKeTheoNam());
                break;
            case "tkThang":
                but = (CustomSubTab) e.getSource();
                but.setSelected(true);
                setPanel(new ThongKeTungThang());
                break;
            case "tkNgay":
                but = (CustomSubTab) e.getSource();
                but.setSelected(true);
                setPanel(new ThongKeTungNgay());
                break;
            case "tkNgayDenNgay":
                but = (CustomSubTab) e.getSource();
                but.setSelected(true);
                setPanel(new ThongKeTungNgayDenNgay());
                break;
        
            default:
                break;
        }
        
    }


    
}
