

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatIntelliJLaf;

import GUI.MainFrame;

public class Main {
	public static void main(String[] args) {
		FlatIntelliJLaf.setup();
        UIManager.put("TableHeader.hoverBackground", Color.decode("#333333"));
        UIManager.put("TableHeader.background", Color.decode("#333333"));
        UIManager.put("TableHeader.foreground", Color.white);
        UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 12));
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}
}
