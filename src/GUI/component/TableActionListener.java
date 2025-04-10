package GUI.component;

import java.util.EventListener;

public interface TableActionListener extends EventListener {
    void onActionPerformed(String actionId, int row);
}