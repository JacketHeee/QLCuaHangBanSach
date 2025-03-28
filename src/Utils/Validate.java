package Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {

    //hàm isEmpty mặc định không kiểm tra chuỗi khoảng trắng và có lỗi nullpointerexception
    public static boolean isEmpty(String i){
        if(i == null){
            return(true);
        }
        if(i.trim().equals("")){
            return(true);
        }
        return(false);
    }
    //Kiểm tra số dương
    public static boolean isPositiveNumber(String i){
        try {
            double number = Double.parseDouble(i);
            if(number > 0){
                return (true);
            }
            return(false);
        } catch (Exception e) {
            return(false);
        }
    }

    public static boolean isEmail(String i){
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$");
        Matcher matcher = pattern.matcher(i);
        return matcher.matches();
    }

    //Kiểm tra theo kiểu (xxx)xxxxxxx
    // (xxx).xxx.xxxx
    // (xxx)-xxx-xxxx
    // xxx-xxx-xxxx
    // xxx.xxx.xxxx
    // xxx xxx xxxx
    // xxxxxxxxxx
    public static boolean isPhoneNumber(String i){
        Pattern pattern = Pattern.compile("^((\\([0-9]{3}\\))|([0-9]{3}))([ .-]?)([0-9]{3})\\4([0-9]{4})$");
        Matcher matcher = pattern.matcher(i);
        return matcher.matches();
    }

    public static boolean isYear(String i){
        Pattern pattern = Pattern.compile("^(\\d{4})$");
        Matcher matcher = pattern.matcher(i);
        return matcher.matches();
    }

    // public static void main(String[] args) {
    //     String[] string = {"(123)1231234","(123)-123-1234","123-123-1234","123.123.1234","123 123 1234","1231231234"};
    //     String[] string2 = {"(12312312334","(123)-1231234","(123)123-1234"};

    //     for(String i : string){
    //         System.out.println(Validate.isPhoneNumber(i));
    //     }
    //     for(String i : string2){
    //         System.out.println(Validate.isPhoneNumber(i));
    //     }
    // }

}
