package GUI.component;

import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class InputFormItem extends JPanel{
    private JLabel label;
    private JTextField textField;
    private JComboBox<String> combobox;
    // private // date tutu

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

            default:
                break;
        }
    }

    public void setTextBox(){
        this.add(label, "grow");
        this.add(textField, "grow");
    }

    public void setCombobox(){
        this.add(label, "grow");
        this.add(combobox, "grow");
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

    public String getSelection(){
        return((String)combobox.getSelectedItem());
    }

}
