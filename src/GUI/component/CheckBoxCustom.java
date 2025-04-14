package GUI.component;

import javax.swing.JCheckBox;

public class CheckBoxCustom extends JCheckBox{
    private int maChucNang;
    private String hanhDong;
    private String chucNang;

    public CheckBoxCustom(int maChucNang, String hanhDong, String chucNang) {
        super();
        this.maChucNang = maChucNang;
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

    public int getMaChucNang() {
        return maChucNang;
    }

    public void setMaChucNang(int maChucNang) {
        this.maChucNang = maChucNang;
    }

    
}
