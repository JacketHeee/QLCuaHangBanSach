package BUS.ThongKeBUS;



import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import DAO.ThongKeDAO.TongQuanThongKeDAO;
import DTO.SachDTO;
import DTO.TyLeDTO;
import utils.FormatterUtil;

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
            BigDecimal revenue = overviewDAO.getRevenueToday();
            return FormatterUtil.formatNumber(revenue);
        } catch (Exception e) {
            e.printStackTrace();
            return "0đ";
        }
    }

    public String getImportCostToday() {
        try {
            BigDecimal cost = overviewDAO.getImportCostToday();
            return FormatterUtil.formatNumber(cost);
        } catch (Exception e) {
            e.printStackTrace();
            return "0đ";
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