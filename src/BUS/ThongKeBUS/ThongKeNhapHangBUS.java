package BUS.ThongKeBUS;

import DAO.ThongKeDAO.ThongKeNhapHangDAO;
import DTO.PhieuNhapDTO;
import DTO.ThongKe.ThongKeDoanhThuDTO;
import DTO.ThongKe.ThongKeNhapHangDTO;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ThongKeNhapHangBUS {
    private ThongKeNhapHangDAO thongKeNhapHangDAO;

    public ThongKeNhapHangBUS() {
        this.thongKeNhapHangDAO = new ThongKeNhapHangDAO();
    }

    public List<ThongKeNhapHangDTO> getImportStats(Date startDate, Date endDate, String type) {
        try {
            if ("book".equalsIgnoreCase(type)) {
                return thongKeNhapHangDAO.getImportByBook(startDate, endDate);
            } else if ("employee".equalsIgnoreCase(type)) {
                return thongKeNhapHangDAO.getImportByEmployee(startDate, endDate);
            }
            else if ("ncc".equalsIgnoreCase(type)) {
                return thongKeNhapHangDAO.getImportBySupplier(startDate, endDate);
            }

            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<PhieuNhapDTO> getImports(String id, Date startDate, Date endDate, String type) {
        try {
            if ("book".equalsIgnoreCase(type)) {
                return thongKeNhapHangDAO.getImportsByBook(id, startDate, endDate);
            } else if ("employee".equalsIgnoreCase(type)) {
                return thongKeNhapHangDAO.getImportsByEmployee(id, startDate, endDate);
            }
            else if ("ncc".equalsIgnoreCase(type)) {
                return thongKeNhapHangDAO.getImportsBySupplier(id, startDate, endDate);
            }
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String[] getTotalImport(Date startDate, Date endDate) {
        try {
            double[] result = thongKeNhapHangDAO.getTotalImport(startDate, endDate);
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            return new String[]{String.valueOf((int) result[0]), currencyFormat.format(result[1])};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"0", "0"};
        }
    }

    public List<ThongKeNhapHangDTO> getTop5Books(Date startDate, Date endDate) {
        try {
            return thongKeNhapHangDAO.getTop5Books(startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ThongKeNhapHangDTO> getTop5NCC(Date startDate, Date endDate) {
        try {
            return thongKeNhapHangDAO.getTop5Suppliers(startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}