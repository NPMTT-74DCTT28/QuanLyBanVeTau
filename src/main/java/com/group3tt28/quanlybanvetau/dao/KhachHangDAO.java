/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.dao;

import com.group3tt28.quanlybanvetau.model.KhachHang;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author qphwn
 */
public class KhachHangDAO {

    private static final String TEN_BANG = "khach_hang";
    private static final String COT_ID = "id";
    private static final String COT_CCCD = "cccd";
    private static final String COT_HO_TEN = "ho_ten";
    private static final String COT_NGAY_SINH = "ngay_sinh";
    private static final String COT_GIOI_TINH = "gioi_tinh";
    private static final String COT_SDT = "sdt";
    private static final String COT_DIA_CHI = "dia_chi";
    
    public boolean insert(KhachHang khachHang) {
        //Viết code INSERT (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }
    
    public boolean update(KhachHang khachHang) {
        //Viết code UPDATE (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }
    
    public boolean delete(int id) {
        //Viết code DELETE (đặt trong try-cacth)
        //return ps.executeUpdate() > 0 (PreparedStatement)
        return true;
    }
    
    public List<KhachHang> getAll() {
        List<KhachHang> list = new ArrayList<>();
        //Viết code SELECT *
        //dùng PreparedStatement + ResultSet
        return list;
    }
}
