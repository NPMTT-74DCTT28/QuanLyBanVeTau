/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.ToaTau;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author qphwn
 */
public class ToaTauDAO {

    private static final String TEN_BANG = "toa_tau";
    private static final String COT_ID = "id";
    private static final String COT_MA_TOA = "ma_toa";
    private static final String COT_ID_TAU = "id_tau";
    private static final String COT_ID_LOAI_TOA = "id_loai_toa";
    
    public boolean insert(ToaTau toaTau) {
        //Viết code INSERT (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }
    
    public boolean update(ToaTau toaTau) {
        //Viết code UPDATE (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }
    
    public boolean delete(int id) {
        //Viết code DELETE (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }
    
    public List<ToaTau> getAll() {
        List<ToaTau> list = new ArrayList<>();
        //Viết code SELECT *
        //dùng PreparedStatement + ResultSet
        return list;
    }
}
