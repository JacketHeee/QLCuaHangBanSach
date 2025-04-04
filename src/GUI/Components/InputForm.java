// package GUI.Components;

// import java.awt.Color;
// import java.util.ArrayList;

// import javax.swing.JPanel;

// import net.miginfocom.swing.MigLayout;

// public class InputForm extends JPanel{
//     private ArrayList<InputFormItem> listItem;
//     private String[] listContent;

//     public InputForm(String... listContent){
//         this.listContent = listContent;
//         listItem = new ArrayList<>();
//         this.initComponent();
//     }

//     public void initComponent(){
//         this.setLayout(new MigLayout("wrap 1", "[grow]"));
//         this.setBackground(Color.decode("#FFFFFF"));
//         for(String i : listContent){
//             InputFormItem item = new InputFormItem(i, "text");
//             listItem.add(item);
//             this.add(item, "grow");
//         }
//     }

//     public ArrayList<InputFormItem> getListItem() {
//         return listItem;
//     }

//     public void setListItem(ArrayList<InputFormItem> listItem) {
//         this.listItem = listItem;
//     }

//     public String[] getListContent() {
//         return listContent;
//     }

//     public void setListContent(String[] listContent) {
//         this.listContent = listContent;
//     }

//     public void addItem(InputFormItem item){
//         this.listItem.add(item);
//         this.add(item, "grow");
//     }

    
// }
