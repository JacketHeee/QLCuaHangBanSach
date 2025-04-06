package GUI.forms.thongke;

import javax.swing.JButton;
import javax.swing.JPanel;

import GUI.component.CustomButtonTab;
import GUI.component.CustomScrollPane;
import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Component;

public class TongQuanForm extends JPanel implements ActionListener{
    private CustomButtonTab butTongQuan;
    private CustomButtonTab butDoanhThu;
    private CustomButtonTab butNhapHang;
    private CustomButtonTab butTonKho;

    private JPanel mainPanel;
    private JPanel headJPanel;


    public TongQuanForm() {
        init();
    }


    

    private void init() {
        setLayout(new MigLayout("gap 0"));
        
        headJPanel = new JPanel(new MigLayout("insets 2,gap 0"));
        // headJPanel.setBorder(new MatteBorder(0,0,1,0,Color.decode("#e8e8e8")));
        initButtontab();

        add(headJPanel,"pushx,growx,wrap");
        
        mainPanel = new JPanel(new MigLayout("insets 0"));
        add(mainPanel,"push,grow");

        setPanel(new TongQuan());
    }

    private void initButtontab() {
        butTongQuan = new CustomButtonTab("Tổng quan");
        butDoanhThu = new CustomButtonTab("Doanh thu");
        butNhapHang = new CustomButtonTab("Nhập hàng");
        butTonKho = new CustomButtonTab("Tồn kho");

        butTongQuan.setActionCommand("tongquan");
        butDoanhThu.setActionCommand("doanhthu");
        butNhapHang.setActionCommand("nhaphang");
        butTonKho.setActionCommand("tonkho");

        butTongQuan.setSelected(true);

        butTongQuan.addActionListener(this);
        butDoanhThu.addActionListener(this);
        butNhapHang.addActionListener(this);
        butTonKho.addActionListener(this);
        

        headJPanel.add(butTongQuan,"growy");
        headJPanel.add(butDoanhThu,"growy");
        headJPanel.add(butNhapHang,"growy");
        headJPanel.add(butTonKho,"growy");

    }

    

    public void setPanel(Component panel) {
        mainPanel.removeAll();
        mainPanel.add(panel,"push, grow");
        mainPanel.repaint();
        mainPanel.validate();
    }


    private void setTabDefault() {
        CustomButtonTab but;

        for (Component x : headJPanel.getComponents()) {
            but = (CustomButtonTab) x;
            but.setDefault(baseTheme.backgroundColor,baseTheme.textColor);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        setTabDefault();

        CustomButtonTab but;
        switch (e.getActionCommand()) {
            case "tongquan":
                but = (CustomButtonTab) e.getSource();
                but.setSelected(true);
                setPanel(new TongQuan());
                break;
            case "doanhthu":
                but = (CustomButtonTab) e.getSource();
                but.setSelected(true);
                setPanel(new DoanhThuForm());
                break;
            case "nhaphang":
                but = (CustomButtonTab) e.getSource();
                but.setSelected(true);
                setPanel(new CustomScrollPane(new NhapHang()));
                break;
            case "tonkho":
                but = (CustomButtonTab) e.getSource();
                but.setSelected(true);
                setPanel(new TonKho());
                break;
        
            default:
                break;
        }
        
    }

    
}
