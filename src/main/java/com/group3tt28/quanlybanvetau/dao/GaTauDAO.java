/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.GaTau;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author qphwn
 */
public class GaTauDAO {

    private static final String TEN_BANG = "ga_tau";
    private static final String COT_ID = "id";
    private static final String COT_MA_GA = "ma_ga";
    private static final String COT_TEN_GA = "ten_ga";
    private static final String COT_DIA_CHI = "dia_chi";
    private static final String COT_THANH_PHO = "thanh_pho";
    
    public int insert(GaTau gaTau) {
        //Viết code INSERT (đặt trong try-cacth)
        //return ps.executeUpdate() (PreparedStatement)
        return 0;
    }
    
    public int update(GaTau gaTau) {
        //Viết code UPDATE (đặt trong try-cacth)
        //return ps.executeUpdate() (PreparedStatement)
        return 0;
    }
    
    public int delete(int id) {
        //Viết code DELETE (đặt trong try-cacth)
        //return ps.executeUpdate() (PreparedStatement)
        return 0;
    }
    
    public List<GaTau> getAll() {
        List<GaTau> list = new ArrayList<>();
        //Viết code SELECT *
        //dùng PreparedStatement + ResultSet
        return list;
    }
}
