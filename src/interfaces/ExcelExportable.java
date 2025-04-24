package interfaces;

import java.util.List;

public interface ExcelExportable <T> {
    String getTitle();
    List<String> getColumnHeaders();
    List<T> getData();
}
