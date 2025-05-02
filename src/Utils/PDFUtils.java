package utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import DTO.CT_HoaDonDTO;
import DTO.HoaDonDTO;
import DAO.TaiKhoanDAO;

import javax.swing.*;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;

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
            Rectangle a6Size = new Rectangle(14.5f * 28.35f, 10.5f * 28.35f);
            Document document = new Document(a6Size, 10, 10, 10, 10);      
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 8, Font.BOLD);
            Font normalFont = new Font(baseFont, 6, Font.NORMAL);
            Font boldFont = new Font(baseFont, 7, Font.BOLD);

            // Title
            Paragraph title = new Paragraph("HÓA ĐƠN BÁN LẺ", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            Paragraph separator = new Paragraph("--------------------------------------------------", normalFont);
            separator.setAlignment(Element.ALIGN_CENTER);
            document.add(separator);

            // Thông tin hóa đơn
            String tenNhanVien = TaiKhoanDAO.getInstance().getUsernameByMaTK(hoaDon.getMaTK());

            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingBefore(10f);
            infoTable.setSpacingAfter(10f);
            float[] columnWidths = {1f, 1f};
            infoTable.setWidths(columnWidths);

            infoTable.addCell(createCell("So HD: " + hoaDon.getMaHD(), boldFont));
            infoTable.addCell(createCell("Gio: " + hoaDon.getNgayBan().toLocalTime().toString(), boldFont));
            infoTable.addCell(createCell("Ngay: " + hoaDon.getNgayBan().toLocalDate().toString(), boldFont));
            infoTable.addCell(createCell("Nhan Vien: " + tenNhanVien, boldFont));

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
                table.addCell(new PdfPCell(new Phrase(ct.getTenSach(), normalFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(ct.getSoLuong()), normalFont)));
                table.addCell(new PdfPCell(new Phrase(formatMoney(ct.getGiaBan()), normalFont)));
                table.addCell(new PdfPCell(new Phrase(formatMoney(ct.getGiaBan().multiply(new BigDecimal(ct.getSoLuong()))), normalFont)));
            }

            document.add(table);
            // Tổng tiền
            Paragraph tong = new Paragraph("Tong: " + formatMoney(hoaDon.getTongTien()), boldFont);
            tong.setAlignment(Element.ALIGN_RIGHT);
            document.add(tong);

            Paragraph thankYou = new Paragraph("Cảm ơn quý khách đã mua hàng!", normalFont);
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

    // Hàm hỗ trợ tạo ô header
    private static PdfPCell createHeaderCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        return cell;
    }

    // Hàm định dạng tiền
    private static String formatMoney(BigDecimal amount) {
        return String.format("%,.0f", amount.doubleValue());
    }
}
