package utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class FormatterUtil {
    public static String formatNumberVN(BigDecimal number) {
        if (number.compareTo(BigDecimal.ONE) >=0 ) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("vi", "VN"));
            symbols.setGroupingSeparator('.'); // dùng dấu . cho phần ngàn
            DecimalFormat df = new DecimalFormat("#,##0", symbols);
            return df.format(number) + "đ";
        }
        else {
            BigDecimal percent = number.multiply(new BigDecimal("100"));
            return percent.stripTrailingZeros().toPlainString() + "%";
        }
    }

    public static String formatDateTime(LocalDateTime currentDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime;
    }

    public static boolean checkTonTai(ArrayList<Integer> arr, int value) {
        for (Integer x : arr) 
            if (x == value) return true;
        return false;
    }

    public static String formatAsPercent(BigDecimal value) {
        if (value == null) return "";
        
        BigDecimal percentValue = value.multiply(BigDecimal.valueOf(100));
        return percentValue.stripTrailingZeros().toPlainString() + "%";
    }


    public static void main(String[] args) {
        System.out.println(formatAsPercent(new BigDecimal("0.05")));
    }
}