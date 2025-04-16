// package search;

// import java.util.ArrayList;

// import javax.swing.JComboBox;

// import interfaces.Searchable;
// import utils.TextUtils;

// public class AddressSearch implements Searchable<String> {

//     private ArrayList<String> danhSach;

//     public AddressSearch(ArrayList<String> danhSach) {
//         this.danhSach = danhSach;
//     }

//     @Override
//     public ArrayList<String> search(String keyword, JComboBox<String> comboBox) {
//         String selectedItem = (String)comboBox.getSelectedItem(); 
//         String keywordFormatted = TextUtils.boDau(keyword)
//                                    .toLowerCase()
//                                    .trim()
//                                    .replaceAll("\\s+", " ");

//         return new ArrayList<>(danhSach.stream()
//         .filter(x -> TextUtils.boDau(x).toLowerCase().contains(keywordFormatted))
//         .toList()
//         );
//     }
// }
