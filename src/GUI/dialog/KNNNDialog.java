package GUI.dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import GUI.component.CustomButton;
import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;
import utils.Validate;

public class KNNNDialog extends JDialog implements ActionListener{
    private String fKName;
    private JTextArea textFK;
    private onDataSubmitListener listener;
    private CustomButton btnTao;
    private CustomButton btnThem;
    private JComboBox<String> comboBox;
    private ArrayList<String> listItem;
    private JFrame parent;

    public KNNNDialog(JFrame parent, String title, String fKName, String[] listItemCbx, onDataSubmitListener listener){
        super(parent, title, true);
        this.parent = parent;
        this.fKName = fKName;
        this.listener = listener;
        this.btnTao = new CustomButton("Tạo");
        this.btnThem = new CustomButton("Thêm");
        this.comboBox = new JComboBox<>(listItemCbx);
        this.listItem = new ArrayList<>(Arrays.asList(listItemCbx));
        this.textFK = new JTextArea(3, 20);
        init();
    }

    public void init(){
        this.setLayout(new MigLayout("wrap 1, insets 40 0 0 0", "[grow]"));
        JPanel header = new JPanel();
        header.setLayout(new MigLayout("insets 20 20 20 20"));
        header.setBackground(Color.decode(baseTheme.mainColor));
        JLabel title = new JLabel("<html><strong><font size=+2>" + "Thêm " + fKName + "</font></strong><html>");
        title.setForeground(Color.decode("#FFFFFF"));
        header.add(title, "center");
        this.add(header, "grow");


        textFK.setFont(new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 14));
        textFK.setEditable(false);
        textFK.setLineWrap(true);
        textFK.setWrapStyleWord(true);
        textFK.setBackground(new Color(245, 245, 245));
        textFK.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200)),
        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("wrap 2", "[][grow]"));
        panel.add(comboBox);
        panel.add(btnThem, "right");
        panel.add(textFK, "grow, span 2");
        panel.add(btnTao, "span 2, right");
        this.add(panel, "grow");
        this.btnTao.addActionListener(e ->{
                if(Validation()){
                    String text = textFK.getText();
                    listener.onDataSubmit(text);
                    dispose();
                }
            }
        );

        this.setSize(350, 400);
        this.setLocationRelativeTo(null);
        setListener();
    }

    public void setListener(){
        btnThem.addActionListener(this);
    }    

    //in lồng trong class
    public interface onDataSubmitListener  {
        void onDataSubmit(String s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedItem = (String)comboBox.getSelectedItem();
        if(selectedItem != null){
            textFK.append(selectedItem + ", ");
            //xóa item
            for(int i = 0; i < listItem.size(); i++){
                if(listItem.get(i).equals(selectedItem)){
                    listItem.remove(i);
                    break;
                }
            }
            //cập nhật
            String[] newList = listItem.toArray(new String[0]);
            updateCbx(newList);
        }
    }

    public void updateCbx(String[] newList){
        comboBox.removeAllItems(); 

        for (String i : newList) {
            comboBox.addItem(i); 
        }
    }

    public boolean Validation(){
        String text = textFK.getText();
        if(Validate.isEmpty(text)){
            JOptionPane.showMessageDialog(parent, "Chưa có " + fKName + " nào để thêm!");
            return(false);
        }
        return(true);
    }
}

