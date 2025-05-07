package utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import BUS.HoaDonBUS;
import BUS.KhuyenMaiBUS;
import DTO.CT_HoaDonDTO;
import DTO.HoaDonDTO;
import DTO.KhuyenMaiDTO;
import DAO.NhanVienDAO;
import DAO.TaiKhoanDAO;

import javax.swing.*;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.text.html.HTMLDocument;
// import java.math.BigDecimal;
import java.math.RoundingMode;

public class PDFUtils {

    public static void taoHoaDonPDF(HoaDonDTO hoaDon, List<CT_HoaDonDTO> chiTietList) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu hóa đơn");
            fileChooser.setSelectedFile(new java.io.File("HD" + hoaDon.getMaHD() + ".pdf"));

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection != JFileChooser.APPROVE_OPTION) {
                System.out.println("Đã hủy lưu file.");
                return;
            }

            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(".pdf")) {
                filePath += ".pdf";
            }

            // Bắt đầu tạo PDF
            float newWidth = 10 * 28.35f; 
            float originalHeight = PageSize.A4.getHeight(); 
            Rectangle customSize = new Rectangle(newWidth, originalHeight);
            Document document = new Document(customSize,14.17f, 14.17f, 14.17f, 14.17f); 

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 8, Font.BOLD);
            Font normalFont = new Font(baseFont, 6, Font.NORMAL);
            Font boldFont = new Font(baseFont, 7, Font.BOLD);

            // Title
            Paragraph info = new Paragraph("DN SÁCH ChMaTraPhuAnThHi TP-HCM\nNS AN DUONG VUONG 273\nĐịa chỉ: 273 - An Dương Vương P3 - Q5 - TP.HCM\nĐT: 0123456789\n------------------------------------------------------------", normalFont);
            info.setAlignment(Element.ALIGN_CENTER);
            document.add(info);


            Paragraph title = new Paragraph("HÓA ĐƠN BÁN LẺ", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10f); // Add blank line after title
            document.add(title);

            // Thông tin hóa đơn
            String tenNhanVien = NhanVienDAO.getInstance().gettenNVByMaTK(hoaDon.getMaTK());

            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            float[] columnWidths = {1f, 1f};
            infoTable.setWidths(columnWidths);

            infoTable.addCell(createCell("So HD: " + hoaDon.getMaHD(), boldFont));
            infoTable.addCell(createCell("Gio: " + hoaDon.getNgayBan().toLocalTime().toString(), boldFont));
            infoTable.addCell(createCell("Ngay: " + hoaDon.getNgayBan().toLocalDate().toString(), boldFont));
            infoTable.addCell(createCell("Nhan Vien: " + tenNhanVien, boldFont));
            PdfPCell dashLineCell = new PdfPCell(new Phrase("-----------------------------------------------------------------------------------------------------------------------------", normalFont));
            dashLineCell.setColspan(2);
            dashLineCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            dashLineCell.setBorder(Rectangle.NO_BORDER);
            infoTable.addCell(dashLineCell);
            document.add(infoTable);

            // Bảng chi tiết hóa đơn
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3, 1, 2, 2});

            table.addCell(createHeaderCell("Name", boldFont));
            table.addCell(createHeaderCell("SL", boldFont));
            table.addCell(createHeaderCell("Price", boldFont));
            table.addCell(createHeaderCell("Total", boldFont));

            for (CT_HoaDonDTO ct : chiTietList) {
                table.addCell(createCell(ct.getTenSach(), normalFont));
                table.addCell(createMidCell(String.valueOf(ct.getSoLuong()), normalFont));
                table.addCell(createMidCell(formatMoney(ct.getGiaBan()), normalFont));
                table.addCell(createMidCell(formatMoney(ct.getGiaBan().multiply(new BigDecimal(ct.getSoLuong()))), normalFont));
            }
            PdfPCell dashLineCell2 = new PdfPCell(new Phrase("-----------------------------------------------------------------------------------------------------------------------------", normalFont));
            dashLineCell2.setColspan(4);
            dashLineCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            dashLineCell2.setBorder(Rectangle.NO_BORDER);
            table.addCell(dashLineCell2);
            document.add(table);
            
            // Tổng tiền


            PdfPTable summaryTable = new PdfPTable(2);
            summaryTable.setWidthPercentage(40);
            summaryTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            summaryTable.setWidths(new float[]{3f, 2f});
            

            BigDecimal tongCong = null; 
            BigDecimal giaTriKhuyenMai = BigDecimal.ZERO;

            KhuyenMaiBUS kMaiBUS = KhuyenMaiBUS.getInstance();

            
            if (hoaDon.getMaKM() != 1) {
                KhuyenMaiDTO km = kMaiBUS.getInstanceByMa(hoaDon.getMaKM()); 
                if (km.getGiaTriGiam().compareTo(BigDecimal.ONE) <0 ) {
                    // Tạo 1 - x
                    BigDecimal denominator = BigDecimal.ONE.subtract(km.getGiaTriGiam());
                    // Thực hiện phép chia: B / (1 - x)
                    tongCong = hoaDon.getTongTien().divide(denominator, 10, RoundingMode.HALF_UP);
                    giaTriKhuyenMai = tongCong.multiply(km.getGiaTriGiam());
                }
                else {
                    giaTriKhuyenMai = km.getGiaTriGiam();
                    tongCong = hoaDon.getTongTien().add(giaTriKhuyenMai);
                }
            }
            else {
                tongCong = hoaDon.getTongTien();
            }

           
            
            // Tổng cộng
            summaryTable.addCell(createRightAlignCell("Tổng cộng:", boldFont));
            summaryTable.addCell(createRightAlignCell(formatMoney(tongCong), normalFont));
            
            // Khuyến mãi
            summaryTable.addCell(createRightAlignCell("Khuyến mãi:", boldFont));
            summaryTable.addCell(createRightAlignCell(formatMoney(giaTriKhuyenMai), normalFont));


            // Phải thu
            summaryTable.addCell(createRightAlignCell("Phải thu:", boldFont));
            summaryTable.addCell(createRightAlignCell(formatMoney(hoaDon.getTongTien()), normalFont));
            
            // Khách trả
            summaryTable.addCell(createRightAlignCell("Số tiền khách trả:", boldFont));
            summaryTable.addCell(createRightAlignCell(formatMoney(hoaDon.getTongTien()), normalFont));
            
            // Thối lại
            summaryTable.addCell(createRightAlignCell("Tiền thối lại khách hàng:", boldFont));
            summaryTable.addCell(createRightAlignCell("0", normalFont));

            document.add(summaryTable);
            

            Paragraph thankYou = new Paragraph("Cảm ơn quý khách đã mua hàng!", boldFont);
            thankYou.setAlignment(Element.ALIGN_CENTER);
            thankYou.setSpacingBefore(10f);
            document.add(thankYou);

            document.close();
            System.out.println("Da luu PDF tai: " + filePath);
            JOptionPane.showMessageDialog(null, "Xuất hóa đơn thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Xuất hóa đơn thất bại: " + e.getMessage());
        }
    }

    // Hàm hỗ trợ tạo ô không viền
    private static PdfPCell createCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
    // Hàm hỗ trợ tạo ô không viền căn giữa
    private static PdfPCell createMidCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
    // Hàm hỗ trợ tạo ô header
    private static PdfPCell createHeaderCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    // Hàm định dạng tiền
    private static String formatMoney(BigDecimal amount) {
        return String.format("%,.0f", amount.doubleValue());
    }

    private static PdfPCell createRightAlignCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT); 
        cell.setBorder(Rectangle.NO_BORDER);            
        return cell;
    }
    
}
