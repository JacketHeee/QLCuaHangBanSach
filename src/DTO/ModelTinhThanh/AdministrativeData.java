package DTO.ModelTinhThanh;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

// Lớp chính chứa toàn bộ dữ liệu
public class AdministrativeData {
    private ArrayList<Province> data;
    @JsonProperty("data_date")
    private String dataDate;
    @JsonProperty("generate_date")
    private long generateDate;
    private Stats stats;
    private ArrayList<String> fullAddresses = null; // thuộc tính thêm vào

    public ArrayList<String> getFullAddresses() {
        if (fullAddresses == null) {
            generateFullAddresses();
        }
        return fullAddresses;
    }

    // public AdministrativeData() {
    // }

    // Getters và Setters
    public ArrayList<Province> getData() { return data; }
    public void setData(ArrayList<Province> data) { this.data = data; }
    public String getDataDate() { return dataDate; }
    public void setDataDate(String dataDate) { this.dataDate = dataDate; }
    public long getGenerateDate() { return generateDate; }
    public void setGenerateDate(long generateDate) { this.generateDate = generateDate; }
    public Stats getStats() { return stats; }
    public void setStats(Stats stats) { this.stats = stats; }


    // Hàm này duyệt qua toàn bộ dữ liệu và tạo danh sách địa chỉ đầy đủ
    private void generateFullAddresses() {
        fullAddresses = new ArrayList<>();
        if (data != null) {
            for (Province province : data) {
                String provinceName = province.getName();
                if (province.getLevel2s() != null) {
                    for (District district : province.getLevel2s()) {
                        String districtName = district.getName();
                        if (district.getLevel3s() != null) {
                            for (Ward ward : district.getLevel3s()) {
                                String wardName = ward.getName();
                                // Ghép lại theo định dạng "Xã, Huyện, Tỉnh"
                                String fullAddress = String.format("%s, %s, %s", wardName, districtName, provinceName);
                                fullAddresses.add(fullAddress);
                            }
                        }
                    }
                }
            }
        }
    }
}