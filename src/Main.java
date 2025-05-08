import javax.swing.UIManager;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import java.awt.Font;

import GUI.Login;

public class Main {
	public static void main(String[] args) {
		FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("resources/themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN,13));
		
		FlatIntelliJLaf.setup();
		// FlatMacDarkLaf.setup();
		java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
	}
}