package utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        //cụm 1: Bắt đầu ít nhất 1 chữ hoa/thường/số 
        //cụm 2: có thể không có hoặc có 1 hoặc nhiều cụm ".xxx", dấu chấm xuất hiện 0 hoặc 1 lần
        //cụm 3: bắt buộc xuất hiện một dấu @, cụm sau xuất hiện ít nhất 1 ký tự 
        //cụm 4: bắt buộc xuất hiện 1 dấu '.' theo sau có ít nhất 1 ký tự, cụm 4 xuất hiện ít nhất 1 lần
        //abc@gmail.com;                         [   cụm 1   ][       cụm 2        ][    cụm 3  ][      cụm 4        ]              
        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+([\\._]?[A-Za-z0-9]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)+$");
        Matcher matcher = pattern.matcher(i);
        return matcher.matches();
    }

    public static boolean isPhoneNumber(String i){
        //cụm 1: có thể có dạng (xxx) hoặc xxx, bắt đầu bằng số 0, theo sau một số từ 1-9, tiếp tục một số từ 0-9
        //cụm 2: Bắt đầu có thể có hoặc không 1 trong 3 ký tự 'dấu cách', dấu '.', dấu '-' (các dấu này thuộc nhóm số 4), theo sau là 3 chữ số
        //cụm 3: Bắt đầu = ký tự giống với nhóm số 4, theo sau là 4 chữ số
        //                                       [            cụm 1             ][      cụm 2       ][    cụm 3  ]   
        Pattern pattern = Pattern.compile("^((\\(0[1-9]\\d\\))|(0[1-9]\\d))([\\s\\.-]?)(\\d{3})\\4(\\d{4})$");
        Matcher matcher = pattern.matcher(i);
        return matcher.matches();
    }

    public static boolean isYear(String i){
        Pattern pattern = Pattern.compile("^(19|20)\\d{2}$");
        Matcher matcher = pattern.matcher(i);
        return matcher.matches();
    }

    public static boolean isDate(String dateStr){ 
        if(!dateStr.matches("\\d{1,2}/\\d{1,2}/\\d{4}")){
            return(false);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        try {
            LocalDate date = LocalDate.parse(dateStr, formatter);
            int year = date.getYear();
            return year >= 1900 && year <= 2099;
        } catch (Exception e) {
            return(false);
        }
    }

    public static boolean lengthGreaterThan(String s, int i){
        if(s.length() > i){
            return(true);
        }
        return(false);
    }

    public static boolean isStartDateAndEndDate(String startDateS, String endDateS){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date startDate;
        java.util.Date endDate;
        try {
            startDate = dateFormat.parse(startDateS);
            endDate = dateFormat.parse(endDateS);
            return(startDate.before(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Lỗi định dạng");
            return(false);
        }
    }

    public static boolean isStartTimeAndEndTime(String timeStartS, String timeEndS){
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        
        java.util.Date timeStart;
        java.util.Date timeEnd;
        try {
            timeStart = timeFormat.parse(timeStartS);
            timeEnd = timeFormat.parse(timeEndS);
            return(timeStart.before(timeEnd));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Lỗi định dạng");
            return(false);
        }

    }

    // public static boolean isNgayBatDauNgayKetThuc(String ngayBatDau, String ngayKetThuc){
    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    //     LocalDate startDate = LocalDate.parse(ngayBatDau, formatter);
    //     LocalDate endDate = LocalDate.parse(ngayKetThuc, formatter);

    //     if(startDate.isBefore(endDate) || startDate.isEqual(endDate)){// có thể bằng ngày kết thúc
    //         return(true);
    //     }
    //     return(false);
    // }

    public static boolean isGiaBatDauGiaKetThuc(String giaBatDau, String giaKetThuc){
        BigDecimal giaBD = BigDecimal.valueOf(Double.parseDouble(giaBatDau));
        BigDecimal giaKT = BigDecimal.valueOf(Double.parseDouble(giaKetThuc));
        return(giaBD.compareTo(giaKT) < 0);
    }

    public static boolean isBetweenStartDateAndEndDate(String ngayBatDau, String ngayKetThuc, LocalDateTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate timeHD = time.toLocalDate();
        LocalDate startDate = LocalDate.parse(ngayBatDau, formatter);
        LocalDate endDate = LocalDate.parse(ngayKetThuc, formatter);

        if(startDate.isBefore(timeHD) && endDate.isAfter(timeHD) || startDate.isEqual(timeHD) || endDate.isEqual(timeHD)){
            return(true);
        }
        return(false);
    }

    public static boolean isBetweenTuGiaDenGia(String tienFst, String tienScn, BigDecimal tien){
        BigDecimal tien1 = BigDecimal.valueOf(Double.parseDouble(tienFst));
        BigDecimal tien2 = BigDecimal.valueOf(Double.parseDouble(tienScn));

        if(tien1.compareTo(tien) < 0 && tien2.compareTo(tien) > 0 || tien1.compareTo(tien) == 0 || tien2.compareTo(tien) == 0){
            return(true);
        }
        return(false);
    }

    public static void main(String[] args) {
        // String[] string = {"(023)1231234","(023)-123-1234","023-123-1234","023.123.1234","023 123 1234","0231231234"};
        // String[] string2 = {"(02312312334","(023)-1231234","(023)123-1234"};

        // for(String i : string){
        //     System.out.println(Validate.isPhoneNumber(i));
        // }
        // for(String i : string2){
        //     System.out.println(Validate.isPhoneNumber(i));
        // }
        System.out.println(new Validate().isDate("22/02/2024"));



    }

}
