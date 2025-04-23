package GUI.dialog;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.Toolkit;

import GUI.MainFrame;
import GUI.component.CustomScrollPane;
import GUI.forms.TaoHoaDonForm;

public class AddHoaDonDialog extends JDialog{
    private TaoHoaDonForm taoHoaDonForm;

    public AddHoaDonDialog(MainFrame mainFrame) {
        super(mainFrame,"",true);
        this.taoHoaDonForm = new TaoHoaDonForm(mainFrame);
        add(new CustomScrollPane(taoHoaDonForm));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 1200;
        int height = screenSize.height;
        setSize(width,height);
        setLocationRelativeTo(mainFrame);
    }

    public TaoHoaDonForm getTaoHoaDonForm() {
        return taoHoaDonForm;
    }

    public void setTaoHoaDonForm(TaoHoaDonForm taoHoaDonForm) {
        this.taoHoaDonForm = taoHoaDonForm;
    } 

    
}
