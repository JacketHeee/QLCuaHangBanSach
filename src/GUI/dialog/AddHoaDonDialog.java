package GUI.dialog;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;

import DTO.HoaDonDTO;
import GUI.MainFrame;
import GUI.component.CustomScrollPane;
import GUI.forms.TaoHoaDonForm;

public class AddHoaDonDialog extends JDialog{
    private TaoHoaDonForm taoHoaDonForm;
    private MainFrame mainFrame;

    public AddHoaDonDialog(MainFrame mainFrame) {
        super(mainFrame,"",true);
        this.mainFrame = mainFrame;
        this.taoHoaDonForm = new TaoHoaDonForm(mainFrame,() -> setVisible(false));
        add(new CustomScrollPane(taoHoaDonForm));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 1200;
        int height = screenSize.height;
        setSize(width,height);
        setLocationRelativeTo(mainFrame);
    }
    //cho chi tiết
    public AddHoaDonDialog(MainFrame mainFrame, HoaDonDTO hoaDon) {
        super(mainFrame,"",true);
        this.taoHoaDonForm = new TaoHoaDonForm(mainFrame, hoaDon, "detail",() -> setVisible(false));
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
