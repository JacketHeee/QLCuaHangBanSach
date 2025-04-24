package DTO.ThongKe;

import java.math.BigDecimal;

public class ThongKeNhapHangDTO {
    private int id; 
    private String name; 
    private int soluong;
    private BigDecimal tongtien;

    

    public ThongKeNhapHangDTO(int id, String name, int soluong, BigDecimal tongtien) {
        this.id = id;
        this.name = name;
        this.soluong = soluong;
        this.tongtien = tongtien;
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
    public BigDecimal getTongtien() {
        return tongtien;
    }
    public void setTongtien(BigDecimal tongtien) {
        this.tongtien = tongtien;
    }

    
    
}
