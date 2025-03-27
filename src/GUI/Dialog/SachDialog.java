package GUI.Dialog;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.formdev.flatlaf.FlatClientProperties;

import GUI.Components.InputForm;
import GUI.Panel.SachPnl;
import net.miginfocom.swing.MigLayout;

public class SachDialog extends JDialog{
    SachPnl sachPnl;
    JLabel content; // hộp thoại thêm/ sửa/ xem
    String type; // "thêm"/ "sửa"/ "xem"
    InputForm inputForm;
    
    //listAttribute để tạo inputForm
    public SachDialog(SachPnl sachPnl, String content, String type, String... listAttribute){
        super(sachPnl.getMainFrame(), content, true);
        this.sachPnl = sachPnl;
        this.content = new JLabel(content);
        this.type = type;
        this.inputForm = new InputForm(listAttribute);
        this.initComponent();
    }

    public void initComponent(){
        this.setLayout(new MigLayout("wrap 1", "[grow]"));
        this.getContentPane().setBackground(Color.decode("#FFFFFF"));

        content.setFont(new Font("Segoe UI", Font.BOLD, 26));
        this.add(content, "center");

        this.add(inputForm, "grow");
        // if(this.type.equals("thêm")){
            
        // }

        this.setSize(300, 500);
        this.setLocationRelativeTo(sachPnl.getMainFrame());
        this.setVisible(true);
    }


}
