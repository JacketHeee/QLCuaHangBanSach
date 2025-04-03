// package GUI.Components;

// import java.awt.Color;
// import java.util.Calendar;
// import java.util.Date;
// import java.time.LocalDateTime;
// import java.time.ZoneId;

// import javax.swing.JComboBox;
// import javax.swing.JLabel;
// import javax.swing.JPanel;
// import javax.swing.JPasswordField;
// import javax.swing.JSpinner;
// import javax.swing.JTextField;
// import javax.swing.SpinnerDateModel;

// import com.formdev.flatlaf.FlatClientProperties;
// import com.toedter.calendar.JDateChooser;

// import net.miginfocom.swing.MigLayout;

// public class InputFormItem extends JPanel{
//     JLabel content;
//     JTextField textField;
//     JPasswordField passwordField;
//     JComboBox comboBox;
//     JDateChooser dateChooser;
//     SpinnerDateModel timeModel;
//     JSpinner timeSpinner;
//     String type; // text, pass, combobox, ...
//     String[] itemComboBox;

//     public InputFormItem(String content, String type, String... itemComboBox){
//         this.content = new JLabel(content);
//         this.textField = new JTextField();
//         this.type = type;
//         this.itemComboBox = itemComboBox;
//         this.initComponent();
//     }

//     public void initComponent(){
//         this.setBackground(Color.decode("#FFFFFF"));
//         this.textField.putClientProperty(FlatClientProperties.STYLE, "background: #FFFFFF; focusWidth:0; innerFocusWidth:0");
//         this.setLayout(new MigLayout("wrap 1", "[grow]"));
//         this.add(content, "grow");

//         if(this.type.equals("text")){
//             this.add(textField, "grow");
//         }
//         else if(this.type.equals("pass")){

//         }
//         else if(this.type.equals("combobox")){
//             comboBox = new JComboBox<String>(itemComboBox);
//             this.add(comboBox);
//         }
//         else if(this.type.equals("calendar")){
//             dateChooser = new JDateChooser();
//             timeModel = new SpinnerDateModel();
//             timeSpinner = new JSpinner(timeModel);

//             dateChooser.setDateFormatString("dd/MM/yyyy");
//             JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
//             timeSpinner.setEditor(timeEditor);
//             this.add(dateChooser);
//             this.add(timeSpinner);
//         }
//     }

//     //set, get thẳng text trong textField
//     public String getContent(){
//         return(textField.getText());
//     }
    
//     public void setContent(String text){
//         this.textField.setText(text);
//     }

//     public JComboBox getComboBox() {
//         return comboBox;
//     }

//     public void setComboBox(JComboBox comboBox) {
//         this.comboBox = comboBox;
//     }

//     public String getSelection(){
//         return((String)this.comboBox.getSelectedItem());
//     }

//     public void setSelection(String str){
//         this.comboBox.setSelectedItem(str);
//     }

//     // lấy string để validate
//     public String getDateS(){
//         //Lấy chuỗi từ JTextField
//         JTextField textField = (JTextField)dateChooser.getDateEditor().getUiComponent();
//         return(textField.getText());

//     }

//     public LocalDateTime getDateTime(){
//         Date selectedDate = dateChooser.getDate();
//         Date selectedTime = (Date)timeSpinner.getValue();

//         Calendar calendar = Calendar.getInstance();
//         calendar.setTime(selectedDate);

//         Calendar timeCalendar = Calendar.getInstance();
//         timeCalendar.setTime(selectedTime);

//         calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
//         calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
//         calendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));

//         return calendar.getTime().toInstant().
//         atZone(ZoneId.systemDefault()).
//         toLocalDateTime();
//     }

//     public void setDateTime(LocalDateTime dateTime){
//         // chuyển đổi thừ localdatetime sang date
//         Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

//         //Cài đặt cho JDateChooser
//         dateChooser.setDate(date);
//         //Cài đặt cho JSpinner
//         Calendar calendar = Calendar.getInstance();
//         calendar.setTime(date);
//         timeSpinner.setValue(calendar.getTime());
//     }
    
// }
