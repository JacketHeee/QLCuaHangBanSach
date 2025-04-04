package GUI.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Insets;

import javax.swing.JButton;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import resources.base.baseTheme;

public class ButtonAction extends JButton{

    private String text; 
    private String iconImg;
    private String id;
    
    public ButtonAction(String text, String iconImg, String id) {
        super(text,new FlatSVGIcon(ButtonAction.class.getResource("../../resources/img/icon/"+iconImg)).derive(20,20));
        this.text = text; 
        this.iconImg = iconImg;
        this.id = id;
        init();
        setBackgroundButton();
    }

    private void init() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        putClientProperty(FlatClientProperties.STYLE, "focusWidth: 0; innerFocusWidth: 0; borderWidth: 0");
        setIconTextGap(10);                
        setMargin(new Insets(5, 10, 5, 10));
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setIcon(String icon) {
        this.iconImg = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private void setBackgroundButton() {
        switch (id) {
            case "add":
                setBackground(Color.decode(baseTheme.mainColor));
                setForeground(Color.white);
                break;
            
            case "importExcel": 
                setBackground(Color.decode("#FDE1C3"));
                setForeground(Color.decode(baseTheme.textColor));
                break;

            case "exportExcel": 
                setBackground(Color.decode("#A8E8B9"));
                setForeground(Color.decode(baseTheme.textColor));
                break;
            default:
                break;
        }
    }

    
}
