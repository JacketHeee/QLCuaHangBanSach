package GUI.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.PhuongThucTTBUS;
import DTO.PhuongThucTTDTO;
import GUI.MainFrame;
import GUI.component.CustomButton;
import GUI.component.InputForm;
import GUI.forms.PhuongThucThanhToanForm;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import resources.base.baseTheme;
import utils.Validate;

public class PhuongThucTTDialog extends JDialog implements ActionListener{
    private PhuongThucThanhToanForm phuongThucTTPanel;
    private MainFrame mainFrame;
    private PhuongThucTTBUS phuongThucTTBUS; 
    private JLabel label;
    private String type;
    private String[][] attributes;
    private InputForm inputForm;
    private int rowSelected;

    
    public PhuongThucTTDialog(PhuongThucThanhToanForm phuongThucTTPanel, String title, String function, String type, String[][] attributes, int... row){
        super(phuongThucTTPanel.getMainFrame(), title, true);
        this.phuongThucTTPanel = phuongThucTTPanel;
        this.mainFrame = this.phuongThucTTPanel.getMainFrame();
        this.phuongThucTTBUS = this.phuongThucTTPanel.getPhuongThucTTBUS();
        this.type = type;
        this.attributes = attributes;
        inputForm = new InputForm(attributes);
        this.label = new JLabel("<html><strong><font size=+2>" + function + "</font></strong><html>");
        if(row.length == 1){
            this.rowSelected = row[0];
        }
        this.init();
    }

    public void init(){
        this.setLayout(new MigLayout("wrap 1, insets 30 0 20 0, debug", "[grow]"));
        this.getContentPane().setBackground(Color.decode("#FFFFFF"));

        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("insets 20 0 20 0, align center"));
        panel.add(label);  
        label.setForeground(Color.decode("#FFFFFF"));
        panel.putClientProperty(FlatClientProperties.STYLE, String.format("background: %s", baseTheme.mainColor)); 

        int originalSize = 250;
        int rowsize = 70;
        this.setSize(new Dimension(300, originalSize + rowsize * attributes.length));
        this.add(panel, "grow");

        this.add(inputForm, "grow");
        setLocationRelativeTo(mainFrame);

        setButton();
    }

    public void setButton(){
        if(type.equals("add")){
            CustomButton btnThem = new CustomButton("Thêm");
            btnThem.setActionCommand("add");
            btnThem.addActionListener(this);
            CustomButton btnHuy = new CustomButton("Hủy");
            btnHuy.setActionCommand("exit");
            btnHuy.addActionListener(this);
            JPanel panel = new JPanel();
            panel.setLayout(new MigLayout("wrap 2"));
            panel.setBackground(Color.decode("#FFFFFF"));
            panel.add(btnHuy);
            panel.add(btnThem);
            this.add(new JLabel(), "push y");
            this.add(panel, "right, gap right 10");
        }
        else if(type.equals("update")){// phương thức thanh toán thì không có sửa
            CustomButton btnSua = new CustomButton("Sửa");
            btnSua.setActionCommand("update");
            btnSua.addActionListener(this);
            CustomButton btnHuy = new CustomButton("Hủy");
            btnHuy.setActionCommand("exit");
            btnHuy.addActionListener(this);
            JPanel panel = new JPanel();
            panel.setLayout(new MigLayout("wrap 2"));
            panel.setBackground(Color.decode("#FFFFFF"));
            panel.add(btnHuy);
            panel.add(btnSua);
            this.add(new JLabel(), "push y");
            this.add(panel, "right, gap right 10");

            //set dữ liệu cũ
            setOldData();
        }
    }

    public void setOldData(){
        String ma = phuongThucTTPanel.getTable().getCellData(rowSelected, 0);
        

        inputForm.getListItem().get(0).setText(phuongThucTTBUS.getPTTTById(Integer.parseInt(ma)).getTenPTTT());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")){
            if(validation()){
                insert();
            }
        }
        else if (e.getActionCommand().equals("update")){
            if(validation()){
                update();
            }
        }
        else if(e.getActionCommand().equals("exit")){
            this.dispose();
        }
    }

    public void insert(){
        String ten = inputForm.getListItem().get(0).getText();
        PhuongThucTTDTO pttt = new PhuongThucTTDTO(ten);
        if(phuongThucTTBUS.insert(pttt) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Thêm thành công");
            String[] row = {pttt.getMaPT()+"", pttt.getTenPTTT()};
            phuongThucTTPanel.getTable().addDataRow(row);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Thêm thất bại!");
            this.dispose();
        }
    }

    public void update(){
        int ma = Integer.parseInt(phuongThucTTPanel.getTable().getCellData(rowSelected, 0));
        String ten = inputForm.getListItem().get(0).getText();
        PhuongThucTTDTO pttt = new PhuongThucTTDTO(ma, ten);
        if(phuongThucTTBUS.update(pttt) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Sửa thành công");
            String[] row = {pttt.getMaPT()+"", pttt.getTenPTTT()};
            phuongThucTTPanel.getTable().addDataRow(row);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Sửa thất bại!");
            this.dispose();
        }
    }

    public boolean validation(){
        // String ten = inputForm.getListItem().get(0).getText();
        // if(Validate.isEmpty(ten)){
        //     JOptionPane.showMessageDialog(mainFrame, "Tên phương thức thanh toán không được để trống!");
        //     return(false);
        // }
        
        JTextField input = inputForm.getListItem().get(0).getTextField();
        if(Validate.isEmpty(input.getText())){
            JOptionPane.showMessageDialog(mainFrame, "Tên phương thức thanh toán không được để trống!","",JOptionPane.WARNING_MESSAGE);
            input.requestFocusInWindow();
            return(false);
        }
        return(true);
    }
}
