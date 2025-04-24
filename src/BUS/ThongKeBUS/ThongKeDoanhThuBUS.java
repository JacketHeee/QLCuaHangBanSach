package BUS.ThongKeBUS;

import dao.RevenueDAO;
import dto.InvoiceDTO;
import dto.RevenueStatsDTO;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ThongKeDoanhThuBUS {
    private ThongKeDoanhThuBUS revenueDAO;

    public ThongKeDoanhThuBUS() {
        this.revenueDAO = new ThongKeDoanhThuDAO();
    }

    public List<RevenueStatsDTO> getRevenueStats(Date startDate, Date endDate, String type) {
        try {
            if ("book".equalsIgnoreCase(type)) {
                return revenueDAO.getRevenueByBook(startDate, endDate);
            } else if ("customer".equalsIgnoreCase(type)) {
                return revenueDAO.getRevenueByCustomer(startDate, endDate);
            }
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<InvoiceDTO> getInvoices(String id, Date startDate, Date endDate, String type) {
        try {
            if ("book".equalsIgnoreCase(type)) {
                return revenueDAO.getInvoicesByBook(id, startDate, endDate);
            } else if ("customer".equalsIgnoreCase(type)) {
                return revenueDAO.getInvoicesByCustomer(id, startDate, endDate);
            }
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String[] getTotalRevenue(Date startDate, Date endDate) {
        try {
            double[] result = revenueDAO.getTotalRevenue(startDate, endDate);
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            return new String[]{String.valueOf((int) result[0]), currencyFormat.format(result[1])};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"0", "0"};
        }
    }

    public List<RevenueStatsDTO> getTop5Books(Date startDate, Date endDate) {
        try {
            return revenueDAO.getTop5Books(startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}