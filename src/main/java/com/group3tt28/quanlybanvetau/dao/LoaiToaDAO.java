/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.LoaiToa;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author qphwn
 */
public class LoaiToaDAO {

    private static final String TEN_BANG = "loai_toa";
    private static final String COT_ID = "id";
    private static final String COT_TEN_LOAI = "ten_loai";
    private static final String COT_HE_SO_GIA = "he_so_gia";
    
    public boolean insert(LoaiToa loaiToa) {
        //Viết code INSERT (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }
    
    public boolean update(LoaiToa loaiToa) {
        //Viết code UPDATE (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }
    
    public boolean delete(int id) {
        //Viết code DELETE (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }
    
    public List<LoaiToa> getAll() {
        List<LoaiToa> list = new ArrayList<>();
        //Viết code SELECT *
        //dùng PreparedStatement + ResultSet
        return list;
    }
}
