package GUI.component;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class InputForm extends JPanel{
    private ArrayList<InputFormItem> listItem;
    private String[][] arr;

    public InputForm(String[][] arr){
        this.arr = arr;
        this.listItem = new ArrayList<>();
        this.init();
    } 

    public void init(){
        this.setLayout(new MigLayout("wrap 1, insets 0 10 0 10", "[grow]"));
        this.setBackground(Color.decode("#FFFFFF"));
 
        for(int i = 0; i < arr.length; i++){
            listItem.add(new InputFormItem(arr[i][0], arr[i][1]));
            this.add(listItem.get(i), "grow");
        }
    }

    public ArrayList<InputFormItem> getListItem() {
        return listItem;
    }

    public void setListItem(ArrayList<InputFormItem> listItem) {
        this.listItem = listItem;
    }

    
}
