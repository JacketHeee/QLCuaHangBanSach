package DTO.ModelTinhThanh;

import com.fasterxml.jackson.annotation.JsonProperty;

// Lớp cho Phường/Xã (Level 3)
public class Ward {
    @JsonProperty("level3_id")
    private String level3Id;
    private String name;
    private String type;

    // Getters và Setters
    public String getLevel3Id() { return level3Id; }
    public void setLevel3Id(String level3Id) { this.level3Id = level3Id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() { return name; }
}