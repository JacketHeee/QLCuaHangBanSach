package GUI.component;

import javax.swing.JButton;
import javax.swing.border.MatteBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Cursor;

import resources.base.baseTheme;

public class CustomSubTab extends JButton {
    private String text; 
    private boolean isSelected;
    private String id; 

    public CustomSubTab(String text) {
        super(text);
        this.text = text; 
        this.isSelected = false;
        init();
    }

    private void init() {
        setDefault(baseTheme.backgroundColor,baseTheme.textColor);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        
        if (isSelected == false) {
            setDefault(baseTheme.backgroundColor,baseTheme.textColor);
        }
        else {
            setDefault(baseTheme.backgroundColor,baseTheme.mainColor);
        }       
    }

    public void setDefault(String color,String colorText) {
        setForeground(Color.decode(colorText));
        setBackground(Color.decode(baseTheme.backgroundColor));
        MatteBorder bottomBorder = new MatteBorder(0,0,2,0,Color.decode(color));
        EmptyBorder padding = new EmptyBorder(8,20,8,20);
        putClientProperty("arc: ", 0);
        setBorder(BorderFactory.createCompoundBorder(bottomBorder, padding));
        this.repaint();
        this.revalidate();
    }
}
