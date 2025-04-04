// package GUI.Panel;
// import java.awt.event.MouseEvent;
// import java.awt.event.MouseListener;
// import java.util.ArrayList;
// import javax.swing.JOptionPane;
// import javax.swing.JPanel;
// import javax.swing.JScrollPane;
// import javax.swing.JTable;
// import javax.swing.table.DefaultTableModel;

// import BUS.TheLoaiBUS;
// import DTO.TheLoaiDTO;
// import GUI.MainFrame;
// import GUI.Components.FunctionBar;
// import GUI.Components.FunctionItem;
// import GUI.Dialog.TheLoaiDialog;
// import net.miginfocom.swing.MigLayout;

// public class TheLoaiPnl extends JPanel implements MouseListener {
//     private FunctionBar functionBar;
//     private JTable table;
//     private DefaultTableModel model;
//     private JScrollPane scrollPane;
//     private String[] header = {"Mã thể loại", "Tên thể loại"};
//     private String[] functionList = {"Thêm", "Xóa", "Sửa", "Chi tiết"};
//     private String[] listAttribute = {"Tên thể loại"};
//     private TheLoaiBUS theLoaiBUS;
//     private MainFrame mainFrame;
    
//     public TheLoaiPnl() {
//         functionBar = new FunctionBar(functionList);
//         table = new JTable();
//         model = new DefaultTableModel(header, 0);
//         scrollPane = new JScrollPane();
//         theLoaiBUS = TheLoaiBUS.getInstance();
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
//         loadDataTable(theLoaiBUS.getAll());
//         addFunctionListener();
//     }
    
//     public void loadDataTable(ArrayList<TheLoaiDTO> listTheLoai) {
//         for(TheLoaiDTO i : listTheLoai) {
//             model.addRow(new Object[] {i.getMaTheLoai(), i.getTenTheLoai()});
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
//         new TheLoaiDialog(this, "Thêm Thể Loại", "thêm", listAttribute);
//     }

//     public void delete(){
//         if(table.getSelectedRow() == -1){
//             JOptionPane.showMessageDialog(null, "Vui lòng chọn thể loại cần xóa");
//             return;
//         }
//         int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa thể loại này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
//         if(confirm == JOptionPane.YES_OPTION){
//             int row = table.getSelectedRow();
//             int maTheLoai = (int)model.getValueAt(row, 0);
//             if(theLoaiBUS.delete(maTheLoai) != 0){
//                 JOptionPane.showMessageDialog(null, "Xóa thể loại thành công!");
//                 model.removeRow(row);
//             }
//             else{
//                 JOptionPane.showMessageDialog(null, "Xóa thể loại thất bại!");
//             }
//         }
//     }

//     public void update(){
//         if(table.getSelectedRow() == -1){
//             JOptionPane.showMessageDialog(null, "Vui lòng chọn thể loại cần sửa");
//             return;
//         }
//         new TheLoaiDialog(this, "Chỉnh sửa thể loại", "sửa", listAttribute);
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

//     public TheLoaiBUS getTheLoaiBUS() {
//         return theLoaiBUS;
//     }

//     public void setTheLoaiBUS(TheLoaiBUS theLoaiBUS) {
//         this.theLoaiBUS = theLoaiBUS;
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
