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
        String sql ="INSERT INTO NHOMQUYEN (tenrole) VALUES (?)";
            
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("NhomQuyen");
        rowInserted = jdbcUtil.executeUpdate(sql, nq.getTenRole());
        jdbcUtil.Close();
        nq.setMaRole(nextID);
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String query = "UPDATE NHOMQUYEN SET TRANGTHAI = 0 WHERE maRole = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query, id);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(NhomQuyenDTO nq) {
        int rowUpdated = 0;
        String query = "UPDATE NHOMQUYEN SET tenrole = ? WHERE maRole = ?";

        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(
            query,
            nq.getTenRole(),
            nq.getMaRole()
        );
        jdbcUtil.Close();
        return rowUpdated;
    }

    public ArrayList<NhomQuyenDTO> getAll() {
        ArrayList<NhomQuyenDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM NHOMQUYEN WHERE TRANGTHAI = 1";
        
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int maRole = rs.getInt("maRole");
                String tenRole = rs.getString("tenrole");
                
                NhomQuyenDTO nq = new NhomQuyenDTO(maRole, tenRole);
                result.add(nq);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public NhomQuyenDTO SelectByID(int maRole){
        NhomQuyenDTO result = null;
        String sql = "SELECT * FROM NHOMQUYEN WHERE maRole = ?";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maRole);
        try {
            while(rs.next()){
                String tenRole = rs.getString("tenrole");
                
                NhomQuyenDTO nq = new NhomQuyenDTO(maRole, tenRole);
                result = nq;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        jdbcUtil.Close();
        return(result);
    }
}