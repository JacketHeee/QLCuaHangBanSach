package GUI.forms.thongke;

import javax.swing.*;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.PhieuNhapBUS;
import BUS.ThongKeBUS.ThongKeDoanhThuBUS;
import BUS.ThongKeBUS.ThongKeNhapHangBUS;
import DTO.HoaDonDTO;
import DTO.PhieuNhapDTO;
import DTO.SachDTO;
import DTO.ThongKe.ThongKeDoanhThuDTO;
import DTO.ThongKe.ThongKeNhapHangDTO;
import DTO.ThongKe.ThongKeNhapHangDTO;
import GUI.MainFrame;
import GUI.component.CustomBoldJLabel;
import GUI.component.CustomButton;
import GUI.component.CustomTable;
import GUI.component.InputFormItem;
import GUI.component.LabelInfor;
import GUI.component.LabelTongQuan;
import GUI.component.TableActionListener;
import GUI.component.TableNoTouch;
import GUI.component.chart.HorizontalBarChartV2;
import GUI.dialog.AddHoaDonDialog;
import GUI.dialog.AddPhieuNhapDialog;
import net.miginfocom.swing.MigLayout;
import raven.chart.data.pie.DefaultPieDataset;
import raven.chart.pie.PieChart;
import resources.base.baseTheme;
import utils.FormatterUtil;
import utils.Validate;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Date;

public class NhapHang extends JPanel implements ActionListener, TableActionListener {
    JComboBox<String> comboBox;
    String[] choose = {"Sách","Nhà cung cấp"};
    private ThongKeNhapHangBUS doanhThuBUS;
    private Date startDate; 
    private Date endDate;
    private JPanel panelTable;
    private String[] headerSach = {"Mã sách","Tên sách","Số lượng nhập","Tổng tiền"}; 
    private String[] headerKhachHang = {"Mã NCC","Tên NCC","Số phiếu nhập","Tổng tiền"};
    private CustomTable tableKhachHang;
    private CustomTable tableSach;

    private CustomTable table; 
    private PieChart pieChartt;
    private int[] dataPieChart;
    private CustomTable tableHoaDon;
    private List<ThongKeNhapHangDTO> listSach;
    private List<ThongKeNhapHangDTO> listKH;
    private String[] tongHD_TongDT;

    private List<ThongKeNhapHangDTO> listTop5Sach;
    private List<ThongKeNhapHangDTO> lisTop5Kh;

    private MainFrame mainFrame;
    private PhieuNhapBUS phieuNhapBUS;

    private InputFormItem inputStartDate;
    private InputFormItem inputEndDate;

    private CustomButton btnLoc;
    private boolean flag = false;

    private LabelTongQuan labelTongQuanTHD;
    private LabelTongQuan labelTongQuanTDT;

    public NhapHang(MainFrame mainFrame) {
        doanhThuBUS = new ThongKeNhapHangBUS();
        this.mainFrame = mainFrame;
        this.phieuNhapBUS = PhieuNhapBUS.getInstance();
        loadData();
        init();
    }

    private void init() {
        setLayout(new MigLayout("insets 0, gap 0,wrap 1"));
        add(getHeader(),"pushx,growx, hmin 60");
        add(getMainContent(),"push,grow, gaptop 10");
    }

    private void loadData() {
        // Calendar cal = Calendar.getInstance();

        // // startDate: 01/05/2025
        // cal.set(2025, Calendar.APRIL, 1);
        // startDate = cal.getTime();

        // // endDate: 01/01/2027 (vì 24 tháng sau tháng 4/2025 là tháng 4/2027)
        // cal.set(2027, Calendar.APRIL, 1);
        // endDate = cal.getTime();

        // Ngày hiện tại
        endDate = new Date();

        // Ngày đầu tháng
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        startDate = cal.getTime();

        listSach = doanhThuBUS.getImportStats(startDate,endDate,"book");
        listKH = doanhThuBUS.getImportStats(startDate,endDate,"ncc");
        tongHD_TongDT = doanhThuBUS.getTotalImport(startDate,endDate);

        listTop5Sach = doanhThuBUS.getTop5Books(startDate, endDate);
        lisTop5Kh = doanhThuBUS.getTop5NCC(startDate, endDate);
            
        // listSach = doanhThuBUS.getImportStats("book");
        // listKH = doanhThuBUS.getImportStats("ncc");
        // tongHD_TongDT = doanhThuBUS.getTotalImport();

        // listTop5Sach = doanhThuBUS.getTop5Books();
        // lisTop5Kh = doanhThuBUS.getTop5NCC();
    }

