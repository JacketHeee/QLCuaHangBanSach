package GUI.dialog;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.Toolkit;

import GUI.MainFrame;
import GUI.component.CustomScrollPane;
import GUI.forms.TaoPhieuNhapForm;

public class AddPhieuNhapDialog extends JDialog{
    private TaoPhieuNhapForm taoPhieuNhapForm;

    public AddPhieuNhapDialog(MainFrame mainFrame) {
        super(mainFrame,"",true);
        this.taoPhieuNhapForm = new TaoPhieuNhapForm(mainFrame);
        add(new CustomScrollPane(taoPhieuNhapForm));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 1200;
        int height = screenSize.height;
        setSize(width,height);
        setLocationRelativeTo(mainFrame);
    }

    public TaoPhieuNhapForm getTaoPhieuNhapForm() {
        return taoPhieuNhapForm;
    }

    public void setTaoPhieuNhapForm(TaoPhieuNhapForm taoPhieuNhapForm) {
        this.taoPhieuNhapForm = taoPhieuNhapForm;
    } 

    
}
