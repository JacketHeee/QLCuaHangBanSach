package DTO.ModelTinhThanh;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

// Lớp cho Tỉnh/Thành phố (Level 1)
public class Province {
    @JsonProperty("level1_id")
    private String level1Id;
    private String name;
    private String type;
    private ArrayList<District> level2s;

    // Getters và Setters
    public String getLevel1Id() { return level1Id; }
    public void setLevel1Id(String level1Id) { this.level1Id = level1Id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public ArrayList<District> getLevel2s() { return level2s; }
    public void setLevel2s(ArrayList<District> level2s) { this.level2s = level2s; }

    @Override
    public String toString() { return name; }
}