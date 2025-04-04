package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.TaiKhoanDTO;
import config.JDBCUtil;
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
        String sql = String.format(
            "INSERT INTO TAIKHOAN (username, password, maRole) VALUES ('%s', '%s', '%d')",
            tk.getUsername(),
            tk.getPassword(),
            tk.getMaRole()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        int nextID = jdbcUtil.getAutoIncrement("TaiKhoan");
        rowInserted = jdbcUtil.executeUpdate(sql);
        jdbcUtil.Close();
        tk.setMaTK(nextID);
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        int rowDeleted = 0;
        String query = String.format("UPDATE TAIKHOAN SET TRANGTHAI = 0 WHERE maTK = '%d'", id);
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowDeleted = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowDeleted;
    }

    @Override
    public int update(TaiKhoanDTO tk) {
        int rowUpdated = 0;
        String query = String.format(
            "UPDATE TAIKHOAN SET username = '%s', password = '%s', maRole = '%d' WHERE maTK = '%d'",
            tk.getUsername(),
            tk.getPassword(),
            tk.getMaRole(),
            tk.getMaTK()
        );
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowUpdated = jdbcUtil.executeUpdate(query);
        jdbcUtil.Close();
        return rowUpdated;
    }

    public ArrayList<TaiKhoanDTO> getAll() {
        ArrayList<TaiKhoanDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM TAIKHOAN WHERE TRANGTHAI = 1";
        
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
            while (rs.next()) {
                int maTK = rs.getInt("maTK");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int maRole = rs.getInt("maRole");
                
                TaiKhoanDTO tk = new TaiKhoanDTO(maTK, username, password, maRole);
                result.add(tk);
            }
            jdbcUtil.Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public TaiKhoanDTO SelectTaiKhoanByUserName(String userName){
        TaiKhoanDTO result = null;
        String sql = String.format("SELECT * FROM taiKhoan WHERE username = '%s'", userName);
        try {
            JDBCUtil jdbcUtil = new JDBCUtil();
            jdbcUtil.Open();
            ResultSet rs = jdbcUtil.executeQuery(sql);
            while(rs.next()){
                int maTK = rs.getInt("maTK");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int maRole = rs.getInt("maRole");

                TaiKhoanDTO tk = new TaiKhoanDTO(maTK, username, password, maRole);
                result = tk;
            }
            jdbcUtil.Close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return(result);
    }   
}