    private void loadDataWithDate() {    
        listSach = doanhThuBUS.getImportStats(startDate,endDate,"book");
        listKH = doanhThuBUS.getImportStats(startDate,endDate,"ncc");
        tongHD_TongDT = doanhThuBUS.getTotalImport(startDate,endDate);

        listTop5Sach = doanhThuBUS.getTop5Books(startDate, endDate);
        lisTop5Kh = doanhThuBUS.getTop5NCC(startDate, endDate);
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
        panel.add(inputEndDate, "grow, pushx");


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
        panel.add(new JLabel("<html><font size='+1'><b>Tình hình nhập hàng</b></font></html>"));

        // comboBox.setSelectedItem("Sách");
        // String select = (String)comboBox.getSelectedItem();
        // String[] headerTable;

        // Tạo dữ liệu cho tableSach
        
        // Tạo dữ liệu cho tableKhachHang
        ArrayList<String[]> dataSach = new ArrayList<>();
        for (ThongKeNhapHangDTO x : listSach) {
            dataSach.add(new String[]{x.getId()+"",x.getName(),x.getSoluong()+"",FormatterUtil.formatNumberVN(x.getTongtien())+""});
        }

        ArrayList<String[]> dataKhachHang = new ArrayList<>();

        for (ThongKeNhapHangDTO x : listKH) {
            dataKhachHang.add(new String[]{x.getId()+"",x.getName(),x.getSoluong()+"",FormatterUtil.formatNumberVN(x.getTongtien())+""});
        }

        tableSach = new TableNoTouch(dataSach,new CustomTable.OnSelectRowListener() {
            @Override
            public void OnSelectRow(int row) {
                if(flag){
                    loadTableWithDate(row);
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
                if(flag){
                    loadTableWithDate(row);
                }
                else{
                    loadTable(row);
                }
            }
        }, headerKhachHang);
        tableKhachHang.setMaxTextWidth(80);

       
       // Chọn bảng dựa trên comboBox
        String select = comboBox.getSelectedItem() != null ? (String) comboBox.getSelectedItem() : "Sách";
        table = select.equals("Nhà cung cấp") ? tableKhachHang : tableSach;
        

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

        labelTongQuanTHD = new LabelTongQuan("Tổng số phiếu nhập","hoadon.svg",tongHD_TongDT[0],baseTheme.soLuong);
        panelLabel.add(labelTongQuanTHD, "pushx,growx,sg 1");
        labelTongQuanTDT = new LabelTongQuan("Tổng tiền (đ)","doanhthu.svg",tongHD_TongDT[1],baseTheme.tongTien);
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

    

    private DefaultPieDataset createPieData(List<ThongKeNhapHangDTO> list) {

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        for (ThongKeNhapHangDTO x : list) {
            dataset.addValue(x.getName(), x.getSoluong());
        }


        return dataset;
    }


    
    private JPanel getListHoaDon() {
        JPanel panel = new JPanel(new MigLayout("insets 0, gap 10,wrap 1"));
        // panel.setOpaque(true);
        // panel.setBackground(Color.white);
        panel.add(new CustomBoldJLabel("Phiếu nhập tương ứng", 1),"pushx, al center"); 
        tableHoaDon = new CustomTable(null, new String[][] {{"detail.svg","detail"}},"Mã phiếu nhập","Tổng tiền");
        tableHoaDon.setMaxTextWidth(25);
        
        panel.add(tableHoaDon,"push,grow");
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

        if (((String)comboBox.getSelectedItem()).equals("Nhà cung cấp")) {
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

    public void loadTable(int row){
        System.out.println("con cho biet o ne");
        int ma = getIdByRowIndex(row);
        ArrayList<String[]> dataUpdate = new ArrayList<>();
        if (((String)comboBox.getSelectedItem()).equals("Nhà cung cấp")) {
            System.out.println("Nhà cung cấp" + ma);
            for (PhieuNhapDTO x : doanhThuBUS.getImports(ma+"", "ncc")) {
                dataUpdate.add(new String[] {x.getMaNhap()+"",FormatterUtil.formatNumberVN(x.getTongTien())+""});
            };
        }
        else {
            System.out.println("Sách" + ma);
            for (PhieuNhapDTO x : doanhThuBUS.getImports(ma+"", "book")) {
                dataUpdate.add(new String[] {x.getMaNhap()+"",FormatterUtil.formatNumberVN(x.getTongTien())+""});
            };
        }

        tableHoaDon.updateTable(dataUpdate);
        tableHoaDon.setActionListener(this);
    }

    public void loadTableWithDate(int row){
        System.out.println("con cho biet o ne");
        int ma = getIdByRowIndex(row);
        ArrayList<String[]> dataUpdate = new ArrayList<>();
        if (((String)comboBox.getSelectedItem()).equals("Nhà cung cấp")) {
            System.out.println("Nhà cung cấp" + ma);
            for (PhieuNhapDTO x : doanhThuBUS.getImports(ma+"", startDate, endDate, "ncc")) {
                dataUpdate.add(new String[] {x.getMaNhap()+"",FormatterUtil.formatNumberVN(x.getTongTien())+""});
            };
        }
        else {
            System.out.println("Sách" + ma);
            for (PhieuNhapDTO x : doanhThuBUS.getImports(ma+"", startDate, endDate, "book")) {
                dataUpdate.add(new String[] {x.getMaNhap()+"",FormatterUtil.formatNumberVN(x.getTongTien())+""});
            };
        }

        tableHoaDon.updateTable(dataUpdate);
        tableHoaDon.setActionListener(this);
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
            case "Nhà cung cấp":
                updatePanelTable(tableKhachHang);
                break;
            case "Sách":
                updatePanelTable(tableSach);
                break;
            default:
                break;
        }
        
    }

    @Override
    public void onActionPerformed(String actionId, int row) {
        mainFrame.glassPane.setVisible(true);
        if(actionId.equals("detail")){
            int maNhap = Integer.parseInt(tableHoaDon.getCellData(row, 0));
            PhieuNhapDTO phieuNhap = phieuNhapBUS.getInstanceByID(maNhap);
            AddPhieuNhapDialog addPhieuNhapDialog = new AddPhieuNhapDialog(mainFrame, phieuNhap);
            addPhieuNhapDialog.setVisible(true);
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
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                loadDataWithDate();
                flag = true;
    
                ArrayList<String[]> dataSach = new ArrayList<>();
                for (ThongKeNhapHangDTO x : listSach) {
                    dataSach.add(new String[]{x.getId()+"",x.getName(),x.getSoluong()+"",FormatterUtil.formatNumberVN(x.getTongtien())+""});
                }
        
                ArrayList<String[]> dataKhachHang = new ArrayList<>();
        
                for (ThongKeNhapHangDTO x : listKH) {
                    dataKhachHang.add(new String[]{x.getId()+"",x.getName(),x.getSoluong()+"",FormatterUtil.formatNumberVN(x.getTongtien())+""});
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
                for (ThongKeNhapHangDTO x : listSach) {
                    dataSach.add(new String[]{x.getId()+"",x.getName(),x.getSoluong()+"",FormatterUtil.formatNumberVN(x.getTongtien())+""});
                }
        
                ArrayList<String[]> dataKhachHang = new ArrayList<>();
        
                for (ThongKeNhapHangDTO x : listKH) {
                    dataKhachHang.add(new String[]{x.getId()+"",x.getName(),x.getSoluong()+"",FormatterUtil.formatNumberVN(x.getTongtien())+""});
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