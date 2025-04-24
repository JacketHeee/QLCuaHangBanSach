package GUI.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import BUS.SachBUS;
import DTO.SachDTO;
import resources.base.baseTheme;
import utils.ExcelExporter;
import utils.ExcelImporter;

public class ButtonAction extends JButton {

    private String text;
    private String iconImg;
    private String id;
    private Runnable reloadCallback; 
    public ButtonAction(String text, String iconImg, String id) {
        this(text, iconImg, id, null);
    }
    
    public ButtonAction(String text, String iconImg, String id, Runnable reloadCallback) {
        super(text, new FlatSVGIcon(ButtonAction.class.getResource("../../resources/img/icon/" + iconImg)).derive(20, 20));
        this.text = text;
        this.iconImg = iconImg;
        this.id = id;
        this.reloadCallback = reloadCallback;
        init();
        setBackgroundButton();
        // addDefaultAction();
    }   

    private void init() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        putClientProperty(FlatClientProperties.STYLE, "focusWidth: 0; innerFocusWidth: 0; borderWidth: 0");
        setIconTextGap(10);
        setMargin(new Insets(5, 10, 5, 10));
    }

    private void setBackgroundButton() {
        switch (id) {
            case "add":
                setBackground(Color.decode(baseTheme.mainColor));
                setForeground(Color.white);
                break;
            case "importExcel":
                setBackground(Color.decode("#FDE1C3"));
                setForeground(Color.decode(baseTheme.textColor));
                break;
            case "exportExcel":
                setBackground(Color.decode("#A8E8B9"));
                setForeground(Color.decode(baseTheme.textColor));
                break;
            default:
                break;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    

}
