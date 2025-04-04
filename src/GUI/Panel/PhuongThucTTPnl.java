// package GUI.Panel;

// import java.awt.event.MouseEvent;
// import java.awt.event.MouseListener;
// import java.util.ArrayList;
// import javax.swing.*;
// import javax.swing.table.DefaultTableModel;
// import net.miginfocom.swing.MigLayout;

// import BUS.PhuongThucTTBUS;
// import DTO.PhuongThucTTDTO;
// import GUI.MainFrame;
// import GUI.Components.FunctionBar;
// import GUI.Components.FunctionItem;
// import GUI.Dialog.PhuongThucTTDialog;

// public class PhuongThucTTPnl extends JPanel implements MouseListener {
//     private FunctionBar functionBar;
//     private JTable table;
//     private DefaultTableModel model;
//     private JScrollPane scrollPane;
//     private String[] header = {"Mã phương thức", "Tên phương thức thanh toán"};
//     private String[] functionList = {"Thêm", "Xóa", "Sửa", "Chi tiết"};
//     private String[] listAttribute = {"Tên phương thức thanh toán"};
//     private PhuongThucTTBUS phuongThucTTBUS;
//     private MainFrame mainFrame;
    
//     public PhuongThucTTPnl() {
//         functionBar = new FunctionBar(functionList);
//         table = new JTable();
//         model = new DefaultTableModel(header, 0);
//         scrollPane = new JScrollPane();
//         phuongThucTTBUS = PhuongThucTTBUS.getInstance();
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
//         loadDataTable(phuongThucTTBUS.getAll());
//         addFunctionListener();
//     }
    
//     public void loadDataTable(ArrayList<PhuongThucTTDTO> listPhuongThucTT) {
//         for(PhuongThucTTDTO i : listPhuongThucTT) {
//             model.addRow(new Object[] {i.getMaPT(), i.getTenPTTT()});
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
//         new PhuongThucTTDialog(this, "Thêm Phương Thức Thanh Toán", "thêm", listAttribute);
//     }

//     public void delete(){
//         if(table.getSelectedRow() == -1){
//             JOptionPane.showMessageDialog(null, "Vui lòng chọn phương thức thanh toán cần xóa");
//             return;
//         }
//         int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa phương thức thanh toán này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
//         if(confirm == JOptionPane.YES_OPTION){
//             int row = table.getSelectedRow();
//             int maPT = (int)model.getValueAt(row, 0);
//             if(phuongThucTTBUS.delete(maPT) != 0){
//                 JOptionPane.showMessageDialog(null, "Xóa phương thức thanh toán thành công!");
//                 model.removeRow(row);
//             }
//             else{
//                 JOptionPane.showMessageDialog(null, "Xóa phương thức thanh toán thất bại!");
//             }
//         }
//     }

//     public void update(){
//         if(table.getSelectedRow() == -1){
//             JOptionPane.showMessageDialog(null, "Vui lòng chọn phương thức thanh toán cần sửa");
//             return;
//         }
//         new PhuongThucTTDialog(this, "Chỉnh sửa phương thức thanh toán", "sửa", listAttribute);
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

//     public PhuongThucTTBUS getPhuongThucTTBUS() {
//         return phuongThucTTBUS;
//     }

//     public void setPhuongThucTTBUS(PhuongThucTTBUS phuongThucTTBUS) {
//         this.phuongThucTTBUS = phuongThucTTBUS;
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
