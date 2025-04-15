package GUI.component;

import java.awt.Color;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import com.formdev.flatlaf.FlatClientProperties;
import com.toedter.calendar.JDateChooser;

import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;

public class InputFormItem extends JPanel{
    private JLabel label;
    private JTextField textField;
    private JComboBox<String> combobox;
    private JDateChooser inputDate;
    // private JFormattedTextField numberField;
    private JDateChooser inputDateTime;
    private JSpinner timeSpinner;
    private JTextField textDC;
    private CustomButton btnDC;

    public InputFormItem(String type, String title){
        this.label = new JLabel(title);
        this.setBackground(Color.decode("#FFFFFF"));
        this.setLayout(new MigLayout("wrap 1", "[grow]"));
        switch (type) {
            case "textbox":
                this.textField = new JTextField();
                setTextBox();
                break;
            case "combobox":
                this.combobox = new JComboBox<>();
                setCombobox();
                break;
            case "inputDate":
                this.inputDate = new JDateChooser();
                inputDate.setDateFormatString("dd/MM/yyyy");
                setInputDate();
                break;
            // case "number":
            //     this.numberField = new JFormattedTextField();
            //     setInputNumber();
            //     break;
            case "inputDateTime":
                this.inputDateTime = new JDateChooser();
                inputDateTime.setDateFormatString("dd/MM/yyyy");
                SpinnerDateModel timeModel = new SpinnerDateModel();
                this.timeSpinner = new JSpinner(timeModel);
                JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
                timeSpinner.setEditor(timeEditor);
                //set ngày hiện tại
                timeSpinner.setValue(new java.util.Date());
                setInputDateTime();
                break;
            case "inputDC":
                this.textDC = new JTextField();
                this.btnDC = new CustomButton("Tạo");
                setinputDC();
                break;
            default:
                break;
        }
    }

    public void setTextBox(){
        this.add(label, "grow");
        this.add(textField, "grow");
    }

    public void setCombobox(){
        this.combobox.setEditable(false);
        this.add(label, "grow");
        this.add(combobox, "grow");
    }

    public void setInputDate(){
        this.add(label, "grow");
        this.add(inputDate, "grow");
    }

    // public void setInputNumber(){
    //     this.add(label, "grow");
    //     this.add(numberField, "grow");
    // }

    public void setInputDateTime(){
        this.add(label, "grow");
        //ô nhập Ngày
        this.add(inputDateTime, "grow");
        //ô nhập thời gian
        this.add(timeSpinner, "grow");
    }

    public void setinputDC(){
        this.add(label, "grow");
        JPanel panel = new JPanel(new MigLayout("wrap 2, insets 0", "[grow][]"));
        panel.putClientProperty(FlatClientProperties.STYLE, "background:#FFFFFF");
        panel.add(textDC, "grow, gapright 5");
        panel.add(btnDC, "grow");
        this.add(panel, "grow");
    }

    public void setListCombobox(String... list){
        for(String i : list){
            this.combobox.addItem(i);
        }
    }

    public String getText(){
        return(this.textField.getText());
    }

    public void setText(String i){
        this.textField.setText(i);
    }

    public String getTextDC(){
        return(this.textDC.getText());
    }

    public void setTextDC(String i){
        this.textDC.setText(i);
    }

    public CustomButton getButtonDC(){
        return(this.btnDC);
    }

    public String getSelection(){
        return((String)combobox.getSelectedItem());
    }

    public void setSelection(String text){
        for(int i = 0; i < combobox.getItemCount(); i++){
            if(combobox.getItemAt(i).equals(text)){
                combobox.setSelectedIndex(i);
                return;
            }
        }
        System.out.println("Lỗi không thấy giá trị combobox");
    }

    public java.sql.Date getDate(){
        java.util.Date utilDate = inputDate.getDate();
        java.sql.Date sqlDate = new Date(utilDate.getTime());
        return(sqlDate);
    }

    public String getDateString(){
        String result = ((JTextField) inputDate.getDateEditor().getUiComponent()).getText();
        return(result);  
    }

    public void setDate(java.sql.Date sqlDate){
        inputDate.setDate(sqlDate);
    }

    public void setDateByString(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date utilDate = new java.util.Date();
        try {
            utilDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        inputDate.setDate(utilDate);
    }

    // public void setNumber(String t){
    //     this.numberField.setText(t);
    // }

    // public String getNumber(){

    // }
    //Lấy ngày kiểu string từ dateTime
    public String getDateTimeString(){
        String result = ((JTextField) inputDateTime.getDateEditor().getUiComponent()).getText();
        System.out.println(result);
        return(result);
    }

    public String getDateTimeTimeString(){
        java.util.Date time = (java.util.Date)timeSpinner.getValue();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timeText = sdf.format(time);

        return(timeText);
    }

    public void setDateTime(LocalDateTime dateTime){   //Chưa test
        //set ngày
        java.util.Date fullDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        inputDateTime.setDate(fullDate);
        //set thời gian
        Calendar cal = Calendar.getInstance();
        cal.setTime(fullDate);
        // reset về mặc định tránh lỗi
        cal.set(1970, Calendar.JANUARY, 1);
        
        timeSpinner.setValue(cal.getTime());
    }

    public void setDateTimeByString(String dateTimeS){   //Chưa test
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeS, formatter);
        //set ngày
        java.util.Date fullDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        inputDateTime.setDate(fullDate);
        //set thời gian
        Calendar cal = Calendar.getInstance();
        cal.setTime(fullDate);
        // reset về mặc định tránh lỗi
        cal.set(1970, Calendar.JANUARY, 1);
        
        timeSpinner.setValue(cal.getTime());
    }

    public LocalDateTime getDateTime(){
        java.util.Date datePart = inputDateTime.getDate();
        java.util.Date timePart = (java.util.Date)timeSpinner.getValue();

        //Kết hợp ngày giờ
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(datePart);
        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(timePart);

        dateCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
        dateCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
        dateCal.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));

        java.util.Date combinedDate = dateCal.getTime();
        LocalDateTime ldt = combinedDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return(ldt);
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }

    public JComboBox<String> getCombobox() {
        return combobox;
    }

    public void setCombobox(JComboBox<String> combobox) {
        this.combobox = combobox;
    }

    

}
