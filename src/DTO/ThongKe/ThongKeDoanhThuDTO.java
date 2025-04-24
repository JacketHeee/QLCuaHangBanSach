package DTO.ThongKe;

import java.math.BigDecimal;

public class ThongKeDoanhThuDTO {
    private int id; 
    private String name; 
    private int soluong;
    private BigDecimal doanhthu;

    
    public ThongKeDoanhThuDTO(int id, String name, int soluong, BigDecimal doanhthu) {
        this.id = id;
        this.name = name;
        this.soluong = soluong;
        this.doanhthu = doanhthu;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getSoluong() {
        return soluong;
    }
    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
    public BigDecimal getDoanhthu() {
        return doanhthu;
    }
    public void setDoanhthu(BigDecimal doanhthu) {
        this.doanhthu = doanhthu;
    } 
}
