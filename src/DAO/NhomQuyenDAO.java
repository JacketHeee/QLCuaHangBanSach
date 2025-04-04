package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.NhomQuyenDTO;
import config.JDBCUtil;

public class NhomQuyenDAO implements DAOInterface<NhomQuyenDTO> {
    private static NhomQuyenDAO instance;
    private NhomQuyenDAO() {}
    
    public static NhomQuyenDAO getInstance() {
        if (instance == null) {
            instance = new NhomQuyenDAO();
        }
        return instance;
    }
    
    @Override
    public int insert(NhomQuyenDTO nq) {
        int rowInserted = 0;
        String sql = String.format(
            "INSERT INTO NHOMQUYEN (tenrole) VALUES ('%s')",
            nq.getTenRole()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("NhomQuyen");
        rowInserted = jdbcUtil.executeUpdate(sql);
        jdbcUtil.Close();
        nq.setMaRole(nextID);
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String query = String.format("UPDATE NHOMQUYEN SET TRANGTHAI = 0 WHERE maRole = '%d'", id);
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(NhomQuyenDTO nq) {
        int rowUpdated = 0;
        String query = String.format(
            "UPDATE NHOMQUYEN SET tenrole = '%s' WHERE maRole = '%d'",
            nq.getTenRole(),
            nq.getMaRole()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowUpdated;
    }

    public ArrayList<NhomQuyenDTO> getAll() {
        ArrayList<NhomQuyenDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM NHOMQUYEN WHERE TRANGTHAI = 1";
        
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
            while (rs.next()) {
                int maRole = rs.getInt("maRole");
                String tenRole = rs.getString("tenrole");
                
                NhomQuyenDTO nq = new NhomQuyenDTO(maRole, tenRole);
                result.add(nq);
            }
            jdbcUtil.Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public NhomQuyenDTO SelectByID(int maRole){
        NhomQuyenDTO result = null;
        String sql = String.format("SELECT * FROM NHOMQUYEN WHERE maRole = '%d'", maRole);
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
            while(rs.next()){
                String tenRole = rs.getString("tenrole");
                
                NhomQuyenDTO nq = new NhomQuyenDTO(maRole, tenRole);
                result = nq;
            }
            jdbcUtil.Close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return(result);
    }
}