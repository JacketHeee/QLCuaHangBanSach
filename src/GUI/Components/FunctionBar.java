package GUI.Components;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class FunctionBar extends JPanel{
	private ArrayList<FunctionItem> listItem;
	private String[] listFunction;
	
	public FunctionBar(String... listFunction) {
		listItem = new ArrayList<>();
		this.listFunction = listFunction;
		this.initComponent();
	}
	
	public void initComponent() {
		this.setBackground(Color.decode("#FFFFFF"));
		this.setLayout(new MigLayout());
		
		for(String i : this.listFunction) {
			FunctionItem fCI = new FunctionItem(i); 
			listItem.add(fCI);
            this.add(fCI);
		}
        
	}

    public ArrayList<FunctionItem> getListItem() {
        return listItem;
    }

    public void setListItem(ArrayList<FunctionItem> listItem) {
        this.listItem = listItem;
    }

    public String[] getListFunction() {
        return listFunction;
    }

    public void setListFunction(String[] listFunction) {
        this.listFunction = listFunction;
    }

    

}
