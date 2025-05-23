package GUI.component;

import java.awt.Color;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import com.formdev.flatlaf.FlatClientProperties;
import com.toedter.calendar.JDateChooser;

import net.miginfocom.swing.MigLayout;
import utils.ImageUtil;

public class InputFormItem extends JPanel{
    private JLabel label;
    private JTextField textField;
    private JComboBox<String> combobox;
    private JDateChooser inputDate;
    private JDateChooser inputDateTime;
    private JSpinner timeSpinner;
    private JTextField textDC;
    private CustomButton btnDC;
    private JTextField textKNNN;
    private CustomButton btnKNNN;
    private CustomTextFieldSL textSL;
    private JTextArea textArea;
    private JPasswordField pass; 
    //Cho ảnh
    private PanelPicture panelPicture;

    private MigLayout migLayout;
    public String type;

    public InputFormItem(String type, String title){
        this.type = type;
        this.label = new JLabel(title);
        this.migLayout = new MigLayout("wrap 1", "[grow]");
        this.setBackground(Color.decode("#FFFFFF"));
        this.setLayout(migLayout);
        switch (type) {
            case "pass": 
                pass = new JPasswordField();
                pass.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mật khẩu");
                pass.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true");
                setPassBox();
                break; 
            case "textbox":
                this.textField = new JTextField();
                setTextBox();
                break;
            case "textArea":
                this.textArea = new JTextArea(3,30);
                textArea.setLineWrap(true);              // Tự động xuống dòng
                textArea.setWrapStyleWord(true);         // Ngắt ở khoảng trắng

                // Thiết lập kích thước cố định
                textArea.setMinimumSize(textArea.getPreferredSize());
                textArea.setMaximumSize(textArea.getPreferredSize());
                textArea.setPreferredSize(textArea.getPreferredSize());
                setTextAreaBox();
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
            case "inputKNNN":
                this.textKNNN = new JTextField();
                this.btnKNNN = new CustomButton("Tạo");
                setinputKNNN();
                break;    
            case "inputAnh":
                this.panelPicture = new PanelPicture();
                setinputAnh();
                break;
            case "inputSL":
                this.textSL = new CustomTextFieldSL();
                setInputSL();
            default:
                break;
        }
    }

    public void setTextBox(){
        this.add(label, "grow");
        this.add(textField, "grow");
    }

    public void setPassBox(){
        this.add(label, "grow");
        this.add(pass, "grow");
    }

    public String getTextPass() {
        return new String(pass.getPassword());
    }

    public void setTextPass(String pass) {
        this.pass.setText(pass);
    }

    public JPasswordField getPass() {
        return pass;
    }

    public void setTextAreaBox(){
        this.add(label, "grow");
        this.add(textArea, "grow");
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
        textDC.setEditable(false);
        JPanel panel = new JPanel(new MigLayout("wrap 2, insets 0", "[grow][]"));
        panel.putClientProperty(FlatClientProperties.STYLE, "background:#FFFFFF");
        panel.add(textDC, "grow, gapright 5");
        panel.add(btnDC, "grow");
        this.add(panel, "grow");
    }

    public void setinputKNNN(){
        this.add(label, "grow");
        textKNNN.setEditable(false);
        JPanel panel = new JPanel(new MigLayout("wrap 2, insets 0", "[grow][]"));
        panel.putClientProperty(FlatClientProperties.STYLE, "background:#FFFFFF");
        panel.add(textKNNN, "grow, gapright 5");
        panel.add(btnKNNN, "grow");
        this.add(panel, "grow");
    }

    public void setinputAnh(){
        this.setLayout(new MigLayout("wrap 1"));
        this.add(panelPicture);
    }


    public void setListCombobox(String... list){
        for(String i : list){
            this.combobox.addItem(i);
        }
    }

    public void setInputSL(){
        this.add(label, "grow");
        this.add(textSL, "grow");
    }

    public void setTextSL(String i){
        this.textSL.setText(i);
    }

    public String getTextSL(){
        return(this.textSL.getText());
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

    public String getPath(){
        return(this.panelPicture.getPath());
    }

    public ImageUtil getImageUtil() {
        return this.panelPicture.getImageUtil();
    }

    public void setAnh(String path){
        this.panelPicture.setAnh(path);
    }

    public void setTextKNNN(String text){
        this.textKNNN.setText(text);
    }

    public String getTextKNNN(){
        return(this.textKNNN.getText());
    }

    public CustomButton getBtnKNNN() {
        return btnKNNN;
    }

    public void setBtnKNNN(CustomButton btnKNNN) {
        this.btnKNNN = btnKNNN;
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

    public void setEdit(boolean i){
        this.panelPicture.setEdit(i);
    }
    
    public void setEditKNNN(boolean i){
        this.btnKNNN.setEnabled(i);
    }

    public void setLayoutConstraint(){
        this.migLayout.setLayoutConstraints("wrap 2");
        this.migLayout.setColumnConstraints("[][grow]");
    }

    public void setBackground(String color){
        this.setBackground(Color.decode(color));
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public JDateChooser getInputDate() {
        return inputDate;
    }

    public void setInputDate(JDateChooser inputDate) {
        this.inputDate = inputDate;
    }

    public JDateChooser getInputDateTime() {
        return inputDateTime;
    }

    public void setInputDateTime(JDateChooser inputDateTime) {
        this.inputDateTime = inputDateTime;
    }

    public JSpinner getTimeSpinner() {
        return timeSpinner;
    }

    public void setTimeSpinner(JSpinner timeSpinner) {
        this.timeSpinner = timeSpinner;
    }

    public void setTextDC(JTextField textDC) {
        this.textDC = textDC;
    }

    public CustomButton getBtnDC() {
        return btnDC;
    }

    public void setBtnDC(CustomButton btnDC) {
        this.btnDC = btnDC;
    }

    public void setTextKNNN(JTextField textKNNN) {
        this.textKNNN = textKNNN;
    }

    public void setTextSL(CustomTextFieldSL textSL) {
        this.textSL = textSL;
    }

    public PanelPicture getPanelPicture() {
        return panelPicture;
    }

    public void setPanelPicture(PanelPicture panelPicture) {
        this.panelPicture = panelPicture;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    
    
}
