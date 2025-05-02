package GUI.component.search;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import BUS.SachBUS;
import DTO.SachDTO;

public class TextFieldListSach extends JTextField implements KeyListener{
    // list<SachDTO>
    // mã, tên, giá bán
    private JPopupMenu popupMenu;
    private JList<String> suggestionList;
    private ArrayList<String[]> listInfo; // lưu mã sách ; thông tin sách 
    private ArrayList<SachDTO> listSach;
    private CallBack callBack;
    private SachBUS sachBUS;
    private boolean finishSelect = false;
    private UpdateTongGiaCTWhenAddSach updateTongGiaCTWhenAddSach;

    public TextFieldListSach(ArrayList<SachDTO> listSach, CallBack callBack, UpdateTongGiaCTWhenAddSach updateTongGiaCTWhenAddSach){
        this.popupMenu = new JPopupMenu();
        this.suggestionList = new JList<>();
        this.listInfo = new ArrayList<>();
        this.listSach = listSach;
        this.callBack = callBack;
        this.sachBUS = SachBUS.getInstance();
        this.updateTongGiaCTWhenAddSach = updateTongGiaCTWhenAddSach;
        this.init();
    }

    public void init(){
        JScrollPane scrollPane = new JScrollPane(suggestionList);
        this.popupMenu.add(scrollPane);
        suggestionList.setFixedCellWidth(300);
        getList();
        addListener();
    }

    public void addListener(){
        this.addKeyListener(this);
        this.suggestionList.addKeyListener(this);
    }

    
    public void updateList(){
        String textFindo = this.getText();
        String textFind = textFindo.trim();
        if(textFind != null && !textFind.equals("")){
            ArrayList<String> result = new ArrayList<>(); 
            for(int i = 0; i < listInfo.size(); i++){
                if(listInfo.get(i)[0].contains(textFind)){
                    result.add(listInfo.get(i)[1]);
                }
            }
            if(result.size() == 0){
                this.popupMenu.setVisible(false);
            }
            else{
                this.suggestionList.setListData(result.toArray(new String[0]));
                this.suggestionList.setSelectedIndex(0);
                this.popupMenu.show(this, 0, this.getHeight());
            }
        }
        else{
            this.popupMenu.setVisible(false);
        }
    }

    public void getList(){
        for(int i = 0; i < listSach.size(); i++){
            listInfo.add(new String[] {
                listSach.get(i).getMaSach() + "",
                listSach.get(i).getMaSach() + " " + listSach.get(i).getTenSach() + " " + listSach.get(i).getGiaBan()
            });
        }
    }

    public SachDTO getDataRow(){
        SachDTO sach = null;
        if(suggestionList.getSelectedValue() != null){
            String selected = suggestionList.getSelectedValue();
            int ma = Integer.parseInt(selected.split(" ")[0]);
            sach = sachBUS.getInstanceByID(ma);
        }
        return(sach);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(e.getSource() == this){
                updateList();
                finishSelect = true;
            }
            else if(e.getSource() == suggestionList){
                if(finishSelect){               //Sau khi người dùng đã chọn 1 sp trong popup và đã nhấn Enter
                    SachDTO data = getDataRow();
                    if(data != null){
                        callBack.updateRowDatacb(data); //trả về sachDTO
                        updateTongGiaCTWhenAddSach.TinhTongGiaCTWhenAddSach();  //update tổng giá khi thêm mới sách
                        popupMenu.setVisible(false);
                    }
                }
            }
        }
    }
    //truyền sách DTO ra ngoài
    public interface CallBack {
        public void updateRowDatacb(SachDTO sach);
    }
    //cho lắng nghe sk khi thêm mới sách
    public interface UpdateTongGiaCTWhenAddSach {
        public void TinhTongGiaCTWhenAddSach();
    }
}
