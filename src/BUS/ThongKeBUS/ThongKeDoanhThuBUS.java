package BUS.ThongKeBUS;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import DAO.ThongKeDAO.ThongKeDoanhThuDAO;
import DTO.HoaDonDTO;
import DTO.ThongKe.ThongKeDoanhThuDTO;

public class ThongKeDoanhThuBUS {
    private ThongKeDoanhThuDAO doanhThuDAO;

    public ThongKeDoanhThuBUS() {
        this.doanhThuDAO = new ThongKeDoanhThuDAO();
    }

    // get thoong ke theo sach hoac khach hang 
    public List<ThongKeDoanhThuDTO> getRevenueStats(Date startDate, Date endDate, String type) {
        try {
            if ("book".equalsIgnoreCase(type)) {
                return doanhThuDAO.getRevenueByBook(startDate, endDate);
            } else if ("customer".equalsIgnoreCase(type)) {
                return doanhThuDAO.getRevenueByCustomer(startDate, endDate);
            }
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //get hoa down tuong tung voi id 
    public List<HoaDonDTO> getInvoices(String id, Date startDate, Date endDate, String type) {
        try {
            if ("book".equalsIgnoreCase(type)) {
                return doanhThuDAO.getInvoicesByBook(id, startDate, endDate);
            } else if ("customer".equalsIgnoreCase(type)) {
                return doanhThuDAO.getInvoicesByCustomer(id, startDate, endDate);
            }
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //get so hoa don va tong doanh thu 
    public String[] getTotalRevenue(Date startDate, Date endDate) {
        try {
            double[] result = doanhThuDAO.getTotalRevenue(startDate, endDate);
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            return new String[]{String.valueOf((int) result[0]), currencyFormat.format(result[1])};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"0", "0"};
        }
    }

    public List<ThongKeDoanhThuDTO> getTop5Books(Date startDate, Date endDate) {
        try {
            return doanhThuDAO.getTop5Books(startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ThongKeDoanhThuDTO> getTop5Kh(Date startDate, Date endDate) {
        try {
            return doanhThuDAO.getTop5Customers(startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ThongKeDoanhThuDTO> getRevenueStats(String type) {
        try {
            if ("book".equalsIgnoreCase(type)) {
                return doanhThuDAO.getRevenueByBook();
            } else if ("customer".equalsIgnoreCase(type)) {
                return doanhThuDAO.getRevenueByCustomer();
            }
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<HoaDonDTO> getInvoices(String id, String type) {
        try {
            if ("book".equalsIgnoreCase(type)) {
                return doanhThuDAO.getInvoicesByBook(id);
            } else if ("customer".equalsIgnoreCase(type)) {
                return doanhThuDAO.getInvoicesByCustomer(id);
            }
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String[] getTotalRevenue() {
        try {
            double[] result = doanhThuDAO.getTotalRevenue();
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            return new String[]{String.valueOf((int) result[0]), currencyFormat.format(result[1])};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"0", "0"};
        }
    }

    public List<ThongKeDoanhThuDTO> getTop5Books() {
        try {
            return doanhThuDAO.getTop5Books();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ThongKeDoanhThuDTO> getTop5Kh() {
        try {
            return doanhThuDAO.getTop5Customers();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}