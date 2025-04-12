package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ChiTietQuyenDTO;
import config.JDBCUtil;

public class ChiTietQuyenDAO implements DAOInterface<ChiTietQuyenDTO> {
    private static ChiTietQuyenDAO instance;
    private ChiTietQuyenDAO() {}
    
    public static ChiTietQuyenDAO getInstance() {
        if (instance == null) {
            instance = new ChiTietQuyenDAO();
        }
        return instance;
    }
    
    @Override
    public int insert(ChiTietQuyenDTO ctq) {
        int rowInserted = 0;
        String sql = "INSERT INTO CHITIETQUYEN (maRole, maChucNang, hanhDong) VALUES (?,?,?)";

        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        rowInserted = jdbcUtil.executeUpdate(
            sql,
            ctq.getMaRole(),
            ctq.getMaChucNang(),
            ctq.getHanhDong()
        );
        jdbcUtil.Close();
        return rowInserted;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public int update(ChiTietQuyenDTO ctq) {
        return 0;
    }

    public ArrayList<ChiTietQuyenDTO> getAll() {
        ArrayList<ChiTietQuyenDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETQUYEN";
        
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql);
        try {
            while (rs.next()) {
                int maRole = rs.getInt("maRole");
                int maChucNang = rs.getInt("maChucNang");
                String hanhDong = rs.getString("hanhDong");
                
                ChiTietQuyenDTO ctq = new ChiTietQuyenDTO(maRole, maChucNang, hanhDong);
                result.add(ctq);
            }
            jdbcUtil.Close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<ChiTietQuyenDTO> selectChiTietQuyenByMaNQ(int maNQ){
        ArrayList<ChiTietQuyenDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETQUYEN WHERE maRole = ?";  
    
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maNQ);
        try {
            while(rs.next()){
                int maRole = rs.getInt("maRole");
                int maChucNang = rs.getInt("maChucNang");
                String hanhDong = rs.getString("hanhDong");
                
                ChiTietQuyenDTO ctq = new ChiTietQuyenDTO(maRole, maChucNang, hanhDong);
                result.add(ctq);
            }      
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return(result);
    }

    public ArrayList<Integer> getListMaCNByMaNQ(int maNQ){
        ArrayList<Integer> result = new ArrayList<>();
        String sql = "SELECT DISTINCT maChucNang FROM CHITIETQUYEN WHERE maRole = ?"; 
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maNQ);
        try {
            while(rs.next()){
                int maChucNang = rs.getInt("maChucNang");
                result.add(maChucNang);
            }
            jdbcUtil.Close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return(result);
    }

    public ArrayList<ChiTietQuyenDTO> getListChiTietQuyenByMaRoleMaCN(int maRole, int maCN){
        ArrayList<ChiTietQuyenDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETQUYEN WHERE maRole = ? AND maChucNang = ?";  

        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.Open();
        ResultSet rs = jdbcUtil.executeQuery(sql, maRole, maCN);
        try {
            while(rs.next()){
                int maChucNang = rs.getInt("maChucNang");
                String hanhDong = rs.getString("hanhDong");
                ChiTietQuyenDTO chiTietQuyenDTO = new ChiTietQuyenDTO(maRole, maChucNang, hanhDong);
                result.add(chiTietQuyenDTO);
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        jdbcUtil.Close();
        return(result);
    }

}
