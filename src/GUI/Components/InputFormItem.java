package GUI.Components;

import java.awt.Color;

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
    String type; // text, pass, combobox, ...

    public InputFormItem(String content, String type){
        this.content = new JLabel(content);
        this.textField = new JTextField();
        this.initComponent();
    }

    public void initComponent(){
        this.setBackground(Color.decode("#FFFFFF"));
        this.textField.putClientProperty(FlatClientProperties.STYLE, "background: #FFFFFF; focusWidth:0; innerFocusWidth:0");
        this.setLayout(new MigLayout("wrap 1", "[grow]"));
        this.add(content, "grow");
        this.add(textField, "grow");
    }

    //set, get thẳng text trong textField
    public String getContent(){
        return(textField.getText());
    }
    
    public void setContent(String text){
        this.textField.setText(text);
    }
}
