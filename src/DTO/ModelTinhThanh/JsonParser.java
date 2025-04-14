// package DTO.ModelTinhThanh;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import java.io.File;
// import java.io.IOException;
// import java.net.URL;

// public class JsonParser {
//     public AdministrativeData parseJson(String filePath) {
//         ObjectMapper mapper = new ObjectMapper();
//         try {
//             // Đọc tệp JSON
//             // File jsonFile = new File(filePath);
//             URL resourceUrl = getClass().getResource(filePath);
//             if (resourceUrl == null) {
//                 System.err.println("Không tìm thấy file: " + filePath);
//                 return null;
//             }
//             File jsonFile = new File(resourceUrl.toURI());
//             // Phân tích thành đối tượng AdministrativeData
//             return mapper.readValue(jsonFile, AdministrativeData.class);
//         } catch (IOException e) {
//             e.printStackTrace();
//             return null;
//         }
//     }
// }

package DTO.ModelTinhThanh;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class JsonParser {
    public AdministrativeData parseJson() {
        String filePath = "/resources/json/dvhcvn.json";
        
        ObjectMapper mapper = new ObjectMapper(); //tạo đối tượng của Jackson để xử lý JSon 
        try {
            // Tìm đường dẫn tài nguyên từ classpath
            URL resourceUrl = getClass().getResource(filePath);
            if (resourceUrl == null) {
                System.err.println("Không tìm thấy file: " + filePath);
                return null;
            }

            // Tạo đối tượng File từ URL
            File jsonFile = new File(resourceUrl.toURI());

            // Phân tích JSON thành đối tượng AdministrativeData
            return mapper.readValue(jsonFile, AdministrativeData.class); //đọc file JSon và ánh xạ nó 

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}

