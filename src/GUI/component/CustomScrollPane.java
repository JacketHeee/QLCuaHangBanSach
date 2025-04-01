package GUI.component;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class CustomScrollPane extends JScrollPane{
     
    public CustomScrollPane(Component view) {
        super(view);
        init();
    }



    private void init() {
        setOpaque(false);
		setBorder(null);
		
		JScrollBar verticalBar = this.getVerticalScrollBar();
		verticalBar.setPreferredSize(new Dimension(5, 0)); // Chiều rộng thanh cuộn dọc
    }
}
