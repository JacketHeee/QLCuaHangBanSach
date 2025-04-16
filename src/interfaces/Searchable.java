package interfaces;

import java.util.ArrayList;

import javax.swing.JComboBox;

public interface Searchable<T> {
    ArrayList<T> search(String keyword, JComboBox<String> comboBox);    
}
