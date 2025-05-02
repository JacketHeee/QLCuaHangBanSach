
import DAO.HoaDonDAO;
import DAO.TaiKhoanDAO;
import DTO.HoaDonDTO;

public class TestHoaDonDAO {
    public static void main(String[] args) {
        int maHDCanLay = 1; 

        HoaDonDAO hoaDonDAO = HoaDonDAO.getInstance();
        HoaDonDTO hoaDon = hoaDonDAO.getInstanceByID(maHDCanLay);
        String tenNhanVien = TaiKhoanDAO.getInstance().getUsernameByMaTK(hoaDon.getMaTK());
                if (hoaDon != null && hoaDon.getMaHD() != 0) {
            System.out.println("=== Thông tin hóa đơn ===");
            System.out.println("Mã hóa đơn: " + hoaDon.getMaHD());
            System.out.println("Ngày bán: " + hoaDon.getNgayBan());
            System.out.println("Tổng tiền: " + hoaDon.getTongTien());
            System.out.println("Tên nhân viên: " + tenNhanVien);
            System.out.println("=========================");
        } else {
            System.out.println("Không tìm thấy hóa đơn có mã: " + maHDCanLay);
        }
    }
}




