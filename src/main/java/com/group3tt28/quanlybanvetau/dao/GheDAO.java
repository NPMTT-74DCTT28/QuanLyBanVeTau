/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.Ghe;
import com.group3tt28.quanlybanvetau.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author qphwn
 */
public class GheDAO {
    
    private static final String TEN_BANG = "ghe";
    private static final String COT_ID = "id";
    private static final String COT_SO_GHE = "so_ghe";
    private static final String COT_ID_TOA_TAU = "id_toa_tau";
    
    public boolean insert(Ghe ghe) {
        if (ghe == null) {
            return false;
        }
        
        String sql = "INSERT INTO " + TEN_BANG + " ("
                + COT_SO_GHE + "," + COT_ID_TOA_TAU
                + ") VALUES (?, ?)";
        
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ghe.getSoGhe());
            ps.setInt(2, ghe.getIdToaTau());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi he thong khi them ghe: " + e.getMessage(), e);
        }
        
    }
    
    public boolean update(Ghe ghe) {
        if (ghe == null) {
            return false;
        }
        
        String sql = "UPDATE " + TEN_BANG + " SET "
                + COT_SO_GHE + " = ?, "
                + COT_ID_TOA_TAU + " = ?, "
                + " WHERE " + COT_ID + " = ?";
        
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ghe.getSoGhe());
            ps.setInt(2, ghe.getIdToaTau());
            ps.setInt(3, ghe.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi he thong khi cap nhat nhan vien:" + e.getMessage(), e);
        }
    }
    
    public boolean delete(int id) {
        if (id < 1){
            return false;
        }
        
        String sql = "DELETE FROM " + TEN_BANG 
                + " WHERE " + COT_ID + " = ?";
        
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Xay ra loi he thong khi xoa ghe: " + e.getMessage(), e);
        }
    }
    
    public List<Ghe> getAll() {
        List<Ghe> list = new ArrayList<>();
        //Viết code SELECT *
        //dùng PreparedStatement + ResultSet
        return list;
    }
}
