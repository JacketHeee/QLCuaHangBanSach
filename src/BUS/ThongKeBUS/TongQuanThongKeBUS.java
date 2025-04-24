package BUS.ThongKeBUS;



import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import DAO.ThongKeDAO.TongQuanThongKeDAO;
import DTO.SachDTO;
import DTO.TyLeDTO;

public class TongQuanThongKeBUS {
    private TongQuanThongKeDAO overviewDAO;

    public TongQuanThongKeBUS() {
        this.overviewDAO = new TongQuanThongKeDAO();
    }

    public String getOrdersToday() {
        try {
            return String.valueOf(overviewDAO.getOrdersToday());
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    public String getRevenueToday() {
        try {
            double revenue = overviewDAO.getRevenueToday();
            return NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(revenue);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    public String getImportCostToday() {
        try {
            double cost = overviewDAO.getImportCostToday();
            return NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(cost);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    public String getLowStockBooks(int nguong) {
        try {
            return String.valueOf(overviewDAO.getLowStockBooks(nguong));
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    public List<BigDecimal> getRevenue7Days() {
        try {
            return overviewDAO.getRevenue7Days();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<SachDTO> getTopBooks7Days() {
        try {
            return overviewDAO.getTopBooks7Days();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public TyLeDTO getPromotionUsage() {
        try {
            return overviewDAO.getPromotionUsage();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}