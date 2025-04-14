package DTO.ModelTinhThanh;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

// Lớp cho Quận/Huyện (Level 2)
public class District {
    @JsonProperty("level2_id")
    private String level2Id;
    private String name;
    private String type;
    private ArrayList<Ward> level3s;

    // Getters và Setters
    public String getLevel2Id() { return level2Id; }
    public void setLevel2Id(String level2Id) { this.level2Id = level2Id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public ArrayList<Ward> getLevel3s() { return level3s; }
    public void setLevel3s(ArrayList<Ward> level3s) { this.level3s = level3s; }

    @Override
    public String toString() { return name; }
}