package GUI.Components;

import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;

public class InputFormItem extends JPanel{
    JLabel content;
    JTextField textField;
    JPasswordField passwordField;
    JComboBox comboBox;
    String type; // text, pass, combobox, ...
    String[] itemComboBox;

    public InputFormItem(String content, String type, String... itemComboBox){
        this.content = new JLabel(content);
        this.textField = new JTextField();
        this.type = type;
        this.itemComboBox = itemComboBox;
        this.initComponent();
    }

    public void initComponent(){
        this.setBackground(Color.decode("#FFFFFF"));
        this.textField.putClientProperty(FlatClientProperties.STYLE, "background: #FFFFFF; focusWidth:0; innerFocusWidth:0");
        this.setLayout(new MigLayout("wrap 1", "[grow]"));
        this.add(content, "grow");

        if(this.type.equals("text")){
            this.add(textField, "grow");
        }
        else if(this.type.equals("pass")){

        }
        else if(this.type.equals("combobox")){
            comboBox = new JComboBox<String>(itemComboBox);
            this.add(comboBox);
        }
    }

    //set, get thẳng text trong textField
    public String getContent(){
        return(textField.getText());
    }
    
    public void setContent(String text){
        this.textField.setText(text);
    }

    public JComboBox getComboBox() {
        return comboBox;
    }

    public void setComboBox(JComboBox comboBox) {
        this.comboBox = comboBox;
    }

    public String getSelection(){
        return((String)this.comboBox.getSelectedItem());
    }

    public void setSelection(String str){
        this.comboBox.setSelectedItem(str);
    }
    
}
