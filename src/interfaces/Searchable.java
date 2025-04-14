package interfaces;

import java.util.ArrayList;

public interface Searchable<T> {
    ArrayList<T> search(String keyword);    
}
