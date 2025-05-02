package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import DTO.NhaCungCapDTO;
import config.JDBCUtil;

public class NhaCungCapDAO implements DAOInterface<NhaCungCapDTO> {
    private static NhaCungCapDAO instance;

    private NhaCungCapDAO() {}

    public static NhaCungCapDAO getInstance() {
        if (instance == null) {
            instance = new NhaCungCapDAO();
        }
        return instance;
    }

    @Override
    public int insert(NhaCungCapDTO t) {
        int rowInserted = 0;
        String sql = "INSERT INTO NHACUNGCAP (tenNCC, diaChi, soDT, email) VALUES (?,?,?,?)";
            
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("NHACUNGCAP");
        rowInserted = jdbcUtil.executeUpdate(
            sql, 
            t.getTenNCC(), 
            t.getDiaChi(), 
            t.getSoDT(), 
            t.getEmail()
        );
        jdbcUtil.Close();
        t.setMaNCC(nextID);
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String sql = "UPDATE NHACUNGCAP SET trangThai = 0 WHERE maNCC = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(sql, id);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(NhaCungCapDTO t) {
        int rowUpdated = 0;
        String sql = "UPDATE NHACUNGCAP SET tenNCC = ?, diaChi = ?, soDT = ?, email = ? WHERE maNCC = ?";

        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(
            sql,
            t.getTenNCC(),
            t.getDiaChi(), 
            t.getSoDT(), 
            t.getEmail(), 
            t.getMaNCC()
        );
        jdbcUtil.Close();
        return rowUpdated;
    }

    public ArrayList<NhaCungCapDTO> getAll() {
        ArrayList<NhaCungCapDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM NHACUNGCAP WHERE trangThai = 1";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int maNCC = rs.getInt("maNCC");
                String tenNCC = rs.getString("tenNCC");
                String diaChi = rs.getString("diaChi");
                String soDT = rs.getString("soDT");
                String email = rs.getString("email");
                NhaCungCapDTO ncc = new NhaCungCapDTO(maNCC, tenNCC, diaChi, soDT, email);
                result.add(ncc);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public NhaCungCapDTO getNhaCungCapById(int maNhaCC) {
        NhaCungCapDTO result = null;
        String sql = "SELECT * FROM NHACUNGCAP WHERE maNCC = " + maNhaCC;
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int maNCC = rs.getInt("maNCC");
                String tenNCC = rs.getString("tenNCC");
                String diaChi = rs.getString("diaChi");
                String soDT = rs.getString("soDT");
                String email = rs.getString("email");
                NhaCungCapDTO ncc = new NhaCungCapDTO(maNCC, tenNCC, diaChi, soDT, email);
                result = ncc;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }
}
