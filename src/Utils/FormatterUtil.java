package utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatterUtil {
    public static String formatNumberVN(BigDecimal number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("vi", "VN"));
        symbols.setGroupingSeparator('.'); // dùng dấu . cho phần ngàn
        DecimalFormat df = new DecimalFormat("#,##0", symbols);
        return df.format(number) + "₫";
    }

    public static String formatDateTime(LocalDateTime currentDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime;
    }


    public static void main(String[] args) {
        System.out.println(formatNumberVN(new BigDecimal(1298749298)));
    }
}