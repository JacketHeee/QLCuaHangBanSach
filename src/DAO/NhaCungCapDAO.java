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
        String sql = String.format(
            "INSERT INTO NHACUNGCAP (tenNCC, diaChi, soDT, email) VALUES ('%s', '%s', '%s', '%s')",
            t.getTenNCC(), t.getDiaChi(), t.getSoDT(), t.getEmail()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowInserted = jdbcUtil.executeUpdate(sql);
        int nextID = jdbcUtil.getAutoIncrement("NHACUNGCAP");
        jdbcUtil.Close();
        t.setMaNCC(nextID);
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String sql = String.format("UPDATE NHACUNGCAP SET trangThai = 0 WHERE maNCC = %d", id);
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(sql);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(NhaCungCapDTO t) {
        int rowUpdated = 0;
        String sql = String.format(
            "UPDATE NHACUNGCAP SET tenNCC = '%s', diaChi = '%s', soDT = '%s', email = '%s' WHERE maNCC = %d",
            t.getTenNCC(), t.getDiaChi(), t.getSoDT(), t.getEmail(), t.getMaNCC()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(sql);
        jdbcUtil.Close();
        return rowUpdated;
    }

    public ArrayList<NhaCungCapDTO> getAll() {
        ArrayList<NhaCungCapDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM NHACUNGCAP WHERE trangThai = 1";
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
            while (rs.next()) {
                int maNCC = rs.getInt("maNCC");
                String tenNCC = rs.getString("tenNCC");
                String diaChi = rs.getString("diaChi");
                String soDT = rs.getString("soDT");
                String email = rs.getString("email");
                NhaCungCapDTO ncc = new NhaCungCapDTO(maNCC, tenNCC, diaChi, soDT, email);
                result.add(ncc);
            }
            jdbcUtil.Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
