package GUI.forms.thongke;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.HoaDonBUS;
import BUS.ThongKeBUS.ThongKeDoanhThuBUS;
import DTO.HoaDonDTO;
import DTO.ThongKe.ThongKeDoanhThuDTO;
import GUI.MainFrame;
import GUI.component.CustomBoldJLabel;
import GUI.component.CustomButton;
import GUI.component.CustomTable;
import GUI.component.InputFormItem;
import GUI.component.LabelTongQuan;
import GUI.component.TableActionListener;
import GUI.component.TableNoTouch;
import GUI.dialog.AddHoaDonDialog;
import net.miginfocom.swing.MigLayout;
import raven.chart.data.pie.DefaultPieDataset;
import raven.chart.pie.PieChart;
import resources.base.baseTheme;
import utils.FormatterUtil;
import utils.Validate;

public class DoanhThuForm extends JPanel implements ActionListener, TableActionListener{
    JComboBox<String> comboBox;
    String[] choose = {"Sách","Khách hàng"};
    private ThongKeDoanhThuBUS doanhThuBUS;
    private Date startDate; 
    private Date endDate;
    private JPanel panelTable;
    private String[] headerSach = {"Mã sách","Tên sách","Số lượng bán","Tổng tiền"}; 
    private String[] headerKhachHang = {"Mã KH","Tên khách hàng","Số hóa đơn","Tổng tiền"};
    private CustomTable tableKhachHang;
    private CustomTable tableSach;

    private CustomTable table; 
    private PieChart pieChartt;
    private int[] dataPieChart;
    private CustomTable tableHoaDon;
    private List<ThongKeDoanhThuDTO> listSach;
    private List<ThongKeDoanhThuDTO> listKH;
    private String[] tongHD_TongDT;

    private List<ThongKeDoanhThuDTO> listTop5Sach;
    private List<ThongKeDoanhThuDTO> lisTop5Kh;

    private MainFrame mainFrame;
    private HoaDonBUS hoaDonBUS;
    private InputFormItem inputStartDate;  
    private InputFormItem inputEndDate;

    private CustomButton btnLoc;
    private boolean flag = false;

    private LabelTongQuan labelTongQuanTHD;
    private LabelTongQuan labelTongQuanTDT;


    public DoanhThuForm(MainFrame mainFrame) {
        doanhThuBUS = new ThongKeDoanhThuBUS();
        hoaDonBUS = HoaDonBUS.getInstance();
        this.mainFrame = mainFrame;
        loadData();
        init();
        System.out.println(startDate + " " + endDate);
    }

    private void init() {
        setLayout(new MigLayout("insets 0, gap 0,wrap 1"));
        add(getHeader(),"pushx,growx, hmin 60");
        add(getMainContent(),"push,grow, gaptop 10");
    }

    private void loadDataWithDate() {
        // Calendar cal = Calendar.getInstance();

        // startDate: 01/05/2025
        // cal.set(2025, Calendar.APRIL, 1);
        // startDate = cal.getTime();

        // endDate: 01/01/2027 (vì 24 tháng sau tháng 4/2025 là tháng 4/2027)
        // cal.set(2027, Calendar.APRIL, 1);
        // endDate = cal.getTime();
            
        listSach = doanhThuBUS.getRevenueStats(startDate,endDate,"book");
        listKH = doanhThuBUS.getRevenueStats(startDate,endDate,"customer");
        tongHD_TongDT = doanhThuBUS.getTotalRevenue(startDate,endDate);

        listTop5Sach = doanhThuBUS.getTop5Books(startDate, endDate);
        lisTop5Kh = doanhThuBUS.getTop5Kh(startDate, endDate);
    }

    private void loadData(){
        // Ngày hiện tại
        endDate = new Date();

        // Ngày đầu tháng
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        startDate = cal.getTime();
        listSach = doanhThuBUS.getRevenueStats(startDate,endDate,"book");
        listKH = doanhThuBUS.getRevenueStats(startDate,endDate,"customer");
        tongHD_TongDT = doanhThuBUS.getTotalRevenue(startDate,endDate);

        listTop5Sach = doanhThuBUS.getTop5Books(startDate, endDate);
        lisTop5Kh = doanhThuBUS.getTop5Kh(startDate, endDate);

        // listSach = doanhThuBUS.getRevenueStats("book");
        // listKH = doanhThuBUS.getRevenueStats("customer");
        // tongHD_TongDT = doanhThuBUS.getTotalRevenue();

        // listTop5Sach = doanhThuBUS.getTop5Books();
        // lisTop5Kh = doanhThuBUS.getTop5Kh();
    }

