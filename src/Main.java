import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;

import GUI.Login;

public class Main {

	public static void main(String[] args) {
		FlatIntelliJLaf.setup(); 
		FlatLaf.registerCustomDefaultsSource("");
		java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
	}
    
}
