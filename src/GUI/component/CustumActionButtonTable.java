package GUI.component;

import javax.swing.JButton;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class CustumActionButtonTable extends JButton {
    private String icon; 
    private String id; 
    private int row;

    public CustumActionButtonTable(String icon, String id, int row) {
        super(new FlatSVGIcon(CustumActionButtonTable.class.getResource("../../resources/img/icon/"+icon)).derive(15,15));
        this.icon = icon; 
        this.id = id;
        this.row = row;
        putClientProperty(FlatClientProperties.STYLE, "focusWidth:0; innerFocusWidth:0");
    }

    public String getIconn() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

}