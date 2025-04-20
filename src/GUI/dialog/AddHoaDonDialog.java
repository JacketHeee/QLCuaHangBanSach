package GUI.dialog;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.Toolkit;

import GUI.MainFrame;
import GUI.component.CustomScrollPane;
import GUI.forms.TaoHoaDonForm;

public class AddHoaDonDialog extends JDialog{
    private MainFrame mainFrame;

    public AddHoaDonDialog(MainFrame mainFrame) {
        super(mainFrame,"",true);
        this.mainFrame = mainFrame;
        add(new CustomScrollPane(new TaoHoaDonForm(mainFrame)));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 1200;
        int height = screenSize.height;
        setSize(width,height);
        setLocationRelativeTo(mainFrame);
        setVisible(true);
    } 
}