    private JPanel getHeader() {
        JPanel panel = new JPanel(new MigLayout("insets 10 30 10 30, gap 5,al center center"));
        panel.setBackground(Color.decode(baseTheme.selectedButton));
        panel.add(new JLabel("Thống kê theo")); 
        comboBox = new JComboBox<>(choose);
        comboBox.addActionListener(this);
        panel.add(comboBox);
        panel.add(new JLabel(),"pushx"); 

        inputStartDate = new InputFormItem("inputDate", "Từ ngày");
        
        inputEndDate = new InputFormItem("inputDate", "Đến");
        
                SwingUtilities.invokeLater(() -> inputStartDate.getInputDate().setDate(startDate));
                SwingUtilities.invokeLater(() -> inputEndDate.getInputDate().setDate(endDate));
        

        inputStartDate.setBackground(baseTheme.selectedButton);
        inputEndDate.setBackground(baseTheme.selectedButton);
        inputEndDate.setLayoutConstraint();
        inputStartDate.setLayoutConstraint();

        panel.add(inputStartDate, "grow, pushx");
        // panel.add(new JLabel("Từ ngày"));
        // panel.add(new JTextField(20));

        panel.add(inputEndDate, "grow, pushx");
        // panel.add(new JLabel("Đến"));
        // panel.add(new JTextField(20));
        
        btnLoc = new CustomButton("Lọc");
        btnLoc.setActionCommand("Loc");
        btnLoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Loc();
            }
        });
        panel.add(btnLoc);

        return panel;
    }


    private JPanel getMainContent() {
        JPanel panel = new JPanel(new MigLayout("insets 0, gap 10"));
        panelTable = getPanelTable();
        panel.add(panelTable,"push,grow");
        panel.add(getPanelTongQuan(),"pushy,growy");
        return panel;
    }

    

    private JPanel getPanelTable() {
        JPanel panel = new JPanel(new MigLayout("insets 0, gap 5,wrap 1"));
        panel.add(new JLabel("<html><font size='+1'><b>Tình hình kinh doanh</b></font></html>"));

        // comboBox.setSelectedItem("Sách");
        // String select = (String)comboBox.getSelectedItem();
        // String[] headerTable;

        // Tạo dữ liệu cho tableSach
        
        // Tạo dữ liệu cho tableKhachHang
        ArrayList<String[]> dataSach = new ArrayList<>();
        for (ThongKeDoanhThuDTO x : listSach) {
            dataSach.add(new String[]{x.getId()+"",x.getName(),x.getSoluong()+"",FormatterUtil.formatNumberVN(x.getDoanhthu())+""});
        }

        ArrayList<String[]> dataKhachHang = new ArrayList<>();

        for (ThongKeDoanhThuDTO x : listKH) {
            dataKhachHang.add(new String[]{x.getId()+"",x.getName(),x.getSoluong()+"",FormatterUtil.formatNumberVN(x.getDoanhthu())+""});
        }

        tableSach = new TableNoTouch(dataSach,new CustomTable.OnSelectRowListener() {
            @Override
            public void OnSelectRow(int row) {
                System.out.println("hang " + row);
                if(flag){
                    loadTableWidthDate(row);
                }
                else{
                    loadTable(row);
                }
            }
        }, headerSach);
        tableSach.setMaxTextWidth(80);

        tableKhachHang = new TableNoTouch(dataKhachHang,new CustomTable.OnSelectRowListener() {
            @Override
            public void OnSelectRow(int row) {
                System.out.println("khach " +  row);
                if(flag){
                    loadTableWidthDate(row);
                }
                else{
                    loadTable(row);
                }
            }
        }, headerKhachHang);
        tableKhachHang.setMaxTextWidth(80);

       
       // Chọn bảng dựa trên comboBox
        String select = comboBox.getSelectedItem() != null ? (String) comboBox.getSelectedItem() : "Sách";
        table = select.equals("Khách hàng") ? tableKhachHang : tableSach;
        

        panel.add(table,"push,grow");
        
        return panel;
    }

    private JPanel getPanelTongQuan() {
        JPanel panel = new JPanel(new MigLayout("insets 0, gap 10,wrap 1"));
        panel.setPreferredSize(new Dimension(300,500));
        // panel.setMinimumSize(new Dimension(300,500));

        panel.add(getTongQuan(),"pushx,growx");
        panel.add(getListHoaDon(),"push,grow");
        return panel;
    }

    JPanel panelPie;
    
    private JPanel getTongQuan() {
        JPanel panel = new JPanel(new MigLayout("insets 0, gap 10,wrap 1"));
        
        JPanel panelLabel = new JPanel(new MigLayout("insets 0, gap 10, al center center"));

        labelTongQuanTHD = new LabelTongQuan("Tổng số hóa đơn","hoadon.svg",tongHD_TongDT[0],baseTheme.soLuong);
        panelLabel.add(labelTongQuanTHD, "pushx,growx,sg 1");
        labelTongQuanTDT = new LabelTongQuan("Doanh thu (đ)","doanhthu.svg",tongHD_TongDT[1],baseTheme.tongTien);
        panelLabel.add(labelTongQuanTDT,"pushx, growx,sg 1");

        panel.add(panelLabel,"pushx,growx");

        // panelPie = panelPieChart();
        pieChartt = pieChart();
        panel.add(pieChartt,"h 290::290,pushx,growx");
        return panel;
    }

    private PieChart pieChart() {
        PieChart pieChart1 = new PieChart();
        pieChart1.setChartType(PieChart.ChartType.DONUT_CHART);
        // pieChart1.setBackground(Color.white);
        pieChart1.setOpaque(false);
        JLabel header1 = new JLabel("<html><font><b>Top 5</b></font></html>",JLabel.CENTER);
        header1.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:+1");
        pieChart1.setHeader(header1);

        pieChart1.getChartColor().addColor(Color.decode("#f87171"), Color.decode("#fb923c"), Color.decode("#fbbf24"), Color.decode("#a3e635"), Color.decode("#34d399"), Color.decode("#22d3ee"), Color.decode("#818cf8"), Color.decode("#c084fc"));
        pieChart1.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:5,5,5,5,$Component.borderColor,,20");

        pieChart1.setDataset(createPieData(listTop5Sach));
        // add(pieChart1, "split 3,height 2
        return pieChart1;
    }

    

    private DefaultPieDataset createPieData(List<ThongKeDoanhThuDTO> list) {

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        for (ThongKeDoanhThuDTO x : list) {
            dataset.addValue(x.getName(), x.getSoluong());
        }


        return dataset;
    }
    
    private JPanel getListHoaDon() {
        JPanel panel = new JPanel(new MigLayout("insets 0, gap 10,wrap 1"));
        // panel.setOpaque(true);
        // panel.setBackground(Color.white);
        panel.add(new CustomBoldJLabel("Hóa đơn tương ứng", 1),"pushx, al center"); 
        tableHoaDon = new CustomTable(null, new String[][] {{"detail.svg","detail"}},"Mã hóa đơn","Tổng tiền");
        tableHoaDon.setMaxTextWidth(25);
        
        panel.add(tableHoaDon,"push,grow");
        tableHoaDon.setActionListener(this);
        return panel;
    }


    private void updatePanelTable(CustomTable newTable) {
        // Xóa bảng cũ khỏi panelTable
        panelTable.removeAll();
        // Thêm tiêu đề
        panelTable.add(new JLabel("<html><font size='+1'><b>Tình hình kinh doanh</b></font></html>"));
        // Thêm bảng mới
        table = newTable;
        panelTable.add(table, "push,grow");
        // Cập nhật giao diện

        if (((String)comboBox.getSelectedItem()).equals("Khách hàng")) {
            // headerPieChart.setText("<html><font><b>Top 5 khách hàng mua nhiều nhất</b></font></html>");
            pieChartt.setDataset(createPieData(lisTop5Kh));
        }
        else {
            // headerPieChart.setText("<html><font><b>Top 5 sách bán chạy nhất</b></font></html>");
            pieChartt.setDataset(createPieData(listTop5Sach));
        }

        // headerPieChart.repaint();
        // headerPieChart.revalidate();

        // panelPie.repaint();
        // panelPie.revalidate();
        
        panelTable.revalidate();
        panelTable.repaint();
    }

    public void loadTableWidthDate(int row){
        System.out.println("con cho biet o ne");
        int ma = getIdByRowIndex(row);
        ArrayList<String[]> dataUpdate = new ArrayList<>();
        if (((String)comboBox.getSelectedItem()).equals("Khách hàng")) {
            System.out.println("Khách hàng" + ma);
            for (HoaDonDTO x : doanhThuBUS.getInvoices(ma+"", startDate, endDate, "customer")) {
                dataUpdate.add(new String[] {x.getMaHD()+"",FormatterUtil.formatNumberVN(x.getTongTien())+""});
            };
        }
        else {
            System.out.println("Sách" + ma);
            for (HoaDonDTO x : doanhThuBUS.getInvoices(ma+"", startDate, endDate, "book")) {
                dataUpdate.add(new String[] {x.getMaHD()+"",FormatterUtil.formatNumberVN(x.getTongTien())+""});
            };
        }

        tableHoaDon.updateTable(dataUpdate);
    }

    public void loadTable(int row){
        System.out.println("con cho biet o ne");
        int ma = getIdByRowIndex(row);
        ArrayList<String[]> dataUpdate = new ArrayList<>();
        if (((String)comboBox.getSelectedItem()).equals("Khách hàng")) {
            System.out.println("Khách hàng" + ma);
            for (HoaDonDTO x : doanhThuBUS.getInvoices(ma+"", "customer")) {
                dataUpdate.add(new String[] {x.getMaHD()+"",FormatterUtil.formatNumberVN(x.getTongTien())+""});
            };
        }
        else {
            System.out.println("Sách" + ma);
            for (HoaDonDTO x : doanhThuBUS.getInvoices(ma+"", "book")) {
                dataUpdate.add(new String[] {x.getMaHD()+"",FormatterUtil.formatNumberVN(x.getTongTien())+""});
            };
        }

        tableHoaDon.updateTable(dataUpdate);
    }

    public int getIdByRowIndex(int row){
        // JLabel label = (JLabel)this.table.getRowLabels().get(row).get(0);
        // int ma = Integer.parseInt(label.getText());
        // return(ma);
        try {
            List<Component> rowComponents = table.getRowLabels().get(row);
            if (rowComponents == null || rowComponents.isEmpty()) {
                System.out.println("Hàng " + row + " không tồn tại trong rowLabels");
                return -1; // Hoặc giá trị mặc định khác
            }
            Component component = rowComponents.get(0);
            if (!(component instanceof JLabel)) {
                System.out.println("Thành phần tại hàng " + row + ", cột 0 không phải JLabel");
                return -1;
            }
            JLabel label = (JLabel) component;
            String text = label.getText();
            if (text == null || text.trim().isEmpty()) {
                System.out.println("Giá trị tại hàng " + row + ", cột 0 rỗng");
                return -1;
            }
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            System.out.println("Giá trị tại hàng " + row + ", cột 0 không phải số nguyên: " + e.getMessage());
            return -1;
        }
    }
    


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        JComboBox combo = (JComboBox) e.getSource();

        switch ((String) combo.getSelectedItem()) {
            case "Khách hàng":
                updatePanelTable(tableKhachHang);
                break;
            case "Sách":
                updatePanelTable(tableSach);
                break;

            default:
                System.out.println("action");
                break;
        }
        
    }

    @Override
    public void onActionPerformed(String actionId, int row) {
        mainFrame.glassPane.setVisible(true);
        if(actionId.equals("detail")){
            int maHD = Integer.parseInt(tableHoaDon.getCellData(row, 0));
            HoaDonDTO hoaDon = hoaDonBUS.getInstanceByID(maHD);
            AddHoaDonDialog addHoaDonDialog = new AddHoaDonDialog(mainFrame, hoaDon);
            addHoaDonDialog.setVisible(true);
        }
        else{
            System.out.println("Unknow Action");
        }
        mainFrame.glassPane.setVisible(false);
    }

    public void Loc(){
        flag = false;
        String ngayBatDauS = inputStartDate.getDateString();
        String ngayKetThucS = inputEndDate.getDateString();

        if(Validation(ngayBatDauS, ngayKetThucS)){
            if(!Validate.isEmpty(ngayBatDauS) && !Validate.isEmpty(ngayKetThucS)){ //Cho bộ lọc
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    startDate = sdf.parse(ngayBatDauS);
                    endDate = sdf.parse(ngayKetThucS);
                    System.out.println(startDate + " " + endDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                loadDataWithDate();
                flag = true;
    
                ArrayList<String[]> dataSach = new ArrayList<>();
                for (ThongKeDoanhThuDTO x : listSach) {
                    dataSach.add(new String[]{x.getId()+"",x.getName(),x.getSoluong()+"",FormatterUtil.formatNumberVN(x.getDoanhthu())+""});
                }
        
                ArrayList<String[]> dataKhachHang = new ArrayList<>();
        
                for (ThongKeDoanhThuDTO x : listKH) {
                    dataKhachHang.add(new String[]{x.getId()+"",x.getName(),x.getSoluong()+"",FormatterUtil.formatNumberVN(x.getDoanhthu())+""});
                }
    
                tableKhachHang.updateTable(dataKhachHang);
                tableSach.updateTable(dataSach);

                labelTongQuanTHD.setCount(tongHD_TongDT[0]);
                labelTongQuanTDT.setCount(tongHD_TongDT[1]);

                if (((String)comboBox.getSelectedItem()).equals("Khách hàng")) {
                    // headerPieChart.setText("<html><font><b>Top 5 khách hàng mua nhiều nhất</b></font></html>");
                    pieChartt.setDataset(createPieData(lisTop5Kh));
                }
                else {
                    // headerPieChart.setText("<html><font><b>Top 5 sách bán chạy nhất</b></font></html>");
                    pieChartt.setDataset(createPieData(listTop5Sach));
                }
            }
            else{   //Cập nhật lại tất cả
                loadData();
                flag = false;
                ArrayList<String[]> dataSach = new ArrayList<>();
                for (ThongKeDoanhThuDTO x : listSach) {
                    dataSach.add(new String[]{x.getId()+"",x.getName(),x.getSoluong()+"",FormatterUtil.formatNumberVN(x.getDoanhthu())+""});
                }
                
                ArrayList<String[]> dataKhachHang = new ArrayList<>();
        
                for (ThongKeDoanhThuDTO x : listKH) {
                    dataKhachHang.add(new String[]{x.getId()+"",x.getName(),x.getSoluong()+"",FormatterUtil.formatNumberVN(x.getDoanhthu())+""});
                }
    
                tableKhachHang.updateTable(dataKhachHang);
                tableSach.updateTable(dataSach);
            }
        }
    }

    public boolean Validation(String ngayBatDauS, String ngayKetThucS){
        if(!Validate.isEmpty(ngayBatDauS) && !Validate.isDate(ngayBatDauS)){
            JOptionPane.showMessageDialog(mainFrame, "Ngày bắt đầu phải đúng định dạng");
            return(false);
        }
        else if(!Validate.isEmpty(ngayKetThucS) && !Validate.isDate(ngayKetThucS)){
            JOptionPane.showMessageDialog(mainFrame, "Ngày kết thúc phải đúng định dạng");
            return(false);
        }
        else if(Validate.isEmpty(ngayBatDauS) && !Validate.isEmpty(ngayKetThucS)){
            JOptionPane.showMessageDialog(mainFrame, "Vui lòng nhập ngày bắt đầu");
            return(false);
        }
        else if(!Validate.isEmpty(ngayBatDauS) && Validate.isEmpty(ngayKetThucS)){
            JOptionPane.showMessageDialog(mainFrame, "Vui lòng nhập ngày kết thúc");
            return(false);
        }
        else if(!Validate.isEmpty(ngayBatDauS) && !Validate.isEmpty(ngayKetThucS) && !Validate.isStartDateAndEndDate(ngayBatDauS, ngayKetThucS)){
            JOptionPane.showMessageDialog(mainFrame, "Ngày bắt đầu phải trước ngày kết thúc");
            return(false);
        }
        return(true);
    }
    
}