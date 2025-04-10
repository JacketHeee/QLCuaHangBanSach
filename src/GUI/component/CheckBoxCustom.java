package GUI.component;

import javax.swing.JCheckBox;

public class CheckBoxCustom extends JCheckBox{
    private String hanhDong;

    public CheckBoxCustom(String hanhDong){
        super();
        this.hanhDong = hanhDong;
    }

    public String gethanhDong() {
        return hanhDong;
    }

    public void sethanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }
}
