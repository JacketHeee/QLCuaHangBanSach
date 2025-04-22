package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    // public static boolean isDate(String i){ 
    //     String yearS = new String(); //Validate năm nhuận
    //     try {
    //         yearS = i.substring(i.length() - 4);
    //     } catch (Exception e) {
    //         System.out.println("Chuỗi chưa tới 4 ký tự");
    //         return(false);
    //     }
    //     if(!isPositiveNumber(i)){
    //         return(false);
    //     }
    //     int year = Integer.parseInt(yearS);
    //     //Năm nhuận
    //     if(year % 4 == 0){

    //     }
    //     // System.out.println(year);
    //     // Cụm 1: Cho ngày + / + tháng cho những tháng có 31 ngày (1, 3, 5, 7, 8, 10, 12)
    //     // Cụm 2: Cho ngày + / + tháng cho những tháng có 30 ngày (4, 6, 9, 11)
    //     // Cụm 3: Cho ngày + / + tháng cho tháng 2 có 28 ngày
    //     //                                        [                                               Ngày + tháng                                                   ][      Năm     ]
    //     //                                         [           Cụm 1                          ][             Cụm 2                ][        Cụm 3                ]                    
    //     Pattern pattern = Pattern.compile("^((0?[1-9]|[12][0-9]|3[01])/(0?[13578]|1[02])|(0?[1-9]|[12][0-9]|30)/(0?[469]|11)|(0?[1-9]|1[0-9]|2[0-8])/(0?2))/(19|20)\\d{2}$");
    //     Matcher matcher = pattern.matcher(i);
    //     return matcher.matches();
    // }

    public static boolean isDate(String dateStr) { //Kết hợp regex cơ bản + xử lý logic
        // Kiểm tra định dạng cơ bản bằng regex
        if (!dateStr.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) { //kiểm tra xem có đúng định dang chuỗi chưa
            return false;
        }

        // Sử dụng DateTimeFormatter với định dạng linh hoạt
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        try {
            LocalDate date = LocalDate.parse(dateStr, formatter); //dùng LocalDate chuyển đổi từ chuỗi sang LocalDate theo format ngày,
            int year = date.getYear();
            return year >= 1900 && year <= 2099;
        } catch (DateTimeParseException e) { //nếu chuyển không thành công có nghĩa chuỗi đúng với định dạng ngày, nhưng ngày không hợp lệ
            return false;
        }
    }
    /* Công việc kiểm tra isDate chia ra làm 2 công việc: 
        1. Kiểm tra định dạng ngày (dùng regex)
        2. Kiểm tra tính hợp lệ của ngày (năm nhuận, 28, 29 ngày ...) Tận dụng sự chuyển đổi từ String->LocalDate
//////////////
Những gì LocalDate.parse kiểm tra
Khi bạn gọi LocalDate.parse(dateStr, formatter) với một chuỗi dateStr (ví dụ: "1/1/2025") và một DateTimeFormatter (ví dụ: ofPattern("d/M/yyyy")), quá trình phân tích sẽ:

    Kiểm tra định dạng chuỗi:
        Chuỗi dateStr phải khớp với mẫu định dạng được khai báo trong formatter.
        Ví dụ: Với DateTimeFormatter.ofPattern("d/M/yyyy"), chuỗi phải có dạng:
        Ngày: 1 hoặc 2 chữ số (1-31).
        Tháng: 1 hoặc 2 chữ số (1-12).
        Năm: 4 chữ số (ví dụ: 2025).
        Phân tách bằng dấu /.
        Nếu định dạng không khớp (ví dụ: "1-1-2025" hoặc "abc"), DateTimeParseException sẽ được ném ra.

    Kiểm tra tính hợp lệ của ngày tháng:
        Sau khi phân tích chuỗi thành ngày, tháng, năm, LocalDate sẽ kiểm tra xem các giá trị này có tạo thành một ngày hợp lệ hay không. Cụ thể:
        Tháng: Phải nằm trong khoảng 1-12.
        Ngày: Phải hợp lệ với tháng và năm tương ứng:
        Các tháng 1, 3, 5, 7, 8, 10, 12: Tối đa 31 ngày.
        Các tháng 4, 6, 9, 11: Tối đa 30 ngày.
        Tháng 2:
        Tối đa 28 ngày trong năm không nhuận.
        Tối đa 29 ngày trong năm nhuận (năm nhuận là năm chia hết cho 4, nhưng không chia hết cho 100, trừ khi chia hết cho 400).
        Năm: LocalDate hỗ trợ các năm trong khoảng rất rộng (thường từ -999,999,999 đến +999,999,999), nên hầu như không có giới hạn thực tế về năm.
        Nếu ngày không hợp lệ, ví dụ:
        "31/4/2025" (tháng 4 chỉ có 30 ngày).
        "29/2/2021" (2021 không phải năm nhuận, nên ngày 29/2 không hợp lệ).
        thì DateTimeParseException sẽ được ném ra.
        
    Tạo đối tượng LocalDate:
        Nếu tất cả các kiểm tra trên đều vượt qua, LocalDate.parse sẽ tạo ra một đối tượng LocalDate biểu diễn ngày tháng hợp lệ.
        Ví dụ: "1/1/2025" sẽ được chuyển thành LocalDate biểu diễn ngày 1 tháng 1 năm 2025.
     */

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
