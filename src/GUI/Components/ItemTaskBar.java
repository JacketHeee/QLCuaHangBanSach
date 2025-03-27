package GUI.Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ItemTaskBar extends JPanel{
	private JLabel taskName;
	
	public ItemTaskBar(String content) {
		taskName = new JLabel(content);
		this.initComponent();
	}
	
	public void initComponent() {
		this.setPreferredSize(new Dimension(1, 40));
		this.setLayout(new MigLayout("wrap 1"));
		this.setBackground(Color.decode("#333333"));
		taskName.setFont(new Font("Segoe UI", Font.BOLD, 13));
		taskName.setForeground(Color.decode("#EEEEEE"));
		this.add(taskName);
	}
	
	public String getContent() {
		return taskName.getText();
	}
}
