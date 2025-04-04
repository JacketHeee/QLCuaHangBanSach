// package GUI.Panel;

// import java.awt.event.MouseEvent;
// import java.awt.event.MouseListener;
// import java.util.ArrayList;

// import javax.swing.JOptionPane;
// import javax.swing.JPanel;
// import javax.swing.JScrollPane;
// import javax.swing.JTable;
// import javax.swing.table.DefaultTableModel;

// import BUS.ChucNangBUS;
// import DTO.ChucNangDTO;
// import GUI.MainFrame;
// import GUI.Components.FunctionBar;
// import GUI.Components.FunctionItem;
// import GUI.Dialog.ChucNangDialog;
// import net.miginfocom.swing.MigLayout;

// public class ChucNangPnl extends JPanel implements MouseListener{
//     private FunctionBar functionBar;
//     private JTable table;
//     private DefaultTableModel model;
//     private JScrollPane scrollPane;
//     private String[] header = {"Mã chức năng", "Tên chức năng"};
//     private String[] functionList = {"Thêm", "Xóa", "Sửa", "Chi tiết"};
//     private String[] listAttribute = {"Tên chức năng"};
//     private ChucNangBUS chucNangBUS;

//     private MainFrame mainFrame;
    
//     public ChucNangPnl() {
//         functionBar = new FunctionBar(functionList);
//         table = new JTable();
//         model = new DefaultTableModel(header, 0);
//         scrollPane = new JScrollPane();
//         chucNangBUS = ChucNangBUS.getInstance();
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
//         loadDataTable(chucNangBUS.getAll());
//         addFunctionListener();
//     }
    
//     public void loadDataTable(ArrayList<ChucNangDTO> listChucNang) {
//         for(ChucNangDTO i : listChucNang) {
//             model.addRow(new Object[] {i.getMaChucNang(), i.getTenChucNang()});
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
//         new ChucNangDialog(this, "Thêm Chức Năng", "thêm", listAttribute);
//     }

//     public void delete(){
//         if(table.getSelectedRow() == -1){
//             JOptionPane.showMessageDialog(null, "Vui lòng chọn chức năng cần xóa");
//             return;
//         }
//         int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa chức năng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
//         if(confirm == JOptionPane.YES_OPTION){
//             int row = table.getSelectedRow();
//             int maChucNang = (int)model.getValueAt(row, 0);
//             if(chucNangBUS.delete(maChucNang) != 0){
//                 JOptionPane.showMessageDialog(null, "Xóa chức năng thành công!");
//                 model.removeRow(row);
//             }
//             else{
//                 JOptionPane.showMessageDialog(null, "Xóa chức năng thất bại!");
//             }
//         }
//     }

//     public void update(){
//         if(table.getSelectedRow() == -1){
//             JOptionPane.showMessageDialog(null, "Vui lòng chọn chức năng cần sửa");
//             return;
//         }
//         new ChucNangDialog(this, "Chỉnh sửa chức năng", "sửa", listAttribute);
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

//     public ChucNangBUS getChucNangBUS() {
//         return chucNangBUS;
//     }

//     public void setChucNangBUS(ChucNangBUS chucNangBUS) {
//         this.chucNangBUS = chucNangBUS;
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
