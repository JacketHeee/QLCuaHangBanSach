package GUI.forms.thongke;

import javax.swing.*;

import com.formdev.flatlaf.FlatClientProperties;

import GUI.component.TableNoTouch;
import GUI.component.chart.TableNoTouchNoScroll;
import net.miginfocom.swing.MigLayout;
import raven.chart.data.pie.DefaultPieDataset;
import raven.chart.pie.PieChart;
import resources.base.baseTheme;

import java.awt.*;


import java.util.ArrayList;
import java.util.Arrays;

public class NhapHang extends JPanel {
    
    public NhapHang() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("insets 0 0 20 0, gap 30"));

        addHeader();
        add(new JLabel("<html><b><font size='+3' color='#000000'>Thống kê nhập hàng trong quý</font></b></html>"),"pushx,al center,wrap,gaptop 30");
        addNhapTheoDauSach();
        addTongQuan();
        addChiTietNhap();
    }

    private void addHeader() {
        JPanel panel = new JPanel(new MigLayout("insets 10, al center center, gap 5"));
        panel.setBackground(Color.decode(baseTheme.selectedButton));
        panel.add(new JLabel("Từ ngày"));
        panel.add(new JTextField(10));
        panel.add(new JLabel("Đến ngày"));
        panel.add(new JTextField(10));
        panel.add(new JLabel("Nhà cung cấp "));
        panel.add(new JComboBox<>(new String[] {"Tất cả","Nguyễn Hùng Mạnh","Strong man","Cá thể vũ trụ quên neft sức mạnh"}));

        panel.add(new JButton("Lọc"),"sg 1");
        panel.add(new JButton("Làm mới"),"sg 1");
        panel.add(new JButton("Xuất Excel"),"sg 1");
        
        add(panel,"pushx,grow,wrap");
    }

    private void addTongQuan() {
        JPanel panel = new JPanel(new MigLayout("insets 10, wrap 1,gap 15, al center center"));
        // panel.setBackground(Color.white);

        panel.add(new JLabel("<html><b><font size='+3' color='#008951'><u>TỔNG QUAN</u></font></b></html>"));
        panel.add(new JLabel(String.format("<html><b><font size='+1'>Tổng số lần nhập: %d</font></b></html>",100)));
        panel.add(new JLabel(String.format("<html><b><font size='+1'>Tổng số lượng sách nhập: %d</font></b></html>",1000)));
        panel.add(new JLabel(String.format("<html><b><font size='+1'>Tổng tiền nhập: %d</font></b></html>",100000000)));
        
        add(panel,"gaptop 110, pushx, grow,wrap");
    }

    ArrayList<String[]> datas = new ArrayList<>(Arrays.asList(
        new String[] {"PN01", "00:00:00 23/07/2024", "Strong bef bef", "1000","100.000.000₫"},
        new String[] {"PN01", "00:00:00 23/07/2024", "Strong bef bef", "1000","100.000.000₫"},
        new String[] {"PN01", "00:00:00 23/07/2024", "Strong bef bef", "1000","100.000.000₫"},
        new String[] {"PN01", "00:00:00 23/07/2024", "Strong bef bef", "1000","100.000.000₫"},
        new String[] {"PN01", "00:00:00 23/07/2024", "Strong bef bef", "1000","100.000.000₫"},
        new String[] {"PN01", "00:00:00 23/07/2024", "Strong bef bef", "1000","100.000.000₫"},
        new String[] {"PN01", "00:00:00 23/07/2024", "Strong bef bef", "1000","100.000.000₫"},
        new String[] {"PN01", "00:00:00 23/07/2024", "Strong bef bef", "1000","100.000.000₫"},
        new String[] {"PN01", "00:00:00 23/07/2024", "Strong bef bef", "1000","100.000.000₫"},
        new String[] {"PN01", "00:00:00 23/07/2024", "Strong bef bef", "1000","100.000.000₫"},
        new String[] {"PN01", "00:00:00 23/07/2024", "Strong bef bef", "1000","100.000.000₫"},
        new String[] {"PN01", "00:00:00 23/07/2024", "Strong bef bef", "1000","100.000.000₫"},
        new String[] {"PN01", "00:00:00 23/07/2024", "Strong bef bef", "1000","100.000.000₫"},
        new String[] {"PN01", "00:00:00 23/07/2024", "Strong bef bef", "1000","100.000.000₫"},
        new String[] {"PN01", "00:00:00 23/07/2024", "Strong bef bef", "1000","100.000.000₫"},
        new String[] {"PN01", "00:00:00 23/07/2024", "Strong bef bef", "1000","100.000.000₫"},
        new String[] {"PN01", "00:00:00 23/07/2024", "Strong bef bef", "1000","100.000.000₫"}
    ));

    private void addChiTietNhap() {
        JPanel panel = new JPanel(new MigLayout("wrap,insets 0,gap 10"));
        panel.add(new JLabel("<html><b><font size='+1'>Bảng chi tiết nhập hàng theo kỳ</font></b></html>"));
        panel.add(new TableNoTouchNoScroll(datas, "Mã phiếu","Ngày nhập","NCC","Số lượng","Tổng tiền"),"pushx,grow");
        
        JPanel panelAction = new JPanel(new MigLayout("al right"));
        panelAction.add(new JButton("Xuất Excel"),"sg 1");
        panelAction.add(new JButton("In báo cáo"),"sg 1");

        panel.add(panelAction,"pushx,grow");
        add(panel,"gaptop 50, pushx, grow,wrap");
    }

    ArrayList<String[]> dataSach = new ArrayList<>(Arrays.asList(
        new String[] {"Dế mèn phiêu lưu ký","100","100.000.000","100.000.000.000"},
        new String[] {"Cuộc đời là những ước mơ","100","100.000.000","100.000.000.000"},
        new String[] {"Thiên thượng thiên hạ","100","100.000.000","100.000.000.000"},
        new String[] {"Thế nào là 1 con sói đầu đàn","100","100.000.000","100.000.000.000"},
        new String[] {"Ai chẳng có ước mơ","100","100.000.000","100.000.000.000"},
        new String[] {"Hãy cứ xông pha","100","100.000.000","100.000.000.000"},
        new String[] {"Càng trì hoãn, càng ngu","100","100.000.000","100.000.000.000"},
        new String[] {"Sức mạnh của giấc ngủ","100","100.000.000","100.000.000.000"},
        new String[] {"Giấc mơ trưa","100","100.000.000","100.000.000.000"},
        new String[] {"Dế mèn phiêu lưu ký","100","100.000.000","100.000.000.000"},
        new String[] {"Tạo hóa quên neft Hùng Mạnh","100","100.000.000","100.000.000.000"}
    ));



    private void addNhapTheoDauSach() {
        JPanel panel = new JPanel(new MigLayout("insets 0, gap 20"));
        panel.add(new JLabel("<html><b><font size='+1'>Bảng Top 10 sách nhập nhiều nhất</font></b></html>"),"wrap");
        panel.add(new TableNoTouchNoScroll(dataSach, "Tên sách","Số lượng nhập","Giá nhập","Thành tiền"),"pushx,grow,gapright 10");
        
        panel.add(pieChart(),"pushx,grow");
        add(panel,"gaptop 50, pushx, grow,wrap");

    }

    private PieChart pieChart() {
        PieChart pieChart1 = new PieChart();
        // pieChart1.setBackground(Color.white);
        JLabel header1 = new JLabel("<html><font><b>Top 5 sách nhập nhiều nhất</b></font></html>",JLabel.CENTER);
        header1.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:+1");
        pieChart1.setHeader(header1);
        pieChart1.getChartColor().addColor(Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"), Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"), Color.decode("#818cf8"), Color.decode("#c084fc"));
        pieChart1.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");
        pieChart1.setDataset(createPieData());
        // add(pieChart1, "split 3,height 2
        return pieChart1;
    }

    private DefaultPieDataset createPieData() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.addValue("Hãy cứ xông pha", (int)(Math.random() * 1000));
        dataset.addValue("Tạo hóa quên neft Hùng Mạnh", (int)(Math.random() * 1000));
        dataset.addValue("Ai chẳng có ước mơ", (int)(Math.random() * 1000));
        dataset.addValue("Từ chối giá như", (int)(Math.random() * 1000));
        dataset.addValue("Con sói đầu đàn", (int)(Math.random() * 1000));
        dataset.addValue("Các sách khác", 30);
        return dataset;
    }
    
}
