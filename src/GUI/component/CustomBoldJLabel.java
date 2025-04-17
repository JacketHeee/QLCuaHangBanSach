package GUI.component;

import javax.swing.JLabel;

public class CustomBoldJLabel extends JLabel{

    public CustomBoldJLabel(String text, int sizeFont) {
        super(String.format("<html><b><font size='+%d'>%s</font></b></html>",sizeFont,text));
    }
    
}
