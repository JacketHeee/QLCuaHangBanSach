package utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import DTO.CT_HoaDonDTO;
import DTO.HoaDonDTO;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Stream;

public class PDFHoaDonExporter {

    public static void exportHoaDonToPDF(HoaDonDTO hoaDon, List<CT_HoaDonDTO> chiTietList, String folderPath, String tenNhanVien) {
        try {
            String fileName = hoaDon.getMaHD() + ".pdf";
            File file = new File(folderPath, fileName);

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

    
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

            Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Mã hóa đơn: HD" + hoaDon.getMaHD(), fontNormal));
            document.add(new Paragraph("Ngày bán: " + hoaDon.getNgayBan().toString(), fontNormal));
            document.add(new Paragraph("Nhân viên lập hóa đơn: " + tenNhanVien, fontNormal));
            document.add(new Paragraph("Tổng tiền: " + hoaDon.getTongTien() + " VNĐ", fontNormal));
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(5); 
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            Stream.of("STT", "Mã Sách", "Số lượng", "Giá bán", "Thành tiền").forEach(columnTitle -> {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(1);
                header.setPhrase(new Phrase(columnTitle));
                table.addCell(header);
            });

            int stt = 1;
            for (CT_HoaDonDTO ct : chiTietList) {
                table.addCell(String.valueOf(stt++));
                table.addCell(ct.getMaSach());
                table.addCell(String.valueOf(ct.getSoLuong()));
                table.addCell(ct.getGiaBan().toString());
                table.addCell(ct.getThanhTien().toString());
            }

            document.add(table);

            document.add(new Paragraph("Cảm ơn quý khách!", fontNormal));
            document.close();

            System.out.println(" Đã xuất hóa đơn ra file: " + file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
