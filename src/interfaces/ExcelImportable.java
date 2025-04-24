package interfaces;

import java.io.File;
import java.util.List;

public interface ExcelImportable<T> {
    List<T> readFromExcel(File file) throws Exception;
}
