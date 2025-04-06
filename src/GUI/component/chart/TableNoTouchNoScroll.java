package GUI.component.chart;

import GUI.component.TableNoTouch;
import java.awt.BorderLayout;
import java.util.ArrayList;
public class TableNoTouchNoScroll extends TableNoTouch {
    
    public TableNoTouchNoScroll(ArrayList<String[]> data,String...headers) {
        super(data, headers);
    }

    @Override
    public void addScollPane() {
        add(dataPanel, BorderLayout.CENTER);
    }
}
