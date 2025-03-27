package GUI.Components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

public class FunctionItem extends JPanel implements MouseListener{	// làm tạm thời cho các nút thêm xóa sửa
	private JLabel content;
	
	public FunctionItem(String content) {
		this.content = new JLabel(content);
		this.initComponent();
	}
	
	public void initComponent() {
        content.setFont(new Font("Segoe UI", Font.BOLD, 13));
		content.setForeground(Color.decode("#FFFFFF"));
        this.putClientProperty(FlatClientProperties.STYLE, "background: #333333; arc:10");
        this.setPreferredSize(new Dimension(60, 40));
		this.add(content);
        this.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JPanel panel = (JPanel)e.getSource();
		panel.setBackground(Color.decode("#444444"));
		panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JPanel panel = (JPanel)e.getSource();
		panel.setBackground(Color.decode("#333333"));
	}
	

}
