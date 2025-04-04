// package GUI.Panel;
// import java.awt.event.MouseEvent;
// import java.awt.event.MouseListener;
// import java.util.ArrayList;
// import javax.swing.*;
// import javax.swing.table.DefaultTableModel;
// import net.miginfocom.swing.MigLayout;

// import BUS.TacGiaBUS;
// import DTO.TacGiaDTO;
// import GUI.MainFrame;
// import GUI.Components.FunctionBar;
// import GUI.Components.FunctionItem;
// import GUI.Dialog.TacGiaDialog;

// public class TacGiaPnl extends JPanel implements MouseListener {
//     private FunctionBar functionBar;
//     private JTable table;
//     private DefaultTableModel model;
//     private JScrollPane scrollPane;
//     private String[] header = {"Mã tác giả", "Tên tác giả"};
//     private String[] functionList = {"Thêm", "Xóa", "Sửa", "Chi tiết"};
//     private String[] listAttribute = {"Tên tác giả"};
//     private TacGiaBUS tacGiaBUS;
//     private MainFrame mainFrame;
    
//     public TacGiaPnl() {
//         functionBar = new FunctionBar(functionList);
//         table = new JTable();
//         model = new DefaultTableModel(header, 0);
//         scrollPane = new JScrollPane();
//         tacGiaBUS = TacGiaBUS.getInstance();
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
//         loadDataTable(tacGiaBUS.getAll());
//         addFunctionListener();
//     }
    
//     public void loadDataTable(ArrayList<TacGiaDTO> listTacGia) {
//         for(TacGiaDTO i : listTacGia) {
//             model.addRow(new Object[] {i.getMaTacGia(), i.getTenTacGia()});
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
//         new TacGiaDialog(this, "Thêm Tác Giả", "thêm", listAttribute);
//     }

//     public void delete(){
//         if(table.getSelectedRow() == -1){
//             JOptionPane.showMessageDialog(null, "Vui lòng chọn tác giả cần xóa");
//             return;
//         }
//         int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa tác giả này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
//         if(confirm == JOptionPane.YES_OPTION){
//             int row = table.getSelectedRow();
//             int maTacGia = (int)model.getValueAt(row, 0);
//             if(tacGiaBUS.delete(maTacGia) != 0){
//                 JOptionPane.showMessageDialog(null, "Xóa tác giả thành công!");
//                 model.removeRow(row);
//             }
//             else{
//                 JOptionPane.showMessageDialog(null, "Xóa tác giả thất bại!");
//             }
//         }
//     }

//     public void update(){
//         if(table.getSelectedRow() == -1){
//             JOptionPane.showMessageDialog(null, "Vui lòng chọn tác giả cần sửa");
//             return;
//         }
//         new TacGiaDialog(this, "Chỉnh sửa tác giả", "sửa", listAttribute);
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

//     public TacGiaBUS getTacGiaBUS() {
//         return tacGiaBUS;
//     }

//     public void setTacGiaBUS(TacGiaBUS tacGiaBUS) {
//         this.tacGiaBUS = tacGiaBUS;
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