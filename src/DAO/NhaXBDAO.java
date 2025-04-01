package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.NhaXBDTO;
import config.JDBCUtil;

public class NhaXBDAO implements DAOInterface<NhaXBDTO> {
    private static NhaXBDAO instance;
    private NhaXBDAO() {}
    
    public static NhaXBDAO getInstance() {
        if (instance == null) {
            instance = new NhaXBDAO();
        }
        return instance;
    }

    @Override
    public int insert(NhaXBDTO t) {
        int rowInserted = 0;
        String sql = String.format(
            "INSERT INTO NHAXB (tenNXB, diaChi, soDT, email) VALUES ('%s', '%s', '%s', '%s')",
            t.getTenNXB(), t.getDiaChi(), t.getSoDT(), t.getEmail()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("NHAXB");
        rowInserted = jdbcUtil.executeUpdate(sql);
        jdbcUtil.Close();
        t.setMaNXB(nextID);
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String query = String.format("UPDATE NHAXB SET trangThai = 0 WHERE maNXB = '%d'", id);
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(NhaXBDTO t) {
        int rowUpdated = 0;
        String query = String.format(
            "UPDATE NHAXB SET tenNXB = '%s', diaChi = '%s', soDT = '%s', email = '%s' WHERE maNXB = '%d'",
            t.getTenNXB(), t.getDiaChi(), t.getSoDT(), t.getEmail(), t.getMaNXB()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowUpdated;
    }

    public ArrayList<NhaXBDTO> getAll() {
        ArrayList<NhaXBDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM NHAXB WHERE trangThai = 1";
        
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("maNXB");
                String tenNXB = rs.getString("tenNXB");
                String diaChi = rs.getString("diaChi");
                String soDT = rs.getString("soDT");
                String email = rs.getString("email");
                NhaXBDTO nxb = new NhaXBDTO(id, tenNXB, diaChi, soDT, email);
                result.add(nxb);
            }
            jdbcUtil.Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
