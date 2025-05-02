package GUI.component;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;

import com.formdev.flatlaf.FlatClientProperties;

public class CustomButton extends JButton{
    
    public CustomButton() {
        super();
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        putClientProperty(FlatClientProperties.STYLE, "focusWidth: 0; innerFocusWidth: 0");
    }

    public CustomButton(String txt) {
        super(txt);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        putClientProperty(FlatClientProperties.STYLE, "focusWidth: 0; innerFocusWidth: 0");
    }

    public void cssSaveButton() {
        // but.setBorder(new EmptyBorder(10,5,10,5));
        setBackground(Color.decode("#4882FF"));
        setBackground(Color.white);
        repaint();
        revalidate();
    }

    public void cssCancelButton() {
        // but.setBorder(new EmptyBorder(10,5,10,5));
        setBackground(Color.decode("#f"));
        setBackground(Color.white);
        repaint();
        revalidate();
    }
}
