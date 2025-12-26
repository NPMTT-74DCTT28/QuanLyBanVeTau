/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.Ghe;
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
        //Viết code INSERT (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }
    
    public boolean update(Ghe ghe) {
        //Viết code UPDATE (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }
    
    public boolean delete(int id) {
        //Viết code DELETE (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }
    
    public List<Ghe> getAll() {
        List<Ghe> list = new ArrayList<>();
        //Viết code SELECT *
        //dùng PreparedStatement + ResultSet
        return list;
    }
}
