package BUS.ThongKeBUS;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import DAO.ThongKeDAO.ThongKeNhapHangDAO;
import DTO.PhieuNhapDTO;
import DTO.ThongKe.ThongKeNhapHangDTO;

public class ThongKeNhapHangBUS {
    private ThongKeNhapHangDAO thongKeNhapHangDAO;

    public ThongKeNhapHangBUS() {
        this.thongKeNhapHangDAO = new ThongKeNhapHangDAO();
    }

    public List<ThongKeNhapHangDTO> getImportStats(LocalDateTime startDate, LocalDateTime endDate, String type) {
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

    public List<PhieuNhapDTO> getImports(String id, LocalDateTime startDate, LocalDateTime endDate, String type) {
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

    public String[] getTotalImport(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            double[] result = thongKeNhapHangDAO.getTotalImport(startDate, endDate);
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            return new String[]{String.valueOf((int) result[0]), currencyFormat.format(result[1])};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"0", "0"};
        }
    }

    public List<ThongKeNhapHangDTO> getTop5Books(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            return thongKeNhapHangDAO.getTop5Books(startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ThongKeNhapHangDTO> getTop5NCC(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            return thongKeNhapHangDAO.getTop5Suppliers(startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ThongKeNhapHangDTO> getImportStats(String type) {
        try {
            if ("book".equalsIgnoreCase(type)) {
                return thongKeNhapHangDAO.getImportByBook();
            } else if ("employee".equalsIgnoreCase(type)) {
                return thongKeNhapHangDAO.getImportByEmployee();
            }
            else if ("ncc".equalsIgnoreCase(type)) {
                return thongKeNhapHangDAO.getImportBySupplier();
            }

            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<PhieuNhapDTO> getImports(String id, String type) {
        try {
            if ("book".equalsIgnoreCase(type)) {
                return thongKeNhapHangDAO.getImportsByBook(id);
            } else if ("employee".equalsIgnoreCase(type)) {
                return thongKeNhapHangDAO.getImportsByEmployee(id);
            }
            else if ("ncc".equalsIgnoreCase(type)) {
                return thongKeNhapHangDAO.getImportsBySupplier(id);
            }
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String[] getTotalImport() {
        try {
            double[] result = thongKeNhapHangDAO.getTotalImport();
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            return new String[]{String.valueOf((int) result[0]), currencyFormat.format(result[1])};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"0", "0"};
        }
    }

    public List<ThongKeNhapHangDTO> getTop5Books() {
        try {
            return thongKeNhapHangDAO.getTop5Books();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ThongKeNhapHangDTO> getTop5NCC() {
        try {
            return thongKeNhapHangDAO.getTop5Suppliers();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
