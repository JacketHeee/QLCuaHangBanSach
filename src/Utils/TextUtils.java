package utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class TextUtils {
    public static String boDau(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("").replaceAll("đ", "d").replaceAll("Đ", "D");
    }

    public static String orverFlowText(String text, int limit, int countCol) {
        int lim = (int) limit/(countCol);
        if (text!=null && text.length() > lim) {
            text = text.substring(0, lim-3) + "...";
        }
        return text;
    }
}
