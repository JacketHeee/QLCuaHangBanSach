package GUI.dialog;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import BUS.DanhMuc_TGBUS;
import BUS.PhanLoaiBUS;
import BUS.SachBUS;
import BUS.TacGiaBUS;
import BUS.TheLoaiBUS;
import DTO.SachDTO;
import net.miginfocom.swing.MigLayout;
import utils.FormatterUtil;

public class PopupSachDetail extends JPopupMenu{
    private int maSach;
    private SachBUS sachBUS;
    private PhanLoaiBUS phanLoaiBUS;
    private DanhMuc_TGBUS danhMuc_TGBUS;
    private ArrayList<String> listInfo;
    private TheLoaiBUS theLoaiBUS;
    private TacGiaBUS tacGiaBUS;

    public PopupSachDetail(int maSach){
        this.sachBUS = SachBUS.getInstance();
        this.phanLoaiBUS = PhanLoaiBUS.getInstance();
        this.danhMuc_TGBUS = DanhMuc_TGBUS.getInstance();
        this.theLoaiBUS = TheLoaiBUS.getInstance();
        this.tacGiaBUS = TacGiaBUS.getInstance();
        this.listInfo = new ArrayList<>();
        this.maSach = maSach;
        init();
    }

    public void init(){
        SachDTO sach = sachBUS.getInstanceByID(maSach);
        this.add(getContentPanel(sach));
    }

    public JPanel getContentPanel(SachDTO sach){
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("wrap 2", "[][grow]"));
        panel.setBackground(Color.decode("#FFFFFF"));
        int maSach = sach.getMaSach();
        ArrayList<Integer> listTL = phanLoaiBUS.getAllMaTheLoaiByMaSach(maSach);
        ArrayList<Integer> listTG = danhMuc_TGBUS.getAllMaTacGiaByMaSach(maSach); 
        String strTL = new String();
        for(int i : listTL){
            strTL += theLoaiBUS.getTenByMa(i) + ", ";
        }
        String strTG = new String();
        for(int i : listTG){
            strTG += tacGiaBUS.getTenByMa(i) + ", ";
        }

        String[] listTitle = {"ID: ","Tên: ","Số lượng: ","Giá bán: ","Năm xuất bản: ","Mã nhà xuất bản: ","Thể loại: ", "Tác giả: "}; //Thể loại , tác giả
        String[] listInfo = {
            sach.getMaSach() + ""
            , sach.getTenSach()
            , sach.getSoLuong() + ""
            , FormatterUtil.formatNumberVN(sach.getGiaBan()) + ""
            , sach.getNamXB() + ""
            , sach.getMaNXB() + ""
            , removeLastComma(strTL)
            , removeLastComma(strTG)
        };

        for(int i = 0; i < listTitle.length; i++){
            JLabel labelTitle = new JLabel(listTitle[i]);
            JLabel labelInfo = new JLabel(listInfo[i]);
            panel.add(labelTitle);
            panel.add(labelInfo);
        }
        return panel;
    }

    public String removeLastComma(String text) {
        if (text != null) {
            text = text.trim();
            if (text.endsWith(",")) {
                return text.substring(0, text.length() - 1);
            }
        }
        return text;
    }

}
