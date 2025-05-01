package GUI.dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import BUS.ChiTietQuyenBUS;
import BUS.ChucNangBUS;
import BUS.NhomQuyenBUS;
import DTO.ChiTietQuyenDTO;
import DTO.NhomQuyenDTO;

import GUI.component.CheckBoxCustom;
import GUI.component.CustomButton;
import GUI.component.CustomTable;
import GUI.forms.PhanQuyenForm;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import utils.Validate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class AddNhomQuyen extends JDialog implements ActionListener, ItemListener{
    JFrame parent;
    String title;
    private String type;
    private PhanQuyenForm phanQuyenForm;
    private ChiTietQuyenBUS chiTietQuyenBUS;
    private NhomQuyenBUS nhomQuyenBUS;
    private ChucNangBUS chucNangBUS;
    private ArrayList<ArrayList<CheckBoxCustom>> listCheckBox;
    private CustomTable customTable;
    private ArrayList<String[]> listAllowSelect;
    private int rowSelected;
    private ArrayList<String[]> listHD_CN;
    private ArrayList<String[]> oldList;

    // NhomQuyenBUS nhomQuyenBUS
    public AddNhomQuyen(JFrame parent, PhanQuyenForm phanQuyenForm, String title, String type, int... row) {
        super(parent,title,true);
        this.type = type;
        this.phanQuyenForm = phanQuyenForm;
        this.customTable = phanQuyenForm.getTable();
        this.chiTietQuyenBUS = this.phanQuyenForm.getChiTietQuyenBUS();
        this.nhomQuyenBUS = this.phanQuyenForm.getNhomQuyenBUS();
        this.chucNangBUS = this.phanQuyenForm.getChucNangBUS();
        this.listCheckBox = new ArrayList<>();
        this.listAllowSelect = new ArrayList<>();
        listHD_CN = new ArrayList<>();
        if(row.length == 1){
            rowSelected = row[0];
        }
        loadListAllowSelect();
        init(); 
    }

    private void init() {
        setSize(1024,768);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel(new MigLayout());
        if(type.equals("add")){
            panel.add(getInforPanel(),"pushx, growx,wrap");
            panel.add(getActionChooser(),"push,grow");
            //Cài đặt không cho chọn khi chưa chọn nút xem
            setDefaultCheckBox();
            //Cài đặt listener nút xem
            setListenerCheckBox();
        }
        else if(type.equals("update")){
            panel.add(getInforPanel(),"pushx, growx,wrap");
            panel.add(getActionChooser(),"push,grow");
            setOldData();
            this.oldList = getOldListUpdate();
            setListenerCheckBox();
        }
        else if(type.equals("detail")){
            panel.add(getInforPanel(),"pushx, growx,wrap");
            panel.add(getActionChooser(),"push,grow");
            setOldData();
            setCheckBoxForDetail();
        }

        add(panel);

        setVisible(true);
    }

    private void setOldData(){
        int ma = Integer.parseInt(phanQuyenForm.getTable().getCellData(rowSelected, 0));
        ArrayList<ChiTietQuyenDTO> listCTQ = chiTietQuyenBUS.getListChiTietQuyenByMaRole(ma);
        //Tạo mảng lưu mã chức năng, hành động của chức năng. 
        //Duyệt từng chức năng
        int j = 0;
        for(int i = 0; i < listCheckBox.size() && j < listCTQ.size(); i++){
            int maCN = listCheckBox.get(i).get(0).getMaChucNang();
            String hanhDong = new String();
            if(maCN == listCTQ.get(j).getMaChucNang()){
                //Duyệt từng ctq của mỗi chức năng
                while(j < listCTQ.size() && maCN == listCTQ.get(j).getMaChucNang()){
                    hanhDong += listCTQ.get(j).getHanhDong();
                    j++;
                }
                listHD_CN.add(new String[] {maCN + "", hanhDong});
            }
        }

        int indexOflistHD_CN = 0;
        for(int i = 0; i < listCheckBox.size(); i++){
            int maCN = i + 1;
            //Trường hợp dsctq không có chức năng đang xét
            if(Integer.parseInt(listHD_CN.get(indexOflistHD_CN)[0]) != maCN){
                //set không tích được ở các ô còn lại
                for(int k = 1; k < listCheckBox.get(i).size(); k++){
                    listCheckBox.get(i).get(k).setEnabled(false);
                }
                continue;
            }
            //xem
            listCheckBox.get(i).get(0).setSelected(true);
            if(listHD_CN.get(indexOflistHD_CN)[1].contains("Thêm")){
                listCheckBox.get(i).get(1).setSelected(true);
            }
            if(listHD_CN.get(indexOflistHD_CN)[1].contains("Sửa")){
                listCheckBox.get(i).get(2).setSelected(true);
            }
            if(listHD_CN.get(indexOflistHD_CN)[1].contains("Xóa")){
                listCheckBox.get(i).get(3).setSelected(true);
            }
            if(indexOflistHD_CN < listHD_CN.size() - 1){
                indexOflistHD_CN++;
            }
        }
    }

    
    private JTextField fieldNameRole;
    
    private JPanel getInforPanel() {
        JPanel panel = new JPanel(new MigLayout("al center center"));
        panel.add(new JLabel("<html><b>Tên nhóm quyền: </b></html>"));
        fieldNameRole = new JTextField();
        if(type.equals("update")){
            fieldNameRole.setText(phanQuyenForm.getTable().getCellData(rowSelected, 1));
        }
        else if(type.equals("detail")){
            fieldNameRole.setText(phanQuyenForm.getTable().getCellData(rowSelected, 1));
            fieldNameRole.setEditable(false);
        }
        panel.add(fieldNameRole,"pushx, grow");        
        return panel;
    }

    private JPanel getActionChooser() {
        JPanel panel = new JPanel(new MigLayout("wrap 5"));
    
        panel.add(new JLabel("<html><b>DANH MỤC CHỨC NĂNG</b></html>"), "pushx, growx, cell 0 0");
        panel.add(new JLabel("<html><b>Xem</b></html>"), "pushx,al center, cell 1 0");
        panel.add(new JLabel("<html><b>Thêm</b></html>"), "pushx,al center, cell 2 0");
        panel.add(new JLabel("<html><b>Sửa</b></html>"), "pushx,al center, cell 3 0");
        panel.add(new JLabel("<html><b>Xóa</b></html>"), "pushx,al center, cell 4 0,wrap");

        initListCheckBox();
        for(int i = 0; i < listAllowSelect.size(); i++){
            int maCN = i + 1;
            panel.add(new JLabel(listAllowSelect.get(i)[0]),"pushx,gaptop 15");
            String chucNang = listAllowSelect.get(i)[1];
            String hanhDong = listAllowSelect.get(i)[2];
            int k = 0;
            int skipCell = 0;
            if(hanhDong.contains("Xem")){
                listCheckBox.get(i).add(new CheckBoxCustom(maCN, "Xem", chucNang));
                panel.add(listCheckBox.get(i).get(k),"pushx, al center");
                k++;
            }
            else{
                panel.add(new JPanel());
                skipCell++;
            }
            if(hanhDong.contains("Thêm")){
                listCheckBox.get(i).add(new CheckBoxCustom(maCN, "Thêm", chucNang));
                panel.add(listCheckBox.get(i).get(k),"pushx, al center");
                k++;
            }
            else{
                panel.add(new JPanel());
                skipCell++;
            }
            if(hanhDong.contains("Sửa")){
                listCheckBox.get(i).add(new CheckBoxCustom(maCN, "Sửa", chucNang));
                panel.add(listCheckBox.get(i).get(k),"pushx, al center");
                k++;
            }
            else{
                panel.add(new JPanel());
                skipCell++;
            }
            if(hanhDong.contains("Xóa")){
                listCheckBox.get(i).add(new CheckBoxCustom(maCN, "Xóa", chucNang));
                panel.add(listCheckBox.get(i).get(k),"pushx, al center");
                k++;
            }
            else{
                panel.add(new JPanel());
                skipCell++;
            }
            int soCN = hanhDong.split(" ").length;
            int lastIndex = soCN + skipCell;
            if(lastIndex < 3){
                panel.add(new JPanel(), "skip " + (2 - lastIndex));
            }
        }

        JPanel panelbtn = new JPanel();
        if(type.equals("add")){
            CustomButton btnThem = new CustomButton("Thêm");    
            btnThem.setActionCommand("add");
            CustomButton btnHuy = new CustomButton("Hủy");    
            btnHuy.setActionCommand("exit");
            panelbtn.add(btnHuy);
            panelbtn.add(btnThem);
            btnThem.addActionListener(this);
            btnHuy.addActionListener(this);
        }
        else if(type.equals("update")){
            CustomButton btnSua = new CustomButton("Sửa");    
            btnSua.setActionCommand("update");
            CustomButton btnHuy = new CustomButton("Hủy");    
            btnHuy.setActionCommand("exit");
            panelbtn.add(btnHuy);
            panelbtn.add(btnSua);
            btnSua.addActionListener(this);
            btnHuy.addActionListener(this);
        }
        else if(type.equals("detail")){
            CustomButton btnHuy = new CustomButton("Thoát");    
            btnHuy.setActionCommand("exit");
            panelbtn.add(btnHuy);
            btnHuy.addActionListener(this);
        }

        panel.add(panelbtn, "push y, span 5, right");

        return panel;
    }

    public void initListCheckBox(){
        int n = listAllowSelect.size();
        for(int i = 0; i < n; i++){
            listCheckBox.add(new ArrayList<>());
        }
    }

    public void loadListAllowSelect(){
        listAllowSelect.add(new String[]{"Sách", "book", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Thể loại", "category", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Tác giả", "author", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Nhà xuất bản", "nxb", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Vùng kệ", "vungtl", "Xem Thêm Sửa Xóa"}); 
        listAllowSelect.add(new String[]{"Nhà cung cấp", "ncc", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Quản lý phiếu nhập", "qlInput", "Xem Thêm"}); 
        listAllowSelect.add(new String[]{"Quản lý hóa đơn", "qlBill", "Xem Thêm"}); 
        listAllowSelect.add(new String[]{"Khuyến mãi", "promotion", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Phương thức thanh toán", "pttt", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Nhân viên", "nv", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Tài khoản", "taikhoan", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Khách hàng", "khachhang", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Phân quyền", "phanquyen", "Xem Thêm Sửa Xóa"});
        listAllowSelect.add(new String[]{"Báo cáo & thống kê", "report", "Xem"});
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("add")){
            if(Validation()){
                createNhomQuyen();
            }
        }
        else if(e.getActionCommand().equals("update")){
            if(Validation()){
                update();
            }
        }
        else if(e.getActionCommand().equals("exit")){
            this.dispose();
        }
    }

    public void createNhomQuyen(){
        String tenNQ = fieldNameRole.getText();
        NhomQuyenDTO nhomQuyen = new NhomQuyenDTO(tenNQ);
        if(nhomQuyenBUS.insert(nhomQuyen) == 0){
            JOptionPane.showMessageDialog(parent, "Thêm nhóm quyền thất bại");
            return;
        }
    
        int maRole = nhomQuyenBUS.getMaNhomQuyenByTen(tenNQ);

        //Tạo list chi tiết quyền dựa trên listCheckBox
        for(int i = 0; i < listCheckBox.size(); i++){
            String tenCN = listAllowSelect.get(i)[1];
            int maCN = chucNangBUS.getMaChucNangByTen(tenCN);
            for(int j = 0; j < listCheckBox.get(i).size(); j++){
                if(listCheckBox.get(i).get(j).isSelected()){
                    ChiTietQuyenDTO chiTietQuyenDTO = new ChiTietQuyenDTO(maRole, maCN, listCheckBox.get(i).get(j).getHanhDong());
                    chiTietQuyenBUS.insert(chiTietQuyenDTO);
                }
            }
        }
        Notifications.getInstance().setJFrame(parent);
        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Thêm thành công");
        String[] s = {maRole + "",tenNQ};
        customTable.addDataRow(s);
        this.dispose();
    }

    public void update(){
        int maRole = Integer.parseInt(phanQuyenForm.getTable().getCellData(rowSelected, 0));
        String tenNQ = fieldNameRole.getText();
        NhomQuyenDTO nhomQuyen = new NhomQuyenDTO(maRole, tenNQ);
        if(nhomQuyenBUS.update(nhomQuyen) == 0){
            JOptionPane.showMessageDialog(parent, "Sửa thất bại");
            return;
        }

        ArrayList<ChiTietQuyenDTO> listCTQ = new ArrayList<>();

        // //Tạo newlist chi tiết quyền dựa trên listCheckBox
        for(int i = 0; i < listCheckBox.size(); i++){
            String tenCN = listAllowSelect.get(i)[1];
            int maCN = chucNangBUS.getMaChucNangByTen(tenCN);
            for(int j = 0; j < listCheckBox.get(i).size(); j++){
                if(listCheckBox.get(i).get(j).isSelected()){
                    ChiTietQuyenDTO chiTietQuyenDTO = new ChiTietQuyenDTO(maRole, maCN, listCheckBox.get(i).get(j).getHanhDong());
                    listCTQ.add(chiTietQuyenDTO);
                }
            }
        }

        ArrayList<String[]> newList = getNewListUpdate(listCTQ);

        int lStart = 0;  //biến đếm cho oldList
        int mStart = 0;  //biến đếm cho newList
        int lEnd = 0;
        int mEnd = 0;
        for(int i = 0; i < listCheckBox.size() && (lEnd < oldList.size() || mEnd < newList.size()); i++){
            int maCN = listCheckBox.get(i).get(0).getMaChucNang();
            int maCNOld = Integer.parseInt(oldList.get(lStart)[0]);
            int maCNNew = Integer.parseInt(newList.get(mStart)[0]);
            System.out.println(maCNNew + "-" + maCN);
            //Cả 2 đều không có chức năng thì bỏ qua
            if(maCN != Integer.parseInt(oldList.get(lStart)[0])
            && maCN != Integer.parseInt(newList.get(mStart)[0])){
                System.out.println("no");
                continue;
            }   
            //Cũ có, mới không có cả chức năng
            else if(maCN == Integer.parseInt(oldList.get(lStart)[0])
            && maCN != Integer.parseInt(newList.get(mStart)[0])){
                deleteCTQForCN(maRole, maCN);
                while(maCNOld == maCN && lEnd < oldList.size()){
                    lEnd++;
                    if(lEnd < oldList.size()) maCNOld = Integer.parseInt(oldList.get(lEnd)[0]);
                }
                lStart = lEnd;
                System.out.println("yes, no");
                continue;
            }
            //Cũ không có, mới có cả chức năng
            else if(maCN != Integer.parseInt(oldList.get(lStart)[0])
            && maCN == Integer.parseInt(newList.get(mStart)[0])){
                insertCTQForCN(maRole, maCN, newList); 
                while(maCNNew == maCN && mEnd < newList.size()){
                    mEnd++;
                    if(mEnd < newList.size()) maCNNew = Integer.parseInt(newList.get(mEnd)[0]);
                }
                mStart = mEnd;
                System.out.println("no, yes");
                continue;
            }
            System.out.println("yes");
            //Cả 2 đều có
            //Lấy chỉ số kết thúc + 1 của chức năng
            while(maCNOld == maCN && lEnd < oldList.size()){
                lEnd++;
                if(lEnd < oldList.size()) maCNOld = Integer.parseInt(oldList.get(lEnd)[0]);
            }
            while(maCNNew == maCN && mEnd < newList.size()){
                mEnd++;
                if(mEnd < newList.size()) maCNNew = Integer.parseInt(newList.get(mEnd)[0]);
            }
            boolean checkOldXem, checkOldThem, checkOldSua, checkOldXoa; checkOldXem = checkOldThem = checkOldSua = checkOldXoa = false;
            boolean checkNewXem, checkNewThem, checkNewSua, checkNewXoa; checkNewXem = checkNewThem = checkNewSua = checkNewXoa = false;
            //Check oldlist
            System.out.print("old " + maCN + "\t");
            for(int g = lStart; g < lEnd; g++){      
                System.out.print(oldList.get(g)[1] + "\t");
                if(oldList.get(g)[1].equals("Xem")){
                    checkOldXem = true;
                }
                else if(oldList.get(g)[1].equals("Thêm")){
                    checkOldThem = true;
                }
                else if(oldList.get(g)[1].equals("Sửa")){
                    checkOldSua = true;
                }
                else if(oldList.get(g)[1].equals("Xóa")){
                    checkOldXoa = true;
                }
            }
            //Check newList
            System.out.print("new " + maCN + "\t");
            for(int h = mStart; h < mEnd; h++){
                System.out.print(newList.get(h)[1] + "\t");
                if(newList.get(h)[1].equals("Xem")){
                    checkNewXem = true;
                }
                else if(newList.get(h)[1].equals("Thêm")){
                    checkNewThem = true;
                }
                else if(newList.get(h)[1].equals("Sửa")){
                    checkNewSua = true;
                }
                else if(newList.get(h)[1].equals("Xóa")){
                    checkNewXoa = true;
                }
            }
            lStart = lEnd;
            mStart = mEnd;
            //Trường hợp là phần tử cuối
            if(lStart == oldList.size()) lStart -= 1;
            if(mStart == newList.size()) mStart -= 1;
            //Nếu list cũ có, list mới không có => xóa chiTietQuyen
            //Nếu list cũ không có, list mới có => thêm chiTietQuyen
            if(checkOldXem && !checkNewXem){
                chiTietQuyenBUS.deleteByMaRoleAndMaCNAndHanhDong(maRole, maCN, "Xem");
                System.out.println("luv");
            }
            if(!checkOldXem && checkNewXem){
                ChiTietQuyenDTO ctq = new ChiTietQuyenDTO(maRole, maCN, "Xem");
                chiTietQuyenBUS.insert(ctq);
                System.out.println("luv");
            }
            if(checkOldThem && !checkNewThem){
                chiTietQuyenBUS.deleteByMaRoleAndMaCNAndHanhDong(maRole, maCN, "Thêm");
                System.out.println("luv");
            }
            if(!checkOldThem && checkNewThem){
                ChiTietQuyenDTO ctq = new ChiTietQuyenDTO(maRole, maCN, "Thêm");
                chiTietQuyenBUS.insert(ctq);
                System.out.println("luv");
            }
            if(checkOldSua && !checkNewSua){
                chiTietQuyenBUS.deleteByMaRoleAndMaCNAndHanhDong(maRole, maCN, "Sửa");   
                System.out.println("luv");         
            }
            if(!checkOldSua && checkNewSua){
                ChiTietQuyenDTO ctq = new ChiTietQuyenDTO(maRole, maCN, "Sửa");
                chiTietQuyenBUS.insert(ctq);
                System.out.println("luv");
            }
            if(checkOldXoa && !checkNewXoa){
                chiTietQuyenBUS.deleteByMaRoleAndMaCNAndHanhDong(maRole, maCN, "Xóa");   
                System.out.println("luv");         
            }
            if(!checkOldXoa && checkNewXoa){
                ChiTietQuyenDTO ctq = new ChiTietQuyenDTO(maRole, maCN, "Xóa");
                chiTietQuyenBUS.insert(ctq);    
                System.out.println("luv");        
            }
        }

        Notifications.getInstance().setJFrame(parent);
        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,"Sửa thành công");
        String[] s = {maRole + "",tenNQ};
        customTable.setRowData(rowSelected, s);
        this.dispose();
    }

    public void deleteCTQForCN(int maRole, int maCN){
        ArrayList<String> list = chiTietQuyenBUS.getAllHanhDongByMaRoleAndMaCN(maRole, maCN);
        for(int i = 0; i < list.size(); i++){
            chiTietQuyenBUS.deleteByMaRoleAndMaCNAndHanhDong(maRole, maCN, list.get(i));
        }
    }

    public void insertCTQForCN(int maRole, int maCN, ArrayList<String[]> newList){
        ArrayList<String[]> temp = new ArrayList<>();
        for(int i = 0; i < newList.size(); i++){
            if(maCN == Integer.parseInt(newList.get(i)[0])){
                temp.add(newList.get(i));
            }
        }
        for(int i = 0; i < temp.size(); i++){
            String hanhDong = temp.get(i)[1];
            ChiTietQuyenDTO chiTietQuyen = new ChiTietQuyenDTO(maRole, maCN, hanhDong);
            chiTietQuyenBUS.insert(chiTietQuyen);
        }
    }

    public ArrayList<String[]> getNewListUpdate(ArrayList<ChiTietQuyenDTO> list){
        ArrayList<String[]> result = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            ChiTietQuyenDTO ctq = list.get(i);
            result.add(new String[] {ctq.getMaChucNang() + "", ctq.getHanhDong()});
        }
        return(result);
    }

    public ArrayList<String[]> getOldListUpdate(){
        ArrayList<String[]> result = new ArrayList<>();
        for(int i = 0; i < listCheckBox.size(); i++){
            for(int j = 0; j < listCheckBox.get(i).size(); j++){
                if(listCheckBox.get(i).get(j).isSelected()){
                    int maCN = listCheckBox.get(i).get(j).getMaChucNang();
                    String hanhDong = listCheckBox.get(i).get(j).getHanhDong();
                    result.add(new String[] {maCN + "", hanhDong});
                }
            }
        }
        return(result);
    }

    
    @Override
    public void itemStateChanged(ItemEvent e) {
        CheckBoxCustom checkBox = (CheckBoxCustom)e.getSource();
        if(checkBox.isSelected()){
            setEnabled(checkBox.getChucNang());
        }
        else {
            setUnEnable(checkBox.getChucNang());
        }
    }
    
    public void setDefaultCheckBox(){
        for(int i = 0; i < listCheckBox.size(); i++){
            for(int j = 1; j < listCheckBox.get(i).size(); j++){
                listCheckBox.get(i).get(j).setEnabled(false);
            }
        }
    }
    
    public void setListenerCheckBox(){
        for(int i = 0; i < listCheckBox.size(); i++){
            listCheckBox.get(i).get(0).addItemListener(this);
        }
    }
    
    public void setEnabled(String chucNang){
        for(int i = 0; i < listCheckBox.size(); i++){
            String tenCN = listCheckBox.get(i).get(0).getChucNang();
            if(chucNang.equals(tenCN)){
                for(int j = 1; j < listCheckBox.get(i).size(); j++){
                    listCheckBox.get(i).get(j).setEnabled(true);
                }
                break;
            }
        }
    }
    
    public void setUnEnable(String chucNang){
        for(int i = 0; i < listCheckBox.size(); i++){
            String tenCN = listCheckBox.get(i).get(0).getChucNang();
            if(chucNang.equals(tenCN)){
                for(int j = 1; j < listCheckBox.get(i).size(); j++){
                    listCheckBox.get(i).get(j).setEnabled(false);
                    listCheckBox.get(i).get(j).setSelected(false);
                }
                break;
            }
        }
    }
    
    public void setCheckBoxForDetail(){
        for(int i = 0; i < listCheckBox.size(); i++){
            for(int j = 0; j < listCheckBox.get(i).size(); j++){
                listCheckBox.get(i).get(j).setEnabled(false);
            }
        }
    }

    public boolean Validation(){


        // JTextField input = inputForm.getListItem().get(0).getTextField();
        // if(Validate.isEmpty(input.getText())){
        //     JOptionPane.showMessageDialog(mainFrame, "Tên vùng không được để trống!","Thiếu thông tin",JOptionPane.WARNING_MESSAGE);
        //     input.requestFocusInWindow();
        //     return(false);
        // }

        if(Validate.isEmpty(fieldNameRole.getText())){
            JOptionPane.showMessageDialog(parent, "Tên nhóm quyền không được để trống!","",JOptionPane.WARNING_MESSAGE);
            fieldNameRole.requestFocusInWindow();
            return(false);
        }
        return(true);
    }
}
