package GUI.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import net.miginfocom.swing.MigLayout;
import resources.base.baseTheme;

public class ButtonTaskBar extends JPanel{
    private String text; 
    private String nameSVGIcon; 
    private String id; 
    private boolean isSelected;

    public ButtonTaskBar(String text, String nameSVGIcon, String id) {
        this.text = text;
        this.nameSVGIcon = nameSVGIcon;
        this.id = id;
        this.isSelected = false;
        init();
    }

    private JLabel label;
    private void init() {
        setOpaque(true);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        putClientProperty("arc",5);

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setBackground(Color.decode(baseTheme.selectedButton));
                label.setBackground(Color.decode(baseTheme.selectedButton));
            }
            public void mouseExited(MouseEvent e) {
                if (isSelected == false) {
                    setBackground(Color.decode(baseTheme.backgroundColor));
                    label.setBackground(Color.decode(baseTheme.backgroundColor));
                }
                else {
                    label.setBackground(Color.decode(baseTheme.mainColor));
                    setBackground(Color.decode(baseTheme.selectedButton));
                }
            }
        });     

        setLayout(new MigLayout());
        
        label = new JLabel();
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(3,20));
        // label.putClientProperty("arc", 3);
        label.putClientProperty(FlatClientProperties.STYLE, "arc: 3");
        label.setBackground(Color.decode(baseTheme.mainColor));

        add(label);

        JLabel icon = new JLabel(new FlatSVGIcon(ButtonTaskBar.class.getResource("../../resources/img/icon/" + this.nameSVGIcon)).derive(20,20));

        add(icon,"gap left 10");

        JLabel txt = new JLabel(this.text);

        add(txt,"pushx,growx,gap left 10");
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNameSVGIcon() {
        return nameSVGIcon;
    }

    public void setNameSVGIcon(String nameSVGIcon) {
        this.nameSVGIcon = nameSVGIcon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;

        if (isSelected == false) {
            setBackground(Color.decode(baseTheme.backgroundColor));
            label.setBackground(Color.decode(baseTheme.backgroundColor));
        }
        else {
            setBackground(Color.decode(baseTheme.selectedButton));
            label.setBackground(Color.decode(baseTheme.mainColor));
        }       
        this.repaint();
        this.revalidate();
    }
}
