package BUS.ThongKeBUS;

import dao.ImportDAO;
import dto.ImportDTO;
import dto.ImportStatsDTO;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ThongKeNhapHangBUS {
    private ImportDAO importDAO;

    public ThongKeNhapHangBUS() {
        this.importDAO = new ImportDAO();
    }

    public List<ImportStatsDTO> getImportStats(Date startDate, Date endDate, String type) {
        try {
            if ("book".equalsIgnoreCase(type)) {
                return importDAO.getImportByBook(startDate, endDate);
            } else if ("employee".equalsIgnoreCase(type)) {
                return importDAO.getImportByEmployee(startDate, endDate);
            }
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ImportDTO> getImports(String id, Date startDate, Date endDate, String type) {
        try {
            if ("book".equalsIgnoreCase(type)) {
                return importDAO.getImportsByBook(id, startDate, endDate);
            } else if ("employee".equalsIgnoreCase(type)) {
                return importDAO.getImportsByEmployee(id, startDate, endDate);
            }
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String[] getTotalImport(Date startDate, Date endDate) {
        try {
            double[] result = importDAO.getTotalImport(startDate, endDate);
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            return new String[]{String.valueOf((int) result[0]), currencyFormat.format(result[1])};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"0", "0"};
        }
    }
}