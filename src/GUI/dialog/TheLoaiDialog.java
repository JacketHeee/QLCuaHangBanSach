package GUI.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import BUS.TheLoaiBUS;
import DTO.TheLoaiDTO;
import GUI.MainFrame;
import GUI.component.CustomButton;
import GUI.component.InputForm;
import GUI.forms.TheLoaiForm;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import resources.base.baseTheme;
import utils.Validate;

public class TheLoaiDialog extends JDialog implements ActionListener{
    private TheLoaiForm theLoaiPanel;
    private MainFrame mainFrame;
    private TheLoaiBUS theLoaiBUS; 
    private JLabel label;
    private String type;
    private String[][] attributes;
    private InputForm inputForm;
    private int rowSelected;

    
    public TheLoaiDialog(TheLoaiForm theLoaiPanel, String title, String function, String type, String[][] attributes, int... row){
        super(theLoaiPanel.getMainFrame(), title, true);
        this.theLoaiPanel = theLoaiPanel;
        this.mainFrame = this.theLoaiPanel.getMainFrame();
        this.theLoaiBUS = this.theLoaiPanel.getTheLoaiBUS();
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
        this.setLayout(new MigLayout("wrap 1, insets 30 0 20 0", "[grow]"));
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
            this.add(new JPanel(), "push y");
            this.add(panel, "right, gap right 10");
        }
        else if(type.equals("update")){// thể loại thì không có sửa
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
            this.add(new JPanel(), "push y");
            this.add(panel, "right, gap right 10");

            //set dữ liệu cũ
            setOldData();
        }
    }

    public void setOldData(){
        String tenTheLoai = theLoaiPanel.getTable().getCellData(rowSelected, 1);

        inputForm.getListItem().get(0).setText(tenTheLoai);
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
        TheLoaiDTO tl = new TheLoaiDTO(ten);
        if(theLoaiBUS.insert(tl) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Thêm thành công");
            String[] row = {tl.getMaTheLoai()+"", tl.getTenTheLoai()};
            theLoaiPanel.getTable().addDataRow(row);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Thêm thể loại thất bại!");
            this.dispose();
        }
    }

    public void update(){
        int ma = Integer.parseInt(theLoaiPanel.getTable().getCellData(rowSelected, 0));
        String ten = inputForm.getListItem().get(0).getText();
        TheLoaiDTO tl = new TheLoaiDTO(ma, ten);
        if(theLoaiBUS.update(tl) != 0){
            Notifications.getInstance().setJFrame(mainFrame);
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Sửa thành công");
            String[] row = {tl.getMaTheLoai()+"", tl.getTenTheLoai()};
            theLoaiPanel.getTable().setRowData(rowSelected, row);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Sửa thất bại!");
            this.dispose();
        }
    }

    public boolean validation(){
        String ten = inputForm.getListItem().get(0).getText();
        if(Validate.isEmpty(ten)){
            JOptionPane.showMessageDialog(mainFrame, "Tên thể loại không được để trống!");
            return(false);
        }
        return(true);
    }
}
