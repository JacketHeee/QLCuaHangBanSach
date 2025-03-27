package GUI.component;

import javax.swing.JButton;
import java.awt.Cursor;

public class CustomButton extends JButton{
    
    public CustomButton() {
        super();
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public CustomButton(String txt) {
        super(txt);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
