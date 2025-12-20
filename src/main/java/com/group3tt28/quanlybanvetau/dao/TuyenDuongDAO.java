/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.TuyenDuong;
import com.group3tt28.quanlybanvetau.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author qphwn
 */
public class TuyenDuongDAO {

    private static final String TEN_BANG = "tuyen_duong";
    private static final String COT_ID = "id";
    private static final String COT_MA_TUYEN = "ma_tuyen";
    private static final String COT_TEN_TUYEN = "ten_tuyen";
    private static final String COT_ID_GA_DI = "id_ga_di";
    private static final String COT_ID_GA_DEN = "id_ga_den";
    private static final String COT_KHOANG_CACH = "khoang_cach_km";
    private static final String COT_GIA_CB = "gia_co_ban";
    public boolean checkTrung(String maTuyen, int idGaDi, int idGaDen){
        if (maTuyen == null|| maTuyen.trim().isEmpty()) {
            return false;
        }
        if (idGaDi == idGaDen) {
            return false;
        }
        String sql = "SELECT "+COT_MA_TUYEN+" FROM " +TEN_BANG+ " WHERE "+COT_MA_TUYEN+" = ?";
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maTuyen);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            } 
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public boolean insert(TuyenDuong tuyenDuong) {
        if (tuyenDuong == null) {
            return false;
        }
        String sql = "INSERT INTO "+TEN_BANG+"("+COT_MA_TUYEN+","
                +COT_TEN_TUYEN+","
                +COT_ID_GA_DI+","
                +COT_ID_GA_DEN+","
                +COT_KHOANG_CACH+","
                +COT_GIA_CB+")"
        + " VALUES(?,?,?,?,?,?)";
        try(Connection conn =DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tuyenDuong.getMaTuyen());
            ps.setString(2, tuyenDuong.getTenTuyen());
            ps.setInt(3, tuyenDuong.getIdGaDi());
            ps.setInt(4, tuyenDuong.getIdGaDen());
            ps.setDouble(5, tuyenDuong.getKhoangCachKm());
            ps.setDouble(6, tuyenDuong.getGiaCoBan());
            return ps.executeUpdate()>0;
        } catch (Exception e) {
            throw new RuntimeException("Xay ra loi khi them tuyen duong:"+e.getMessage(),e);
        }
    }
    
    public boolean update(TuyenDuong tuyenDuong) {
        //Viết code UPDATE (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }
    
    public boolean delete(int id) {
        //Viết code DELETE (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }
    
    public List<TuyenDuong> getAll() {
        List<TuyenDuong> list = new ArrayList<>();
        //Viết code SELECT *
        //dùng PreparedStatement + ResultSet
        return list;
    }
}
