package DTO.ModelTinhThanh;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stats {
    @JsonProperty("elapsed_time")
    private double elapsedTime;
    @JsonProperty("level1_count")
    private int level1Count;
    @JsonProperty("level2_count")
    private int level2Count;
    @JsonProperty("level3_count")
    private int level3Count;

    // Getters và Setters
    public double getElapsedTime() { return elapsedTime; }
    public void setElapsedTime(double elapsedTime) { this.elapsedTime = elapsedTime; }
    public int getLevel1Count() { return level1Count; }
    public void setLevel1Count(int level1Count) { this.level1Count = level1Count; }
    public int getLevel2Count() { return level2Count; }
    public void setLevel2Count(int level2Count) { this.level2Count = level2Count; }
    public int getLevel3Count() { return level3Count; }
    public void setLevel3Count(int level3Count) { this.level3Count = level3Count; }
}