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
        this.setBackground(Color.decode("#FFFFFF"));
        if(!existsAnh()){
            this.setLayout(new MigLayout("wrap 1, insets 0 30 0 30", "[grow]"));
            this.setInputForm();
        }
        else{
            this.setInputFormAnh();
        }
    }

    public void setInputForm(){
        for(int i = 0; i < arr.length; i++){
            listItem.add(new InputFormItem(arr[i][0], arr[i][1]));
            this.add(listItem.get(i), "grow");
        }
    }

    public void setInputFormAnh(){  //tạm thời cài đặt ảnh ở cuối
        System.out.println("luv");
        for(int i = 0; i < arr.length; i++){
            listItem.add(new InputFormItem(arr[i][0], arr[i][1]));
        }

        this.setLayout(new MigLayout("wrap 2, insets 0", "[][grow]"));
        JPanel panelAnh = new JPanel();
        panelAnh.setLayout(new MigLayout());
        panelAnh.setBackground(Color.decode("#FFFFFF"));
        panelAnh.add(listItem.get(listItem.size()-1));

        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("wrap 1, insets 0 10 0 10", "[grow]"));
        panel.setBackground(Color.decode("#FFFFFF"));


        for(int i = 0; i < listItem.size() - 1; i++){
            panel.add(listItem.get(i), "grow");
        }

        this.add(panelAnh);
        this.add(panel, "grow");

    }

    public boolean existsAnh(){
        for(int i = 0; i < arr.length; i++){
            if(arr[i][0].equals("inputAnh")){
                return(true);
            }
        }
        return(false);
    }

    public ArrayList<InputFormItem> getListItem() {
        return listItem;
    }

    public void setListItem(ArrayList<InputFormItem> listItem) {
        this.listItem = listItem;
    }

    public void hideInputItems(int... index) {
        // Đặt tất cả các item thành visible trước
        for (InputFormItem item : listItem) {
            item.setVisible(true);
        }
        // Ẩn các item tại các chỉ số được chỉ định
        for (int i : index) {
            if (i >= 0 && i < listItem.size()) {
                listItem.get(i).setVisible(false);
            }
        }

        this.removeAll();

        this.setLayout(new MigLayout("wrap 1, insets 0 30 0 30", "[grow]"));
            for (InputFormItem item : listItem) {
                if (item.isVisible()) {
                    this.add(item, "grow");
                }
            }
        this.revalidate();
        this.repaint();
    }

    public void showAll() {
        // Đặt tất cả các item thành visible trước
        for (InputFormItem item : listItem) {
            item.setVisible(true);
        }

        // // Ẩn các item tại các chỉ số được chỉ định
        // for (int i : index) {
        //     if (i >= 0 && i < listItem.size()) {
        //         listItem.get(i).setVisible(false);
        //     }
        // }

        this.removeAll();

        this.setLayout(new MigLayout("wrap 1, insets 0 30 0 30", "[grow]"));
            for (InputFormItem item : listItem) {
                if (item.isVisible()) {
                    this.add(item, "grow");
                }
            }
        this.revalidate();
        this.repaint();
    }

    
}
