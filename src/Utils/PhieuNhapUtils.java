package utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import DAO.NhaCungCapDAO;
import DAO.NhanVienDAO;
import DAO.SachDAO;
import DAO.TaiKhoanDAO;
import DTO.PhieuNhapDTO;
import DTO.CT_PhieuNhapDTO;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.text.html.HTMLDocument;

public class PhieuNhapUtils {

    public static void taoPhieuNhapPDF(PhieuNhapDTO phieuNhap, List<CT_PhieuNhapDTO> listCTPN) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu phiếu nhập PDF");
        fileChooser.setSelectedFile(new File("PhieuNhap_" + phieuNhap.getMaNhap() + ".pdf"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            System.out.println("Đã hủy lưu file.");
            return;
        }

        File selectedFile = fileChooser.getSelectedFile();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
            document.open();

            // Font
            BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 15, Font.BOLD);
            Font normalFont = new Font(baseFont, 10, Font.NORMAL);
            Font boldFont = new Font(baseFont, 12, Font.BOLD);

            // Tiêu đề
            Paragraph title = new Paragraph("CHI TIẾT PHIẾU NHẬP", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            Paragraph info = new Paragraph("------------------------------------------------------------", normalFont);
            info.setAlignment(Element.ALIGN_CENTER);
            document.add(info);
            // Thông tin chung
            String tenNhanVien = NhanVienDAO.getInstance().gettenNVByMaTK(phieuNhap.getMaTK());
            String tenNCC = NhaCungCapDAO.getInstance().getNCCnameByMaNCC(phieuNhap.getMaNCC());


            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            float[] columnWidths = {1f, 1f};
            infoTable.setWidths(columnWidths);

            infoTable.addCell(createCell("Nhan Vien: " + tenNhanVien, boldFont));
            infoTable.addCell(createCell("Nha cung cap: " + tenNCC , boldFont));
            infoTable.addCell(createCell("Ma phieu nhap: " + phieuNhap.getMaNhap() , boldFont));
            infoTable.addCell(createCell("Ngay: " + phieuNhap.getNgayNhap().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")), boldFont));
            document.add(infoTable);
            // Bảng chi tiết
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1.2f, 3.5f, 1.5f, 2f, 2.5f});
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Header
            String[] headers = {"Mã sách", "Tên sách", "Số lượng", "Giá nhập", "Thành tiền"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, boldFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setMinimumHeight(25f);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            BigDecimal tongTien = BigDecimal.ZERO;

            for (CT_PhieuNhapDTO ctpn : listCTPN) {
                int maSach = ctpn.getmaSach();
                String tenSach = SachDAO.getInstance().getTenByMa(maSach);
                int soLuong = ctpn.getSoLuongNhap();
                BigDecimal giaNhap = ctpn.getGiaNhap();
                BigDecimal thanhTien = giaNhap.multiply(BigDecimal.valueOf(soLuong));
                tongTien = tongTien.add(thanhTien);

                table.addCell(createMidCell(String.valueOf(maSach),normalFont));
                table.addCell(Cell(tenSach, normalFont));
                table.addCell(createMidCell(String.valueOf(soLuong),normalFont));
                table.addCell(createMidCell(String.format("%,.0f", giaNhap),normalFont));
                table.addCell(createMidCell(String.format("%,.0f", thanhTien),normalFont));
            }

            document.add(table);

            Paragraph tongTienPara = new Paragraph("Tổng tiền: " + String.format("%,.0f", tongTien) + "đ", boldFont);
            tongTienPara.setAlignment(Element.ALIGN_RIGHT);
            document.add(tongTienPara);

            document.close();
            JOptionPane.showMessageDialog(null, "Xuất phiếu nhập PDF thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tạo PDF: " + e.getMessage());
        }
    }
    private static PdfPCell createCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);            
        return cell;
        }
    
    // Hàm hỗ trợ tạo ô không viền
    private static PdfPCell Cell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));   
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(20f);       
        return cell;
    }
    // Hàm hỗ trợ tạo ô không viền căn giữa
    private static PdfPCell createMidCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(20f);
        return cell;
    }
}
