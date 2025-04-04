// package GUI.Panel;

// import java.awt.event.MouseEvent;
// import java.awt.event.MouseListener;
// import java.util.ArrayList;
// import javax.swing.*;
// import javax.swing.table.DefaultTableModel;
// import net.miginfocom.swing.MigLayout;

// import BUS.NhomQuyenBUS;
// import DTO.NhomQuyenDTO;
// import GUI.MainFrame;
// import GUI.Components.FunctionBar;
// import GUI.Components.FunctionItem;
// import GUI.Dialog.NhomQuyenDialog;

// public class NhomQuyenPnl extends JPanel implements MouseListener {
//     private FunctionBar functionBar;
//     private JTable table;
//     private DefaultTableModel model;
//     private JScrollPane scrollPane;
//     private String[] header = {"Mã nhóm quyền", "Tên nhóm quyền"};
//     private String[] functionList = {"Thêm", "Xóa", "Sửa", "Chi tiết"};
//     private String[] listAttribute = {"Tên nhóm quyền"};
//     private NhomQuyenBUS nhomQuyenBUS;
//     private MainFrame mainFrame;
    
//     public NhomQuyenPnl() {
//         functionBar = new FunctionBar(functionList);
//         table = new JTable();
//         model = new DefaultTableModel(header, 0);
//         scrollPane = new JScrollPane();
//         nhomQuyenBUS = NhomQuyenBUS.getInstance();
//         this.initComponent();
//     }
    
//     public void initComponent() {
//         this.setLayout(new MigLayout("wrap 1, insets 0","[grow]", "[][grow]"));
        
//         table.setModel(model);
//         table.setFocusable(false);
//         table.getTableHeader().setReorderingAllowed(false);
//         table.setDefaultEditor(Object.class, null);
        
//         scrollPane.setViewportView(table);
        
//         this.add(functionBar);
//         this.add(scrollPane, "grow");
//         loadDataTable(nhomQuyenBUS.getAll());
//         addFunctionListener();
//     }
    
//     public void loadDataTable(ArrayList<NhomQuyenDTO> listNhomQuyen) {
//         for(NhomQuyenDTO i : listNhomQuyen) {
//             model.addRow(new Object[] {i.getMaRole(), i.getTenRole()});
//         }
//     }

//     public void addFunctionListener(){
//         for(FunctionItem i : functionBar.getListItem()){
//             i.addMouseListener(this);
//         }
//     }
    
//     @Override
//     public void mouseClicked(MouseEvent e) {}

//     @Override
//     public void mousePressed(MouseEvent e) {
//         if(e.getSource() == functionBar.getListItem().get(0)){
//             insert();
//         }
//         else if(e.getSource() == functionBar.getListItem().get(1)){
//             delete();
//         }
//         else if(e.getSource() == functionBar.getListItem().get(2)){
//             update();
//         }
//     }

//     public void insert(){
//         new NhomQuyenDialog(this, "Thêm Nhóm Quyền", "thêm", listAttribute);
//     }

//     public void delete(){
//         if(table.getSelectedRow() == -1){
//             JOptionPane.showMessageDialog(null, "Vui lòng chọn nhóm quyền cần xóa");
//             return;
//         }
//         int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nhóm quyền này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
//         if(confirm == JOptionPane.YES_OPTION){
//             int row = table.getSelectedRow();
//             int maRole = (int)model.getValueAt(row, 0);
//             if(nhomQuyenBUS.delete(maRole) != 0){
//                 JOptionPane.showMessageDialog(null, "Xóa nhóm quyền thành công!");
//                 model.removeRow(row);
//             }
//             else{
//                 JOptionPane.showMessageDialog(null, "Xóa nhóm quyền thất bại!");
//             }
//         }
//     }

//     public void update(){
//         if(table.getSelectedRow() == -1){
//             JOptionPane.showMessageDialog(null, "Vui lòng chọn nhóm quyền cần sửa");
//             return;
//         }
//         new NhomQuyenDialog(this, "Chỉnh sửa nhóm quyền", "sửa", listAttribute);
//     }

//     @Override
//     public void mouseReleased(MouseEvent e) {}
//     @Override
//     public void mouseEntered(MouseEvent e) {}
//     @Override
//     public void mouseExited(MouseEvent e) {}

//     public MainFrame getMainFrame() {
//         return mainFrame;
//     }

//     public void setMainFrame(MainFrame mainFrame) {
//         this.mainFrame = mainFrame;
//     }

//     public NhomQuyenBUS getNhomQuyenBUS() {
//         return nhomQuyenBUS;
//     }

//     public void setNhomQuyenBUS(NhomQuyenBUS nhomQuyenBUS) {
//         this.nhomQuyenBUS = nhomQuyenBUS;
//     }

//     public JTable getTable() {
//         return table;
//     }

//     public void setTable(JTable table) {
//         this.table = table;
//     }

//     public DefaultTableModel getModel() {
//         return model;
//     }

//     public void setModel(DefaultTableModel model) {
//         this.model = model;
//     }
// }
