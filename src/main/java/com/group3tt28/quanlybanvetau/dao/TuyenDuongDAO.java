/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.TuyenDuong;
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
    
    public int insert(TuyenDuong tuyenDuong) {
        //Viết code INSERT (đặt trong try-cacth)
        //return ps.executeUpdate() (PreparedStatement)
        return 0;
    }
    
    public int update(TuyenDuong tuyenDuong) {
        //Viết code UPDATE (đặt trong try-cacth)
        //return ps.executeUpdate() (PreparedStatement)
        return 0;
    }
    
    public int delete(int id) {
        //Viết code DELETE (đặt trong try-cacth)
        //return ps.executeUpdate() (PreparedStatement)
        return 0;
    }
    
    public List<TuyenDuong> getAll() {
        List<TuyenDuong> list = new ArrayList<>();
        //Viết code SELECT *
        //dùng PreparedStatement + ResultSet
        return list;
    }
}
