package DAO;

import DTO.TaiKhoanDTO;
import config.JDBCUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class TaiKhoanDAO implements DAOInterface<TaiKhoanDTO> {
    private static TaiKhoanDAO instance;
    private TaiKhoanDAO() {}
    
    public static TaiKhoanDAO getInstance() {
        if (instance == null) {
            instance = new TaiKhoanDAO();
        }
        return instance;
    }
    
    @Override
    public int insert(TaiKhoanDTO tk) {
        int rowInserted = 0;
        String sql = "INSERT INTO TAIKHOAN (username, password, maRole) VALUES (?,?,?)";

        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("TaiKhoan");
        rowInserted = jdbcUtil.executeUpdate(
            sql,
            tk.getUsername(),
            tk.getPassword(),
            tk.getMaRole()
        );
        jdbcUtil.Close();
        tk.setMaTK(nextID);
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String query = "UPDATE TAIKHOAN SET TRANGTHAI = 0 WHERE maTK = ?";
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query, id);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(TaiKhoanDTO tk) {
        int rowUpdated = 0;
        String query = "UPDATE TAIKHOAN SET username = ?, password = ?, maRole = ? WHERE maTK = ?";

        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(
            query,
            tk.getUsername(),
            tk.getPassword(),
            tk.getMaRole(),
            tk.getMaTK()
        );
        jdbcUtil.Close();
        return rowUpdated;
    }

    public ArrayList<TaiKhoanDTO> getAll() {
        ArrayList<TaiKhoanDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM TAIKHOAN WHERE TRANGTHAI = 1";
        
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int maTK = rs.getInt("maTK");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int maRole = rs.getInt("maRole");
                
                TaiKhoanDTO tk = new TaiKhoanDTO(maTK, username, password, maRole);
                result.add(tk);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return result;
    }

    public TaiKhoanDTO SelectTaiKhoanByUserName(String userName){
        TaiKhoanDTO result = null;
        String sql = "SELECT * FROM taiKhoan WHERE username = ?";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, userName);
        try {
            while(rs.next()){
                int maTK = rs.getInt("maTK");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int maRole = rs.getInt("maRole");

                TaiKhoanDTO tk = new TaiKhoanDTO(maTK, username, password, maRole);
                result = tk;
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }
        jdbcUtil.Close();
        return(result);
    }
    
    public String getUsernameByMaTK(int maTK) {
        String username = null;
        String sql = "SELECT username FROM TAIKHOAN WHERE maTK = ? AND trangThai = 1";
    
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maTK);
    
        try {
            if (rs.next()) {
                username = rs.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        jdbcUtil.Close();
        return username;
    }
    
}
