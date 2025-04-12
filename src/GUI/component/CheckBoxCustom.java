package GUI.component;

import javax.swing.JCheckBox;

public class CheckBoxCustom extends JCheckBox{
    private String hanhDong;
    private String chucNang;

    public CheckBoxCustom(String chucNang, String hanhDong){
        super();
        this.hanhDong = hanhDong;
        this.chucNang = chucNang;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }

    public String getChucNang() {
        return chucNang;
    }

    public void setChucNang(String chucNang) {
        this.chucNang = chucNang;
    }

    
}
