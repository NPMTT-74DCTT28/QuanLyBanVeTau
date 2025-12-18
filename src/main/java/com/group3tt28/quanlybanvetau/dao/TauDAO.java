/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.Tau;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author qphwn
 */
public class TauDAO {

    private static final String TEN_BANG = "tau";
    private static final String COT_ID = "id";
    private static final String COT_MA_TAU = "ma_tau";
    private static final String COT_TEN_TAU = "ten_tau";
    
    public int insert(Tau tau) {
        //Viết code INSERT (đặt trong try-cacth)
        //return ps.executeUpdate() (PreparedStatement)
        return 0;
    }
    
    public int update(Tau tau) {
        //Viết code UPDATE (đặt trong try-cacth)
        //return ps.executeUpdate() (PreparedStatement)
        return 0;
    }
    
    public int delete(int id) {
        //Viết code DELETE (đặt trong try-cacth)
        //return ps.executeUpdate() (PreparedStatement)
        return 0;
    }
    
    public List<Tau> getAll() {
        List<Tau> list = new ArrayList<>();
        //Viết code SELECT *
        //dùng PreparedStatement + ResultSet
        return list;
    }
}
