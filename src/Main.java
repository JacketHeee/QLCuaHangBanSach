import javax.swing.UIManager;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import java.awt.Font;

// import GUI.Login;
import GUI.MainFrame;

public class Main {
	public static void main(String[] args) {
		FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN,13));
		FlatIntelliJLaf.setup();
		// java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));

        MainFrame mainFrame = new MainFrame();
        
        mainFrame.setVisible(true);
	}
}
